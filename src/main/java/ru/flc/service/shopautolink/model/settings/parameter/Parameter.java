package ru.flc.service.shopautolink.model.settings.parameter;

import org.dav.service.view.Title;
import ru.flc.service.shopautolink.model.settings.type.Password;
import ru.flc.service.shopautolink.view.Constants;

import java.io.File;

public class Parameter
{
	public static String[] getTitleKeys()
	{
		return new String[] { Constants.KEY_COLUMN_PARAM_NAME, Constants.KEY_COLUMN_PARAM_VALUE};
	}
	
    private Title key;
    private Object value;
    private Class<?> type;
    private boolean visible;

    public Parameter(Title key, Object value, Class<?> type, boolean visible)
    {
    	if (key == null)
            throw new IllegalArgumentException(Constants.EXCPT_PARAM_KEY_EMPTY);

        if (value == null)
            throw new IllegalArgumentException(Constants.EXCPT_PARAM_VALUE_EMPTY);

        if (type == null)
            throw new IllegalArgumentException(Constants.EXCPT_PARAM_TYPE_EMPTY);

        this.key = key;
        this.value = value;
        this.type = type;
        this.visible = visible;
    }

    public String getDisplayName()
    {
        return key.getText();
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value) throws IllegalArgumentException
    {
    	if (value == null)
    		throw new IllegalArgumentException(Constants.EXCPT_PARAM_VALUE_EMPTY);
    	
    	String thisClassName = getType().getSimpleName();
    	String thatClassName = value.getClass().getSimpleName();
    	
    	if (thisClassName.equals(thatClassName))
        	this.value = value;
    	else if (Constants.CLASS_NAME_STRING.equals(thisClassName))
    		this.value = value.toString();
    	else if (Constants.CLASS_NAME_STRING.equals(thatClassName))
		{
			String stringValue = (String) value;
			
			switch (thisClassName)
			{
				case Constants.CLASS_NAME_BOOLEAN:
					this.value = Boolean.valueOf(stringValue);
					break;
				case Constants.CLASS_NAME_INTEGER:
					this.value = Integer.parseInt(stringValue);
					break;
				case Constants.CLASS_NAME_DOUBLE:
					this.value = Double.parseDouble(stringValue);
					break;
				case Constants.CLASS_NAME_FILE:
					this.value = new File(stringValue);
					break;
				case Constants.CLASS_NAME_PASSWORD:
					this.value = new Password(stringValue);
				default:
					throw new IllegalArgumentException(String.format(Constants.EXCPT_PARAM_VALUE_WRONG, stringValue));
			}
		}
    	else
    		throw new IllegalArgumentException(String.format(Constants.EXCPT_PARAM_VALUE_WRONG, value.toString()));
    }

    public Class<?> getType()
    {
        return type;
    }

    public boolean isVisible()
	{
		return visible;
	}
}
