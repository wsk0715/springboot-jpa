package com.example.springboot_jpa.common.util;

import com.example.springboot_jpa.common.exception.SpringbootJpaException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EncryptUtil {

	@Value("${security.encrypt.hash.algorithm}")
	private String HASH_ALGORITHM;

	@Value("${security.encrypt.aes.algorithm}")
	private String AES_ALGORITHM;

	@Value("${security.encrypt.aes.keySize}")
	private int AES_KEY_SIZE;

	@Value("${security.encrypt.aes.iv}")
	private String IV;


	/**
	 * 단방향 해싱 함수(SHA-256)
	 */
	public String hash(String target) {

		try {
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			byte[] hash = digest.digest(target.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException e) {
			throw new SpringbootJpaException("SHA-256 해싱 실패", e);
		}
	}

	/**
	 * AES 암호화 (Base64 인코딩된 결과 반환)
	 */
	public String encrypt(String plainText, String key) {
		try {
			Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
			SecretKeySpec secretKeySpec = new SecretKeySpec(getAESKey(key), "AES");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

			byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(encryptedBytes);
		} catch (Exception e) {
			throw new SpringbootJpaException("AES 암호화 실패", e);
		}
	}

	/**
	 * AES 복호화 (Base64 디코딩된 결과 반환)
	 */
	public String decrypt(String cipherText, String key) {
		try {
			Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
			SecretKeySpec secretKeySpec = new SecretKeySpec(getAESKey(key), "AES");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

			byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
			byte[] decryptedBytes = cipher.doFinal(decodedBytes);
			return new String(decryptedBytes, StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new SpringbootJpaException("AES 복호화 실패", e);
		}
	}

	/**
	 * 256비트 AES 키 생성
	 */
	private byte[] getAESKey(String key) {
		try {
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			byte[] keyBytes = digest.digest(key.getBytes(StandardCharsets.UTF_8));
			return keyBytes;
		} catch (NoSuchAlgorithmException e) {
			throw new SpringbootJpaException("AES 키 생성 실패", e);
		}
	}

	/**
	 * 새로운 AES 키 생성 (랜덤)
	 */
	public String generateAESKey() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(AES_KEY_SIZE);
			SecretKey secretKey = keyGenerator.generateKey();
			return Base64.getEncoder().encodeToString(secretKey.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			throw new SpringbootJpaException("AES 키 생성 실패", e);
		}
	}

}
