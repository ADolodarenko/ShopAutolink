package ru.flc.service.shopautolink.model.settings;

import org.dav.service.view.Title;

public interface ValueSetter
{
    void setBooleanValue(Title key, boolean value);
    void setIntValue(Title key, int value);
    void setDoubleValue(Title key, double value);
    void setStringValue(Title key, String value);
    void setObjectValue(Title key, Object value);
}
