package com.hua.util;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.util.Base64;

public class AesEncryptor implements TextEncryptor {
	
	private static final String ALGO = "AES";
//	private static final byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't',
//			'K', 'e', 'y' };

	private String secret;
	public AesEncryptor(String secret) {
		this.secret = secret;
	}


	@Override
	public String encrypt(String data) {
		try {
			Key key = generateKey();
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.ENCRYPT_MODE, key);
			byte[] encVal = c.doFinal(data.getBytes());
			return Base64.getEncoder().encodeToString(encVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String decrypt(String encryptedData) {
		try {
			Key key = generateKey();
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.DECRYPT_MODE, key);
			byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
			byte[] decValue = c.doFinal(decordedValue);
			return new String(decValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Generate a new encryption key.
	 */
	private Key generateKey() throws Exception {
		byte[] keyValue = secret.getBytes();
		return new SecretKeySpec(keyValue, ALGO);
	}
}
