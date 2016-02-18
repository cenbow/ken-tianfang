package com.tianfang.business.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.business.mapper.SportVideoMapper;
import com.tianfang.business.pojo.SportVideo;
import com.tianfang.business.pojo.SportVideoExample;
import com.tianfang.business.pojo.SportVideoExample.Criteria;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;

@Repository
public class SportVideoDao extends MyBatisBaseDao<SportVideo>{

	@Autowired
	@Getter
	private SportVideoMapper mapper;
	
	public List<SportVideo> getCriteriaPage(PageQuery page,
			Map<String, Object> map) {
		SportVideoExample example = new SportVideoExample();
		Criteria criteria = example.createCriteria();
		byCriteria(criteria,map);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		if(page!=null){
			example.setOrderByClause("create_time desc limit "+page.getStartNum()+","+page.getPageSize());	
		}
		return mapper.selectByExample(example);
	}

	public void byCriteria(Criteria criteria,Map<String, Object> map){
		if(map!=null){
			if(map.get("id")!=null && !map.get("id").equals("")){
				criteria.andIdEqualTo(map.get("id")+"");
			}
			if(map.get("videoName")!=null && !map.get("videoName").equals("")){
				criteria.andVideoNameLike("%"+map.get("videoName")+"%");
			}
			if(map.get("publishPeople")!=null && !map.get("publishPeople").equals("")){
				criteria.andPublishPeopleEqualTo(map.get("publishPeople")+"");
			}
			if(map.get("teamId")!=null && !map.get("teamId").equals("")){
				criteria.andTeamIdEqualTo(map.get("teamId")+"");
			}
			if(map.get("gameId")!=null && !map.get("gameId").equals("")){
				criteria.andGameIdEqualTo(map.get("gameId")+"");
			}
			if(map.get("videoStatus")!=null && !map.get("videoStatus").equals("")){
				criteria.andVideoStatusEqualTo(Integer.valueOf(map.get("videoStatus")+""));
			}
			if(map.get("userId")!=null && !map.get("userId").equals("")){
				criteria.andUserIdEqualTo(map.get("userId")+"");
			}
			if(map.get("videoType")!=null && !map.get("videoType").equals("")){
				criteria.andVideoTypeEqualTo(Integer.valueOf(map.get("videoType")+""));
			}
		}
	}

	public long getCount(Map<String, Object> map) {
		SportVideoExample example = new SportVideoExample();
		Criteria criteria = example.createCriteria();
		byCriteria(criteria,map);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example).size();
	}

	public long insertVideo(SportVideo sportVideo) {
		sportVideo.setId(UUID.randomUUID()+"");
		sportVideo.setVideoStatus(DataStatus.DISABLED);
		sportVideo.setStat(DataStatus.ENABLED);
		sportVideo.setCreateTime(new Date());
		sportVideo.setLastUpdateTime(new Date());
		try {
			mapper.insert(sportVideo);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 
	 * @param video  修改对象
	 * @param sport
	 */
	public void getCriteria(SportVideo video,SportVideo sport){
		if(video.getCreateTime()==null || video.getCreateTime().equals("")){
			
		}
		video.getMarked();
		video.getVideoName();
		video.getLastUpdateTime();
		video.getStat();
		video.getVideoStatus();
		video.getId();
	}
	
	public long editVideo(SportVideo video) {			
		return mapper.updateByPrimaryKeySelective(video);
	}

	public long delVideo(String id) {
		SportVideo video =mapper.selectByPrimaryKey(id);
		video.setStat(DataStatus.DISABLED);
		try {
			mapper.updateByPrimaryKey(video);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	public void updateClickCount(String videoId) {
		// TODO Auto-generated method stub
		SportVideo sportVideo = selectByPrimaryKey(videoId);
		sportVideo.setId(videoId);
		if(sportVideo.getClickRate()!=null){
			sportVideo.setClickRate(sportVideo.getClickRate()+1);	
		}else{
			sportVideo.setClickRate(1);
		}
		
		mapper.updateByPrimaryKeySelective(sportVideo);
	}

	public List<SportVideo> findVideoByTop(int i,Integer videoStatus) {
		SportVideoExample example = new SportVideoExample();
		Criteria criteria = example.createCriteria();
	
		criteria.andVideoStatusEqualTo(videoStatus);
		
		criteria.andStatEqualTo(DataStatus.ENABLED);
		example.setOrderByClause(" click_rate desc limit "+i);	
	
		return mapper.selectByExample(example);
	}
}
