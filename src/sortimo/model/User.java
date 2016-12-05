package sortimo.model;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sortimo.databaseoperations.UserDb;

public class User {
	private String username;
	
	private String firstname;
	
	private String password;
	
	private String lastname;
	
	private String email;
	
	private String roles;
	
	private String rights;
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	public void getUserAccount(String username) {
		UserDb userDb = new UserDb();
		try {
			System.out.println("DB UserAccount request");
			ResultSet rs = userDb.getUserAccount(username);
			while (rs.next()) {
				this.setUsername(rs.getString("username"));
				this.setFirstname(rs.getString("firstname"));
				this.setLastname(rs.getString("lastname"));
				this.setPassword(rs.getString("password"));
				this.setEmail(rs.getString("email"));
				this.setRoles(rs.getString("roles"));
				this.setRights(rs.getString("rights"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean login(String inputPassword) {
		
		String password = "false";
		String inputPasswordHash = null;
		
		// Eingegebenes Passwort md5Hashen
		try {
			HelperFunctions helper = new HelperFunctions();
			inputPasswordHash = helper.md5Hash(inputPassword).trim();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// eingegebenes Passwort und Passwort aus DB vergleichen
		if (inputPasswordHash != null && password.equals(inputPasswordHash)) {
			this.writeUserCookie();
			System.out.println("passwort ist richtig");
			return true;
		}
		else {
			System.out.println("passwort ist FALSCH");
			return false;
		}
	}
	
	private void writeUserCookie() {		
		Cookie userCookie = new Cookie("FormularManagerUser", this.getUsername());
		// Cookie fuer 30Tage setzen
		userCookie.setMaxAge(2592000);
		this.getResponse().addCookie(userCookie);
	}
	
	public boolean logout(Cookie[] cookies) {
		Cookie cookie = null;
		
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().equals("FormularManagerUser")) {
					cookie.setMaxAge(0);
					this.getResponse().addCookie(cookie);
					return true;
				}
			}
		}
		return false;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
}
