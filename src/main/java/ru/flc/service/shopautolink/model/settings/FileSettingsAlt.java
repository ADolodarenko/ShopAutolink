package ru.flc.service.shopautolink.model.settings;

import org.dav.service.util.ResourceManager;

import java.io.File;

public class FileSettingsAlt extends TransmissiveSettings
{
	private File file;
	
	public FileSettingsAlt(ResourceManager resourceManager)
	{
		super(resourceManager);
	}
	
	@Override
	public void load() throws Exception
	{
		SettingsManager.loadSettings();
		
		loadParameter(SettingsManager.PARAM_NAME_PACK_SIZE, Integer.class);
	}
	
	@Override
	public void save() throws Exception
	{
		SettingsManager.setIntValue(SettingsManager.PARAM_NAME_PACK_SIZE, getPackSize());
		
		SettingsManager.saveSettings();
	}
	
	public File getFile()
	{
		return file;
	}
	
	public void setFile(File file)
	{
		this.file = file;
	}
	
	public int getPackSize()
	{
		return ((Integer) paramMap.get(SettingsManager.PARAM_NAME_PACK_SIZE).getValue());
	}
}
