package ru.flc.service.shopautolink.model.accessobject.source.file.excel;

public class ExcelFileSourceFactory
{
    public static ExcelFileSource getSource(String fileNameExtension)
    {
        if (fileNameExtension == null)
            return null;

        String extension = fileNameExtension.toLowerCase();

        if ("xls".equals(extension))
            return XLSFileSource.getInstance();

        if ("xlsx".equals(extension))
            return XLSXFileSource.getInstance();

        return null;
    }
}
