package com.tianfang.business.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tianfang.business.dto.MenuDto;
import com.tianfang.business.dto.SportHomeMenuDto;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;

@Service
public interface ISportHomeMenuService {
	public List<MenuDto> findSportHomeMenuList(SportHomeMenuDto dto);
	
	public PageResult<SportHomeMenuDto> findPage(SportHomeMenuDto dto,PageQuery page);
	
	public List<SportHomeMenuDto> findByMenuType(Integer menuType);
	
	public Integer save(SportHomeMenuDto sportHomeMenuDto) ;
	
	public SportHomeMenuDto findById(String id);
	
	public Integer edit(SportHomeMenuDto dto);
	
	public Integer delete(String ids);
	
	public List<SportHomeMenuDto> findByParentId(String parentId);
	
	public List<SportHomeMenuDto> findAll();
}
