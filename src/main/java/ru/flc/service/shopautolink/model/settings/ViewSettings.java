package ru.flc.service.shopautolink.model.settings;

import org.dav.service.util.ResourceManager;
import ru.flc.service.shopautolink.SAResourceManager;

import java.awt.*;
import java.util.Locale;

public class ViewSettings implements Settings
{
    private Locale locale;
    private boolean maximized;
    private Point leftTopCorner;
    private Dimension measurements;

    @Override
    public void load() throws Exception
    {
        SettingsManager.loadSettings();

        String localeString = SettingsManager.getStringParameter(SettingsManager.PARAM_NAME_APP_LOCALE);
        if ("RU".equalsIgnoreCase(localeString))
            locale = SAResourceManager.RUS_LOCALE;
        else
            locale = SAResourceManager.ENG_LOCALE;





    }

    @Override
    public void save() throws Exception
    {

    }

    public Locale getLocale()
    {
        return locale;
    }

    public boolean isMaximized()
    {
        return maximized;
    }

    public Point getLeftTopCorner()
    {
        return leftTopCorner;
    }

    public Dimension getMeasurements()
    {
        return measurements;
    }
}
