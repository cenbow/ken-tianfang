package com.tianfang.train.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.train.dto.AssistantDto;
import com.tianfang.train.dto.AssistantSpaceTimeRespDto;

public interface AssistantMappingExMapper {
	List<AssistantDto> findAssistantByPage(@Param("assistantDto") AssistantDto assistantDto,@Param("page") PageQuery page);
	
	Long findAssistantByPageCount(@Param("assistantDto") AssistantDto assistantDto);
	
	List<AssistantSpaceTimeRespDto> findAssistantAddressTimeById(@Param("id") String id);
	
}
