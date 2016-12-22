package sortimo.storage;

public class BossStorage {

	private String username;
	
	private String firstname;
	
	private String lastname;
	
	private String email;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		
		string.append(this.getUsername());
		string.append(";");
		string.append(this.getFirstname());
		string.append(";");
		string.append(this.getLastname());
		string.append(";");
		string.append(this.getEmail());

		return string.toString();
	}
}
