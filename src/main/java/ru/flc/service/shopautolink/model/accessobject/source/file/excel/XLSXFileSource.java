package ru.flc.service.shopautolink.model.accessobject.source.file.excel;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class XLSXFileSource extends ExcelFileSource
{
    public XLSXFileSource(boolean forWriting)
    {
    	super(forWriting);
    }

    @Override
    protected void getWorkbook() throws IOException
    {
    	if (forWriting)
    		workbook = new XSSFWorkbook();
    	else
        	workbook = new XSSFWorkbook(inputStream);
    }
}
