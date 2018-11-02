package ru.flc.service.shopautolink.model.settings;

import ru.flc.service.shopautolink.SAResourceManager;

import java.awt.*;
import java.util.Locale;

public class ViewSettings implements Settings
{
    private Locale appLocale;
    private boolean mainWindowMaximized;
    private Point mainWindowPosition;
    private Dimension mainWindowSize;
    
    private Dimension mainWindowPreferredSize;
    
    public ViewSettings(Dimension mainWindowPreferredSize)
	{
		this.mainWindowPreferredSize = mainWindowPreferredSize;
	}

    @Override
    public void load() throws Exception
    {
        SettingsManager.loadSettings();
	
		loadLocale();
		loadMainWindowMaximized();
		loadMainWindowPosition();
		loadMainWindowSize();
	}
	
	@Override
    public void save() throws Exception
    {
    	SettingsManager.setStringValue(SettingsManager.PARAM_NAME_APP_LANGUAGE, appLocale.getLanguage());

    	SettingsManager.setStringValue(SettingsManager.PARAM_NAME_MAIN_WIN_MAXIMIZED, String.valueOf(mainWindowMaximized));

    	SettingsManager.setIntValue(SettingsManager.PARAM_NAME_MAIN_WIN_X, mainWindowPosition.x);
    	SettingsManager.setIntValue(SettingsManager.PARAM_NAME_MAIN_WIN_Y, mainWindowPosition.y);

    	SettingsManager.setIntValue(SettingsManager.PARAM_NAME_MAIN_WIN_WIDTH, mainWindowSize.width);
    	SettingsManager.setIntValue(SettingsManager.PARAM_NAME_MAIN_WIN_HEIGHT, mainWindowSize.height);

    	SettingsManager.saveSettings();
    }
		
	private void loadLocale()
	{
		String localeString = SettingsManager.getStringValue(SettingsManager.PARAM_NAME_APP_LANGUAGE);
		
		if ("RU".equalsIgnoreCase(localeString))
			appLocale = SAResourceManager.RUS_LOCALE;
		else
			appLocale = SAResourceManager.ENG_LOCALE;
	}
	
	private void loadMainWindowMaximized()
	{
		String maximizedString = SettingsManager.getStringValue(SettingsManager.PARAM_NAME_MAIN_WIN_MAXIMIZED);
		
		if ("TRUE".equalsIgnoreCase(maximizedString))
			mainWindowMaximized = true;
		else
			mainWindowMaximized = false;
	}
	
	private void loadMainWindowPosition()
	{
		int x = 0;
		if (SettingsManager.hasValue(SettingsManager.PARAM_NAME_MAIN_WIN_X))
			x = SettingsManager.getIntValue(SettingsManager.PARAM_NAME_MAIN_WIN_X);
		
		int y = 0;
		if (SettingsManager.hasValue(SettingsManager.PARAM_NAME_MAIN_WIN_Y))
			y = SettingsManager.getIntValue(SettingsManager.PARAM_NAME_MAIN_WIN_Y);
		
		mainWindowPosition = new Point(x, y);
	}
	
	private void loadMainWindowSize()
	{
		int width = 0;
		if (SettingsManager.hasValue(SettingsManager.PARAM_NAME_MAIN_WIN_WIDTH))
			width = SettingsManager.getIntValue(SettingsManager.PARAM_NAME_MAIN_WIN_WIDTH);
		
		int height = 0;
		if (SettingsManager.hasValue(SettingsManager.PARAM_NAME_MAIN_WIN_HEIGHT))
			height = SettingsManager.getIntValue(SettingsManager.PARAM_NAME_MAIN_WIN_HEIGHT);
		
		if (width > 0 && height > 0)
			mainWindowSize = new Dimension(width, height);
		else
			mainWindowSize = mainWindowPreferredSize;
	}

    public Locale getAppLocale()
    {
        return appLocale;
    }
    
    public boolean isMainWindowMaximized()
    {
        return mainWindowMaximized;
    }

    public Point getMainWindowPosition()
    {
        return mainWindowPosition;
    }

    public Dimension getMainWindowSize()
    {
        return mainWindowSize;
    }

	public void setAppLocale(Locale appLocale)
	{
		this.appLocale = appLocale;
	}

	public void setMainWindowMaximized(boolean mainWindowMaximized)
	{
		this.mainWindowMaximized = mainWindowMaximized;
	}

	public void setMainWindowPosition(Point mainWindowPosition)
	{
		this.mainWindowPosition = mainWindowPosition;
	}

	public void setMainWindowSize(Dimension mainWindowSize)
	{
		this.mainWindowSize = mainWindowSize;
	}
}
