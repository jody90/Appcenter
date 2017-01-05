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
	
	/**
	 * Speichert Formulareingaben in der Datenbank
	 * 
	 * @param responseData Formulareingaben
	 * @param globalData automatisch gesamelte Nutzerdaten
	 * @return boolean
	 * @throws Exception
	 */
	public boolean insertFormResponse(Map<String, String> responseData, Map<String, String> globalData) throws Exception {
		Connect conClass = new Connect();
		connect = conClass.getConnection();
		
		System.out.println(responseData);
		System.out.println(globalData);
		
		System.out.println(responseData.get("bossApproved"));
		
		String sql = "INSERT INTO "
				+ "formularmanager_forms_response "
				+ "values (default, ?, ?, ?, default, default, default, default, default, ?, ?)";
		
		responseData.remove("action");
		responseData.remove("form_id");
		
		Gson gson = new Gson(); 
		String json = gson.toJson(responseData);

		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, globalData.get("formId"));
		preparedStatement.setString(2, json);
		preparedStatement.setString(3, globalData.get("username"));
		preparedStatement.setString(4, responseData.get("boss"));
		preparedStatement.setString(5, responseData.get("bossApproved"));
		preparedStatement.executeUpdate();
		
		writeDatabaseResponse = true;
		conClass.close();
		return writeDatabaseResponse;
	}
}
