package com.yuanjun.weixindemo.service.distribution.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanjun.weixindemo.dao.IWebUserDao;
import com.yuanjun.weixindemo.model.LayerIMUserInfo;
import com.yuanjun.weixindemo.model.ServerMessage;
import com.yuanjun.weixindemo.model.SystemMessage;
import com.yuanjun.weixindemo.model.UserSequency;
import com.yuanjun.weixindemo.model.WebUser;
import com.yuanjun.weixindemo.service.distribution.InformationDistributionService;
import com.yuanjun.weixindemo.util.GsonUtil;

@Service
public class InformationDistributionImpl implements InformationDistributionService {
	
	@Autowired
	private IWebUserDao webUserDao;
	
	
	@Autowired
	private DistributionData distributionData;

	/**
	 * 筛选一个客服
	 */
	@Override
	public String selectOneServer() {
		if(distributionData.serverSessionSequency.size()>0) {
			UserSequency userSequency = distributionData.serverSessionSequency.iterator().next();
			userSequency.getNum().incrementAndGet();
			return userSequency.getUserid();
		}
		return null;
	}

	//向客服发送信息
	@Override
	public void toServerSendMessage(String clientid, String message) {
		String serveruserid = distributionData.clientToServer.get(clientid);
		if(Objects.equals(null, serveruserid)) {
			List<Session> sessions = distributionData.clientSession.get(clientid);
			sendNoServerConnect(sessions);
			return;
		}
		WebUser user = webUserDao.findById(clientid).get();
		LayerIMUserInfo layerIMUserInfo = LayerIMUserInfo.transformUser(user, message);
		List<Session> sessions = distributionData.serverSession.get(serveruserid);
		if(sessions==null) {
			return;
		}
		for(Session session :sessions) {
			if(session.isOpen())
				session.getAsyncRemote().sendText(GsonUtil.getGson().toJson(layerIMUserInfo));
		}
	}


	/**
	 * 向客户发信息
	 */
	@Override
	public void toClientSendMessage(String serveruserid,ServerMessage serverMessage) {
		String clientuserid = serverMessage.getUserid();
		WebUser user = webUserDao.findById(serveruserid).get();
		LayerIMUserInfo layerIMUserInfo = LayerIMUserInfo.transformUser(user, serverMessage.getMessage());
		List<Session> sessions = distributionData.clientSession.get(clientuserid);
		if(sessions==null) {
			return;
		}
		for(Session session :sessions) {
			if(session.isOpen())
				session.getAsyncRemote().sendText(GsonUtil.getGson().toJson(layerIMUserInfo));
		}
	}

	@Override
	public void clientConnect(String clientuserid,Session session) {
		//将session 添加到 管理器中
		List<Session> sessions = null;
		//发送消息 通知客户 正在连接客服 请稍等。。。
		sendClientSayhello(session);
		//如果集合中没有这个客户 说明是最新接入的客户
		if(distributionData.clientSession.containsKey(clientuserid)) {
			sessions  = distributionData.clientSession.get(clientuserid);
			if(!distributionData.noConnectClient.contains(clientuserid)) {
				sessions.add(session);
				return;
			}
		}else {
			sessions = new CopyOnWriteArrayList<Session>();
			distributionData.clientSession.putIfAbsent(clientuserid, sessions);
		}
		sessions.add(session);
		String serveruserid = selectOneServer();
		
		if(Objects.equals(null, serveruserid)) {//当前没有可以连接的客服
			sendNoServerConnect(sessions);
			if(!distributionData.noConnectClient.contains(clientuserid)) {
				distributionData.noConnectClient.add(clientuserid);
			}
			return;
		}
		if(distributionData.noConnectClient.contains(clientuserid)) {
			distributionData.noConnectClient.remove(clientuserid);
		}
		WebUser user = webUserDao.findById(serveruserid).get();
		sendConnectServerSuccess(sessions, user.getCode());
		distributionData.clientToServer.putIfAbsent(clientuserid,serveruserid);
	}
	
