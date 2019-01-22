package com.yuanjun.weixindemo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanjun.weixindemo.interceptor.LoginInterceptor;
import com.yuanjun.weixindemo.model.WebUser;
import com.yuanjun.weixindemo.service.webuser.IWebUserService;
import com.yuanjun.weixindemo.util.GsonUtil;

@Controller
@RequestMapping("/webUser")
public class WebUserController {

	@Autowired
	private IWebUserService webUserService;

	
	@PostMapping(value="login")
	public String login(HttpServletRequest request,@RequestParam("name")String name,
			@RequestParam("password")String password,Model model){
		Map<String, Object> result = new HashMap<String, Object>();
		WebUser user = webUserService.getWebUser(name);
		String code = "0";
		boolean state = false;
		String message = "";
		HttpSession session = request.getSession();
		if(Objects.equals(user, null)){
			message = "当前用户不存在";
		}else if(Objects.equals(password, user.getPassword())){
			session.setAttribute(LoginInterceptor.SESSION_KEY_PREFIX, user);
			code = "1";
			state = true;
		}else{
			message = "登录不成功";
		}
		result.put("code", code);//状态码 1成功 0 有问题
		result.put("state", state);//状态 true成功
		result.put("message", message);//错误信息
		model.addAllAttributes(result);
		if(state){
			if(user.getRole().equals("2")) {
				return "redirect:/weixin/index/index.html";
			}else {
				return "redirect:/admin/index/index.html";
			}
		}else
			return "redirect:/admin/index/login.html";
	}
	
	@RequestMapping("/customerChat")
	public String onlineCustomerService(HttpServletRequest request,@RequestParam("userid")String userid,Model model){
		Map<String, Object> result = new HashMap<String, Object>();
		WebUser user = webUserService.getWebUser(userid);
		String code = "0";
		boolean state = false;
		String message = "";
		HttpSession session = request.getSession();
		if(Objects.equals(user, null)){
			message = "当前用户不存在";
		}else{
			session.setAttribute(LoginInterceptor.SESSION_KEY_PREFIX, user);
			code = "1";
			state = true;
		}
		result.put("code", code);//状态码 1成功 0 有问题
		result.put("state", state);//状态 true成功
		result.put("message", message);//错误信息
		model.addAllAttributes(result);
		return "redirect:/weixin/index/index.html";
	};
	
	/**
	 * 用于注册用户
	 * @param user
	 * @return
	 */
	@ResponseBody
	@PostMapping(value="register")
	public String register(WebUser user){
		Map<String, Object> result = new HashMap<String, Object>();
		String code = "1";
		boolean state = true;
		String message = "";
		try {
			webUserService.addWebUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			code = "0";
			state = false;
			message = e.getMessage();
		}
		result.put("code", code);
		result.put("state", state);
		result.put("message", message);
		
		return GsonUtil.getGson().toJson(result);
	}
	
	
	@ResponseBody
	@GetMapping(value="loginout")
	public String loginout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.removeAttribute(LoginInterceptor.SESSION_KEY_PREFIX);
		return "success";
	};
	
	public String getAllUser(){
		return null;
	}
	
}
