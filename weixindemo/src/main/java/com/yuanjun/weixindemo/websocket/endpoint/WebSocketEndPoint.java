package com.yuanjun.weixindemo.websocket.endpoint;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * 这是一个websocket服务
 * 主要是用来获取连接
 * 发送信息的一个服务
 * @author xingwei
 *
 */
@ServerEndpoint("/weixin/{role}")
public class WebSocketEndPoint {

	/**
	 * 用于保存数据的服务   当有信息发送时 都有将数据保存到数据库中
	 */
	private static RedisTemplate<String, String> redisTemplate;
	
	@Resource(name="redisTemplate")
	public static void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		WebSocketEndPoint.redisTemplate = redisTemplate;
	}

	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的Session对象。
    private static CopyOnWriteArraySet<Session> webSocketServer = new CopyOnWriteArraySet<Session>();
    
    private static CopyOnWriteArraySet<Session> webSocketClient = new CopyOnWriteArraySet<Session>();

    /**
     * 连接关闭调用的方法
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("role") String role) {
        if(role.equals("server")){//连接者是服务人员
        	webSocketServer.add(session);
        	System.out.println("有新连接加入！当前连接者是服务人员");
        }else if(role.equals("client")){
        	webSocketClient.add(session);
        	System.out.println("有新连接加入！ 当前连接者是客户人员");
        }
        addOnlineCount();
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session,@PathParam("role") String role) {
    	if(role.equals("server")){//连接者是服务人员
        	webSocketServer.remove(session);
        	System.out.println("有新连接加入！当前连接者是服务人员");
        }else if(role.equals("client")){
        	webSocketClient.remove(session);
        	System.out.println("有新连接加入！ 当前连接者是客户人员");
        }
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        //群发消息
        
    }

    /**
     * 发生错误时调用
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message) throws IOException {
    }

    public static synchronized int getOnlineCount() {
        return WebSocketEndPoint.onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketEndPoint.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
    	WebSocketEndPoint.onlineCount--;
    }
}
