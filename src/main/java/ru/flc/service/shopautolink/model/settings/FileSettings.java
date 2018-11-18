package ru.flc.service.shopautolink.model.settings;

import org.dav.service.util.ResourceManager;
import ru.flc.service.shopautolink.model.settings.parameter.ParameterHeader;
import ru.flc.service.shopautolink.view.Constants;

import java.io.File;

public class FileSettings extends TransmissiveSettings
{
	private static final int PARAM_COUNT = 1;
	
	private File file;
	private boolean fileWritable;
	
	public FileSettings(ResourceManager resourceManager) throws Exception
	{
		super(resourceManager);
		
		headers = new ParameterHeader[PARAM_COUNT];
		headers[0] = new ParameterHeader(Constants.KEY_PARAM_PACK_SIZE, Integer.class);
		
		init();
	}
	
	@Override
	public void save() throws Exception
	{
		SettingsManager.setIntValue(headers[0].getKeyString(), getPackSize());
		
		SettingsManager.saveSettings();
	}
	
	public File getFile()
	{
		return file;
	}
	
	public boolean isFileWritable()
	{
		return fileWritable;
	}
	
	public void setFileWritable(boolean fileWritable)
	{
		this.fileWritable = fileWritable;
	}
	
	public void setFile(File file)
	{
		this.file = file;
	}
	
	public int getPackSize()
	{
		return ((Integer) paramMap.get(headers[0].getKeyString()).getValue());
	}
}
