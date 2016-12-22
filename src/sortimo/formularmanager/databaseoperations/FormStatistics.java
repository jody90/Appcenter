package sortimo.formularmanager.databaseoperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import sortimo.databaseoperations.Connect;
import sortimo.formularmanager.storage.FormsStatisticsStorage;

public class FormStatistics {
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	
	
	/**
	 * Holt alle Formularantworten aus der Datenbank.
	 * 
	 * @param formId Formular ID
	 * @param country Land
	 * @return FromsStatisticsStorage Object
	 * @throws Exception
	 */
	public Map<Integer, FormsStatisticsStorage> getStatistics(String formId, String country) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "SELECT * "
				+ "FROM formularmanager_forms_response "
				+ "WHERE form_id = ?";
	
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, formId);
		ResultSet rsData = preparedStatement.executeQuery();
		
		Map<Integer, FormsStatisticsStorage> tmpList = new HashMap<>();
		
		while (rsData.next()) {

			FormsStatisticsStorage statisticsStorage = new FormsStatisticsStorage();
			statisticsStorage.setResponseId(rsData.getInt("id"));
			statisticsStorage.setFormId(rsData.getInt("form_id"));
			statisticsStorage.setValue(rsData.getString("value"));
			statisticsStorage.setUsername(rsData.getString("username"));
			statisticsStorage.setCreatedAt(rsData.getString("created_at"));
			statisticsStorage.setModifiedAt(rsData.getString("modified_at"));
			statisticsStorage.setProcessState(rsData.getString("process_state"));
			statisticsStorage.setProcessedBy(rsData.getString("processed_by"));
			statisticsStorage.setNotes(rsData.getString("notes"));
			statisticsStorage.setBossApproved(rsData.getInt("boss_approved"));
			statisticsStorage.setBossId(rsData.getInt("boss_id"));
			
			tmpList.put(statisticsStorage.getResponseId(), statisticsStorage);

		}
		
		conClass.close();
		return tmpList;
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
				+ "SET process_state = ?, processed_by = ?, notes = ? "
				+ "WHERE id = ?";
		
		System.out.println(request.getParameter("notes"));
		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, request.getParameter("state"));
		preparedStatement.setString(2, request.getParameter("processedBy"));
		preparedStatement.setString(3, request.getParameter("notes"));
		preparedStatement.setString(4, request.getParameter("responseId"));
		preparedStatement.execute();
	}
	
}
