package com.example.springboot_jpa.common.encryption;

import com.example.springboot_jpa.common.exception.SpringbootJpaException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashEncryptUtil {

	private static final String HASH_ALGORITHM = "SHA256";

	/**
	 * 단방향 해싱 함수(SHA-256)
	 */
	public static String hash(String target) {
		try {
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			byte[] hash = digest.digest(target.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException e) {
			throw new SpringbootJpaException(HASH_ALGORITHM + " 해싱 실패", e);
		}
	}

}
