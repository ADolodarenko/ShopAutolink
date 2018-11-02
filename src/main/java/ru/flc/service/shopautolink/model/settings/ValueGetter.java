package ru.flc.service.shopautolink.model.settings;

import org.dav.service.view.Title;

public interface ValueGetter
{
    boolean getBooleanValue(Title key);
    int getIntValue(Title key);
    double getDoubleValue(Title key);
    String getStringValue(Title key);
    Object getObjectValue(Title key);
}
