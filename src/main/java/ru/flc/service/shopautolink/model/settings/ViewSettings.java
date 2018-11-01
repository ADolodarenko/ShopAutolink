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
    	SettingsManager.setStringParameter(SettingsManager.PARAM_NAME_APP_LANGUAGE, appLocale.getLanguage());

    	SettingsManager.setStringParameter(SettingsManager.PARAM_NAME_MAIN_WIN_MAXIMIZED, String.valueOf(mainWindowMaximized));

    	SettingsManager.setIntParameter(SettingsManager.PARAM_NAME_MAIN_WIN_X, mainWindowPosition.x);
    	SettingsManager.setIntParameter(SettingsManager.PARAM_NAME_MAIN_WIN_Y, mainWindowPosition.y);

    	SettingsManager.setIntParameter(SettingsManager.PARAM_NAME_MAIN_WIN_WIDTH, mainWindowSize.width);
    	SettingsManager.setIntParameter(SettingsManager.PARAM_NAME_MAIN_WIN_HEIGHT, mainWindowSize.height);

    	SettingsManager.saveSettings();
    }
		
	private void loadLocale()
	{
		String localeString = SettingsManager.getStringParameter(SettingsManager.PARAM_NAME_APP_LANGUAGE);
		
		if ("RU".equalsIgnoreCase(localeString))
			appLocale = SAResourceManager.RUS_LOCALE;
		else
			appLocale = SAResourceManager.ENG_LOCALE;
	}
	
	private void loadMainWindowMaximized()
	{
		String maximizedString = SettingsManager.getStringParameter(SettingsManager.PARAM_NAME_MAIN_WIN_MAXIMIZED);
		
		if ("TRUE".equalsIgnoreCase(maximizedString))
			mainWindowMaximized = true;
		else
			mainWindowMaximized = false;
	}
	
	private void loadMainWindowPosition()
	{
		int x = 0;
		if (SettingsManager.hasParameter(SettingsManager.PARAM_NAME_MAIN_WIN_X))
			x = SettingsManager.getIntParameter(SettingsManager.PARAM_NAME_MAIN_WIN_X);
		
		int y = 0;
		if (SettingsManager.hasParameter(SettingsManager.PARAM_NAME_MAIN_WIN_Y))
			y = SettingsManager.getIntParameter(SettingsManager.PARAM_NAME_MAIN_WIN_Y);
		
		mainWindowPosition = new Point(x, y);
	}
	
	private void loadMainWindowSize()
	{
		int width = 0;
		if (SettingsManager.hasParameter(SettingsManager.PARAM_NAME_MAIN_WIN_WIDTH))
			width = SettingsManager.getIntParameter(SettingsManager.PARAM_NAME_MAIN_WIN_WIDTH);
		
		int height = 0;
		if (SettingsManager.hasParameter(SettingsManager.PARAM_NAME_MAIN_WIN_HEIGHT))
			height = SettingsManager.getIntParameter(SettingsManager.PARAM_NAME_MAIN_WIN_HEIGHT);
		
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
