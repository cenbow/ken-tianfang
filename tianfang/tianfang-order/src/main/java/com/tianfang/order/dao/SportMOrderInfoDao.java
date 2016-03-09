package com.tianfang.order.dao;

import java.util.List;

import lombok.Getter;

import org.apache.zookeeper.Op.Create;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.dto.SportMOrderInfoDto;
import com.tianfang.order.mapper.SportMOrderInfoExMapper;
import com.tianfang.order.mapper.SportMOrderInfoMapper;
import com.tianfang.order.pojo.SportMOrderInfo;
import com.tianfang.order.pojo.SportMOrderInfoExample;
import com.tianfang.order.pojo.SportMOrderInfoExample.Criteria;

@Repository
public class SportMOrderInfoDao extends MyBatisBaseDao<SportMOrderInfo>{
	
	@Getter
	@Autowired
	private SportMOrderInfoMapper mappers;
	
	@Getter
	@Autowired
	private SportMOrderInfoExMapper mappersEx;

	@Override
	public Object getMapper() {
		// TODO Auto-generated method stub
		return mappers;
	}

	public List<SportMOrderInfo> selectOrderInfo(SportMOrderInfoDto orderInfoDto, PageQuery page) {
		SportMOrderInfoExample example = new SportMOrderInfoExample();
		SportMOrderInfoExample.Criteria criteria = example.createCriteria();
		byCriteria(orderInfoDto,criteria);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		if(page!=null){
			example.setOrderByClause(" create_time desc limit "+page.getStartNum() +","+page.getPageSize());
		}
		return mappers.selectByExample(example);
	}

	private void byCriteria(SportMOrderInfoDto orderInfoDto, Criteria criteria) {
		if(StringUtils.isNotBlank(orderInfoDto.getOrderId())){
			criteria.andOrderIdEqualTo(orderInfoDto.getOrderId());
		}
		if(StringUtils.isNotBlank(orderInfoDto.getOrderInfoNo())){
			criteria.andOrderInfoNoEqualTo(orderInfoDto.getOrderInfoNo());
		}
	}

	public long countOrderInfo(SportMOrderInfoDto orderInfoDto) {
		SportMOrderInfoExample example = new SportMOrderInfoExample();
		Criteria criteria = example.createCriteria();
		byCriteria(orderInfoDto,criteria);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mappers.countByExample(example);
	}

	public List<SportMOrderInfo> selectAll() {
		SportMOrderInfoExample example = new SportMOrderInfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mappers.selectByExample(example);
	}

	public List<SportMOrderInfo> selectAll(SportMOrderInfoDto orderInfo) {
		SportMOrderInfoExample example = new SportMOrderInfoExample();
		Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(orderInfo.getOrderId())) {
			criteria.andOrderIdEqualTo(orderInfo.getOrderId());
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mappers.selectByExample(example);
	}

	public List<SportMOrderInfoDto> findOrderInfo(SportMOrderInfoDto orderInfoDto, PageQuery page) {
		return mappersEx.findOrderInfo(orderInfoDto,page);
	}

	public long countOrderExInfo(SportMOrderInfoDto orderInfoDto) {
		return mappersEx.countOrderInfo(orderInfoDto);
	}

}
