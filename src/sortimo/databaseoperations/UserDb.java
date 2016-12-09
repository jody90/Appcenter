package sortimo.databaseoperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDb {
	
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	
	public ResultSet getUserAccount(String username) throws Exception {
		System.out.println("User Account db Abfrage");
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "SELECT "
				+ "users.username, "
				+ "firstname, "
				+ "lastname, "
				+ "password, "
				+ "email, "
				+ "GROUP_CONCAT(DISTINCT users_rights.right_id ORDER BY users_rights.right_id) AS rights, "
				+ "GROUP_CONCAT(DISTINCT users_roles.role_id ORDER BY users_roles.role_id) AS roles "
				+ "FROM users "
				+ "LEFT JOIN users_roles "
				+ "ON users.username = users_roles.username "
				+ "LEFT JOIN users_rights "
				+ "ON users.username = users_rights.username "
				+ "WHERE users.username = ?";
	
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, username);
		
		ResultSet rs = preparedStatement.executeQuery();
		
		conClass.close();
		
		return rs;
	}
	
	public String getRightsFromRoles(String ids) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "SELECT "
				+ "GROUP_CONCAT(DISTINCT roles_rights.right_id ORDER BY roles_rights.right_id) AS rights "
				+ "FROM roles_rights "
				+ "WHERE role_id IN ("+ids+")";
		
		preparedStatement = connect.prepareStatement(sql);
		
		ResultSet rs = preparedStatement.executeQuery();
		
		String rights = null;
		
		while (rs.next()) {
			rights = rs.getString("rights");
		}
		
		conClass.close();
		
		return rights;
        
	}
	
	public ResultSet getRights(String ids) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "SELECT * "
				+ "FROM rights "
				+ "WHERE id IN ("+ids+")";
		
		preparedStatement = connect.prepareStatement(sql);
		
		ResultSet rs = preparedStatement.executeQuery();
		
		conClass.close();
		
		return rs;
        
	}
}
