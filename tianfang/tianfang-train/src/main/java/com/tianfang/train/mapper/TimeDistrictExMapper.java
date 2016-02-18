package com.tianfang.train.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.train.pojo.TimeDistrict;

public interface TimeDistrictExMapper {
	
	/**
	 * 
	 * @author YIn
	 * @time:2015年9月7日 下午2:50:08
	 * @param spaceId
	 * @return
	 */
	List<TimeDistrict> findTimeDistrictBySpaceId(@Param("spaceId") String spaceId);


}