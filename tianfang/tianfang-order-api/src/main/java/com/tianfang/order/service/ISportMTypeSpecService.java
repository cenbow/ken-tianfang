package com.tianfang.order.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.order.dto.SportMTypeSpecDto;
import com.tianfang.order.dto.SportTypeSpecExDto;

/**
 * 类型规格关联表
 * @author Administrator
 *
 */
public interface ISportMTypeSpecService {

	long save(SportMTypeSpecDto sportMTypeSpecDto);

	PageResult<SportTypeSpecExDto> selectTypeSpec(SportTypeSpecExDto spexDto,PageQuery page);

	long delete(String id);

	List<SportMTypeSpecDto> selectAll();

	List<SportMTypeSpecDto> getByCriteria(SportMTypeSpecDto mTypeSpec);
}
