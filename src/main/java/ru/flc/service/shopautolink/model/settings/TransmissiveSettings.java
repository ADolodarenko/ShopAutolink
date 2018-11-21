package ru.flc.service.shopautolink.model.settings;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;
import ru.flc.service.shopautolink.model.settings.parameter.Parameter;
import ru.flc.service.shopautolink.model.settings.parameter.ParameterHeader;
import ru.flc.service.shopautolink.view.Constants;

import java.io.File;
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
		
		if (Constants.CLASS_NAME_BOOLEAN.equals(className))
			value = Boolean.FALSE;
		else if (Constants.CLASS_NAME_INTEGER.equals(className))
			value = Integer.valueOf(0);
		else if (Constants.CLASS_NAME_DOUBLE.equals(className))
			value = Double.valueOf(0.0);
		else if (Constants.CLASS_NAME_LOCALE.equals(className))
			value = resourceManager.getCurrentLocale();
		else if (Constants.CLASS_NAME_FILE.equals(className))
			value = new File(Constants.MESS_SP_LOG_FILE_DEFAULT_PATTERN);
		else if (Constants.CLASS_NAME_STRING.equals(className))
			value = "";
		
		if (value == null)
			throw new Exception(Constants.EXCPT_VALUE_TYPE_WRONG);
		
		return new Parameter(key, value, cl);
	}
	
	private Parameter getParameter(ParameterHeader header) throws Exception
	{
		String keyString = header.getKeyString();
		Class<?> cl = header.getType();
		
		Title key = new Title(resourceManager, keyString);
		Object value = null;
		
		String className = cl.getSimpleName();
		
		if (Constants.CLASS_NAME_BOOLEAN.equals(className))
			value = SettingsManager.getBooleanValue(keyString);
		else if (Constants.CLASS_NAME_INTEGER.equals(className))
			value = SettingsManager.getIntValue(keyString);
		else if (Constants.CLASS_NAME_DOUBLE.equals(className))
			value = SettingsManager.getDoubleValue(keyString);
		else if (Constants.CLASS_NAME_STRING.equals(className))
		{
			value = SettingsManager.getStringValue(keyString);
			
			if (value == null)
				value = "";
		}
		else if (Constants.CLASS_NAME_LOCALE.equals(className))
		{
			String localeName = SettingsManager.getStringValue(keyString);
			
			value = findLocale(localeName);
			
			if (value == null)
				value = resourceManager.getCurrentLocale();
		}
		else if (Constants.CLASS_NAME_FILE.equals(className))
		{
			String fileName = SettingsManager.getStringValue(keyString);

			if (fileName == null)
				fileName = Constants.MESS_SP_LOG_FILE_DEFAULT_PATTERN;

			value = new File(fileName);
		}
		
		if (value == null)
			throw new Exception(Constants.EXCPT_VALUE_TYPE_WRONG);
		
		return new Parameter(key, value, cl);
	}
	
	private Locale findLocale(String localeName)
	{
		if (localeName == null || localeName.isEmpty())
			return null;
		
		for (Locale locale : resourceManager.getAvailableLocales())
			if (locale.toString().equals(localeName))
				return locale;
		
		
		return null;
	}
	
	public List<Parameter> getParameterList()
	{
		return new ArrayList<>(paramMap.values());
	}
}
