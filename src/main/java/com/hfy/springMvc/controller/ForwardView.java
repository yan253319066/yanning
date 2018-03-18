package com.hfy.springMvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hfy.dictionary.entity.Dictionary;
import com.hfy.dictionary.service.DictionaryService;
import com.hfy.utils.RequestParamUtil;

/**
 * 公用跳转页面
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年10月15日 下午1:13:37
 * @version 1.0 
 *
 */
@Controller
@RequestMapping(value = ForwardView.BASE_URL)
public class ForwardView {
	private static Logger logger = LoggerFactory.getLogger(ForwardView.class);
	public static final String BASE_URL = "/forward";
	
	@Autowired
	private DictionaryService dictionaryService;
	
	/**
	 * 公用跳转页面
	 * @author yanning
	 * @date 2016年11月12日 下午4:54:34
	 * @version 1.0
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("view")
	public String view(HttpServletRequest request, Model model) {
		// TODO Auto-generated method stub
		String url = request.getParameter("url");
		logger.info("url="+url);
		if (StringUtils.isBlank(url)) {
			logger.error("forward path is null.");
			logger.error("跳转路径为空。");
		}
		/**
		 * 获取生成tag的数据
		 */
		String tags = request.getParameter("tags");
		if (StringUtils.isNotBlank(tags)) {
			String[] tagArr = tags.split(",");
			for (String tag : tagArr) {
				if (!model.containsAttribute(tag+"List")) {
					try {
						List<Dictionary> dicList = dictionaryService.getDictionaryList(tag, true);
						model.addAttribute(tag, dicList);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		/**
		 * 将request过来的参数装入model
		 */
		RequestParamUtil.formModel(request, model);
		return url;
	}
}
