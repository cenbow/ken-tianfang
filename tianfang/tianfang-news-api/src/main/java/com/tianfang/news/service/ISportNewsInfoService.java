package com.tianfang.news.service;

import java.util.List;
import java.util.Map;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.news.dto.SportNewsInfoDto;

public interface ISportNewsInfoService {

	/**
	 * 保存新闻(资讯)信息
	 * 
	 * @param news
	 */
    SportNewsInfoDto saveNews(SportNewsInfoDto news);
	
	/**
	 * 非物理删除,更新新闻(资讯)状态为0
	 * 
	 * @param id
	 */
	int deleteNews(String id);
	
	/**
	 * 更新新闻(资讯)信息
	 * 
	 * @param news
	 */
	SportNewsInfoDto updateNews(SportNewsInfoDto news);
	
	/**
	 * 根据主键id获取新闻(资讯)信息
	 * 
	 * @param id
	 * @return
	 */
	SportNewsInfoDto getNewsById(String id);
	
	/**
	 * 根据参数查询新闻(资讯)信息
	 * 
	 * @param params
	 * @return
	 */
	List<SportNewsInfoDto> findNewsByParams(Map<String, Object> params);
	
	/**
	 * 根据参数分页查询新闻(资讯)信息
	 * 
	 * @param params
	 * @param page
	 * @return
	 */
	PageResult<SportNewsInfoDto> findNewsByParams(Map<String, Object> params, int currPage, int pageSize);
	
	/**
	 * 
		 * 此方法描述的是：根据参数分页查询新闻(资讯)信息
		 * @author: cwftalus@163.com
		 * @version: 2015年10月21日 下午6:34:32
	 */
	PageResult<SportNewsInfoDto> findNewsByParams(SportNewsInfoDto params, int currPage, int pageSize);
	
	/**
	 * 首页展示新闻(资讯)信息
	 * 
	 * @param size 展示最大条数(如果为空或者size小于等于0,默认给3条)
	 * @return
	 */
	List<SportNewsInfoDto> findIndexNews(Integer size);

	/**
	 * 
	 * pageRanking：更改前端页面显示位置
	 * @param id
	 * @param positionId
	 * @return
	 * @exception	
	 * @author Administrator
	 * @date 2015年11月9日 下午4:47:32
	 */
	public int pageRanking(String id, Integer positionId);
	
	/**
	 * 通过条件查询新闻信息
	 * @param dto
	 * @param page
	 * @return
	 */
	public List<SportNewsInfoDto> findNewsByCons(SportNewsInfoDto dto, PageQuery page);
	
	/**
	 * 
	 * release：改变发布状态
	 * @param id
	 * @param releaseStat
	 * @return
	 * @exception	
	 * @author Administrator
	 * @date 2015年11月28日 下午3:24:58
	 */
	public Integer release(String id,Integer releaseStat);
}