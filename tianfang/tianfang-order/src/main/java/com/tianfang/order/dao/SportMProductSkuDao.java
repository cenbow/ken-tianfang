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
import com.tianfang.order.dto.SportMEvaluateDto;
import com.tianfang.order.dto.SportMOrderInfoDto;
import com.tianfang.order.dto.SportMProductSkuDto;
import com.tianfang.order.dto.SportMProductSkuSpecValuesDto;
import com.tianfang.order.dto.SportMProductSpecDto;
import com.tianfang.order.dto.SportMProductSpecValuesDto;
import com.tianfang.order.mapper.SportMProductSkuExMapper;
import com.tianfang.order.mapper.SportMProductSkuMapper;
import com.tianfang.order.pojo.SportMProductSku;
import com.tianfang.order.pojo.SportMProductSkuExample;
import com.tianfang.order.pojo.SportMSpecValues;

@Repository
public class SportMProductSkuDao extends MyBatisBaseDao<SportMProductSku>{

	@Getter
	@Autowired
	private SportMProductSkuMapper mappers;
	
	@Getter
	@Autowired
	private SportMProductSkuExMapper exMappr;	

	@Override
	public Object getMapper() {
		// TODO Auto-generated method stub
		return mappers;
	}
	
	public List<SportMProductSpecDto> selectSkuSpecGroup(String productSkuId) {
		List<SportMProductSpecDto> sportMProductSpecDtos = exMappr.selectSkuSpecGroup(productSkuId);
		return sportMProductSpecDtos;
	}
	
	public List<SportMProductSpecValuesDto> selectSkuSpecValuesGroup(String productSkuId) {
		List<SportMProductSpecValuesDto> sportMProductSpecValuesDtos = exMappr.selectSkuSpecValuesGroup(productSkuId);
		return sportMProductSpecValuesDtos;
	}
	
	public List<SportMProductSkuSpecValuesDto> selectSkuSpecValues(String productSkuId) {
		List<SportMProductSkuSpecValuesDto> specValuesDtos = exMappr.selectSkuSpecValues(productSkuId);
		return specValuesDtos;
	}
	
	public List<SportMOrderInfoDto> selectOrderBySpu(String id,PageQuery page) {
		List<SportMOrderInfoDto> sportMOrderInfoDtos = exMappr.selectOrderBySpu(id, page);
		return sportMOrderInfoDtos;
	}
	
	public long selectOrderBySpuCount(String id) {
		long result = exMappr.selectOrderBySpuCount(id);
		return result;
	}
	
	public List<SportMEvaluateDto> selectEvaluate(SportMEvaluateDto sportMEvaluateDto,PageQuery page) {
		List<SportMEvaluateDto> sportMEvaluateDtos = exMappr.selectEvaluate(sportMEvaluateDto, page);
		return sportMEvaluateDtos;
	}
	
	public long selectEvaluateCount(SportMEvaluateDto sportMEvaluateDto) {
		long result = exMappr.selectEvaluateCount(sportMEvaluateDto);
		return result;
	}
	
	public List<SportMProductSkuDto> findPage(SportMProductSkuDto sportMProductSkuDto,PageQuery page) {
		List<SportMProductSkuDto> results = exMappr.selectProductSkuByPage(sportMProductSkuDto, page);
		return results;
	}
	
	public long count(SportMProductSkuDto sportMProductSkuDto) {
		/*SportMProductSkuExample example = new SportMProductSkuExample();
		SportMProductSkuExample.Criteria criteria = example.createCriteria();
		if (null != sportMProductSkuDto && StringUtils.isNotBlank(sportMProductSkuDto.getProductId())) {
			criteria.andProductIdEqualTo(sportMProductSkuDto.getProductId());
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mappers.countByExample(example);*/
		return exMappr.selectProductSkuCount(sportMProductSkuDto);
	}
	
	public SportMProductSku findSkuByProductId(String productId) {
		SportMProductSkuExample example = new SportMProductSkuExample();
		SportMProductSkuExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(productId)) {
			criteria.andProductIdEqualTo(productId);
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		List<SportMProductSku> sportMProductSkus = mappers.selectByExample(example);
		SportMProductSku sportMProductSku = new SportMProductSku();
		if (sportMProductSkus.size() > 0) {
			sportMProductSku = sportMProductSkus.get(0);
		}
		return sportMProductSku;
	}

	public List<SportMProductSku> findAllSku() {
		SportMProductSkuExample example = new SportMProductSkuExample();
		SportMProductSkuExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mappers.selectByExample(example);
	}
	
	public List<SportMProductSkuDto> findAllSkuByProductId(String productId) {
		SportMProductSkuExample example = new SportMProductSkuExample();
		SportMProductSkuExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(productId)) {
			criteria.andProductIdEqualTo(productId);
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		List<SportMProductSku> sportMProductSkus = mappers.selectByExample(example);
		return BeanUtils.createBeanListByTarget(sportMProductSkus, SportMProductSkuDto.class);
	}

	public List<SportMProductSku> findSkuByProductIdList(String productId) {
		SportMProductSkuExample example = new SportMProductSkuExample();
		SportMProductSkuExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(productId)) {
			criteria.andProductIdEqualTo(productId);
		}
		criteria.andProductStatusEqualTo(DataStatus.ENABLED);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mappers.selectByExample(example);
	}
	
	public List<SportMProductSku> findSkuByCriteria(SportMProductSku sku) {
		SportMProductSkuExample example = new SportMProductSkuExample();
		SportMProductSkuExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(sku.getSpecName())) {
			criteria.andSpecNameEqualTo(sku.getSpecName());
		}
		criteria.andProductStatusEqualTo(DataStatus.ENABLED);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mappers.selectByExample(example);
	}
}
