package com.tianfang.order.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.dto.SportMProductSpecDto;
import com.tianfang.order.mapper.SportMProductSpecMapper;
import com.tianfang.order.pojo.SportMProductSpec;
import com.tianfang.order.pojo.SportMProductSpecExample;


@Repository
public class SportMProductSpecDao extends MyBatisBaseDao<SportMProductSpec>{

	@Getter
	@Autowired
	private SportMProductSpecMapper mappers;
	
	@Override
	public Object getMapper() {
		// TODO Auto-generated method stub
		return mappers;
	}

	public List<SportMProductSpec> findBySkuId(String skuId) {
		SportMProductSpecExample example = new SportMProductSpecExample();
		SportMProductSpecExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(skuId)) {
			criteria.andProductSkuIdEqualTo(skuId);
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		List<SportMProductSpec> sportMProductSpecs = mappers.selectByExample(example);
		return sportMProductSpecs;
	}	
}
