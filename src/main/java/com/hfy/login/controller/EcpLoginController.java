package com.hfy.login.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hfy.attachment.entity.Attachment;
import com.hfy.attachment.service.AttachmentService;
import com.hfy.base.controller.impl.BaseControllerImpl;
import com.hfy.base.entity.Result;
import com.hfy.constants.Constants;
import com.hfy.enums.AttaType;
import com.hfy.enums.ResultCode;
import com.hfy.login.entity.Login;
import com.hfy.login.service.EcpLoginService;
import com.hfy.utils.MathUtil;

import net.sf.json.JSONObject;

/**
 * 登录退出controller
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年11月1日 下午8:52:50
 * @version 1.0 
 *
 */
@Controller
@RequestMapping(value = EcpLoginController.BASE_URL)
public class EcpLoginController extends BaseControllerImpl{
	private static Logger logger = LoggerFactory.getLogger(EcpLoginController.class);
	public static final String BASE_URL = "/login";
	
	@Autowired
	EcpLoginService loginService;
	@Autowired
	private AttachmentService attachmentService;

	/**
	 * 获取头像
	 * @author yanning
	 * @date 2016年12月5日 下午6:15:11
	 * @version 1.0
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("getFavicon")
	public ModelAndView getFavicon(HttpServletRequest req, HttpServletResponse resp){
		try {
			Login login = getCurrentUser();
			Attachment atta = new Attachment();
			atta.setTableName("base_user");
			atta.setTableId(login.getId());
			atta.setAttaType(AttaType.TOU_XIANG.getId());
			atta = attachmentService.getEntity(atta);
			if (atta == null) {
				return null;
			}
			File filePic = new File(atta.getAttaPath()+atta.getAttaNewName());
	         if(filePic.exists()){
	             FileInputStream is = new FileInputStream(filePic);
	             int i = is.available(); // 得到文件大小  
	             byte data[] = new byte[i];  
	             is.read(data); // 读数据  
	             is.close();  
	             resp.setContentType("image/*"); // 设置返回的文件类型  
	             OutputStream toClient = resp.getOutputStream(); // 得到向客户端输出二进制数据的对象  
	             toClient.write(data); // 输出数据  
	             toClient.close();  
	         }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        return null;
	}
	/**
	 * 修改头像
	 * @author yanning
	 * @date 2016年12月5日 下午5:54:43
	 * @version 1.0
	 * @param req
	 * @return
	 */
	@RequestMapping("updateFavicon")
	@ResponseBody
	public Result updateFavicon(HttpServletRequest req){
		Result result = new Result();
		int res = -1;
		try {
			Login login = getCurrentUser();
			res = loginService.updateFavicon(req, AttaType.TOU_XIANG.getId(), login.getId(), "base_user");
			if (res > 0) {
				result.setCode(ResultCode.SUCCESS.getId());
			}else{
				result.setCode(ResultCode.FAIL.getId());
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(ResultCode.ERROR.getId());
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 用户自己修改密码
	 * @author yanning
	 * @date 2016年11月30日 下午4:21:33
	 * @version 1.0
	 * @param req
	 * @return
	 */
	@RequestMapping("updatePassword")
	@ResponseBody
	public Result updatePassword(HttpServletRequest req){
		Result result = new Result();
		int res = -1;
		String newpwd = req.getParameter("newpwd");
		String oldpwd = req.getParameter("oldpwd");
		try {
			Login login = getCurrentUser();
			Map<String,Object> user = loginService.authUser(login.getUsername());
			if (user.get("password").toString().equals(MathUtil.encryptPassWordSHA(oldpwd))) {
				res = loginService.updatePassword(login.getId(), MathUtil.encryptPassWordSHA(newpwd), login.getUsername());
			}
			if (res > 0) {
				result.setCode(ResultCode.SUCCESS.getId());
			}else{
				result.setCode(ResultCode.FAIL.getId());
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(ResultCode.ERROR.getId());
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 管理员帮忙修改密码
	 * @author yanning
	 * @date 2016年11月30日 下午4:22:10
	 * @version 1.0
	 * @param req
	 * @return
	 */
	@RequestMapping("adminUpdatePassword")
	@ResponseBody
	public Result adminUpdatePassword(HttpServletRequest req){
		Result result = new Result();
		int res = -1;
		String userId = req.getParameter("userId");
		String newpwd = req.getParameter("newpwd");
		try {
			Login login = getCurrentUser();
			//管理员没有输入新密码默认123456
			if (StringUtils.isBlank(newpwd)) {
				newpwd = "123456";
			}
			res = loginService.updatePassword(userId, MathUtil.encryptPassWordSHA(newpwd), login.getUsername());
			if (res > 0) {
				result.setCode(ResultCode.SUCCESS.getId());
			}else{
				result.setCode(ResultCode.FAIL.getId());
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(ResultCode.ERROR.getId());
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 验证密码是否正确
	 * @author yanning
	 * @date 2016年11月30日 下午5:25:08
	 * @version 1.0
	 * @param req
	 * @return
	 */
	@RequestMapping("checkPassword")
	@ResponseBody
	public boolean checkPassword(HttpServletRequest req){
		String oldpwd = req.getParameter("oldpwd");
		try {
			Login login = getCurrentUser();
			Map<String,Object> user = loginService.authUser(login.getUsername());
			if (user.get("password").toString().equals(MathUtil.encryptPassWordSHA(oldpwd))) {
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 登录后获取用户权限
	 * @author yanning
	 * @date 2016年11月14日 下午5:45:07
	 * @version 1.0
	 * @return
	 */
	@RequestMapping("getMenuList")
	@ResponseBody
	public Result getMenuList(){
		Result result = new Result();
		try {
			Login login = getCurrentUser();
			result.setCode(ResultCode.SUCCESS.getId());
			result.setData(loginService.getMenuListByUserId(login.getId()));
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(ResultCode.ERROR.getId());
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 获取登录用户左侧菜单
	 * @author yanning
	 * @date 2016年11月17日 上午9:56:11
	 * @version 1.0
	 * @return
	 */
	@RequestMapping("getLeftMenuList")
	@ResponseBody
	public Result getLeftMenuList(){
		Result result = new Result();
		try {
			Login login = getCurrentUser();
			result.setCode(ResultCode.SUCCESS.getId());
			result.setData(loginService.getLeftMenuListByUserId(login.getId()));
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(ResultCode.ERROR.getId());
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 跳转登录页面
	 * @author yanning
	 * @date 2016年11月1日 下午1:00:37
	 * @version 1.0
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("loginPage")
	public String loginPage(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			return "redirect:../";
		}else{
			if ("XMLHttpRequest".equalsIgnoreCase((req).getHeader("X-Requested-With"))) {
				logger.info("request ajax...");
				Result result = new Result();
				result.setCode("401");
				result.setMsg("session timeout...");
				resp.setHeader("sessionstatus", "timeout"); 
				resp.setCharacterEncoding("UTF-8");  
				ServletOutputStream out = resp.getOutputStream();  
				out.println(JSONObject.fromObject(result).toString());  
				out.flush();  
				out.close(); 
			} else {  
				logger.info("request not ajax...");
				return "login/login";
			}
		}
		return null;
	}
	/**
	 * 没有使用该验证
	 * @author yanning
	 * @date 2016年10月30日 下午12:36:19
	 * @version 1.0
	 * @param request
	 * @return
	 */
	@Deprecated
	@RequestMapping("login")
	public String login(HttpServletRequest request){
		logger.info("login...");
		Result result = new Result();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			// 身份验证
			Subject subject = SecurityUtils.getSubject();
			logger.info("-----------是否登录："+subject.isAuthenticated());
			if (!subject.isAuthenticated()) {
				subject.login(new UsernamePasswordToken(username, password));
				//登录成功后把用户信息放入session
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("username", username);
				Login user = loginService.getEntity(paramMap);
				subject.getSession().setAttribute(Constants.SESSION_KEY_USER, user);
			}
			for (Iterator<Object> iterator = subject.getSession().getAttributeKeys().iterator(); iterator.hasNext();) {
				Object obj = (Object) iterator.next();
				logger.info("--------"+obj);
			}
			logger.info("login user:"+username);
			result.setCode("200");
			result.setMsg("登录成功");
			return "redirect:../";
		} catch (UnknownAccountException uae) {// 不存在的账号
			//login.error.unknownAccount
			result.setCode("300");
			result.setMsg("账号不存在");
		} catch (IncorrectCredentialsException e) {  
			result.setCode("400");
			result.setMsg("用户名或密码错误");
        } catch (ExcessiveAttemptsException e) {  
            result.setCode("500");
			result.setMsg("登录失败多次，账户锁定10分钟");
        } catch (AuthenticationException e) {
        	e.printStackTrace();
			//login.error.accountPwd
			result.setCode("600");
			result.setMsg("其他错误");
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			//common.error.systemerr
			result.setCode("700");
			result.setMsg("系统错误");
		}
		return "redirect:../login.jsp";
	}
	/**
	 * 退出登录
	 * @author yanning
	 * @date 2016年10月29日 下午3:05:21
	 * @version 1.0
	 * @return
	 */
	@RequestMapping("logout")
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		SecurityUtils.getSecurityManager().logout(subject);
		subject.getSession().removeAttribute(Constants.SESSION_KEY_USER);
		return "redirect:/home.action";
	}
	/**
	 * 登录窗口
	 * 没有用
	 * @author yanning
	 * @date 2016年10月30日 下午5:18:48
	 * @version 1.0
	 * @return
	 */
	@RequestMapping("lockscreen")
	public String lockscreen(){
		return "login/lockscreen";
	}
}
