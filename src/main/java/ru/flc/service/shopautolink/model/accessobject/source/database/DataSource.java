package ru.flc.service.shopautolink.model.accessobject.source.database;

import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.accessobject.source.Source;

import java.util.List;

public interface DataSource extends Source
{
	void applyUploadedTitleLinks();
	void uploadTitleLinkPack(List<TitleLink> pack);
	void processTitleLinks();
}
