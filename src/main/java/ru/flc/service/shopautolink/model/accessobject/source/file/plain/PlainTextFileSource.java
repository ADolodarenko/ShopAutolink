package ru.flc.service.shopautolink.model.accessobject.source.file.plain;

import ru.flc.service.shopautolink.model.Element;
import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.accessobject.source.file.FileSource;
import ru.flc.service.shopautolink.model.settings.FileSettings;
import ru.flc.service.shopautolink.model.settings.Settings;
import ru.flc.service.shopautolink.view.Constants;

import java.io.*;
import java.util.List;

public class PlainTextFileSource implements FileSource
{
	private static String buildStringFromElements(List<Element> elementList, char elementSeparator)
	{
		if (elementList == null || elementList.isEmpty())
			return null;

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < elementList.size(); i++)
		{
			if (i > 0)
				builder.append(elementSeparator);

			builder.append(elementList.get(i).getValue().toString());
		}

		return builder.toString();
	}

	private char elementSeparator = ';';

	private File file;
	
	private PrintWriter writer;
	
	@Override
	public TitleLink getNextLink()
	{
		return null;
	}
	
	@Override
	public void putResultLine(List<Element> line) throws Exception
	{
		if (writer != null)
			writer.println(buildStringFromElements(line, elementSeparator));
	}
	
	@Override
	public void open() throws Exception
	{
		writer = new PrintWriter(file) ;
	}
	
	@Override
	public void close() throws Exception
	{
		if (writer != null)
			writer.close();
	}
	
	@Override
	public void tune(Settings settings) throws Exception
	{
		close();
		
		if (settings != null)
		{
			String settingsClassName = settings.getClass().getSimpleName();
			
			if (Constants.CLASS_NAME_FILESETTINGS.equals(settingsClassName))
				resetParameters((FileSettings)settings);
			else
				throw new IllegalArgumentException(Constants.EXCPT_FILE_SETTINGS_WRONG);
		}
		else
			throw new IllegalArgumentException(Constants.EXCPT_FILE_SETTINGS_EMPTY);
	
	}
	
	private void resetParameters(FileSettings settings)
	{
		this.file = settings.getFile();
	}
}
