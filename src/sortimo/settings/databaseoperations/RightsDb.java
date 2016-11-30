package sortimo.settings.databaseoperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sortimo.databaseoperations.Connect;
import sortimo.settings.storage.RightsStorage;

public class RightsDb {
	
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	
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
		
		sql = "DELETE FROM "
				+ "users_rights "
				+ "WHERE right_id = ?";
		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, rightId);
		preparedStatement.executeUpdate();
	}
}
