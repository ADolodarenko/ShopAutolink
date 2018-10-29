package ru.flc.service.shopautolink.model.settings;

public class DatabaseSettings implements Settings
{
    private String driverName = "com.sybase.jdbc3.jdbc.SybDriver";
    private String connectionPrefix = "jdbc:sybase:Tds";
    private String host = "dbsrv";
    private int port = 5000;
    private String catalog = "O";
    private String userName = "dolodarenko";
    private char[] password;
    private String tableName = "dav_tmp_table";  //dav_shop_prod
    private String storedProcedureName = "dav_tmp_proc";  //usp_shop_prod_buff_add
    private int channelId = 729;
    private int priceId = 28;

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

    public char[] getPassword()
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
