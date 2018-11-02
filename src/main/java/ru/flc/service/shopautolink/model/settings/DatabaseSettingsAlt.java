package ru.flc.service.shopautolink.model.settings;

import org.dav.service.util.ResourceManager;
import org.dav.service.view.Title;

import java.util.HashMap;
import java.util.Map;

public class DatabaseSettingsAlt implements Settings
{
    private static final String RESOURCE_MANAGER_EXCEPTION_STRING = "Resource manager is empty.";
    private static final String VALUE_TYPE_EXCEPTION_STRING = "Wrong value type.";

    private ResourceManager resourceManager;
    private Map<String, ParameterAlt> paramMap;

    public DatabaseSettingsAlt(ResourceManager resourceManager)
    {
        if (resourceManager == null)
            throw new IllegalArgumentException(RESOURCE_MANAGER_EXCEPTION_STRING);

        this.resourceManager = resourceManager;
        paramMap = new HashMap<>();
    }

    @Override
    public void load() throws Exception
    {
        SettingsManager.loadSettings();

        loadParameter(SettingsManager.PARAM_NAME_DB_DRIVER, String.class);
        loadParameter(SettingsManager.PARAM_NAME_DB_CONN_PREF, String.class);
        loadParameter(SettingsManager.PARAM_NAME_DB_HOST, String.class);
        loadParameter(SettingsManager.PARAM_NAME_DB_PORT, Integer.class);
        loadParameter(SettingsManager.PARAM_NAME_DB_CATALOG, String.class);
        loadParameter(SettingsManager.PARAM_NAME_DB_USER, String.class);
        loadParameter(SettingsManager.PARAM_NAME_DB_PASSWORD, String.class);
        loadParameter(SettingsManager.PARAM_NAME_DB_TABLE, String.class);
        loadParameter(SettingsManager.PARAM_NAME_DB_SP, String.class);
        loadParameter(SettingsManager.PARAM_NAME_CHANNEL, Integer.class);
        loadParameter(SettingsManager.PARAM_NAME_PRICE_LIST, Integer.class);
    }

    @Override
    public void save() throws Exception
    {
        SettingsManager.setStringValue(SettingsManager.PARAM_NAME_DB_DRIVER, getDriverName());
        SettingsManager.setStringValue(SettingsManager.PARAM_NAME_DB_CONN_PREF, getConnectionPrefix());
        SettingsManager.setStringValue(SettingsManager.PARAM_NAME_DB_HOST, getHost());
        SettingsManager.setIntValue(SettingsManager.PARAM_NAME_DB_PORT, getPort());
        SettingsManager.setStringValue(SettingsManager.PARAM_NAME_DB_CATALOG, getCatalog());
        SettingsManager.setStringValue(SettingsManager.PARAM_NAME_DB_USER, getUserName());
        SettingsManager.setStringValue(SettingsManager.PARAM_NAME_DB_PASSWORD, getPassword());
        SettingsManager.setStringValue(SettingsManager.PARAM_NAME_DB_TABLE, getTableName());
        SettingsManager.setStringValue(SettingsManager.PARAM_NAME_DB_SP, getStoredProcedureName());
        SettingsManager.setIntValue(SettingsManager.PARAM_NAME_CHANNEL, getChannelId());
        SettingsManager.setIntValue(SettingsManager.PARAM_NAME_PRICE_LIST, getPriceId());

        SettingsManager.saveSettings();
    }

    public String getDriverName()
    {
        return ((String) paramMap.get(SettingsManager.PARAM_NAME_DB_DRIVER).getValue());
    }

    public String getConnectionPrefix()
    {
        return ((String) paramMap.get(SettingsManager.PARAM_NAME_DB_CONN_PREF).getValue());
    }

    public String getHost()
    {
        return ((String) paramMap.get(SettingsManager.PARAM_NAME_DB_HOST).getValue());
    }

    public int getPort()
    {
        return ((Integer) paramMap.get(SettingsManager.PARAM_NAME_DB_PORT).getValue());
    }

    public String getCatalog()
    {
        return ((String) paramMap.get(SettingsManager.PARAM_NAME_DB_CATALOG).getValue());
    }

    public String getUserName()
    {
        return ((String) paramMap.get(SettingsManager.PARAM_NAME_DB_USER).getValue());
    }

    public String getPassword()
    {
        return ((String) paramMap.get(SettingsManager.PARAM_NAME_DB_PASSWORD).getValue());
    }

    public String getTableName()
    {
        return ((String) paramMap.get(SettingsManager.PARAM_NAME_DB_TABLE).getValue());
    }

    public String getStoredProcedureName()
    {
        return ((String) paramMap.get(SettingsManager.PARAM_NAME_DB_SP).getValue());
    }

    public int getChannelId()
    {
        return ((Integer) paramMap.get(SettingsManager.PARAM_NAME_CHANNEL).getValue());
    }

    public int getPriceId()
    {
        return ((Integer) paramMap.get(SettingsManager.PARAM_NAME_PRICE_LIST).getValue());
    }

    private void loadParameter(String keyString, Class<?> cl) throws Exception
    {
        paramMap.put(keyString, getParameter(keyString, cl));
    }

    private ParameterAlt getParameter(String keyString, Class<?> cl) throws Exception
    {
        Title key = new Title(resourceManager, keyString);
        Object value = null;

        String className = cl.getSimpleName();

        if ("Boolean".equals(className))
            value = SettingsManager.getBooleanValue(keyString);
        else if ("Integer".equals(className))
            value = SettingsManager.getIntValue(keyString);
        else if ("Double".equals(className))
            value = SettingsManager.getDoubleValue(keyString);
        else if ("String".equals(className))
            value = SettingsManager.getStringValue(keyString);

        if (value == null)
            throw new Exception(VALUE_TYPE_EXCEPTION_STRING);

        return new ParameterAlt(key, value, cl);
    }
}
