package com.tianfang.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.order.dto.SportMProductSpuDto;

public interface SportMProductSpuExMapper {

	List<SportMProductSpuDto> selectPageAll(@Param("spu") SportMProductSpuDto spu ,@Param("page") PageQuery page);
	
	long countPageAll(@Param("spu") SportMProductSpuDto spu);
}