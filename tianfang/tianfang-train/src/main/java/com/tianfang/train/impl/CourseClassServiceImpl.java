package com.tianfang.train.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.util.BeanUtils;
import com.tianfang.train.dao.CourseClassDao;
import com.tianfang.train.dao.TrainingAddressDao;
import com.tianfang.train.dto.CourseClassAdressTimeDto;
import com.tianfang.train.dto.CourseClassDtoX;
import com.tianfang.train.dto.CourseClassReqDto;
import com.tianfang.train.dto.CourseClassRespDto;
import com.tianfang.train.dto.TrainingAddressDtoX01;
import com.tianfang.train.service.ICourseClassService;

@Service
public class CourseClassServiceImpl implements ICourseClassService{

	
	@Autowired
	private CourseClassDao courseClassDao;
	@Resource
	private TrainingAddressDao trainingAddressDao;
	private Logger log = Logger.getLogger(getClass());
	
	@Override
	public CourseClassDtoX findById(String id) {
		return courseClassDao.findCourseClassByClassId(id);
	}


	@Override
	public List<CourseClassDtoX> findAvailableCourseClassByClassId(String id) {
		return courseClassDao.findAvailableCourseClassByClassId(id);
	}


	@Override
	public CourseClassAdressTimeDto getCourseClassAdressTimeInfoByClassid(String id) {
		return courseClassDao.getCourseClassAdressTimeInfoByClassid(id);
	}

	@Override
	public CourseClassDtoX getCCDet(String id) {
		CourseClassDtoX ccdtox = courseClassDao.getCCDet(id);
		return ccdtox;
	}

	public List<CourseClassReqDto> findByCourseId(String courseId) {
	    SimpleDateFormat result_form = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
	    List<CourseClassReqDto> courseClassReqDtoLists = new ArrayList<CourseClassReqDto>();
	    List<CourseClassRespDto> courseClassRespDtos = courseClassDao.findClassByCourseId(courseId);
	    for (CourseClassRespDto courseClassRespDto : courseClassRespDtos) {
	        CourseClassReqDto courseClassReqDto = BeanUtils.createBeanByTarget(courseClassRespDto, CourseClassReqDto.class);
	        courseClassReqDto.setOpenDate(result_form.format(new Date(courseClassRespDto.getOpenDate()*1000)));
	        courseClassReqDtoLists.add(courseClassReqDto);
	    }
	    return courseClassReqDtoLists;
	}
	

	@Override
	public List<TrainingAddressDtoX01> getAllAddrs() {
		return trainingAddressDao.getAllAddrs();
	}
}
