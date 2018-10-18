package ru.flc.service.shopautolink.model.dao;

import ru.flc.service.shopautolink.model.TitleLink;

import java.util.List;

public interface DataSource
{
	void applyUploadedTitleLinks();
	void uploadTitleLinkPack(List<TitleLink> pack);
	void processTitleLinks();
}
