package sortimo.databaseoperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import sortimo.storage.BossStorage;

public class UsersDb {
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	
	/**
	 * Holt alle User mit der Rolle Boss aus der Datenbank
	 * 
	 * @return Liste mit BossStorage Objekten
	 * @throws Exception
	 */
	public List<BossStorage> getBosses() throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "SELECT "
				+ "users.username, "
				+ "firstname, "
				+ "lastname, "
				+ "email "
				+ "FROM users "
				+ "LEFT JOIN users_roles "
				+ "ON users.username = users_roles.username "
				+ "WHERE users_roles.role_id = 46";
		
		preparedStatement = connect.prepareStatement(sql);
		
		ResultSet rs = preparedStatement.executeQuery();
		
		List<BossStorage> bossStorageList = new ArrayList<>();
		
		while (rs.next()) {
			BossStorage bossStorage = new BossStorage();
			bossStorage.setUsername(rs.getString("username"));
			bossStorage.setFirstname(rs.getString("firstname"));
			bossStorage.setLastname(rs.getString("lastname"));
			bossStorage.setEmail(rs.getString("email"));

			bossStorageList.add(bossStorage);
			
		}
		
		conClass.close();
		
		return bossStorageList;
	}
	
}
