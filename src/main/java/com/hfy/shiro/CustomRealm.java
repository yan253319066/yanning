package com.hfy.shiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hfy.constants.Constants;
import com.hfy.login.entity.Login;
import com.hfy.login.service.EcpLoginService;

/**
 * shiro 登录权限控制
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年10月3日 上午1:06:53
 * @version 1.0 
 *
 */
@Component
public class CustomRealm extends AuthorizingRealm {
	@Autowired
	EcpLoginService loginService;

	/**
	 * 认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		String username = (String) authcToken.getPrincipal();  
		if (StringUtils.isNotEmpty(username)) {
			Map<String, Object> user = null;
			try {
				user = loginService.authUser(username);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (user != null) {
				// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配  
				SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(  
						user.get("USERNAME").toString(), // 用户名  
						user.get("PASSWORD").toString(), // 密码  
						getName() // realm name  
						);  
				return authenticationInfo;  
			}
	  
		}
		return null;
	}

	/**
	 * 授权信息 为当前登录的Subject授予角色和权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		Subject subject = SecurityUtils.getSubject();
		Login user = (Login)subject.getSession().getAttribute(Constants.SESSION_KEY_USER);

		List<String> permissions = new ArrayList<String>();  
        if (user != null) {  
            permissions.add(user.getId());  
        }  
        
		/*List<SecFunctionDto> functionList = secFunctionService.getFunctionListByMemberid(map);

		// 从数据库取出用户权限
		for (SecFunctionDto secFunctionDto : functionList) {
			StringBuffer sb = new StringBuffer(secFunctionDto.getPlatform());
			sb.append(":").append(secFunctionDto.getFunctionCode());
			for (String operCode : secFunctionDto.getOperCode()) {
				sb.append(":").append(operCode);
				info.addStringPermission(sb.toString());
			}
		}

		info.addRole(member.getMbrType());*/
        permissions.add("adm");
		info.addStringPermissions(permissions);
		return info;
	}

	/**
	 * 将一些数据放到ShiroSession中,以便于其它地方使用
	 * 
	 * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
	 */
	void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			System.out.println(currentUser.getPrincipal());
			Session session = currentUser.getSession();
			System.out.println("session 默认超时时间："+session.getTimeout());
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}
	
    /** 
     * 设定Password校验. 
     */  
    @PostConstruct  
    public void initCredentialsMatcher() {  
    	//该句作用是重写shiro的密码验证，让shiro用我自己的验证  
        setCredentialsMatcher(new CustomCredentialsMatcher());  
  
    }  

}
