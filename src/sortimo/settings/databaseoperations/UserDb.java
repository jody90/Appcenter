package sortimo.settings.databaseoperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sortimo.databaseoperations.Connect;

public class UserDb {
	
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	
	public Map<String, String> getUser(String editUser) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
			
		String sql = "SELECT "
				+ "users.username, "
				+ "firstname, "
				+ "lastname, "
				+ "email, "
				+ "GROUP_CONCAT(DISTINCT users_rights.right_id ORDER BY users_rights.right_id) AS rights,"
				+ "GROUP_CONCAT(DISTINCT users_roles.role_id ORDER BY users_roles.role_id) AS roles "
				+ "FROM users "
				+ "LEFT JOIN users_roles "
				+ "ON users.username = users_roles.username "
				+ "LEFT JOIN users_rights "
				+ "ON users.username = users_roles.username "
				+ "WHERE users.username = ? "
				+ "group by users.username";
	
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, editUser);
		
		ResultSet rs = preparedStatement.executeQuery();
		Map<String, String> user = new HashMap<String, String>();
		
		while (rs.next()) {
			user.put("username", rs.getString("username"));
			user.put("firstname", rs.getString("firstname"));
			user.put("lastname", rs.getString("lastname"));
			user.put("email", rs.getString("email"));
			user.put("roles", rs.getString("roles"));
			user.put("rights", rs.getString("rights"));
		}
		return user;
	}
	
	public List<Map<String, String>> getUsers(String editUser) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		List<Map<String, String>> usersList= new ArrayList<Map<String, String>>();
		
		String sql = "SELECT "
				+ "users.username, "
				+ "firstname, "
				+ "lastname, "
				+ "email, "
				+ "GROUP_CONCAT(DISTINCT users_rights.right_id ORDER BY users_rights.right_id) AS rights, "
				+ "GROUP_CONCAT(DISTINCT users_roles.role_id ORDER BY users_roles.role_id) AS roles "
				+ "FROM users "
				+ "LEFT JOIN users_roles "
				+ "ON users.username = users_roles.username "
				+ "LEFT JOIN users_rights "
				+ "ON users.username = users_roles.username "
				+ "WHERE users.username LIKE '%' ? '%' "
				+ "group by users.username";
	
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, editUser);
		
		ResultSet rs = preparedStatement.executeQuery();
		
		while (rs.next()) {
			Map<String, String> users = new HashMap<String, String>();
			users.put("username", rs.getString("username"));
			users.put("firstname", rs.getString("firstname"));
			users.put("lastname", rs.getString("lastname"));
			users.put("email", rs.getString("email"));
			users.put("roles", rs.getString("roles"));
			users.put("rights", rs.getString("rights"));
			usersList.add(users);
		}
		return usersList;
	}
}
