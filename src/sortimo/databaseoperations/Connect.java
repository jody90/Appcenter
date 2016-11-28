package sortimo.databaseoperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class Connect {
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	public Connection getConnection() throws Exception {
		Connection connect = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/sortimo_appcenter?" + "user=root&password=root");			
		}
		catch (Exception e) {
			throw e;
		}
		return connect;
	}
	
	// You need to close the resultSet
	public void close() throws Exception {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} 
		catch (Exception e) {
			throw e;
		}
	}
	
}
