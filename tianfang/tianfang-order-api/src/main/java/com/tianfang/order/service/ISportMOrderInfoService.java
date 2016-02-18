package com.tianfang.order.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.order.dto.SportMOrderInfoDto;

public interface ISportMOrderInfoService {

	PageResult<SportMOrderInfoDto> selectOrderInfo(	SportMOrderInfoDto orderInfoDto, PageQuery changeToPageQuery);
	
	List<SportMOrderInfoDto> selectAll( SportMOrderInfoDto orderInfo);

	/**
	 * 联表查询获取订单详情数据
	 * @param orderInfoDto
	 * @param changeToPageQuery
	 * @return
	 */
	PageResult<SportMOrderInfoDto> findOrderInfo(SportMOrderInfoDto orderInfoDto, PageQuery changeToPageQuery);
	
	public List<SportMOrderInfoDto> findOrderInfoById(String orderId);
	
	public List<SportMOrderInfoDto> findOrderInfoByNo(String orderIdNo);
	
	public void updateOrderInfoById (String id,Integer orderStatus);
    }
