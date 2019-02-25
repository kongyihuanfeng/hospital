package com.yuanjun.weixindemo.service.webuser;

import java.util.List;

import com.yuanjun.weixindemo.model.WebUser;

public interface IWebUserService {

	public WebUser getWebUserById(String userid);
	public WebUser getWebUser(String name);
	
	public List<WebUser> findAll();
	
	public void changePassword(String name,String password);
	
	public void addWebUser(WebUser webUser);
	
	public void deleteUser(String id);
	
	public WebUser findWbeUserByOpenId(String openId);
}
