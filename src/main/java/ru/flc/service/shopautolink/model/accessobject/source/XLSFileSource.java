package ru.flc.service.shopautolink.model.accessobject.source;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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

    public static XLSFileSource getInstance()
    {
        return SingletonHelper.INSTANCE;
    }

	private static int getCellIntValue(Cell cell)
	{
		if (cell != null && cell.getCellType() == CellType.NUMERIC)
			return (int) cell.getNumericCellValue();
		else
			return -1;
	}

	private static String getCellStringValue(Cell cell)
	{
		if (cell != null && cell.getCellType() == CellType.STRING)
			return cell.getStringCellValue();
		else
			return null;
	}

    private XLSFileSource()
    {}

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
	}

	private static class SingletonHelper
    {
        private static final XLSFileSource INSTANCE = new XLSFileSource();
    }
}
