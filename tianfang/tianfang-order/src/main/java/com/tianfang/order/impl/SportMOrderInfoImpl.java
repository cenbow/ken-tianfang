package com.tianfang.order.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.order.dao.SportMOrderInfoDao;
import com.tianfang.order.dto.SportMOrderInfoDto;
import com.tianfang.order.pojo.SportMOrderInfo;
import com.tianfang.order.service.ISportMOrderInfoService;

@Service
public class SportMOrderInfoImpl implements ISportMOrderInfoService{

	@Autowired
	private SportMOrderInfoDao sportMOrderInfoDao;

	@Override
	public PageResult<SportMOrderInfoDto> selectOrderInfo(SportMOrderInfoDto orderInfoDto, PageQuery page) {
		List<SportMOrderInfo> orderInfo = sportMOrderInfoDao.selectOrderInfo(orderInfoDto,page);
		List<SportMOrderInfoDto> orderInfoLis= BeanUtils.createBeanListByTarget(orderInfo, SportMOrderInfoDto.class);
		if(page!=null){
			long total = sportMOrderInfoDao.countOrderInfo(orderInfoDto);
			page.setTotal(total);
			return new PageResult<SportMOrderInfoDto>(page,orderInfoLis);
		}
		return null;
	}

	@Override
	public List<SportMOrderInfoDto> selectAll(SportMOrderInfoDto orderInfo) {
		List<SportMOrderInfo> orderInfolis= sportMOrderInfoDao.selectAll(orderInfo);
		if(orderInfolis.size()>0){
			return  BeanUtils.createBeanListByTarget(orderInfolis, SportMOrderInfoDto.class);
		}
		return null;
	}

	@Override
	public PageResult<SportMOrderInfoDto> findOrderInfo(SportMOrderInfoDto orderInfoDto, PageQuery page) {
		List<SportMOrderInfoDto> orderInfoLis = sportMOrderInfoDao.findOrderInfo(orderInfoDto,page); 
	    if(page!=null){
	    	long total = sportMOrderInfoDao.countOrderExInfo(orderInfoDto);
	    	page.setTotal(total);
	    	return new PageResult<SportMOrderInfoDto>(page,orderInfoLis);
	    }
		return null;
	}
	
	public List<SportMOrderInfoDto> findOrderInfoById(String orderId) {
		SportMOrderInfoDto sportMOrderInfoDto = new SportMOrderInfoDto();
		sportMOrderInfoDto.setOrderId(orderId);
		List<SportMOrderInfo> orderInfos = sportMOrderInfoDao.selectOrderInfo(sportMOrderInfoDto, null);
		return BeanUtils.createBeanListByTarget(orderInfos, SportMOrderInfoDto.class);
	}
	
	public List<SportMOrderInfoDto> findOrderInfoByNo(String orderIdNo) {
		SportMOrderInfoDto sportMOrderInfoDto = new SportMOrderInfoDto();
		sportMOrderInfoDto.setOrderInfoNo(orderIdNo);
		List<SportMOrderInfo> orderInfos = sportMOrderInfoDao.selectOrderInfo(sportMOrderInfoDto, null);
		return BeanUtils.createBeanListByTarget(orderInfos, SportMOrderInfoDto.class);
	} 
	
	public void updateOrderInfoById (String id,Integer orderStatus) {
		SportMOrderInfo sportMOrderInfo = sportMOrderInfoDao.selectByPrimaryKey(id);
		sportMOrderInfo.setOrderStatus(orderStatus);
		sportMOrderInfoDao.updateByPrimaryKeySelective(sportMOrderInfo);
	}
}
