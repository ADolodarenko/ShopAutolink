package ru.flc.service.shopautolink.model.logic;

import ru.flc.service.shopautolink.model.LogEvent;
import ru.flc.service.shopautolink.model.accessobject.TitleLinkFao;
import ru.flc.service.shopautolink.view.Constants;
import ru.flc.service.shopautolink.view.table.LogEventTableModel;
import ru.flc.service.shopautolink.model.accessobject.TitleLinkDao;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TitleLinkProcessor extends SwingWorker<LogEvent, LogEvent>
{
    private TitleLinkDao dataObject;
    private TitleLinkFao fileObject;
    private LogEventTableModel logModel;

    public TitleLinkProcessor(TitleLinkDao dataObject, TitleLinkFao fileObject, LogEventTableModel logModel)
    {
        if (dataObject == null)
            throw new IllegalArgumentException(Constants.EXCPT_DATA_OBJECT_EMPTY);
    
        if (fileObject == null)
            throw new IllegalArgumentException(Constants.EXCPT_FILE_OBJECT_EMPTY);

        this.dataObject = dataObject;
        this.fileObject = fileObject;
        this.logModel = logModel;
    }

    @Override
    protected LogEvent doInBackground() throws Exception
    {
        int result = processLinks();

        if (result > -1)
            return new LogEvent(Constants.KEY_PROCESSOR_SUCCESS, result);
        else
            return new LogEvent(Constants.KEY_PROCESSOR_FAILURE);
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
                logModel.addRow(new LogEvent(Constants.KEY_PROCESSOR_CANCELLED));
    }

    private int processLinks()
    {
        int result = -1;

        try
        {
            dataObject.open();
            List<String> resultLines = dataObject.processLinks();
            
            fileObject.putResultLines(resultLines);

            result = resultLines.size();
        }
        catch (Exception e)
        {

        }
        finally
        {
            try
            {
                dataObject.close();
            }
            catch (Exception e)
            {}
        }

        return result;
    }
}
