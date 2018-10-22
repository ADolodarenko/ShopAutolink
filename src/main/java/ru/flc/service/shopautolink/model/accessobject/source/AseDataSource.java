package ru.flc.service.shopautolink.model.accessobject.source;

import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.settings.Settings;

import java.util.List;

public class AseDataSource implements DataSource
{
	public static AseDataSource getInstance()
	{
		return SingletonHelper.INSTANCE;
	}

	public void tune(Settings settings)
	{
		;
	}

	@Override
	public void applyUploadedTitleLinks()
	{}
	
	@Override
	public void uploadTitleLinkPack(List<TitleLink> pack)
	{}
	
	@Override
	public void processTitleLinks()
	{}

	private static class SingletonHelper
	{
		private static final AseDataSource INSTANCE = new AseDataSource();
	}
}
