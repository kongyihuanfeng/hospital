package com.yuanjun.weixindemo.util.Message;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;

import com.thoughtworks.xstream.XStream;
import com.yuanjun.weixindemo.bean.Image;
import com.yuanjun.weixindemo.bean.ImageMessage;
import com.yuanjun.weixindemo.util.BaseMessageUtil;
import com.yuanjun.weixindemo.util.UploadUtil;
import com.yuanjun.weixindemo.util.WeiXinUtil;

public class ImageMessageUtil implements BaseMessageUtil<ImageMessage> {
	/**
	 * 将信息转为xml格式
	 */
	public String messageToxml(ImageMessage imageMessage) {
		XStream xtream = new XStream();
		xtream.alias("xml", imageMessage.getClass());
		xtream.alias("Image", new Image().getClass());
		return xtream.toXML(imageMessage);
	}
	/**
	 * 封装信息
	 */
	public String initMessage(String FromUserName, String ToUserName) {
		Image image = new Image();
		image.setMediaId(getmediaId());
		ImageMessage message = new ImageMessage();
		message.setFromUserName(ToUserName);
		message.setToUserName(FromUserName);
		message.setCreateTime(new Date().getTime());
		message.setImage(image);
		message.setMsgType("image");
		return messageToxml(message);
	}
	/**
	 * 获取Media
	 * @return
	 */
	public String getmediaId(){
		String path = "f:/1.jpg";
		String accessToken = WeiXinUtil.getAccess_Token();
		String mediaId = null;
		try {
			mediaId = UploadUtil.upload(path, accessToken, "image");
			
		} catch (KeyManagementException | NoSuchAlgorithmException
				| NoSuchProviderException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mediaId;
	}
}
