package com.yuanjun.weixindemo;

import com.yuanjun.weixindemo.translate.TransApi;

public class TestTranslate {
	public static void main(String[] args) {
		String query = "苹果手机";
		String query1 = "Hello world";
		System.out.println(TransApi.getTransResult(query));
		System.out.println(TransApi.getTransResult(query1));		
	}
}
