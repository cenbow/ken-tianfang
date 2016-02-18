package com.tianfang.business.service;

import java.util.List;
import java.util.Map;

import com.tianfang.business.dto.SportTeamDto;
import com.tianfang.business.dto.SportVideoDto;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;

public interface ISportVideoService {
	/**
	 * 分页查询视频集合
	 * @param page
	 * @param map
	 * @return
	 */
	PageResult<SportVideoDto> getCriteriaPage(PageQuery page,
			Map<String, Object> map);

	/**
	 * 新增
	 * @param videoDto
	 * @return
	 */
	long insertVideo(SportVideoDto videoDto);

	/**
	 * 修改
	 * @param videoDto
	 * @return
	 */
	long editVideo(SportVideoDto videoDto);

	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	long delVideo(String ids);

	/**
	 * 根据id查询
	 * @param map
	 * @return
	 */
	SportVideoDto selectById(Map<String, Object> map);
	/**
	 * 
		 * 此方法描述的是：点击数量计算
		 * @author: cwftalus@163.com
		 * @version: 2015年12月3日 下午2:54:15
	 */
	void updateClickCount(String videoId);
	/**
	 * 
		 * 此方法描述的是：查询top数据
		 * @author: cwftalus@163.com
		 * @version: 2015年12月3日 下午2:54:12
	 */
	List<SportVideoDto> findVideoByTop(int i,Integer videoStatus);

}
