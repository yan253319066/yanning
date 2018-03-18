package com.hfy.shiro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hfy.base.entity.Result;
import com.hfy.cache.Cache;
import com.hfy.constants.Constants;
import com.hfy.login.entity.Ecpmenu;
import com.hfy.login.entity.Login;

import net.sf.json.JSONObject;

/**
 * shiro权限过滤器
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年11月1日 下午8:51:05
 * @version 1.0 
 *
 */
public class PermissionAuthorizationFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(PermissionAuthorizationFilter.class);
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(Cache.getConfigValue("background.auth.switch"))||Cache.getConfigValue("background.auth.switch").equalsIgnoreCase("off")) {
			filter.doFilter(request, response);
			return;
		}
		Subject subject = SecurityUtils.getSubject();
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		Login login = (Login)subject.getSession().getAttribute(Constants.SESSION_KEY_USER);
		//没有登录或者页面跳转不需要验证权限
		if(login == null || req.getServletPath().indexOf("forward/view.action") != -1 || req.getServletPath().equals("/")){
			filter.doFilter(request, response);
			return;
		}
		if(login.getUsername().equals(req.getRemoteUser())){
			for (Ecpmenu menu : login.getMenuList()) {
				if (StringUtils.isBlank(menu.getResUrl())) {
					continue;
				}
				if(menu.getResUrl().indexOf(req.getServletPath().substring(1,req.getServletPath().length()))!=-1){
					filter.doFilter(request, response);
					return;
				}
			}
		}
		if ("XMLHttpRequest".equalsIgnoreCase((req).getHeader("X-Requested-With"))) {
			logger.info("unauthorized...没有权限");
			Result result = new Result();
			result.setCode("402");
			result.setMsg("unauthorized");
			resp.setHeader("auth", "false"); 
			resp.setCharacterEncoding("UTF-8"); 
			ServletOutputStream out = resp.getOutputStream();
			out.print(JSONObject.fromObject(result).toString());
		}else{
			req.getRequestDispatcher("WEB-INF/view/common/error/unauthorized.jsp").forward(req, resp);
		}
		return;
//		if(subject.isPermitted("ad")){
//			filter.doFilter(request, response);
//		}else{
//			
//		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}


}