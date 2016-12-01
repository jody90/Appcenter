package sortimo.formularmanager.storage;

import java.util.HashMap;
import java.util.Map;

public class FromsStatisticsStorage {

	private int formId;
	
	private String jsonForm;

	private String htmlForm;

	private String formTitle;
	
	private String username;
	
	private String processState;

	private String processedBy;

	private Map<Integer, Map<String, String>> statisticsValue;

	private Map<Integer, Map<String, String>> statisticsValueHelper = new HashMap<Integer, Map<String, String>>();

	public Map<Integer, Map<String, String>> getStatisticsValue() {
		return statisticsValue;
	}

	public void setStatisticsValue(String value, String createdAt, int id, String username, String processState, String processedBy) {
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("value", value);
		map.put("createdAt", createdAt);
		map.put("username", username);	
		map.put("processState", processState);	
		map.put("processedBy", processedBy);	
		
		statisticsValueHelper.put(id, map);
		this.statisticsValue = statisticsValueHelper;
	}

	public int getFormId() {
		return formId;
	}

	public void setFormId(int i) {
		this.formId = i;
	}
	
	public String getJsonForm() {
		return jsonForm;
	}

	public void setJsonForm(String jsonForm) {
		this.jsonForm = jsonForm;
	}
	
	public String getFormTitle() {
		return formTitle;
	}
	
	public void setFormTitle(String formTitle) {
		this.formTitle = formTitle;
	}

	public String getHtmlForm() {
		return htmlForm;
	}

	public void setHtmlForm(String htmlForm) {
		this.htmlForm = htmlForm;
	

//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public String getProcessState() {
//		return processState;
//	}
//
//	public void setProcessState(String processState) {
//		this.processState = processState;
//	}
//
//	public String getProcessedBy() {
//		return processedBy;
//	}
//
//	public void setProcessedBy(String processedBy) {
//		this.processedBy = processedBy;
//	}}
}}
