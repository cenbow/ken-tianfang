package com.tianfang.order.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.order.dto.SportMCategoryDto;
import com.tianfang.order.dto.SportMTypeDto;

public interface ISportMCategoryService {
	
	public PageResult<SportMCategoryDto> findPage(SportMCategoryDto sportMCategoryDto,PageQuery page);
	
	public Integer save(SportMCategoryDto sportMCategoryDto);
	
	public SportMCategoryDto findById(String id);
	
	public Object delete(String ids);
	
	public List<SportMTypeDto> getAllType();
	
	public List<SportMCategoryDto> findAllCategory();
}
