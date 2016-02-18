package com.tianfang.business.service;

import java.util.List;

import com.tianfang.business.dto.SportAddressesDto;


public interface ISportAddressesService {

	/**
	 * 获取上海所有区域
	 * @param string 
	 * @return
	 */
	List<SportAddressesDto> getAddresses(String parendId);
	/**
	 * 根据条件查询
	 * @param str  ParentId 
	 * @return
	 */
	List<SportAddressesDto> getDistrict(SportAddressesDto addresses);
	
	/**
	 * 根据id获取对象
	 * @param string 
	 * @return
	 */
	SportAddressesDto getAddressesById(Integer id);

}
