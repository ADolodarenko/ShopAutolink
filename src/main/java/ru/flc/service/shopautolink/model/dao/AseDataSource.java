package ru.flc.service.shopautolink.model.dao;

import ru.flc.service.shopautolink.model.TitleLink;

import java.util.List;

public class AseDataSource implements DataSource
{
	@Override
	public boolean clearTitleLinks()
	{
		return false;
	}
	
	@Override
	public boolean uploadTitleLinkPack(List<TitleLink> pack)
	{
		return false;
	}
	
	@Override
	public boolean updateTitleLinks()
	{
		return false;
	}
}
