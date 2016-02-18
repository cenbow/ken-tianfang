package com.tianfang.onteach.service;

import java.util.List;

import com.tianfang.common.model.PageResult;
import com.tianfang.onteach.dto.OnlineTeachColumnDto;

public interface IOnTeachColumnService {
	/**
	 * 分页查询
	 */
	PageResult<OnlineTeachColumnDto> findOnTeachByParams(OnlineTeachColumnDto params, int currPage, int pageSize);

	void insert(OnlineTeachColumnDto params);

	void update(OnlineTeachColumnDto params);

	OnlineTeachColumnDto findObjectById(String id);

//	void updateClickCount(String oId);

	void updateStatus(String ids);

	void release(String id, Integer releaseStat);

	List<OnlineTeachColumnDto> findColumnsBy(OnlineTeachColumnDto dto);
	
//	PageResult<OnlineTeachColumnDto> findOnTeachcolumnNameByParams(int currPage, int pageSize);
	
	
	
	
}
