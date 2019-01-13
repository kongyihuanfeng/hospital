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

}
