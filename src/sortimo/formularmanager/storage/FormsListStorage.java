package sortimo.formularmanager.storage;

public class FormsListStorage {
		
	private String id;
	
	private String type;

	private String country;

	private String createdAt;

	private String modifiedAt;
	
	private String formTitle;
	
	private String validFrom;
	
	private String validTo;
	
	private String evaluationType;

	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	
	public String getFormTitle() {
		return formTitle;
	}

	public void setFormTitle(String formTitle) {
		this.formTitle = formTitle;
	}

	public String getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public String getValidTo() {
		return validTo;
	}

	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}

	public String getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(String evaluationType) {
		this.evaluationType = evaluationType;
	}
	
	@Override
	public String toString() {
		
		StringBuilder string = new StringBuilder();
		string.append(this.getId());
		string.append(";");
		string.append(this.getType());
		string.append(";");
		string.append(this.getCountry());
		string.append(";");
		string.append(this.getCreatedAt());
		string.append(";");
		string.append(this.getModifiedAt());
		string.append(";");
		string.append(this.getFormTitle());
		string.append(";");
		string.append(this.getValidFrom());
		string.append(";");
		string.append(this.getValidTo());
		string.append(";");
		string.append(this.getEvaluationType());
		
		return string.toString();
	}

}
