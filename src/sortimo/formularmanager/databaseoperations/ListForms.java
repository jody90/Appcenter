package sortimo.formularmanager.databaseoperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import sortimo.databaseoperations.Connect;
import sortimo.formularmanager.storage.FormsListStorage;

public class ListForms {
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	
	/**
	 * Holt alle Formulare aus der Datenbank
	 * 
	 * @param land Land
	 * @return Liste mit FormsListStorage Objekten
	 * @throws Exception
	 */
	public ArrayList<FormsListStorage> getFormsList(String land) throws Exception {	
		
		Connect conClass = new Connect();
		connect = conClass.getConnection();

		String sql = "SELECT formularmanager_forms.*,"
				+ "MAX(CASE WHEN meta_name = 'formTitle' THEN meta_value END) as formTitle, "
				+ "MAX(CASE WHEN meta_name = 'validFrom' THEN meta_value END) as validFrom, "
				+ "MAX(CASE WHEN meta_name = 'validTo' THEN meta_value END) as validTo,"
				+ "MAX(CASE WHEN meta_name = 'evaluationType' THEN meta_value END) as evaluationType "
				+ "FROM formularmanager_forms "
				+ "LEFT JOIN formularmanager_forms_meta "
				+ "ON formularmanager_forms.id = formularmanager_forms_meta.form_id "
				+ "WHERE delete_status != 1 "
				+ "AND country LIKE ? "
				+ "GROUP BY form_id";
		
		if (land.equals("ALL")) {
			land = "%";
		}
		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, land);

		ResultSet rs = preparedStatement.executeQuery();
		
		ArrayList<FormsListStorage> results = new ArrayList<FormsListStorage>();		
		
		while(rs.next()) {
			FormsListStorage list = new FormsListStorage();
			list.setId(rs.getString("id"));
			list.setType(rs.getString("type"));
			list.setCountry(rs.getString("country"));
			list.setCreatedAt(rs.getString("created_at"));
			list.setModifiedAt(rs.getString("modified_at"));
			list.setFormTitle(rs.getString("formTitle"));
			list.setValidFrom(rs.getString("validFrom"));
			list.setValidTo(rs.getString("validTo"));
			list.setEvaluationType(rs.getString("evaluationType"));
			
			results.add(list);			
		}
		conClass.close();
		return results;
	}	
}
