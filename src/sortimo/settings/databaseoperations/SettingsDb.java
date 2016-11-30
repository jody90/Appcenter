package sortimo.settings.databaseoperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import sortimo.databaseoperations.Connect;
import sortimo.settings.storage.RightsStorage;
import sortimo.settings.storage.RolesStorage;

public class SettingsDb {
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	
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
	
	public List<RolesStorage> getRoles() throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "SELECT * "
				+ "FROM roles";
		
		preparedStatement = connect.prepareStatement(sql);
		
		ResultSet rs = preparedStatement.executeQuery();
		List<RolesStorage> rolesList = new ArrayList<RolesStorage>();
		
		while (rs.next()) {
			RolesStorage role = new RolesStorage();
			role.setId(rs.getInt("id"));
			role.setName(rs.getString("name"));
			role.setDescription(rs.getString("description"));
			rolesList.add(role);
		}
		
		return rolesList;
        
	}
	
	public List<RightsStorage> getRights() throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "SELECT * "
				+ "FROM rights";
		
		preparedStatement = connect.prepareStatement(sql);
		
		ResultSet rs = preparedStatement.executeQuery();
		List<RightsStorage> rightsList = new ArrayList<RightsStorage>();
		
		while (rs.next()) {
			RightsStorage right = new RightsStorage();
			right.setId(rs.getInt("id"));
			right.setName(rs.getString("name"));
			right.setDescription(rs.getString("description"));
			rightsList.add(right);
		}
		
		return rightsList;
        
	}
	
	public Map<String, String> getRole(String roleId) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "SELECT * "
				+ "FROM roles "
				+ "LEFT JOIN roles_rights "
				+ "ON roles.id = roles_rights.role_id "
				+ "WHERE roles.id = ?";
		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, roleId);
		
		ResultSet rs = preparedStatement.executeQuery();
		Map<String, String> role = new HashMap<String, String>();
		
		String rights = null;
		
		while (rs.next()) {
			role.put("id", rs.getString("id"));
			role.put("name", rs.getString("name"));
			role.put("description", rs.getString("description"));
			rights = rights == null ? rs.getString("right_id") : rights + "," + rs.getString("right_id") ;
		}
		
		role.put("rights", rights);
		
		return role;
        
	}
	
	public void addRole(Map<String, String> roleData) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String id = roleData.get("roleId").equals("-1") ? null : roleData.get("roleId");
		
		String sql = "INSERT INTO "
				+ "roles "
				+ "values (?, ?, ?) "
				+ "ON DUPLICATE KEY UPDATE "
				+ "name = ?, description = ?";
		
		preparedStatement = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, id);
		preparedStatement.setString(2, roleData.get("name"));
		preparedStatement.setString(3, roleData.get("description"));
		preparedStatement.setString(4, roleData.get("name"));
		preparedStatement.setString(5, roleData.get("description"));
		preparedStatement.executeUpdate();

		ResultSet rs = preparedStatement.getGeneratedKeys();
        
		String lastInsertId = roleData.get("roleId");
		
		if (rs.next()) {
            lastInsertId = rs.getString(1);
        }

		Gson gson = new Gson();
		
		String oldRightsJson = roleData.get("oldRights");
		int[] oldRights = gson.fromJson(oldRightsJson, int[].class); 
		
		for (int i = 0; i < oldRights.length; i++) {
			sql = "DELETE FROM "
					+ "roles_rights "
					+ "WHERE role_id = ? "
					+ "AND right_id = ?";
			
			preparedStatement = connect.prepareStatement(sql);
			preparedStatement.setString(1, lastInsertId);
			preparedStatement.setInt(2, oldRights[i]);
			preparedStatement.executeUpdate();
		}

		String rightsJson = roleData.get("rights");
		int[] rights = gson.fromJson(rightsJson, int[].class); 
		
		for (int i = 0; i < rights.length; i++) {
			sql = "INSERT INTO "
					+ "roles_rights "
					+ "values (?, ?) "
					+ "ON DUPLICATE KEY UPDATE "
					+ "role_id = ?, right_id = ?";
			
			preparedStatement = connect.prepareStatement(sql);
			preparedStatement.setString(1, lastInsertId);
			preparedStatement.setInt(2, rights[i]);
			preparedStatement.setString(3, lastInsertId);
			preparedStatement.setInt(4, rights[i]);
			preparedStatement.executeUpdate();
		}	
	}
	
	public void deleteRole(String roleId) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "DELETE FROM "
				+ "roles "
				+ "WHERE id = ?";
		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, roleId);
		preparedStatement.executeUpdate();

		sql = "DELETE FROM "
				+ "roles_rights "
				+ "WHERE role_id = ?";
		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, roleId);
		preparedStatement.executeUpdate();
	}
	
	public void addRight(Map<String, String> rightData) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String id = rightData.get("rightId").equals("-1") ? null : rightData.get("rightId");
		
		String sql = "INSERT INTO "
				+ "rights "
				+ "values (?, ?, ?) "
				+ "ON DUPLICATE KEY UPDATE "
				+ "name = ?, description = ?";
		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, id);
		preparedStatement.setString(2, rightData.get("name"));
		preparedStatement.setString(3, rightData.get("description"));
		preparedStatement.setString(4, rightData.get("name"));
		preparedStatement.setString(5, rightData.get("description"));
		preparedStatement.executeUpdate();
	}
	
	public void deleteRight(String rightId) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "DELETE FROM "
				+ "rights "
				+ "WHERE id = ?";
		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, rightId);
		preparedStatement.executeUpdate();

		sql = "DELETE FROM "
				+ "roles_rights "
				+ "WHERE right_id = ?";
		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, rightId);
		preparedStatement.executeUpdate();
	}
	
}
