package ru.flc.service.shopautolink.model.settings;

import java.io.File;

public class FileSettings implements Settings
{
	private File file;

	//Убрать эту треногу
	public FileSettings(File file)
	{
		this.file = file;
	}

	public File getFile()
	{
		return file;
	}

	@Override
	public void load()
	{


	}

	@Override
	public void save()
	{

	}
}
