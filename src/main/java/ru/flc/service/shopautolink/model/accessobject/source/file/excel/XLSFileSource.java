package ru.flc.service.shopautolink.model.accessobject.source.file.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class XLSFileSource extends ExcelFileSource
{
    public static XLSFileSource getInstance()
    {
        return SingletonHelper.INSTANCE;
    }

    private XLSFileSource()
    {}

	@Override
	protected void getWorkbook() throws IOException
	{
		workbook = new HSSFWorkbook(inputStream);
	}

	private static class SingletonHelper
    {
        private static final XLSFileSource INSTANCE = new XLSFileSource();
    }
}
