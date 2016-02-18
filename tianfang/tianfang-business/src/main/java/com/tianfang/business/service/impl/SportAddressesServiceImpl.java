package com.tianfang.business.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.business.dao.SportAddressDao;
import com.tianfang.business.dto.SportAddressesDto;
import com.tianfang.business.pojo.SportAddresses;
import com.tianfang.business.service.ISportAddressesService;
import com.tianfang.common.util.BeanUtils;

@Service
public class SportAddressesServiceImpl implements ISportAddressesService {

	@Autowired
	private SportAddressDao addressDao;

	@Override
	public List<SportAddressesDto> getAddresses(String parendId) {
		List<SportAddresses> addresses = addressDao.getAddresses(parendId);
		if(addresses.size()>0){
			return BeanUtils.createBeanListByTarget(addresses, SportAddressesDto.class);
		}
		return null;
	}

	@Override
	public List<SportAddressesDto> getDistrict(SportAddressesDto addressesDto) {
		SportAddresses res = BeanUtils.createBeanByTarget(addressesDto, SportAddresses.class);
		List<SportAddresses> addresses =addressDao.getDistrict(res);
		if(addresses.size()>0){
			return BeanUtils.createBeanListByTarget(addresses, SportAddressesDto.class);
		}
		return null;
	}

	@Override
	public SportAddressesDto getAddressesById(Integer id) {
		return addressDao.selectById(id);
	}

}
