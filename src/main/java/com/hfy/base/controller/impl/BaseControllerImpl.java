package com.hfy.base.controller.impl;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

//import java.text.SimpleDateFormat;
//import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hfy.base.controller.BaseController;
import com.hfy.base.entity.Result;
import com.hfy.constants.Constants;
import com.hfy.enums.LogType;
import com.hfy.enums.ResultCode;
import com.hfy.log.entity.Log;
import com.hfy.log.service.LogService;
import com.hfy.login.entity.Login;
import com.hfy.utils.DateJsonValueProcessor;
import com.hfy.utils.RequestParamUtil;

import net.sf.json.JsonConfig;
/**
 * controller基类
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年10月18日 上午11:41:37
 * @version 1.0 
 *
 */
@ControllerAdvice
public class BaseControllerImpl implements BaseController{
	private static Logger logger = LoggerFactory.getLogger(BaseControllerImpl.class);
	
    @Autowired  
    private  HttpServletRequest request;  
    @Autowired
    private LogService logService;
    
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	/**
	 * 异常通用捕获
	 * @author yanning
	 * @date 2017年3月31日 上午10:18:00
	 * @version 1.0
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exceptionHandler(final HttpServletRequest request , Exception ex) {
		logger.error(ex.getMessage(), ex);
		
		try {
			/* ==========数据库日志========= */
			Login login = getCurrentUser();
			String ip = request.getRemoteAddr();
			String url = request.getRequestURI();
			Log log = new Log();
			log.setOperStartDate(null);
			log.setIp(ip);
			log.setLogType(LogType.EXCEPTION.getId());
			if(login!=null){
				log.setOperPerson(login.getUsername());
			}
			log.setOperUrl(url);
			log.setOperMethod(null);
			log.setOperData(RequestParamUtil.formJSONObject(request).toString());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			if (sw.toString().length() > 4000) {
				log.setOperException(sw.toString().substring(0, 4000));
			}else{
				log.setOperException(sw.toString());
			}
			log.setOperEndDate(new Date());
			logService.insert(log);
		} catch (Exception e) {
			// 记录本地异常日志
			logger.error("异常信息:{}", e.getMessage());
		}
		
		Result result = new Result();
		result.setCode(ResultCode.ERROR.getId());
		return result;
	}
	/**
	 * 获取当前登录用户
	 * @author yanning
	 * @date 2016年11月11日 下午5:34:29
	 * @version 1.0
	 * @return
	 */
	public Login getCurrentUser(){
		if (SecurityUtils.getSubject() != null) {
			Login login = (Login)SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_KEY_USER);
			return login;
		}
		return null;
	}
	
	/**
	 * 防止controller传入date为""报错
	 * @author yanning
	 * @date 2016年11月11日 下午5:42:04
	 * @version 1.0
	 * @param binder
	 */
	/*@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}*/
	
	/**
	 * JSON日期转换配置
	 */
	private JsonConfig jsonConfig;
	/**
	 *  net.sf.json.JsonConfig
	 * 在获取date类型时转换成yyyy-MM-dd HH:mm:ss
	 * @author yanning
	 * @date 2016年11月22日 上午10:02:11
	 * @version 1.0
	 * @param datePattern
	 * @return
	 */
	public JsonConfig getJsonConfig(String datePattern) {
		logger.info("init jsonConfig...");
		jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor(datePattern));
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new DateJsonValueProcessor(datePattern));
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,new DateJsonValueProcessor(datePattern));
		return jsonConfig;
	}
}
