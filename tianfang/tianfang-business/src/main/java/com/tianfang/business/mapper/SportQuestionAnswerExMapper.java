package com.tianfang.business.mapper;

import com.tianfang.business.dto.SportSfQuestionExDto;
import com.tianfang.business.dto.SportSfResultExDto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SportQuestionAnswerExMapper {
   
	/**
	 * 获取关联表信息
	 * @param dto
	 * @return
	 */
	List<SportSfQuestionExDto> selectInfo(@Param("dto") SportSfQuestionExDto dto);
	
	/**
	 * 连表查询result 集合
	 * @param resultDto
	 * @param page
	 * @return
	 */
	List<SportSfResultExDto> selectOrSfCriteria(@Param("params") SportSfResultExDto params);

	long countOrSfCriterias(@Param("params") SportSfResultExDto params);
	
}