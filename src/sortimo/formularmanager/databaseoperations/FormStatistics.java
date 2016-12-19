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
	
	/**
	 * Holt alle Formularantworten aus der Datenbank.
	 * 
	 * @param formId Formular ID
	 * @param country Land
	 * @return FromsStatisticsStorage Object
	 * @throws Exception
	 */
	public FromsStatisticsStorage getStatistics(String formId, String country) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "SELECT * "
				+ "FROM formularmanager_forms_response "
				+ "WHERE form_id = ?";
	
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, formId);
		ResultSet rsData = preparedStatement.executeQuery();
		
		while (rsData.next()) {
			String value = rsData.getString("value");
			String createdAt = rsData.getString("created_at");
			String username = rsData.getString("username");
			String processState = rsData.getString("process_state");
			String processedBy = rsData.getString("processed_by");
			int id = rsData.getInt("id");

			statisticsStorage.setStatisticsValue(value, createdAt, id, username, processState, processedBy);
			
			statisticsStorage.setFormId(rsData.getInt("form_id"));
		}
		
		sql = "SELECT form_id, "
				+ "MAX(CASE WHEN meta_name = 'formContentJson' THEN meta_value END) as formContentJson, "
				+ "MAX(CASE WHEN meta_name = 'formContentHtml' THEN meta_value END) as formContentHtml, "
				+ "MAX(CASE WHEN meta_name = 'formTitle' THEN meta_value END) as formTitle "
				+ "FROM formularmanager_forms_meta "
				+ "WHERE form_id = ? "
				+ "GROUP BY form_id";
		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, formId);
		ResultSet rsJsonForm = preparedStatement.executeQuery();
		
		while (rsJsonForm.next()) {
			String jsonForm = rsJsonForm.getString("formContentJson");
			String htmlForm = rsJsonForm.getString("formContentHtml");
			String formTitle = rsJsonForm.getString("formTitle");
			
			statisticsStorage.setJsonForm(jsonForm);
			statisticsStorage.setHtmlForm(htmlForm);
			statisticsStorage.setFormTitle(formTitle);
		}
	
		conClass.close();
		return statisticsStorage;
	}
}
