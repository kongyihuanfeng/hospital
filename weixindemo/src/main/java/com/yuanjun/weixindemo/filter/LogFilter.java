package com.yuanjun.weixindemo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.yuanjun.weixindemo.model.WebUser;

public class LogFilter implements Filter {

	@Value("${server.servlet.context-path}")
	private String urlPre;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession();
		if(session.getAttribute("user")!=null){
//			WebUser user = (WebUser) session.getAttribute("user");
			chain.doFilter(request, response);
		}else{
			//response.sendRedirect(urlPre+"/weblogin/admin/index/login.html");
			return;
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
