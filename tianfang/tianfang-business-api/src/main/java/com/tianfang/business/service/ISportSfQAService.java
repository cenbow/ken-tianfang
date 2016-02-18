/**
 * 
 */
package com.tianfang.business.service;

import java.util.List;
import java.util.Map;

import com.tianfang.business.dto.SportQuestionAnswerDto;
import com.tianfang.business.dto.SportSfQuestionDto;
import com.tianfang.business.dto.SportSfQuestionExDto;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;

/**
 * @author wk.s
 *
 */
public interface ISportSfQAService {


	public List<SportQuestionAnswerDto> selectByCriteria(SportQuestionAnswerDto dto);
	/**
	 * 新增
	 * @param dto
	 * @return
	 */
	public boolean add(SportQuestionAnswerDto dto);
	
	/**
	 * 更新
	 * @param dto
	 * @return
	 */
	public boolean update(SportQuestionAnswerDto dto);
	
	/**
	 * 查询
	 * @param map
	 * @param page
	 * @return
	 */
	public List<SportQuestionAnswerDto> list(Map<String, Object> map, PageQuery page);
	
	/**
	 * 删除
	 * @param dto
	 * @return
	 */
	public boolean delete(String id);
	/**
	 * 获取 问题-答案 关联表信息 
	 * @param dto
	 * @return
	 */
	public List<SportSfQuestionExDto> selectInfo(SportSfQuestionExDto dto);
	
}
