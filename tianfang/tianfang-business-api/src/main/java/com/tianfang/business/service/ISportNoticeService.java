package com.tianfang.business.service;

import java.util.List;

import com.tianfang.business.dto.SportNoticeDto;
import com.tianfang.common.model.PageResult;

/**
 * 球队公告操作
 * 
 * @author xiang_wang
 *
 * 2015年11月14日下午3:12:19
 */
public interface ISportNoticeService {
	/**
	 * 保存球队公告信息
	 * 
	 * @param notice
	 */
	int saveNotice(SportNoticeDto notice);
	
	/**
	 * 非物理删除,更新球队公告状态为0
	 * 
	 * @param id
	 */
	int deleteNotice(String id);
	
	/**
	 * 更新球队公告信息
	 * 
	 * @param notice
	 */
	int updateNotice(SportNoticeDto notice);
	
	/**
	 * 根据主键id获取球队公告信息
	 * 
	 * @param id
	 * @return SportNoticeDto
	 */
	SportNoticeDto getNoticeById(String id);
	
	/**
	 * 根据参数查询球队公告信息
	 * 
	 * @param params
	 * @return
	 */
	List<SportNoticeDto> findNoticeByParams(SportNoticeDto params);
	
	/**
	 * 根据参数分页查询球队公告信息
	 * @author xiang_wang
	 * 2015年11月14日下午3:08:20
	 */
	PageResult<SportNoticeDto> findNoticeByParams(SportNoticeDto params, int currPage, int pageSize);
	
	/**
	 * 战队公告置顶操作
	 * @author xiang_wang
	 * 2015年11月14日下午3:10:43
	 */
	boolean setTop(String id, String teamId);
	
	/**
	 * 获取球队置顶的公告信息(若没有置顶公告则是最近发布的一条公告)
	 * @param teamId 球队id(必需)
	 * @return
	 * @author xiang_wang
	 * 2015年11月23日下午3:57:17
	 */
	SportNoticeDto getTopNotice(String teamId);
}