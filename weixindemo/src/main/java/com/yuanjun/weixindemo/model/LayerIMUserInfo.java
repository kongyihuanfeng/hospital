package com.yuanjun.weixindemo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LayerIMUserInfo {

	private String id;
	private String username;
	private String status;
	private String sign;
	private String avatar="../../static/admin/images/default_avatar.gif";
	private String message;
	private String type = "friend";
	private String msgType = "1";
	private String fromid;
	public static LayerIMUserInfo transformUser(WebUser user,String message) {
		LayerIMUserInfo layerIMUser = new LayerIMUserInfo();
		layerIMUser.id = user.getGid();
		layerIMUser.fromid = layerIMUser.id;
		layerIMUser.username = user.getName();
		layerIMUser.status = user.getStatus();
		layerIMUser.sign = user.getSign();
		if(user.getPhotoPath()!=null)
			layerIMUser.avatar = user.getPhotoPath();
		layerIMUser.message = message;
		return layerIMUser;
	}
}
