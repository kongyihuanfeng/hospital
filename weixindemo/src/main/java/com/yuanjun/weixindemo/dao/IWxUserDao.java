package com.yuanjun.weixindemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yuanjun.weixindemo.model.WxUser;

public interface IWxUserDao extends JpaRepository<WxUser, String> {
	
	public WxUser getByNickname(String nickname);
	
	@Modifying
	@Query("update WxUser set subscribe=?2 where openid=?1")
	public void updateSubscribe(String openid,int subscribe);

}
