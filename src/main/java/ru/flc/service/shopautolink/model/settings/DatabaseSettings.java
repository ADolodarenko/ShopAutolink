package ru.flc.service.shopautolink.model.settings;

import org.dav.service.util.ResourceManager;
import ru.flc.service.shopautolink.model.settings.parameter.ParameterHeader;
import ru.flc.service.shopautolink.model.settings.type.Password;
import ru.flc.service.shopautolink.view.Constants;

public class DatabaseSettings extends TransmissiveSettings
{
	private static final int PARAM_COUNT = 11;
	
    public DatabaseSettings(ResourceManager resourceManager) throws Exception
	{
		super(resourceManager);
		
		headers = new ParameterHeader[PARAM_COUNT];
		
		headers[0] = new ParameterHeader(Constants.KEY_PARAM_DB_DRIVER, String.class, "");
		headers[1] = new ParameterHeader(Constants.KEY_PARAM_DB_CONN_PREF, String.class, "");
		headers[2] = new ParameterHeader(Constants.KEY_PARAM_DB_HOST, String.class, "");
		headers[3] = new ParameterHeader(Constants.KEY_PARAM_DB_PORT, Integer.class, Integer.valueOf(1));
		headers[4] = new ParameterHeader(Constants.KEY_PARAM_DB_CATALOG, String.class, "");
		headers[5] = new ParameterHeader(Constants.KEY_PARAM_DB_USER, String.class, "");
		headers[6] = new ParameterHeader(Constants.KEY_PARAM_DB_PASSWORD, Password.class, new Password(""));
		headers[7] = new ParameterHeader(Constants.KEY_PARAM_DB_TABLE, String.class, "");
		headers[8] = new ParameterHeader(Constants.KEY_PARAM_DB_SP, String.class, "");
		headers[9] = new ParameterHeader(Constants.KEY_PARAM_CHANNEL, Integer.class, Integer.valueOf(1));
		headers[10] = new ParameterHeader(Constants.KEY_PARAM_PRICE_LIST, Integer.class, Integer.valueOf(1));
		
		init();
	}

    @Override
    public void save() throws Exception
    {
        SettingsManager.setStringValue(headers[0].getKeyString(), getDriverName());
        SettingsManager.setStringValue(headers[1].getKeyString(), getConnectionPrefix());
        SettingsManager.setStringValue(headers[2].getKeyString(), getHost());
        SettingsManager.setIntValue(headers[3].getKeyString(), getPort());
        SettingsManager.setStringValue(headers[4].getKeyString(), getCatalog());
        SettingsManager.setStringValue(headers[5].getKeyString(), getUserName());
        SettingsManager.setStringValue(headers[6].getKeyString(), getPassword().getSecret());
        SettingsManager.setStringValue(headers[7].getKeyString(), getTableName());
        SettingsManager.setStringValue(headers[8].getKeyString(), getStoredProcedureName());
        SettingsManager.setIntValue(headers[9].getKeyString(), getChannelId());
        SettingsManager.setIntValue(headers[10].getKeyString(), getPriceId());

        SettingsManager.saveSettings();
    }

    public String getDriverName()
    {
        return ((String) paramMap.get(headers[0].getKeyString()).getValue());
    }

    public String getConnectionPrefix()
    {
        return ((String) paramMap.get(headers[1].getKeyString()).getValue());
    }

    public String getHost()
    {
        return ((String) paramMap.get(headers[2].getKeyString()).getValue());
    }

    public int getPort()
    {
        return ((Integer) paramMap.get(headers[3].getKeyString()).getValue());
    }

    public String getCatalog()
    {
        return ((String) paramMap.get(headers[4].getKeyString()).getValue());
    }

    public String getUserName()
    {
        return ((String) paramMap.get(headers[5].getKeyString()).getValue());
    }

    public Password getPassword()
    {
        return ((Password) paramMap.get(headers[6].getKeyString()).getValue());
    }

    public String getTableName()
    {
        return ((String) paramMap.get(headers[7].getKeyString()).getValue());
    }

    public String getStoredProcedureName()
    {
        return ((String) paramMap.get(headers[8].getKeyString()).getValue());
    }

    public int getChannelId()
    {
        return ((Integer) paramMap.get(headers[9].getKeyString()).getValue());
    }

    public int getPriceId()
    {
        return ((Integer) paramMap.get(headers[10].getKeyString()).getValue());
    }
    
}
