package com.hfy.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.hfy.config.entity.Config;
import com.hfy.config.service.ConfigService;
/**
 * 处理缓存
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年12月19日 下午12:14:59
 * @version 1.0 
 *
 */
public class CacheBeanPostProcessor implements BeanPostProcessor {
	private static final Logger logger = LoggerFactory.getLogger(CacheBeanPostProcessor.class);
	ConfigService configService;
	/**
	 * 在spring中定义的bean初始化前调用这个方法
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		return bean;
	}
	/**
	 * 在spring中定义的bean初始化后调用这个方法
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		if (bean instanceof ConfigService) {
			try {
				logger.debug("开始缓存系统基础配置数据...");
				logger.debug("bean..."+beanName);
				configService = (ConfigService) bean;
				List<Config> configList = configService.queryList();
				Map<String,String> configMap = new HashMap<String, String>();
				for (Config config : configList) {
					configMap.put(config.getCode(), config.getValue());
				}
				Cache.setConfigMap(configMap);
				logger.debug("结束缓存系统基础配置数据...");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return bean;
	}

}
