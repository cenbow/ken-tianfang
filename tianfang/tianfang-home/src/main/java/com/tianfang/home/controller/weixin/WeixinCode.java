package com.tianfang.home.controller.weixin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class WeixinCode {

	/**
	 * 请求类型
	 */
	@Getter
	@Setter
	private String type;
	@Getter
	@Setter
	private String access_token;
	@Getter
	@Setter
	private String expires_in;
	@Getter
	@Setter
	private String refresh_token;
	@Getter
	@Setter
	private String openid;

}
