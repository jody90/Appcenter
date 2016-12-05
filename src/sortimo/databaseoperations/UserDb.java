package sortimo.databaseoperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDb {
	
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	
	public ResultSet getUserAccount(String username) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "SELECT "
				+ "users.username, "
				+ "firstname, "
				+ "lastname,"
				+ "password, "
				+ "email, "
				+ "GROUP_CONCAT(DISTINCT users_rights.right_id ORDER BY users_rights.right_id) AS rights,"
				+ "GROUP_CONCAT(DISTINCT users_roles.role_id ORDER BY users_roles.role_id) AS roles "
				+ "FROM users "
				+ "LEFT JOIN users_roles "
				+ "ON users.username = users_roles.username "
				+ "LEFT JOIN users_rights "
				+ "ON users.username = users_roles.username "
				+ "WHERE users.username = ?";
	
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, username);
		
		ResultSet rs = preparedStatement.executeQuery();
		
		conClass.close();
		
		return rs;
	}	
}
