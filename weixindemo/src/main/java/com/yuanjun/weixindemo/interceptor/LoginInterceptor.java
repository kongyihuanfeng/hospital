package com.yuanjun.weixindemo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static String urlPre;
	
    public  String getUrlPre() {
		return urlPre;
	}

	public  void setUrlPre(String urlPre) {
		LoginInterceptor.urlPre = urlPre;
	}

	public final static String SESSION_KEY_PREFIX = "user";

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
    	HttpSession session = request.getSession();
    	Object user = session.getAttribute(SESSION_KEY_PREFIX);
        if (user==null)  {
        	//登录页面
            response.sendRedirect(urlPre+"/admin/index/login.html");  
            return false;  
        }
        return true;
    }
}
