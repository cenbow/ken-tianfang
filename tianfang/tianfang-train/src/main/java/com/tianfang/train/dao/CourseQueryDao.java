package com.tianfang.train.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.train.mapper.TrainingCourseMapper;
import com.tianfang.train.pojo.TrainingCourse;

@Repository
public class CourseQueryDao{

	@Autowired
	private TrainingCourseMapper mapper;	


	public List<TrainingCourse> findAll(){
		return mapper.selectByExample(null);
	}
}
