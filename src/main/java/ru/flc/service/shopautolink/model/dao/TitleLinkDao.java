package ru.flc.service.shopautolink.model.dao;

import ru.flc.service.shopautolink.model.TitleLink;

import java.util.List;

public class TitleLinkDao
{
	private DataSource dataSource;
	
	public TitleLinkDao(DataSource dataSource)
	{
		if (dataSource == null)
			throw new IllegalArgumentException("Empty data source.");
		
		this.dataSource = dataSource;
	}
	
	public boolean uploadLinkPack(List<TitleLink> pack)
	{
		return dataSource.uploadTitleLinkPack(pack);
	}
	
	public boolean clearLinks()
	{
		return dataSource.clearTitleLinks();
	}
	
	public boolean updateLinks()
	{
		return dataSource.updateTitleLinks();
	}
}
