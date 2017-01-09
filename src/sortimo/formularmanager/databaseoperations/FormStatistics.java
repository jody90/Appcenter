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
	 * Holt alle Response Daten eines einzigen Response Formulars aus der DB
	 * 
	 * @param responseId
	 * @return
	 * @throws Exception
	 */
	public FormsStatisticsStorage getFormStatistics(String responseId) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		FormsStatisticsStorage statisticsStorage = new FormsStatisticsStorage();
		
		String sql = "SELECT * "
				+ "FROM formularmanager_forms_response "
				+ "WHERE id = ?";
		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, responseId);
		ResultSet rsData = preparedStatement.executeQuery();
		
		while (rsData.next()) {
			
			Integer bossApproved = rsData.getObject("boss_approved") == null ? null : rsData.getInt("boss_approved");
			String createdAt = rsData.getString("created_at").substring(0, rsData.getString("created_at").length() - 2) + "";
			String modifiedAt = rsData.getString("modified_at").substring(0, rsData.getString("modified_at").length() - 2) + "";
			
			statisticsStorage.setResponseId(rsData.getInt("id"));
			statisticsStorage.setFormId(rsData.getInt("form_id"));
			statisticsStorage.setValue(rsData.getString("value"));
			statisticsStorage.setUsername(rsData.getString("username"));
			statisticsStorage.setCreatedAt(createdAt);
			statisticsStorage.setModifiedAt(modifiedAt);
			statisticsStorage.setProcessState(rsData.getString("process_state"));
			statisticsStorage.setProcessedBy(rsData.getString("processed_by"));
			statisticsStorage.setNotes(rsData.getString("notes"));
			statisticsStorage.setBossApproved(bossApproved);
			statisticsStorage.setBoss(rsData.getString("boss"));
		}
		
		conClass.close();
		return statisticsStorage;
	}
	
	/**
	 * Holt nur die notwendigen Daten der Responded Forms aus der DB um eine Liste erzeugen zu koennen
	 * 
	 * @param formId
	 * @param country
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, FormsStatisticsStorage> getRespondedForms(String formId, String country) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "SELECT "
				+ "id, "
				+ "formularmanager_forms_response.form_id, "
				+ "username, "
				+ "created_at, "
				+ "modified_at, "
				+ "process_state, "
				+ "processed_by, "
				+ "boss_approved, "
				+ "boss,"
				+ "MAX(CASE WHEN meta_name = 'formTitle' THEN meta_value END) as formTitle "
				+ "FROM formularmanager_forms_response "
				+ "LEFT JOIN formularmanager_forms_meta "
				+ "ON formularmanager_forms_response.form_id = formularmanager_forms_meta.form_id "
				+ "WHERE formularmanager_forms_response.form_id = ? "
				+ "GROUP BY id";
	
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, formId);
		ResultSet rsData = preparedStatement.executeQuery();
		
		Map<Integer, FormsStatisticsStorage> tmpList = new HashMap<>();
		
		while (rsData.next()) {
			
			Integer bossApproved = rsData.getObject("boss_approved") == null ? null : rsData.getInt("boss_approved");
			String createdAt = rsData.getString("created_at").substring(0, rsData.getString("created_at").length() - 2) + "";
			String modifiedAt = rsData.getString("modified_at").substring(0, rsData.getString("modified_at").length() - 2) + "";

			FormsStatisticsStorage statisticsStorage = new FormsStatisticsStorage();
			statisticsStorage.setResponseId(rsData.getInt("id"));
			statisticsStorage.setFormId(rsData.getInt("form_id"));
			statisticsStorage.setUsername(rsData.getString("username"));
			statisticsStorage.setCreatedAt(createdAt);
			statisticsStorage.setModifiedAt(modifiedAt);
			statisticsStorage.setProcessState(rsData.getString("process_state"));
			statisticsStorage.setProcessedBy(rsData.getString("processed_by"));
			statisticsStorage.setBossApproved(bossApproved);
			statisticsStorage.setBoss(rsData.getString("boss"));
			statisticsStorage.setFormTitle(rsData.getString("formTitle"));
			
			tmpList.put(statisticsStorage.getResponseId(), statisticsStorage);

		}
		
		conClass.close();
		return tmpList;
	}
	
	public Map<Integer, FormsStatisticsStorage> getChartStatistics(String formId) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "SELECT * "
				+ "FROM formularmanager_forms_response "
				+ "WHERE formularmanager_forms_response.form_id = ? "
				+ "GROUP BY id";
	
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, formId);
		ResultSet rsData = preparedStatement.executeQuery();
		
		Map<Integer, FormsStatisticsStorage> tmpList = new HashMap<>();
		
		while (rsData.next()) {
			
			Integer bossApproved = rsData.getObject("boss_approved") == null ? null : rsData.getInt("boss_approved");
			String createdAt = rsData.getString("created_at").substring(0, rsData.getString("created_at").length() - 2) + "";
			String modifiedAt = rsData.getString("modified_at").substring(0, rsData.getString("modified_at").length() - 2) + "";

			FormsStatisticsStorage statisticsStorage = new FormsStatisticsStorage();
			statisticsStorage.setResponseId(rsData.getInt("id"));
			statisticsStorage.setFormId(rsData.getInt("form_id"));
			statisticsStorage.setValue(rsData.getString("value"));
			statisticsStorage.setUsername(rsData.getString("username"));
			statisticsStorage.setCreatedAt(createdAt);
			statisticsStorage.setModifiedAt(modifiedAt);
			statisticsStorage.setProcessState(rsData.getString("process_state"));
			statisticsStorage.setProcessedBy(rsData.getString("processed_by"));
			statisticsStorage.setBossApproved(bossApproved);
			statisticsStorage.setBoss(rsData.getString("boss"));
			
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
		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, request.getParameter("state"));
		preparedStatement.setString(2, request.getParameter("processedBy"));
		preparedStatement.setString(3, request.getParameter("notes"));
		preparedStatement.setString(4, request.getParameter("responseId"));
		preparedStatement.execute();
	}
	
}
