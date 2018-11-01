package ru.flc.service.shopautolink.model.settings;

import org.dav.service.util.ResourceManager;
import ru.flc.service.shopautolink.SAResourceManager;

import java.io.*;
import java.util.Properties;

public class SettingsManager
{
    public static final String PARAM_NAME_APP_LANGUAGE = "Language";
    public static final String PARAM_NAME_MAIN_WIN_X = "MainWindowX";
    public static final String PARAM_NAME_MAIN_WIN_Y = "MainWindowY";
    public static final String PARAM_NAME_MAIN_WIN_MAXIMIZED = "MainWindowMaximized";
    public static final String PARAM_NAME_MAIN_WIN_WIDTH = "MainWindowWidth";
    public static final String PARAM_NAME_MAIN_WIN_HEIGHT = "MainWindowHeight";
    public static final String PARAM_NAME_DB_DRIVER = "DatabaseDriver";
    public static final String PARAM_NAME_DB_CONN_PREF = "DatabaseConnectionPref";
    public static final String PARAM_NAME_DB_HOST = "DatabaseHost";
    public static final String PARAM_NAME_DB_PORT = "DatabasePort";
    public static final String PARAM_NAME_DB_CATALOG = "DatabaseCatalog";
    public static final String PARAM_NAME_DB_USER = "DatabaseUser";
    public static final String PARAM_NAME_DB_PASSWORD = "DatabasePassword";
    public static final String PARAM_NAME_DB_TABLE = "DatabaseTable";
    public static final String PARAM_NAME_DB_SP = "DatabaseStoredProcedure";
    public static final String PARAM_NAME_CHANNEL = "ChannelId";
    public static final String PARAM_NAME_PRICE_LIST = "PriceListId";
    public static final String PARAM_NAME_PACK_SIZE = "PacketSize";

    private static ResourceManager resourceManager = SAResourceManager.getInstance();
    private static Properties properties = new Properties();

    public static boolean hasParameter(String key)
    {
        return properties.containsKey(key);
    }
    
    public static String getStringParameter(String key)
    {
        return properties.getProperty(key);
    }

    public static void setStringParameter(String key, String value)
    {
        properties.setProperty(key, value);
    }

    public static int getIntParameter(String key) throws NumberFormatException
    {
        return Integer.parseInt(getStringParameter(key));
    }

    public static void setIntParameter(String key, int value)
    {
        setStringParameter(key, String.valueOf(value));
    }

    public static void loadSettings() throws IOException
    {
        try (InputStream input = new FileInputStream(resourceManager.getConfig()))
        {
            properties.load(input);
        }
    }

    public static void saveSettings() throws IOException
    {
        try (OutputStream output = new FileOutputStream(resourceManager.getConfig()))
        {
            properties.store(output, "Shop autolinking properties");
        }
    }
}
