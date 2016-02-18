package com.tianfang.train.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.train.dto.SpaceDto;
import com.tianfang.train.dto.TrainingAddressDto;
import com.tianfang.train.pojo.TrainingAddress;


public interface TraingAddressExMapper {
	
	List<TrainingAddressDto> getAllAddressInfo(@Param("id") String id);
	
	int insert(TrainingAddress trainingAddress);
	/**
	 * 更新场地时间段关联信息
	 * @param id
	 * @return
	 */
	int update(String id);
	
	List<SpaceDto> findAllSpace();
}
