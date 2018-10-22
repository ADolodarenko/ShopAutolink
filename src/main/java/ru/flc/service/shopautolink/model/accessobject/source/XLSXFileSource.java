package ru.flc.service.shopautolink.model.accessobject.source;

import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.settings.Settings;

public class XLSXFileSource implements FileSource
{
    public static XLSXFileSource getInstance()
    {
        return SingletonHelper.INSTANCE;
    }

    private XLSXFileSource()
    {}

    @Override
    public TitleLink getNextLink()
    {
        return null;
    }

    @Override
    public void tune(Settings settings)
    {

    }

    private static class SingletonHelper
    {
        private static final XLSXFileSource INSTANCE = new XLSXFileSource();
    }
}
