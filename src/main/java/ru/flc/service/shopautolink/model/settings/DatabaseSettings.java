package ru.flc.service.shopautolink.model.settings;

import org.dav.service.view.Title;

public class DatabaseSettings implements Settings, ValueGetter
{
    private String driverName = "com.sybase.jdbc3.jdbc.SybDriver";
    private String connectionPrefix = "jdbc:sybase:Tds";
    private String host = "dbsrv";
    private int port = 5000;
    private String catalog = "FLC_RU";
    private String userName = "dolodarenko";
    private String password = "killallhumas";
    private String tableName = "O..dav_tmp_table";  //dav_shop_prod
    private String storedProcedureName = "dav_tmp_proc";  //usp_shop_prod_buff_add
    private int channelId = 729;
    private int priceId = 28;

    @Override
    public void load() throws Exception
    {
        SettingsManager.loadSettings();

        driverName = SettingsManager.getStringValue(SettingsManager.PARAM_NAME_DB_DRIVER);
        connectionPrefix = SettingsManager.getStringValue(SettingsManager.PARAM_NAME_DB_CONN_PREF);
        host = SettingsManager.getStringValue(SettingsManager.PARAM_NAME_DB_HOST);
        port = SettingsManager.getIntValue(SettingsManager.PARAM_NAME_DB_PORT);
        catalog = SettingsManager.getStringValue(SettingsManager.PARAM_NAME_DB_CATALOG);
        userName = SettingsManager.getStringValue(SettingsManager.PARAM_NAME_DB_USER);
        password = SettingsManager.getStringValue(SettingsManager.PARAM_NAME_DB_PASSWORD);
        tableName = SettingsManager.getStringValue(SettingsManager.PARAM_NAME_DB_TABLE);
        storedProcedureName = SettingsManager.getStringValue(SettingsManager.PARAM_NAME_DB_SP);
        channelId = SettingsManager.getIntValue(SettingsManager.PARAM_NAME_CHANNEL);
        priceId = SettingsManager.getIntValue(SettingsManager.PARAM_NAME_PRICE_LIST);
    }

    @Override
    public void save() throws Exception
    {
        SettingsManager.setStringValue(SettingsManager.PARAM_NAME_DB_DRIVER, driverName);
        SettingsManager.setStringValue(SettingsManager.PARAM_NAME_DB_CONN_PREF, connectionPrefix);
        SettingsManager.setStringValue(SettingsManager.PARAM_NAME_DB_HOST, host);
        SettingsManager.setIntValue(SettingsManager.PARAM_NAME_DB_PORT, port);
        SettingsManager.setStringValue(SettingsManager.PARAM_NAME_DB_CATALOG, catalog);
        SettingsManager.setStringValue(SettingsManager.PARAM_NAME_DB_USER, userName);
        SettingsManager.setStringValue(SettingsManager.PARAM_NAME_DB_PASSWORD, password);
        SettingsManager.setStringValue(SettingsManager.PARAM_NAME_DB_TABLE, tableName);
        SettingsManager.setStringValue(SettingsManager.PARAM_NAME_DB_SP, storedProcedureName);
        SettingsManager.setIntValue(SettingsManager.PARAM_NAME_CHANNEL, channelId);
        SettingsManager.setIntValue(SettingsManager.PARAM_NAME_PRICE_LIST, priceId);

        SettingsManager.saveSettings();
    }

    public String getDriverName()
    {
        return driverName;
    }
    
    public String getConnectionPrefix()
    {
        return connectionPrefix;
    }
    
    public String getHost()
    {
        return host;
    }
    
    public int getPort()
    {
        return port;
    }
    
    public String getCatalog()
    {
        return catalog;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }
    
    public String getTableName()
    {
        return tableName;
    }
    
    public String getStoredProcedureName()
    {
        return storedProcedureName;
    }
    
    public int getChannelId()
    {
        return channelId;
    }
    
    public int getPriceId()
    {
        return priceId;
    }

    @Override
    public boolean getBooleanValue(Title key)
    {
        return false;
    }

    @Override
    public int getIntValue(Title key)
    {
        return 0;
    }

    @Override
    public double getDoubleValue(Title key)
    {
        return 0;
    }

    @Override
    public String getStringValue(Title key)
    {
        return null;
    }

    @Override
    public Object getObjectValue(Title key)
    {
        return null;
    }
}
