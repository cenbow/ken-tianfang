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
import com.tianfang.order.dto.SportMCategoryDto;
import com.tianfang.order.mapper.SportMCategoryExMapper;
import com.tianfang.order.mapper.SportMCategoryMapper;
import com.tianfang.order.pojo.SportMCategory;
import com.tianfang.order.pojo.SportMCategoryExample;

@Repository
public class SportMCategoryDao extends MyBatisBaseDao<SportMCategory>{

	@Getter
	@Autowired
	private SportMCategoryMapper mappers;
	
	@Getter
	@Autowired
	private SportMCategoryExMapper exMappers;

	@Override
	public Object getMapper() {
		// TODO Auto-generated method stub
		return mappers;
	}
	
	public List<SportMCategoryDto> findPage(SportMCategoryDto sportMCategoryDto,PageQuery page) {
		/*SportMCategoryExample example = new SportMCategoryExample();
		SportMCategoryExample.Criteria criteria = example.createCriteria();
		if (null != page) {
			example.setOrderByClause(" create_time desc limit "+page.getStartNum() +"," + page.getPageSize());
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);*/
		List<SportMCategoryDto> result = exMappers.selectCategoryByPage(sportMCategoryDto, page);
//		List<SportMCategoryDto> result = BeanUtils.createBeanListByTarget(sportMCategories, SportMCategoryDto.class);
		return result;
	}
	
	public long count(SportMCategoryDto sportMCategoryDto) {
		/*SportMCategoryExample example = new SportMCategoryExample();
		SportMCategoryExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mappers.countByExample(example);*/
		return exMappers.selectCategoryByCount(sportMCategoryDto);
	}
	
	public List<SportMCategoryDto> findAllCategory() {
		SportMCategoryExample example = new SportMCategoryExample();
		SportMCategoryExample.Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo("0");
		criteria.andStatEqualTo(DataStatus.ENABLED);
		List<SportMCategory> sportMCategories = mappers.selectByExample(example);
		return BeanUtils.createBeanListByTarget(sportMCategories, SportMCategoryDto.class);
	}
	
	public List<SportMCategoryDto> findByTypeId(String typeId) {
		SportMCategoryExample example = new SportMCategoryExample();
		SportMCategoryExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(typeId)) {
			criteria.andTypeIdEqualTo(typeId);
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		List<SportMCategory> sportMCategories = mappers.selectByExample(example);
		return BeanUtils.createBeanListByTarget(sportMCategories, SportMCategoryDto.class);
	}
}
