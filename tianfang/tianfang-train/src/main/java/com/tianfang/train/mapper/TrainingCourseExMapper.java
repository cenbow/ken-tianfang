package com.tianfang.train.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.train.dto.TrainingCourseDtoX;
import com.tianfang.train.pojo.TrainingCourse;

public interface TrainingCourseExMapper {
	List<TrainingCourseDtoX> selectCourseInfo(@Param("trainingCourseDto") TrainingCourseDtoX trainingCourseDto,@Param("page") PageQuery page);

	Long count(@Param("trainingCourseDto") TrainingCourseDtoX trainingCourseDto);
	
	Integer save(TrainingCourse trainingCourse);

	Integer updateByTrainingCourse(TrainingCourse trainingCourse);
	/**
	 * 更新actual_student +1
	 * @param courseId
	 */
	void addOneActualStudent(String courseId);
}
