package com.hfy.shiro;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;

import com.hfy.constants.Constants;
import com.hfy.login.entity.Ecpmenu;
import com.hfy.login.entity.Ecprole;
import com.hfy.login.entity.Login;
import com.hfy.login.service.EcpLoginService;
import com.hfy.message.service.MessageResource;

/**
 * shiro表单验证
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年10月30日 下午12:47:21
 * @version 1.0 
 *
 */
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {
	private static final Logger LOG = LoggerFactory.getLogger(CaptchaFormAuthenticationFilter.class);
	
	@Autowired
	EcpLoginService loginService;
	@Autowired
	MessageResource msg;
	
	public CaptchaFormAuthenticationFilter() {
	}
	
	@Override
	/**
	 * 登录验证
	 */
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		CaptchaUsernamePasswordToken token = createToken(request, response);
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		try {
			String language = (String) req.getSession().getAttribute("language");
			if(StringUtils.isNotBlank(language)){
				String[] str = language.split("_");
				Locale locale = new Locale(str[0], str[1]);
				LocaleContextHolder.setLocale(locale);
			}
			/* 图形验证码验证 */
			doCaptchaValidate(req, token);
			Subject subject = getSubject(request, response);
			if (!subject.isAuthenticated()) {
				subject.login(token);// 正常验证
				//登录成功后把用户信息放入session
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("username", request.getParameter("username").toUpperCase());
				Login user = loginService.getEntity(paramMap);
				List<Ecprole> roleList = loginService.getRoleListByUserId(user.getId());
				user.setRoleList(roleList);
				List<Ecpmenu> menuList = loginService.getMenuListByUserId(user.getId());
				user.setMenuList(menuList);
//				Org org = loginService.getOrgByOrgId(String.valueOf(user.getOrgId()));
//				user.setOrg(org);
				subject.getSession().setAttribute(Constants.SESSION_KEY_USER, user);
			}
			LOG.info(token.getUsername() + "login success");
			resp.sendRedirect("../home.action");		
			return false;
//			return onLoginSuccess(token, subject, request, response);
		} 
		catch (LockedAccountException e) {
			LOG.info(token.getUsername() + "锁定的帐号--login.error.lockedAccount " + e);
			request.setAttribute("error", msg.getMessage("login.error.lockedAccount"));
			return onLoginFailure(token, e, request, response);
		} 
		catch (DisabledAccountException e) {
			LOG.info(token.getUsername() + "禁用的帐号--login.error.disabledAccount " + e);
			request.setAttribute("error", msg.getMessage("login.error.disabledAccount"));
			return onLoginFailure(token, e, request, response);
		} 
		catch (UnknownAccountException e) {
			LOG.info(token.getUsername() + "帐号不存在--login.error.unknownAccount " + e);
			request.setAttribute("error", msg.getMessage("login.error.unknownAccount"));
			return onLoginFailure(token, e, request, response);
		} catch (ExcessiveAttemptsException e) {
			LOG.info(token.getUsername() + "登录失败次数过多--" + e);
			request.setAttribute("error", msg.getMessage("login.error.excessiveAttempts"));
			return onLoginFailure(token, e, request, response);
		} catch (IncorrectCredentialsException e) {
			LOG.info(token.getUsername() + "用户名或密码错误--login.error.accountPwd " + e);
			request.setAttribute("error", msg.getMessage("login.error.accountPwd"));
			return onLoginFailure(token, e, request, response);
		} catch (ExpiredCredentialsException e) {
			LOG.info(token.getUsername() + "过期的凭证--" + e);
			request.setAttribute("error", msg.getMessage("login.error.expiredAccount "));
			return onLoginFailure(token, e, request, response);
		} catch (IncorrectCaptchaException e) {
			LOG.info(token.getUsername() + "验证码错误--login.error.validcodeError " + e);
			request.setAttribute("error", msg.getMessage("login.error.validcodeError"));
			return onLoginFailure(token, e, request, response);
		} catch (AuthenticationException e) {
			LOG.info(token.getUsername() + "登录失败--"+msg.getMessage("msg.error.system")+"  " + e);
			request.setAttribute("error", msg.getMessage("msg.error.system"));
			e.printStackTrace();
			return onLoginFailure(token, e, request, response);
		}
	}

	/**
	 * 验证码校验
	 * @author yanning
	 * @date 2016年10月30日 下午12:47:49
	 * @version 1.0
	 * @param request
	 * @param token
	 */
	protected void doCaptchaValidate(HttpServletRequest request, CaptchaUsernamePasswordToken token) {
		// session中的图形码字符串
		String captcha = (String) request.getSession()
				.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		// 比对
		if (captcha != null && !captcha.equalsIgnoreCase(token.getCaptcha())) {
			throw new IncorrectCaptchaException("验证码错误！");
		}
	}

	@Override
	protected CaptchaUsernamePasswordToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		String captcha = getCaptcha(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);

		return new CaptchaUsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha);
	}

	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;

	public String getCaptchaParam() {
		return captchaParam;
	}

	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}
}
