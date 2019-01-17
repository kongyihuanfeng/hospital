package com.yuanjun.weixindemo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class WebUser {

	@Id
	@Column(length=32)
	private String gid;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String sex;
	
	private String phone;
	
	private String remark;
	
	private Integer deleflag;
	
	//头像路径
	private String photoPath;
	
	//角色  0 管理员 1客服 2客户
	private String role;

	//个人签名
	private String sign;
	
	//在线状态    若值为offline代表离线，online或者不填为在线
	private String status;
}
