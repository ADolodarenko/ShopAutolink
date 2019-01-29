package ru.flc.service.shopautolink.model.settings;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;
import ru.flc.service.shopautolink.model.settings.parameter.Parameter;
import ru.flc.service.shopautolink.model.settings.parameter.ParameterHeader;
import ru.flc.service.shopautolink.model.settings.type.Password;
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
		Title key = new Title(resourceManager, keyString);

		Class<?> cl = header.getType();

		Object value = header.getInitialValue();
		
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
		{
			int defaultValue = ((Integer) header.getInitialValue()).intValue();

			value = SettingsManager.getIntValue(keyString, defaultValue);
		}
		else if (Constants.CLASS_NAME_DOUBLE.equals(className))
		{
			double defaultValue = ((Double) header.getInitialValue()).doubleValue();

			value = SettingsManager.getDoubleValue(keyString, defaultValue);
		}
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
				switch (keyString)
				{
					case Constants.KEY_PARAM_SP_LOG_PATTERN:
						fileName = Constants.MESS_SP_LOG_FILE_DEFAULT_PATTERN;
						break;
					default:
						fileName = Constants.MESS_CURRENT_PATH;
				}

			value = new File(fileName);
		}
		else if (Constants.CLASS_NAME_PASSWORD.equals(className))
		{
			String secret = SettingsManager.getStringValue(keyString);

			value = new Password(secret);
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
