package com.hfy.login.entity;import com.hfy.base.entity.BaseEntity;/** * 组织机构实体****************************************************************************** * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途 * 复制、修改或发布本软件. * Copyright (C) 2016 ShenZhen HFY Co.,Ltd * All Rights Reserved. ***************************************************************************** * @author yanning * @date 2016年11月20日 上午10:41:13 * @version 1.0  * */public class Org extends BaseEntity {	private static final long serialVersionUID = 1L;	public Org() {		super();	}	/** 组织名称 */	private String orgName;	public void setOrgName(String orgName) {		this.orgName = orgName;	}	public String getOrgName() {		return this.orgName;	}	/** 组织code */	private String orgCode;	public void setOrgCode(String orgCode) {		this.orgCode = orgCode;	}	public String getOrgCode() {		return this.orgCode;	}	/** 组织级别 */	private String orgLevel;	public void setOrgLevel(String orgLevel) {		this.orgLevel = orgLevel;	}	public String getOrgLevel() {		return this.orgLevel;	}	/** 组织类型 */	private String orgType;	public void setOrgType(String orgType) {		this.orgType = orgType;	}	public String getOrgType() {		return this.orgType;	}	/***/	private Integer parentId;	public void setParentId(Integer parentId) {		this.parentId = parentId;	}	public Integer getParentId() {		return this.parentId;	}	/** 排序 */	private Integer sort;	public void setSort(Integer sort) {		this.sort = sort;	}	public Integer getSort() {		return this.sort;	}	/** 备注 */	private String orgRemark;	public void setOrgRemark(String orgRemark) {		this.orgRemark = orgRemark;	}	public String getOrgRemark() {		return this.orgRemark;	}}