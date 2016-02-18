package com.tianfang.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.order.dto.SportMOrderDto;
import com.tianfang.order.dto.SportMOrderInfoDto;

public interface SportMOrderExMapper {
	List<SportMOrderDto> selectOrder(@Param("orderDto")SportMOrderDto orderDto, @Param("page")PageQuery page);

	long countOrder(@Param("orderDto") SportMOrderDto orderDto);
	
	List<SportMOrderInfoDto> selectOrderInfo(@Param("userId") String userId);
	
	long countUserOrder(@Param("userId") String userId);
}