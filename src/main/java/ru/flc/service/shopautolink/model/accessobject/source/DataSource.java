package ru.flc.service.shopautolink.model.accessobject.source;

import ru.flc.service.shopautolink.model.TitleLink;

import java.util.List;

public interface DataSource extends Source
{
	void applyUploadedTitleLinks();
	void uploadTitleLinkPack(List<TitleLink> pack);
	void processTitleLinks();
}
