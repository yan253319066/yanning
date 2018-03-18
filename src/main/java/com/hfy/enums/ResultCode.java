package com.hfy.enums;
/**
 * 返回结果code枚举
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年10月18日 下午2:48:38
 * @version 1.0 
 *
 */
public enum ResultCode {
	SUCCESS("成功", "200"), FAIL("失败", "300"), ERROR("错误", "400");
	// 成员变量
	private String text;
	private String id;

	// 普通方法
	public static String getName(String id) {
		for (ResultCode s : ResultCode.values()) {
			if (s.getId() == id) {
				return s.getText();
			}
		}
		return null;
	}

	// 构造方法
	private ResultCode(String text, String id) {
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
