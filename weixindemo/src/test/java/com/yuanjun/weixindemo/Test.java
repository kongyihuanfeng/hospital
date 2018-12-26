package com.yuanjun.weixindemo;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.UUID;

import com.yuanjun.weixindemo.util.UploadUtil;
import com.yuanjun.weixindemo.util.WeiXinUtil;

public class Test {
	public static void main(String[] args) {
//		String access_token = WeiXinUtil.getAccess_Token();
//		String path = "f:/1.jpg";
//		try {
//			String mediaId = UploadUtil.upload(path, access_token, "image");
//			System.out.println(mediaId);
//		} catch (KeyManagementException | NoSuchAlgorithmException
//				| NoSuchProviderException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println(UUID.randomUUID());
	}
}
