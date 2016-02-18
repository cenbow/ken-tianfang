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
import com.tianfang.order.dto.SportMTypeDto;
import com.tianfang.order.mapper.SportMTypeMapper;
import com.tianfang.order.pojo.SportMType;
import com.tianfang.order.pojo.SportMTypeExample;

@Repository
public class SportMTypeDao extends MyBatisBaseDao<SportMType>{

	@Getter
	@Autowired
	private SportMTypeMapper mappers;
	
	@Override
	public Object getMapper() {
		// TODO Auto-generated method stub
		return mappers;
	}
	
	public List<SportMTypeDto> findPage(SportMTypeDto sportMTypeDto,PageQuery page) {
		SportMTypeExample example = new SportMTypeExample();
		SportMTypeExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(sportMTypeDto.getTypeName())) {
			criteria.andTypeNameLike("%"+sportMTypeDto.getTypeName()+"%");
		}
		if(null != page){
			example.setOrderByClause(" create_time desc limit "+page.getStartNum() +"," + page.getPageSize());
		}
	    criteria.andStatEqualTo(DataStatus.ENABLED);
	    List<SportMType> sportMTypes = mappers.selectByExample(example);
	    List<SportMTypeDto> results = BeanUtils.createBeanListByTarget(sportMTypes, SportMTypeDto.class);
	    return results;
	}

	public long count(SportMTypeDto sportMTypeDto) {
		SportMTypeExample example = new SportMTypeExample();
		SportMTypeExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(sportMTypeDto.getTypeName())) {
			criteria.andTypeNameLike("%"+sportMTypeDto.getTypeName()+"%");
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mappers.countByExample(example);
	}

	public List<SportMType> selectMTypeAll() {
		SportMTypeExample example = new SportMTypeExample();
		SportMTypeExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mappers.selectByExample(example);
	}
	
	public List<SportMTypeDto> findAllType() {
		SportMTypeExample example = new SportMTypeExample();
		SportMTypeExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		List<SportMType> sportMTypes = mappers.selectByExample(example);
		List<SportMTypeDto> results = BeanUtils.createBeanListByTarget(sportMTypes, SportMTypeDto.class);
	    return results;
	}
	
}
