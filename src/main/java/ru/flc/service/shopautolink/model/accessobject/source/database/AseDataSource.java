package ru.flc.service.shopautolink.model.accessobject.source.database;

import com.sybase.jdbcx.SybDriver;
import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.settings.DatabaseSettings;
import ru.flc.service.shopautolink.model.settings.Settings;

import java.sql.*;
import java.util.List;

public class AseDataSource implements DataSource
{
	private static final String SETTINGS_EMPTY_EXCEPTION_STRING = "Database settings are empty.";
	private static final String SETTINGS_WRONG_EXCEPTION_STRING = "Wrong database settings.";
	private static final String DB_WITHOUT_SP_SUPPORT_EXCEPTION_STRING = "This database doesn't support stored procedures.";
	private static final String TMP_TABLE_NAME = "#tmp_link";
	private static final String TMP_TABLE_CREATE_COMMAND = "create table " + TMP_TABLE_NAME +
			" (gr_id numeric(18,0), product_code varchar(7), prizn numeric(18,0))";
	private static final String TMP_TABLE_INSERT_COMMAND = "insert " + TMP_TABLE_NAME +
			" (gr_id, product_code, prizn) values (?, ?, ?)";
	
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
	
	
	private AseDataSource(){}
	
	@Override
	public void open() throws SQLException
	{
		//TODO: Decript the password here
		connection = DriverManager.getConnection(url, user, password);
		connection.setAutoCommit(false);
		
		DatabaseMetaData metaData = connection.getMetaData();
		
		supportBatchUpdates = metaData.supportsBatchUpdates();
		supportStoredProcedures = metaData.supportsStoredProcedures();
		
		prepareTemporaryTable();
	}
	
	@Override
	public void close() throws SQLException
	{
		if (connection != null && !connection.isClosed())
			connection.close();
	}

	@Override
	public void tune(Settings settings) throws Exception
	{
		close();

		if (settings != null)
		{
			String settingsClassName = settings.getClass().getSimpleName();

			if ("DatabaseSettings".equals(settingsClassName))
				resetParameters((DatabaseSettings)settings);
			else
				throw new IllegalArgumentException(SETTINGS_WRONG_EXCEPTION_STRING);
		}
		else
			throw new IllegalArgumentException(SETTINGS_EMPTY_EXCEPTION_STRING);
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
	
	@Override
	public void processTitleLinks() throws Exception
	{
		if (supportStoredProcedures)
		{
			connection.setAutoCommit(true);

			try (CallableStatement statement = connection.prepareCall("{call " +
					storedProcedureName + " (?, ?)}"))
			{
				statement.setInt(1, priceId);
				statement.setInt(2, channelId);

				statement.executeUpdate();

				connection.commit();
			}
			catch (SQLException e)
			{
				connection.rollback();

				throw e;
			}
			finally
			{
				connection.setAutoCommit(false);
			}
		}
		else
			throw new Exception(DB_WITHOUT_SP_SUPPORT_EXCEPTION_STRING);

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
