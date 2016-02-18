package com.tianfang.order.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
public class SportMOrderToolsDto implements Serializable {
	@Getter
	@Setter
	private String price; //价格 ','分割
	@Getter
	@Setter
	private String number;  //数量   ','分割
	@Getter
	@Setter
	private String skuId;  //skuId ','分割
	@Getter
	@Setter
	private String userId; // userId 当前用户 
	@Getter
	@Setter
	private String sId ; // 收货地址 id
	@Getter
	@Setter
	private Double totalPrices; //商品总价
	@Getter
	@Setter
	private String carId;  //购物车id 
}