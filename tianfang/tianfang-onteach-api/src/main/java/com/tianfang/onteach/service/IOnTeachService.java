package com.tianfang.onteach.service;

import com.tianfang.common.model.PageResult;
import com.tianfang.onteach.dto.OnTeachDto;

public interface IOnTeachService {
	/**
	 * 分页查询
	 */
	PageResult<OnTeachDto> findOnTeachByParams(OnTeachDto params, int currPage, int pageSize);

	void insert(OnTeachDto params);

	void update(OnTeachDto params);

	OnTeachDto findObjectById(String id);

	void updateClickCount(String oId);

	void updateStatus(String ids);

	void release(String id, Integer releaseStat);
	
	PageResult<OnTeachDto> findOnTeachcolumnNameByParams(int currPage, int pageSize);
	
	
	
	
}
