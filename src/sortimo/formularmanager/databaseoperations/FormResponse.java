package sortimo.formularmanager.databaseoperations;

import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Map;
import com.google.gson.*;

import sortimo.databaseoperations.Connect;

public class FormResponse {
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	private boolean writeDatabaseResponse = false;
	
	public boolean insertFormResponse(Map<String, String> responseData, Map<String, String> globalData) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		String sql = "INSERT INTO formular_manager.forms_response values (default, ?, ?, ?, default)";
		
		responseData.remove("action");
		responseData.remove("form_id");
		
		Gson gson = new Gson(); 
		String json = gson.toJson(responseData);

		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, globalData.get("formId"));
		preparedStatement.setString(2, json);
		preparedStatement.setInt(3, Integer.parseInt(globalData.get("userId")));
		preparedStatement.executeUpdate();
		
		writeDatabaseResponse = true;
		return writeDatabaseResponse;
	}
}
