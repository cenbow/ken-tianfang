package com.tianfang.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.order.dto.SportMOrderInfoDto;

public interface SportMOrderInfoExMapper {

	List<SportMOrderInfoDto> findOrderInfo(@Param("orderInfoDto") SportMOrderInfoDto orderInfoDto,@Param("page")PageQuery page);
	
	long countOrderInfo(@Param("orderInfoDto") SportMOrderInfoDto orderInfoDto);

}
