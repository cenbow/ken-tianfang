package com.tianfang.business.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tianfang.business.dto.SportTeamDto;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;

@Service
public interface ITeamService {

	/**
	 * 新增
	 * @param teamDto
	 * @return
	 */
	long saveTeam(SportTeamDto teamDto);


	/**
	 * 分页查询数据
	 * @param page
	 * @param map
	 * @return
	 */
	PageResult<SportTeamDto> getByCriteriaPage(PageQuery page,
			Map<String, Object> map);


	/**
	 * 修改
	 * @param teamDto
	 * @return
	 */
	long editTeam(SportTeamDto teamDto);

	/**
	 * 删除
	 * @param teamDto
	 * @return
	 */
	long delTeam(String ids);


	/**
	 * 获取全部球队信息集合
	 * @return
	 */
	List<SportTeamDto> getAllTeam();


	/**
	 * 根据条件查询单个对象 
	 * @param map
	 * @return
	 */
	SportTeamDto selectById(Map<String, Object> map);
	
	/**
	 * 根据条件查询热门球队(根据(win-lost)降序)
	 * @param params 查询参数{"gameId":value,"distruct":value,"grade":value,"teamType":value}
	 * @param total 热门球队展示个数
	 * @return
	 * @author wangxiang
	 * 2015年11月20日上午11:47:46
	 */
	List<SportTeamDto> queryHotTeam(Map<String, Object> params, int total);
	
	/**
	 * 多表联查球队信息  -- 赛事表 ,区域表,球队类型表
	 * @param params
	 * @param total
	 * @return
	 */
	PageResult<SportTeamDto> selectTeam(Map<String, Object> params, PageQuery page);
	
	
	/**
	 * 查询满足条件的球队个数
	 * @param map  查询参数{"gameId":value,"distruct":value,"grade":value,"teamType":value}
	 * @return
	 * @author xiang_wang
	 * 2015年11月20日下午3:02:32
	 */
	long countTeam(Map<String, Object> map);
	
	/**
	 * 根據條件查詢Team信息
	 */
	List<SportTeamDto> queryTeamByParams(SportTeamDto teamDto);

    /**
     * 按id查询球队信息
     */
	SportTeamDto findTeamBy(SportTeamDto teamDto);
}
