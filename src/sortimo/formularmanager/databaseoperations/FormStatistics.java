package sortimo.formularmanager.databaseoperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;

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
				+ "MAX(CASE WHEN meta_name = 'formTitle' THEN meta_value END) as formTitle ,"
				+ "MAX(CASE WHEN meta_name = 'evaluationType' THEN meta_value END) as evaluationType "
				+ "FROM formularmanager_forms_meta "
				+ "WHERE form_id = ? "
				+ "GROUP BY form_id";
		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, formId);
		ResultSet rsJsonForm = preparedStatement.executeQuery();
		
		while (rsJsonForm.next()) {		
			statisticsStorage.setJsonForm(rsJsonForm.getString("formContentJson"));
			statisticsStorage.setHtmlForm(rsJsonForm.getString("formContentHtml"));
			statisticsStorage.setFormTitle(rsJsonForm.getString("formTitle"));
			statisticsStorage.setEvaluationType(rsJsonForm.getString("evaluationType"));
		}
	
		conClass.close();
		return statisticsStorage;
	}
	
	/**
	 * Updated den Processed State in der DB
	 * 
	 * @param request komplettes request Object
	 * @throws Exception
	 */
	public void updateProcessState(HttpServletRequest request) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "UPDATE formularmanager_forms_response "
				+ "SET process_state = ?, processed_by = ? "
				+ "WHERE id = ?";
		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, request.getParameter("state"));
		preparedStatement.setString(2, request.getParameter("processedBy"));
		preparedStatement.setString(3, request.getParameter("responseId"));
		preparedStatement.execute();
	}
	
}
