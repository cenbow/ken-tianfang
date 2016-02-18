package com.tianfang.business.service;

import java.util.List;

import com.tianfang.business.dto.SportSfResultDto;
import com.tianfang.business.dto.SportSfResultExDto;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;

public interface ISportSfResultService {

	public long save(SportSfResultDto sfResult);

	public long update(SportSfResultDto resultDto);

	public long delete(String id);

	public PageResult<SportSfResultDto> selectPageAll(SportSfResultDto resultDto, PageQuery changeToPageQuery);
	
	/**
	 * 批量保存
	 * @param results
	 * @author xiang_wang
	 * 2015年12月30日上午9:48:26
	 */
	void batchInsert(List<SportSfResultDto> results);

	/**
	 * 連表查詢 result 結果
	 * @param resultDto
	 * @param page
	 * @return
	 */
	public PageResult<SportSfResultExDto> selectOrSfCriteria(SportSfResultExDto resultDto, PageQuery page);
}