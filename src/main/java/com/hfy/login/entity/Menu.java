package com.hfy.login.entity;import com.hfy.base.entity.BaseEntity;/** * 功能菜单权限****************************************************************************** * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途 * 复制、修改或发布本软件. * Copyright (C) 2016 ShenZhen HFY Co.,Ltd * All Rights Reserved. ***************************************************************************** * @author yanning * @date 2016年11月18日 上午10:43:28 * @version 1.0  * */public class Menu extends BaseEntity {	private static final long serialVersionUID = 1L;	public Menu() {		super();	}	/** 功能名称 */	private String menuName;	public void setMenuName(String menuName) {		this.menuName = menuName;	}	public String getMenuName() {		return this.menuName;	}	/** 功能编码 */	private String menuCode;	public void setMenuCode(String menuCode) {		this.menuCode = menuCode;	}	public String getMenuCode() {		return this.menuCode;	}	/** 功能跳转路径 */	private String menuForward;	public void setMenuForward(String menuForward) {		this.menuForward = menuForward;	}	public String getMenuForward() {		return this.menuForward;	}	/** 功能访问路径 */	private String menuPath;	public void setMenuPath(String menuPath) {		this.menuPath = menuPath;	}	public String getMenuPath() {		return this.menuPath;	}	/** ico */	private String menuIco;	public void setMenuIco(String menuIco) {		this.menuIco = menuIco;	}	public String getMenuIco() {		return this.menuIco;	}	/** 父级菜单id */	private Integer parentId;	public void setParentId(Integer parentId) {		this.parentId = parentId;	}	public Integer getParentId() {		return this.parentId;	}	/** 是否默认权限 1否，2是 */	private String defaultPermission;	public void setDefaultPermission(String defaultPermission) {		this.defaultPermission = defaultPermission;	}	public String getDefaultPermission() {		return this.defaultPermission;	}	/** 默认进入页面 */	private String defaultHome;	public void setDefaultHome(String defaultHome) {		this.defaultHome = defaultHome;	}	public String getDefaultHome() {		return this.defaultHome;	}	/** 排序 */	private String sort;	public void setSort(String sort) {		this.sort = sort;	}	public String getSort() {		return this.sort;	}	/** 功能类型 */	private String menuType;	public void setMenuType(String menuType) {		this.menuType = menuType;	}	public String getMenuType() {		return this.menuType;	}	/** 备注 */	private String menuRemark;	public void setMenuRemark(String menuRemark) {		this.menuRemark = menuRemark;	}	public String getMenuRemark() {		return this.menuRemark;	}}