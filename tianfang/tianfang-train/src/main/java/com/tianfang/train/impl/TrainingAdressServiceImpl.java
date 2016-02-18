package com.tianfang.train.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.util.BeanUtils;
import com.tianfang.train.dao.TrainingAddressDao;
import com.tianfang.train.dto.TrainingAddressDto;
import com.tianfang.train.dto.TrainingCourseAddressDto;
import com.tianfang.train.pojo.TrainingAddress;
import com.tianfang.train.service.ITrainingAdressQueryService;

@Service
public class TrainingAdressServiceImpl implements ITrainingAdressQueryService{

	
	@Autowired
	private TrainingAddressDao trainingAddressDao;
	@Override
	public List<TrainingAddressDto> find(TrainingAddressDto addressDto) {
		List<TrainingAddressDto> reList = new ArrayList<TrainingAddressDto>();
		List<TrainingAddress> addressList= trainingAddressDao.find(addressDto);
		if(reList != null){
			reList = BeanUtils.createBeanListByTarget(addressList, TrainingAddressDto.class);
		}
		return reList;
	}
	
	@Override
	public List<TrainingCourseAddressDto> getCourseAddressesTime(TrainingCourseAddressDto courseAddressDto) {
		List<TrainingCourseAddressDto> reList = new ArrayList<TrainingCourseAddressDto>();
		List<TrainingCourseAddressDto> addressList= trainingAddressDao.getCourseAddressesTime(courseAddressDto);
		if(reList != null){
			reList = BeanUtils.createBeanListByTarget(addressList, TrainingCourseAddressDto.class);
		}
		return reList;
	}

	@Override
	public TrainingAddressDto findById(String id) {
		return trainingAddressDao.findById(id);
	}


}
