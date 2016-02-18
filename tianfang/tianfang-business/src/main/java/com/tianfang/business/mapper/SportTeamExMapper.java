package com.tianfang.business.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tianfang.business.dto.SportTeamDto;

/**
 * @author xiang_wang
 *
 * 2015年11月14日下午3:52:44
 */
public interface SportTeamExMapper {

	/**
	 * 根据条件查询热门球队(根据(win-lost)降序)
	 * @param params 查询参数{"gameId":value,"distruct":value,"grade":value,"teamType":value,"total":热门球队展示个数}
	 * @return
	 * @author wangxiang
	 * 2015年11月20日上午11:55:51
	 */
	List<SportTeamDto> queryHotTeam(@Param("params") Map<String, Object> params);
	/**
	 * 分页查询列表
	 * @param page
	 * @param params
	 * @return
	 */
	List<SportTeamDto> selectTeam(@Param("params")Map<String, Object> params);
}
