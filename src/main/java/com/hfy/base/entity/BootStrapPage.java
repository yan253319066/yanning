package com.hfy.base.entity;

import java.io.Serializable;
import java.util.List;
/**
 * bootstrap分页
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年10月18日 上午11:40:28
 * @version 1.0 
 *
 * @param <T>
 */
public class BootStrapPage<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long total;
    
    private List<T> rows;
    
	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
