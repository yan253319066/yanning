package com.hfy.exception;

/**
 * 自定义异常
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2017年3月30日 上午11:29:47
 * @version 1.0 
 *
 */
public class CustomException extends Exception{
	private static final long serialVersionUID = 1L;
	public CustomException(){
        super();
        saveException();
    }
    public CustomException(String msg){
        super(msg);
        saveException();
    }
    public CustomException(String message, Exception cause) {
        super(message, cause);
        saveException();
    }
    public CustomException(Exception cause) {
        super(cause);
        saveException();
    }
    private void saveException(){
    	System.out.println("自定义异常");
    }
}
