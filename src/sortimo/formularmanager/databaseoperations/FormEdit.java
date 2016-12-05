package sortimo.formularmanager.databaseoperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import sortimo.databaseoperations.Connect;


public class FormEdit extends Connect{
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	private boolean writeDatabaseResponse = false;
	
	public boolean insertForm(Map<String, String> globalData, Map<String, String> metaData) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();

		String sql = "INSERT INTO "
				+ "formularmanager_forms "
				+ "values (default, ?, ?, default, default, default)";
	
		preparedStatement = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, globalData.get("formType"));
		preparedStatement.setString(2, globalData.get("country"));
		preparedStatement.executeUpdate();
		
		ResultSet rs = preparedStatement.getGeneratedKeys();
        
		int lastInsertId = -1;
		
		if (rs.next()) {
            lastInsertId = rs.getInt(1);
        }
		else {
			this.close();
		}
		
		if (lastInsertId > -1) {
			sql = "INSERT INTO "
					+ "formularmanager_forms_meta "
					+ "values (default, ?, ?, ?)";
			for (Map.Entry<String, String> entry : metaData.entrySet()) {
				preparedStatement = connect.prepareStatement(sql);
				preparedStatement.setInt(1, lastInsertId);
				preparedStatement.setString(2, entry.getKey());
				preparedStatement.setString(3, entry.getValue());
				preparedStatement.executeUpdate();
			}
			
		}
		conClass.close();
		writeDatabaseResponse = true;
		return writeDatabaseResponse;
	}
	
	public boolean updateForm(Map<String, String> globalData, Map<String, String> metaData) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		int formId = Integer.parseInt(globalData.get("formId"));

		String sql = "UPDATE "
				+ "formularmanager_forms "
				+ "SET type = ?, country = ?, modified_at = default "
				+ "WHERE id = " + formId + "";
		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, globalData.get("formType"));
		preparedStatement.setString(2, globalData.get("country"));
		preparedStatement.executeUpdate();
		
		sql = "INSERT INTO "
			+ "formularmanager_forms_meta "
			+ "values (default, ?, ?, ?) "
			+ "ON DUPLICATE KEY UPDATE "
			+ "meta_name = ?, meta_value = ?";
		
		for (Map.Entry<String, String> entry : metaData.entrySet()) {
			preparedStatement = connect.prepareStatement(sql);
			preparedStatement.setInt(1, formId);
			preparedStatement.setString(2, entry.getKey());
			preparedStatement.setString(3, entry.getValue());
			preparedStatement.setString(4, entry.getKey());
			preparedStatement.setString(5, entry.getValue());
			preparedStatement.executeUpdate();
		}		
		conClass.close();
		writeDatabaseResponse = true;
		return writeDatabaseResponse;
	}
	
	public Map<String, String> getFormData(String formId) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		Map<String, String> formData = new HashMap<String, String>();

		String sql = "SELECT * "
				+ "FROM formularmanager_forms "
				+ "WHERE id = " + formId + " "
				+ "AND delete_status != 1";
		
		preparedStatement = connect.prepareStatement(sql);				
		ResultSet rsData = preparedStatement.executeQuery();
		
		// Den rest nur ausfuehren wenn beim ersten Request ein Response kam
		if (rsData.next()) {
			formData.put("formId", rsData.getString("id"));
			formData.put("formType", rsData.getString("type"));
			formData.put("createdAt", rsData.getString("created_at"));
			formData.put("modifiedAt", rsData.getString("modified_at"));
		
			sql = "SELECT COUNT(*)"
					+ "FROM formularmanager_forms_meta "
					+ "WHERE form_id = " + formId + "";
			
			preparedStatement = connect.prepareStatement(sql);				
			ResultSet rsCount = preparedStatement.executeQuery();
			int count = 0;
			
			if (rsCount.next()) {
				count = rsCount.getInt(1);
			}
			
			for (int i = 1; i <= count; i++) {		
				sql = "SELECT meta_name, meta_value "
						+ "FROM formularmanager_forms_meta "
						+ "WHERE form_id = " + formId + " "
						+ "LIMIT 1 OFFSET " + (i-1) + "";
				
				preparedStatement = connect.prepareStatement(sql);				
				ResultSet rsMeta = preparedStatement.executeQuery();
				if (rsMeta.next()) {
					formData.put(rsMeta.getString("meta_name"), rsMeta.getString("meta_value"));
				}
			}
		}
		conClass.close();
		return formData;
	}
	
	public boolean deleteForm(String formId) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();

		String sql = "UPDATE "
				+ "formularmanager_forms "
				+ "SET delete_status = 1, "
				+ "modified_at = default "
				+ "WHERE id = " + formId + "";
		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.execute();

		boolean writeDatabaseResponse = true;
		conClass.close();
		return writeDatabaseResponse;
	}	
}
