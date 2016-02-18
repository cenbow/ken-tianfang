package com.tianfang.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.user.dto.SportShippingAddressDto;
import com.tianfang.user.mapper.SportShippingAddressMapper;
import com.tianfang.user.pojo.SportShippingAddress;
import com.tianfang.user.pojo.SportShippingAddressExample;
import com.tianfang.user.pojo.SportShippingAddressExample.Criteria;

@Repository
public class SportShippingAddressDao  extends MyBatisBaseDao<SportShippingAddress> {
	@Autowired
	private SportShippingAddressMapper mapper;
	@Override
	public Object getMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据userId查询收货地址
	 * @param shipping
	 * @return
	 */
	public SportShippingAddress getByCriteria(SportShippingAddressDto shipping) {
		SportShippingAddressExample example = new SportShippingAddressExample();
		Criteria criteria = example.createCriteria();
		if(shipping.getUserId()!=null &&!shipping.getUserId().equals("")){
			criteria.andUserIdEqualTo(shipping.getUserId());
		}else{
			return null;
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example).size() > 0 ? mapper.selectByExample(example).get(0):null;
	}

	public long insertShpping(SportShippingAddress shipping) {
		try {
			return mapper.insert(shipping);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public long updateShipping(SportShippingAddress shipping) {
		return mapper.updateByPrimaryKey(shipping);
	}

}
