package com.tianfang.order.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.order.dto.SportMTypeDto;

public interface ISportMTypeService {

	public PageResult<SportMTypeDto> findPage(SportMTypeDto sportMTypeDto,PageQuery page);
	
	public Integer save (SportMTypeDto sportMTypeDto);
	
	public SportMTypeDto findById(String id) ;
	
	public Object delete(String ids);

	/**
	 * 获取全部类型信息集合
	 * @return
	 */
	public List<SportMTypeDto> selectMTypeAll();
}
