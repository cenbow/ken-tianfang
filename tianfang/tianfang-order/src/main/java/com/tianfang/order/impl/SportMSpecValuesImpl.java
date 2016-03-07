package com.tianfang.order.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.order.dao.SportMProductSpecValuesDao;
import com.tianfang.order.dao.SportMSpecValuesDao;
import com.tianfang.order.dto.SportMProductSpecValuesDto;
import com.tianfang.order.dto.SportMSpecValuesDto;
import com.tianfang.order.pojo.SportMProductSpecValues;
import com.tianfang.order.pojo.SportMSpecValues;
import com.tianfang.order.service.ISportMSpecValuesService;

@Service
public class SportMSpecValuesImpl implements ISportMSpecValuesService{

	@Autowired
	private SportMSpecValuesDao sportMSpecValuesDao;
	
	@Autowired
	private SportMProductSpecValuesDao sportMProductSpecValuesDao;

	@Override
	public long save(SportMSpecValuesDto sportMSpecValue) {
		SportMSpecValues sportVaules = BeanUtils.createBeanByTarget(sportMSpecValue, SportMSpecValues.class);
		return sportMSpecValuesDao.save(sportVaules);
	}

	@Override
	public List<SportMSpecValuesDto> selectByCreate(SportMSpecValuesDto specVal) {
		List<SportMSpecValues> lis = sportMSpecValuesDao.selectByCreate(specVal);
		return	BeanUtils.createBeanListByTarget(lis, SportMSpecValues.class);
	}

	@Override
	public Object delete(String id) {
		String[] ids = id.split(",");
		SportMSpecValuesDto sportMSpecValuesDto = new SportMSpecValuesDto();
		long stat = 0;
		for (String str : ids) {
			SportMProductSpecValuesDto sportMProductSpecValues = new SportMProductSpecValuesDto();
			sportMProductSpecValues.setSpecValuesId(str);
			List<SportMProductSpecValues> specValues = sportMProductSpecValuesDao.findProductSpecValues(sportMProductSpecValues);
			SportMSpecValues sportMSpecValues = sportMSpecValuesDao.selectByPrimaryKey(str);
			if (specValues.size()>0) {
				sportMSpecValuesDto = BeanUtils.createBeanByTarget(sportMSpecValues, SportMSpecValuesDto.class);
				sportMSpecValuesDto.setDeleteStat(DataStatus.DISABLED);
				return sportMSpecValuesDto;
			}
			stat = sportMSpecValuesDao.delete(str);
		}
		for (String str : ids) {
			SportMSpecValues sportMSpecValues = sportMSpecValuesDao.selectByPrimaryKey(str);
			sportMSpecValues.setStat(DataStatus.ENABLED);
			sportMSpecValuesDao.updateByPrimaryKeySelective(sportMSpecValues);			
		}
		sportMSpecValuesDto.setDeleteStat(DataStatus.ENABLED);
		return sportMSpecValuesDto;
	}

	@Override
	public long update(SportMSpecValuesDto sportMSpecValue) {
		SportMSpecValues spec = BeanUtils.createBeanByTarget(sportMSpecValue, SportMSpecValues.class);
		return sportMSpecValuesDao.update(spec);
	}
	
}
