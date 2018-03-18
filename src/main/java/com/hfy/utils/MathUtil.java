package com.hfy.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MathUtil {
	private static volatile MathUtil mathUtil = null;

	  public static MathUtil getInstance()
	  {
	    if (mathUtil == null) {
	      synchronized (MathUtil.class) {
	        if (mathUtil == null) {
	          mathUtil = new MathUtil();
	        }
	      }
	    }
	    return mathUtil;
	  }

	  public static String encryptPassWordSHA(String clearlyPasssword)
	  {
	    try {
	      MessageDigest sha = MessageDigest.getInstance("SHA");
	      sha.update(clearlyPasssword.getBytes());
	      return byte2hex(sha.digest());
	    } catch (NoSuchAlgorithmException e) {
	      e.printStackTrace();
	    }
	    return null;
	  }

	  public static String byte2hex(byte[] bytes)
	  {
	    StringBuilder result = new StringBuilder();
	    String temp = null;
	    for (int i = 0; i < bytes.length; i++) {
	      temp = Integer.toHexString(bytes[i] & 0xFF);
	      if (temp.length() == 1) {
	        result.append("0");
	      }
	      result.append(temp);
	    }
	    return result.toString();
	  }
}
