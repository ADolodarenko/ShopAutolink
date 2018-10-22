package ru.flc.service.shopautolink.model.accessobject.source;

import ru.flc.service.shopautolink.model.TitleLink;

public interface FileSource extends Source
{
	TitleLink getNextLink();
}
