package com.yuanjun.weixindemo.service.webuser;

import java.util.List;

import com.yuanjun.weixindemo.model.WxUser;

public interface IWxUserService {

	public WxUser getWxUser(String openid);
	
	public List<WxUser> findAll();
	
	public void updateSubscribe(String openid,int subscribe);
	
	public void addWxUser(WxUser wxUser);
	
	public void deleteWxUser(String openid);
}
