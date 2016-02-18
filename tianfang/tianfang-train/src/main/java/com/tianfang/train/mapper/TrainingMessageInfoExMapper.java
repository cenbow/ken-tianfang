package com.tianfang.train.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.train.dto.TrainingMessageInfoDto;

public interface TrainingMessageInfoExMapper {
	List<TrainingMessageInfoDto> selectMessageInfo(
			@Param("trainingMessageInfoDto") TrainingMessageInfoDto trainingMessageInfoDto,
			@Param("page") PageQuery page);

	long countMessageInfo(@Param("trainingMessageInfoDto") TrainingMessageInfoDto trainingMessageInfoDto);
}
