package com.tianfang.train.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.util.BeanUtils;
import com.tianfang.train.dao.TrainingCourseDao;
import com.tianfang.train.dto.TrainingCourseDto;
import com.tianfang.train.dto.TrainingCourseDtoX;
import com.tianfang.train.service.ICourseQueryService;

@Service
public class CourseQueryServiceImpl implements ICourseQueryService{

	@Autowired
	private TrainingCourseDao trainingCourseDao;
	
	@Override
	public List<TrainingCourseDtoX> findAll() {
		List<TrainingCourseDtoX> courseList =  trainingCourseDao.getACourseLst("1", "");
		List<TrainingCourseDtoX> reList = new ArrayList<TrainingCourseDtoX>();
		if(null != courseList){
			reList = BeanUtils.createBeanListByTarget(courseList, TrainingCourseDto.class);
		}
		return reList;
	}

}
