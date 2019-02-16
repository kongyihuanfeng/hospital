package com.yuanjun.weixindemo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuanjun.weixindemo.interceptor.LoginInterceptor;
import com.yuanjun.weixindemo.model.WebUser;
import com.yuanjun.weixindemo.service.webuser.IWebUserService;
import com.yuanjun.weixindemo.service.webuser.impl.WebUserServiceImpl;
import com.yuanjun.weixindemo.util.GsonUtil;

@RestController
@RequestMapping("/chatService")
public class ChatController {

	@Value("${corporation.domain}")
	private String domain; 
	
	@Autowired
	private IWebUserService webUserService;
	/**
	 * 获取当前登录用户的信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCurrentUserInfo")
	public String getCurrentUserInfo(HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		WebUser user = (WebUser)session.getAttribute(LoginInterceptor.SESSION_KEY_PREFIX);
		if(Objects.equals(null, user)){
			result.put("code", 1);
			result.put("msg", "尚未登录");
		}else{
			result.put("code", 0);
			result.put("msg", "");
			Map<String, Object> mine = new HashMap<String, Object>();
			mine.put("username", user.getName());
			mine.put("id",user.getGid());
			mine.put("status", user.getStatus());
			mine.put("sign", user.getSign());
			mine.put("avatar", user.getPhotoPath());
			result.put("mine", mine);
		}
		return GsonUtil.getGson().toJson(result);
	}
	
	/**
	 * 获取微信用户的信息
	 * @param clientId
	 * @return
	 */
	@RequestMapping("/getClientInfo")
	public String getClientInfo(@RequestParam("userId") String clientId) {
		WebUser user =webUserService.findWbeUserByOpenId(clientId);
		Map<String, Object> result = new HashMap<String, Object>();
		if(Objects.equals(null, user)){
			result.put("code", 1);
			result.put("msg", "尚未登录");
		}else{
			result.put("code", 0);
			result.put("msg", "");
			Map<String, Object> mine = new HashMap<String, Object>();
			mine.put("username", user.getName());
			mine.put("id",user.getGid());
			mine.put("status", user.getStatus());
			mine.put("sign", user.getSign());
			mine.put("avatar", user.getPhotoPath());
			result.put("mine", mine);
		}
		return GsonUtil.getGson().toJson(result);
	}
	/**
	 * 域名在配置文件上配置，前台从后台取
	 * @return
	 */
	@RequestMapping("/getServiceDomain")
	public String getServiceDomain() {
		return domain;
	}
}
