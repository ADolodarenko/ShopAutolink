package ru.flc.service.shopautolink.model.accessobject.source.file;

import ru.flc.service.shopautolink.model.accessobject.source.file.excel.XLSFileSource;
import ru.flc.service.shopautolink.model.accessobject.source.file.excel.XLSXFileSource;
import ru.flc.service.shopautolink.model.accessobject.source.file.plain.PlainTextFileSource;

public class FileSourceFactory
{
    public static FileSource getSource(String fileNameExtension)
    {
        if (fileNameExtension == null)
            return null;

        String extension = fileNameExtension.toLowerCase();
        
        if ("xls".equals(extension))
            return XLSFileSource.getInstance();

        if ("xlsx".equals(extension))
            return XLSXFileSource.getInstance();
        
        if ("txt".equals(extension) || "csv".equals(extension))
            return new PlainTextFileSource();

        return null;
    }
}
