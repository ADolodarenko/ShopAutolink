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
	
	public void uploadLinkPack(List<TitleLink> pack)
	{
		dataSource.uploadTitleLinkPack(pack);
	}
	
	public void applyUploadedLinks()
	{
		dataSource.applyUploadedTitleLinks();
	}
	
	public void processLinks()
	{
		dataSource.processTitleLinks();
	}
}
