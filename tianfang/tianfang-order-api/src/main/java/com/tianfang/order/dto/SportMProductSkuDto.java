package com.tianfang.order.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportMProductSkuDto implements Serializable{
	private static final long serialVersionUID = 1L;
    
	@Getter
	@Setter
    private String id;
	
	@Getter
	@Setter
	private String ids;

	@Getter
	@Setter
    private String productId;

	@Getter
	@Setter
    private String productName;

	@Getter
	@Setter
	private String thumbnail;
	
	@Getter
	@Setter
    private String brandId;
	
	@Getter
	@Setter
	private String brandName;

	@Getter
	@Setter
    private String categoryId;
	
	@Getter
	@Setter
	private String categoryName;
	
	@Getter
	@Setter
	private String typeName;

	@Getter
	@Setter
    private String typeId;

	@Getter
	@Setter
    private Double productPrice;

	@Getter
	@Setter
    private Double productMarketPrice;

	@Getter
	@Setter
    private Double productCostPrice;

	@Getter
	@Setter
    private String specId;
	
	@Getter
	@Setter
    private String specName;

	@Getter
	@Setter
    private String specValueId;
	
	@Getter
	@Setter
	private String specValue;

	@Getter
	@Setter
    private Integer productStatus;

	@Getter
	@Setter
    private Integer productStock;

	@Getter
	@Setter
    private Date createTime;
	
	@Setter
	@Getter
	private String createDate;

	@Getter
	@Setter
    private Date lastUpdateTime;

	@Getter
	@Setter
    private Integer stat;

}