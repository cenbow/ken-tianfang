package com.tianfang.home.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class QrcodeDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Getter
	@Setter
	private String access_token;  //接口调用凭证
	@Getter
	@Setter
	private String expires_in;    //access_token接口调用凭证超时时间，单位（秒）
	@Getter
	@Setter
	private String refresh_token;   //用户刷新access_token
	@Getter
	@Setter
	private String openid;		//授权用户唯一标识
	@Getter
	@Setter
	private String scope;		//用户授权的作用域，使用逗号（,）分隔
	@Getter
	@Setter
	private String unionid;		//只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
}
