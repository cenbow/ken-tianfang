package com.tianfang.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.order.dto.SportMCategoryDto;

public interface SportMCategoryExMapper {
	
	List<SportMCategoryDto> selectCategoryByPage(@Param("sportMCategoryDto") SportMCategoryDto sportMCategoryDto,@Param("page") PageQuery page);

}
