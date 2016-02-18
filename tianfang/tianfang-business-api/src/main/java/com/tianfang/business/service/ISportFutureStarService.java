package com.tianfang.business.service;

import java.util.List;

import com.tianfang.business.dto.SportFutureStarDto;
import com.tianfang.common.model.PageResult;

/**
 * 未来之星操作
 * 
 * @author xiang_wang
 *
 * 2015年11月24日上午9:11:10
 */
public interface ISportFutureStarService {
	
	/**
	 * 保存未来之星信息
	 * @param dto
	 * @return
	 * @author xiang_wang
	 * 2015年11月24日上午9:17:12
	 */
	int saveFutureStar(SportFutureStarDto dto);
	
	/**
	 * 非物理删除,更新未来之星信息状态为0
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2015年11月24日上午9:20:40
	 */
	int deleteFutureStar(String id);
	
	/**
	 * 更新未来之星信息
	 * @param dto
	 * @return
	 * @author xiang_wang
	 * 2015年11月24日上午9:20:51
	 */
	int updateFutureStar(SportFutureStarDto dto);
	
	/**
	 * 根据主键id获取未来之星信息
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2015年11月24日上午9:21:52
	 */
	SportFutureStarDto getFutureStarById(String id);
	
	/**
	 * 根据参数查询未来之星信息
	 * @param params
	 * @return
	 * @author xiang_wang
	 * 2015年11月24日上午9:22:39
	 */
	List<SportFutureStarDto> findFutureStarByParams(SportFutureStarDto params);
	
	/**
	 * 根据参数分页查询未来之星信息
	 * @param params
	 * @param currPage
	 * @param pageSize
	 * @return
	 * @author xiang_wang
	 * 2015年11月24日上午9:22:55
	 */
	PageResult<SportFutureStarDto> findFutureStarByParams(SportFutureStarDto params, int currPage, int pageSize);

	/**
	 * 获取指定数量的未来之星信息
	 * @param type 0-未来之星,1-主题活动 
	 * @param size 展示最大条数(如果为空或者size小于等于0,默认给3条)
	 * @return
	 * @author xiang_wang
	 * 2015年11月24日上午9:34:56
	 */
	List<SportFutureStarDto> findIndexFutureStars(Integer type, Integer size);
	
	/**
	 * 更改前端页面显示位置
	 * @param id 主键id
	 * @param positionId 排序下标
	 * @return
	 * @author xiang_wang
	 * 2015年11月24日上午9:38:37
	 */
	public int pageRanking(String id, Integer positionId);
}