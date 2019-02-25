package com.yuanjun.weixindemo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信关注者信息。
 * @author xingwei
 *
 */
@Getter
@Setter
@Entity
public class WxUser {
	
	// 用户的标识
	@Id
	@Column(length=32)
	  private String openid;
	  // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
	  private int subscribe;
	  // 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	  private String subscribeTime;
	  // 昵称
	  private String nickname;
	  // 用户的性别（1是男性，2是女性，0是未知）
	  private int sex;
	  // 用户所在国家
	  private String country;
	  // 用户所在省份
	  private String province;
	  // 用户所在城市
	  private String city;
	  // 用户的语言，简体中文为zh_CN
	  private String language;
	  // 用户头像
	  @Column(length=200)
	  private String headimgurl;
	  //备注
	  private String remark;
	  //分组id
	  private int groupid;
	  
	  private String subscribe_scene;
	  private int qr_scene;
	  private String qr_scene_str;
	 
	  //只有在用户将公众号绑定到微信开放平台帐号后
	  private String unionid;
}
