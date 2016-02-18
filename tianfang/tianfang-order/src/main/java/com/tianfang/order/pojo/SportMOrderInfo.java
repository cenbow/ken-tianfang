package com.tianfang.order.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportMOrderInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
    private String orderInfoNo;

	@Getter
	@Setter
    private String orderId;

	@Getter
	@Setter
    private Integer orderStatus;

	@Getter
	@Setter
    private String productSkuId;

	@Getter
	@Setter
    private Integer number;

	@Getter
	@Setter
    private String skuName;

	@Getter
	@Setter
    private Double skuProductPrice;

	@Getter
	@Setter
    private Integer productStock;

	@Getter
	@Setter
    private Date createTime;

	@Getter
	@Setter
    private Date lastUpdateTime;
	
	@Getter
	@Setter
    private Integer stat;

}