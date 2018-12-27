package com.yuanjun.weixindemo.service.distribution.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

import javax.websocket.Session;

import org.springframework.stereotype.Service;

import com.yuanjun.weixindemo.model.UserSequency;
import com.yuanjun.weixindemo.service.distribution.InformationDistributionService;
import com.yuanjun.weixindemo.util.DistributionUtil;

@Service
public class InformationDistributionImpl implements InformationDistributionService {

	@Override
	public String selectOneServer() {
		if(DistributionUtil.serverSessionSequency.size()>0) {
			UserSequency userSequency = DistributionUtil.serverSessionSequency.iterator().next();
			userSequency.getNum().incrementAndGet();
			return userSequency.getUserid();
		}
		return null;
	}

	@Override
	public void clientSendMessage(String userid, String message) {
		// TODO Auto-generated method stub
		List<Session> sessions = DistributionUtil.clientSession.get(userid);
		for(Session session :sessions) {
			if(session.isOpen())
				session.getAsyncRemote().sendText(message);
		}
	}



	@Override
	public void serverSendMessage(String serveruserid, String message) {
		// TODO Auto-generated method stub
		List<Session> sessions = DistributionUtil.clientSession.get(serveruserid);
		for(Session session :sessions) {
			if(session.isOpen())
				session.getAsyncRemote().sendText(message);
		}
	}

	@Override
	public void setConnectWithClient(String clientuserid) {
		// TODO Auto-generated method stub
		String serveruserid = selectOneServer();
		DistributionUtil.clientToServer.putIfAbsent(clientuserid,serveruserid);
	}

	@Override
	public void getClientSession(String serveruserid, String clientuserid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reConnectNewServer(String oldServeruserid) {
		Iterator<Entry<String, String>> it =  DistributionUtil.clientToServer.entrySet().iterator();
    	while(it.hasNext()) {
    		Entry<String, String> entry = it.next();
    		String key = entry.getKey();
    		String value = entry.getValue();
    		if(Objects.equals(value, oldServeruserid)) {
    			String serverid = selectOneServer();
    			DistributionUtil.clientToServer.replace(key, oldServeruserid, serverid);
    		}
    	}
		
	}
}