package sortimo.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class HelperFunctions {
	
	private String username;
	
	public String concat(String string, String country) {
		String concatenated = string + country;
		return concatenated;
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
	
	public boolean checkCookie(HttpServletRequest request) {
		// liest alle Cookies in cookies ein
		Cookie[] cookies = null;
		cookies = request.getCookies();

		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("FormularManagerUser")) {
					this.setUsername(cookies[i].getValue());
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
	
}
