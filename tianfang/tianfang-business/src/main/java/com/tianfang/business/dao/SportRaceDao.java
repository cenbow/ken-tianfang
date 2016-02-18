package com.tianfang.business.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.business.dto.SportRaceDto;
import com.tianfang.business.mapper.SportRaceExMapper;
import com.tianfang.business.mapper.SportRaceMapper;
import com.tianfang.business.pojo.SportRace;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;

@Repository
public class SportRaceDao extends MyBatisBaseDao<SportRace> {

	@Getter
	@Autowired
	private SportRaceMapper mapper;

	@Autowired
	private SportRaceExMapper exMapper;

	public void deleteRace(String id) {
		SportRace race = mapper.selectByPrimaryKey(id);
		if (null == race) {
			throw new RuntimeException("对不起,赛事对象不存在!");
		}
		race.setStat(0);
		mapper.updateByPrimaryKey(race);
	}

	public List<SportRaceDto> findRaceByParams(SportRaceDto dto) {
		Map<String, Object> params = assemblyParams(dto);
		List<SportRaceDto> results = exMapper.findRaceByParams(params);
		return results;
	}

	public List<SportRaceDto> findRaceByParams(SportRaceDto dto, PageQuery page) {
		Map<String, Object> params = assemblyParams(dto);
		if (null != page) {
			params.put("start", page.getStartNum());
			params.put("count", page.getPageSize());
		}
		List<SportRaceDto> results = exMapper.findRaceByParams(params);
		return results;
	}

	public int countRaceByParams(SportRaceDto dto) {
		Map<String, Object> params = assemblyParams(dto);
		return exMapper.countRaceByParams(params);
	}

	/*private void assemblyParams(SportRaceDto dto,
			SportRaceExample.Criteria criteria) {
		if (StringUtils.isNotBlank(dto.getId())) {
			criteria.andIdEqualTo(dto.getId());
		}
		if (StringUtils.isNotBlank(dto.getName())) {
			criteria.andNameEqualTo(dto.getName());
		}
		if (StringUtils.isNotBlank(dto.getRaceAddress())) {
			criteria.andRaceAddressLike("%" + dto.getRaceAddress() + "%");
		}
		if (StringUtils.isNotBlank(dto.getHomeTeam())) {
			criteria.andHomeTeamEqualTo(dto.getHomeTeam());
		}
		if (StringUtils.isNotBlank(dto.getVsTeam())) {
			criteria.andVsTeamEqualTo(dto.getVsTeam());
		}

		if (dto.getRaceStatus() != null && dto.getRaceStatus().intValue() >= 0) {
			criteria.andRaceStatusEqualTo(dto.getRaceStatus());
		}
		if (dto.getStat() != null) {
			if (dto.getStat().intValue() == 0 || dto.getStat().intValue() == 1) {
				criteria.andStatEqualTo(dto.getStat());
			}
		} else {
			criteria.andStatEqualTo(DataStatus.ENABLED);
		}

		if (StringUtils.isNotBlank(dto.getStartTimeStr())
				&& StringUtils.isNotBlank(dto.getEndTimeStr())) {
			criteria.andCreateTimeBetween(
					DateUtils.StringToDate(dto.getStartTimeStr(), "yyyy-MM-dd"),
					new Date(DateUtils.StringToDate(dto.getEndTimeStr(),
							"yyyy-MM-dd").getTime()
							+ 24 * 60 * 60 * 1000L - 1));
		}

		if (StringUtils.isNotBlank(dto.getRaceTimeS())
				&& StringUtils.isNotBlank(dto.getRaceTimeE())) {
			criteria.andCreateTimeBetween(
					DateUtils.StringToDate(dto.getRaceTimeS(), "yyyy-MM-dd"),
					new Date(DateUtils.StringToDate(dto.getRaceTimeE(),
							"yyyy-MM-dd").getTime()
							+ 24 * 60 * 60 * 1000L - 1));
		}
	}*/

	private Map<String, Object> assemblyParams(SportRaceDto dto) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(dto.getId())) {
			params.put("id", dto.getId());
		}
		if (StringUtils.isNotBlank(dto.getName())) {
			params.put("name", dto.getName());
		}
		if (StringUtils.isNotBlank(dto.getRaceAddress())) {
			params.put("raceAddress", "%" + dto.getRaceAddress() + "%");
		}
		if (StringUtils.isNotBlank(dto.getHomeTeam())) {
			params.put("homeTeam", dto.getHomeTeam());
		}
		if (StringUtils.isNotBlank(dto.getVsTeam())) {
			params.put("vsTeam", dto.getVsTeam());
		}
		if (StringUtils.isNotBlank(dto.getHomeTeamName())) {
			params.put("homeTeamName", "%" + dto.getHomeTeamName() + "%");
		}
		if (StringUtils.isNotBlank(dto.getVsTeamName())) {
			params.put("vsTeamName", "%" + dto.getVsTeamName() + "%");
		}
		if (StringUtils.isNotBlank(dto.getTeamId())){
			params.put("teamId", dto.getTeamId());
		}

		if (dto.getRaceStatus() != null && dto.getRaceStatus().intValue() >= 0) {
			params.put("raceStatus", dto.getRaceStatus());
		}
		if (dto.getStat() != null) {
			if (dto.getStat().intValue() == 0 || dto.getStat().intValue() == 1) {
				params.put("stat", dto.getStat());
			}
		} else {
			params.put("stat", DataStatus.ENABLED);
		}

		if (StringUtils.isNotBlank(dto.getStartTimeStr())
				&& StringUtils.isNotBlank(dto.getEndTimeStr())) {
			params.put("createTimeS",
					DateUtils.StringToDate(dto.getStartTimeStr(), "yyyy-MM-dd"));
			params.put(
					"createTimeE",
					new Date(DateUtils.StringToDate(dto.getEndTimeStr(),
							"yyyy-MM-dd").getTime()
							+ 24 * 60 * 60 * 1000L - 1));
		}

		if (StringUtils.isNotBlank(dto.getRaceTimeS())
				&& StringUtils.isNotBlank(dto.getRaceTimeE())) {
			params.put("raceTimeS",
					DateUtils.StringToDate(dto.getRaceTimeS(), "yyyy-MM-dd"));
			params.put(
					"raceTimeE",
					new Date(DateUtils.StringToDate(dto.getRaceTimeE(),
							"yyyy-MM-dd").getTime()
							+ 24 * 60 * 60 * 1000L - 1));
		}

		return params;
	}
}