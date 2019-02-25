package com.yuanjun.weixindemo.service.webuser.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanjun.weixindemo.dao.IWebUserDao;
import com.yuanjun.weixindemo.model.WebUser;
import com.yuanjun.weixindemo.service.webuser.IWebUserService;

@Transactional
@Service
public class WebUserServiceImpl implements IWebUserService{

	@Autowired
	private IWebUserDao webUserDao;
	
	@Override
	public WebUser getWebUser(String name) {
		return webUserDao.getByName(name);
	}

	@Override
	public List<WebUser> findAll() {
		return webUserDao.findAll();
	}

	@Override
	public void changePassword(String name, String password) {
		webUserDao.changePassword(name, password);
	}

	@Override
	public void addWebUser(WebUser webUser) {
		webUserDao.save(webUser);
	}

	@Override
	public void deleteUser(String id) {
		webUserDao.deleteById(id);
	}

	@Override
	public WebUser getWebUserById(String userid) {
		return webUserDao.findById(userid).orElse(null);
	}
	
	@Override
	public WebUser findWbeUserByOpenId(String openId) {
		return webUserDao.findByOpenidAndDeleflag(openId, 0);
	}

}
