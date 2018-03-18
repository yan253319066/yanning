package com.hfy.cache;

import java.io.Serializable;
import java.util.Map;
/**
 * 系统缓存
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年12月19日 上午11:57:27
 * @version 1.0 
 *
 */
public class Cache implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 基础配置缓存
	 */
	private static Map<String,String> configMap;

	public static synchronized void setConfigMap(Map<String, String> configMap) {
		Cache.configMap = configMap;
	}

	public static synchronized String getConfigValue(String key){
		String value = null;
		if (configMap!=null && configMap.containsKey(key)){
			value = configMap.get(key);
		}
		return value;
	}
	public static synchronized void saveConfig(String key, String value){
		if (configMap != null) {
			configMap.put(key, value);
		}
	}
	public static synchronized void delConfig(String key){
		if (configMap != null) {
			configMap.remove(key);
		}
	}
}
