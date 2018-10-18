package ru.flc.service.shopautolink.model;

import ru.flc.service.shopautolink.model.dao.TitleLinkDao;
import ru.flc.service.shopautolink.model.fao.TitleLinkFao;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TitleLinkLoader extends SwingWorker<LogEvent, LogEvent>
{
    private TitleLinkFao fileObject;
    private TitleLinkDao dataObject;
    //TODO: declare a table model here

    public TitleLinkLoader(TitleLinkFao fileObject, TitleLinkDao dataObject)
    {
        if (fileObject == null)
            throw new IllegalArgumentException("File access object is empty.");

        if (dataObject == null)
            throw new IllegalArgumentException("Data access object is empty.");

        this.fileObject = fileObject;
        this.dataObject = dataObject;
    }

    @Override
    protected LogEvent doInBackground() throws Exception
    {
        int result = uploadLinks();

        if (result > -1)
            return new LogEvent("Success", result);
        else
            return new LogEvent("Failure");
    }

    @Override
    protected void process(List<LogEvent> chunks)
    {
        //TODO: add the record to the table model
    }

    @Override
    protected void done()
    {
        try
        {
            LogEvent finalEvent = get();

            //TODO: add the record to the table model
        }
        catch (InterruptedException e)
        {}
        catch (ExecutionException e)
        {}
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

                publish(new LogEvent("XXX", linksUploaded));
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
