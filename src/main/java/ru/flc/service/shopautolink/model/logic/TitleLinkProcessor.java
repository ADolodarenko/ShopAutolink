package ru.flc.service.shopautolink.model.logic;

import ru.flc.service.shopautolink.model.Element;
import ru.flc.service.shopautolink.model.LogEvent;
import ru.flc.service.shopautolink.model.LogEventWriter;
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
			return new LogEvent(Constants.KEY_PROCESSOR_SUCCESS, fileObject.getAbsolutePath());
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
				LogEventWriter.writeMessage(Constants.KEY_PROCESSOR_CANCELLED, logModel);
	}

	private int processLinks()
	{
		int result = -1;

		try
		{
			openAccessObjects();

			List<List<Element>> resultLines = null;

			if (!isCancelled())
			{
				resultLines = dataObject.processLinks();
				fileObject.putResultLines(resultLines);
			}

			result = resultLines.size();
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

	private void openAccessObjects() throws Exception
	{
		fileObject.open();
		dataObject.open();
	}

	private void closeAccessObjects() throws Exception
	{
		dataObject.close();
		fileObject.close();
	}
}
