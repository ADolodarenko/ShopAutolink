package ru.flc.service.shopautolink.model.settings;

public class SettingsFactory
{
    public static Settings getSettings(SettingsType type)
    {
        switch (type)
        {
            case FILE_SETTINGS:
                return new FileSettings(null);
            case VIEW_SETTINGS:
                return new ViewSettings();
            case DATABASE_SETTINGS:
                return new DatabaseSettings();
            default:
                return null;
        }
    }

}
