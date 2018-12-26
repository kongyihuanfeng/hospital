package com.yuanjun.weixindemo.util.Message;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 * 类名称: MessageUtil
 * 类描述: 
 * @author yuanjun
 * 创建时间:2017年12月8日下午3:20:48
 */
public class MessageUtil {
	/**
	 * 将微信的请求中参数转成map
	 * @param request
	 * @return
	 */
	public static Map<String,String> xmlToMap(HttpServletRequest request){
		Map<String,String> map = new HashMap<String,String>();
		SAXReader reader = new SAXReader();
		InputStream in = null;
		try {
			in = request.getInputStream();
			Document doc = reader.read(in);
			Element root = doc.getRootElement();
			List<Element> list = root.elements();
			for (Element element : list) {
				map.put(element.getName(), element.getText());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map;
	}
	
	public static String showTranslate(){
		StringBuffer buffer  = new StringBuffer();
		buffer.append("智能翻译使用指南：\n");
		buffer.append("1.翻译苹果\n");
		buffer.append("2.翻译apple\n");
		return buffer.toString();
	}
	
}
