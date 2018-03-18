package com.hfy.base.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hfy.base.entity.BaseEntity;
import com.hfy.base.entity.BootStrapPage;
import com.hfy.base.mapper.BaseMapper;
import com.hfy.base.service.BaseService;
import com.hfy.utils.HumpConvert;
/**
 * Service基类
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年10月18日 上午11:42:12
 * @version 1.0 
 *
 */
public abstract class BaseServiceImpl implements BaseService {
	private final static int PAGE_NUMBER = 1;
	private final static int PAGE_SIZE = 10;
	
	public abstract BaseMapper getMapper();
	
	@Override
	public <ENTITY extends BaseEntity> List<ENTITY> queryList() throws Exception {
		// TODO Auto-generated method stub
		return getMapper().queryList(null);
	}

	@Override
	public <ENTITY extends BaseEntity> List<ENTITY> queryList(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return getMapper().queryList(paramMap);
	}

	@Override
	public <ENTITY extends Serializable> BootStrapPage<ENTITY> queryListPage(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		if(paramMap.get("pageNumber") == null || StringUtils.isBlank(paramMap.get("pageSize").toString())){
			paramMap.put("pageNumber", PAGE_NUMBER);
		}
		if(paramMap.get("pageSize") == null || StringUtils.isBlank(paramMap.get("pageSize").toString())){
			paramMap.put("pageSize", PAGE_SIZE);
		}
		if (paramMap.get("sortName")!=null) {
			PageHelper.startPage(Integer.parseInt(paramMap.get("pageNumber").toString()), Integer.parseInt(paramMap.get("pageSize").toString()), HumpConvert.humpToLine2(paramMap.get("sortName").toString()) +" "+ paramMap.get("sortOrder"));
		}else{
			PageHelper.startPage(Integer.parseInt(paramMap.get("pageNumber").toString()), Integer.parseInt(paramMap.get("pageSize").toString()));
		}
	    List<ENTITY> list = getMapper().queryList(paramMap);
	    //用PageInfo对结果进行包装
	    PageInfo<ENTITY> pageInfo = new PageInfo<ENTITY>(list);
		return getBootStrapPage(pageInfo);
	}

	@Override
	public List<Map<String, Object>> queryListMap() throws Exception {
		// TODO Auto-generated method stub
		return getMapper().queryListMap(null);
	}

	@Override
	public List<Map<String, Object>> queryListMap(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return getMapper().queryListMap(paramMap);
	}

	@Override
	public BootStrapPage<Map<String, Object>> queryListMapPage(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		if(paramMap.get("pageNumber") == null || StringUtils.isBlank(paramMap.get("pageSize").toString())){
			paramMap.put("pageNumber", PAGE_NUMBER);
		}
		if(paramMap.get("pageSize") == null || StringUtils.isBlank(paramMap.get("pageSize").toString())){
			paramMap.put("pageSize", PAGE_SIZE);
		}
		if (paramMap.get("sortName")!=null) {
			PageHelper.startPage(Integer.parseInt(paramMap.get("pageNumber").toString()), Integer.parseInt(paramMap.get("pageSize").toString()), HumpConvert.humpToLine2(paramMap.get("sortName").toString()) +" "+ paramMap.get("sortOrder"));
		}else{
			PageHelper.startPage(Integer.parseInt(paramMap.get("pageNumber").toString()), Integer.parseInt(paramMap.get("pageSize").toString()));
		}
	    List<Map<String, Object>> list = getMapper().queryListMap(paramMap);
	    //用PageInfo对结果进行包装
	    PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		return getBootStrapPage(pageInfo);
	}

	@Override
	public <ENTITY extends BaseEntity> List<ENTITY> queryListByLink(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return getMapper().queryListByLike(paramMap);
	}

	@Override
	public <ENTITY extends Serializable> BootStrapPage<ENTITY> queryListPageByLike(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		if(paramMap.get("pageNumber") == null || StringUtils.isBlank(paramMap.get("pageSize").toString())){
			paramMap.put("pageNumber", PAGE_NUMBER);
		}
		if(paramMap.get("pageSize") == null || StringUtils.isBlank(paramMap.get("pageSize").toString())){
			paramMap.put("pageSize", PAGE_SIZE);
		}
		if (paramMap.get("sortName")!=null) {
			PageHelper.startPage(Integer.parseInt(paramMap.get("pageNumber").toString()), Integer.parseInt(paramMap.get("pageSize").toString()), HumpConvert.humpToLine2(paramMap.get("sortName").toString()) +" "+ paramMap.get("sortOrder"));
		}else{
			PageHelper.startPage(Integer.parseInt(paramMap.get("pageNumber").toString()), Integer.parseInt(paramMap.get("pageSize").toString()));
		}
	    List<ENTITY> list = getMapper().queryListByLike(paramMap);
	    //用PageInfo对结果进行包装
	    PageInfo<ENTITY> pageInfo = new PageInfo<ENTITY>(list);
		return getBootStrapPage(pageInfo);
	}

