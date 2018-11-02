package ru.flc.service.shopautolink.model.settings;

import org.dav.service.view.Title;

public class ParameterAlt
{
    private static final String PARAM_KEY_EXCEPTION_STRING = "Parameter key is empty.";
    private static final String PARAM_VALUE_EXCEPTION_STRING = "Parameter value is empty.";
    private static final String PARAM_TYPE_EXCEPTION_STRING = "Parameter type is empty.";

    private Title key;
    private Object value;
    private Class<?> type;

    public ParameterAlt(Title key, Object value, Class<?> type)
    {
        if (key == null)
            throw new IllegalArgumentException(PARAM_KEY_EXCEPTION_STRING);

        if (value == null)
            throw new IllegalArgumentException(PARAM_VALUE_EXCEPTION_STRING);

        if (type == null)
            throw new IllegalArgumentException(PARAM_TYPE_EXCEPTION_STRING);

        this.key = key;
        this.value = value;
        this.type = type;
    }

    public String getDisplayName()
    {
        return key.getText();
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public Class<?> getType()
    {
        return type;
    }
}
