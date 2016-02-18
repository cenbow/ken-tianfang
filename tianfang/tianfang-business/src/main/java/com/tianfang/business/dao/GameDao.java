package com.tianfang.business.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.business.mapper.SportGameExMapper;
import com.tianfang.business.mapper.SportGameMapper;
import com.tianfang.business.pojo.SportGame;
import com.tianfang.business.pojo.SportGameExample;
import com.tianfang.business.pojo.SportGameExample.Criteria;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;

@Repository
public class GameDao extends MyBatisBaseDao<SportGame> {

	@Autowired
	@Getter
	private SportGameMapper mapper;
	@Autowired
	@Getter
	private SportGameExMapper mapperEx;
	
	private Logger log = Logger.getLogger(getClass());
	/**
	 * 新增
	 * @param sportGame
	 * @return
	 */
	public long insertGame(SportGame sportGame) {
		sportGame.setId(UUID.randomUUID()+"");
		sportGame.setCreateTime(new Date());
		sportGame.setLastUpdateTime(new Date());
		sportGame.setStat(1);
		if(getAllPage(null,null).size()==0){
			sportGame.setPageRanking(1);
		}else{
			sportGame.setPageRanking(getPageRanking());
		}
		try {
			mapper.insert(sportGame);
		} catch (Exception e) {
			log.error("根据课程id和区域id查询可报名的地点发生异常", e);
			return 0;
		}
		return 1;
	}

	/**
	 * 修改
	 * @param sportGame
	 * @return
	 */
	public long updateGame(SportGame sportGame) {
		try {
			mapper.updateByPrimaryKey(sportGame);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public long deleteGame(String id) {
		try {
			SportGame game = mapper.selectByPrimaryKey(id);
			game.setStat(DataStatus.DISABLED);
			mapper.updateByPrimaryKey(game);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

	/**
	 * 分页查询
	 * @param map
	 * @param page
	 * @return
	 */
	public List<SportGame> getAllPage(Map<String, Object> map, PageQuery page) {
		SportGameExample example = new SportGameExample();
		Criteria criteria = example.createCriteria();
		doByParam(criteria,map);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		if(page!=null){
			example.setOrderByClause(" page_ranking  limit "+page.getStartNum()+","+page.getPageSize());
		}
		return mapper.selectByExample(example);
	}

	/**
	 * 查询条件
	 * @param criteria
	 * @param map
	 */
	private void doByParam(Criteria criteria, Map<String, Object> map) {
		if(map!=null){
			if(map.get("id")!=null && !map.get("id").equals("")){
				criteria.andIdEqualTo(map.get("id")+"");
			}
			if(map.get("title")!=null && !map.get("title").equals("")){
				criteria.andTitleLike("%"+map.get("title")+"%");
			}
			if(map.get("subtitle")!=null && !map.get("subtitle").equals("")){
				criteria.andTitleLike("%"+map.get("subtitle")+"%");
			}
			if(map.get("pageRanking")!=null && !map.get("pageRanking").equals("")){
				criteria.andPageRankingEqualTo(Integer.valueOf(map.get("pageRanking")+""));
			}
		}
	}

	/**
	 * 最大页数
	 * @param map
	 * @param page
	 * @return
	 */
	public long countAllPage(Map<String, Object> map, PageQuery page) {
		SportGameExample example = new SportGameExample();
		Criteria criteria = example.createCriteria();
		doByParam(criteria,map);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		if(page!=null){
			example.setOrderByClause(" create_time desc limit "+page.getStartNum()+","+page.getPageSize());
		}
		return mapper.selectByExample(example).size();
	}
	/**
	 * 获取页面排序最大条数
	 * @return
	 */
	public Integer getPageRanking(){
		return (mapperEx.getMaxPage()+1);
	}
}
