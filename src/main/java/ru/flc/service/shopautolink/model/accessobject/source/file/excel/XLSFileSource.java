package ru.flc.service.shopautolink.model.accessobject.source.file.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class XLSFileSource extends ExcelFileSource
{
    public XLSFileSource(boolean forWriting)
    {
    	super(forWriting);
	}

	@Override
	protected void getWorkbook() throws IOException
	{
		if (forWriting)
			workbook = new HSSFWorkbook();
		else
			workbook = new HSSFWorkbook(inputStream);
	}
}
