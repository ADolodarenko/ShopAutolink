package ru.flc.service.shopautolink.model.accessobject;

import ru.flc.service.shopautolink.model.accessobject.source.Source;

public abstract class AccessObject
{
    protected Source source;

    protected void checkSource(String exceptionString) throws Exception
    {
        if (source == null)
            throw new Exception(exceptionString);
    }
}
