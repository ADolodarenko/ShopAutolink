package ru.flc.service.shopautolink.model.accessobject.source.file.excel;

import org.apache.poi.ss.usermodel.*;
import ru.flc.service.shopautolink.model.Element;
import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.accessobject.source.file.FileSource;
import ru.flc.service.shopautolink.model.settings.FileSettings;
import ru.flc.service.shopautolink.model.settings.Settings;
import ru.flc.service.shopautolink.view.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public abstract class ExcelFileSource implements FileSource
{
	private File file;
	
	private CellStyle dateCellStyle;
	
	protected FileInputStream inputStream;
	protected Workbook workbook;
	protected Sheet sheet;
	protected Iterator<Row> rowIterator;
	protected boolean forWriting;

	private int lastRowNum;

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
	
	protected ExcelFileSource(boolean forWriting)
	{
		this.forWriting = forWriting;

		if (this.forWriting)
			this.lastRowNum = 0;
	}
	
	@Override
	public void open() throws Exception
	{
		if (!forWriting)
			inputStream = new FileInputStream(file);
		
		getWorkbook();
		prepareSheet();
	}
	
	@Override
	public void close() throws Exception
	{
		if (inputStream != null)
			inputStream.close();
		
		if (workbook != null)
		{
			if (forWriting)
			{
				FileOutputStream outputStream = new FileOutputStream(file);
				workbook.write(outputStream);
				outputStream.close();
			}
			
			workbook.close();
		}
		
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
			
			if (Constants.CLASS_NAME_FILESETTINGS.equals(settingsClassName))
				resetParameters((FileSettings)settings);
			else
				throw new IllegalArgumentException(Constants.EXCPT_FILE_SETTINGS_WRONG);
		}
		else
			throw new IllegalArgumentException(Constants.EXCPT_FILE_SETTINGS_EMPTY);
	}
	
	@Override
	public TitleLink getNextLink() throws Exception
	{
		if (forWriting)
			throw new IllegalStateException(Constants.EXCPT_FILE_SOURCE_WRITES);
		
		if (hasNextRow())
		{
			Row row = rowIterator.next();

			Cell cell = row.getCell(0);
			int titleId = getCellIntValue(cell);
			if (titleId < 0)
				throw new Exception(Constants.EXCPT_FILE_SOURCE_INCORRECT);

			cell = row.getCell(1);
			String productCode = getCellStringValue(cell);
			if (productCode == null)
			{
				int tempValue = getCellIntValue(cell);

				if (tempValue != -1)
					productCode = String.valueOf(tempValue);

				if (productCode == null)
					throw new Exception(Constants.EXCPT_FILE_SOURCE_INCORRECT);
			}

			cell = row.getCell(2);
			int forSale = getCellIntValue(cell);
			if (forSale < 0)
				throw new Exception(Constants.EXCPT_FILE_SOURCE_INCORRECT);

			return new TitleLink(titleId, productCode, forSale);
		}
		else
			return null;
	}
	
	@Override
	public void putResultLine(List<Element> line) throws Exception
	{
		if (!forWriting)
			throw new IllegalStateException(Constants.EXCPT_FILE_SOURCE_READS);
		
		Row newRow = sheet.createRow(lastRowNum++);
		
		for (int i = 0; i < line.size(); i++)
		{
			Cell cell = newRow.createCell(i);
			Element currentElement = line.get(i);
			
			String valueClassName = currentElement.getType().getSimpleName();
			
			switch (valueClassName)
			{
				case Constants.CLASS_NAME_DOUBLE:
					cell.setCellValue((Double) currentElement.getValue());
					break;
				case Constants.CLASS_NAME_BOOLEAN:
					cell.setCellValue((Boolean) currentElement.getValue());
					break;
				case Constants.CLASS_NAME_DATE:
					cell.setCellStyle(dateCellStyle);
					cell.setCellValue((Date) currentElement.getValue());
					sheet.autoSizeColumn(i);
					break;
				default:
					cell.setCellValue(currentElement.getValue().toString());
			}
		}
	}

	@Override
	public String getAbsolutePath()
	{
		if (file != null)
			return file.getAbsolutePath();
		else
			return null;
	}

	private void prepareSheet()
	{
		if (forWriting)
		{
			sheet = workbook.createSheet();
			
			DataFormat format = workbook.createDataFormat();
			dateCellStyle = workbook.createCellStyle();
			dateCellStyle.setDataFormat(format.getFormat("dd.MM.yyyy HH:mm:ss"));
		}
		else
		{
			sheet = workbook.getSheetAt(0);
			rowIterator = sheet.iterator();
		}
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
