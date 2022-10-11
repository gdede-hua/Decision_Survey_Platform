package com.hua.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.beans.factory.annotation.Value;
/**
 * encrypt the user information
 */
@Converter
public class StringConverter implements AttributeConverter<String, String> {
	
	@Value("${security.dataBase.secretKey}")
	private String secretKey;
	
	@Override
	public String convertToDatabaseColumn(String attribute) {
		return new AesEncryptor(secretKey).encrypt(attribute);
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		return new AesEncryptor(secretKey).decrypt(dbData);
	}

}