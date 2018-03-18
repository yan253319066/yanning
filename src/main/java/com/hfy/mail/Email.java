package com.hfy.mail;

import java.io.Serializable;
/**
 * 邮件实体类
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年12月12日 下午6:24:50
 * @version 1.0 
 *
 */
public class Email implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * html格式邮件
	 */
	public static final String EMAIL_HTML = "html";
	/**
	 * text格式邮件
	 */
	public static final String EMAIL_TEXT = "text";
	/**
	 * 附件
	 */
	public static final String EMAIL_ATTACHMENT = "attachment";
	
	/**
	 * 邮件类型
	 */
	private String mailType;
	/**
	 * 接收地址
	 */
	private String[] toAddress;
	/**
	 * 抄送地址
	 */
	private String[] ccAddress;
	/**
	 * 密送地址
	 */
	private String[] bccAddress;
	/**
	 * 主题
	 */
	private String subject;
	/**
	 * 邮件内容
	 */
	private String msg;
	/**
	 * 附件路径
	 */
	private String attachmentPath;
	/**
	 * 附件描述
	 */
	private String attachmentDescription;
	/**
	 * 附件名称
	 */
	private String attachmentName;
	
	/**
	 * debug
	 */
	private boolean debug;
	
	public String[] getToAddress() {
		return toAddress;
	}
	public void setToAddress(String[] toAddress) {
		this.toAddress = toAddress;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getAttachmentPath() {
		return attachmentPath;
	}
	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}
	public String getAttachmentDescription() {
		return attachmentDescription;
	}
	public void setAttachmentDescription(String attachmentDescription) {
		this.attachmentDescription = attachmentDescription;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public String getMailType() {
		return mailType;
	}
	public void setMailType(String mailType) {
		this.mailType = mailType;
	}
	public String[] getBccAddress() {
		return bccAddress;
	}
	public void setBccAddress(String[] bccAddress) {
		this.bccAddress = bccAddress;
	}
	public String[] getCcAddress() {
		return ccAddress;
	}
	public void setCcAddress(String[] ccAddress) {
		this.ccAddress = ccAddress;
	}
	public boolean isDebug() {
		return debug;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
}
