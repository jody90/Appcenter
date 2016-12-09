package sortimo.databaseoperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sortimo.model.User;
import sortimo.storage.RightsStorage;

public class UserDb {
	
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	
	public User getUserAccount(String username) throws Exception {
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
		
		User user = new User();

		String tmpRights = null;
		while (rs.next()) {
			user.setUsername(rs.getString("username"));
			user.setFirstname(rs.getString("firstname"));
			user.setLastname(rs.getString("lastname"));
			user.setPassword(rs.getString("password"));
			user.setEmail(rs.getString("email"));
			user.setRoles(rs.getString("roles"));
			tmpRights = rs.getString("rights");
		}
		
		List<RightsStorage> rights = new ArrayList<RightsStorage>();
		
		String allRightsString = this.getRightsFromRoles(user.getRoles());
		String[] tmpRightsFromRoles = allRightsString != null ? allRightsString.split(",") : null;
		String[] tmpRightsArray = tmpRights != null ? tmpRights.split(",") : null;

		List<String> tmpAllRights = null;
		if (tmpRightsFromRoles != null) {
			tmpAllRights = new ArrayList<String>(Arrays.asList(tmpRightsFromRoles));
		}
		
		if (tmpRightsArray != null) {
			for (int i = 0; i < tmpRightsArray.length; i++) {
				if (!Arrays.asList(tmpAllRights).contains(tmpRightsArray[i])) {
					tmpAllRights.add(tmpRightsArray[i]);
					allRightsString += "," + tmpRightsArray[i];
				}
			}
		}
		
		ResultSet rightsList = this.getRights(allRightsString);
		
		while (rightsList.next()) {
			RightsStorage userRights = new RightsStorage();
			userRights.setId(rightsList.getInt("id"));
			userRights.setName(rightsList.getString("name"));
			userRights.setDescription(rightsList.getString("description"));
			rights.add(userRights);
		}
		
		user.setRights(rights);
		
		conClass.close();

		return user;
		
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
