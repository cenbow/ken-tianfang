package com.tianfang.train.service;

import java.util.List;

import com.tianfang.train.dto.TrainingAddressDto;
import com.tianfang.train.dto.TrainingCourseAddressDto;


public interface ITrainingAdressQueryService {
	List<TrainingAddressDto> find(TrainingAddressDto addressDto);

	/**
	 * 获取课程相关的地点和时间段
	 * @param courseAddressDto
	 * @return
	 */
	List<TrainingCourseAddressDto> getCourseAddressesTime(TrainingCourseAddressDto courseAddressDto);
	
	
	TrainingAddressDto findById(String id);
}
