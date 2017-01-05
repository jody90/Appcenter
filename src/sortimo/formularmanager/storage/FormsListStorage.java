package sortimo.formularmanager.storage;

import java.util.Map;

public class FormsListStorage {
	
	public FormsListStorage(String id, String type, String country, String created_at, String modified_at, Map<String, String> formMeta) {
		this.setId(id);
		this.setType(type);
		this.setCountry(country);
		this.setCreatedAt(created_at);
		this.setModifiedAt(modified_at);
		this.setFormMeta(formMeta);
	}
	
	private String id;
	
	private String type;

	private String country;

	private String createdAt;

	private String modifiedAt;
	
	private Map<String, String> formMeta;
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public Map<String, String> getFormMeta() {
		return formMeta;
	}

	public void setFormMeta(Map<String, String> formMeta) {
		this.formMeta = formMeta;
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
		string.append(this.getFormMeta());
		
		return string.toString();
	}

}
