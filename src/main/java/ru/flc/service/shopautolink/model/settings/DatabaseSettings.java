package ru.flc.service.shopautolink.model.settings;

public class DatabaseSettings implements Settings
{
    private String driverName;  //"com.sybase.jdbc3.jdbc.SybDriver"
    private String connectionPrefix;
    private String host;
    private int port;
    private String catalog;
    private String userName;
    private String password;
    private String tableName;
    private String storedProcedureName;
    private int channelId;
    private int priceId;

    @Override
    public void load()
    {



    }

    @Override
    public void save()
    {

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
