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
	static int getFieldIntValue(String field)
	{
		int result = -1;

		if (field != null)
			try
			{
				result = Integer.parseInt(field);
			}
			catch (NumberFormatException e)
			{}

		return result;
	}

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

	private boolean forWriting;
	private char elementSeparator = ';';
	private File file;
	private PrintWriter writer;
	private BufferedReader reader;

	public PlainTextFileSource(boolean forWriting)
	{
		this.forWriting = forWriting;
	}
	
	@Override
	public TitleLink getNextLink() throws Exception
	{
		if (forWriting)
			throw new IllegalStateException(Constants.EXCPT_FILE_SOURCE_WRITES);

		String line = reader.readLine();
		if (line == null)
			return null;

		String[] elements = line.split(Character.toString(elementSeparator));
		if (elements.length < 3)
			return null;

		int titleId = getFieldIntValue(elements[0]);
		if (titleId < 0)
			return null;

		String productCode = elements[1];

		int forSale = getFieldIntValue(elements[2]);
		if (forSale < 0)
			return null;

		return new TitleLink(titleId, productCode, forSale);
	}
	
	@Override
	public void putResultLine(List<Element> line) throws Exception
	{
		if (writer != null)
			writer.println(buildStringFromElements(line, elementSeparator));
	}

	@Override
	public String getAbsolutePath()
	{
		if (file != null)
			return file.getAbsolutePath();
		else
			return null;
	}

	@Override
	public void open() throws Exception
	{
		if (forWriting)
			writer = new PrintWriter(file);
		else
			reader = new BufferedReader(new FileReader(file));
	}
	
	@Override
	public void close() throws Exception
	{
		if (writer != null)
			writer.close();

		if (reader != null)
			reader.close();
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
