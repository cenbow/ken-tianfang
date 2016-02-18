package com.tianfang.home.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@SuppressWarnings("serial")
@ToString
public class PayDto implements Serializable{
	@Getter
	@Setter
	private String out_trade_no;// 商户订单号
	
	@Getter
	@Setter
	private String subject;// 订单名称
	
	@Getter
	@Setter
	private String total_fee;// 付款金额
	
	@Getter
	@Setter
	private String sHtmlText;
}
