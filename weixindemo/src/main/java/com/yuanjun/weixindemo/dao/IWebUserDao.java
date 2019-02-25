package com.yuanjun.weixindemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yuanjun.weixindemo.model.WebUser;

public interface IWebUserDao extends JpaRepository<WebUser, String> {
	
	public WebUser getByName(String name);
	
	@Modifying
	@Query("update WebUser set password=?2 where name=?1")
	public void changePassword(String name,String password);

	
	public WebUser findByOpenidAndDeleflag(String openid,Integer deleflag);
}
