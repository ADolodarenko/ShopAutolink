package ru.flc.service.shopautolink.model.logic;

import ru.flc.service.shopautolink.model.LogEvent;
import ru.flc.service.shopautolink.model.LogEventWriter;
import ru.flc.service.shopautolink.view.Constants;
import ru.flc.service.shopautolink.view.table.LogEventTableModel;
import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.accessobject.TitleLinkDao;
import ru.flc.service.shopautolink.model.accessobject.TitleLinkFao;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TitleLinkLoader extends SwingWorker<LogEvent, LogEvent>
{
	private TitleLinkFao fileObject;
    private TitleLinkDao dataObject;
    private LogEventTableModel logModel;
    private int firstRowInFile;
	private int firstColumnInFile;

    public TitleLinkLoader(TitleLinkFao fileObject, TitleLinkDao dataObject,
						   LogEventTableModel logModel,
						   int firstRowInFile, int firstColumnInFile)
    {
		if (fileObject == null)
            throw new IllegalArgumentException(Constants.EXCPT_FILE_OBJECT_EMPTY);

        if (dataObject == null)
            throw new IllegalArgumentException(Constants.EXCPT_DATA_OBJECT_EMPTY);

        this.fileObject = fileObject;
        this.dataObject = dataObject;
        this.logModel = logModel;
        this.firstRowInFile = firstRowInFile;
        this.firstColumnInFile = firstColumnInFile;
    }

    @Override
    protected LogEvent doInBackground() throws Exception
    {
        int result = uploadLinks();

        if (result > -1)
            return new LogEvent(Constants.KEY_LOADER_SUCCESS, result);
        else
            return new LogEvent(Constants.KEY_LOADER_FAILURE);
    }

    @Override
    protected void process(List<LogEvent> chunks)
    {
    	if (isCancelled())
    		return;
    	
    	if (logModel != null)
        	for (LogEvent chunk : chunks)
				LogEventWriter.writeEvent(chunk, logModel);
    }

    @Override
    protected void done()
    {
    	if (logModel != null)
    		if (!isCancelled())
			{
				try
				{
					LogEventWriter.writeEvent(get(), logModel);
				}
				catch (InterruptedException e)
				{
				}
				catch (ExecutionException e)
				{
				}
			}
        	else
				LogEventWriter.writeMessage(Constants.KEY_LOADER_CANCELLED, logModel);
    }

    private int uploadLinks()
    {
        int result = -1;
        int linksUploaded = 0;

        try
        {
        	prepareAccessObjects();
        	
            while (!isCancelled() && fileObject.hasMoreLinks())
            {
                List<TitleLink> pack = fileObject.getNextLinkPack();
                dataObject.uploadLinkPack(pack);

                linksUploaded += pack.size();

                publish(new LogEvent(Constants.KEY_LOADER_PACK_LOADED, linksUploaded));
            }

            if (!isCancelled())
            {
                dataObject.applyUploadedLinks();

                if (linksUploaded > 0)
                    result = linksUploaded;
            }
        }
        catch (Exception e)
        {
			LogEventWriter.writeThrowable(e, logModel);
        }
        finally
		{
			try
			{
				closeAccessObjects();
			}
			catch (Exception e)
			{}
		}

        return result;
    }
    
    private void prepareAccessObjects() throws Exception
	{
		fileObject.open();

		if (firstRowInFile > 1)
			fileObject.moveToRow(firstRowInFile);
		if (firstColumnInFile > 1)
			fileObject.moveToColumn(firstColumnInFile);

		dataObject.open();
	}
	
	private void closeAccessObjects() throws Exception
	{
		dataObject.close();
		fileObject.close();
	}
}
