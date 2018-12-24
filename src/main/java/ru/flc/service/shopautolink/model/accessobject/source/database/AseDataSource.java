package ru.flc.service.shopautolink.model.accessobject.source.database;

import com.sybase.jdbcx.SybDriver;
import ru.flc.service.shopautolink.model.Element;
import ru.flc.service.shopautolink.model.TitleLink;
import ru.flc.service.shopautolink.model.settings.DatabaseSettings;
import ru.flc.service.shopautolink.model.settings.Settings;
import ru.flc.service.shopautolink.model.settings.type.Password;
import ru.flc.service.shopautolink.view.Constants;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AseDataSource implements DataSource
{
	public static AseDataSource getInstance()
	{
		return SingletonHelper.INSTANCE;
	}

	private static String buildDatabaseUrl(DatabaseSettings settings)
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

	private static void executeStatement(PreparedStatement statement, List<List<Element>> outputLines)
			throws SQLException
	{
		boolean done = false;
		boolean isResultSet = statement.execute();

		while (!done)
		{
			if (isResultSet)
				parseResultSet(statement.getResultSet(), outputLines);
			else
			{
				int updateCount = statement.getUpdateCount();

				if (updateCount >= 0)
					outputLines.add(getLineWithOneElement(
							new Element(String.format(Constants.MESS_ROWS_AFFECTED, updateCount), String.class)));
				else
					done = true;
			}

			if (!done)
				isResultSet = statement.getMoreResults();
		}

		SQLWarning warning = statement.getWarnings();

		while (warning != null)
		{
			outputLines.add(getLineWithOneElement(
					new Element(warning.getMessage(), String.class)));

			warning = warning.getNextWarning();
		}
	}

	private static void parseResultSet(ResultSet resultSet, List<List<Element>> outputLines)
			throws SQLException
	{
		outputLines.add(getResultSetHeaderLine(resultSet));

		while (resultSet.next())
			outputLines.add(getResultSetDataLine(resultSet));
	}

	private static List<Element> getResultSetHeaderLine(ResultSet resultSet) throws SQLException
	{
		List<Element> line = new LinkedList<>();

		ResultSetMetaData metaData = resultSet.getMetaData();

		for (int i = 1; i <= metaData.getColumnCount(); i++)
			line.add(new Element(metaData.getColumnLabel(i), String.class));

		return line;
	}

	private static List<Element> getResultSetDataLine(ResultSet resultSet)
			throws SQLException
	{
		List<Element> line = new LinkedList<>();

		ResultSetMetaData metaData = resultSet.getMetaData();

		for (int i = 1; i <= metaData.getColumnCount(); i++)
			line.add(getElementByField(resultSet, i));

		return line;
	}

	private static List<Element> getLineWithOneElement(Element element)
	{
		List<Element> line = new LinkedList<>();
		line.add(element);

		return line;
	}

	private static Element getElementByField(ResultSet resultSet, int fieldNumber)
			throws SQLException
	{
		Object value = resultSet.getObject(fieldNumber);
		Class<?> valueClass = value.getClass();

		return new Element(value, valueClass);
	}


	private String url;
	private String user;
	private Password password;
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
		connection = DriverManager.getConnection(url, user, new String(password.getKey()));

		SQLWarning warning = connection.getWarnings();
		if (warning != null)
			throw warning;

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

			if (Constants.CLASS_NAME_DATABASESETTINGS.equals(settingsClassName))
				resetParameters((DatabaseSettings)settings);
			else
				throw new IllegalArgumentException(Constants.EXCPT_DATABASE_SETTINGS_WRONG);
		}
		else
			throw new IllegalArgumentException(Constants.EXCPT_DATABASE_SETTINGS_EMPTY);
	}
	
	@Override
	public void applyUploadedTitleLinks() throws SQLException
	{
		String truncateCommand = "truncate table " + tableName;
		String insertCommand = "insert " + tableName + " select * from " + Constants.TMP_TABLE_NAME;
		
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
	public List<List<Element>> processTitleLinks() throws Exception
	{
		if (supportStoredProcedures)
		{
			List<List<Element>> resultLines = new LinkedList<>();
			
			connection.setAutoCommit(true);

			try (CallableStatement statement = connection.prepareCall("{call " +
					storedProcedureName + " (?, ?)}"))
			{
				statement.setInt(1, priceId);
				statement.setInt(2, channelId);

				executeStatement(statement, resultLines);
				
				return resultLines;
			}
			catch (SQLException e)
			{
				throw e;
			}
			finally
			{
				connection.setAutoCommit(false);
			}
		}
		else
			throw new Exception(Constants.EXCPT_DATABASE_WITHOUT_SP_SUPPORT);
	}
	
	private void uploadPackAsBatch(List<TitleLink> pack) throws SQLException
	{
		try (PreparedStatement statement = connection.prepareStatement(Constants.TMP_TABLE_INSERT_COMMAND))
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
		try (PreparedStatement statement = connection.prepareStatement(Constants.TMP_TABLE_INSERT_COMMAND))
		{
			for (TitleLink titleLink : pack)
			{
				statement.setInt(1, titleLink.getTitleId());
				statement.setString(2, titleLink.getProductCode());
				statement.setInt(3, titleLink.getForSale());
				
				if (statement.executeUpdate() < 1)
					throw new Exception(Constants.EXCPT_CANT_INSERT_TO_TMP_TABLE);
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
	
	private void prepareTemporaryTable() throws SQLException
	{
		try (Statement statement = connection.createStatement())
		{
			statement.executeUpdate(Constants.TMP_TABLE_CREATE_COMMAND);
			
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
