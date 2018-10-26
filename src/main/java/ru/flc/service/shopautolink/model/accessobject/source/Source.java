package ru.flc.service.shopautolink.model.accessobject.source;

import ru.flc.service.shopautolink.model.settings.Settings;

public interface Source
{
    void tune(Settings settings) throws Exception;
}
