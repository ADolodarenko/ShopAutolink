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

	private static String buildStringFromElements(List<Element> elementList, String elementDelimiter)
	{
		if (elementList == null || elementList.isEmpty())
			return null;

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < elementList.size(); i++)
		{
			if (i > 0)
				builder.append(elementDelimiter);

			builder.append(elementList.get(i).getValue().toString());
		}

		return builder.toString();
	}

	private boolean forWriting;
	private String elementDelimiter = "\t";
	private File file;
	private PrintWriter writer;
	private BufferedReader reader;

	private int firstCellNumber;

	public PlainTextFileSource(boolean forWriting)
	{
		this.forWriting = forWriting;

		if (!this.forWriting)
			this.firstCellNumber = 0;
	}

	@Override
	public void moveToRow(int rowNumber) throws Exception
	{
		if (forWriting)
			throw new IllegalStateException(Constants.EXCPT_FILE_SOURCE_WRITES);

		if (rowNumber > 1)
		{
			int previousRowNumber = rowNumber - 1;
			int i = 0;

			while (i < previousRowNumber)
			{
				String line = reader.readLine();
				if (line == null)
					throw new Exception(Constants.EXCPT_FILE_SOURCE_FEW_ROWS);

				i++;
			}
		}
	}

	@Override
	public void moveToColumn(int columnNumber) throws Exception
	{
		firstCellNumber = columnNumber - 1;
	}

	@Override
	public TitleLink getNextLink() throws Exception
	{
		if (forWriting)
			throw new IllegalStateException(Constants.EXCPT_FILE_SOURCE_WRITES);

		String line = reader.readLine();
		if (line == null)
			return null;

		String[] elements = line.split(elementDelimiter);
		if (elements.length < (firstCellNumber + 3))
			throw new Exception(Constants.EXCPT_FILE_SOURCE_INCORRECT);

		int titleId = getFieldIntValue(elements[firstCellNumber]);
		if (titleId < 0)
			throw new Exception(Constants.EXCPT_FILE_SOURCE_INCORRECT);

		String productCode = elements[firstCellNumber + 1];

		int forSale = getFieldIntValue(elements[firstCellNumber + 2]);
		if (forSale < 0)
			throw new Exception(Constants.EXCPT_FILE_SOURCE_INCORRECT);

		return new TitleLink(titleId, productCode, forSale);
	}
	
	@Override
	public void putResultLine(List<Element> line) throws Exception
	{
		if (writer != null)
			writer.println(buildStringFromElements(line, elementDelimiter));
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

		String delimiter = settings.getFieldDelimiter();
		if (delimiter == null || delimiter.isEmpty())
			this.elementDelimiter = "\t";
		else
			this.elementDelimiter = delimiter;
	}
}
