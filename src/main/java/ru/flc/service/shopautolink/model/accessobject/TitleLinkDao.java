package ru.flc.service.shopautolink.model.accessobject;

import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.accessobject.source.database.DataSource;

import java.util.List;

public class TitleLinkDao implements AccessObject
{
	private static final String DATA_SOURCE_EXCEPTION_STRING = "Empty data source.";

	private DataSource source;
	
	public TitleLinkDao(DataSource source)
	{
		if (source == null)
			throw new IllegalArgumentException(DATA_SOURCE_EXCEPTION_STRING);
		
		this.source = source;
	}
	
	@Override
	public void open() throws Exception
	{
		source.open();
	}
	
	@Override
	public void close() throws Exception
	{
		source.close();
	}
	
	public void uploadLinkPack(List<TitleLink> pack) throws Exception
	{
		source.uploadTitleLinkPack(pack);
	}
	
	public void applyUploadedLinks() throws Exception
	{
		source.applyUploadedTitleLinks();
	}
	
	public void processLinks() throws Exception
	{
		source.processTitleLinks();
	}
}
