package com.tianfang.train.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.train.dto.TrainingUserDto;
import com.tianfang.train.dto.UserCourseDayDto;

public interface TrainingUserExMapper {
	List<TrainingUserDto> selectUserInfo(
			@Param("trainingUserDto") TrainingUserDto trainingUserDto,
			@Param("page") PageQuery page);

	List<TrainingUserDto> selectUserRecord(
			@Param("trainingUserDto") TrainingUserDto trainingUserDto);

	Long count();
	
	Integer updateUser(Map<String, Object> map);

	/***
	 * 下拉框显示值处理
	 * 
	 * @param trainingUserDto
	 * @return
	 */
	// List<TrainingCourseDtoX> selectAddressByCourseName(@Param("courseId")
	// Integer courseId);
	//
	// List<TrainingAddressDto> getAllAddressInfo(@Param("id") Integer id);

	/***
	 * 课程情况统计
	 * 
	 * @param id
	 * @return
	 */
	UserCourseDayDto findCourseCount(@Param("id") String id,
			@Param("courseId") String courseId,
			@Param("classId") String classId, @Param("type") Object type);
}
