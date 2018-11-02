package ru.flc.service.shopautolink.model.logic;

import ru.flc.service.shopautolink.model.LogEvent;
import ru.flc.service.shopautolink.view.table.LogEventTableModel;
import ru.flc.service.shopautolink.model.accessobject.TitleLinkDao;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TitleLinkProcessor extends SwingWorker<LogEvent, LogEvent>
{
    private static final String DATA_OBJECT_EXCEPTION_STRING = "Data access object is empty.";
    private static final String SUCCESS_STRING = "Processor_Success";
    private static final String FAILURE_STRING = "Processor_Failure";
    private static final String CANCELLED_STRING = "Loader_Cancelled";

    private TitleLinkDao dataObject;
    private LogEventTableModel logModel;

    public TitleLinkProcessor(TitleLinkDao dataObject, LogEventTableModel logModel)
    {
        if (dataObject == null)
            throw new IllegalArgumentException(DATA_OBJECT_EXCEPTION_STRING);

        this.dataObject = dataObject;
        this.logModel = logModel;
    }

    @Override
    protected LogEvent doInBackground() throws Exception
    {
        int result = processLinks();

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

    private int processLinks()
    {
        int result = -1;

        try
        {
            dataObject.open();
            dataObject.processLinks();

            result = 0;
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
