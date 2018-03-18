package com.hfy.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import com.hfy.utils.MathUtil;
/**
 * 自定义密码验证
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年11月12日 下午10:21:27
 * @version 1.0 
 *
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {  
    @Override  
       public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {  
           UsernamePasswordToken token = (UsernamePasswordToken) authcToken;  
 
           Object tokenCredentials = encrypt(String.valueOf(token.getPassword()));  
           Object accountCredentials = getCredentials(info);  
           //将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false  
           return equals(tokenCredentials, accountCredentials);  
       }  
 
       //将传进来密码加密方法  
       private String encrypt(String data) {  
    	   String password = MathUtil.encryptPassWordSHA(data);
           return password;  
       }  
}  
