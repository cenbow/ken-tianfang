package com.tianfang.business.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.business.dao.AlbumDao;
import com.tianfang.business.dao.SportTeamDao;
import com.tianfang.business.dto.SportTeamDto;
import com.tianfang.business.pojo.SportAlbum;
import com.tianfang.business.pojo.SportTeam;
import com.tianfang.business.service.ITeamService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;

@Service
public class TeamServiceImpl implements ITeamService {

	@Autowired
	private SportTeamDao teamDao;
	
	@Autowired
	private AlbumDao albumDao;
	
	@Override
	public long saveTeam(SportTeamDto teamDto) {
		if(teamDto.getSetUpTimeStr()!=null&&!teamDto.getSetUpTimeStr().equals("")){
			teamDto.setSetUpTime(DateUtils.parse(teamDto.getSetUpTimeStr(), DateUtils.YMD_DASH));
		}
		SportTeam sportTeam = BeanUtils.createBeanByTarget(teamDto,SportTeam.class);
		long statu = teamDao.saveTeam(sportTeam);
		//
		SportAlbum sportAlbuma = new SportAlbum();
		sportAlbuma.setId(UUIDGenerator.getUUID());
		sportAlbuma.setTitle("全家福");
		sportAlbuma.setGameId(sportTeam.getGameId());
		sportAlbuma.setTeamId(sportTeam.getId());
		sportAlbuma.setCreateTime(new Date());
		sportAlbuma.setStat(DataStatus.ENABLED);
		sportAlbuma.setPicCount(0);
		albumDao.insert(sportAlbuma);
		
		return statu;
	}
	
	@Override
	public PageResult<SportTeamDto> getByCriteriaPage(PageQuery page,
			Map<String, Object> map) {
		List<SportTeam> lis = teamDao.getByCriteriaPage(page,map);
		List<SportTeamDto> result = BeanUtils.createBeanListByTarget(lis, SportTeamDto.class);
		for (int i = 0; i < lis.size(); i++) {
			result.get(i).setSetUpTimeStr(DateUtils.format(lis.get(i).getSetUpTime(), DateUtils.YMD_DASH));
			result.get(i).setLastUpdateTimeStr(DateUtils.format(lis.get(i).getLastUpdateTime(), DateUtils.YMD_DASH));
			result.get(i).setCreateTimeStr(DateUtils.format(lis.get(i).getCreateTime(), DateUtils.YMD_DASH));
		}
		if(page!=null){
			long total = teamDao.countCriteria(map);
			page.setTotal(total);
		}
		return new PageResult<SportTeamDto>(page, result);
	}

	@Override
	public long editTeam(SportTeamDto teamDto) {
		teamDto.setSetUpTime(DateUtils.parse(teamDto.getSetUpTimeStr(), DateUtils.YMD_DASH));
		SportTeam sportTeam = BeanUtils.createBeanByTarget(teamDto,SportTeam.class);
		long statu = teamDao.editTeam(sportTeam);
		return statu;
	}

	@Override
	public long delTeam(String ids) {
		String [] id = ids.split(",");
		long statu =0 ;
		for (String str : id) {
			statu = teamDao.delTeam(str);
			if(statu==0){
				return 0;
			}
		}
		return statu;
	}

	@Override
	public List<SportTeamDto> getAllTeam() {
		List<SportTeam> list =teamDao.getByCriteriaPage(null, null);
		return BeanUtils.createBeanListByTarget(list, SportTeamDto.class);
	}

	@Override
	public SportTeamDto selectById(Map<String, Object> map) {
		List<SportTeam> lis = teamDao.getByCriteriaPage(null, map);
		return lis.size()< 1 ? null : (SportTeamDto) BeanUtils.createBeanListByTarget(lis, SportTeamDto.class).get(0) ;
	}

	@Override
	public List<SportTeamDto> queryHotTeam(Map<String, Object> params, int total) {
		params.put("total", total);
		List<SportTeamDto> datas = teamDao.queryHotTeam(params);
		if (null != datas && datas.size() > 0){
			for (SportTeamDto dto : datas){
				dto.setSetUpTimeStr(DateUtils.format(dto.getSetUpTime(), DateUtils.YMD_DASH));
				dto.setLastUpdateTimeStr(DateUtils.format(dto.getLastUpdateTime(), DateUtils.YMD_DASH));
				dto.setCreateTimeStr(DateUtils.format(dto.getCreateTime(), DateUtils.YMD_DASH));
			}
		}
		
		return datas;
	}
	@Override
	public PageResult<SportTeamDto> selectTeam(Map<String, Object> params, PageQuery page) {
		long total = teamDao.countCriteria(params);
		if (total > 0){
			if (params.containsKey("name") && StringUtils.isNotBlank(params.get("name").toString().trim())){
				params.put("name","%"+params.get("name")+"%");
			}
			params.put("start", page.getStartNum());
			params.put("total", page.getPageSize());
			List<SportTeamDto> datas = teamDao.selectTeam(params);
			
			if (null != datas && datas.size() > 0){
				for (SportTeamDto dto : datas){
					dto.setSetUpTimeStr(DateUtils.format(dto.getSetUpTime(), DateUtils.YMD_DASH));
					dto.setLastUpdateTimeStr(DateUtils.format(dto.getLastUpdateTime(), DateUtils.YMD_DASH));
					dto.setCreateTimeStr(DateUtils.format(dto.getCreateTime(), DateUtils.YMD_DASH));
				}
			}
			page.setTotal(total);
			PageResult<SportTeamDto> result = new PageResult<SportTeamDto>(page, datas);
		
			return result;
		}
		
		return null;
	}

	
	@Override
	public long countTeam(Map<String, Object> map) {
		return teamDao.countCriteria(map);
	}

	@Override
	public List<SportTeamDto> queryTeamByParams(SportTeamDto teamDto) {
		List<SportTeam> list =teamDao.queryTeamByParams(teamDto);
		List<SportTeamDto> dataList =  BeanUtils.createBeanListByTarget(list, SportTeamDto.class);		
		return dataList;
	}

	/**
	 * @author YIn
	 * @time:2015年12月10日 下午4:16:32
	 */
	@Override
	public SportTeamDto findTeamBy(SportTeamDto teamDto) {
		SportTeam team =teamDao.selectByPrimaryKey(teamDto.getId());
		if(team==null){
			return null;
		}
		return BeanUtils.createBeanByTarget(team, SportTeamDto.class);
	}
}