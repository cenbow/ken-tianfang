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
import com.tianfang.order.dto.SportMBrandDto;
import com.tianfang.order.mapper.SportMBrandMapper;
import com.tianfang.order.pojo.SportMBrand;
import com.tianfang.order.pojo.SportMBrandExample;

@Repository
public class SportMBrandDao extends MyBatisBaseDao<SportMBrand>{

	@Getter
	@Autowired
	private SportMBrandMapper mappers;
	
	@Override
	public Object getMapper(){
		return mappers;
	}
	
	public List<SportMBrandDto> findPage(SportMBrandDto sportMBrandDto,PageQuery page) {
		SportMBrandExample example = new SportMBrandExample();
		SportMBrandExample.Criteria criteria = example.createCriteria();
		if (null != sportMBrandDto) {
			if (StringUtils.isNotBlank(sportMBrandDto.getBrandName())) {
				criteria.andBrandNameLike("%"+sportMBrandDto.getBrandName()+"%");
			}
			if (sportMBrandDto.getIsShow() != null) {
				criteria.andIsShowEqualTo(sportMBrandDto.getIsShow());
			}
		}
		
		if(null != page){
			example.setOrderByClause(" create_time desc limit "+page.getStartNum() +"," + page.getPageSize());
		}
	    criteria.andStatEqualTo(DataStatus.ENABLED);
	    List<SportMBrand> sportMBrandList = mappers.selectByExample(example);
	    List<SportMBrandDto> results = BeanUtils.createBeanListByTarget(sportMBrandList, SportMBrandDto.class);
	    return results;
	}
	
	public long count(SportMBrandDto sportMBrandDto) {
		SportMBrandExample example = new SportMBrandExample();
		SportMBrandExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(sportMBrandDto.getBrandName())) {
			criteria.andBrandNameLike("%"+sportMBrandDto.getBrandName()+"%");
		}
		if (sportMBrandDto.getIsShow() != null) {
			criteria.andIsShowEqualTo(sportMBrandDto.getIsShow());
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mappers.countByExample(example);
	}

	public List<SportMBrand> selectAll() {
		SportMBrandExample example = new SportMBrandExample();
		SportMBrandExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mappers.selectByExample(example);
	}
	
}
