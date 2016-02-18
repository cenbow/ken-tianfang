package com.tianfang.business.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.business.dao.GameDao;
import com.tianfang.business.dto.SportGameDto;
import com.tianfang.business.pojo.SportGame;
import com.tianfang.business.service.IGameService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;

@Service
public class GameServiceImpl implements IGameService {

	@Autowired
	private GameDao gameDao;
	
	@Override
	public long insertGame(SportGameDto sportGameDto) {
		SportGame sportGame = BeanUtils.createBeanByTarget(sportGameDto,SportGame.class);
		long statu = gameDao.insertGame(sportGame);
		return statu;
	}

	@Override
	public long updateGame(SportGameDto sportGameDto) {
		SportGame sportGame = BeanUtils.createBeanByTarget(sportGameDto,SportGame.class);
		//根據id查詢對象
		Map<String,Object> map  = new HashMap<String, Object>();
		map.put("id", sportGameDto.getId());
		SportGame game = gameDao.getAllPage(map, null).get(0); 
		if(sportGame.getMicroPic()==null || sportGame.getMicroPic().equals("")){
			sportGame.setMicroPic(game.getMicroPic());
		}
		sportGame.setLastUpdateTime(new Date());
		sportGame.setCreateTime(game.getCreateTime());
		sportGame.setPageRanking(game.getPageRanking());
		sportGame.setStat(DataStatus.ENABLED);
		long statu = gameDao.updateGame(sportGame);
		return statu;
	}

	@Override
	public long deleteGame(String id) {
		String[] ids= id.split(",");
		for (int i = 0; i < ids.length; i++) {
			long statu = gameDao.deleteGame(ids[i]);
			if(statu==0){
				return 0;
			}
		}
		return 1;
	}

	@Override
	public PageResult<SportGameDto> getAllPage(Map<String, Object> map,
			PageQuery page) {
		List<SportGame> lis= gameDao.getAllPage(map,page);
		List<SportGameDto> lisDto = BeanUtils.createBeanListByTarget(lis, SportGameDto.class);
		for (int i = 0; i < lisDto.size(); i++) {
			lisDto.get(i).setCreateTimeStr(DateUtils.format(lisDto.get(i).getCreateTime(),DateUtils.YMD_DASH_WITH_TIME));
			lisDto.get(i).setLastUpdateTimeStr(DateUtils.format(lisDto.get(i).getLastUpdateTime(),DateUtils.YMD_DASH_WITH_TIME));
		}
		if(page!=null){
			long total = gameDao.countAllPage(map,page);
			page.setTotal(total);
		}
		return new PageResult<SportGameDto>(page,lisDto);
	}

	@Override
	public SportGameDto getByCriteria(Map<String, Object> map) {
		List<SportGame> gameList = gameDao.getAllPage(map,null);
		if(gameList!=null && gameList.size()>0){
			SportGame game = gameList.get(0);
			return BeanUtils.createBeanByTarget(game, SportGameDto.class);
		}else{
			return null;
		}
	}

	@Override
	public List<SportGameDto> getGameAll() {
		List<SportGame> game = gameDao.getAllPage(null, null);
		return BeanUtils.createBeanListByTarget(game, SportGameDto.class);
	}

	@Override
	public long updateRanking(String id, String pageRanking) {
		//根据id查询
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		SportGame game = gameDao.getAllPage(map,null).get(0);
		//跟据pageRanking查询
		Map<String,Object> mapPage = new HashMap<String, Object>();
		mapPage.put("pageRanking", pageRanking);
		List<SportGame> gamePageLis = gameDao.getAllPage(mapPage,null);
		//当前位置没被占用
		if(gamePageLis.size()<1){
			game.setPageRanking(Integer.valueOf(pageRanking));
			return gameDao.updateGame(game);
		}else if(gamePageLis.size() ==1){
			SportGame pagePame = gamePageLis.get(0);
			if(pagePame.getPageRanking() == game.getPageRanking()){
				return 1;
			}
			//交换pageRanking位置
			int i = 0;
			i=pagePame.getPageRanking();
			pagePame.setPageRanking(game.getPageRanking());
			game.setPageRanking(i);
			if(gameDao.updateGame(game)<1||gameDao.updateGame(pagePame)<1){
				return 0;
			}
		}else{
			return 0;  //异常  pageRanking保持唯一
		}
		return 1;
	}
}
