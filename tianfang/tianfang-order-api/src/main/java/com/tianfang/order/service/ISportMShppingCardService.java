package com.tianfang.order.service;

import java.util.List;

import com.tianfang.order.dto.SportMProductSkuDto;
import com.tianfang.order.dto.SportMProductSpecDto;
import com.tianfang.order.dto.SportMProductSpecValuesDto;
import com.tianfang.order.dto.SportMShoppingCartDto;

public interface ISportMShppingCardService {

	/**
	 * 根据spuId查询sku
	 * @param car
	 * @return
	 */
	List<SportMProductSkuDto> selectByProduct(SportMShoppingCartDto car);

	/**
	 * 查询规格是否存在
	 * @param specId 
	 * @param pid
	 * @return
	 */
	boolean selectBySpec(String[] specId, String pid);

	/**
	 * 查询规格值是否存在
	 * @param specValueId
	 * @param id
	 * @return
	 */
	boolean selectBySpecValue(String[] specValueId, String id);
	
	public SportMProductSpecValuesDto selectBySpecValue(String specValueId,String pid);
	
	public SportMProductSpecDto selectBySpec(String specId, String pid);

}
