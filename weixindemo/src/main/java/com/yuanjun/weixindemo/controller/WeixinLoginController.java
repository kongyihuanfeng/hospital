package com.yuanjun.weixindemo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yuanjun.weixindemo.translate.TransApi;
import com.yuanjun.weixindemo.util.CheckUtil;
import com.yuanjun.weixindemo.util.TulingApiUtil;
import com.yuanjun.weixindemo.util.Message.CoreService;
import com.yuanjun.weixindemo.util.Message.ImageMessageUtil;
import com.yuanjun.weixindemo.util.Message.MessageUtil;
import com.yuanjun.weixindemo.util.Message.TextMessageUtil;



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
