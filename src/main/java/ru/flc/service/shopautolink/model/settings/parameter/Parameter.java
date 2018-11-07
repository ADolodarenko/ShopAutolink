package ru.flc.service.shopautolink.model.settings.parameter;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;
import ru.flc.service.shopautolink.view.Constants;

public class Parameter
{
	public static String[] getTitleKeys()
	{
		return new String[] { Constants.KEY_PARAM_TITLE_NAME, Constants.KEY_PARAM_TITLE_VALUE };
	}
	
    private ResourceManager resourceManager;
    private Title key;
    private Object value;
    private Class<?> type;

    public Parameter(ResourceManager resourceManager, Title key, Object value, Class<?> type)
    {
    	if (resourceManager == null)
    		throw new IllegalArgumentException(Constants.EXCPT_RESOURCE_MANAGER_EMPTY);
    	
        if (key == null)
            throw new IllegalArgumentException(Constants.EXCPT_PARAM_KEY_EMPTY);

        if (value == null)
            throw new IllegalArgumentException(Constants.EXCPT_PARAM_VALUE_EMPTY);

        if (type == null)
            throw new IllegalArgumentException(Constants.EXCPT_PARAM_TYPE_EMPTY);

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

    public void setValue(Object value) throws IllegalArgumentException
    {
    	if (value == null)
    		throw new IllegalArgumentException(Constants.EXCPT_PARAM_VALUE_EMPTY);
    	
    	String thisClassName = getType().getSimpleName();
    	String thatClassName = value.getClass().getSimpleName();
    	
    	if (thisClassName.equals(thatClassName))
        	this.value = value;
    	else if ("String".equals(thisClassName))
    		this.value = value.toString();
    	else if ("String".equals(thatClassName))
		{
			String stringValue = (String) value;
			
			switch (thisClassName)
			{
				case "Boolean":
					this.value = Boolean.valueOf(stringValue);
					break;
				case "Integer":
					this.value = Integer.parseInt(stringValue);
					break;
				case "Double":
					this.value = Double.parseDouble(stringValue);
					break;
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
}
