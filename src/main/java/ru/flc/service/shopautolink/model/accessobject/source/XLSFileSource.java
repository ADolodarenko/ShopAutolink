package ru.flc.service.shopautolink.model.accessobject.source;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.settings.FileSettings;
import ru.flc.service.shopautolink.model.settings.Settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class XLSFileSource implements FileSource
{
	private File file;
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private Iterator<Row> rowIterator;
	private int rowIndex;

    public static XLSFileSource getInstance()
    {
        return SingletonHelper.INSTANCE;
    }

    private XLSFileSource()
    {}

	@Override
	public TitleLink getNextLink()
	{
		int titleId = 0;
		String productCode = "";
		int forSale = 0;

		if (hasNextRow())
		{
			Row row = rowIterator.next();

			for (int i = 0; i < 3; i++)
			{
				;

			}

			return null;
		}
		else
			return null;
	}

	@Override
	public void tune(Settings settings)
	{
		closeWorkbook();

		if (settings != null)
		{
			String settingsClassName = settings.getClass().getSimpleName();

			if ("FileSettings".equals(settingsClassName))
			{
				resetParameters((FileSettings)settings);
				openWorkbook();
			}
		}
	}

	private boolean hasNextRow()
	{
		return (workbook != null && sheet != null &&
				rowIterator != null && rowIterator.hasNext());
	}

	private void resetParameters(FileSettings settings)
	{
		this.file = settings.getFile();
	}

	private void openWorkbook()
	{
		try
		{
			workbook = new HSSFWorkbook(new FileInputStream(file));
			sheet = workbook.getSheetAt(0);
			rowIterator = sheet.iterator();
		}
		catch (IOException e)
		{}
	}

	private void closeWorkbook()
	{
		if (workbook != null)
		{
			try
			{
				workbook.close();
			}
			catch (IOException e)
			{
			}
		}

		sheet = null;
		workbook = null;
		rowIndex = 0;
	}

	private static class SingletonHelper
    {
        private static final XLSFileSource INSTANCE = new XLSFileSource();
    }
}
