package ru.flc.service.shopautolink.model.accessobject.source.database;

import com.sybase.jdbcx.SybDriver;
import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.settings.DatabaseSettings;
import ru.flc.service.shopautolink.model.settings.Settings;

import java.sql.*;
import java.util.List;

public class AseDataSource implements DataSource
{
	private static final String TMP_TABLE_NAME = "#tmp_link";
	private static final String TMP_TABLE_CREATE_COMMAND = "create table " + TMP_TABLE_NAME +
			" (gr_id numeric(18) not null, product_code varchar(40) not null, sale int not null)";
	private static final String TMP_TABLE_INSERT_COMMAND = "insert " + TMP_TABLE_NAME +
			" (gr_id, product_code, sale) values (?, ?, ?)";
	
	public static AseDataSource getInstance()
	{
		return SingletonHelper.INSTANCE;
	}

	private String url;
	private String user;
	private String password;
	private String tableName;
	private String storedProcedureName;
	private int channelId;
	private int priceId;

	private Connection connection;
	
	private boolean supportBatchUpdates;
	private boolean supportStoredProcedures;

	@Override
	public void tune(Settings settings) throws Exception
	{
		close();

		if (settings != null)
		{
			String settingsClassName = settings.getClass().getSimpleName();

			if ("DatabaseSettings".equals(settingsClassName))
			{
				resetParameters((DatabaseSettings)settings);
				open();
				prepareTemporaryTable();
			}
		}
		else
			throw new IllegalArgumentException("Database settings are empty.");

	}
	
	@Override
	public void open() throws SQLException
	{
		connection = DriverManager.getConnection(url, user, password);
		connection.setAutoCommit(false);
		
		DatabaseMetaData metaData = connection.getMetaData();
		
		supportBatchUpdates = metaData.supportsBatchUpdates();
		supportStoredProcedures = metaData.supportsStoredProcedures();
	}
	
	@Override
	public void close() throws SQLException
	{
		if (connection != null)
			connection.close();
	}
	
	@Override
	public void applyUploadedTitleLinks() throws SQLException
	{
		String truncateCommand = "truncate table " + tableName;
		String insertCommand = "insert " + tableName + " select * from " + TMP_TABLE_NAME;
		
		try (Statement statement = connection.createStatement())
		{
			statement.executeUpdate(truncateCommand);
			statement.executeUpdate(insertCommand);
			
			connection.commit();
		}
		catch (SQLException e)
		{
			connection.rollback();
			
			throw e;
		}
	}
	
	@Override
	public void uploadTitleLinkPack(List<TitleLink> pack) throws Exception
	{
		if (pack != null && pack.size() > 0)
			if (supportBatchUpdates)
				uploadPackAsBatch(pack);
			else
				uploadPackOneByOne(pack);
	}
	
	private void uploadPackAsBatch(List<TitleLink> pack) throws SQLException
	{
		try (PreparedStatement statement = connection.prepareStatement(TMP_TABLE_INSERT_COMMAND))
		{
			for (TitleLink titleLink : pack)
			{
				statement.setInt(1, titleLink.getTitleId());
				statement.setString(2, titleLink.getProductCode());
				statement.setInt(3, titleLink.getForSale());
				
				statement.addBatch();
			}
			
			statement.executeBatch();
			
			connection.commit();
		}
		catch (Exception e)
		{
			connection.rollback();
			
			throw e;
		}
	}
	
	private void uploadPackOneByOne(List<TitleLink> pack) throws Exception
	{
		try (PreparedStatement statement = connection.prepareStatement(TMP_TABLE_INSERT_COMMAND))
		{
			for (TitleLink titleLink : pack)
			{
				statement.setInt(1, titleLink.getTitleId());
				statement.setString(2, titleLink.getProductCode());
				statement.setInt(3, titleLink.getForSale());
				
				if (statement.executeUpdate() < 1)
					throw new Exception("Can't insert a row in the temporary table.");
			}
			
			connection.commit();
		}
		catch (Exception e)
		{
			connection.rollback();
			
			throw e;
		}
	}
	
	@Override
	public void processTitleLinks() throws SQLException
	{
		if (supportStoredProcedures)
			try (CallableStatement statement = connection.prepareCall("{call " +
					storedProcedureName + " (?, ?)}"))
			{
				statement.setInt(1, channelId);
				statement.setInt(2, priceId);
				
				statement.executeUpdate();
				
				connection.commit();
			}
			catch (SQLException e)
			{
				connection.rollback();
				
				throw e;
			}
	}

	private void resetParameters(DatabaseSettings settings) throws Exception
	{
		SybDriver sybDriver = (SybDriver) Class.forName(settings.getDriverName()).newInstance();
		DriverManager.registerDriver(sybDriver);
		
		url = buildDatabaseUrl(settings);
		user = settings.getUserName();
		password = settings.getPassword();
		tableName = settings.getTableName();
		storedProcedureName = settings.getStoredProcedureName();
		channelId = settings.getChannelId();
		priceId = settings.getPriceId();
	}
	
	private String buildDatabaseUrl(DatabaseSettings settings)
	{
		StringBuilder builder = new StringBuilder(settings.getConnectionPrefix());
		builder.append(':');
		builder.append(settings.getHost());
		builder.append(':');
		builder.append(settings.getPort());
		builder.append('/');
		builder.append(settings.getCatalog());
		
		return builder.toString();
	}
	
	private void prepareTemporaryTable() throws SQLException
	{
		try (Statement statement = connection.createStatement())
		{
			statement.executeUpdate(TMP_TABLE_CREATE_COMMAND);
			
			connection.commit();
		}
		catch (SQLException e)
		{
			connection.rollback();
			
			throw e;
		}
	}

	private static class SingletonHelper
	{
		private static final AseDataSource INSTANCE = new AseDataSource();
	}
}
