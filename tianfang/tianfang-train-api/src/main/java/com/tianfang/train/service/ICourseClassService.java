package com.tianfang.train.service;

import java.util.List;

import com.tianfang.train.dto.CourseClassAdressTimeDto;
import com.tianfang.train.dto.CourseClassDtoX;
import com.tianfang.train.dto.CourseClassReqDto;
import com.tianfang.train.dto.TrainingAddressDtoX01;

public interface ICourseClassService {

	CourseClassDtoX findById(String id);
	
	/**
	 * 返回报名未满的指定class,已满的话返回空列表
	 * @param id
	 * @return
	 */
	List<CourseClassDtoX> findAvailableCourseClassByClassId(String id);
	
	/**
	 * 获取class相关的course  address  time 信息
	 * @param id
	 * @return
	 */
	CourseClassAdressTimeDto getCourseClassAdressTimeInfoByClassid(String id);
	
	/**
	 * 通过classId查询课程（小节）
	 * @param id
	 * @return
	 * @2015年9月7日
	 */
	public CourseClassDtoX getCCDet(String id);
	
	/**
	 * 查询所有场地信息
	 * @return
	 * @2015年9月8日
	 */
	public List<TrainingAddressDtoX01> getAllAddrs();
	
	public List<CourseClassReqDto> findByCourseId(String courseId);
}
