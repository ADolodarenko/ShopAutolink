package ru.flc.service.shopautolink.model.settings.parameter;

public class ParameterHeader
{
	private String keyString;
	private Class<?> type;
	
	public ParameterHeader(String keyString, Class<?> type)
	{
		this.keyString = keyString;
		this.type = type;
	}
	
	public String getKeyString()
	{
		return keyString;
	}
	
	public Class<?> getType()
	{
		return type;
	}
}
