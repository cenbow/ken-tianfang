package com.tianfang.train.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.train.dto.TrainApplyDto;
import com.tianfang.train.pojo.UserCourse;

public interface UserCourseExMapper {
	List<UserCourse> findApplyByPage(@Param("trainApplyDto") TrainApplyDto trainApplyDto,@Param("page") PageQuery page);
	
	Long countApply(@Param("trainApplyDto") TrainApplyDto trainApplyDto);
}
