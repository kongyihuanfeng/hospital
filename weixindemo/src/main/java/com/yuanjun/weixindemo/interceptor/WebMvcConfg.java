package com.yuanjun.weixindemo.interceptor;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfg implements WebMvcConfigurer {

	@Value("${server.servlet.context-path}")
	private  String urlPre;
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LoginInterceptor loginInterceptor = new LoginInterceptor();
		loginInterceptor.setUrlPre(urlPre);
		 registry.addInterceptor(loginInterceptor)
		 		 .addPathPatterns("/admin/index/index.html")
		 		 .excludePathPatterns("/","/admin/index/login.html");
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("/").setViewName("forward:/admin/index/login.html");
	}
}
