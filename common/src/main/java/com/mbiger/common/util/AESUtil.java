package com.mbiger.common.util;

import org.apache.commons.net.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;

public class AESUtil {

	/**
	 *加密(GBK)
	 */
	public static String encryptGBK(String input, String key){
		return encrypt(input,key,"GBK");
	}

	/**
	 * 解密(GBK)
	 */
	public static String decryptGBK(String input, String key){
		return decrypt(input,key,"GBK");
	}

	/**
	 *加密
	 */
	public static String encrypt(String input, String key,String charset){
		byte[] crypted = null;
		try{
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			//用GBK解决中文乱码
			crypted = cipher.doFinal(input.getBytes(charset));
		}catch(Exception e){
			e.printStackTrace();
		}
		return new String(Base64.encodeBase64(crypted));
	}


	/**
	 * 解密
	 */
	public static String decrypt(String input, String key,String charset){
		byte[] decrypted = null;
		try{
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skey);
			decrypted = cipher.doFinal(Base64.decodeBase64(input));
		}catch(Exception e){
			e.printStackTrace();
		}
		//用GBK解决中文乱码
		return new String(decrypted, Charset.forName(charset));
	}
}