package ru.flc.service.shopautolink.model;

import ru.flc.service.shopautolink.view.Constants;
import ru.flc.service.shopautolink.view.table.LogEventTableModel;

import java.sql.SQLWarning;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogEventWriter
{
	private static Logger logger = Logger.getLogger(LogEventWriter.class.getName());

	public static void writeMessage(String message, LogEventTableModel model)
	{
		if (message == null || message.isEmpty())
			return;

		if (model != null)
			model.addRow(new LogEvent(message));

		logger.log(Level.INFO, message);
	}

	public static void writeEvent(LogEvent event, LogEventTableModel model)
	{
		if (event == null)
			return;

		if (model != null)
			model.addRow(event);

		logger.log(Level.INFO, event.getText());
	}

	public static void writeThrowable(Throwable throwable, LogEventTableModel model)
	{
		if (throwable == null)
			return;

		String className = throwable.getClass().getSimpleName();

		if (Constants.CLASS_NAME_SQLWARNING.equals(className))
		{
			SQLWarning warning = (SQLWarning) throwable;

			if (model != null)
				writeSQLWarningChain(warning, model);
			else
				writeSQLWarningChain(warning);
		}
		else
		{
			if (model != null)
				writeThrowableChain(throwable, model);
			else
				writeThrowableChain(throwable);
		}
	}

	private static void writeThrowableChain(Throwable throwable, LogEventTableModel model)
	{
		while (throwable != null)
		{
			model.addRow(new LogEvent(throwable));
			logger.log(Level.SEVERE, "Exception: ", throwable);

			throwable = throwable.getCause();
		}
	}

	private static void writeThrowableChain(Throwable throwable)
	{
		while (throwable != null)
		{
			logger.log(Level.SEVERE, "Exception: ", throwable);

			throwable = throwable.getCause();
		}
	}

	private static void writeSQLWarningChain(SQLWarning warning, LogEventTableModel model)
	{
		while (warning != null)
		{
			model.addRow(new LogEvent(warning));
			logger.log(Level.WARNING, "Warning: ", warning);

			warning = warning.getNextWarning();
		}
	}

	private static void writeSQLWarningChain(SQLWarning warning)
	{
		while (warning != null)
		{
			logger.log(Level.WARNING, "Warning: ", warning);

			warning = warning.getNextWarning();
		}
	}
}
