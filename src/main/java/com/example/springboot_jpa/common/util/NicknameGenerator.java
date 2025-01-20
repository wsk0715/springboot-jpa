package com.example.springboot_jpa.common.util;

import com.example.springboot_jpa.oauth.constants.OAuthProvider;
import java.security.SecureRandom;

public class NicknameGenerator {

	private static final int NUMBER_MAX_LENGTH = 20;


	public static String generateRandom(String prefix) {
		StringBuilder sb = new StringBuilder(prefix + "_");
		int l = sb.length();

		SecureRandom random = new SecureRandom();
		for (int i = l; i < NUMBER_MAX_LENGTH; i++) {
			int digit = random.nextInt(10);
			sb.append(digit);
		}

		return sb.toString();
	}

	public static String generateRandom(OAuthProvider provider) {
		String prefix = provider.getCode();
		return generateRandom(prefix);
	}

}
