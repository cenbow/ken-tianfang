package com.tianfang.order.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 购物车
 * @author mr
 *
 */
public class SportMShoppingCartDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String id;    //UUID
	
	@Getter
	@Setter
	private String productName;  //商品名称
	
	@Getter
	@Setter
	private String productId;  //商品id
	
	@Getter
	@Setter
	private String skuId;   //skuId;
	
	@Getter
	@Setter
	private String imgUrl;
	
	@Getter
	@Setter
    private String spuId;
	
	@Getter
	@Setter
	private Integer numberOf;   //数量
	
	@Getter
	@Setter
	private Double  price;  //价格
	
	@Getter
	@Setter
	private String specId;  //specId
	
	@Getter
	@Setter
	private String specStr; //specName 
	
	@Getter
	@Setter
	private String specValuesId; //specValueId
	
	@Getter
	@Setter
	private String specValuesIdStr ; //specValuesIdName 
	
	@Getter
	@Setter
	private int productStock;   //商品库存
	
	@Getter
	@Setter
	private String ids;//specId*specValueId,specId*specValueId,specId*specValueId ids的数据格式先用split(",")分割，再用split("//*")分割
}
