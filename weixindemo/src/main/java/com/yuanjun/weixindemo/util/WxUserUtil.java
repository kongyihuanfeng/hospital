package com.yuanjun.weixindemo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanjun.weixindemo.constant.UrlType;
import com.yuanjun.weixindemo.model.WxUser;

import net.sf.json.JSONObject;

public class WxUserUtil {
	private static Logger log = LoggerFactory.getLogger(WxUserUtil.class);// 定义日志获取状态输出

	/**
	 *    * 获取用户信息    *    * @param accessToken 接口访问凭证    * @param openId 用户标识
	 *    * @return WeixinUserInfo    
	 */
	public static WxUser getUserInfo(String accessToken, String openId) {

		WxUser weixinUserInfo = null;
		// 拼接请求地址
		String requestUrl = UrlType.getWxUserInfo.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 获取用户信息
		JSONObject jsonObject = MaterialUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				weixinUserInfo = new WxUser();
				// 用户的标识
				weixinUserInfo.setOpenid(jsonObject.getString("openid"));
				// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
				weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
				// 用户关注时间
				weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
				// 昵称
				weixinUserInfo.setNickname(jsonObject.getString("nickname"));
				// 用户的性别（1是男性，2是女性，0是未知）
				weixinUserInfo.setSex(jsonObject.getInt("sex"));
				// 用户所在国家
				weixinUserInfo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				weixinUserInfo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				weixinUserInfo.setCity(jsonObject.getString("city"));
				// 用户的语言，简体中文为zh_CN
				weixinUserInfo.setLanguage(jsonObject.getString("language"));
				// 用户头像
				weixinUserInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
			} catch (Exception e) {
				if (0 == weixinUserInfo.getSubscribe()) {
					log.error("用户{}已取消关注", weixinUserInfo.getOpenid());
				} else {
					int errorCode = jsonObject.getInt("errcode");
					String errorMsg = jsonObject.getString("errmsg");
					log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
				}
			}
		}
		return weixinUserInfo;
	}

}
