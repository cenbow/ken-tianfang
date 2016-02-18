package com.tianfang.news.service;

import java.util.List;

import com.tianfang.common.model.PageResult;
import com.tianfang.news.dto.CoachTrainDto;

public interface ICoachTrainService {
    /**
     * 分页查询
     * @author YIn
     * @time:2015年12月24日 上午10:52:40
     */
	PageResult<CoachTrainDto> findCoachTrainByParams(
			CoachTrainDto coachTrainDto, int currPage, int pageSize);
	/**
	 * 增加培训
	 * @author YIn
	 * @time:2015年12月24日 上午11:52:02
	 * @param coachTrainDto
	 * @return
	 */
	Integer addCoachTrain(CoachTrainDto coachTrainDto);
	
	/** 
	 * 删除培训
	 * @author YIn
	 * @time:2015年12月24日 下午2:19:12
	 * @param id
	 * @return
	 */
	int delCoachTrain(String id);
	
	/**
	 *  批量删除培训
	 * @author YIn
	 * @time:2015年12月24日 下午2:28:11
	 * @param ids
	 * @return
	 */
	Integer delByIds(String ids);
	
	/**
	 * 根据ID查询
	 * @author YIn
	 * @time:2015年12月24日 下午5:22:29
	 * @param id
	 * @return
	 */
	CoachTrainDto selectCoachTrainById(String id);
	
	/**
	 * 更新培训
	 * @author YIn
	 * @time:2015年12月24日 下午7:16:28
	 * @param coachTrainDto
	 * @return
	 */
	int updateCoachTrain(CoachTrainDto coachTrainDto);
	
	/**
	 * 更改发布状态
	 * @author YIn
	 * @time:2015年12月24日 下午7:40:26
	 * @param id
	 * @param releaseStat
	 */
	void release(String id, Integer releaseStat);
	/**
	 * 
		 * 此方法描述的是：查询没有分页
		 * @author: cwftalus@163.com
		 * @version: 2015年12月25日 上午1:28:08
	 */
	List<CoachTrainDto> findCoachTrainByParams(CoachTrainDto coachTrainDto);
	

}