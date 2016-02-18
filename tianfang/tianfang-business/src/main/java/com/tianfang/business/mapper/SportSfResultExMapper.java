package com.tianfang.business.mapper;

import java.util.List;

import com.tianfang.business.pojo.SportSfResult;

public interface SportSfResultExMapper {

	/**
	 * 批量新增
	 * @param results
	 * @author xiang_wang
	 * 2015年12月30日上午9:57:36
	 */
	void batchInsert(List<SportSfResult> results);
}
