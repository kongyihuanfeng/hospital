package com.yuanjun.weixindemo.translate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;


public class TransApi {
	//百度翻译的接口地址
    private static final String TRAN_API_URL = "http://api.fanyi.baidu.com/api/trans/vip/translate";
    //百度翻译开发者appid
    private static final String APP_ID = "20181030000227250";
    //开发者秘钥
    private static final String SECURITY_KEY = "zTiXQe28xndkgF_zYSOH"; 
    //翻译源
    private static final String FROM = "auto";
    //翻译结果语言
    private static final String TO  ="auto";
    /**
     * 获取翻译结果
     * @param query
     * @return
     */
    public static String getTransResult(String query) {
        Map<String, String> params = buildParams(query);
        String resultMap = HttpGet.get(TRAN_API_URL, params);
        JSONObject json = JSONObject.fromObject(resultMap);
        StringBuffer buffer = new StringBuffer();
        //保存翻译后的结果
        List<Map> list = (List<Map>) json.get("trans_result");
        for (int i = 0; i < list.size(); i++) {
			buffer.append(list.get(i).get("dst"));
		}
        return buffer.toString(); 
    }

    private static  Map<String, String> buildParams(String query) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", FROM);
        params.put("to", TO);

        params.put("appid", APP_ID);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = APP_ID + query + salt + SECURITY_KEY; // 加密前的原文
        params.put("sign", MD5.md5(src));

        return params;
    }

}
