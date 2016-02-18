package com.tianfang.order.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportMProductSpuDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String productName;

	@Getter
	@Setter
	private String thumbnail;
	
	@Getter
	@Setter
	private String pic;
	
	@Getter
	@Setter
    private String brandId;

	@Getter
	@Setter
    private String categoryId;

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
    private Integer productStatus;   //上下架状态

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
	private String brandName;
	
	@Getter
	@Setter
	private String typeName;
	
	@Getter
	@Setter
	private String categoryName;

	@Getter
	@Setter
	private long start;
	@Getter
	@Setter
	private Integer limit;
    @Getter
    @Setter
    private Integer badEvaluate;

    @Getter
    @Setter
    private Integer inEvaluate;

    @Getter
    @Setter
    private Integer goodEvaluate;
    
    @Getter
    @Setter
    private Integer goodStat;
    
    @Getter
    @Setter
    private Integer priceStat;  //0.不排序  1.降序  2. 升序    --------价格
    
    @Setter
    @Getter
    private String content;
    
    @Getter
    @Setter
    private Integer countEvaluate; //评论总数  0.不排序  1. 降序  2升序 
    @Getter
    @Setter
    private Integer newStat; // 更新时间 0.不排序 1.降序 2 升序
    @Setter 
    @Getter
    private Integer star;
    @Setter 
    @Getter
    private Integer typeStat;
}