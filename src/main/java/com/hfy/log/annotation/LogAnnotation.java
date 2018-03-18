package com.hfy.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 日志注解
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年11月16日 上午11:29:22
 * @version 1.0 
 *
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented  
public @interface LogAnnotation {
	/**
	 * 描述
	 * @author yanning
	 * @date 2016年11月16日 上午11:29:36
	 * @version 1.0
	 * @return
	 */
	String title()  default "";
	/**
	 * 操作表
	 * @author yanning
	 * @date 2016年11月16日 下午5:55:54
	 * @version 1.0
	 * @return
	 */
	String table() default "";
	/**
	 * 操作类型
	 * @author yanning
	 * @date 2016年11月16日 下午5:59:14
	 * @version 1.0
	 * @return
	 */
	String type() default "";
}
