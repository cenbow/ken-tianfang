package com.tianfang.business.service;

import java.util.List;

import com.tianfang.business.dto.SportSfAnswerDto;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;

public interface ISportSfAnswerService {

	long save(SportSfAnswerDto sportSfAnswerDto);

	long update(SportSfAnswerDto sportSfAnswerDto);

	long delete(String ids);

	PageResult<SportSfAnswerDto> selectPageAll(SportSfAnswerDto sportSfAnswerDto, PageQuery changeToPageQuery);

	SportSfAnswerDto selectById(String id);

	/**
	 * 根据id列表查询
	 * @param ids
	 * @param page
	 * @return
	 */
	public List<SportSfAnswerDto> selectByIds(List<String> ids, PageQuery page);
	
	List<SportSfAnswerDto> selectAll();

	List<SportSfAnswerDto> selectList(SportSfAnswerDto sportSfAnswerDto);
}
