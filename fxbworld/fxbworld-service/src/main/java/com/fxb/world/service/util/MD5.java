package com.fxb.world.service.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	
	
	public static String getMD5(String inputStr) {
		String algorithm = "";
		if(inputStr==null){
			return null;
		}
		try{
			algorithm=System.getProperty("MD5.algorithm", "MD5");
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		MessageDigest md=null;
		try{
			md=MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			//logger.error(e.getMessage());
		}
		
		//inputStr = "tsh"+inputStr;
		
		byte buffer[] = inputStr.getBytes();
		
		md.update(buffer);
		
		byte[] bDigest=md.digest();
		
		StringBuffer md5StrBuff = new StringBuffer();  
        for (int i = 0; i < bDigest.length; i++) {
            if (Integer.toHexString(0xFF & bDigest[i]).length() == 1)  
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & bDigest[i]));  
            else  
                md5StrBuff.append(Integer.toHexString(0xFF & bDigest[i]));  
        }  
  
        return md5StrBuff.toString();

	}
	
	
}
