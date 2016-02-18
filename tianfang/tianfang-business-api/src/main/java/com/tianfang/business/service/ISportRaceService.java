package com.tianfang.business.service;

import java.util.List;

import com.tianfang.business.dto.SportRaceDto;
import com.tianfang.common.model.PageResult;

/**
 * 赛事操作接口
 * 
 * @author wangxiang
 *
 * 2015年11月17日
 */
public interface ISportRaceService {
	
	/**
	 * 保存赛事信息
	 * 
	 * @param dto
	 * @return
	 * @author wangxiang
	 * 2015年11月17日上午10:17:42
	 */
	int saveRace(SportRaceDto dto);
	
	/**
	 * 非物理删除,更新赛事状态为0
	 * @param id
	 * @return
	 * @author wangxiang
	 * 2015年11月17日上午10:19:56
	 */
	int deleteRace(String id);
	
	/**
	 * 更新赛事
	 * @param dto
	 * @return
	 * @author wangxiang
	 * 2015年11月17日上午10:20:07
	 */
	int updateRace(SportRaceDto dto);
	
	/**
	 * 根据主键id获取赛事信息
	 * @param id
	 * @return
	 * @author wangxiang
	 * 2015年11月17日上午10:21:50
	 */
	SportRaceDto getRaceById(String id);
	
	/**
	 * 根据参数查询赛事信息
	 * @param params
	 * @return
	 * @author wangxiang
	 * 2015年11月17日上午10:22:21
	 */
	List<SportRaceDto> findRaceByParams(SportRaceDto params);
	
	/**
	 * 根据参数分页查询赛事信息
	 * @param params
	 * @param currPage
	 * @param pageSize
	 * @return
	 * @author wangxiang
	 * 2015年11月17日上午11:08:20
	 */
	PageResult<SportRaceDto> findRaceByParams(SportRaceDto params, int currPage, int pageSize);
}