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

		file = new File(SettingsManager.getStringParameter(SettingsManager.PARAM_NAME_SOURCE_FILE));
		packSize = SettingsManager.getIntParameter(SettingsManager.PARAM_NAME_PACK_SIZE);
	}

	@Override
	public void save() throws Exception
	{
		SettingsManager.setStringParameter(SettingsManager.PARAM_NAME_SOURCE_FILE, file.getAbsolutePath());
		SettingsManager.setIntParameter(SettingsManager.PARAM_NAME_PACK_SIZE, packSize);

		SettingsManager.saveSettings();
	}

	public File getFile()
	{
		return file;
	}

	public int getPackSize()
	{
		return packSize;
	}

	public void setFile(File file)
	{
		this.file = file;
	}

	public void setPackSize(int packSize)
	{
		this.packSize = packSize;
	}
}
