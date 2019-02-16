package com.yuanjun.weixindemo.service.webuser.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanjun.weixindemo.dao.IWxUserDao;
import com.yuanjun.weixindemo.model.WxUser;
import com.yuanjun.weixindemo.service.webuser.IWxUserService;

@Transactional
@Service
public class WxUserServiceImpl implements IWxUserService{

	@Autowired
	private IWxUserDao wxUserDao;

	@Override
	public WxUser getWxUser(String openid) {
		return wxUserDao.findById(openid).orElse(null);
	}

	@Override
	public List<WxUser> findAll() {
		// TODO Auto-generated method stub
		return wxUserDao.findAll();
	}

	@Override
	public void updateSubscribe(String openid, int subscribe) {
		// TODO Auto-generated method stub
		wxUserDao.updateSubscribe(openid, subscribe);
	}

	@Override
	public void addWxUser(WxUser wxUser) {
		// TODO Auto-generated method stub
		wxUserDao.save(wxUser);
	}

	@Override
	public void deleteWxUser(String openid) {
		// TODO Auto-generated method stub
		wxUserDao.deleteById(openid);
	}
	
	

}
