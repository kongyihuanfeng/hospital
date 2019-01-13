package com.yuanjun.weixindemo.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class FilterConfig {

	@Value("${server.servlet.context-path}")
	private String urlPre;
	
    @Bean
    public FilterRegistrationBean registFilter(LogFilter logFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(logFilter);
        registration.addUrlPatterns("/weblogin/admin/**");
        registration.setName("LogFilter");
        registration.setOrder(1);
        return registration;
    }
}
