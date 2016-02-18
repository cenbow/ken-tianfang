package com.tianfang.business.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.business.dto.SportAddressesDto;
import com.tianfang.business.mapper.SportAddressesMapper;
import com.tianfang.business.mapper.SportTeamTypeMapper;
import com.tianfang.business.pojo.SportAddresses;
import com.tianfang.business.pojo.SportAddressesExample;
import com.tianfang.business.pojo.SportTeamType;
import com.tianfang.business.pojo.SportTeamTypeExample;
import com.tianfang.business.pojo.SportTeamTypeExample.Criteria;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.mybatis.MyBatisBaseDao;

@Repository
public class SportTeamTypeDao extends MyBatisBaseDao<SportAddresses> {

	@Autowired
	@Getter
	private SportTeamTypeMapper mapper;
	
	@Override
	public Object getMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SportTeamType> getAllTeamType() {
		SportTeamTypeExample example = new SportTeamTypeExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}
	
}
