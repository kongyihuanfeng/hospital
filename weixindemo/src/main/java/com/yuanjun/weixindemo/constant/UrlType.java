package com.yuanjun.weixindemo.constant;
/**
 * 公共微信接口地址。
 * @author xingwei
 *
 */
public class UrlType {
	
	/**
	 * 开发者id
	 */
	public static final String APPID = "wx73c84a347ba6dcf3";
	/**
	 * 开发者秘钥
	 */
	public static final String APPSECRET="abeab1552e45e3c5f2bc1748cb273949";
	
	// 获取Access_token的接口地址
	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	// 获取素材列表
	public final static String MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
	
	//获取关注者列表
	public final static String getWxUserList = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=";
	
	//获取关注者的基本信息
	public final static String getWxUserInfo = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";

	//用户授权的url
	public final static String wxAuthURL="https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appId}&redirect_uri=${callBackUrl}&response_type=code&scope=${scope}&state=STATE#wechat_redirect";
	
	//获取授权access_token
	public final static String getAuthAccessTokenURL="https://api.weixin.qq.com/sns/oauth2/access_token?appid=${appId}&secret=${secret}&code=${code}&grant_type=authorization_code";
	
	//刷新 access_token 获取30天的期限
	public final static String refreshAuthAccessTokenUrl="https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=${appId}&grant_type=refresh_token&refresh_token=${refresh_token}";
	
	//获取授权用户的信息
	public final static String getWxAuthUserInfoURL="https://api.weixin.qq.com/sns/userinfo?access_token=${access_token}&openid=${openId}&lang=zh_CN";
	
	
	
}
