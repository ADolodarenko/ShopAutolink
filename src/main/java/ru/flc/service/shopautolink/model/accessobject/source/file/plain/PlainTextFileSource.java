package ru.flc.service.shopautolink.model.accessobject.source.file.plain;

import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.accessobject.source.file.FileSource;
import ru.flc.service.shopautolink.model.settings.FileSettings;
import ru.flc.service.shopautolink.model.settings.Settings;
import ru.flc.service.shopautolink.view.Constants;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class PlainTextFileSource implements FileSource
{
	private File file;
	
	private BufferedOutputStream output;
	
	@Override
	public TitleLink getNextLink()
	{
		return null;
	}
	
	@Override
	public void putResultLine(String line) throws Exception
	{
		if (output != null)
			output.write(line.getBytes());
	}
	
	@Override
	public void open() throws Exception
	{
		output = new BufferedOutputStream(new FileOutputStream(file));
	}
	
	@Override
	public void close() throws Exception
	{
		if (output != null)
			output.close();
	}
	
	@Override
	public void tune(Settings settings) throws Exception
	{
		close();
		
		if (settings != null)
		{
			String settingsClassName = settings.getClass().getSimpleName();
			
			if ("FileSettings".equals(settingsClassName))
				resetParameters((FileSettings)settings);
			else
				throw new IllegalArgumentException(Constants.EXCPT_FILE_SETTINGS_WRONG);
		}
		else
			throw new IllegalArgumentException(Constants.EXCPT_FILE_SETTINGS_EMPTY);
	
	}
	
	private void resetParameters(FileSettings settings)
	{
		this.file = settings.getFile();
	}
}
