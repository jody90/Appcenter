package sortimo.formularmanager.storage;

public class FormsStatisticsStorage {

	private int responseId;
	
	private int formId;
	
	private String value;
	
	private String username;
	
	private String createdAt;
	
	private String modifiedAt;
	
	private String processState;
	
	private String processedBy;
	
	private String notes;
	
	private String boss;
	
	private Integer bossApproved;
	
	private String jsonForm;

	private String htmlForm;

	private String formTitle;
	
	private String evaluationType;

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
	}
	
	public String getEvaluationType() {
		return evaluationType;
	}
	
	public void setEvaluationType(String evaluationType) {
		this.evaluationType = evaluationType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(String modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProcessState() {
		return processState;
	}

	public void setProcessState(String processState) {
		this.processState = processState;
	}

	public String getProcessedBy() {
		return processedBy;
	}

	public void setProcessedBy(String processedBy) {
		this.processedBy = processedBy;
	}

	public int getResponseId() {
		return responseId;
	}

	public void setResponseId(int responseId) {
		this.responseId = responseId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getBoss() {
		return boss;
	}

	public void setBoss(String boss) {
		this.boss = boss;
	}

	public Integer getBossApproved() {
		return bossApproved;
	}

	public void setBossApproved(Integer bossApproved) {
		this.bossApproved = bossApproved;
	}

	@Override
	public String toString() {
		
		StringBuilder string = new StringBuilder();
		string.append(this.getResponseId());
		string.append(";");
		string.append(this.getFormId());
		string.append(";");
		string.append(this.getValue());
		string.append(";");
		string.append(this.getUsername());
		string.append(";");
		string.append(this.getCreatedAt());
		string.append(";");
		string.append(this.getModifiedAt());
		string.append(";");
		string.append(this.getProcessState());
		string.append(";");
		string.append(this.getProcessedBy());
		string.append(";");
		string.append(this.getNotes());
		string.append(";");
		string.append(this.getBossApproved());
		string.append(";");
		string.append(this.getBoss());
		string.append(";");
		string.append(this.getJsonForm());
		string.append(";");
		string.append(this.getHtmlForm());
		string.append(";");
		string.append(this.getFormTitle());
		string.append(";");
		string.append(this.getEvaluationType());
		
		return string.toString();
	}

}
