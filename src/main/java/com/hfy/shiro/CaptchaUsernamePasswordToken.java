package com.hfy.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 表单中增加验证码校验
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年10月30日 下午12:46:57
 * @version 1.0 
 *
 */
public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {  
	private static final long serialVersionUID = 1L;
	//验证码字符串  
    private String captcha;  
  
    public CaptchaUsernamePasswordToken(String username, char[] password,  
            boolean rememberMe, String host, String captcha) {  
        super(username, password, rememberMe, host);  
        this.captcha = captcha;  
    }  
  
    public String getCaptcha() {  
        return captcha;  
    }  
  
    public void setCaptcha(String captcha) {  
        this.captcha = captcha;  
    }  
      
}  