package com.hfy.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 自定义验证码异常
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年10月30日 下午12:45:50
 * @version 1.0 
 *
 */
public class IncorrectCaptchaException extends AuthenticationException {  
	  
	private static final long serialVersionUID = 1L;

	public IncorrectCaptchaException() {  
        super();  
    }  
  
    public IncorrectCaptchaException(String message, Throwable cause) {  
        super(message, cause);  
    }  
  
    public IncorrectCaptchaException(String message) {  
        super(message);  
    }  
  
    public IncorrectCaptchaException(Throwable cause) {  
        super(cause);  
    }  
}  