package sortimo.settings.databaseoperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sortimo.databaseoperations.Connect;

public class SettingsDb {
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	
	public List<Map<String, String>> getUsers(String editUser) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		List<Map<String, String>> usersList= new ArrayList<Map<String, String>>();
		
		String sql = "SELECT "
				+ "username, "
				+ "firstname, "
				+ "lastname, "
				+ "email, "
				+ "roles, "
				+ "rights "
				+ "FROM users "
				+ "WHERE username LIKE '%' ? '%'";
	
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
	
	public Map<String, String> getUser(String editUser) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "SELECT "
				+ "username, "
				+ "firstname, "
				+ "lastname, "
				+ "email, "
				+ "roles, "
				+ "rights "
				+ "FROM users "
				+ "WHERE username = ?";
		
	
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
	
	public List<Map<String, String>> getRoles() throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "SELECT * "
				+ "FROM roles";
		
		preparedStatement = connect.prepareStatement(sql);
		
		ResultSet rs = preparedStatement.executeQuery();
		List<Map<String, String>> rolesList = new ArrayList<Map<String, String>>();
		
		while (rs.next()) {
			Map<String, String> role = new HashMap<String, String>();
			role.put("id", rs.getString("id"));
			role.put("name", rs.getString("name"));
			role.put("description", rs.getString("description"));
			rolesList.add(role);
		}
		
		return rolesList;
        
	}
	
	public List<Map<String, String>> getRights() throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "SELECT * "
				+ "FROM rights";
		
		preparedStatement = connect.prepareStatement(sql);
		
		ResultSet rs = preparedStatement.executeQuery();
		List<Map<String, String>> rightsList = new ArrayList<Map<String, String>>();
		
		while (rs.next()) {
			Map<String, String> right = new HashMap<String, String>();
			right.put("id", rs.getString("id"));
			right.put("name", rs.getString("name"));
			right.put("description", rs.getString("description"));
			rightsList.add(right);
		}
		
		return rightsList;
        
	}
	
}
