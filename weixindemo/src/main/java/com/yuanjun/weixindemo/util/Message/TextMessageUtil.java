package com.yuanjun.weixindemo.util.Message;

import java.util.Date;

import com.thoughtworks.xstream.XStream;
import com.yuanjun.weixindemo.bean.MessageText;
import com.yuanjun.weixindemo.util.BaseMessageUtil;

public class TextMessageUtil implements BaseMessageUtil<MessageText> {

	public String messageToxml(MessageText message) {
		XStream xstream  = new XStream();
		xstream.alias("xml", message.getClass());
		return xstream.toXML(message);
	}

	@Override
	public String initMessage(String FromUserName, String ToUserName) {
		MessageText text = new MessageText();
		text.setToUserName(FromUserName);
		text.setFromUserName(ToUserName);
		//text.setContent("欢迎关注机械振动工程党支部");
		text.setContent(MessageUtil.showTranslate());
		text.setCreateTime(new Date().getTime());
		text.setMsgType("text");
	    return  messageToxml(text);
	}
	
	public String initMessage(String FromUserName, String ToUserName,String Content) {
		MessageText text = new MessageText();
		text.setToUserName(FromUserName);
		text.setFromUserName(ToUserName);
		text.setContent(Content);
		text.setCreateTime(new Date().getTime());
		text.setMsgType("text");
	    return  messageToxml(text);
	}
	
	
}
