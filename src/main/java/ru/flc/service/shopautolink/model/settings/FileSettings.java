package ru.flc.service.shopautolink.model.settings;

import java.io.File;

public class FileSettings implements Settings
{
	private File file;
	private int packSize;

	@Override
	public void load() throws Exception
	{
		SettingsManager.loadSettings();
		
		packSize = SettingsManager.getIntValue(SettingsManager.PARAM_NAME_PACK_SIZE);
	}

	@Override
	public void save() throws Exception
	{
		SettingsManager.setIntValue(SettingsManager.PARAM_NAME_PACK_SIZE, packSize);

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
		return packSize;
	}
}
