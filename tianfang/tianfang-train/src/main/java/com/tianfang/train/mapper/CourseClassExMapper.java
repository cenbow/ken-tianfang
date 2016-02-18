package com.tianfang.train.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.train.dto.CollegelessonDto;
import com.tianfang.train.dto.CourseClassRespDto;
import com.tianfang.train.dto.LocaleClass;
import com.tianfang.train.pojo.TrainingAddress;

public interface CourseClassExMapper {
	
	/**
	 * 根据课程classid查询
	 * 
	 * @param ids
	 * @return
	 */
	List<LocaleClass> findCourseClassByClassIds(@Param("ids") List<String> ids);
	/**
	 * YIN
	 */
	List<TrainingAddress> findSpaceByCourseId(@Param("courseId") String courseId);
	
	List<CourseClassRespDto> findClassByCourseId(@Param("courseId") String courseId);
	
	/**
	 * 根据负责人id查询所有课程
	 * 
	 * @param assistantId
	 * @return
	 */
	List<LocaleClass> findCourseClassByAssistantId(@Param("assistantId") String assistantId);
	
	/**
	 * 
		 * 此方法描述的是：学院-->课程介绍 培训课程数据展示
		 * @author: cwftalus@163.com
		 * @version: 2015年10月10日 上午9:38:06
	 */
	List<CollegelessonDto> findByPage(@Param("page") PageQuery page,@Param("marked") Integer marked);

	/**
	 * 
		 * 此方法描述的是：学院-->课程介绍 培训课程数据展示--数量
		 * @author: cwftalus@163.com
		 * @version: 2015年10月10日 上午9:38:06
	 */
	long count(@Param("marked") Integer marked);
}