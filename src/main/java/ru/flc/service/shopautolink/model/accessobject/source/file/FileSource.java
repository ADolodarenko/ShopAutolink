package ru.flc.service.shopautolink.model.accessobject.source.file;

import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.accessobject.source.Source;

import java.io.IOException;

public interface FileSource extends Source
{
	TitleLink getNextLink();
	void putResultLine(String line) throws Exception;
}
