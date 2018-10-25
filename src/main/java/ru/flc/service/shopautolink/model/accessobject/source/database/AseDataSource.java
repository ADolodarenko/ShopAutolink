package ru.flc.service.shopautolink.model.accessobject.source.database;

import com.sybase.jdbcx.SybDriver;
import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.settings.DatabaseSettings;
import ru.flc.service.shopautolink.model.settings.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class AseDataSource implements DataSource
{
	public static AseDataSource getInstance()
	{
		return SingletonHelper.INSTANCE;
	}

	private String url;
	private String user;
	private String password;

	private Connection connection;

	@Override
	public void tune(Settings settings)
	{
		closeConnection();

		if (settings != null)
		{
			String settingsClassName = settings.getClass().getSimpleName();

			if ("DatabaseSettings".equals(settingsClassName))
			{
				resetParameters((DatabaseSettings)settings);
				prepareConnection();
			}
		}

	}

	@Override
	public void applyUploadedTitleLinks()
	{}
	
	@Override
	public void uploadTitleLinkPack(List<TitleLink> pack)
	{

	}
	
	@Override
	public void processTitleLinks()
	{}

	private void closeConnection()
	{
		if (connection != null)
		{
			try
			{
				connection.close();
			}
			catch (SQLException e)
			{
			}
		}
	}

	private void resetParameters(DatabaseSettings settings)
	{
		//TODO: prepare the needed driver here
		SybDriver sybDriver = null;
		try
		{
			sybDriver = (SybDriver) Class.forName(settings.getDriverName()).newInstance();
			DriverManager.registerDriver(sybDriver);
		}
		catch (InstantiationException e)
		{}
		catch (IllegalAccessException e)
		{}
		catch (ClassNotFoundException e)
		{}
		catch (SQLException e)
		{}
	}

	private void prepareConnection()
	{
		//TODO: get the connection here
	}

	private static class SingletonHelper
	{
		private static final AseDataSource INSTANCE = new AseDataSource();
	}
}
