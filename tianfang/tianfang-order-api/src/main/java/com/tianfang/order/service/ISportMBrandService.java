package com.tianfang.order.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.order.dto.SportMBrandDto;

public interface ISportMBrandService {
	
	public PageResult<SportMBrandDto> findPage(SportMBrandDto sportMBrandDto,PageQuery page);

	public Integer save(SportMBrandDto sportMBrandDto);
	
	public SportMBrandDto findById(String id);
	
	public SportMBrandDto delete(String ids) ;

	public List<SportMBrandDto> selectAll();
}
