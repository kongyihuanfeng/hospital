package com.yuanjun.weixindemo.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.yuanjun.weixindemo.bean.AccessToken;
import com.yuanjun.weixindemo.constant.UrlType;
import com.yuanjun.weixindemo.interceptor.LoginInterceptor;
import com.yuanjun.weixindemo.model.WebUser;
import com.yuanjun.weixindemo.model.WxUser;
import com.yuanjun.weixindemo.service.webuser.IWebUserService;
import com.yuanjun.weixindemo.service.webuser.IWxUserService;
import com.yuanjun.weixindemo.util.GsonUtil;
import com.yuanjun.weixindemo.util.PlaceholderUtils;

@Controller
@RequestMapping("/webUser")
public class WebUserController {

	@Value("${corporation.domain}")
	private String domain; 
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private IWxUserService wxUserService;
	
	@Autowired
	private IWebUserService webUserService;

	/**
	 * 用户登录检验
	 * @param request
	 * @param name
	 * @param password
	 * @param model
	 * @return
	 */
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
	
	/**
	 * 用户
	 * @param request
	 * @param userid
	 * @param model
	 * @return
	 */
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
	
	
	/**
	 * 引导用户进行授信操作
	 * @return
	 */
	@RequestMapping("wxAuth")
	public String wxAuth() {
		//回调地址
//		String callBackUrl = "http://"+domain+"/wexin/webUser/authCallBack";
		String callBackUrl = "http://haozhen.ngrok.xiaomiqiu.cn/weixin/webUser/authCallBack";
		Map<String, String> urlParam = new HashMap<String, String>();
		//appid
		urlParam.put("appId", UrlType.APPID);
		//应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo 
		//（弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息 ）
		urlParam.put("scope"	, "snsapi_userinfo");
		try {
			urlParam.put("callBackUrl", URLEncoder.encode(callBackUrl, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//一般不会出现异常
			e.printStackTrace();
		}
		String url = PlaceholderUtils.resolvePlaceholders(UrlType.wxAuthURL, urlParam);
//		restTemplate.getForObject(url, String.class);
		return "redirect:"+url;
	}
	
	/**
	 * 返回客服页面
	 * @param code
	 * @return
	 */
	@RequestMapping("authCallBack")
	public ModelAndView wxAuthCallBack(@RequestParam("code") String code) {
		//获取access_token 与之前的不同
		
		Map<String, String> accessTokenMap = new HashMap<String, String>();
		accessTokenMap.put("appId", UrlType.APPID);
		accessTokenMap.put("secret"	, UrlType.APPSECRET);
		accessTokenMap.put("code", code);
		String url = PlaceholderUtils.resolvePlaceholders(UrlType.getAuthAccessTokenURL, accessTokenMap);
		//得到accsee_token 
		//{"access_token":"18_CEeyc4NNJvbveISGVc-iDeGEs3F3jsCWBzt5wQ3St6jWV31EavaxgtZjTFTSdVkF989mMy5Gt_gLqUAqdi1aN0dgJmuBZUoEXAuwUCIyawI",
		//"expires_in":7200,
		//"refresh_token":"18_BfSIqpYuMNYhjomXlzSCL0hdEeqfWrPVHP4ckEgdIhRiPCt0xY5nB4lKpm0qtA0kvQebVELif0Z0sFcylA8qal8TuwO-emboHhZQrR6-0Vs",
		//"openid":"oBFYa0oBA1qjS083JWbiE-6KWuOc",
		//"scope":"snsapi_userinfo"}
		String access_tokenStr = restTemplate.getForObject(url, String.class);
		AccessToken access_token = GsonUtil.getGson().fromJson(access_tokenStr, AccessToken.class);
		//刷新access_token
		Map<String, String> refreshAccessTokenMap = new HashMap<String, String>();
		refreshAccessTokenMap.put("appId", UrlType.APPID);
		refreshAccessTokenMap.put("refresh_token"	, access_token.getRefresh_token());
		String refresh_token_url = PlaceholderUtils.resolvePlaceholders(UrlType.refreshAuthAccessTokenUrl, refreshAccessTokenMap);
		String refresh_tokenStr = restTemplate.getForObject(refresh_token_url, String.class);
		AccessToken refresh_token = GsonUtil.getGson().fromJson(refresh_tokenStr, AccessToken.class);

		Map<String, String> userInfoMap = new HashMap<String, String>();
		userInfoMap.put("access_token", refresh_token.getAccess_token());
		userInfoMap.put("openId"	, refresh_token.getOpenid());
		//获取用户信息
		//{"openid":"oBFYa0oBA1qjS083JWbiE-6KWuOc","nickname":"èµ¤è³æè¾°","sex":1,"language":"zh_CN","city":"åå¼",
		//"province":"å¤©æ´¥","country":"ä¸­å½",
		//"headimgurl":"http:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/Q0j4TwGTfTIiamNbeIaDHpuNJQSWuw1RT507c9maUNxKfFCwxicgIGPPgP5ibsxiaWibgdYibI8wsNib9KT0IdtiaNre7A\/132","privilege":[]}
		String authUser_url = PlaceholderUtils.resolvePlaceholders(UrlType.getWxAuthUserInfoURL, userInfoMap);
		
		String wxUserStr = restTemplate.getForObject(authUser_url, String.class);
		WxUser wxUser = GsonUtil.getGson().fromJson(wxUserStr, WxUser.class);
		if(wxUserService.getWxUser(wxUser.getOpenid())==null) {
			wxUserService.addWxUser(wxUser);
			WebUser user = new WebUser();
			user.setDeleflag(0);
			user.setGid(UUID.randomUUID().toString().replace("-", ""));
			user.setName(wxUser.getNickname());
			user.setPhotoPath(wxUser.getHeadimgurl());
			user.setOpenid(wxUser.getOpenid());
			user.setRole("2");
			user.setDeleflag(0);
			webUserService.addWebUser(user);
		}
		ModelAndView view = new ModelAndView("index");
		view.addObject("userid", wxUser.getOpenid());
		return view;
	}
	
	@RequestMapping("hello")
	public ModelAndView wxAuthCallBack() {
		ModelAndView view = new ModelAndView("index");
		view.addObject("userid", "oBFYa0oBA1qjS083JWbiE-6KWuOc");
		return view;
	}
	
	
}
