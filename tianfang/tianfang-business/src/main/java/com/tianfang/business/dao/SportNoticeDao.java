package com.tianfang.business.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tianfang.business.dto.SportNoticeDto;
import com.tianfang.business.mapper.SportNoticeExMapper;
import com.tianfang.business.mapper.SportNoticeMapper;
import com.tianfang.business.pojo.SportNotice;
import com.tianfang.business.pojo.SportNoticeExample;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;

@Repository
public class SportNoticeDao extends MyBatisBaseDao<SportNotice> {

	@Getter
	@Autowired
	private SportNoticeMapper mapper;

	@Autowired
	private SportNoticeExMapper exMapper;

	public void deleteNotice(String id) {
		SportNotice Notice = mapper.selectByPrimaryKey(id);
		if (null == Notice) {
			throw new RuntimeException("对不起,球队公告对象不存在!");
		}
		Notice.setStat(0);
		mapper.updateByPrimaryKey(Notice);
	}

	/*public SportNotice getTopNotice(String teamId) {
		SportNoticeExample example = new SportNoticeExample();
		SportNoticeExample.Criteria criteria = example.createCriteria();
		criteria.andTeamIdEqualTo(teamId);
		example.setOrderByClause(" marked desc,create_time desc limit 0,1");
		List<SportNotice> results = mapper.selectByExample(example);
		return CollectionUtils.isEmpty(results) ? null : results.get(0);
	}*/

	public List<SportNoticeDto> findNoticeByParams(SportNoticeDto dto) {
		Map<String, Object> params = assemblyParams(dto);
		List<SportNoticeDto> results = exMapper.findNoticeByParams(params);
		return results;
	}

	public List<SportNoticeDto> findNoticeByParams(SportNoticeDto dto,
			PageQuery page) {
		Map<String, Object> params = assemblyParams(dto);
		if (null != page) {
			params.put("start", page.getStartNum());
			params.put("count", page.getPageSize());
		}
		List<SportNoticeDto> results = exMapper.findNoticeByParams(params);
		return results;
	}

	public int countNoticeByParams(SportNoticeDto dto) {
		SportNoticeExample example = new SportNoticeExample();
		SportNoticeExample.Criteria criteria = example.createCriteria();
		assemblyParams(dto, criteria);
		return mapper.countByExample(example);
	}

	/**
	 * 重置该球队公告置顶
	 * 
	 * @author xiang_wang 2015年11月14日下午4:02:15
	 */
	public void resetTop(String teamId) {
		if (null == teamId || "".equals(teamId.trim())) {
			throw new RuntimeException("对不起,球队id为空");
		}
		exMapper.resetTop(teamId);
	}

	public SportNoticeDto getTopNotice(String teamId){
		Map<String, Object> params = new HashMap<String, Object>(3);
		params.put("teamId", teamId);
		params.put("stat", DataStatus.ENABLED);
		params.put("start", 0);
		params.put("count", 1);
		List<SportNoticeDto> results = exMapper.findNoticeByParams(params);
		return CollectionUtils.isEmpty(results) ? null : results.get(0);
	}
	
	private void assemblyParams(SportNoticeDto dto,
			SportNoticeExample.Criteria criteria) {
		if (StringUtils.isNotBlank(dto.getId())) {
			criteria.andIdEqualTo(dto.getId());
		}
		if (StringUtils.isNotBlank(dto.getTeamId())) {
			criteria.andTeamIdEqualTo(dto.getTeamId());
		}
		if (StringUtils.isNotBlank(dto.getTitle())) {
			criteria.andTitleLike("%" + dto.getTitle() + "%");
		}
		if (StringUtils.isNotBlank(dto.getPublisher())) {
			criteria.andPublisherLike("%" + dto.getPublisher() + "%");
		}

		if (dto.getMarked() != null && dto.getMarked().intValue() >= 0) {
			criteria.andMarkedEqualTo(dto.getMarked());
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
					new Date(DateUtils.StringToDate(dto.getEndTimeStr(), "yyyy-MM-dd").getTime()+24*60*60*1000L-1)
					);
		}
	}
	
	private Map<String, Object> assemblyParams(SportNoticeDto dto) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(dto.getId())) {
			params.put("id", dto.getId());
		}
		if (StringUtils.isNotBlank(dto.getTeamId())) {
			params.put("teamId", dto.getTeamId());
		}
		if (StringUtils.isNotBlank(dto.getTitle())) {
			params.put("title", "%" + dto.getTitle() + "%");
		}
		if (StringUtils.isNotBlank(dto.getPublisher())) {
			params.put("publisher", "%" + dto.getPublisher() + "%");
		}

		if (dto.getMarked() != null && dto.getMarked().intValue() >= 0) {
			params.put("marked", dto.getMarked());
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
			params.put("createTimeS", DateUtils.StringToDate(dto.getStartTimeStr(), "yyyy-MM-dd"));
			params.put("createTimeE", new Date(DateUtils.StringToDate(dto.getEndTimeStr(), "yyyy-MM-dd").getTime()+24*60*60*1000L-1));
		}
		
		return params;
	}
}