package ru.flc.service.shopautolink.model.accessobject.source.file;

import ru.flc.service.shopautolink.model.Element;
import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.accessobject.source.Source;

import java.util.List;

public interface FileSource extends Source
{
	TitleLink getNextLink();
	void putResultLine(List<Element> line) throws Exception;
}
