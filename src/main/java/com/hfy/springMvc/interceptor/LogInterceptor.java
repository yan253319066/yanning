package com.hfy.springMvc.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hfy.cache.Cache;
import com.hfy.constants.Constants;
import com.hfy.enums.LogType;
import com.hfy.log.entity.Log;
import com.hfy.log.service.LogService;
import com.hfy.login.entity.Login;
import com.hfy.utils.RequestParamUtil;
/**
 * 日志记录拦截器
 * 主要记录每次请求和请求错误
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年11月13日 下午7:21:11
 * @version 1.0 
 *
 */
public class LogInterceptor extends HandlerInterceptorAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
	@Autowired
	private LogService logService;
	private Date operStartDate;
    /**
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，
     * SpringMVC中的Interceptor拦截器是链式的，可以同时存在多个Interceptor，
     * 然后SpringMVC会根据声明的前后顺序一个接一个的执行，
     * 而且所有的Interceptor中的preHandle方法都会在Controller方法调用之前调用。
     * SpringMVC的这种Interceptor链式结构也是可以进行中断的，
     * 这种中断方式是令preHandle的返回值为false，当preHandle的返回值为false的时候整个请求就结束了。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String host = request.getRemoteHost();
//        String url = request.getRequestURI();
//
//        logger.info("IP为---->>> " + host + " <<<-----访问了"+url);
    	this.operStartDate = new Date();
        return true;
    }

    /**
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。
     * postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之 后， 也就是在Controller的方法调用之后执行，
     * 但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操作。
     * 这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用，
     * 这跟Struts2里面的拦截器的执行过程有点像，
     * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法，
     * Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor或者是调用action，
     * 然后要在Interceptor之前调用的内容都写在调用invoke之前，要在Interceptor之后调用的内容都写在调用invoke方法之后。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//    	logger.info("preHandle方法返回true，开始执行此方法");
    }

    /**
     * 这个方法最后执行
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。
     * 该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行， 这个方法的主要作用是用于清理资源的，
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
        	if(StringUtils.isBlank(Cache.getConfigValue("log.base.switch"))||Cache.getConfigValue("log.base.switch").equalsIgnoreCase("off")){
        		return;
        	}
        	String host = request.getRemoteHost();
            String url = request.getRequestURI();
            String purl = request.getParameter("url");
            if (url.indexOf("log/") != -1 || (url.indexOf("forward/view.action") != -1 && purl.indexOf("log/") != -1)) {
    			return;
    		}
            Login login = (Login) request.getSession().getAttribute(Constants.SESSION_KEY_USER);
            Log log = new Log();
            log.setIp(host);
            log.setLogType(LogType.RECORD.getId());
            if (login != null) {
            	log.setOperPerson(login.getUsername());
    		}
            if (ex==null) {
            	log.setOperTitle(LogType.RECORD.getText());
    		}else{
    			log.setOperTitle(LogType.EXCEPTION.getText());
    			log.setOperException(ex.getMessage());
    		}
            
            if (StringUtils.isNotBlank(purl)) {
            	log.setOperUrl(url+"?url="+purl);
    		}else{
    			log.setOperUrl(url);
    		}
            HandlerMethod detailHandlerMethod = (HandlerMethod)handler;
            log.setOperMethod(detailHandlerMethod.getBeanType().getName()+"."+detailHandlerMethod.getMethod().getName()+"()");
            if (RequestParamUtil.formJSONObject(request).toString().getBytes("UTF-8").length < 4000) {
            	log.setOperData(RequestParamUtil.formJSONObject(request).toString());
			}else{
				log.setOperData("内容过长");
			}
            if (this.operStartDate != null) {
            	log.setOperStartDate(this.operStartDate);
			}
            log.setOperEndDate(new Date());
			logService.insert(log);
			logger.info("log record success...");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("log record error...");
		}
//        System.out.println(detailHandlerMethod.getBean());
//        System.out.println(detailHandlerMethod.getBeanType());
//        System.out.println(detailHandlerMethod.getBeanType().getName());
//        System.out.println(detailHandlerMethod.getBeanType().getCanonicalName());
//        System.out.println(detailHandlerMethod.getBeanType().getSimpleName());
//        System.out.println(detailHandlerMethod.getMethod());
//        System.out.println(detailHandlerMethod.getReturnType());
//        Method m = detailHandlerMethod.getMethod();
//        for (int i = 0; i < detailHandlerMethod.getMethodParameters().length; i++) {
//        	System.out.println(detailHandlerMethod.getMethodParameters()[i]);
//        	MethodParameter parameter =  detailHandlerMethod.getMethodParameters()[i];
//        	System.out.println(parameter.getParameterName());
//        	System.out.println(parameter.getNestingLevel());
//        	System.out.println(parameter.getParameterIndex());
//        	System.out.println(parameter.getAnnotatedElement());
//        	System.out.println(parameter.getMember());
//        	System.out.println(parameter.getMethod());
//        	System.out.println(parameter.getParameterType());
//		}
//        logger.debug("这个呢。。。。。IP为---->>> " + host + " <<<-----访问成功");
    }
}
