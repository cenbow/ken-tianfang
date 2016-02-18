package com.tianfang.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.business.dao.SportRaceDao;
import com.tianfang.business.dao.SportTeamDao;
import com.tianfang.business.dto.SportRaceDto;
import com.tianfang.business.pojo.SportRace;
import com.tianfang.business.pojo.SportTeam;
import com.tianfang.business.service.ISportRaceService;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;

@Service
public class SportRaceServiceImpl implements ISportRaceService {

	@Autowired
	private SportRaceDao dao;
	@Autowired
	private SportTeamDao teamDao;
	

	@Override
	public int saveRace(SportRaceDto dto) {
		checkExceptionForRace(dto);
		dao.insert(changToEntity(dto));
		updateTeam(dto);
		
		return 1;
	}

	@Override
	public int deleteRace(String id) {
		checkIdException(id);
		String[] ids = id.split(",");
		if (ids.length > 0) {
			SportRace entity;
			for (String str : ids) {
				dao.deleteRace(str);
				entity = dao.selectByPrimaryKey(id);
				resetTeam(entity);
			}
		} else {
			dao.deleteRace(id);
			SportRace entity = dao.selectByPrimaryKey(id);
			resetTeam(entity);
			
		}
		return 1;
	}

	@Override
	public int updateRace(SportRaceDto dto) {
		checkExceptionForRace(dto);
		checkIdException(dto.getId());
		SportRace entity = dao.selectByPrimaryKey(dto.getId());
		checkExceptionForRace(entity);
		
		updateTeam(entity, dto);
		
		setEntity(dto, entity);
		dao.updateByPrimaryKey(entity);
		
		return 1;
	}

	@Override
	public SportRaceDto getRaceById(String id) {
		SportRaceDto dto = new SportRaceDto();
		dto.setId(id);
		List<SportRaceDto> data = dao.findRaceByParams(dto);
		if (data != null && data.size() == 1) {
			formatDate(data.get(0));
			return data.get(0);
		}
		return null;
	}

	@Override
	public List<SportRaceDto> findRaceByParams(SportRaceDto params) {
		List<SportRaceDto> datas = dao.findRaceByParams(params);
		if (null != datas && datas.size() > 0) {
			for (SportRaceDto data : datas) {
				formatDate(data);
			}
			return datas;
		}
		return null;
	}

	@Override
	public PageResult<SportRaceDto> findRaceByParams(SportRaceDto params,
			int currPage, int pageSize) {
		int total = dao.countRaceByParams(params);
		if (total > 0) {
			PageQuery page = new PageQuery(total, currPage, pageSize);
			List<SportRaceDto> datas = dao.findRaceByParams(params, page);
			if (null != datas && datas.size() > 0) {
				int index = (currPage - 1)*pageSize;
				for (SportRaceDto data : datas){
					formatDate(data);
					data.setIndex("第"+(total - index)+"场");
					index++;
				}
				
				PageResult<SportRaceDto> result = new PageResult<SportRaceDto>(
						page, datas);

				return result;
			}
		}
		return null;
	}

	private SportRace changToEntity(SportRaceDto dto) {
		if (dto == null) {
			return null;
		}
		SportRace entity = new SportRace();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}

	/*
	 * private SportRaceDto changToDto(SportRace entity) { if (entity == null) {
	 * return null; } SportRaceDto dto = new SportRaceDto();
	 * BeanUtils.copyProperties(entity, dto); return dto; }
	 */

	private void checkExceptionForRace(SportRaceDto dto) {
		if (null == dto) {
			throw new RuntimeException("对不起,赛事信息为空");
		}
		if (null == dto.getRaceTime()) {
			if (StringUtils.isNotBlank(dto.getRaceTimeStr())) {
				dto.setRaceTime(DateUtils.StringToDate(dto.getRaceTimeStr(),
						"yyyy-MM-dd HH:mm"));
			} else {
				if (StringUtils.isBlank(dto.getRaceTimeDay())
						|| StringUtils.isBlank(dto.getRaceTimeHour())) {
					throw new RuntimeException("对不起,赛事对战时间为空");
				} else {
					dto.setRaceTime(DateUtils.StringToDate(dto.getRaceTimeDay()
							.trim() + " " + dto.getRaceTimeHour().trim(),
							"yyyy-MM-dd HH:mm"));
				}
			}
		}
	}

	private void checkExceptionForRace(SportRace entity) {
		if (null == entity) {
			throw new RuntimeException("对不起,赛事信息不存在");
		}
	}

	private void checkIdException(String id) {
		if (StringUtils.isBlank(id)) {
			throw new RuntimeException("对不起,赛事主键id为空!");
		}
	}

