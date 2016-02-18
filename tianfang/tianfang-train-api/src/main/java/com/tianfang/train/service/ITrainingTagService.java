package com.tianfang.train.service;

import org.springframework.stereotype.Service;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.CourseTagDto;

@Service
public interface ITrainingTagService {
	/***
	 * 分页查询课程标签
	 * 
	 * @param conTagDto
	 * @param page
	 * @return
	 */
	public PageResult<CourseTagDto> findByPage(CourseTagDto courseTagDto,
			PageQuery page);

	/***
	 * 添加标签
	 * 
	 * @param courseTagDto
	 */
	public Object insertTag(CourseTagDto courseTagDto);

	/***
	 * 删除标签
	 * 
	 * @param id
	 */
	public Object deleteById(String id);

	/***
	 * 修改标签
	 * 
	 * @param courseTagDto
	 */
	public Object updateTag(CourseTagDto courseTagDto);
	
	public CourseTagDto getTag(String id);
}
