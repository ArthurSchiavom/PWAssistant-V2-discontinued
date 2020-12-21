package database.base;

import database.tables.CountdownClockTable;
import database.tables.MessagingSchedulerTable;
import database.tables.PWIClockTable;
import database.tables.TriggersTable;
import information.ownerconfiguration.Database;

import javax.annotation.CheckForNull;
import java.sql.*;

/**
 * Manages interaction with the database. <b>Add tables creation to initializeDatabase.</b>
 */
public class DatabaseManager {
	private static DatabaseManager instance;
	private DatabaseManager() {}
	/**
	 * @return This singleton's instance.
	 */
	public static DatabaseManager getInstance() {
		return instance;
	}

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private Connection connection;

	private void initializeTables(Connection connection) throws SQLException {
		Table[] tables = {PWIClockTable.initialize(), CountdownClockTable.initialize(), TriggersTable.initialize()
				, MessagingSchedulerTable.initialize()};

		for (Table table : tables) {
			createTableIfNotExists(connection, table);
			if (!table.loadIntoMemory())
				throw new SQLException("Failed to load table " + table.getTableName() + " into memory");
		}
	}

	/**
	 * Initializes the database manager and the database tables.
	 *
	 * @return If the operation was successful.
	 */
	public static boolean initialize() {
		try {
			Class.forName(DRIVER);
		} catch (Exception e) {
			System.out.println("FAILED TO INITIALIZE THE DATABASE DRIVER");
			e.printStackTrace();
			return false;
		}
		instance = new DatabaseManager();
		return instance.initializeDatabase();
	}

	/**
	 * Initializes the database and it's tables in case they don't exist.
	 *
	 * @return If the operation was successful.
	 */
	private boolean initializeDatabase() {
		startConnection(true);
		try {
			initializeTables(connection);
		} catch (SQLException e) {
			System.out.println("FAILED TO INITIALIZE THE DATABASE AND IT'S TABLES");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Starts a connection with the database.
	 *
	 * @param createDatabase If it should be attempted to create the schema if it doesn't exist yet.
	 */
	private void startConnection(boolean createDatabase) {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + Database.databaseUrl, Database.databaseUsername, Database.databasePassword);
			if (createDatabase) {
				Statement statement = connection.createStatement();
				String command = "CREATE DATABASE IF NOT EXISTS " + Database.databaseName;
				statement.executeUpdate(command);
			}
			connection.setCatalog(Database.databaseName);
		} catch (SQLException e) {
			System.out.println("FAILED TO CONNECT TO THE DATABASE");
			e.printStackTrace();
		}
	}

	/**
	 * Creates a database table if it doesn't exist yet.
	 *
	 * @param connection The database connection.
	 * @param table The table.
	 * @throws SQLException If the operation fails.
	 */
	private void createTableIfNotExists(Connection connection, Table table) throws SQLException {
		Statement statement = connection.createStatement();
		String command = table.calcMySQLCreateTableCommand();
		statement.executeUpdate(command);
	}

	/**
	 * Creates a new empty statement. Prints error message if database access fails.
	 *
	 * @param stm The statement.
	 * @return The requested prepared statement.
	 * @throws SQLException If the application is unable to connect to the database.
	 */
	@CheckForNull
	public PreparedStatement createPreparedStatement(String stm) {
		int attempt = 1;
		boolean success = false;
		while (attempt <= 2 && !success) {
			try {
				Statement testStatement = connection.createStatement();
				testStatement.execute("SELECT 1");
				success = true;
			} catch (SQLException e) {
				if (attempt < 2) {
					startConnection(false);
					attempt++;
				}
				else
					e.printStackTrace();
			}
		}

		if (!success) {
			System.out.println("FAILED TO ACCESS DATABASE.");
			return null;
		}
		
		try {
			return connection.prepareStatement(stm);
		} catch (SQLException e) {
			System.out.println("FAILED TO ACCESS DATABASE.");
			e.printStackTrace();
			return null;
		}
	}
}
