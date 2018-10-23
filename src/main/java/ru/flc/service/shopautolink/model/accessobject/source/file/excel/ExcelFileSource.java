package ru.flc.service.shopautolink.model.accessobject.source.file.excel;

import org.apache.poi.ss.usermodel.*;
import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.accessobject.source.file.FileSource;
import ru.flc.service.shopautolink.model.settings.FileSettings;
import ru.flc.service.shopautolink.model.settings.Settings;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public abstract class ExcelFileSource implements FileSource
{
	protected File file;
	protected Workbook workbook;
	protected Sheet sheet;
	protected Iterator<Row> rowIterator;

	static int getCellIntValue(Cell cell)
	{
		if (cell != null && cell.getCellType() == CellType.NUMERIC)
			return (int) cell.getNumericCellValue();
		else
			return -1;
	}

	static String getCellStringValue(Cell cell)
	{
		if (cell != null && cell.getCellType() == CellType.STRING)
			return cell.getStringCellValue();
		else
			return null;
	}

	@Override
	public TitleLink getNextLink()
	{
		if (hasNextRow())
		{
			Row row = rowIterator.next();

			Cell cell = row.getCell(0);
			int titleId = getCellIntValue(cell);
			if (titleId < 0)
				return null;

			cell = row.getCell(1);
			String productCode = getCellStringValue(cell);
			if (productCode == null)
				return null;

			cell = row.getCell(2);
			int forSale = getCellIntValue(cell);
			if (forSale < 0)
				return null;

			return new TitleLink(titleId, productCode, forSale);
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
				prepareWorkbook();
			}
		}
	}

	protected boolean hasNextRow()
	{
		return (workbook != null && sheet != null &&
				rowIterator != null && rowIterator.hasNext());
	}

	protected void prepareWorkbook()
	{
		try
		{
			getWorkbook();
			sheet = workbook.getSheetAt(0);
			rowIterator = sheet.iterator();
		}
		catch (IOException e)
		{}
	}

	protected abstract void getWorkbook() throws IOException;

	protected void closeWorkbook()
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
	}

	protected void resetParameters(FileSettings settings)
	{
		this.file = settings.getFile();
	}
}
