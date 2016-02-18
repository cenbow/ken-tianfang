package com.tianfang.train.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.train.pojo.TrainingMenu;

public interface TrainingAuthorizationExMapper {
    
	List<TrainingMenu> findTrainingMenuByAdminId(@Param("id") String id);
	
}
