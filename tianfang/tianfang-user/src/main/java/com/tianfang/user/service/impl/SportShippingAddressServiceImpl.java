package com.tianfang.user.service.impl;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.user.dao.SportShippingAddressDao;
import com.tianfang.user.dto.SportShippingAddressDto;
import com.tianfang.user.pojo.SportShippingAddress;
import com.tianfang.user.service.ISportShippingAddressService;

@Service
public class SportShippingAddressServiceImpl implements ISportShippingAddressService {

	@Autowired
	@Getter
	private SportShippingAddressDao SSADao;

	@Override
	public long add(SportShippingAddressDto shi) {
		SportShippingAddress shipping = BeanUtils.createBeanByTarget(shi, SportShippingAddress.class);
		shipping.setId(UUID.randomUUID()+"");
		shipping.setCreateTime(new Date());
		shipping.setStat(DataStatus.ENABLED);
		long stat = 0;
	    stat=SSADao.insertShpping(shipping);
		return stat;
	}

	@Override
	public long up(SportShippingAddressDto shi) {
		SportShippingAddress oldShipping = SSADao.getByCriteria(shi);
		SportShippingAddress shipping = BeanUtils.createBeanByTarget(shi, SportShippingAddress.class);
		shipping.setId(oldShipping.getId());
		shipping.setCreateTime(oldShipping.getCreateTime());
		shipping.setStat(DataStatus.ENABLED);
		shipping.setLastUpdateTime(new Date());
		long stat = 0;
		try {
			stat = SSADao.updateShipping(shipping);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return stat;
	}

	@Override
	public SportShippingAddressDto getByCriteria(SportShippingAddressDto shi) {
		SportShippingAddress shipping = SSADao.getByCriteria(shi);
		if(shipping == null){
			return new SportShippingAddressDto();
		}
		return BeanUtils.createBeanByTarget(shipping, SportShippingAddressDto.class);
	}
}
