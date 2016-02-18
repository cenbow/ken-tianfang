package com.tianfang.train.dao;

import java.util.List;
import java.util.Map;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.TrainingNewsInfoDto;
import com.tianfang.train.mapper.TrainingNewsInfoExMapper;
import com.tianfang.train.mapper.TrainingNewsInfoMapper;
import com.tianfang.train.pojo.TrainingNewsInfo;
import com.tianfang.train.pojo.TrainingNewsInfoExample;

@Repository
public class TrainingNewsInfoDao extends MyBatisBaseDao<TrainingNewsInfo> {

	@Getter
	@Autowired
	private TrainingNewsInfoMapper mapper;
	
	@Getter
    @Autowired
    private TrainingNewsInfoExMapper exMapper;
	
	public Integer pageRankingMax() {
	    return exMapper.pageRankingMax();
	}
	
	public void deleteNews(String id){
		TrainingNewsInfo news = mapper.selectByPrimaryKey(id);
		if (null == news){
			throw new RuntimeException("对不起,新闻(资讯)对象不存在!");
		}
		news.setStat(0);
		mapper.updateByPrimaryKey(news);
	}
	
	public TrainingNewsInfo findByPostionId(Integer positionId) {
	    TrainingNewsInfoExample example = new TrainingNewsInfoExample();
        TrainingNewsInfoExample.Criteria criteria = example.createCriteria();
        if (null != positionId) {
            criteria.andPageRankingEqualTo(positionId);
        }       
        List<TrainingNewsInfo> results = mapper.selectByExample(example);  
        return CollectionUtils.isEmpty(results) ? null : results.get(0);
	}
	
	public List<TrainingNewsInfo> findNewsByParams(Map<String, Object> params){
		TrainingNewsInfoExample example = new TrainingNewsInfoExample();
		TrainingNewsInfoExample.Criteria criteria = example.createCriteria();
        assemblyParams(params, criteria);
        List<TrainingNewsInfo> results = mapper.selectByExample(example);        
		
		return results;
	}
	
	public List<TrainingNewsInfo> findNewsByParams(TrainingNewsInfoDto newsDto,PageQuery page) {
		TrainingNewsInfoExample example = new TrainingNewsInfoExample();
		TrainingNewsInfoExample.Criteria criteria = example.createCriteria();
        assemblyParams(newsDto, criteria);
        if(null != page){
			example.setOrderByClause(" page_ranking asc limit "+page.getStartNum() +"," + page.getPageSize());
		}
        List<TrainingNewsInfo> results = mapper.selectByExample(example);  
        return results;
	}
	
	public List<TrainingNewsInfo> findNewsByParams(
			Map<String, Object> params, PageQuery page) {
		TrainingNewsInfoExample example = new TrainingNewsInfoExample();
		TrainingNewsInfoExample.Criteria criteria = example.createCriteria();
        assemblyParams(params, criteria);
        if(null != page){
			example.setOrderByClause(" create_time desc limit "+page.getStartNum() +"," + page.getPageSize());
		}
        List<TrainingNewsInfo> results = mapper.selectByExample(example);        
		
		return results;
	}
	
	
	public int countNewsByParams(TrainingNewsInfoDto newsDto){
		TrainingNewsInfoExample example = new TrainingNewsInfoExample();
		TrainingNewsInfoExample.Criteria criteria = example.createCriteria();
        assemblyParams(newsDto, criteria);
		return mapper.countByExample(example);
	}
	
