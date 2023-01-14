package com.hua.util;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.hua.model.Users;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.util.Base64;
/**
 * Service interface for symmetric encryption of the user information.
 * @author      John Nikolaou
 */
public class AesEncryptor implements TextEncryptor {

	/**
	 * The ALGO it is the name of the encryption algorithm
	 */
	private static final String ALGO = "AES";

	/**
	 * The secret it is the secret used for the encryption of the data
	 */
	private String secret;

	/**
	 * Class constructor.
	 * @param secret
	 */
	public AesEncryptor(String secret) {
		this.secret = secret;
	}

	/**
	 * Encrypt the raw text string.
	 * @param data
	 */
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
	/**
	 * Decrypt the encrypted text string.
	 * @param encryptedData
	 */
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
