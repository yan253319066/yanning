package com.hfy.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 字符串工具类。
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年10月18日 上午11:41:13
 * @version 1.0 
 *
 */
public abstract class StringUtils {
	/**
	 * 判断指定字符串是否为空。
	 * 
	 * @param str
	 *            待判断的字符串
	 * @return 返回指定字符串是否为空。
	 */
	public static Boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}

	/**
	 * 判断指定字符串是否不为空。
	 * 
	 * @param str
	 *            待判断的字符串
	 * @return 返回指定字符串是否不为空。
	 */
	public static Boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 判断指定字符串是否为空字符串。
	 * 
	 * @param str
	 *            待判断的字符串
	 * @return 返回指定字符串是否为空字符串。
	 */
	public static Boolean isBlank(String str) {
		if (isEmpty(str)) {
			return true;
		}
		for (char c : str.toCharArray()) {
			if (!Character.isWhitespace(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断指定字符串是否不为空字符串。
	 * 
	 * @param str
	 *            待判断的字符串
	 * @return 返回指定字符串是否不为空字符串。
	 */
	public static Boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 截取指定分隔符前的字符串内容。
	 * 
	 * @param str
	 *            待截取的字符串
	 * @param separator
	 *            分隔符
	 * @return 返回指定分隔符前的字符串内容。
	 */
	public static String substringBefore(String str, String separator) {

		int pos = str.indexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	/**
	 * 截取最后一个分隔符前的字符串内容。
	 * 
	 * @param str
	 *            待截取的字符串
	 * @param separator
	 *            分隔符
	 * @return 返回最后一个分隔符前的字符串内容。
	 */
	public static String substringBeforeLast(String str, String separator) {

		int pos = str.lastIndexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	/**
	 * 截取指定分隔符后的字符串内容。
	 * 
	 * @param str
	 *            待截取的字符串
	 * @param separator
	 *            分隔符
	 * @return 返回指定分隔符后的字符串内容。
	 */
	public static String substringAfter(String str, String separator) {

		int pos = str.indexOf(separator);
		if (pos == -1) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	/**
	 * 截取最后一个分隔符后的字符串内容。
	 * 
	 * @param str
	 *            待截取的字符串
	 * @param separator
	 *            分隔符
	 * @return 返回最后一个分隔符后的字符串内容。
	 */
	public static String substringAfterLast(String str, String separator) {

		int pos = str.lastIndexOf(separator);
		if (pos == -1 || pos == (str.length() - separator.length())) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	/**
	 * 截取两个分隔符之间的字符串。
	 * 
	 * @param str
	 *            待截取的字符串
	 * @param startSeparator
	 *            开始分隔符
	 * @param endSeparator
	 *            结束分隔符
	 * @return 返回两个分隔符之间的字符串。
	 */
	public static String substringBetween(String str, String startSeparator,
			String endSeparator) {
		if (str == null || startSeparator == null || endSeparator == null) {
			return null;
		}
		int start = str.indexOf(startSeparator);
		if (start != -1) {
			int end = str
					.indexOf(endSeparator, start + startSeparator.length());
			if (end != -1) {
				return str.substring(start + startSeparator.length(), end);
			}
		}
		return null;
	}

	/**
	 * 截取指定起始位置和截取长度的字符串。
	 * 
	 * @param str
	 *            待截取的字符串
	 * @param pos
	 *            起始位置
	 * @param len
	 *            截取长度
	 * @return 返回指定起始位置和截取长度的字符串。
	 */
	public static String mid(String str, int pos, int len) {

		if (str.length() <= (pos + len)) {
			return str.substring(pos);
		}
		return str.substring(pos, pos + len);
	}

	/**
	 * 将一个字符串数组用指定分隔符串联起来。
	 * 
	 * @param strs
	 *            字符串数组
	 * @param separator
	 *            分隔符
	 * @return 返回串联起来的字符串。
	 */
	public static String join(String[] strs, String separator) {

		StringBuilder builder = new StringBuilder();
		for (String str : strs) {
			builder.append(str + separator);
		}

		String result = builder.toString();
		if (!separator.isEmpty()) {
			result = substringBeforeLast(result, separator);
		}
		return result;
	}
	
	/**
	 * 将一个字符串数组用指定分隔符串联起来并且为每个字符串加上引号。
	 * 
	 * @param strs
	 *            字符串数组
	 * @param separator
	 *            分隔符
	 * @return 返回串联起来的字符串。
	 */
	public static String join(String[] strs, String separator,String contains) {

		StringBuilder builder = new StringBuilder();
		for (String str : strs) {
			builder.append(contains+str+contains + separator);
		}

		String result = builder.toString();
		if (!separator.isEmpty()) {
			result = substringBeforeLast(result, separator);
		}
		return result;
	}

	/**
	 * 将一个字符串列表用指定分隔符串联起来。
	 * 
	 * @param strs
	 *            字符串数组
	 * @param separator
	 *            分隔符
	 * @return 返回串联起来的字符串数组。
	 */
	public static String join(List<String> strs, String separator) {
		return join(strs.toArray(new String[] {}), separator);
	}
	
	/**
	 * 将一个字符串列表用指定分隔符串联起来。
	 * 
	 * @param strs
	 *            字符串数组
	 * @param separator
	 *            分隔符
	 * @return 返回串联起来的字符串数组。
	 */
	public static String join(Integer[] strs, String separator) {
		StringBuilder builder = new StringBuilder();
		for (Integer str : strs) {
			builder.append(str + separator);
		}

		String result = builder.toString();
		if (!separator.isEmpty()) {
			result = substringBeforeLast(result, separator);
		}
		return result;
	}
	
	/**
	 * 将一个字符串列表用指定分隔符串联起来。
	 * 
	 * @param strs
	 *            字符串数组
	 * @param separator
	 *            分隔符
	 * @return 返回串联起来的字符串数组。
	 */
	public static String join(Double[] strs, String separator) {
		StringBuilder builder = new StringBuilder();
		for (Double str : strs) {
			builder.append(str + separator);
		}

		String result = builder.toString();
		if (!separator.isEmpty()) {
			result = substringBeforeLast(result, separator);
		}
		return result;
	}

	/**
	 * 对字符串进行字符集转换。
	 * 
	 * @param str
	 *            字符串
	 * @param origEncoding
	 *            原字符集编码
	 * @param destEncoding
	 *            新字符集编码
	 * @return 返回转换后的字符串。
	 */
	public static String encode(String str, String origEncoding, String destEncoding) {
		try {
			return new String(str.getBytes(origEncoding), destEncoding);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 对字符串转换成整型数组。
	 * 
	 * @param str
	 * 				字符串
	 * @return 返回转换后的整型数组。
	 */
	public static Integer[] convertInteger(String str) {
		String[] arrays = str.split(",");
		int row = 0;
		Integer[] integers = new Integer[arrays.length];
		for(String s : arrays) {
			integers[row] = Integer.parseInt(s);
			row++;
		}
		return integers;
	}
	
	/**
	 * 对字符串转换成字符串数组。
	 * 
	 * @param str
	 * 				字符串
	 * @return 返回转换后的字符串数组。
	 */
	public static List<String> convertString(String str) {
		String[] arrays = str.split(",");
		List<String> strings = new ArrayList<String>(arrays.length);
		for(String s : arrays) {
			strings.add(s);
		}
		return strings;
	}
	
	/**
	 * 对字符串转换成Double数组。
	 * 
	 * @param str
	 * 				字符串
	 * @return 返回转换后的Double数组。
	 */
	public static Double[] convertDouble(String str) {
		String[] arrays = str.split(",");
		int row = 0;
		Double[] doubles = new Double[arrays.length];
		for(String s : arrays) {
			doubles[row] = Double.parseDouble(s);
			row++;
		}
		return doubles;
	}

	/**
	 * 第一个字母大写
	 */
	public static String getFirstCharToUpper(String str){
		return str.substring(0, 1).toUpperCase()+str.substring(1,str.length());
	}
	/**
	 * 第一个字母小写
	 * @author yanning
	 * @date 2016年10月26日 上午10:38:28
	 * @version 1.0
	 * @param str
	 * @return
	 */
	public static String getFirstCharToLower(String str){
		return str.substring(0, 1).toLowerCase()+str.substring(1,str.length());
	}
	/**
	 *通过类名获取bean
	 */
	public static String getBeanNameFormClassName(String className){
		return StringUtils.getFirstCharToLower(className.split("\\.")[className.split("\\.").length-1]);
	}
	public static String getObjectToString(Object obj){
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
	}
	
	public static boolean hasLength(CharSequence str){
		return (str != null) && (str.length() > 0);
	}

	public static boolean hasText(CharSequence str)
	  {
	    if (!hasLength(str)) {
	      return false;
	    }
	    int strLen = str.length();
	    for (int i = 0; i < strLen; i++) {
	      if (!Character.isWhitespace(str.charAt(i))) {
	        return true;
	      }
	    }
	    return false;
	  }
}
