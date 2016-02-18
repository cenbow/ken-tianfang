package com.tianfang.user.service;

import com.tianfang.user.dto.SportShippingAddressDto;

/**
 * 收货地址
 * @author Administrator
 *
 */
public interface ISportShippingAddressService {
	/**
	 * 添加地址
	 * @param shi
	 * @return
	 */
	long add(SportShippingAddressDto shi);
	/**
	 * 修改
	 * @param shipping
	 * @return
	 */
	long up(SportShippingAddressDto shipping);
	/**
	 * 获取单条信息
	 * @param shipping
	 * @return
	 */
	SportShippingAddressDto getByCriteria(SportShippingAddressDto shipping);
}
