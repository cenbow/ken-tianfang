package com.tianfang.order.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.Getter;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.dto.SportMEvaluateDto;
import com.tianfang.order.mapper.SportMEvaluateExMapper;
import com.tianfang.order.mapper.SportMEvaluateMapper;
import com.tianfang.order.pojo.SportMEvaluate;
import com.tianfang.order.pojo.SportMEvaluateExample;

@Repository
public class SportMEvaluateDao extends MyBatisBaseDao<SportMEvaluate>{

	@Getter
	@Autowired
	private SportMEvaluateMapper mappers;
	
	@Getter
	@Autowired
	private SportMEvaluateExMapper exMappers;
	
	@Override
	public Object getMapper() {
		// TODO Auto-generated method stub
		return mappers;
	}
	
	public long countStar(String productSkuId) {
		return exMappers.countStar(productSkuId);
	}
	
	public List<SportMEvaluate> getAllEvaluate(SportMEvaluateDto sportMEvaluateDto) {
		SportMEvaluateExample example = new SportMEvaluateExample();
		SportMEvaluateExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(sportMEvaluateDto.getProductSkuId())) {
			criteria.andProductSkuIdEqualTo(sportMEvaluateDto.getProductSkuId());
		}
		if (null != sportMEvaluateDto.getStar()) {
			criteria.andStarEqualTo(sportMEvaluateDto.getStar());
		}
		if (null != sportMEvaluateDto.getEvaluateStatus()) {
			criteria.andEvaluateStatusEqualTo(sportMEvaluateDto.getEvaluateStatus());
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mappers.selectByExample(example);
	}
}
