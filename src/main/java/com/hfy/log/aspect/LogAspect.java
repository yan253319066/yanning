package com.hfy.log.aspect;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hfy.constants.Constants;
import com.hfy.enums.LogType;
import com.hfy.log.annotation.LogAnnotation;
import com.hfy.log.entity.Log;
import com.hfy.log.service.LogService;
import com.hfy.login.entity.Login;
import com.hfy.utils.RequestParamUtil;

import net.sf.json.JSONObject;

/**
 * 日志切面
 * 这个切面由BaseControllerImpl exceptionHandler代替保存异常日志
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年11月16日 上午11:29:45
 * @version 1.0 
 *
 */
//@Aspect
//@Component
public class LogAspect {
	@Resource
	private LogService logService;
	
	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	private Date operStartDate;
	
	public LogAspect(){
		logger.info("-----------LogAspect--------------");
	}

	/**
	 * Controller层切点 
	 * @author yanning
	 * @date 2016年11月16日 上午11:30:10
	 * @version 1.0
	 */
	@Pointcut("@annotation(com.hfy.log.annotation.LogAnnotation)")
	public void controllerAspect() {
	}
	
	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @author yanning
	 * @date 2016年11月16日 上午11:30:10
	 * @version 1.0
	 * @param joinPoint
	 *            切点
	 */
	@Before("execution(* *..controller*..*(..))")
	public void doBefore(JoinPoint joinPoint) {
		this.operStartDate = new Date();
	}
	@After("controllerAspect()")
	public void doAfter(JoinPoint joinPoint){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		// 读取session中的用户
		Login login = (Login) session.getAttribute(Constants.SESSION_KEY_USER);
		// 请求的IP
		String ip = request.getRemoteAddr();
		String url = request.getRequestURI();
		// 获取用户请求方法的参数并序列化为JSON格式字符串
//		String params = "";
//		if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
//			for (int i = 0; i < joinPoint.getArgs().length; i++) {
//				logger.info(joinPoint.getArgs()[i]+"");
//				params += JSONUtils.valueToString(joinPoint.getArgs()[i]) + ";";
//			}
//		}
		try {
			// *========数据库日志=========*//
			Log log = new Log();
			log.setIp(ip);
			log.setLogType(LogType.BUSINESS.getId());
			if(login!=null){
				log.setOperPerson(login.getUsername());
			}
			JSONObject obj = getAnnotationInfo(joinPoint);
			log.setOperTitle(obj.getString("title"));
			log.setOperTable(obj.getString("table"));
			log.setOperType(obj.getString("type"));
			log.setOperUrl(url);
			log.setOperMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
			log.setOperData(RequestParamUtil.formJSONObject(request).toString());
			log.setOperStartDate(this.operStartDate);
			log.setOperEndDate(new Date());
			logService.insert(log);
		} catch (Exception e) {
			// 记录本地异常日志
			logger.error("==前置通知异常==");
			logger.error("异常信息:{}", e.getMessage());
		}
	}

	/**
	 * 异常通知 用于拦截记录异常日志
	 * @author yanning
	 * @date 2016年11月16日 上午11:31:36
	 * @version 1.0
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "execution(* *..controller*..*(..))", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		// 读取session中的用户
		Login login = (Login) session.getAttribute(Constants.SESSION_KEY_USER);
		// 获取请求ip
		String ip = request.getRemoteAddr();
		String url = request.getRequestURI();
		// 获取用户请求方法的参数并序列化为JSON格式字符串
//		String params = "";
//		if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
//			for (int i = 0; i < joinPoint.getArgs().length; i++) {
//				params += JSONUtils.valueToString(joinPoint.getArgs()[i]) + ";";
//			}
//		}
		try {
			/* ==========数据库日志========= */
			Log log = new Log();
			log.setOperStartDate(this.operStartDate);
			log.setIp(ip);
			log.setLogType(LogType.EXCEPTION.getId());
			if(login!=null){
				log.setOperPerson(login.getUsername());
			}
			JSONObject obj = getAnnotationInfo(joinPoint);
			if (!obj.isEmpty()) {
				log.setOperTitle(obj.getString("title"));
				log.setOperTable(obj.getString("table"));
				log.setOperType(obj.getString("type"));
			}
			log.setOperUrl(url);
			log.setOperMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
			log.setOperData(RequestParamUtil.formJSONObject(request).toString());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			if (sw.toString().length() > 4000) {
				log.setOperException(sw.toString().substring(0, 4000));
			}else{
				log.setOperException(sw.toString());
			}
			log.setOperEndDate(new Date());
			logService.insert(log);
		} catch (Exception ex) {
			// 记录本地异常日志
			logger.error("异常信息:{}", ex.getMessage());
		}
		/* ==========记录本地异常日志========== */
		logger.error("异常方法:{}异常代码:{}异常信息:{}数据:{}",
				joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(),
				e.getMessage(), RequestParamUtil.formJSONObject(request).toString());

	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * @author yanning
	 * @date 2016年11月16日 上午11:32:55
	 * @version 1.0
	 * @param joinPoint
	 * @return 方法描述
	 * @throws Exception
	 */
	public static JSONObject getAnnotationInfo(JoinPoint joinPoint) throws Exception {
		JSONObject obj = new JSONObject();
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					if (method.getAnnotation(LogAnnotation.class) != null) {
						String title = method.getAnnotation(LogAnnotation.class).title();
						String table = method.getAnnotation(LogAnnotation.class).table();
						String type = method.getAnnotation(LogAnnotation.class).type();
						obj.put("title", title);
						obj.put("table", table);
						obj.put("type", type);
					}
					break;
				}
			}
		}
		return obj;
	}
}
