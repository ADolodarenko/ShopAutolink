package ru.flc.service.shopautolink.model.settings;

public class DatabaseSettings implements Settings
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

        driverName = SettingsManager.getStringParameter(SettingsManager.PARAM_NAME_DB_DRIVER);
        connectionPrefix = SettingsManager.getStringParameter(SettingsManager.PARAM_NAME_DB_CONN_PREF);
        host = SettingsManager.getStringParameter(SettingsManager.PARAM_NAME_DB_HOST);
        port = SettingsManager.getIntParameter(SettingsManager.PARAM_NAME_DB_PORT);
        catalog = SettingsManager.getStringParameter(SettingsManager.PARAM_NAME_DB_CATALOG);
        userName = SettingsManager.getStringParameter(SettingsManager.PARAM_NAME_DB_USER);
        password = SettingsManager.getStringParameter(SettingsManager.PARAM_NAME_DB_PASSWORD);
        tableName = SettingsManager.getStringParameter(SettingsManager.PARAM_NAME_DB_TABLE);
        storedProcedureName = SettingsManager.getStringParameter(SettingsManager.PARAM_NAME_DB_SP);
        channelId = SettingsManager.getIntParameter(SettingsManager.PARAM_NAME_CHANNEL);
        priceId = SettingsManager.getIntParameter(SettingsManager.PARAM_NAME_PRICE_LIST);
    }

    @Override
    public void save() throws Exception
    {
        SettingsManager.setStringParameter(SettingsManager.PARAM_NAME_DB_DRIVER, driverName);
        SettingsManager.setStringParameter(SettingsManager.PARAM_NAME_DB_CONN_PREF, connectionPrefix);
        SettingsManager.setStringParameter(SettingsManager.PARAM_NAME_DB_HOST, host);
        SettingsManager.setIntParameter(SettingsManager.PARAM_NAME_DB_PORT, port);
        SettingsManager.setStringParameter(SettingsManager.PARAM_NAME_DB_CATALOG, catalog);
        SettingsManager.setStringParameter(SettingsManager.PARAM_NAME_DB_USER, userName);
        SettingsManager.setStringParameter(SettingsManager.PARAM_NAME_DB_PASSWORD, password);
        SettingsManager.setStringParameter(SettingsManager.PARAM_NAME_DB_TABLE, tableName);
        SettingsManager.setStringParameter(SettingsManager.PARAM_NAME_DB_SP, storedProcedureName);
        SettingsManager.setIntParameter(SettingsManager.PARAM_NAME_CHANNEL, channelId);
        SettingsManager.setIntParameter(SettingsManager.PARAM_NAME_PRICE_LIST, priceId);

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
}
