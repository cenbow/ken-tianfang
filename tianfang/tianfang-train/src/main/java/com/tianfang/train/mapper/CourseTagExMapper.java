package com.tianfang.train.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tianfang.train.dto.CourseTagDto;
import com.tianfang.train.pojo.CourseTag;

public interface CourseTagExMapper {
	/***
	 * 通过标签名查询标签信息来检测是否标签已存在
	 * 
	 * @param courseTagDto
	 * @return
	 */
	CourseTag selectTagInfoByTagName(
			@Param("courseTagDto") CourseTagDto courseTagDto);

	/***
	 * 如果标签存在但只是状态为0，重新设置状态为1
	 * 
	 * @param map
	 * @return
	 */
	Integer updateCourseTagStatus(Map<String, Object> map);

	/***
	 * 删除课程标签
	 * 
	 * @param map
	 * @return
	 */
	boolean deleteCourseTag(Map<String, Object> map);

	/***
	 * 编辑课程标签
	 * 
	 * @param map
	 * @return
	 */
	Integer updateCourseTag(Map<String, Object> map);
	
	/**
	 * 通过id查询courseTag信息
	 * @param id
	 * @return
	 * @2015年9月10日
	 */
	CourseTagDto getCTBId(Map<String, Object> map);
}