	public void clientReConnect(String clientuserid) {
		//有客服接入 可以重新连接客服
		List<Session> sessions = null;
		if(distributionData.clientSession.containsKey(clientuserid)) {
			sessions  = distributionData.clientSession.get(clientuserid);
			String serveruserid = selectOneServer();
			if(Objects.equals(null, serveruserid)) {//当前没有可以连接的客服
				sendNoServerConnect(sessions);
				if(!distributionData.noConnectClient.contains(clientuserid)) {
					distributionData.noConnectClient.add(clientuserid);
				}
				return;
			}
			
			if(distributionData.noConnectClient.contains(clientuserid)) {
				distributionData.noConnectClient.remove(clientuserid);
			}
			WebUser user = webUserDao.findById(serveruserid).get();
			sendConnectServerSuccess(sessions, user.getCode());
			distributionData.clientToServer.putIfAbsent(clientuserid,serveruserid);
			
		}else {
			System.out.println("这种情况目前不会出现");
		}
		
	}
	
	/**
	 * 发送系统消息
	 */
	public void sendSystemMessage(List<Session> sessions,String message) {
		SystemMessage sysMsg = new SystemMessage(new Date(), message);
		for(Session session:sessions) {
			if(session.isOpen()) {
					session.getAsyncRemote().sendText(GsonUtil.getGson().toJson(sysMsg));
			}
		}
	}
	
	public void sendClientSayhello(Session session) {
		List<Session> sessions = new ArrayList<Session>();
		sessions.add(session);
		this.sendSystemMessage(sessions, "正在连接客服 请稍等。。。");
	}
	
	public void sendNoServerConnect(List<Session> sessions) {
		this.sendSystemMessage(sessions, "服务人员繁忙，请耐心等待...");
	}
	
	public void sendConnectServerSuccess(List<Session> sessions,String serverCode) {
		this.sendSystemMessage(sessions, "你好，编号为: "+serverCode+" 客服为您服务");
	}


	@Override
	public void reConnectNewServer(String oldServeruserid) {
		Iterator<Entry<String, String>> it =  distributionData.clientToServer.entrySet().iterator();
    	while(it.hasNext()) {
    		Entry<String, String> entry = it.next();
    		String key = entry.getKey();
    		String value = entry.getValue();
    		if(Objects.equals(value, oldServeruserid)) {
    			String serverid = selectOneServer();
    			if(Objects.equals(serverid, null)) {
    				
    				return;
    			}
    			distributionData.clientToServer.replace(key, oldServeruserid, serverid);
    		}
    	}
		
	}
	/**
	 * 客服连接服务
	 */
	@Override
	public void serverConnect(String serveruserid, Session session) {
		List<Session> sessions = null;
		if(distributionData.serverSession.containsKey(serveruserid)) {
			sessions  = distributionData.serverSession.get(serveruserid);
		}else {
			sessions = new CopyOnWriteArrayList<Session>();
			distributionData.serverSession.putIfAbsent(serveruserid, sessions);
		}
		sessions.add(session);		
		
		//加入到客户筛选客服的队列中
		if(!distributionData.serverSessionSequencyMap.containsKey(serveruserid)) {
			UserSequency sequency = new UserSequency();
			sequency.setUserid(serveruserid);
			distributionData.serverSessionSequencyMap.put(serveruserid, sequency);
			distributionData.serverSessionSequency.add(sequency);
		}
		
		//触发未响应的客户重新连接客服
		if(!Objects.equals(null, distributionData.noConnectClient) 
				&& distributionData.noConnectClient.size()>0) {
			for(String clientid: distributionData.noConnectClient) {
				clientReConnect(clientid);
			}
		}
		
	}

	@Override
	public void clientClose(String clientuserid) {
		//删除客户与客服关系信息
		distributionData.clientToServer.remove(clientuserid);
		//删除客户的session
		distributionData.clientSession.remove(clientuserid);
		//删除未连接的客户
		distributionData.noConnectClient.remove(clientuserid);
	}

	@Override
	public void serverClose(String serveruserid) {
		//删除客服的session
		distributionData.serverSession.remove(serveruserid);
		//删除客服的 筛选信息
		UserSequency usersequency = distributionData.serverSessionSequencyMap.remove(serveruserid);
		distributionData.serverSessionSequency.remove(usersequency);
		//对客户进行转移
		reConnectNewServer(serveruserid);
	}
	
	
	
}
