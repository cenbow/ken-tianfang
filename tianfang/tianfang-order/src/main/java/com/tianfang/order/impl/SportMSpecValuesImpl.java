package com.tianfang.order.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.util.BeanUtils;
import com.tianfang.order.dao.SportMSpecValuesDao;
import com.tianfang.order.dto.SportMSpecValuesDto;
import com.tianfang.order.pojo.SportMSpecValues;
import com.tianfang.order.service.ISportMSpecValuesService;

@Service
public class SportMSpecValuesImpl implements ISportMSpecValuesService{

	@Autowired
	private SportMSpecValuesDao sportMSpecValuesDao;

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
	public long delete(String id) {
		String[] ids = id.split(",");
		long stat = 0;
		for (String str : ids) {
			stat = sportMSpecValuesDao.delete(str);
		}
		return stat;
	}

	@Override
	public long update(SportMSpecValuesDto sportMSpecValue) {
		SportMSpecValues spec = BeanUtils.createBeanByTarget(sportMSpecValue, SportMSpecValues.class);
		return sportMSpecValuesDao.update(spec);
	}
	
}
