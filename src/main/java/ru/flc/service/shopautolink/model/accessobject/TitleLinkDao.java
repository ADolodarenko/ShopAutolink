package ru.flc.service.shopautolink.model.accessobject;

import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.accessobject.source.DataSource;

import java.util.List;

public class TitleLinkDao extends AccessObject
{
	private static final String DATA_SOURCE_EXCEPTION_STRING = "Empty data source.";

	public static TitleLinkDao getInstance()
	{
		return SingletonHelper.INSTANCE;
	}

	private TitleLinkDao()
	{}

	public void setDataSource(DataSource dataSource)
	{
		this.source = dataSource;
	}
	
	public void uploadLinkPack(List<TitleLink> pack) throws Exception
	{
		checkSource(DATA_SOURCE_EXCEPTION_STRING);
		((DataSource)source).uploadTitleLinkPack(pack);
	}
	
	public void applyUploadedLinks() throws Exception
	{
		checkSource(DATA_SOURCE_EXCEPTION_STRING);
		((DataSource)source).applyUploadedTitleLinks();
	}
	
	public void processLinks() throws Exception
	{
		checkSource(DATA_SOURCE_EXCEPTION_STRING);
		((DataSource)source).processTitleLinks();
	}

	private static class SingletonHelper
	{
		private static final TitleLinkDao INSTANCE = new TitleLinkDao();
	}
}
