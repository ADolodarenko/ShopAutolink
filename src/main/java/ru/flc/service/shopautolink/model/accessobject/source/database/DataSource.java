package ru.flc.service.shopautolink.model.accessobject.source.database;

import ru.flc.service.shopautolink.model.Element;
import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.accessobject.source.Source;

import java.sql.SQLException;
import java.util.List;

public interface DataSource extends Source
{
	void applyUploadedTitleLinks() throws SQLException;
	void uploadTitleLinkPack(List<TitleLink> pack) throws Exception;
	List<List<Element>> processTitleLinks() throws Exception;
}
