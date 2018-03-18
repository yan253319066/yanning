package com.hfy.mail;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hfy.cache.Cache;
import com.hfy.utils.DESUtils;

/**
 * 发送邮件
 ******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途 复制、修改或发布本软件. Copyright (C)
 * 2016 ShenZhen HFY Co.,Ltd All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年12月12日 下午6:41:28
 * @version 1.0
 *
 */
public class EmailUtils {
	private static Logger logger = LoggerFactory.getLogger(EmailUtils.class);

	private static String host;
	private static String username;
	private static String password;
	private static String fromname;
	private static String fromaddress;
	private static String mailSwitch;

	static {
		Properties prop = new Properties();
		try {
			InputStream in = EmailUtils.class.getResourceAsStream("/email.properties");
			prop.load(in);
			host = prop.getProperty("mail.smtp.host").trim();
			username = DESUtils.getDecryptString(prop.getProperty("mail.username")).trim();
			password = DESUtils.getDecryptString(prop.getProperty("mail.password")).trim();
			fromaddress = DESUtils.getDecryptString(prop.getProperty("mail.fromaddress")).trim();
			fromname = prop.getProperty("mail.fromname").trim();
			mailSwitch = Cache.getConfigValue("mail.switch").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @author yanning
	 * @date 2016年12月12日 下午6:41:43
	 * @version 1.0
	 * @param email
	 * @throws EmailException
	 * @throws UnsupportedEncodingException
	 */
	public static void sendEmail(Email email) throws EmailException, UnsupportedEncodingException {
		if (StringUtils.isBlank(mailSwitch)) {
			logger.debug("email switch is null...");
			return;
		}
		if (mailSwitch.equalsIgnoreCase("off")) {
			logger.debug("Sending mail is closed...");
			return;
		}
		if (email == null) {
			throw new RuntimeException("Email entity is null...");
		}
		if (email.getToAddress() == null || email.getToAddress().length < 0) {
			throw new RuntimeException("Email toAddress is null...");
		}
		if (email.getMailType() == null)
			sendHtmlEmail(email);
		else if (email.getMailType().equals(Email.EMAIL_TEXT))
			sendTextEmail(email);
		else if (email.getMailType().equals(Email.EMAIL_TEXT))
			sendMultiPartEmail(email);
		else
			sendHtmlEmail(email);
	}

	/**
	 * 文本格式发送邮件
	 * 
	 * @author yanning
	 * @date 2016年12月12日 下午6:41:56
	 * @version 1.0
	 * @param mail
	 * @throws EmailException
	 */
	private static void sendTextEmail(Email mail) throws EmailException {
		logger.debug("start send text email...");
		SimpleEmail email = new SimpleEmail();
		email.setDebug(mail.isDebug());
		email.setHostName(host);// 设置SMTP服务器
		email.setAuthentication(username, password);// 如果你的邮件服务器设置了密码，请输入密码，否则此语句可以忽略
		email.setCharset("utf-8");
		email.setFrom(fromaddress, fromname, "utf-8");// 设置发件人信息
		email.addTo(mail.getToAddress());// 设置收件人信息
		if (mail.getCcAddress() != null && mail.getCcAddress().length > 0) {
			email.addCc(mail.getCcAddress());
		}
		if (mail.getBccAddress() != null && mail.getBccAddress().length > 0) {
			email.addBcc(mail.getBccAddress());
		}
		email.setSubject(mail.getSubject());// 设置主题
		email.setMsg(mail.getMsg());// 设置邮件内容
		email.send();// 发送邮件
		logger.debug("end send text email...");
	}

	/**
	 * html格式发送邮件
	 * 
	 * @author yanning
	 * @date 2016年12月12日 下午6:42:09
	 * @version 1.0
	 * @param mail
	 * @throws EmailException
	 */
	private static void sendHtmlEmail(Email mail) throws EmailException {
		logger.debug("start send html email...");
		HtmlEmail email = new HtmlEmail();
		email.setDebug(mail.isDebug());
		email.setHostName(host);
		email.setAuthentication(username, password);
		email.setCharset("utf-8");
		email.addTo(mail.getToAddress());
		if (mail.getCcAddress() != null && mail.getCcAddress().length > 0) {
			email.addCc(mail.getCcAddress());
		}
		if (mail.getBccAddress() != null && mail.getBccAddress().length > 0) {
			email.addBcc(mail.getBccAddress());
		}
		email.setFrom(fromaddress, fromname, "utf-8");
		email.setSubject(mail.getSubject());
		// 设置html内容，实际使用时可以从文本读入写好的html代码
		email.setHtmlMsg(mail.getMsg());
		email.send();
		logger.debug("end send html email...");
	}

	/**
	 * 带附件的发送邮件
	 * 
	 * @author yanning
	 * @date 2016年12月12日 下午6:42:21
	 * @version 1.0
	 * @param mail
	 * @throws EmailException
	 * @throws UnsupportedEncodingException
	 */
	private static void sendMultiPartEmail(Email mail) throws EmailException, UnsupportedEncodingException {
		logger.debug("start send attachment email...");
		MultiPartEmail email = null;
		email = new MultiPartEmail();
		email.setDebug(mail.isDebug()); 
		email.setHostName(host);
		email.setAuthentication(username, password);
		email.setCharset("utf-8");
		email.addTo(mail.getToAddress());
		if (mail.getCcAddress() != null && mail.getCcAddress().length > 0) {
			email.addCc(mail.getCcAddress());
		}
		if (mail.getBccAddress() != null && mail.getBccAddress().length > 0) {
			email.addBcc(mail.getBccAddress());
		}
		email.setFrom(fromaddress, fromname, "utf-8");
		email.setSubject(mail.getSubject());
		email.setMsg(mail.getMsg());
		// 为邮件添加附加内容
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(mail.getAttachmentPath());// 本地文件
		// attachment.setURL(new URL("http://xxx/a.gif"));//远程文件
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription(mail.getAttachmentDescription());
		// 设置附件显示名字，必须要编码，不然中文会乱码
		attachment.setName(MimeUtility.encodeText(mail.getAttachmentName()));
		// 将附件添加到邮件中
		email.attach(attachment);
		email.send();
		logger.debug("end send attachment email...");
	}
	public static void main(String[] args) {
		Email email = new Email();
		email.setSubject("测试标题");
		String[] toAddress = {"253319066@qq.com"};
		email.setToAddress(toAddress);
		email.setMsg("这是测试内容");
		email.setDebug(true);
		try {
			EmailUtils.sendEmail(email);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
