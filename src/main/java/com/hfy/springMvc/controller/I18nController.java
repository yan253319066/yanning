package com.hfy.springMvc.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.hfy.base.entity.Result;
import com.hfy.enums.ResultCode;
import com.hfy.message.service.MessageResource;

/**
 * 国际化
 ******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途 复制、修改或发布本软件. Copyright (C)
 * 2016 ShenZhen HFY Co.,Ltd All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年10月28日 下午3:41:18
 * @version 1.0
 *
 */
@Controller
@RequestMapping(value = I18nController.BASE_URL)
public class I18nController {
	private static Logger logger = LoggerFactory.getLogger(I18nController.class);
	public static final String BASE_URL = "/i18n";
	@Autowired
	MessageResource msg;

	/**
	 * 设置语言
	 * 
	 * @author yanning
	 * @date 2016年10月28日 下午3:41:33
	 * @version 1.0
	 * @param request
	 * @param langType
	 * @return
	 */
	@RequestMapping("setLanguage")
	@ResponseBody
	public Result setLanguage(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "langType", defaultValue = "zh") String langType) {
		Result result = new Result();
		if (langType.toLowerCase().indexOf("zh") != -1 && langType.toLowerCase().indexOf("tw") != -1) {
			Locale locale = new Locale("zh", "TW");
			request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
			LocaleContextHolder.setLocale(locale);
			request.getSession().setAttribute("language", locale.toString());
			logger.info(msg.getMessage("init.language"));
		} else if (langType.toLowerCase().indexOf("zh") != -1) {
			Locale locale = new Locale("zh", "CN");
			request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
			LocaleContextHolder.setLocale(locale);
			request.getSession().setAttribute("language", locale.toString());
			logger.info(msg.getMessage("init.language"));
		} else if (langType.toLowerCase().indexOf("en") != -1) {
			Locale locale = new Locale("en", "US");
			request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
			LocaleContextHolder.setLocale(locale);
			request.getSession().setAttribute("language", locale.toString());
			logger.info(msg.getMessage("init.language"));
		} else {
			request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,LocaleContextHolder.getLocale());
			request.getSession().setAttribute("language", LocaleContextHolder.getLocale().toString());
			logger.info(msg.getMessage("init.language"));
		}
		result.setCode(ResultCode.SUCCESS.getId());
		return result;
	}
}
