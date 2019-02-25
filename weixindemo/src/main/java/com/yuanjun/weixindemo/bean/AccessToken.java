package com.yuanjun.weixindemo.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessToken {
	
	private String access_token;//获取到的凭证
	
	private int expires_in;//凭证有效时间
	
	//用户刷新access_token
    private String refresh_token;
    //用户唯一标识
    private String openid;
    //用户授权的作用域
    private String scope;	
}	
