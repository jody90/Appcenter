package sortimo.formularmanager.databaseoperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import sortimo.databaseoperations.Connect;
import sortimo.formularmanager.storage.FormsStatisticsStorage;

public class Boss {
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	
	
	/**
	 * Holt alle Formulare die vom jeweiligen Boss approved werden muessen
	 * 
	 * @param boss
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, FormsStatisticsStorage> getForms(String boss) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "SELECT "
				+ "id, "
				+ "formularmanager_forms_response.form_id, "
				+ "value, "
				+ "username, "
				+ "formularmanager_forms_response.created_at, "
				+ "formularmanager_forms_response.modified_at, "
				+ "process_state, "
				+ "processed_by, "
				+ "notes, "
				+ "boss_approved, "
				+ "boss, "
				+ "MAX(CASE WHEN meta_name = 'evaluationType' THEN meta_value END) as evaluationType "
				+ "FROM formularmanager_forms_response "
				+ "LEFT JOIN formularmanager_forms_meta "
				+ "ON formularmanager_forms_response.form_id = formularmanager_forms_meta.form_id "
				+ "WHERE boss = ? "
				+ "AND boss_approved IS NULL";
		
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, boss);
		ResultSet rsData = preparedStatement.executeQuery();
		
		Map<Integer, FormsStatisticsStorage> tmpList = new HashMap<>();
		
		while (rsData.next()) {

			Integer bossApproved = rsData.getObject("boss_approved") == null ? null : rsData.getInt("boss_approved");
			String createdAt = rsData.getString("created_at").substring(0, rsData.getString("created_at").length() - 2) + "";
			String modifiedAt = rsData.getString("modified_at").substring(0, rsData.getString("modified_at").length() - 2) + "";
			
			FormsStatisticsStorage bossFormsStorage = new FormsStatisticsStorage();
			bossFormsStorage.setResponseId(rsData.getInt("id"));
			bossFormsStorage.setFormId(rsData.getInt("form_id"));
			bossFormsStorage.setValue(rsData.getString("value"));
			bossFormsStorage.setUsername(rsData.getString("username"));
			bossFormsStorage.setCreatedAt(createdAt);
			bossFormsStorage.setModifiedAt(modifiedAt);
			bossFormsStorage.setProcessState(rsData.getString("process_state"));
			bossFormsStorage.setProcessedBy(rsData.getString("processed_by"));
			bossFormsStorage.setNotes(rsData.getString("notes"));
			bossFormsStorage.setBossApproved(bossApproved);
			bossFormsStorage.setBoss(rsData.getString("boss"));
			bossFormsStorage.setEvaluationType(rsData.getString("evaluationType"));
			
			tmpList.put(rsData.getInt("id"), bossFormsStorage);

		}
		
		conClass.close();
		return tmpList;
	}
	
}
