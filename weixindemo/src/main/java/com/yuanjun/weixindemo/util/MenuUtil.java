package com.yuanjun.weixindemo.util;

import net.sf.json.JSONObject;

import com.yuanjun.weixindemo.bean.Button;
import com.yuanjun.weixindemo.bean.ClickButton;
import com.yuanjun.weixindemo.bean.Menu;
import com.yuanjun.weixindemo.bean.ViewButton;

/**
 * 
 * 类名称: MemuUtil
 * 类描述: 菜单工具
 * @author yuanjun
 * 创建时间:2017年12月8日下午8:42:15
 */
public class MenuUtil {
	
	private static final String CTRATE_MENU_URL  = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	/**
	 * 创建菜单
	 * @param accessToken 
	 * @param Menu 菜单json格式字符串
	 * @return
	 */
	public static int createMenu(String accessToken,String Menu){
		int result = Integer.MIN_VALUE;
		String url = CTRATE_MENU_URL.replaceAll("ACCESS_TOKEN", accessToken);
		JSONObject json = WeiXinUtil.doPoststr(url, Menu);
		if(json!=null){
			//从返回的数据包中取数据{"errcode":0,"errmsg":"ok"}
			result = json.getInt("errcode");
		}
		return result;
	}
	
	public static String initMenu(){
		String result = "";
		//创建点击一级菜单
		ViewButton button11 = new ViewButton();
		button11.setName("美的空调");
		button11.setType("view");
		button11.setUrl("https://mp.weixin.qq.com/s/8D3hA6ELJ_Euo1quhMRtRA");
		ViewButton button12 = new ViewButton();
		button12.setName("格力空调");
		button12.setType("view");
		button12.setUrl("https://mp.weixin.qq.com/s/8D3hA6ELJ_Euo1quhMRtRA");
		ClickButton button13 = new ClickButton();
		button13.setName("点一下");
		button13.setType("click");
		button13.setKey("1");
		//封装到一级菜单
		Button button1 = new Button();
		button1.setName("空调类型");
		button1.setType("click");
		button1.setSub_button(new Button[]{button11,button12,button13});
		
		//创建跳转型一级菜单
		ViewButton button21 = new ViewButton();
		button21.setName("客服服务");
		button21.setType("view");
		button21.setUrl("http://yundou.store/weixin/weixin/index.html");
		
		//创建其他类型的菜单与click型用法一致
		ClickButton button31 = new ClickButton();
		button31.setName("拍照发图");
		button31.setType("pic_photo_or_album");
		button31.setKey("31");
		
		ClickButton button32 = new ClickButton();
		button32.setName("发送位置");
		button32.setKey("32");
		button32.setType("location_select");
		
		//封装到一级菜单
		Button button3 = new Button();
		button3.setName("菜单");
		button3.setType("click");
		button3.setSub_button(new Button[]{button31,button32});
		
		//封装菜单
		Menu menu = new Menu();
		menu.setButton(new Button[]{button1,button21,button3});
		return JSONObject.fromObject(menu).toString();
	}
}
