package ru.flc.service.shopautolink.model.settings;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;
import ru.flc.service.shopautolink.model.settings.parameter.ParameterAlt;
import ru.flc.service.shopautolink.model.settings.parameter.ParameterHeader;
import ru.flc.service.shopautolink.view.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TransmissiveSettings implements Settings
{
	private ResourceManager resourceManager;
	protected Map<String, ParameterAlt> paramMap;
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
	
	private ParameterAlt initParameter(ParameterHeader header) throws Exception
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
		else if ("String".equals(className))
			value = "";
		
		if (value == null)
			throw new Exception(Constants.EXCPT_VALUE_TYPE_WRONG);
		
		return new ParameterAlt(resourceManager, key, value, cl);
	}
	
	private ParameterAlt getParameter(ParameterHeader header) throws Exception
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
		
		if (value == null)
			throw new Exception(Constants.EXCPT_VALUE_TYPE_WRONG);
		
		return new ParameterAlt(resourceManager, key, value, cl);
	}
	
	public List<ParameterAlt> getParameterList()
	{
		return new ArrayList<>(paramMap.values());
	}
}
