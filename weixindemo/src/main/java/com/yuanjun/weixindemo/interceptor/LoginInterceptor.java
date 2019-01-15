package com.yuanjun.weixindemo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        if (request.getSession().getAttribute(LoginInterceptor.SESSION_KEY_PREFIX)==null)  {
            response.sendRedirect(urlPre+"/admin/index/login.html");  
            return false;  
        }
        return true;
    }
}
