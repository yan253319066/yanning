package com.hfy.admin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hfy.utils.CurrentLogin;

/**
 * 该类现在主要是 特定一些账号才可以查询某些数据
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年12月27日 上午10:28:55
 * @version 1.0 
 *
 */
public class AdminCheck {
	private static Logger logger = LoggerFactory.getLogger(AdminCheck.class);
	private static String hfyUsername;
	private static String hfyRoleCode;
	private static String hfyMenuCode;
	
	static {
		Properties prop = new Properties();
		try {
			InputStream in = AdminCheck.class.getResourceAsStream("/hfy.properties");
			prop.load(in);
			hfyUsername = prop.getProperty("hfy.username").trim();
			hfyRoleCode = prop.getProperty("hfy.roleCode").trim();
			hfyMenuCode = prop.getProperty("hfy.menuCode").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 验证是否是admin(hfy)登录，增加相应查询条件
	 * @author yanning
	 * @date 2016年12月27日 上午10:30:06
	 * @version 1.0
	 * @param paramMap
	 * @throws Exception
	 */
	public static Map<String, Object> checkIsAdmin(Map<String, Object> paramMap) throws Exception{
		if (StringUtils.isBlank(hfyUsername)) {
			logger.debug("hfyUsername is null...");
			return paramMap;
		}
		String[] username = null;
		String[] roleCode = null;
		if (StringUtils.isNotBlank(hfyUsername)) {
			username = hfyUsername.split(",");
		}
		if (StringUtils.isNotBlank(hfyRoleCode)) {
			roleCode = hfyRoleCode.split(",");
		}
		if (paramMap == null) {
			paramMap = new HashMap<String, Object>();
		}
		if (hfyUsername.contains(CurrentLogin.getCurrentLogin().getUsername())) {
			paramMap.put("isAdmin", true);
		}else{
			paramMap.put("usernameList",Arrays.asList(username));
			paramMap.put("roleCodeList",Arrays.asList(roleCode));
			paramMap.put("hfyMenuCode",hfyMenuCode);
		}
		return paramMap;
	}
}
