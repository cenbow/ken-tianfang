package com.tianfang.business.service;

import java.util.List;

import com.tianfang.business.dto.SportSfResultDto;
import com.tianfang.business.dto.SportSuggestionUserDto;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;

public interface ISportSuggestionUserService {

	String save(SportSuggestionUserDto suggestionUserDto);

	long update(SportSuggestionUserDto suggestionUserDto);

	long delete(String ids);

	PageResult<SportSuggestionUserDto> selectPageAll( SportSuggestionUserDto suggestionUserDto, PageQuery changeToPageQuery);
	
	/**
	 * 用户反馈意见提交动作
	 * @param suggestionUserDto
	 * @param results
	 * @return
	 * @author xiang_wang
	 * 2015年12月30日上午10:40:50
	 */
	boolean submitSuggestion(SportSuggestionUserDto suggestionUserDto, List<SportSfResultDto> results);
}
