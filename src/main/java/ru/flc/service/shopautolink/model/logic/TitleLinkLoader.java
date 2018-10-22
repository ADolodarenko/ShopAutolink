package ru.flc.service.shopautolink.model.logic;

import ru.flc.service.shopautolink.model.LogEvent;
import ru.flc.service.shopautolink.model.LogEventTableModel;
import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.accessobject.TitleLinkDao;
import ru.flc.service.shopautolink.model.accessobject.TitleLinkFao;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TitleLinkLoader extends SwingWorker<LogEvent, LogEvent>
{
	private static final String FILE_OBJECT_EXCEPTION_STRING = "File access object is empty.";
	private static final String DATA_OBJECT_EXCEPTION_STRING = "Data access object is empty.";
	private static final String SUCCESS_STRING = "Loader_Success";
	private static final String FAILURE_STRING = "Loader_Failure";
	private static final String CANCELLED_STRING = "Loader_Cancelled";
	private static final String PACK_LOADED_STRING = "Loader_Pack_Loaded";
	
    private TitleLinkFao fileObject;
    private TitleLinkDao dataObject;
    private LogEventTableModel logModel;

    public TitleLinkLoader(TitleLinkFao fileObject, TitleLinkDao dataObject,
						   LogEventTableModel logModel)
    {
        if (fileObject == null)
            throw new IllegalArgumentException(FILE_OBJECT_EXCEPTION_STRING);

        if (dataObject == null)
            throw new IllegalArgumentException(DATA_OBJECT_EXCEPTION_STRING);

        this.fileObject = fileObject;
        this.dataObject = dataObject;
        this.logModel = logModel;
    }

    @Override
    protected LogEvent doInBackground() throws Exception
    {
        int result = uploadLinks();

        if (result > -1)
            return new LogEvent(SUCCESS_STRING, result);
        else
            return new LogEvent(FAILURE_STRING);
    }

    @Override
    protected void process(List<LogEvent> chunks)
    {
    	if (isCancelled())
    		return;
    	
    	if (logModel != null)
        	for (LogEvent chunk : chunks)
        		logModel.addRow(chunk);
    }

    @Override
    protected void done()
    {
    	if (logModel != null)
    		if (!isCancelled())
			{
				try
				{
					logModel.addRow(get());
				}
				catch (InterruptedException e)
				{
				}
				catch (ExecutionException e)
				{
				}
			}
        	else
        		logModel.addRow(new LogEvent(CANCELLED_STRING));
    }

    private int uploadLinks()
    {
        int result = -1;
        int linksUploaded = 0;

        try
        {
            while (!isCancelled() && fileObject.hasMoreLinks())
            {
                List<TitleLink> pack = fileObject.getNextLinkPack();
                dataObject.uploadLinkPack(pack);

                linksUploaded += pack.size();

                publish(new LogEvent(PACK_LOADED_STRING, linksUploaded));
            }

            if (!isCancelled())
            {
                dataObject.applyUploadedLinks();
                result = linksUploaded;
            }
        }
        catch (Exception e)
        {

        }

        return result;
    }
}
