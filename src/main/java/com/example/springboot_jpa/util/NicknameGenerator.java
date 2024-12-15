package com.example.springboot_jpa.util;

import java.security.SecureRandom;

public class NicknameGenerator {

	private static final int NUMBER_MAX_LENGTH = 20;


	public static String generateRandomNickname(String prefix) {
		StringBuilder sb = new StringBuilder(prefix).append("-");
		int l = sb.length();

		SecureRandom random = new SecureRandom();
		for (int i = l; i < NUMBER_MAX_LENGTH; i++) {
			int digit = random.nextInt(10);
			sb.append(digit);
		}

		return sb.toString();
	}

}
