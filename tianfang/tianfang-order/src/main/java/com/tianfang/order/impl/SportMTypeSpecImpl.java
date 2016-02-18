package com.tianfang.order.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.dao.SportMTypeSpecDao;
import com.tianfang.order.dto.SportMTypeSpecDto;
import com.tianfang.order.dto.SportTypeSpecExDto;
import com.tianfang.order.pojo.SportMTypeSpec;
import com.tianfang.order.service.ISportMTypeSpecService;

@Service
public class SportMTypeSpecImpl implements ISportMTypeSpecService{

	@Autowired
	private SportMTypeSpecDao sportMTypeSpecDao;

	@Override
	public long save(SportMTypeSpecDto sportMTypeSpecDto) {
		SportMTypeSpec sportMTypeSpec = BeanUtils.createBeanByTarget(sportMTypeSpecDto, SportMTypeSpec.class);
		return sportMTypeSpecDao.save(sportMTypeSpec);
	}

	@Override
	public PageResult<SportTypeSpecExDto> selectTypeSpec(SportTypeSpecExDto spexDto,PageQuery page) {
		
		if(!StringUtils.isBlank(spexDto.getSpecName())){
			spexDto.setSpecName("%"+spexDto.getSpecName()+"%");
		}
		if(!StringUtils.isBlank(spexDto.getTypeName())){
			spexDto.setTypeName("%"+spexDto.getTypeName()+"%");
		}
		if(page!=null){
			spexDto.setStart(page.getStartNum());
			spexDto.setLimit(page.getPageSize());
		}
		List<SportTypeSpecExDto> lis = sportMTypeSpecDao.selectTypeSpec(spexDto);
		if(lis.size() > 0){
			long total = sportMTypeSpecDao.countTypeSpec(spexDto);
			page.setTotal(total);
		}
		return new PageResult<SportTypeSpecExDto>(page, lis);
	}

	@Override
	public long delete(String id) {
		String[] ids = id.split(",");
		long stat =0;
		for (String str : ids) {
			stat = sportMTypeSpecDao.delete(str);
			if(stat<1){
				return stat;
			}
		}
		return stat;
	}

	@Override
	public List<SportMTypeSpecDto> selectAll() {
		List<SportMTypeSpec> specLis = sportMTypeSpecDao.selectAll();
		if(specLis.size()>0){
			return BeanUtils.createBeanListByTarget(specLis, SportMTypeSpecDto.class);
		}
		return null;
	}

	@Override
	public List<SportMTypeSpecDto> getByCriteria(SportMTypeSpecDto mTypeSpec) {
		List<SportMTypeSpec> specLis = sportMTypeSpecDao.selectByCriteria(mTypeSpec);
		return BeanUtils.createBeanListByTarget(specLis, SportMTypeSpecDto.class);
	}
}
