package com.yuanjun.weixindemo.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.yuanjun.weixindemo.bean.AccessToken;
import com.yuanjun.weixindemo.constant.UrlType;
import com.yuanjun.weixindemo.redis.RedisUtil;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

/**
 * 
 * 类名称: WeiXinUtil
 * 类描述:
 * @author xingwei
 */
public class WeiXinUtil {
	/**
	 * 开发者id
	 */
	private static final String APPID = "wx73c84a347ba6dcf3";
	/**
	 * 开发者秘钥
	 */
	private static final String APPSECRET="abeab1552e45e3c5f2bc1748cb273949";
	/**
	 * 处理doget请求
	 * @param url
	 * @return
	 */
	public static JSONObject doGetstr(String url){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		try {
			CloseableHttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity!=null){
				String result = EntityUtils.toString(entity);
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
		
	}
	/**
	 * 处理post请求
	 * @param url
	 * @return
	 */
	public static JSONObject doPoststr(String url,String outStr){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		JSONObject jsonObject = null;
		try {
			httpPost.setEntity(new StringEntity(outStr, "utf-8"));
			CloseableHttpResponse response = httpclient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(),"utf-8");
		    jsonObject =JSONObject.fromObject(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	public static AccessToken getAccessToken(){
		System.out.println("从接口中获取");
		Jedis jedis  = RedisUtil.getJedis();
		AccessToken token = new AccessToken();
		String url = UrlType.ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject json = doGetstr(url);
		if(json!=null){
			token.setAccess_token(json.getString("access_token"));
			token.setExpires_in(json.getInt("expires_in"));
			jedis.set("access_token", json.getString("access_token"));
			jedis.expire("access_token", 60*60*2);
		}
		RedisUtil.returnResource(jedis);
		return token;
	}
	/**
	 * 获取凭证
	 * @return
	 */
	public static String  getAccess_Token(){
		System.out.println("从缓存中读取");
		Jedis jedis  = RedisUtil.getJedis();
		String access_token = jedis.get("access_token");
		if(access_token==null){
			AccessToken token = getAccessToken();
			access_token = token.getAccess_token();
		}
		RedisUtil.returnResource(jedis);
		return access_token;
	}
	
}
