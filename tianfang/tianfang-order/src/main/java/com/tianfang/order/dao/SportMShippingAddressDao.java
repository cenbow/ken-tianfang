package com.tianfang.order.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.dto.SportMShippingAddressDto;
import com.tianfang.order.mapper.SportMShippingAddressMapper;
import com.tianfang.order.pojo.SportMShippingAddress;
import com.tianfang.order.pojo.SportMShippingAddressExample;
import com.tianfang.order.pojo.SportMShippingAddressExample.Criteria;

@Repository
public class SportMShippingAddressDao extends MyBatisBaseDao<SportMShippingAddress>{

	@Getter
	@Autowired
	private SportMShippingAddressMapper mappers;
	
	@Override
	public Object getMapper() {
		// TODO Auto-generated method stub
		return mappers;
	}
	
	public List<SportMShippingAddressDto> findPage(SportMShippingAddressDto sportMShippingAddressDto,PageQuery page) {
		SportMShippingAddressExample example = new SportMShippingAddressExample();
		SportMShippingAddressExample.Criteria criteria = example.createCriteria();
		criteriaBy(sportMShippingAddressDto, criteria);
	    criteria.andStatEqualTo(DataStatus.ENABLED);
		List<SportMShippingAddress> sportMShippingAddresses = mappers.selectByExample(example);
		List<SportMShippingAddressDto> results = BeanUtils.createBeanListByTarget(sportMShippingAddresses, SportMShippingAddressDto.class);
		return results;
	}

	public long count(SportMShippingAddressDto sportMShippingAddressDto) {
		SportMShippingAddressExample example = new SportMShippingAddressExample();
		SportMShippingAddressExample.Criteria criteria = example.createCriteria();
		criteriaBy(sportMShippingAddressDto, criteria);
	    criteria.andStatEqualTo(DataStatus.ENABLED);
	    return mappers.countByExample(example);
	}
	
	public void criteriaBy(SportMShippingAddressDto sportMShippingAddressDto,Criteria criteria) {
		if (StringUtils.isNotBlank(sportMShippingAddressDto.getName())) {
			criteria.andNameLike("%"+sportMShippingAddressDto.getName()+"%");
		}
		if (StringUtils.isNotBlank(sportMShippingAddressDto.getProvince())) {
			criteria.andProvinceLike("%"+sportMShippingAddressDto.getProvince()+"%");
		}
		if (StringUtils.isNotBlank(sportMShippingAddressDto.getPhone())) {
			criteria.andPhoneLike("%"+sportMShippingAddressDto.getPhone()+"%");
		}
		if (StringUtils.isNotBlank(sportMShippingAddressDto.getUserId())){
			criteria.andUserIdEqualTo(sportMShippingAddressDto.getUserId());
		}
	}

	/**
	 * 根据用户id查询默认选中地址列表
	 * @param maker   选中状态
	 * @param userId   用户id
	 * @return
	 */
	public List<SportMShippingAddress> selectByUidOrMarker(Integer maker,String userId) {
		SportMShippingAddressExample example = new SportMShippingAddressExample();
		SportMShippingAddressExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andMakerEqualTo(maker);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mappers.selectByExample(example);
	}
}
