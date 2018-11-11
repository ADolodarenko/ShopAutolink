package ru.flc.service.shopautolink.model.settings;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;
import ru.flc.service.shopautolink.SAResourceManager;
import ru.flc.service.shopautolink.model.settings.parameter.Parameter;
import ru.flc.service.shopautolink.model.settings.parameter.ParameterHeader;
import ru.flc.service.shopautolink.view.Constants;

import java.util.*;

public abstract class TransmissiveSettings implements Settings
{
	private ResourceManager resourceManager;
	protected Map<String, Parameter> paramMap;
	protected ParameterHeader[] headers;
	
	protected TransmissiveSettings(ResourceManager resourceManager)
	{
		if (resourceManager == null)
			throw new IllegalArgumentException(Constants.EXCPT_RESOURCE_MANAGER_EMPTY);
		
		this.resourceManager = resourceManager;
		paramMap = new HashMap<>();
	}
	
	@Override
	public void init() throws Exception
	{
		setParameters(false);
	}
	
	@Override
	public void load() throws Exception
	{
		setParameters(true);
	}
	
	private void setParameters(boolean load) throws Exception
	{
		if (load)
			SettingsManager.loadSettings();
		
		for (int i = 0; i < headers.length; i++)
			setParameter(load, headers[i]);
	}
	
	protected void setParameter(boolean load, ParameterHeader header) throws Exception
	{
		if (load)
			paramMap.put(header.getKeyString(), getParameter(header));
		else
			paramMap.put(header.getKeyString(), initParameter(header));
	}
	
	private Parameter initParameter(ParameterHeader header) throws Exception
	{
		String keyString = header.getKeyString();
		Class<?> cl = header.getType();
		
		Title key = new Title(resourceManager, keyString);
		Object value = null;
		
		String className = cl.getSimpleName();
		
		if ("Boolean".equals(className))
			value = Boolean.FALSE;
		else if ("Integer".equals(className))
			value = Integer.valueOf(0);
		else if ("Double".equals(className))
			value = Double.valueOf(0.0);
		else if ("Locale".equals(className))
			value = resourceManager.getCurrentLocale();
		else if ("String".equals(className))
			value = "";
		
		if (value == null)
			throw new Exception(Constants.EXCPT_VALUE_TYPE_WRONG);
		
		return new Parameter(resourceManager, key, value, cl);
	}
	
	private Parameter getParameter(ParameterHeader header) throws Exception
	{
		String keyString = header.getKeyString();
		Class<?> cl = header.getType();
		
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
		{
			value = SettingsManager.getStringValue(keyString);
			
			if (value == null)
				value = "";
		}
		else if ("Locale".equals(className))
		{
			String localeName = SettingsManager.getStringValue(keyString);
			
			value = findLocale(localeName);
			
			if (value == null)
				value = resourceManager.getCurrentLocale();
		}
		
		if (value == null)
			throw new Exception(Constants.EXCPT_VALUE_TYPE_WRONG);
		
		return new Parameter(resourceManager, key, value, cl);
	}
	
	private Locale findLocale(String localeName)
	{
		if (localeName == null || localeName.isEmpty())
			return null;
		
		String rmClassName = resourceManager.getClass().getSimpleName();
		
		if ("SAResourceManager".equals(rmClassName))
		{
			for (Locale locale : ((SAResourceManager) resourceManager).getAvailableLocales())
				if (locale.toString().equals(localeName))
					return locale;
		}
		
		return null;
	}
	
	public List<Parameter> getParameterList()
	{
		return new ArrayList<>(paramMap.values());
	}
}
