package com.tianfang.train.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.TrainInfoDto;
import com.tianfang.train.mapper.TrainingMapper;
import com.tianfang.train.pojo.Training;
import com.tianfang.train.pojo.TrainingExample;
import com.tianfang.train.pojo.TrainingExample.Criteria;


@Repository
public class TrainInfoDao extends MyBatisBaseDao<Training> {
	@Autowired
	@Getter
	private TrainingMapper mapper;
	
	public List<Training> findByPage(TrainInfoDto trainInfoDto, PageQuery page) {
		TrainingExample example = new TrainingExample();
		Criteria criteria = example.createCriteria();
		setQueryParam(trainInfoDto, criteria);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		example.setOrderByClause(" create_time desc limit " + page.getStartNum() + ", " + page.getPageSize());
		return mapper.selectByExample(example);
	}

	public long count(TrainInfoDto trainInfoDto) {
		TrainingExample example = new TrainingExample();
		Criteria criteria = example.createCriteria();
		
		setQueryParam(trainInfoDto, criteria);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.countByExample(example);
	}
	
	public int logicDeleteById(String id) {
		Training training = new Training();
		training.setId(id);
		training.setStat(DataStatus.DISABLED);
		return mapper.updateByPrimaryKeySelective(training);
	}
	
	private void setQueryParam(TrainInfoDto trainInfoDto, Criteria criteria) {
		if(StringUtils.isNotEmpty(trainInfoDto.getTrainTheme())) {
			criteria.andTrainThemeLike("%" + trainInfoDto.getTrainTheme() + "%");
		}
		
		if(StringUtils.isNotEmpty(trainInfoDto.getTrainAuthor())) {
			criteria.andTrainAuthorLike("%" + trainInfoDto.getTrainAuthor() + "%");
		}
	}
	/*public List<RaceInfoDto> findAll(RaceInfoDto param){
		RaceInfoExample example = new RaceInfoExample();
		RaceInfoExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(1);
		if(param!=null){
		if(!StringUtils.isEmpty(param.getId())){
			criteria.andIdEqualTo(param.getId());
		}
	}	
		List<RaceInfo> list = mapper.selectByExample(example);
		if(list!=null&&list.size()>0){
			List<RaceInfoDto> result = new ArrayList<RaceInfoDto>();
			for(RaceInfo raceInfo:list){
				RaceInfoDto raceInfoDto = new RaceInfoDto();
				BeanUtils.copyProperties(raceInfo, raceInfoDto);
				SportType sportType = sportTypeMapper.selectByPrimaryKey(raceInfoDto.getSportTypeId()+"");
				if(sportType!=null){
					raceInfoDto.setSportName(sportType.getSportName());
				}
				result.add(raceInfoDto);
			}
			return result;
		}
		return null;
	}
	
	public List<RaceTeamDto> findTeamsByRace(RaceTeamDto param){
		RaceTeamExample example = new RaceTeamExample();
		RaceTeamExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(1);
		criteria.andRaceIdEqualTo(param.getRaceId());
		List<RaceTeam> list = mapperTeam.selectByExample(example);
		return BeanUtils.createBeanListByTarget(list, RaceTeamDto.class);
	}
	*//**
	 * 
		 * 此方法描述的是：赛事分页信息
		 * @author: cwftalus@163.com
		 * @version: 2015年4月17日 上午9:14:37
	 *//*
	public List<RaceInfo> findBy(Integer infoType,PageQuery page) {
		RaceInfoExample example = new RaceInfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		
		if(infoType!=null){
			criteria.andInfoTypeEqualTo(infoType);	
		}
		
		example.setOrderByClause(" publish_time desc limit " + page.getStartNum() + ", " + page.getPageSize());
		
		return mapper.selectByExample(example);
	}
	
	*//**
	 * 
	 * findByInfoType：热门赛事
	 * @param infotype
	 * @return
	 * @exception	
	 * @author Administrator
	 * @date 2015年7月24日 下午5:46:52
	 *//*
	public List<RaceInfo> findByInfoType(Integer infotype){
	    RaceInfoExample example = new RaceInfoExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        criteria.andInfoTypeEqualTo(infotype);
        example.setOrderByClause(" publish_time desc");
        return mapper.selectByExample(example);
	}
	
	*//**
	 * 
		 * 此方法描述的是：此方法描述的是：赛事分页信息COUNT
		 * @author: cwftalus@163.com
		 * @version: 2015年5月5日 下午1:46:34
	 *//*
	public long count(Integer infoType) {
		RaceInfoExample example = new RaceInfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		
		if(infoType!=null){
			criteria.andInfoTypeEqualTo(infoType);	
		}

		return mapper.countByExample(example);
	}
*/
} 
