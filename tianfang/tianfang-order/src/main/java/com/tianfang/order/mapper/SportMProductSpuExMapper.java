package com.tianfang.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.order.dto.SportMProductSpuDto;

public interface SportMProductSpuExMapper {

	List<SportMProductSpuDto> selectPageAll(@Param("spu") SportMProductSpuDto spu);
	
	long countPageAll(@Param("spu") SportMProductSpuDto spu);
}