package com.hfy.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hfy.base.entity.BaseEntity;
import com.hfy.base.entity.BootStrapPage;

/**
 * 基础Service接口
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年10月3日 下午2:07:24
 * @version 1.0 
 *
 */
public interface BaseService {
		/**
		 * 查询所有数据
		 * @author yanning
		 * @date 2016年10月3日 下午2:19:31
		 * @version 1.0
		 * @return
		 * @throws Exception
		 */
		<ENTITY extends BaseEntity> List<ENTITY> queryList() throws Exception;
		/**
		 * 按传入参数查询数据
		 * @author yanning
		 * @date 2016年10月3日 下午2:19:46
		 * @version 1.0
		 * @param paramMap
		 * @return
		 * @throws Exception
		 */
		<ENTITY extends BaseEntity> List<ENTITY> queryList(Map<String,Object> paramMap) throws Exception;
		/**
		 * 按传入参数查询数据（分页）默认10条
		 * @author yanning
		 * @date 2016年10月6日 下午3:35:11
		 * @version 1.0
		 * @param paramMap
		 * @return
		 * @throws Exception
		 */
		<ENTITY extends Serializable> BootStrapPage<ENTITY> queryListPage(Map<String,Object> paramMap) throws Exception;
		/**
		 * 查询所有数据
		 * @author yanning
		 * @date 2016年10月15日 下午3:38:34
		 * @version 1.0
		 * @return
		 * @throws Exception
		 */
		List<Map<String,Object>> queryListMap() throws Exception;
		/**
		 * 按传入参数查询数据
		 * @author yanning
		 * @date 2016年10月15日 下午3:38:34
		 * @version 1.0
		 * @return
		 * @throws Exception
		 */
		List<Map<String,Object>> queryListMap(Map<String,Object> paramMap) throws Exception;
		/**
		 * 按传入参数查询数据（分页）默认10条
		 * @author yanning
		 * @date 2016年10月15日 下午4:13:26
		 * @version 1.0
		 * @param paramMap
		 * @return
		 * @throws Exception
		 */
		BootStrapPage<Map<String,Object>> queryListMapPage(Map<String,Object> paramMap) throws Exception;
		/**
		 * 按传入参数模糊查询数据
		 * @author yanning
		 * @date 2016年10月3日 下午2:20:30
		 * @version 1.0
		 * @param paramMap
		 * @return
		 * @throws Exception
		 */
		<ENTITY extends BaseEntity> List<ENTITY> queryListByLink(Map<String,Object> paramMap) throws Exception;
		/**
		 * 按传入参数模糊查询（分页）默认10条
		 * @author yanning
		 * @date 2016年10月6日 下午3:36:12
		 * @version 1.0
		 * @param paramMap
		 * @return
		 * @throws Exception
		 */
		<ENTITY extends Serializable> BootStrapPage<ENTITY> queryListPageByLike(Map<String,Object> paramMap) throws Exception;
		/**
		 * 按传入参数模糊查询数据
		 * @author yanning
		 * @date 2016年10月3日 下午2:20:30
		 * @version 1.0
		 * @param paramMap
		 * @return
		 * @throws Exception
		 */
		List<Map<String,Object>> queryListMapByLink(Map<String,Object> paramMap) throws Exception;
		/**
		 * 按传入参数模糊查询（分页）默认10条
		 * @author yanning
		 * @date 2016年10月6日 下午3:36:12
		 * @version 1.0
		 * @param paramMap
		 * @return
		 * @throws Exception
		 */
		BootStrapPage<Map<String,Object>> queryListMapPageByLike(Map<String,Object> paramMap) throws Exception;
		/**
		 * 查询总数
		 * @author yanning
		 * @date 2016年10月3日 下午2:21:03
		 * @version 1.0
		 * @return
		 * @throws Exception
		 */
		long getCount() throws Exception;
		/**
		 * 按参数查询总数
		 * @author yanning
		 * @date 2016年10月3日 下午2:21:11
		 * @version 1.0
		 * @param paramMap
		 * @return
		 * @throws Exception
		 */
		long getCount(Map<String,Object> paramMap) throws Exception;
		/**
		 * 按id查询实体
		 * @author yanning
		 * @date 2016年10月3日 下午2:21:22
		 * @version 1.0
		 * @param id
		 * @return
		 * @throws Exception
		 */
		<ENTITY extends BaseEntity> ENTITY getEntity(String id) throws Exception;
		/**
		 * 按传入参数查询实体
		 * @author yanning
		 * @date 2016年10月3日 下午2:21:32
		 * @version 1.0
		 * @param paramMap
		 * @return
		 * @throws Exception
		 */
		<ENTITY extends BaseEntity> ENTITY getEntity(Map<String,Object> paramMap) throws Exception;
		/**
		 * 按实体查询实体
		 * @author yanning
		 * @date 2016年10月25日 上午11:43:05
		 * @version 1.0
		 * @param entity
		 * @return
		 * @throws Exception
		 */
		<ENTITY extends BaseEntity> ENTITY getEntity(ENTITY entity) throws Exception;
		
		/**
		 * 更新实体
		 * @author yanning
		 * @date 2016年10月3日 下午2:22:17
		 * @version 1.0
		 * @param entity
		 * @return
		 * @throws Exception
		 */
		<ENTITY extends BaseEntity> int update(ENTITY entity) throws Exception;
		/**
		 * 批量更新
		 * @author yanning
		 * @date 2016年10月3日 下午2:24:12
		 * @version 1.0
		 * @param entityList
		 * @return
		 * @throws Exception
		 */
		<ENTITY extends BaseEntity> int updateBatch(List<ENTITY> entityList) throws Exception;
		/**
		 * 新增
		 * @author yanning
		 * @date 2016年10月3日 下午2:24:22
		 * @version 1.0
		 * @param entity
		 * @return
		 * @throws Exception
		 */
		<ENTITY extends BaseEntity> int insert(ENTITY entity) throws Exception;
		/**
		 * 批量新增
		 * @author yanning
		 * @date 2016年10月3日 下午2:24:33
		 * @version 1.0
		 * @param entityList
		 * @return
		 * @throws Exception
		 */
		<ENTITY extends BaseEntity> int insertBatch(List<ENTITY> entityList) throws Exception;
		/**
		 * 按实体参数删除
		 * @author yanning
		 * @date 2016年10月3日 下午2:24:41
		 * @version 1.0
		 * @param entity
		 * @return
		 * @throws Exception
		 */
		<ENTITY extends BaseEntity> int delete(ENTITY entity) throws Exception;
		/**
		 * 按传入参数删除
		 * @author yanning
		 * @date 2016年10月3日 下午2:25:54
		 * @version 1.0
		 * @param paramMap
		 * @return
		 * @throws Exception
		 */
		int delete(Map<String,Object> paramMap) throws Exception;
		/**
		 * 按id删除
		 * @author yanning
		 * @date 2016年10月3日 下午2:24:57
		 * @version 1.0
		 * @param id
		 * @return
		 * @throws Exception
		 */
		int delete(String id) throws Exception;
		/**
		 * 按id批量删除
		 * @author yanning
		 * @date 2016年11月6日 下午5:59:12
		 * @version 1.0
		 * @param list
		 * @return
		 * @throws Exception
		 */
		<T> int deleteBatch(List<T> list) throws Exception;
		
}
