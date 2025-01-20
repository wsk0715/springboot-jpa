package com.example.springboot_jpa.common.encryption;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptEncryptUtil {

	private static final int SALT_STRENGTH = 10;


	public static String encode(String plainText) {
		String salt = BCrypt.gensalt(SALT_STRENGTH);
		return BCrypt.hashpw(plainText, salt);
	}

	public static boolean matches(String plainText, String hashedText) {
		return BCrypt.checkpw(plainText, hashedText);
	}

}
