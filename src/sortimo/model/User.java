package sortimo.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sortimo.databaseoperations.UserDb;

public class User {
	
	private String username;
	
	private String password;
	
	private HttpServletResponse servletResponse;
	
	private HttpServletRequest servletRequest;
	
	private Map<String, String> userInfo;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean login() {
		
		UserDb userDb = new UserDb();
		String password = "false";
		String inputPassword = "false";
		
		// Daten von DB abfragen
		try {
			password = userDb.getUserPassword(this.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Eingegebenes Passwort md5Hashen
		try {
			inputPassword = this.md5Hash(this.getPassword()).trim();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// eingegebenes Passwort und Passwort aus DB vergleichen
		if (password.equals(inputPassword)) {
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
		HttpServletResponse response = this.getServletResponse();
		
		Cookie userCookie = new Cookie("FormularManagerUser", this.getUsername());
		// Cookie fuer 30Tage setzen
		userCookie.setMaxAge(2592000);
		response.addCookie(userCookie);
	}
	
	public String md5Hash(String password) throws NoSuchAlgorithmException {
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.reset();
		m.update(password.getBytes());
		byte[] digest = m.digest();
		BigInteger bigInt = new BigInteger(1,digest);
		String hashtext = bigInt.toString(16);
		while(hashtext.length() < 32 ){
			hashtext = "0"+hashtext;
		}
		
		return hashtext;
	}
	
	public boolean logout(Cookie[] cookies) {
		Cookie cookie = null;
		
		if (cookies != null) {
			HttpServletResponse response = this.getServletResponse();

			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().equals("FormularManagerUser")) {
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					return true;
				}
			}
		}
		
		return false;
	}
		
	public String isLoggedIn(Cookie[] cookies) {
		
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("FormularManagerUser")) {
					this.setUsername(cookies[i].getValue());
					return cookies[i].getValue();
				}
			}
		}
		
		return null;
	}

	public Map<String, String> getUserInfo() {
		if (userInfo == null) {
			UserDb userDb = new UserDb();
			try {
				Map<String, String> userInfo = userDb.getUserAccount(this.getUsername());
				return userInfo;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return userInfo;
	}

	public void setUserInfo(Map<String, String> userInfo) {
		this.userInfo = userInfo;
	}

	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}
}
