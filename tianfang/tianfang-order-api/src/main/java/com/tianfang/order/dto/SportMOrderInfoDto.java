package com.tianfang.order.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportMOrderInfoDto implements Serializable {
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
	private String thumbnail;

	@Getter
	@Setter
    private Integer orderStatus;

	@Getter
	@Setter
    private String productSkuId;
	
	@Getter
	@Setter
	private String productSpuId;

	@Getter
	@Setter
    private Integer number;

	@Getter
	@Setter
    private String skuName;
	
	@Getter
	@Setter
    private String nickName;

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
	
	@Getter
	@Setter
	private String orderNo;
	
	@Getter
	@Setter
	private String createTimeStr;
	
	@Getter
	@Setter
	private Integer evaluateStat;

}