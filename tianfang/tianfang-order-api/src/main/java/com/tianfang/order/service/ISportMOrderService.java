package com.tianfang.order.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.order.dto.SportMOrderDto;
import com.tianfang.order.dto.SportMOrderToolsDto;
import com.tianfang.order.dto.SportMUserOrderDto;

public interface ISportMOrderService {

	long save(SportMOrderDto orderDto);

	PageResult<SportMOrderDto> selectAllOrder(SportMOrderDto orderDto,PageQuery changeToPageQuery);
	
	List<SportMOrderDto> selectAll();

	/**
	 * 联表查询 订单信息
	 * @param orderDto
	 * @param changeToPageQuery
	 * @return
	 */
	PageResult<SportMOrderDto> selectOrder(SportMOrderDto orderDto,	PageQuery changeToPageQuery);
	
	public PageResult<SportMUserOrderDto> findOrderByUser(String userId,PageQuery page);

	SportMOrderDto addOrder(SportMOrderToolsDto orderTools);
	
	public SportMOrderDto findOrderById(String orderId,String orderNo);
	
	public void updateOrderById (String orderId,String orderNo,Integer orderStatus,Integer paymentStatus);
	
	public Integer orderDelete(String orderId);
	
	public int saveOrderStatus(String orderId,Integer orderStatus);
	
	public Integer getOrderStatus(String order_no);
}
