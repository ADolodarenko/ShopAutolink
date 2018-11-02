package ru.flc.service.shopautolink.model.settings;

import org.dav.service.view.Title;

/**
 * This is some kind of streak between an 'interface' class, like an AbstractTableModel successor, and a settings storage of any type.
 * The settings storage has to implement either ValueGetter interface or ValueSetter or both of them.
 * The idea of the Parameter class is that the 'interface' class mustn't know anything about the source of parameter.
 */
public class Parameter
{
    private static final String PARAM_KEY_EXCEPTION_STRING = "Parameter key is empty.";
    private static final String PARAM_TYPE_EXCEPTION_STRING = "Parameter type is empty.";
    private static final String PARAM_GETTER_EXCEPTION_STRING = "Parameter getter is empty.";
    private static final String PARAM_SETTER_EXCEPTION_STRING = "Parameter setter is empty.";

    private Title key;
    private ValueType type;
    private ValueGetter getter;
    private ValueSetter setter;

    public Parameter(Title key, ValueType type, ValueGetter getter, ValueSetter setter)
    {
        if (key == null)
            throw new IllegalArgumentException(PARAM_KEY_EXCEPTION_STRING);

        if (type == null)
            throw new IllegalArgumentException(PARAM_TYPE_EXCEPTION_STRING);

        if (getter == null)
            throw new IllegalArgumentException(PARAM_GETTER_EXCEPTION_STRING);

        if (setter == null)
            throw new IllegalArgumentException(PARAM_SETTER_EXCEPTION_STRING);

        this.key = key;
        this.type = type;
        this.getter = getter;
        this.setter = setter;
    }

    public String getDisplayName()
    {
        return key.getText();
    }

    public ValueType getType()
    {
        return type;
    }

    public boolean getBooleanValue()
    {
        return getter.getBooleanValue(key);
    }

    public int getIntValue()
    {
        return getter.getIntValue(key);
    }

    public double getDoubleValue()
    {
        return getter.getDoubleValue(key);
    }

    public String getStringValue()
    {
        return getter.getStringValue(key);
    }

    public Object getObjectValue()
    {
        return getter.getObjectValue(key);
    }

    public void setBooleanValue(boolean value)
    {
        setter.setBooleanValue(key, value);
    }

    public void setIntValue(int value)
    {
        setter.setIntValue(key, value);
    }

    public void setDoubleValue(double value)
    {
        setter.setDoubleValue(key, value);
    }

    public void setStringValue(String value)
    {
        setter.setStringValue(key, value);
    }

    public void setObjectValue(Object value)
    {
        setter.setObjectValue(key, value);
    }
}
