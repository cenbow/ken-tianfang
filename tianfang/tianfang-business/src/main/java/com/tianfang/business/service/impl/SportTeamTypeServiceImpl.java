package com.tianfang.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.business.dao.SportTeamTypeDao;
import com.tianfang.business.dto.SportTeamTypeDto;
import com.tianfang.business.pojo.SportTeamType;
import com.tianfang.business.service.ISportTeamTypeService;
import com.tianfang.common.util.BeanUtils;

@Service
public class SportTeamTypeServiceImpl implements ISportTeamTypeService {
	
	@Autowired
	private SportTeamTypeDao sportTeamTypeDao;
	@Override
	public List<SportTeamTypeDto> getAllTeamType() {
		List<SportTeamType> teamType = sportTeamTypeDao.getAllTeamType();
		return BeanUtils.createBeanListByTarget(teamType, SportTeamTypeDto.class);
	}

	
}
