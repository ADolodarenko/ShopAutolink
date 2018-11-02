package ru.flc.service.shopautolink.model.settings;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;

import java.util.HashMap;
import java.util.Map;

public abstract class TransmissiveSettings implements Settings
{
	private static final String RESOURCE_MANAGER_EXCEPTION_STRING = "Resource manager is empty.";
	private static final String VALUE_TYPE_EXCEPTION_STRING = "Wrong value type.";
	
	private ResourceManager resourceManager;
	protected Map<String, ParameterAlt> paramMap;
	
	protected TransmissiveSettings(ResourceManager resourceManager)
	{
		if (resourceManager == null)
			throw new IllegalArgumentException(RESOURCE_MANAGER_EXCEPTION_STRING);
		
		this.resourceManager = resourceManager;
		paramMap = new HashMap<>();
	}
	
	protected void loadParameter(String keyString, Class<?> cl) throws Exception
	{
		paramMap.put(keyString, getParameter(keyString, cl));
	}
	
	private ParameterAlt getParameter(String keyString, Class<?> cl) throws Exception
	{
		Title key = new Title(resourceManager, keyString);
		Object value = null;
		
		String className = cl.getSimpleName();
		
		if ("Boolean".equals(className))
			value = SettingsManager.getBooleanValue(keyString);
		else if ("Integer".equals(className))
			value = SettingsManager.getIntValue(keyString);
		else if ("Double".equals(className))
			value = SettingsManager.getDoubleValue(keyString);
		else if ("String".equals(className))
			value = SettingsManager.getStringValue(keyString);
		
		if (value == null)
			throw new Exception(VALUE_TYPE_EXCEPTION_STRING);
		
		return new ParameterAlt(key, value, cl);
	}
}
