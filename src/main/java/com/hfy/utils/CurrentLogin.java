package com.hfy.utils;

import org.apache.shiro.SecurityUtils;

import com.hfy.constants.Constants;
import com.hfy.login.entity.Login;

public class CurrentLogin {
	public static Login getCurrentLogin(){
		if (SecurityUtils.getSubject() != null) {
			Login login = (Login)SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_KEY_USER);
			return login;
		}
		return null;
	}
}
