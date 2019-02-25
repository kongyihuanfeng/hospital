package com.yuanjun.weixindemo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.yuanjun.weixindemo.bean.AccessToken;
import com.yuanjun.weixindemo.constant.UrlType;
import com.yuanjun.weixindemo.model.WxUser;
import com.yuanjun.weixindemo.service.webuser.IWxUserService;
import com.yuanjun.weixindemo.util.CheckUtil;
import com.yuanjun.weixindemo.util.PlaceholderUtils;
import com.yuanjun.weixindemo.util.Message.CoreService;



/**
 * 
 * 类名称: LoginController
 * 类描述: 与微信对接登陆验证
 * @author yuanjun
 * 创建时间:2017年12月5日上午10:52:13
 */
@RestController
public class WeixinLoginController {
	
	
	@RequestMapping(value = "wxdemo",method=RequestMethod.GET)
	public void login(HttpServletRequest request,HttpServletResponse response){
		System.out.println("success");
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		PrintWriter out = null;
		try {
			  out = response.getWriter();
			if(CheckUtil.checkSignature(signature, timestamp, nonce)){
				out.write(echostr);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	}
	
	@RequestMapping(value = "wxdemo",method=RequestMethod.POST)
	public void dopost(HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		String message = CoreService.processRequest(request);
		if(message==null) {
			return;
		}
		try {
			out = response.getWriter();
			out.write(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.close();
	}
}
