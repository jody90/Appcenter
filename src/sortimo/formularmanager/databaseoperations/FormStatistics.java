package sortimo.formularmanager.databaseoperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import sortimo.databaseoperations.Connect;
import sortimo.formularmanager.storage.FromsStatisticsStorage;

public class FormStatistics {
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	private FromsStatisticsStorage statisticsStorage = new FromsStatisticsStorage();
	
	public FromsStatisticsStorage getStatistics(String formId, String country) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "SELECT * "
				+ "FROM formular_manager.forms_response "
				+ "WHERE form_id = ?";
	
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, formId);
		ResultSet rsData = preparedStatement.executeQuery();
		
		while (rsData.next()) {
			String value = rsData.getString("value");
			String createdAt = rsData.getString("created_at");
			int id = rsData.getInt("id");
			int userId = rsData.getInt("user_id");

			statisticsStorage.setStatisticsValue(value, createdAt, id, userId);
			
			statisticsStorage.setFormId(rsData.getInt("form_id"));
		}
		
		sql = "SELECT form_id, "
				+ "MAX(CASE WHEN meta_name = 'formContentJson' THEN meta_value END) as formContentJson, "
				+ "MAX(CASE WHEN meta_name = 'formTitle' THEN meta_value END) as formTitle "
				+ "FROM formular_manager.forms_meta "
				+ "WHERE form_id = ? "
				+ "GROUP BY form_id";
		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, formId);
		ResultSet rsJsonForm = preparedStatement.executeQuery();
		
		while (rsJsonForm.next()) {
			String jsonForm = rsJsonForm.getString("formContentJson");
			String formTitle = rsJsonForm.getString("formTitle");
			
			statisticsStorage.setJsonForm(jsonForm);
			statisticsStorage.setFormTitle(formTitle);
		}
	
		conClass.close();
		return statisticsStorage;
	}
}
