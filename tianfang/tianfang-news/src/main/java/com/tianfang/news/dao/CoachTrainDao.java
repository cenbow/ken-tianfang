package com.tianfang.news.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.news.dto.CoachTrainDto;
import com.tianfang.news.mapper.SportCoachTrainMapper;
import com.tianfang.news.pojo.SportCoachTrain;
import com.tianfang.news.pojo.SportCoachTrainExample;

@Repository
public class CoachTrainDao extends MyBatisBaseDao<SportCoachTrain> {

	@Getter
	@Autowired
	private SportCoachTrainMapper mapper;

	/**
	 * @author YIn
	 * @time:2015年12月24日 上午11:17:19
	 */
	public List<SportCoachTrain> findCoachTrainByParams(
			CoachTrainDto coachTrainDto, PageQuery page) {
		SportCoachTrainExample example = new SportCoachTrainExample();
		SportCoachTrainExample.Criteria criteria = example.createCriteria();
        assemblyParams(coachTrainDto, criteria);
        /*if(null != page){
			switch (newsDto.getOrderType()) {
			//orderType=1表示根据marked desc, create_time desc排序
			case 1:
				example.setOrderByClause(" marked desc, create_time desc limit "+page.getStartNum() +"," + page.getPageSize());break;
			//orderType=1表示根据page_ranking desc排序
			case 2:
				example.setOrderByClause(" ISNULL(page_ranking) ASC, create_time DESC limit "+page.getStartNum() +"," + page.getPageSize());break;
			default:	
				example.setOrderByClause(" page_ranking asc limit "+page.getStartNum() +"," + page.getPageSize());
			}
		}*/
        example.setOrderByClause(" create_time DESC limit "+page.getStartNum() +"," + page.getPageSize());
        List<SportCoachTrain> results = mapper.selectByExample(example);  
        return results;
	}

	private void assemblyParams(CoachTrainDto coachTrainDto,SportCoachTrainExample.Criteria criteria) {
		if (StringUtils.isNotBlank(coachTrainDto.getId())){
    		criteria.andIdEqualTo(coachTrainDto.getId());
    	}
    	if (StringUtils.isNotBlank(coachTrainDto.getTitle())){
    		criteria.andTitleLike("%" +coachTrainDto.getTitle()+"%");
    	}

    	if(coachTrainDto.getReleaseStat()!=null){
    		criteria.andReleaseStatEqualTo(coachTrainDto.getReleaseStat());
    	}
    	
    	if (coachTrainDto.getMarked()!=null){
    		criteria.andMarkedEqualTo(coachTrainDto.getMarked());
    	}
    	if(coachTrainDto.getTrainType() != null){
    		criteria.andTrainTypeEqualTo(coachTrainDto.getTrainType());
    	}
    	criteria.andStatEqualTo(DataStatus.ENABLED);
    	
    	
    	if(StringUtils.isNotBlank(coachTrainDto.getStartTimeStr()) && StringUtils.isNotBlank(coachTrainDto.getEndTimeStr())){
    		criteria.andCreateTimeBetween(DateUtils.StringToDate(coachTrainDto.getStartTimeStr(), "yyyy-MM-dd"), DateUtils.StringToDate(coachTrainDto.getEndTimeStr(), "yyyy-MM-dd"));	
    	}
	}
	/**
	 * @author YIn
	 * @time:2015年12月24日 上午11:17:24
	 */
	public int countByParams(CoachTrainDto coachTrainDto) {
		SportCoachTrainExample example = new SportCoachTrainExample();
		SportCoachTrainExample.Criteria criteria = example.createCriteria();
        assemblyParams(coachTrainDto, criteria);
		return mapper.countByExample(example);
	}

	/**
	 * @author YIn
	 * @time:2015年12月24日 下午5:24:35
	 */
	public SportCoachTrain selectCoachTrainById(String id) {
		SportCoachTrainExample example = new SportCoachTrainExample();
		SportCoachTrainExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByPrimaryKey(id);
	}

	public List<SportCoachTrain> findCoachTrainByParams(CoachTrainDto coachTrainDto) {
		SportCoachTrainExample example = new SportCoachTrainExample();
		SportCoachTrainExample.Criteria criteria = example.createCriteria();
        assemblyParams(coachTrainDto, criteria);
        example.setOrderByClause(" ISNULL(page_ranking) ASC, page_ranking asc ,create_time DESC");
        List<SportCoachTrain> results = mapper.selectByExample(example);  
        return results;
	}
	
}