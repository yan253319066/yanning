package com.hfy.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志类型枚举
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年11月13日 下午6:18:56
 * @version 1.0 
 *
 */
public enum LogType {
	RECORD("record", "1"), BUSINESS("business", "2"), EXCEPTION("exception", "3");
	// 成员变量
	private String text;
	private String id;
	
	private static final Map<String, String> logTypeMap = new HashMap<String, String>();  
    static {  
    	for (LogType s : LogType.values()) {  
    		logTypeMap.put(s.getId(), s.getText());  
        }  
    }  

	public static Map<String, String> getLogtypemap() {
		return logTypeMap;
	}

	// 普通方法
	public static String getName(String id) {
		for (LogType s : LogType.values()) {
			if (s.getId() == id) {
				return s.getText();
			}
		}
		return null;
	}

	// 构造方法
	private LogType(String text, String id) {
		this.setText(text);
		this.setId(id);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
