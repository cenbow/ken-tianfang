package com.tianfang.order.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.order.dto.SportMShippingAddressDto;

public interface ISportMShippingAddressService {
	
	public PageResult<SportMShippingAddressDto> findPage(SportMShippingAddressDto sportMShippingAddressDto,PageQuery page);

	public long save(SportMShippingAddressDto shippingAdd);

	/**
	 * 获取全部集合
	 * @param shipping
	 * @return
	 */
	public List<SportMShippingAddressDto> findAll(SportMShippingAddressDto shipping);

	public long delete(String id);
	
	

}
