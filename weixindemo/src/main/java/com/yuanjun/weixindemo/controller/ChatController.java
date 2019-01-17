package com.yuanjun.weixindemo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuanjun.weixindemo.interceptor.LoginInterceptor;
import com.yuanjun.weixindemo.model.WebUser;
import com.yuanjun.weixindemo.util.GsonUtil;

@RestController
@RequestMapping("/chatService")
public class ChatController {

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
}
