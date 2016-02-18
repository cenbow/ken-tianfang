package com.tianfang.train.service;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.TrainingMessageInfoDto;
import com.tianfang.train.dto.UserCourseDto;
import com.tianfang.train.dto.UserCourseMessageDto;

/**
 * 消息Service
 * @author meng.meng
 *
 */
public interface ITrainingMessageInfoService {

	/**
	 * 创建保存消息
	 * @param trainingMessageInfoDto
	 */
	void saveTrainingMessageInfo(TrainingMessageInfoDto trainingMessageInfoDto);
	
		
	/**
	 * 查询系统消息
	 * @param trainingMessageInfoDto
	 * @param page
	 * @return
	 */
	PageResult<TrainingMessageInfoDto> findSystemMessage(TrainingMessageInfoDto trainingMessageInfoDto,PageQuery page);
	
	
	/**
	 * 根据消息主键获取消息详细信息
	 * @param id
	 * @return
	 */
	TrainingMessageInfoDto findMessageById(String id);
	/**
	 * 查询用户课程消息
	 * @param page
	 * @return
	 */
	PageResult<UserCourseMessageDto> findCourseMessage(UserCourseDto userCourseDto, PageQuery page);
	

	/**
	 * 更新系统消息状态.(更新为已读等)
	 * @param status
	 */
	void updateSystemMessageStatus(TrainingMessageInfoDto trainingMessageInfoDto);
	

	/**
	 * 删除系统消息
	 */
	void deleteSystemMessage(TrainingMessageInfoDto trainingMessageInfoDto);
	
	
	/**
	 * 清空系统消息
	 */
	void truncateSystemMessage(TrainingMessageInfoDto trainingMessageInfoDto);
}
