package com.tianfang.order.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.dto.SportMProductSpecValuesDto;
import com.tianfang.order.mapper.SportMProductSpecValuesMapper;
import com.tianfang.order.pojo.SportMProductSpecValues;
import com.tianfang.order.pojo.SportMProductSpecValuesExample;

@Repository
public class SportMProductSpecValuesDao extends MyBatisBaseDao<SportMProductSpecValues>{

	@Getter
	@Autowired
	private SportMProductSpecValuesMapper mappers;

	@Override
	public Object getMapper() {
		// TODO Auto-generated method stub
		return mappers;
	}
	
	public List<SportMProductSpecValues> findBySukId(String skuId) {
		SportMProductSpecValuesExample example = new SportMProductSpecValuesExample();
		SportMProductSpecValuesExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(skuId)) {
			criteria.andProductSkuIdEqualTo(skuId);
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		List<SportMProductSpecValues> specValues = mappers.selectByExample(example);
		return specValues;
	}
}
