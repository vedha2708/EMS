package com.BisagN.dao;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpSession;

public interface HexatoAsciiDAO {

	public String hexToAscii(String hexStr);
	public  String asciiToHex(String asciiValue);
	public Cipher EncryptionSHA256Algo(HttpSession session,String enckey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException ;
	//public Cipher decrypt(HttpSession session,String enckey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException ;
	
	//public Cipher EncryptionSHA256Algo(HttpSession session,String enckey);
	//public String decrypt(String strToDecrypt, String secret);
	public String decrypt(String strToDecrypt, String enckey,HttpSession session);
	public String getAlphaNumericString();
}
