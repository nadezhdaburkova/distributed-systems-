package ru.nsu.ccfit.burkova.task1;

import java.sql.*;
import java.util.Date;


public class Main {

	private static final String DB_URL = "jdbc:h2:mem:~/TEST;DB_CLOSE_DELAY=-1";

	private static final String USER = "sa";
	private static final String PASS = "";

	private static final int NUMBER_OF_RECORDS = 1000;
	private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS Test_table" +
			                                               "(ID NUMBER," +
			                                               " NAME VARCHAR(50)," +
			                                               " CREATION_DATE TIMESTAMP);";
	private static final String SQL_TRUNCATE = "TRUNCATE TABLE Test_table";

	public static void main(String[] args) {
		Connection connection = null;

		try {
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			statement.executeUpdate(SQL_CREATE_TABLE);

			//Statement
			Date date = new Date();
			for (int i = 0; i < NUMBER_OF_RECORDS; ++i) {
				statement.execute(String.format("INSERT INTO Test_table " +
						                                "(ID, NAME, CREATION_DATE)" +
						                                " VALUES(%d, '%d', NOW())", i, i));
			}
			connection.commit();
			System.out.println("Statement time: " + ((Long)(new Date().getTime() - date.getTime())));
			statement.execute(SQL_TRUNCATE);

			//PreparedStatement
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Test_table " +
					                                                                  "(ID, NAME, CREATION_DATE)" +
					                                                                  " VALUES(?, ?, NOW())");
			date = new Date();
			for (int i = 0; i < NUMBER_OF_RECORDS; ++i) {
				preparedStatement.setInt(1, i);
				preparedStatement.setString(2, i + "");
				preparedStatement.execute();
			}
			connection.commit();
			System.out.println("PreparedStatement time: " + ((Long)(new Date().getTime() - date.getTime())));
			statement.execute(SQL_TRUNCATE);

			//PreparedStatement + Batch
			date = new Date();
			for (int i = 0; i < NUMBER_OF_RECORDS; ++i) {
				preparedStatement.setInt(1, i);
				preparedStatement.setString(2, i + "");
				preparedStatement.executeBatch();
			}
			connection.commit();
			System.out.println("PreparedStatement + Batch time: " + ((Long)(new Date().getTime() - date.getTime())));
			statement.execute(SQL_TRUNCATE);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
}
