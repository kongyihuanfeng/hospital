package com.yuanjun.weixindemo;

import java.util.List;

import com.yuanjun.weixindemo.bean.Material;
import com.yuanjun.weixindemo.util.MaterialUtil;
import com.yuanjun.weixindemo.util.WeiXinUtil;

public class TestMaterial {
	public static void main(String[] args) {
//		String accessToken  = WeiXinUtil.getAccess_Token();
		String accessToken = "17_ztdW60kouHpUrDufHaWSqX56V57ukObXMIYBI3vUCsBbgV1i26146rOEUCx8L9KotUcimcvTNlWIwQY13-amgLwxFnmOXIaG0LbkYO4pu42oXifzL04W42GFaMdOXGjsYxSdIWgMB0SAdwNhPBXeAFAZLH";
        List<Material> lists = MaterialUtil.getMaterial(accessToken,"news",0,10);//调用获取素材列表的方法
        System.out.println(lists.size());//输出
        for (Material material : lists) {
        	System.out.println(material.getTitle());
        	System.out.println(material.getThumb_media_id());//输出
		}
        // https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx73c84a347ba6dcf3&secret=abeab1552e45e3c5f2bc1748cb273949
        //token 17_-y95z95aSj1b-HPBi3umJeGodAysNn_ngg7vYKIfpP_9ofG_yK2Cm5sSk1ioQsGOxMuaeA-BlA0FWY-ZP89rI7hTwdZttR1ZBhhLRsA_o1UEuzucs6ZsI1tYwDFFZKq_kp9qhUr6nNOR6UrdVPDfACAPJX
//        美的
//        bf23ETH1fghtDjPnIx0ivP2qT_fPIWOgVg3AazQkwoc
//        格力空调
//        bf23ETH1fghtDjPnIx0ivHI3F4FsayMbYojSS85DCMk
//        二手空调
//        bf23ETH1fghtDjPnIx0ivJ3VBlF4YmajhhqxmFUTx-o
        
        //用户openid
//        "oBFYa0gR8MKfzVBNNjmriNEy_6g0", 
//        "oBFYa0gkTWEYVvosFAjqYDaKLtNQ", 
//        "oBFYa0oBA1qjS083JWbiE-6KWuOc", 
//        "oBFYa0lAKzu48H8SkON9Cwsh00JE", 
//        "oBFYa0pctxRELCNbo96YuxY7yI68", 
//        "oBFYa0nvhzPA-uSpeRcjpb8Koagk"
	}

}
