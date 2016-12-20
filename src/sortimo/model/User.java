package sortimo.model;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sortimo.databaseoperations.UserDb;
import sortimo.storage.RightsStorage;

public class User {
	private String username;
	
	private String firstname;
	
	private String password;
	
	private String lastname;
	
	private String email;
	
	private String roles;

	private List<RightsStorage> rights;
		
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	/**
	 * 
	 * @param username Benutzername
	 */
	public void getUserAccount(String username) {
		UserDb userDb = new UserDb();
		try {
			System.out.println("DB UserAccount request");
			ResultSet rs = userDb.getUserAccount(username);
			String tmpRights = null;
			while (rs.next()) {
				this.setUsername(rs.getString("username"));
				this.setFirstname(rs.getString("firstname"));
				this.setLastname(rs.getString("lastname"));
				this.setPassword(rs.getString("password"));
				this.setEmail(rs.getString("email"));
				this.setRoles(rs.getString("roles"));
				tmpRights = rs.getString("rights");
			}
			
			rs = null;
			
			List<RightsStorage> rights = new ArrayList<RightsStorage>();
			
			String allRightsString = userDb.getRightsFromRoles(this.getRoles());
			String[] tmpRightsFromRoles = allRightsString != null ? allRightsString.split(",") : null;
			String[] tmpRightsArray = tmpRights != null ? tmpRights.split(",") : null;

			List<String> tmpAllRights = null;
			if (tmpRightsFromRoles != null) {
				tmpAllRights = new ArrayList<String>(Arrays.asList(tmpRightsFromRoles));
			}
			
			if (tmpRightsArray != null) {
				for (int i = 0; i < tmpRightsArray.length; i++) {
					if (!Arrays.asList(tmpAllRights).contains(tmpRightsArray[i])) {
						tmpAllRights.add(tmpRightsArray[i]);
						allRightsString += "," + tmpRightsArray[i];
					}
				}
			}
			
			ResultSet rightsList = userDb.getRights(allRightsString);
			
			while (rightsList.next()) {
				RightsStorage userRights = new RightsStorage();
				userRights.setId(rightsList.getInt("id"));
				userRights.setName(rightsList.getString("name"));
				userRights.setDescription(rightsList.getString("description"));
				rights.add(userRights);
			}
			
			this.setRights(rights);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param inputPassword eingegebenes passwort
	 * @param username benutzername
	 * @return boolean
	 */
	public boolean login(String inputPassword, String username) {
		
		this.getUserAccount(username);
		String inputPasswordHash = null;
		
		// Eingegebenes Passwort md5Hashen
		try {
			HelperFunctions helper = new HelperFunctions();
			inputPasswordHash = helper.md5Hash(inputPassword).trim();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		// eingegebenes Passwort und Passwort aus DB vergleichen
		if (inputPasswordHash != null && this.getPassword() != null && this.getPassword().equals(inputPasswordHash)) {
			this.writeUserCookie();
			System.out.println("passwort ist richtig");
			return true;
		}
		else {
			System.err.println("passwort ist FALSCH");
			return false;
		}
	}
	
	private void writeUserCookie() {		
		Cookie userCookie = new Cookie("FormularManagerUser", this.getUsername());
		// Cookie fuer 30Tage setzen
		userCookie.setMaxAge(2592000);
		this.getResponse().addCookie(userCookie);
	}
	
	/**
	 * 
	 * @param cookies
	 * @return boolean
	 */
	public boolean logout(Cookie[] cookies) {
		Cookie cookie = null;
		this.setRights(null);
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
		string.append(";");
		string.append(this.getRights());
		string.append(";");
		string.append(this.getRoles());
		string.append(";");
		string.append(this.getPassword());
		
		return string.toString();
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

	public List<RightsStorage> getRights() {
		return rights;
	}

	public void setRights(List<RightsStorage> rights) {
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