	@Override
	public List<Map<String, Object>> queryListMapByLink(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return getMapper().queryListMapByLike(paramMap);
	}

	@Override
	public BootStrapPage<Map<String, Object>> queryListMapPageByLike(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		if(paramMap.get("pageNumber") == null || StringUtils.isBlank(paramMap.get("pageSize").toString())){
			paramMap.put("pageNumber", PAGE_NUMBER);
		}
		if(paramMap.get("pageSize") == null || StringUtils.isBlank(paramMap.get("pageSize").toString())){
			paramMap.put("pageSize", PAGE_SIZE);
		}
		if (paramMap.get("sortName")!=null) {
			PageHelper.startPage(Integer.parseInt(paramMap.get("pageNumber").toString()), Integer.parseInt(paramMap.get("pageSize").toString()), HumpConvert.humpToLine2(paramMap.get("sortName").toString()) +" "+ paramMap.get("sortOrder"));
		}else{
			PageHelper.startPage(Integer.parseInt(paramMap.get("pageNumber").toString()), Integer.parseInt(paramMap.get("pageSize").toString()));
		}
	    List<Map<String, Object>> list = getMapper().queryListMapByLike(paramMap);
	    //用PageInfo对结果进行包装
	    PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		return getBootStrapPage(pageInfo);
	}

	@Override
	public long getCount() throws Exception {
		// TODO Auto-generated method stub
		return getMapper().count(null);
	}

	@Override
	public long getCount(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return getMapper().count(paramMap);
	}

	@Override
	public <ENTITY extends BaseEntity> ENTITY getEntity(String id) throws Exception {
		// TODO Auto-generated method stub
		return getMapper().getEntityById(id);
	}

	@Override
	public <ENTITY extends BaseEntity> ENTITY getEntity(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return getMapper().getEntityByParam(paramMap);
	}
	
	@Override
	public <ENTITY extends BaseEntity> ENTITY getEntity(ENTITY entity) throws Exception {
		// TODO Auto-generated method stub
		return getMapper().getEntity(entity);
	}

	@Override
	public <ENTITY extends BaseEntity> int update(ENTITY entity) throws Exception {
		// TODO Auto-generated method stub
		return getMapper().update(entity);
	}

	@Override
	public <ENTITY extends BaseEntity> int updateBatch(List<ENTITY> entityList) throws Exception {
		// TODO Auto-generated method stub
		return getMapper().updateBatch(entityList);
	}

	@Override
	public <ENTITY extends BaseEntity> int insert(ENTITY entity) throws Exception {
		// TODO Auto-generated method stub
		return getMapper().insert(entity);
	}

	@Override
	public <ENTITY extends BaseEntity> int insertBatch(List<ENTITY> entityList) throws Exception {
		// TODO Auto-generated method stub
		return getMapper().insertBatch(entityList);
	}

	@Override
	public <ENTITY extends BaseEntity> int delete(ENTITY entity) throws Exception {
		// TODO Auto-generated method stub
		return getMapper().deleteByEntity(entity);
	}

	@Override
	public int delete(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return getMapper().deleteByParam(paramMap);
	}

	@Override
	public int delete(String id) throws Exception {
		// TODO Auto-generated method stub
		return getMapper().deleteById(id);
	}

	@Override
	public <T> int deleteBatch(List<T> list) throws Exception {
		// TODO Auto-generated method stub
		return getMapper().deleteBatch(list);
	}
	
	/**
	 * 封装成bootstrap分页
	 * @author yanning
	 * @date 2016年11月6日 下午6:03:36
	 * @version 1.0
	 * @param pageInfo
	 * @return
	 * @throws Exception
	 */
	private <T> BootStrapPage<T> getBootStrapPage(PageInfo<T> pageInfo) throws Exception {
		BootStrapPage<T> page = new BootStrapPage<T>();
		page.setTotal(pageInfo.getTotal());
		page.setRows(pageInfo.getList());
		return page;
	}

}
