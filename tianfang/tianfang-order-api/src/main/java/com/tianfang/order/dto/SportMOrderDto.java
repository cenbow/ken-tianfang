package com.tianfang.order.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportMOrderDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String orderNo;

	@Getter
	@Setter
    private Integer orderStatus;

	@Getter
	@Setter
    private Integer paymentStatus;

	@Getter
	@Setter
    private Date orderTime;

	@Getter
	@Setter
    private Double totalPrice;

	@Getter
	@Setter
    private String userId;

	@Getter
	@Setter
    private String shippingAddressId;

	@Getter
	@Setter
    private Date createTime;

	@Getter
	@Setter
    private Date lastUpdateTime;

	@Getter
	@Setter
    private Integer stat;

	@Getter
	@Setter
	private String aname; //收货人地址
	
	@Getter
	@Setter
	private String uname ;//订单提交用户
	@Getter
	@Setter
	private String addressInfo ;//收货地址
	@Getter
	@Setter
	private String skuId ;
	@Getter
	@Setter
	private String skuName ;
}