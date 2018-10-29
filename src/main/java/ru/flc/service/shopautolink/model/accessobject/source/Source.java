package ru.flc.service.shopautolink.model.accessobject.source;

import ru.flc.service.shopautolink.model.settings.Settings;

public interface Source
{
    void open() throws Exception;
    void close() throws Exception;
    void tune(Settings settings) throws Exception;
}