	private void setEntity(SportRaceDto dto, SportRace entity) {
		if (StringUtils.isNotBlank(dto.getName())){
			entity.setName(dto.getName());
		}
		entity.setType(dto.getType());
		entity.setRaceTime(dto.getRaceTime());
		entity.setRaceAddress(dto.getRaceAddress());
		entity.setHomeTeam(dto.getHomeTeam());
		entity.setVsTeam(dto.getVsTeam());
		entity.setHomeTeamNumber(dto.getHomeTeamNumber());
		entity.setVsTeamNumber(dto.getVsTeamNumber());
		if (null != dto.getRaceStatus()){
			entity.setRaceStatus(dto.getRaceStatus());
		}
	}

	/**
	 * 日期数据格式化
	 * @param data
	 * @author xiang_wang
	 * 2015年11月23日上午9:41:11
	 */
	private void formatDate(SportRaceDto data) {
		data.setCreateTimeStr(DateUtils.format(data.getCreateTime(),
				"yyyy-MM-dd HH:mm:ss"));
		data.setRaceTimeStr(DateUtils.format(data.getRaceTime(),
				"yyyy-MM-dd HH:mm"));
		data.setRaceTimeDay(DateUtils.format(data.getRaceTime(),
				"yyyy-MM-dd"));
		data.setRaceTimeHour(DateUtils.format(data.getRaceTime(),
				"HH:mm"));
	}
	
	/**
	 * 新增球赛时更新对应球队的比赛平胜负战绩
	 * @param dto
	 * @author xiang_wang
	 * 2015年12月11日下午4:39:32
	 */
	private void updateTeam(SportRaceDto dto) {
		// 比赛结束才触发
		if (null != dto && null != dto.getRaceStatus() && dto.getRaceStatus().intValue() == 2){
			SportTeam home = teamDao.selectByPrimaryKey(dto.getHomeTeam());
			SportTeam vs = teamDao.selectByPrimaryKey(dto.getVsTeam());
			int homeNumber = dto.getHomeTeamNumber();
			int vsNumber = dto.getVsTeamNumber();
			// 主场胜
			if (homeNumber > vsNumber){
				home.setWin(initInteger(home.getWin()) + 1);
				vs.setLost(initInteger(vs.getLost()) + 1);
			}else if(homeNumber < vsNumber){
				home.setLost(initInteger(home.getLost()) + 1);
				vs.setWin(initInteger(vs.getWin()) + 1);
			}else{
				home.setDrew(initInteger(home.getDrew()) + 1);
				vs.setDrew(initInteger(vs.getDrew()) + 1);
			}
			teamDao.updateByPrimaryKey(home);
			teamDao.updateByPrimaryKey(vs);
		}
	}
	
	/**
	 * 更新球赛时更新对应球队的比赛平胜负战绩
	 * @param before 之前的数据
	 * @param after  修改后的数据
	 * @author xiang_wang
	 * 2015年12月11日下午4:44:23
	 */
	private void updateTeam(SportRace before, SportRaceDto after) {
		if (null != before && null != before.getRaceStatus() && before.getRaceStatus().intValue() != 2){
			if (after.getRaceStatus().intValue() == 2){
				if (before.getHomeTeam().equals(after.getHomeTeam()) && before.getVsTeam().equals(after.getVsTeam())){
					updateTeam(after);
				}else{
					resetTeam(before); // 还原到初始状态
					
					updateTeam(after);
				}
			}
		}else{
			resetTeam(before);
			
			updateTeam(after);
		}
	}

	/**
	 * 重置对应球队的比赛平胜负战绩
	 * @param before
	 * @author xiang_wang
	 * 2015年12月11日下午5:09:55
	 */
	private void resetTeam(SportRace before) {
		if (null != before && null != before.getRaceStatus() && before.getRaceStatus().intValue() == 2){
			SportTeam home = teamDao.selectByPrimaryKey(before.getHomeTeam());
			SportTeam vs = teamDao.selectByPrimaryKey(before.getVsTeam());
			int homeNumber = before.getHomeTeamNumber();
			int vsNumber = before.getVsTeamNumber();
			// 主场胜
			if (homeNumber > vsNumber){
				home.setWin(home.getWin() - 1);
				vs.setLost(vs.getLost() - 1);
			}else if(homeNumber < vsNumber){
				home.setLost(home.getLost() - 1);
				vs.setWin(vs.getWin() - 1);
			}else{
				home.setDrew(home.getDrew() - 1);
				vs.setDrew(vs.getDrew() - 1);
			}
			teamDao.updateByPrimaryKey(home);
			teamDao.updateByPrimaryKey(vs);
		}
	}
	
	private int initInteger(Integer arg){
		return arg == null? 0 : arg.intValue();
	}
}