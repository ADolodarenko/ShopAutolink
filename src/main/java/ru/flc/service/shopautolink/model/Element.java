package ru.flc.service.shopautolink.model;

public class Element
{
	private Object value;
	private Class<?> type;
	
	public Element(Object value, Class<?> type)
	{
		this.value = value;
		this.type = type;
	}
	
	public Object getValue()
	{
		return value;
	}
	
	public Class<?> getType()
	{
		return type;
	}
}