	private void assemblyParams(TrainingNewsInfoDto newsDto,TrainingNewsInfoExample.Criteria criteria) {
		if (StringUtils.isNotBlank(newsDto.getId())){
    		criteria.andIdEqualTo(newsDto.getId());
    	}
    	if (StringUtils.isNotBlank(newsDto.getTitle())){
    		criteria.andTitleLike("%" +newsDto.getTitle()+"%");
    	}

    	if (newsDto.getMarked()!=null){
    		criteria.andMarkedEqualTo(newsDto.getMarked());
    	}
    	
    	criteria.andStatEqualTo(DataStatus.ENABLED);
    	
    	
    	if(StringUtils.isNotBlank(newsDto.getStartTimeStr()) && StringUtils.isNotBlank(newsDto.getEndTimeStr())){
    		criteria.andCreateTimeBetween(DateUtils.StringToDate(newsDto.getStartTimeStr(), "yyyy-MM-dd"), DateUtils.StringToDate(newsDto.getEndTimeStr(), "yyyy-MM-dd"));	
    	}
    	
    	
//    	if (params.containsKey("createTimeS") && params.containsKey("createTimeE")){
//    		criteria.andCreateTimeBetween(DateUtils.StringToDate(params.get("createTimeS").toString(), "yyyy-MM-dd HH:mm:ss"), DateUtils.StringToDate(params.get("createTimeE").toString(), "yyyy-MM-dd HH:mm:ss"));
//    	}
//    	else if (params.containsKey("createTimeS")){
//    		criteria.andCreateTimeGreaterThanOrEqualTo( DateUtils.StringToDate(params.get("createTimeS").toString(), "yyyy-MM-dd HH:mm:ss"));
//    	}
//    	else if (params.containsKey("createTimeE")){
//    	   
//    		criteria.andCreateTimeLessThanOrEqualTo(DateUtils.StringToDate(params.get("createTimeE").toString(), "yyyy-MM-dd HH:mm:ss"));
//    	}
	}
	
	public int countNewsByParams(Map<String, Object> params){
		TrainingNewsInfoExample example = new TrainingNewsInfoExample();
		TrainingNewsInfoExample.Criteria criteria = example.createCriteria();
        assemblyParams(params, criteria);
		return mapper.countByExample(example);
	}
	
	public List<TrainingNewsInfo> findIndexNews(Integer size){
		TrainingNewsInfoExample example = new TrainingNewsInfoExample();
		TrainingNewsInfoExample.Criteria criteria = example.createCriteria();
		criteria.andMarkedEqualTo(1);
		criteria.andStatEqualTo(1);
        if(null != size && size.intValue() > 0){
			example.setOrderByClause(" create_time desc limit 0," + size);
		}else{
			example.setOrderByClause(" create_time desc limit 0,3");
		}
        List<TrainingNewsInfo> results = mapper.selectByExample(example);
		return results;
	}

	/**
	 * 组装查询参数
	 * 
	 * @param params
	 * @param criteria
	 */
	private void assemblyParams(Map<String, Object> params,
			TrainingNewsInfoExample.Criteria criteria) {
		if (null != params) {
        	if (params.containsKey("id")){
        		criteria.andIdEqualTo((String)params.get("id"));
        	}
        	if (params.containsKey("title")){
        		criteria.andTitleLike("%" +(String)params.get("title")+"%");
        	}
        	if (params.containsKey("subtract")){
        		criteria.andSubtractLike("%"+(String)params.get("subtract")+"%");
        	}
        	if (params.containsKey("marked")){
        		criteria.andMarkedEqualTo(Integer.parseInt(""+params.get("marked")));
        	}
        	if (params.containsKey("stat")){
        		criteria.andStatEqualTo(Integer.parseInt(""+params.get("stat")));
        	}
        	if (params.containsKey("createTimeS") && params.containsKey("createTimeE")){
        		criteria.andCreateTimeBetween(DateUtils.StringToDate(params.get("createTimeS").toString(), "yyyy-MM-dd HH:mm:ss"), DateUtils.StringToDate(params.get("createTimeE").toString(), "yyyy-MM-dd HH:mm:ss"));
        	}
        	else if (params.containsKey("createTimeS")){
        		criteria.andCreateTimeGreaterThanOrEqualTo( DateUtils.StringToDate(params.get("createTimeS").toString(), "yyyy-MM-dd HH:mm:ss"));
        	}
        	else if (params.containsKey("createTimeE")){
        	   
        		criteria.andCreateTimeLessThanOrEqualTo(DateUtils.StringToDate(params.get("createTimeE").toString(), "yyyy-MM-dd HH:mm:ss"));
        	}
        }
	}
	/**
	 * 统计选中状态的新条数
	 * @param map
	 */
	public  List<TrainingNewsInfo> countByMarked(Map<String, Object> map) {
		TrainingNewsInfoExample example = new TrainingNewsInfoExample();
		TrainingNewsInfoExample.Criteria criteria = example.createCriteria();
        assemblyParams(map, criteria);
        return mapper.selectByExample(example);
	}
}