package com.hfy.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

/**
 * messeage Service
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年11月12日 下午10:20:52
 * @version 1.0 
 *
 */
@Service
public class MessageResource {
	@Autowired
	MessageSource messageSource;
	/**
	 * 按键值获取properties文件中的值
	 * @author yanning
	 * @date 2016年12月12日 上午9:46:23
	 * @version 1.0
	 * @param msgKey
	 * @return
	 */
	public String getMessage(String msgKey){
		return messageSource.getMessage(msgKey, null, LocaleContextHolder.getLocale());
	}
	/**
	 * 按键值获取properties文件中的值
	 * @author yanning
	 * @date 2016年12月12日 上午9:47:08
	 * @version 1.0
	 * @param msgKey
	 * @param args
	 * 代替value中的参数
	 * @return
	 */
	public String getMessage(String msgKey, Object[] args){
		return messageSource.getMessage(msgKey, args, LocaleContextHolder.getLocale());
	}
}
