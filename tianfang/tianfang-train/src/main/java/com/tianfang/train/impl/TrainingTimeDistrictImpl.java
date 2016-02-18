package com.tianfang.train.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.util.BeanUtils;
import com.tianfang.train.dao.TrainingTimeDistrictDao;
import com.tianfang.train.dto.TrainingTimeDistrictDto;
import com.tianfang.train.pojo.TimeDistrict;
import com.tianfang.train.service.ITrainingTimeDistrictService;

@Service
public class TrainingTimeDistrictImpl implements ITrainingTimeDistrictService{

	@Autowired
	private TrainingTimeDistrictDao trainingTimeDistrictImplDao;
	
	@Override
	public TrainingTimeDistrictDto findById(String id) {
		TrainingTimeDistrictDto districtDto = null;
		TimeDistrict district = trainingTimeDistrictImplDao.findById(id);
		if(null != district){
			districtDto = BeanUtils.createBeanByTarget(district, TrainingTimeDistrictDto.class);
		}
		return districtDto;
	}

}
