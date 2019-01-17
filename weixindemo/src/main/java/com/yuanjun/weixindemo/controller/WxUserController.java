package com.yuanjun.weixindemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuanjun.weixindemo.model.WxUser;
import com.yuanjun.weixindemo.service.webuser.IWxUserService;
/**
 * 微信关注者控制器。
 * @author xingwei
 *
 */
@Controller
@RequestMapping("/wxUser")
public class WxUserController {
	
	@Autowired
	private IWxUserService wxUserService;
	
	@RequestMapping(value="getWxUserList")
	public List<WxUser> getAllUser(){
		return wxUserService.findAll();
	}

}
