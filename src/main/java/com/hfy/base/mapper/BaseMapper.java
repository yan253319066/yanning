package com.hfy.base.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hfy.base.entity.BaseEntity;

/**
 * 基础dao
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年10月3日 下午2:07:12
 * @version 1.0 
 *
 */
public interface BaseMapper {
	/**
	 * 新增
	 * @author yanning
	 * @date 2016年10月3日 下午2:13:49
	 * @version 1.0
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	<ENTITY extends BaseEntity> int insert(ENTITY entity) throws Exception;
	/**
	 * 批量新增
	 * @author yanning
	 * @date 2016年10月3日 下午2:13:57
	 * @version 1.0
	 * @param list
	 * @return
	 * @throws Exception
	 */
	<ENTITY extends BaseEntity> int insertBatch(List<ENTITY> list) throws Exception;
	/**
	 * 修改
	 * @author yanning
	 * @date 2016年10月3日 下午2:14:06
	 * @version 1.0
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	<ENTITY extends BaseEntity> int update(ENTITY entity) throws Exception;
	/**
	 * 批量修改
	 * @author yanning
	 * @date 2016年10月3日 下午2:14:11
	 * @version 1.0
	 * @param list
	 * @return
	 * @throws Exception
	 */
	<ENTITY extends BaseEntity> int updateBatch(List<ENTITY> list) throws Exception;
	/**
	 * 按id删除
	 * @author yanning
	 * @date 2016年10月3日 下午2:14:21
	 * @version 1.0
	 * @param id
	 * @return
	 * @throws Exception
	 */
	int deleteById(Object id) throws Exception;
	/**
	 * 按对象删除
	 * @author yanning
	 * @date 2016年10月19日 上午9:54:01
	 * @version 1.0
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	<ENTITY extends BaseEntity> int deleteByEntity(ENTITY entity) throws Exception;
	/**
	 * 按传入参数删除
	 * @author yanning
	 * @date 2016年10月3日 下午2:16:35
	 * @version 1.0
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	int deleteByParam(Map<String,Object> paramMap) throws Exception;
	/**
	 * 批量删除
	 * @author yanning
	 * @date 2016年10月19日 下午5:58:59
	 * @version 1.0
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	<T> int deleteBatch(List<T> list) throws Exception;
	/**
	 * 获取总数
	 * @author yanning
	 * @date 2016年10月3日 下午2:17:25
	 * @version 1.0
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	Long count(Map<String,Object> paramMap) throws Exception;
	/**
	 * 按传入参数查询数据
	 * @author yanning
	 * @date 2016年10月3日 下午2:17:36
	 * @version 1.0
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	<ENTITY extends Serializable> List<ENTITY> queryList(Map<String,Object> paramMap) throws Exception;
	/**
	 * 按传入参数查询数据
	 * @author yanning
	 * @date 2016年10月15日 下午3:40:10
	 * @version 1.0
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> queryListMap(Map<String,Object> paramMap) throws Exception;
	/**
	 * 按传入参数模糊查询
	 * @author yanning
	 * @date 2016年10月3日 下午2:18:36
	 * @version 1.0
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	<ENTITY extends Serializable> List<ENTITY> queryListByLike(Map<String,Object> paramMap) throws Exception;
	/**
	 * 按传入参数模糊查询
	 * @author yanning
	 * @date 2016年10月15日 下午4:14:50
	 * @version 1.0
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> queryListMapByLike(Map<String,Object> paramMap) throws Exception;
	/**
	 * 按id查询实体
	 * @author yanning
	 * @date 2016年10月3日 下午2:18:00
	 * @version 1.0
	 * @param id
	 * @return
	 * @throws Exception
	 */
	<ENTITY extends BaseEntity> ENTITY getEntityById(Object id) throws Exception;
	/**
	 * 按传入参数查询实体
	 * @author yanning
	 * @date 2016年10月4日 下午4:12:08
	 * @version 1.0
	 * @param paramMap
	 * @throws Exception
	 * @return
	 */
	<ENTITY extends BaseEntity> ENTITY getEntityByParam(Map<String,Object> paramMap) throws Exception;
	/**
	 * 按实体查询实体
	 * @author yanning
	 * @date 2016年11月6日 下午5:57:01
	 * @version 1.0
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	<ENTITY extends BaseEntity> ENTITY getEntity(ENTITY entity) throws Exception;
}
