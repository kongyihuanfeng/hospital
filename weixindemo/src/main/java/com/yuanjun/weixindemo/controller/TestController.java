package com.yuanjun.weixindemo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yuanjun.weixindemo.util.MenuUtil;
import com.yuanjun.weixindemo.util.WeiXinUtil;



@RestController
public class TestController {
	@RequestMapping(value = "hello",method=RequestMethod.GET)
	public String login(HttpServletRequest request,HttpServletResponse response){
		System.out.println("success");
		String accessToken  = WeiXinUtil.getAccess_Token();
		String menu = MenuUtil.initMenu();
		System.out.println(menu);
		int result = MenuUtil.createMenu(accessToken,menu);
		//返回菜单记录
		if(result==0){
			System.out.println("菜单创建成功");
		}else{
			System.out.println("错误码"+result);
		}
		return "hello word";
	}
}

