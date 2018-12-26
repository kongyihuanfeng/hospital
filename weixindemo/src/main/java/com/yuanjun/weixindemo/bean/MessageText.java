package com.yuanjun.weixindemo.bean;

/**
 * 
 * 类名称: MessageTest
 * 类描述: 消息内容实体
 * @author yuanjun
 * 创建时间:2017年12月5日上午10:41:40
 */
public class MessageText extends BaseMessage {
	
	private String Content;//文本消息内容
	
	private String MsgId;//消息id，64位整型
	
	public MessageText(){
		
	}

	
	public MessageText(String toUserName, String fromUserName,
			long createTime, String msgType, String content, String msgId) {
		super();
		ToUserName = toUserName;
		FromUserName = fromUserName;
		CreateTime = createTime;
		MsgType = msgType;
		Content = content;
		MsgId = msgId;
	}


	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
	
	
}
