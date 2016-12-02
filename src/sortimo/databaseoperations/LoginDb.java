package sortimo.databaseoperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class LoginDb {
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	
	public String getUserPassword(String username) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();

		String sql = "SELECT password "
				+ "FROM users "
				+ "WHERE username = ?";
	
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, username);
		
		ResultSet rs = preparedStatement.executeQuery();
		
		String password = null;
		
		while (rs.next()) {
			password = rs.getString("password");
		}
		
		return password;
		
	}
	
	public Map<String, String> getUserAccount(String username) throws Exception {
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
		
		Map<String, String> userInfo = new HashMap<String, String>();
		
		while (rs.next()) {
			userInfo.put("username", rs.getString("username"));
			userInfo.put("firstname", rs.getString("firstname"));
			userInfo.put("lastname", rs.getString("lastname"));
			userInfo.put("email", rs.getString("email"));
			userInfo.put("roles", rs.getString("roles"));
			userInfo.put("rights", rs.getString("rights"));
		}
		
		return userInfo;
	}
}