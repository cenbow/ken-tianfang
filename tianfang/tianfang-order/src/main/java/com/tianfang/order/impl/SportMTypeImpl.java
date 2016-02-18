package com.tianfang.order.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.dao.SportMTypeDao;
import com.tianfang.order.dto.SportMTypeDto;
import com.tianfang.order.pojo.SportMType;
import com.tianfang.order.service.ISportMTypeService;
@Service
public class SportMTypeImpl implements ISportMTypeService{
	
	@Autowired
	private SportMTypeDao sportMTypeDao;
	
	public PageResult<SportMTypeDto> findPage(SportMTypeDto sportMTypeDto,PageQuery page) {
		List<SportMTypeDto> sportMTypeDtos = sportMTypeDao.findPage(sportMTypeDto, page);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (SportMTypeDto sportmTypeDto : sportMTypeDtos) {
			sportmTypeDto.setCreateDate(sdf.format(sportmTypeDto.getCreateTime()));
		}
		page.setTotal(sportMTypeDao.count(sportMTypeDto));
		return new PageResult<SportMTypeDto>(page,sportMTypeDtos);
	}
	
	public SportMTypeDto findById(String id) {
		SportMType sportMType = sportMTypeDao.selectByPrimaryKey(id);
		SportMTypeDto sportMTypeDto = BeanUtils.createBeanByTarget(sportMType, SportMTypeDto.class);
		return sportMTypeDto;
	}
	
	public Integer save(SportMTypeDto sportMTypeDto) {
		SportMType sportMType = BeanUtils.createBeanByTarget(sportMTypeDto, SportMType.class);
		if (StringUtils.isNotBlank(sportMType.getId())) {
			return sportMTypeDao.updateByPrimaryKeySelective(sportMType);
		}
		return sportMTypeDao.insert(sportMType);
	}
	
	public Object delete(String ids) {
		String[] idStr = ids.split(",");
		if (idStr.length>0) {
			for (String id : idStr) {
				SportMType sportMType = sportMTypeDao.selectByPrimaryKey(id);
				sportMType.setStat(DataStatus.DISABLED);
				sportMTypeDao.updateByPrimaryKeySelective(sportMType);
			}			
		}else {
			SportMType sportMType = sportMTypeDao.selectByPrimaryKey(ids);
			sportMType.setStat(DataStatus.DISABLED);
			sportMTypeDao.updateByPrimaryKeySelective(sportMType);
		}
		return null;
	}

	@Override
	public List<SportMTypeDto> selectMTypeAll() {
		List<SportMType> lis = sportMTypeDao.selectMTypeAll();
		if(lis.size() >0){
			return BeanUtils.createBeanListByTarget(lis, SportMTypeDto.class);
		}
		return null;
	}
}
