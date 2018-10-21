package ru.flc.service.shopautolink.model.logic;

import ru.flc.service.shopautolink.model.LogEvent;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

public class TitleLinkProcessor extends SwingWorker<LogEvent, LogEvent>
{
    @Override
    protected LogEvent doInBackground() throws Exception
    {
        long sum = 0;

        for (int i = 0; i < 10; i++)
            try
            {
                Thread.sleep(1000);

                sum += i;
            }
            catch (InterruptedException e)
            {}

        return new LogEvent("%n", sum);
    }

    @Override
    protected void done()
    {
        try
        {
            System.out.println(get().getText());
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
    }
}