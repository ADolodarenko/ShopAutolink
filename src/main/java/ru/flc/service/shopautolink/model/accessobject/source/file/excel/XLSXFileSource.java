package ru.flc.service.shopautolink.model.accessobject.source.file.excel;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class XLSXFileSource extends ExcelFileSource
{
    public static XLSXFileSource getInstance()
    {
        return SingletonHelper.INSTANCE;
    }

    private XLSXFileSource()
    {}

    @Override
    protected void getWorkbook() throws IOException
    {
        workbook = new XSSFWorkbook(inputStream);
    }

    private static class SingletonHelper
    {
        private static final XLSXFileSource INSTANCE = new XLSXFileSource();
    }
}
