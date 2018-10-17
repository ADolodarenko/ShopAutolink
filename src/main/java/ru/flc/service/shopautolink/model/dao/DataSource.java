package ru.flc.service.shopautolink.model.dao;

import ru.flc.service.shopautolink.model.TitleLink;

import java.util.List;

public interface DataSource
{
	boolean clearTitleLinks();
	boolean uploadTitleLinkPack(List<TitleLink> pack);
	boolean updateTitleLinks();
}
