package sortimo.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HelperFunctions {
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
}
