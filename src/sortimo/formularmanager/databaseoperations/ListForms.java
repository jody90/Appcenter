package sortimo.formularmanager.databaseoperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sortimo.databaseoperations.Connect;
import sortimo.formularmanager.storage.FormsListStorage;

public class ListForms {
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	
	public ArrayList<FormsListStorage> getFormsList(String land) throws Exception {	
		
		Connect conClass = new Connect();
		connect = conClass.getConnection();

		String sql = "SELECT * "
				+ "FROM formularmanager_forms "
				+ "WHERE delete_status != 1 "
				+ "AND country LIKE ?";
		
		if (land.equals("ALL")) {
			land = "%";
		}
		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, land);

		ResultSet rs = preparedStatement.executeQuery();
		
		ArrayList<FormsListStorage> results = new ArrayList<FormsListStorage>();		
		
		while(rs.next()) {
			String id = rs.getString("id");
			String type = rs.getString("type");
			String country = rs.getString("country");
			String createdAt = rs.getString("created_at");
			String modifiedAt = rs.getString("modified_at");
			
			sql = "SELECT form_id, "
					+ "MAX(CASE WHEN meta_name = 'formTitle' THEN meta_value END) as formTitle, "
					+ "MAX(CASE WHEN meta_name = 'validFrom' THEN meta_value END) as validFrom, "
					+ "MAX(CASE WHEN meta_name = 'validTo' THEN meta_value END) as validTo "
					+ "FROM formularmanager_forms_meta "
					+ "WHERE form_id = " + id + " "
					+ "GROUP BY form_id";

			preparedStatement = connect.prepareStatement(sql);				
			ResultSet rsMeta = preparedStatement.executeQuery();
			
			Map<String, String> formMeta = new HashMap<String, String>();
			
			if (rsMeta.next()) {			
				formMeta.put("formTitle", rsMeta.getString("formTitle"));
				formMeta.put("validFrom", rsMeta.getString("validFrom"));
				formMeta.put("validTo", rsMeta.getString("validTo"));
			}
			
			results.add(new FormsListStorage(id, type, country, createdAt, modifiedAt, formMeta));
		}
		return results;
	}	
}
