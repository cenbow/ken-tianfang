package com.tianfang.business.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.business.dto.SportTeamDto;
import com.tianfang.business.mapper.SportTeamExMapper;
import com.tianfang.business.mapper.SportTeamMapper;
import com.tianfang.business.pojo.SportTeam;
import com.tianfang.business.pojo.SportTeamExample;
import com.tianfang.business.pojo.SportTeamExample.Criteria;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;


@Repository
public class SportTeamDao extends MyBatisBaseDao<SportTeam> {
	
	@Autowired
	@Getter
	private SportTeamMapper mapper;
	
	@Autowired
	private SportTeamExMapper exMapper;
	
	
	public long saveTeam(SportTeam sportTeam) {
		if(sportTeam.getMembers() == null){
			sportTeam.setMembers(0);
		}
		if(sportTeam.getSetUpTime() == null || sportTeam.getSetUpTime().equals("")){
			sportTeam.setSetUpTime(new Date());
		}
		if(sportTeam.getWin() == null || sportTeam.getWin().equals("")){
			sportTeam.setWin(0);
		}
		if(sportTeam.getDrew() == null || sportTeam.getDrew().equals("")){
			sportTeam.setDrew(0);
		}
		if(sportTeam.getLost() == null || sportTeam.getLost().equals("")){
			sportTeam.setLost(0);
		}
		sportTeam.setId(UUID.randomUUID()+"");
		sportTeam.setCreateTime(new Date());
		sportTeam.setLastUpdateTime(new Date());
		sportTeam.setStat(DataStatus.ENABLED);
		try {
			mapper.insert(sportTeam);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

	public List<SportTeam> getByCriteriaPage(PageQuery page,
			Map<String, Object> map) {
		SportTeamExample example = new SportTeamExample();
		Criteria criteria = example.createCriteria();
		byCriteria(criteria,map);
		if(page!=null){
			example.setOrderByClause(" create_time desc limit "+page.getStartNum()+","+page.getPageSize());
		}
		return mapper.selectByExample(example);
	}

	/**
	 * 此方法根据条件查询
	 * @param criteria
	 * @param team
	 */
	private void byCriteria(Criteria criteria,Map<String, Object> map) {
		if(map!=null){
			if(map.get("id")!=null && !map.get("id").equals("")){
				criteria.andIdEqualTo(map.get("id")+"");
			}
			if(map.get("gameId")!=null && !map.get("gameId").equals("")){
				criteria.andGameIdEqualTo(map.get("gameId")+"");
			}
			if(map.get("name")!=null && !map.get("name").equals("")){
				criteria.andNameLike("%"+map.get("name")+"%");
			}
			if(map.get("distruct")!=null && !map.get("distruct").equals("")){
				criteria.andDistructEqualTo(map.get("distruct")+"");
			}
			if(map.get("grade")!=null && !map.get("grade").equals("")){
				criteria.andGradeEqualTo(map.get("grade")+"");
			}
			if(map.get("teamType")!=null && !map.get("teamType").equals("")){
				criteria.andTeamTypeEqualTo(map.get("teamType")+"");
			}
			if(map.get("stat")!=null && !map.get("stat").equals("")){
				criteria.andStatEqualTo(Integer.parseInt((map.get("stat") + "")));
			}
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}

	/**
	 * 获取最大条数
	 * @param page
	 * @param map
	 * @return
	 */
	public long countCriteria(Map<String, Object> map) {
		SportTeamExample example = new SportTeamExample();
		Criteria criteria = example.createCriteria();
		byCriteria(criteria,map);
		return mapper.countByExample(example);
	}

	public long editTeam(SportTeam team) {
		SportTeam sport = mapper.selectByPrimaryKey(team.getId());
		byIsNull(team,sport);
		team.setLastUpdateTime(new Date());
		team.setStat(DataStatus.ENABLED);
		try {
			mapper.updateByPrimaryKeySelective(team);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	public long delTeam(String teamId) {
		SportTeam sportTeam = mapper.selectByPrimaryKey(teamId);
		try {
			sportTeam.setStat(DataStatus.DISABLED);
			mapper.updateByPrimaryKey(sportTeam);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 判断条件是否为null
	 * @param team
	 * @param sport
	 */
	public void byIsNull(SportTeam team,SportTeam sport){
		if(team.getLogo()==null || team.getLogo().equals("")){
			team.setLogo(sport.getLogo());
		}
		if(team.getSetUpTime()==null ||team.getSetUpTime().equals("")){
			team.setSetUpTime(sport.getSetUpTime());
		}
		if(team.getLineup()==null ||team.getLineup().equals("")){
			team.setLineup(sport.getLineup());
		}
		if(team.getOwnerId()==null || team.getOwnerId().equals("")){
			team.setOwnerId(sport.getOwnerId());
		}
		if(team.getProvince()==null || team.getProvince().equals("")){
			team.setProvince(sport.getProvince());
		}
		if(team.getDistruct()==null || team.getDistruct().equals("")){
			team.setDistruct(sport.getDistruct());
		}
		if(team.getName() == null || team.getName().equals("")){
			team.setName(sport.getName());
		}
		if(team.getHomeCourt()==null || team.getHomeCourt().equals("")){
			team.setHomeCourt(sport.getHomeCourt());
		}
		if(team.getCreater()== null || team.getCreater().equals("")){
			team.setCreater(sport.getCreater());
		}
		if(team.getMembers()== null || team.getMembers().equals("")){
			team.setMembers(sport.getMembers());
		}
		if(team.getSetUpTime()== null || team.getSetUpTime().equals("")){
			team.setSetUpTime(sport.getSetUpTime());
		}
		if(team.getTeamIntroduce()== null ||team.getTeamIntroduce().equals("")){
			team.setTeamIntroduce(sport.getTeamIntroduce());
		}
	}
	
	public List<SportTeamDto> queryHotTeam(Map<String, Object> params){
		 return exMapper.queryHotTeam(params);
	 }
	
	public List<SportTeamDto> selectTeam(Map<String, Object> params){
		 return exMapper.selectTeam(params);
	 }

	public List<SportTeam> queryTeamByParams(SportTeamDto teamDto) {
		SportTeamExample example = new SportTeamExample();
		Criteria criteria = example.createCriteria();
		
		if(!StringUtils.isEmpty(teamDto.getOwnerId())){
			criteria.andOwnerIdEqualTo(teamDto.getOwnerId());	
		}
		
		if(!StringUtils.isEmpty(teamDto.getGameId())){
			criteria.andGameIdEqualTo(teamDto.getGameId());	
		}
		
		criteria.andStatEqualTo(DataStatus.ENABLED);
		
		return mapper.selectByExample(example);
	}
	
}