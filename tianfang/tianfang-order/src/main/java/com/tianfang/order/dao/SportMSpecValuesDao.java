package com.tianfang.order.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.Getter;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.dto.SportMSpecValuesDto;
import com.tianfang.order.mapper.SportMSpecValuesMapper;
import com.tianfang.order.pojo.SportMSpecValues;
import com.tianfang.order.pojo.SportMSpecValuesExample;
import com.tianfang.order.pojo.SportMSpecValuesExample.Criteria;

@Repository
public class SportMSpecValuesDao extends MyBatisBaseDao<SportMSpecValues>{

	@Autowired
	@Getter
	private SportMSpecValuesMapper mapper;
	
	public long save(SportMSpecValues sportVaules) {
		sportVaules.setId(UUID.randomUUID()+"");
		sportVaules.setCreateTime(new Date());
		sportVaules.setLastUpdateTime(new Date());
		sportVaules.setStat(DataStatus.ENABLED);
		long stat =0 ;
		try {
			stat = mapper.insert(sportVaules);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}

	public List<SportMSpecValues> selectByCreate(SportMSpecValuesDto specValues) {
		SportMSpecValuesExample example = new SportMSpecValuesExample();
	    Criteria criteria = example.createCriteria();
	    byCriteria(criteria,specValues);
	    criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}

	private void byCriteria(Criteria criteria, SportMSpecValuesDto specValues) {
		if(!StringUtils.isBlank(specValues.getSpecValue())){
			criteria.andSpecValueLike("%"+specValues.getSpecValue()+"%");
		}
		if(specValues.getSpecValueOrder()!=null){
			criteria.andSpecValueOrderEqualTo(specValues.getSpecValueOrder());
		}
		if(!StringUtils.isBlank(specValues.getSpecId())){
			criteria.andSpecIdEqualTo(specValues.getSpecId());
		}
		if(specValues.getCreateTime()!=null){
			criteria.andCreateTimeEqualTo(specValues.getCreateTime());
		}
	}

	public long delete(String id) {
		SportMSpecValues obj = mapper.selectByPrimaryKey(id);
		obj.setStat(DataStatus.DISABLED);
		long stat = 0;
		try {
			stat = mapper.updateByPrimaryKey(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}

	public long update(SportMSpecValues sportMSpecValue) {
		SportMSpecValues oldSpec =mapper.selectByPrimaryKey(sportMSpecValue.getId());
		checkUp(oldSpec,sportMSpecValue);
		sportMSpecValue.setStat(DataStatus.ENABLED);
		sportMSpecValue.setLastUpdateTime(new Date());
		long stat =0;
		try {
			stat = mapper.updateByPrimaryKey(sportMSpecValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}

	private void checkUp(SportMSpecValues oldSpec,SportMSpecValues sportMSpecValue) {
		if(StringUtils.isBlank(sportMSpecValue.getSpecValue())){
			sportMSpecValue.setSpecValue(oldSpec.getSpecValue());
		}
		if(StringUtils.isBlank(sportMSpecValue.getSpecValueOrder())){
			sportMSpecValue.setSpecValueOrder(oldSpec.getSpecValueOrder());
		}
		if(StringUtils.isBlank(sportMSpecValue.getSpecValuePic())){
			sportMSpecValue.setSpecValuePic(oldSpec.getSpecValuePic());
		}
		if(sportMSpecValue.getCreateTime()==null){
			sportMSpecValue.setCreateTime(oldSpec.getCreateTime());
		}
		if(StringUtils.isBlank(sportMSpecValue.getSpecId())){
			sportMSpecValue.setSpecId(oldSpec.getSpecId());
		}
	}
	
	public List<SportMSpecValuesDto> findValueBySpecId(String specId) {
		SportMSpecValuesExample example = new SportMSpecValuesExample();
		SportMSpecValuesExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(specId)) {
			criteria.andSpecIdEqualTo(specId);
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		List<SportMSpecValues> specValues = mapper.selectByExample(example);
		List<SportMSpecValuesDto> result = BeanUtils.createBeanListByTarget(specValues, SportMSpecValuesDto.class);
		return result;
	}
	
	public SportMSpecValuesDto findByIdAndSpecId(String id,String specId) {
		SportMSpecValuesExample example = new SportMSpecValuesExample();
		SportMSpecValuesExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(id)) {
			criteria.andIdEqualTo(id);
		}
		if (StringUtils.isNotBlank(specId)) {
			criteria.andSpecIdEqualTo(specId);
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		List<SportMSpecValues> specValues = mapper.selectByExample(example);
		SportMSpecValuesDto specValuesDto = new SportMSpecValuesDto();
		if (specValues.size()>0) {
			specValuesDto = BeanUtils.createBeanByTarget(specValues.get(0), SportMSpecValuesDto.class);
		}
		return specValuesDto;
	}
}
