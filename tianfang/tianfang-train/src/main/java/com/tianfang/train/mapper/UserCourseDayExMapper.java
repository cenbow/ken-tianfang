package com.tianfang.train.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.train.pojo.UserCourseDay;

public interface UserCourseDayExMapper {
	
	/**
	 * 批量保存点名记录
	 * 
	 * @param days
	 * @return
	 */
	void insertList(@Param("list") List<UserCourseDay> list);

	/**
	 * 批量更新点名记录
	 * 
	 * @param days
	 * @return
	 */
	void update(@Param("day") UserCourseDay day);
}