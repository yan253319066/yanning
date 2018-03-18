package com.hfy.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import net.sf.json.JSONObject;

/**
 * request参数进行封装工具类
 ******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途 复制、修改或发布本软件. Copyright (C)
 * 2016 ShenZhen HFY Co.,Ltd All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年10月18日 下午5:33:21
 * @version 1.0
 *
 */
public class RequestParamUtil {
	/**
	 * 将request传递的参数封装成map
	 * @author yanning
	 * @date 2016年10月18日 下午5:38:36
	 * @version 1.0
	 * @param request
	 * @return
	 */
	public static Map<String, Object> formMap(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Map<String, String[]> tmp = request.getParameterMap();
		if (tmp != null) {
			for (String key : tmp.keySet()) {
				String[] values = tmp.get(key);
				map.put(key, values.length == 1 ? values[0].trim() : values);
			}
		}
		return map;
	}
	/**
	 * 将request传递的参数封装成map
	 * 去掉是空字符串的参数
	 * @author yanning
	 * @date 2016年10月26日 下午4:53:09
	 * @version 1.0
	 * @param request
	 * @return
	 */
	public static Map<String, Object> formMapDelEmpty(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Map<String, String[]> tmp = request.getParameterMap();
		if (tmp != null) {
			for (String key : tmp.keySet()) {
				String[] values = tmp.get(key);
				if(values.length==1&&StringUtils.isNotBlank(values[0])){
					map.put(key, values.length == 1 ? values[0].trim() : values);
				}
			}
		}
		return map;
	}
	/**
	 * 将request传递的参数封装成map
	 * @author yanning
	 * @date 2016年11月16日 下午5:42:06
	 * @version 1.0
	 * @param request
	 * @return
	 */
	public static JSONObject formJSONObject(HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		@SuppressWarnings("unchecked")
		Map<String, String[]> tmp = request.getParameterMap();
		if (tmp != null) {
			for (String key : tmp.keySet()) {
				String[] values = tmp.get(key);
				obj.put(key, values.length == 1 ? values[0].trim() : values);
			}
		}
		return obj;
	}
	/**
	 * 将request过来的参数装入model
	 * @author yanning
	 * @date 2016年11月12日 下午5:01:05
	 * @version 1.0
	 * @param request
	 * @param model
	 * @return
	 */
	public static Model formModel(HttpServletRequest request, Model model){
		@SuppressWarnings("unchecked")
		Map<String, String[]> tmp = request.getParameterMap();
		if (tmp != null) {
			for (String key : tmp.keySet()) {
				String[] values = tmp.get(key);
				model.addAttribute(key, values.length == 1 ? values[0].trim() : values);
			}
		}
		return model;
	}
}
