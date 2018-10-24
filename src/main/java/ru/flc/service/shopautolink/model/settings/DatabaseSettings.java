package ru.flc.service.shopautolink.model.settings;

public class DatabaseSettings implements Settings
{
    private String driverName;  //"com.sybase.jdbc3.jdbc.SybDriver"
    private String serverName;
    private String catalogName;
    private String userName;
    private String password;

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

    public String getServerName()
    {
        return serverName;
    }

    public String getCatalogName()
    {
        return catalogName;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }
}
