package com.hfy.enums;
/**
 * 附件类型枚举
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年12月5日 下午4:30:15
 * @version 1.0 
 *
 */
public enum AttaType {
	TOU_XIANG("头像", "touxiang");
	// 成员变量
	private String text;
	private String id;

	// 普通方法
	public static String getName(String id) {
		for (AttaType s : AttaType.values()) {
			if (s.getId().equals(id)) {
				return s.getText();
			}
		}
		return null;
	}

	// 构造方法
	private AttaType(String text, String id) {
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
