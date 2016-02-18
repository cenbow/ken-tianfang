package com.tianfang.train.service;

import java.text.ParseException;

import org.springframework.stereotype.Service;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.TrainingMessageInfoDto;
import com.tianfang.train.dto.UserCourseDto;
import com.tianfang.train.dto.UserCourseMessageDto;

@Service
public interface ITrainingMessageInfosService {

	/***
	 * 分页查询消息详情
	 * 
	 * @param trainingMessageInfoDto
	 * @param page
	 * @return
	 */
	public PageResult<TrainingMessageInfoDto> findPage(
			TrainingMessageInfoDto trainingMessageInfoDto, PageQuery page);

	/***
	 * 增加消息信息
	 * 
	 * @param trainingMessageInfoDto
	 * @return
	 */
	public Object insertMessageInfo(
			TrainingMessageInfoDto trainingMessageInfoDto);

	/***
	 * 删除消息信息
	 * 
	 * @param id
	 * @return
	 */
	public Object deleteMessageInfoById(String id);

	/***
	 * 修改消息信息
	 * 
	 * @param trainingMessageInfoDto
	 * @return
	 */
	public Object updateMessageInfo(
			TrainingMessageInfoDto trainingMessageInfoDto);

	public TrainingMessageInfoDto getMessageInfo(String id);
	
	/**
	 * 查询用户课程消息
	 * @param page
	 * @return
	 * @throws ParseException 
	 */
	PageResult<UserCourseMessageDto> findCourseMessage(UserCourseDto userCourseDto, PageQuery page) throws ParseException;
}
