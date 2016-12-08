package sortimo.settings.databaseoperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import sortimo.databaseoperations.Connect;
import sortimo.model.HelperFunctions;

public class ManageUserDb {
	
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
				+ "ON users.username = users_rights.username "
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
				+ "ON users.username = users_rights.username "
				+ "WHERE users.username LIKE '%' ? '%' "
				+ "GROUP BY users.username";
	
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
	
	public void addAccount(Map<String, String> userData) throws Exception {
		HelperFunctions helper = new HelperFunctions();
		
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "INSERT INTO "
				+ "users "
				+ "values (?, ?, ?, ?, ?) "
				+ "ON DUPLICATE KEY UPDATE "
				+ "username = ?, lastname = ?, firstname = ?, email = ?";
		
		String password = helper.md5Hash("1");
		String username = userData.get("oldUsername") != null ? userData.get("oldUsername") : userData.get("username");
	
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, password);
		preparedStatement.setString(3, userData.get("lastname"));
		preparedStatement.setString(4, userData.get("firstname"));
		preparedStatement.setString(5, userData.get("email"));
		preparedStatement.setString(6, userData.get("username"));
		preparedStatement.setString(7, userData.get("lastname"));
		preparedStatement.setString(8, userData.get("firstname"));
		preparedStatement.setString(9, userData.get("email"));
		preparedStatement.executeUpdate();
		
		sql = "DELETE FROM "
				+ "users_rights "
				+ "WHERE username = ?";		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, username);
		preparedStatement.executeUpdate();
		
		Gson gson = new Gson();
		String rightsJson = userData.get("rights");
		int[] rights = gson.fromJson(rightsJson, int[].class); 
		
		for (int i = 0; i < rights.length; i++) {
			sql = "INSERT INTO "
					+ "users_rights "
					+ "values (?, ?)";		
			preparedStatement = connect.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setInt(2, rights[i]);
			preparedStatement.executeUpdate();
		}	
		
		sql = "DELETE FROM "
				+ "users_roles "
				+ "WHERE username = ?";		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, username);
		preparedStatement.executeUpdate();
		
		String rolesJson = userData.get("roles");
		int[] roles = gson.fromJson(rolesJson, int[].class); 
		
		for (int i = 0; i < roles.length; i++) {
			sql = "INSERT INTO "
					+ "users_roles "
					+ "values (?, ?)";		
			preparedStatement = connect.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setInt(2, roles[i]);
			preparedStatement.executeUpdate();
		}	
	}
	
	public void deleteAccount(String deleteUser) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "DELETE "
				+ "FROM users "
				+ "WHERE username = ?";
		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, deleteUser);
		preparedStatement.executeUpdate();
	}

	public void updatePassword(String editUser, String password) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		HelperFunctions helper = new HelperFunctions();
		String hashedPassword = helper.md5Hash(password);
		
		String sql = "UPDATE users "
				+ "SET password = ? "
				+ "WHERE username = ?";
		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, hashedPassword);
		preparedStatement.setString(2, editUser);
		preparedStatement.executeUpdate();
		
	}
}
