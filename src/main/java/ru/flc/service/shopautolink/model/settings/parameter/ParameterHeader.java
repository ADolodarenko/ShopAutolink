package ru.flc.service.shopautolink.model.settings.parameter;

public class ParameterHeader
{
	private String keyString;
	private Class<?> type;
	private Object initialValue;
	
	public ParameterHeader(String keyString, Class<?> type, Object initialValue)
	{
		this.keyString = keyString;
		this.type = type;
		this.initialValue = initialValue;
	}
	
	public String getKeyString()
	{
		return keyString;
	}
	
	public Class<?> getType()
	{
		return type;
	}

	public Object getInitialValue()
	{
		return initialValue;
	}
}
