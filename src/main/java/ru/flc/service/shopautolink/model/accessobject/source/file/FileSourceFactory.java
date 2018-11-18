package ru.flc.service.shopautolink.model.accessobject.source.file;

import ru.flc.service.shopautolink.model.accessobject.source.file.excel.XLSFileSource;
import ru.flc.service.shopautolink.model.accessobject.source.file.excel.XLSXFileSource;
import ru.flc.service.shopautolink.model.accessobject.source.file.plain.PlainTextFileSource;

public class FileSourceFactory
{
    public static FileSource getSource(String fileNameExtension, boolean forWriting)
    {
        if (fileNameExtension == null)
            return null;

        String extension = fileNameExtension.toLowerCase();
        
        if ("xls".equals(extension))
            return new XLSFileSource(forWriting);

        if ("xlsx".equals(extension))
            return new XLSXFileSource(forWriting);
        
        if ("txt".equals(extension) || "csv".equals(extension))
            return new PlainTextFileSource();

        return null;
    }
}
