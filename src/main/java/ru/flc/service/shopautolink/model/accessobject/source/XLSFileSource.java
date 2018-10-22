package ru.flc.service.shopautolink.model.accessobject.source;

import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.settings.Settings;

public class XLSFileSource implements FileSource
{
    public static XLSFileSource getInstance()
    {
        return SingletonHelper.INSTANCE;
    }

    private XLSFileSource()
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
        private static final XLSFileSource INSTANCE = new XLSFileSource();
    }
}
