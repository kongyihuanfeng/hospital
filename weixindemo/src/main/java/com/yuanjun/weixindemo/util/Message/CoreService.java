package com.yuanjun.weixindemo.util.Message;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yuanjun.weixindemo.translate.TransApi;
import com.yuanjun.weixindemo.util.TulingApiUtil;
import com.yuanjun.weixindemo.util.WxUserUtil;

/**
 * 处理微信发送的消息。
 * @author xingwei
 * 
 */
public class CoreService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		// 默认返回的文本消息内容
		String respContent = null;
		try {
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.xmlToMap(request);
			// 发送方帐号（open_id）
			String FromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String ToUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 内容
			String Content = requestMap.get("Content");

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				TextMessageUtil textMessage = new TextMessageUtil();
				if ("图片".equals(Content)) {
					ImageMessageUtil util = new ImageMessageUtil();
					respContent = util.initMessage(FromUserName, ToUserName);
				} else if ("1".equals(Content)) {
					respContent = textMessage.initMessage(FromUserName, ToUserName);
				} else if (Content.startsWith("翻译")) {
					// 设置翻译的规则，以翻译开头
					// 将翻译开头置为""
					String query = Content.replaceAll("^翻译", "");
					if (query == "") {
						// 如果查询字段为空，调出使用指南
						respContent = textMessage.initMessage(FromUserName, ToUserName);
					} else {
						respContent = textMessage.initMessage(FromUserName, ToUserName, TransApi.getTransResult(query));
					}
				} else {
					String robotResult = TulingApiUtil.getTulingResult(Content);
					respContent = textMessage.initMessage(FromUserName, ToUserName, robotResult);
				}
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是音频消息！";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "谢谢您的关注！";
					//WxUserUtil.getUserInfo(accessToken, openId);
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// TODO 自定义菜单权没有开放，暂不处理该类消息
					TextMessageUtil textMessage = new TextMessageUtil();

					String eventkey = requestMap.get("EventKey");
					if ("1".equals(eventkey)) {
						respContent = textMessage.initMessage(FromUserName, ToUserName, "您发送的是1！");
					} else {
						respContent = textMessage.initMessage(FromUserName, ToUserName, "您发送的是点击消息！");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return respContent;
	}
}
