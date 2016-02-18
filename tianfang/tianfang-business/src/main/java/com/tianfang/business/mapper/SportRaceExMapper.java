package com.tianfang.business.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tianfang.business.dto.SportRaceDto;

public interface SportRaceExMapper {
	
	/**
	 * 根据参数进行赛事表和球队表联查
	 * @param params
	 * @return
	 * @author wangxiang
	 * 2015年11月17日下午1:26:00
	 */
	List<SportRaceDto> findRaceByParams(@Param("params") Map<String, Object> params);
	
	/**
	 * 根据参数统计进行赛事表和球队表联查
	 * @param params
	 * @return
	 * @author wangxiang
	 * 2015年11月17日下午1:26:00
	 */
	int countRaceByParams(@Param("params") Map<String, Object> params);
}