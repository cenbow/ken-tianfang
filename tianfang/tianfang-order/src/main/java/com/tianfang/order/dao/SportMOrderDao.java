package com.tianfang.order.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.dto.SportMOrderDto;
import com.tianfang.order.dto.SportMOrderInfoDto;
import com.tianfang.order.mapper.SportMOrderExMapper;
import com.tianfang.order.mapper.SportMOrderMapper;
import com.tianfang.order.pojo.SportMOrder;
import com.tianfang.order.pojo.SportMOrderExample;
import com.tianfang.order.pojo.SportMOrderExample.Criteria;

@Repository
public class SportMOrderDao extends MyBatisBaseDao<SportMOrder>{
	
	@Getter
	@Autowired
	private SportMOrderMapper mappers;
	
	@Getter
	@Autowired
	private SportMOrderExMapper mappersEx;
	
	@Override
	public Object getMapper() {
		// TODO Auto-generated method stub
		return mappers;
	}

	public List<SportMOrder> selectPageAll(SportMOrderDto orderDto,	PageQuery page) {
		SportMOrderExample example = new SportMOrderExample();
		Criteria criteria = example.createCriteria();
		byCriteria(orderDto,criteria);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		if(page!=null){
			example.setOrderByClause("create_time desc limit "+page.getStartNum()+","+page.getPageSize());
		}
		return mappers.selectByExample(example);
	}

	private void byCriteria(SportMOrderDto orderDto, Criteria criteria) {
		if (StringUtils.isNotBlank(orderDto.getOrderNo())){
			criteria.andOrderNoEqualTo(orderDto.getOrderNo());
		}
		if (StringUtils.isNotBlank(orderDto.getUserId())) {
			criteria.andUserIdEqualTo(orderDto.getUserId());
		}
	}

	public long countPageAll(SportMOrderDto orderDto) {
		SportMOrderExample example = new SportMOrderExample();
		Criteria criteria = example.createCriteria();
		byCriteria(orderDto,criteria);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mappers.countByExample(example);
	}

	public List<SportMOrder> selectAll() {
		SportMOrderExample example = new SportMOrderExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mappers.selectByExample(example);
	}

	public List<SportMOrderDto> selectOrder(SportMOrderDto orderDto, PageQuery page) {
		
		return mappersEx.selectOrder(orderDto,page);
	}

	public long countOrder(SportMOrderDto orderDto) {
		return mappersEx.countOrder(orderDto);
	}
	
	public List<SportMOrderInfoDto> selectOrderInfo(String userId) {
		return mappersEx.selectOrderInfo(userId);
	}
	
	public long countUserOrder(String userId) {
		return mappersEx.countUserOrder(userId);
	}
	
	public SportMOrderDto findOrderById(String orderId,String orderNo) {
		SportMOrderExample example = new SportMOrderExample();
		SportMOrderExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(orderId)) {
			criteria.andIdEqualTo(orderId);
		}
		if (StringUtils.isNotBlank(orderNo)) {
			criteria.andOrderNoEqualTo(orderNo);
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		List<SportMOrder> sportMOrders = mappers.selectByExample(example);
		SportMOrderDto sportMOrderDto = new SportMOrderDto();
		if (sportMOrders.size()>0) {
			sportMOrderDto = BeanUtils.createBeanByTarget(sportMOrders.get(0), SportMOrderDto.class);
		}
		return sportMOrderDto;
	}

	/**
	 * 查找失效订单列表
	 * @param dayNumber 失效天数
	 * @return
	 */
	public List<SportMOrder> selectDestroyOrder(long dayNumber) {
		return mappersEx.selectDestroyOrder(dayNumber);
	}

	public long updateOrderStat(long dayNumber) {
		return mappersEx.updateOrderStat(dayNumber);
	}
}
