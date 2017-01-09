package sortimo.formularmanager.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import sortimo.databaseoperations.Connect;
import sortimo.formularmanager.storage.FormsStatisticsStorage;

public class Todo {
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;

	public Map<Integer, FormsStatisticsStorage> getForms(String userRoles) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "SELECT formularmanager_forms_response.* "
				+ "FROM formularmanager_forms_response "
				+ "LEFT JOIN formularmanager_forms_meta "
				+ "ON formularmanager_forms_response.form_id = formularmanager_forms_meta.form_id "
				+ "WHERE meta_value IN ("+userRoles+") "
				+ "AND NOT process_state = 'rejected' "
				+ "AND NOT process_state = 'finished' "
				+ "AND boss_approved = 1 "
				+ "GROUP BY formularmanager_forms_response.id";
		
		preparedStatement = connect.prepareStatement(sql);
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
			
			sql = "SELECT "
			+ "MAX(CASE WHEN meta_name = 'evaluationType' THEN meta_value END) as evaluationType, "
			+ "MAX(CASE WHEN meta_name = 'formTitle' THEN meta_value END) as formTitle "
			+ "FROM formularmanager_forms_meta "
			+ "WHERE form_id = ?";
			
			preparedStatement = connect.prepareStatement(sql);
			preparedStatement.setInt(1, rsData.getInt("form_id"));
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				bossFormsStorage.setEvaluationType(rs.getString("evaluationType"));
				bossFormsStorage.setFormTitle(rs.getString("formTitle"));
			}
			
			if (bossFormsStorage.getEvaluationType().equals("chart")) {
				bossFormsStorage = null;
			}
			
			tmpList.put(rsData.getInt("id"), bossFormsStorage);

		}
		
		conClass.close();
		return tmpList;
	}

}
