package ru.flc.service.shopautolink.model.accessobject.source.file.excel;

import org.apache.poi.ss.usermodel.*;
import ru.flc.service.shopautolink.model.Element;
import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.accessobject.source.file.FileSource;
import ru.flc.service.shopautolink.model.settings.FileSettings;
import ru.flc.service.shopautolink.model.settings.Settings;
import ru.flc.service.shopautolink.view.Constants;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

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
	public void open() throws Exception
	{
		getWorkbook();
		sheet = workbook.getSheetAt(0);
		rowIterator = sheet.iterator();
	}
	
	@Override
	public void close() throws Exception
	{
		if (workbook != null)
			workbook.close();
		
		sheet = null;
		workbook = null;
	}
	
	@Override
	public void tune(Settings settings) throws Exception
	{
		close();
		
		if (settings != null)
		{
			String settingsClassName = settings.getClass().getSimpleName();
			
			if ("FileSettings".equals(settingsClassName))
				resetParameters((FileSettings)settings);
			else
				throw new IllegalArgumentException(Constants.EXCPT_FILE_SETTINGS_WRONG);
		}
		else
			throw new IllegalArgumentException(Constants.EXCPT_FILE_SETTINGS_EMPTY);
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
			{
				int tempValue = getCellIntValue(cell);

				if (tempValue != -1)
					productCode = String.valueOf(tempValue);

				if (productCode == null)
					return null;
			}

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
	public void putResultLine(List<Element> line) throws Exception
	{
		;
	}
	
	protected boolean hasNextRow()
	{
		return (workbook != null && sheet != null &&
				rowIterator != null && rowIterator.hasNext());
	}

	protected abstract void getWorkbook() throws IOException;

	protected void resetParameters(FileSettings settings)
	{
		this.file = settings.getFile();
	}
}
