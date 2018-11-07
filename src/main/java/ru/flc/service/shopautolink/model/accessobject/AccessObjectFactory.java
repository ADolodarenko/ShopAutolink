package ru.flc.service.shopautolink.model.accessobject;

import org.apache.commons.io.FilenameUtils;
import ru.flc.service.shopautolink.model.accessobject.source.database.AseDataSource;
import ru.flc.service.shopautolink.model.accessobject.source.database.DataSource;
import ru.flc.service.shopautolink.model.accessobject.source.file.FileSource;
import ru.flc.service.shopautolink.model.accessobject.source.file.FileSourceFactory;
import ru.flc.service.shopautolink.model.settings.DatabaseSettings;
import ru.flc.service.shopautolink.model.settings.FileSettings;

import java.io.File;

public class AccessObjectFactory
{
    public static TitleLinkFao getFileAccessObject(FileSettings settings)
    {
        if (settings == null)
            return null;

        File sourceFile = settings.getFile();
        String fileNameExtension = FilenameUtils.getExtension(sourceFile.getAbsolutePath());

        FileSource source = FileSourceFactory.getSource(fileNameExtension);

        if (source == null)
            return null;

        TitleLinkFao object = null;

        try
        {
            source.tune(settings);
            object = new TitleLinkFao(source, settings.getPackSize());
        }
        catch (Exception e)
        {}

        return object;
    }

    public static TitleLinkDao getDataAccessObject(DatabaseSettings settings)
    {
        if (settings == null)
            return null;

        DataSource source = AseDataSource.getInstance();

        TitleLinkDao object = null;

        try
        {
            source.tune(settings);
            object = new TitleLinkDao(source);
        }
        catch (Exception e)
        {}

        return object;
    }
}
