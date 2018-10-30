package ru.flc.service.shopautolink.model.settings;

import java.io.File;

public class FileSettings implements Settings
{
	private File file;
	private int packSize;

	//Убрать эту треногу
	public FileSettings(File file, int packSize)
	{
		this.file = file;
		this.packSize = packSize;
	}

	public File getFile()
	{
		return file;
	}

	public int getPackSize()
	{
		return packSize;
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
