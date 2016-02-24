package com.tianfang.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.order.dto.SportMOrderDto;
import com.tianfang.order.dto.SportMOrderInfoDto;
import com.tianfang.order.pojo.SportMOrder;

public interface SportMOrderExMapper {
	List<SportMOrderDto> selectOrder(@Param("orderDto")SportMOrderDto orderDto, @Param("page")PageQuery page);

	long countOrder(@Param("orderDto") SportMOrderDto orderDto);
	
	List<SportMOrderInfoDto> selectOrderInfo(@Param("userId") String userId);
	
	long countUserOrder(@Param("userId") String userId);

	/**
	 * 查询失效订单
	 * @param dayNumber 失效天数
	 * @return
	 */
	List<SportMOrder> selectDestroyOrder(@Param("dayNumber") long dayNumber);

	/**
	 * 修改所有失效订单的状态
	 * @param dayNumber 
	 * @return
	 */
	long updateOrderStat(@Param("dayNumber")long dayNumber);
}