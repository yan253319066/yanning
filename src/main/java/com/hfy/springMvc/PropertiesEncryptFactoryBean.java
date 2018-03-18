package com.hfy.springMvc;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.hfy.utils.DESUtils;
/**
 * properties文件解密
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年12月12日 下午12:22:29
 * @version 1.0 
 *
 */
public class PropertiesEncryptFactoryBean extends PropertyPlaceholderConfigurer{
	/**
	 * 存放需要加密的字段
	 */
	private String[] encryptPropNames = {"jdbc.username", "jdbc.password","jdbc2.username", "jdbc2.password"};  
    
    @Override  
    protected String convertProperty(String propertyName, String propertyValue)  
    {  
          
        //如果在加密属性名单中发现该属性  
        if (isEncryptProp(propertyName))  
        {  
            String decryptValue = DESUtils.getDecryptString(propertyValue);  
            return decryptValue;  
        }else {  
            return propertyValue;  
        }  
          
    }  
      
    private boolean isEncryptProp(String propertyName)  
    {  
        for (String encryptName : encryptPropNames)  
        {  
            if (encryptName.equals(propertyName))  
            {  
                return true;  
            }  
        }  
        return false;  
    }  
    
}
