package com.tianfang.business.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tianfang.business.dto.SportGameDto;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;

/**
 * 热门赛事
 * @author Administrator
 *
 */
@Service
public interface IGameService {

	/**
	 * 添加
	 * @param sportGame
	 * @return
	 */
	long insertGame(SportGameDto sportGame);

	/**
	 * 修改
	 * @param sportGame
	 * @return
	 */
	long updateGame(SportGameDto sportGame);

	/**
	 * 删除
	 * @param sportGame
	 * @return
	 */
	long deleteGame(String id);

	/**
	 * 分页查询数据
	 * @param map
	 * @param page
	 * @return
	 */
	PageResult<SportGameDto> getAllPage(Map<String, Object> map, PageQuery page);

	/**
	 * 根据条件查询 不分页
	 * @param map
	 * @return
	 */
	SportGameDto getByCriteria(Map<String, Object> map);

	/**
	 * 获取全部赛事信息列表
	 * @return
	 */
	List<SportGameDto> getGameAll();

	/**
	 * 修改页面显示位置
	 * @param id
	 * @param pageRanking
	 * @return
	 */
	long updateRanking(String id, String pageRanking);

}
