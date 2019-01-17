package com.yuanjun.weixindemo.service.distribution;

import com.yuanjun.weixindemo.model.ServerMessage;

public interface InformationDistributionService {
	
	
	public void reConnectNewServer(String oldServeruserid);
	
	public void setConnectWithClient(String clientuserid);

	public String selectOneServer();
	

	public void clientSendMessage(String userid,String message);
	
	public void getClientSession(String serveruserid,String clientuserid);
	
	
	public void serverSendMessage(String serveruserid,ServerMessage serverMessage);
	
}
