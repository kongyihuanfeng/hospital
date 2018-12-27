package com.yuanjun.weixindemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.yuanjun.weixindemo.websocket.endpoint.ChatInformationEndpoint;

@Configuration
public class WebSocketConfig {

	 @Bean
	 public ServerEndpointExporter serverEndpointExporter() {
	        return new ServerEndpointExporter();
	 }
	 
}
