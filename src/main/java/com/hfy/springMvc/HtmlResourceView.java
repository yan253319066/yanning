package com.hfy.springMvc;

import java.io.File;
import java.util.Locale;

import org.springframework.web.servlet.view.InternalResourceView;
/**
 * 后缀为.html的视图经过该类跳转
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年10月12日 下午8:56:27
 * @version 1.0 
 *
 */
public class HtmlResourceView extends InternalResourceView {
	@Override  
    public boolean checkResource(Locale locale) {  
	     File file = new File(this.getServletContext().getRealPath("/") + getUrl());
	     return file.exists();// 判断该页面是否存在  
    }  
}
