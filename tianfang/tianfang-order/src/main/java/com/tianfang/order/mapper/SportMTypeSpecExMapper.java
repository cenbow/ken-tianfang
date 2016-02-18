package com.tianfang.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.order.dto.SportMSpecDto;
import com.tianfang.order.dto.SportMSpecValuesDto;
import com.tianfang.order.dto.SportTypeSpecExDto;

public interface SportMTypeSpecExMapper {

	List<SportTypeSpecExDto> selectTypeSpec(@Param("spexDto") SportTypeSpecExDto spexDto);
    
	long countTypeSpec(@Param("spexDto") SportTypeSpecExDto spexDto);
	
	List<SportMSpecDto> findSpecByTypeId(@Param("typeId") String typeId);
}