package com.tianfang.business.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.business.dto.SportHomeMenuDto;
import com.tianfang.business.mapper.SportHomeMenuMapper;
import com.tianfang.business.pojo.SportHomeMenu;
import com.tianfang.business.pojo.SportHomeMenuExample;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;

@Repository
public class SportHomeMenuDao extends MyBatisBaseDao<SportHomeMenu>{
	@Autowired
	@Getter
	private SportHomeMenuMapper mapper;

	public List<SportHomeMenu> findSportHomeMenuList(SportHomeMenuDto dto) {
		SportHomeMenuExample example = new SportHomeMenuExample();
		SportHomeMenuExample.Criteria criteria = example.createCriteria();
		assemblyParams(dto, criteria);
		criteria.andStatEqualTo(DataStatus.ENABLED);
	    
        example.setOrderByClause(" menu_type,menu_order");
        
	    List<SportHomeMenu> resultList = mapper.selectByExample(example);
	    return resultList;
	}
	
	public List<SportHomeMenuDto> findPage(SportHomeMenuDto dto,PageQuery page) {
		SportHomeMenuExample example = new SportHomeMenuExample();
		SportHomeMenuExample.Criteria criteria = example.createCriteria();	
		assemblyParams(dto, criteria);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		if (null != page) {
            example.setOrderByClause(" menu_type,menu_order,create_time asc limit "+page.getStartNum() +"," + page.getPageSize());
        }
		List<SportHomeMenu> sportHomeMenus = mapper.selectByExample(example);
		return BeanUtils.createBeanListByTarget(sportHomeMenus, SportHomeMenuDto.class);
	}
	
	public List<SportHomeMenuDto> findByMenuType(Integer menuType) {
		SportHomeMenuExample example = new SportHomeMenuExample();
		SportHomeMenuExample.Criteria criteria = example.createCriteria();
		criteria.andMenuTypeEqualTo(menuType);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		List<SportHomeMenu> sportHomeMenus = mapper.selectByExample(example);
		return BeanUtils.createBeanListByTarget(sportHomeMenus, SportHomeMenuDto.class);
	}
	
	public long count(SportHomeMenuDto dto) {
		SportHomeMenuExample example = new SportHomeMenuExample();
		SportHomeMenuExample.Criteria criteria = example.createCriteria();	  
		assemblyParams(dto, criteria);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.countByExample(example);
	}
	
	private void assemblyParams(SportHomeMenuDto dto,
			SportHomeMenuExample.Criteria criteria) {
		if (null != dto){
			if (StringUtils.isNotBlank(dto.getId())) {
				criteria.andIdEqualTo(dto.getId());
			}
			if (StringUtils.isNotBlank(dto.getParentId())) {
				criteria.andParentIdEqualTo(dto.getParentId());
			}
			if (null != dto.getMenuType()){
				criteria.andMenuTypeEqualTo(dto.getMenuType());
			}
			if (StringUtils.isNotBlank(dto.getCname())){
				criteria.andCnameLike("%"+dto.getCname().trim()+"%");
			}
			if (null != dto.getVideoType()){
				criteria.andVideoTypeEqualTo(dto.getVideoType());
			}
		}
	}
}
