package com.BisagN.validation;

import java.util.Arrays;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class CheckFileFormatValidation {
	
	public Boolean check_PDF_File(byte[] bytes) throws DecoderException {
		byte[] pdf = Hex.decodeHex("25504446");
		byte[] pdf_file = Arrays.copyOfRange(bytes,0,4);
		if(Arrays.equals(pdf, pdf_file)) {
			return true;
		}else {
			return false;
		}
	}
	public Boolean check_ZIP_File(byte[] bytes) throws DecoderException {
		byte[] pkzip = Hex.decodeHex("504B0304");
		byte[] pkzip_file = Arrays.copyOfRange(bytes,0,4);
		if(Arrays.equals(pkzip, pkzip_file)) {
			return true;
		}else {
			return false;
		}
	}
	
	public Boolean check_RAR_File(byte[] bytes) throws DecoderException {
		byte[] rar4 = Hex.decodeHex("526172211A0700"); // hexa code
		byte[] rar5 = Hex.decodeHex("526172211A070100");
		
		byte[] rar4_file = Arrays.copyOfRange(bytes,0,7);
		byte[] rar5_file =Arrays.copyOfRange(bytes,0,8);
		
		if(Arrays.equals(rar4, rar4_file) || Arrays.equals(rar5, rar5_file)){
			return true;
		}else {
			return false;
		}
	}
	
	public Boolean check_JPEG_File(byte[] bytes) throws DecoderException {
		//byte[] jpeg = Hex.decodeHex("494600");
		byte[] jpeg = Hex.decodeHex("FFD8FFE0");
		byte[] jpeg_file = Arrays.copyOfRange(bytes,0,4);
		//System.out.println("Check JPEG = "+jpeg.toString() + "==" +jpeg_file.toString());
		if(Arrays.equals(jpeg,jpeg_file)) {
			return true;
		}else {
			return false;
		}
	}
	
	public Boolean check_PNG_File(byte[] bytes) throws DecoderException {
		byte[] png = Hex.decodeHex("89504E470D0A1A0A");
		byte[] png_file = Arrays.copyOfRange(bytes,0,8);
		if(Arrays.equals(png,png_file)) {
			return true;
		}else {
			return false;
		}
	}
}
