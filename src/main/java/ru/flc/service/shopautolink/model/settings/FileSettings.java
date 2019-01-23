package ru.flc.service.shopautolink.model.settings;

import org.dav.service.util.ResourceManager;
import ru.flc.service.shopautolink.model.settings.parameter.ParameterHeader;
import ru.flc.service.shopautolink.view.Constants;

import java.io.File;

public class FileSettings extends TransmissiveSettings
{
	private static final int PARAM_COUNT = 6;
	
	private File file;
	private boolean fileWritable;
	
	public FileSettings(ResourceManager resourceManager) throws Exception
	{
		super(resourceManager);
		
		headers = new ParameterHeader[PARAM_COUNT];
		headers[0] = new ParameterHeader(Constants.KEY_PARAM_PACK_SIZE, Integer.class);
		headers[1] = new ParameterHeader(Constants.KEY_PARAM_SP_LOG_PATTERN, File.class);
		headers[2] = new ParameterHeader(Constants.KEY_PARAM_FIELD_DELIMITER, String.class);
		headers[3] = new ParameterHeader(Constants.KEY_PARAM_SOURCE_FILE_PATH, File.class);
		headers[4] = new ParameterHeader(Constants.KEY_PARAM_SOURCE_FILE_FIRST_ROW, Integer.class);
		headers[5] = new ParameterHeader(Constants.KEY_PARAM_SOURCE_FILE_FIRST_COLUMN, Integer.class);

		init();
	}
	
	@Override
	public void save() throws Exception
	{
		SettingsManager.setIntValue(headers[0].getKeyString(), getPackSize());
		SettingsManager.setStringValue(headers[1].getKeyString(), getStoredProcedureLogNamePattern().getAbsolutePath());
		SettingsManager.setStringValue(headers[2].getKeyString(), getFieldDelimiter());
		SettingsManager.setStringValue(headers[3].getKeyString(), getSourceFilePath().getAbsolutePath());
		SettingsManager.setIntValue(headers[4].getKeyString(), getSourceFileFirstRow());
		SettingsManager.setIntValue(headers[5].getKeyString(), getSourceFileFirstColumn());
		
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

	public File getStoredProcedureLogNamePattern()
	{
		return (File) paramMap.get(headers[1].getKeyString()).getValue();
	}

	public String getFieldDelimiter()
	{
		return (String) paramMap.get(headers[2].getKeyString()).getValue();
	}

	public File getSourceFilePath()
	{
		return (File) paramMap.get(headers[3].getKeyString()).getValue();
	}

	public void setSourceFilePath(File filePath)
	{
		paramMap.get(headers[3].getKeyString()).setValue(filePath);
	}

	public int getSourceFileFirstRow()
	{
		return ((Integer) paramMap.get(headers[4].getKeyString()).getValue());
	}

	public int getSourceFileFirstColumn()
	{
		return ((Integer) paramMap.get(headers[5].getKeyString()).getValue());
	}
}
