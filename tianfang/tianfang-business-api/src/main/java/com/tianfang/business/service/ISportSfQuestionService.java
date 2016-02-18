package com.tianfang.business.service;

import java.util.List;

import com.tianfang.business.dto.SportSfQuestionDto;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;

public interface ISportSfQuestionService {

	long save(SportSfQuestionDto sportSfQuestion);

	long update(SportSfQuestionDto sportSfQuestion);

	long delete(String ids);

	PageResult<SportSfQuestionDto> selectPageAll(SportSfQuestionDto sportSfQuestion, PageQuery changeToPageQuery);

	SportSfQuestionDto selectById(String id);
	
	List<SportSfQuestionDto> selectAll(SportSfQuestionDto sportSfQuestion);

}
