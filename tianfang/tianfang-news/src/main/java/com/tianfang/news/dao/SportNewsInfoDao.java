package com.tianfang.news.dao;

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
import com.tianfang.news.dto.SportNewsInfoDto;
import com.tianfang.news.mapper.SportNewsInfoExMapper;
import com.tianfang.news.mapper.SportNewsInfoMapper;
import com.tianfang.news.pojo.SportNewsInfo;
import com.tianfang.news.pojo.SportNewsInfoExample;

@Repository
public class SportNewsInfoDao extends MyBatisBaseDao<SportNewsInfo> {

	@Getter
	@Autowired
	private SportNewsInfoMapper mapper;
	
	@Getter
    @Autowired
    private SportNewsInfoExMapper exMapper;
	
	public Integer pageRankingMax() {
	    return exMapper.pageRankingMax();
	}
	
	public void deleteNews(String id){
		SportNewsInfo news = mapper.selectByPrimaryKey(id);
		if (null == news){
			throw new RuntimeException("对不起,新闻(资讯)对象不存在!");
		}
		news.setStat(0);
		mapper.updateByPrimaryKey(news);
	}
	
	public SportNewsInfo findByPostionId(Integer positionId) {
	    SportNewsInfoExample example = new SportNewsInfoExample();
        SportNewsInfoExample.Criteria criteria = example.createCriteria();
        if (null != positionId) {
            criteria.andPageRankingEqualTo(positionId);
        }       
        List<SportNewsInfo> results = mapper.selectByExample(example);  
        return CollectionUtils.isEmpty(results) ? null : results.get(0);
	}
	
	public List<SportNewsInfo> findNewsByParams(Map<String, Object> params){
		SportNewsInfoExample example = new SportNewsInfoExample();
		SportNewsInfoExample.Criteria criteria = example.createCriteria();
        assemblyParams(params, criteria);
        
        example.setOrderByClause(" ISNULL(page_ranking) ASC, page_ranking ASC, create_time DESC");
        
        List<SportNewsInfo> results = mapper.selectByExample(example);        
		
		return results;
	}
	
	public List<SportNewsInfo> findNewsByParams(SportNewsInfoDto newsDto,PageQuery page) {
		SportNewsInfoExample example = new SportNewsInfoExample();
		SportNewsInfoExample.Criteria criteria = example.createCriteria();
        assemblyParams(newsDto, criteria);
        if(null != page){
        	
			switch (newsDto.getOrderType()) {
			//orderType=1表示根据marked desc, create_time desc排序
			case 1:
				example.setOrderByClause(" marked desc, create_time desc limit "+page.getStartNum() +"," + page.getPageSize());break;
			//orderType=1表示根据page_ranking desc排序
			case 2:
				example.setOrderByClause(" ISNULL(page_ranking) ASC, page_ranking ASC, create_time DESC limit "+page.getStartNum() +"," + page.getPageSize());break;
			default:	
				example.setOrderByClause(" ISNULL(page_ranking) ASC, page_ranking ASC, create_time DESC limit "+page.getStartNum() +"," + page.getPageSize());
			}
		}
        List<SportNewsInfo> results = mapper.selectByExample(example);  
        return results;
	}
	
	public List<SportNewsInfo> findNewsByParams(
			Map<String, Object> params, PageQuery page) {
		SportNewsInfoExample example = new SportNewsInfoExample();
		SportNewsInfoExample.Criteria criteria = example.createCriteria();
        assemblyParams(params, criteria);
        if(null != page){
        	if(!params.get("orderType").equals("") && params.get("orderType") != null){
        		
        		switch(Integer.valueOf(String.valueOf(params.get("orderType")))){
        		//orderType=1 表示marked desc,create_time desc
        		case 1:
        			example.setOrderByClause(" marked desc,create_time desc limit "+page.getStartNum() +"," + page.getPageSize());break;
        			//orderType=2 表示page_ranking desc
        		case 2:
        			example.setOrderByClause(" ISNULL(page_ranking) ASC, page_ranking ASC, create_time DESC limit "+page.getStartNum() +"," + page.getPageSize());break;
        		default:
        			example.setOrderByClause(" ISNULL(page_ranking) ASC, page_ranking ASC, create_time DESC limit "+page.getStartNum() +"," + page.getPageSize());
        		}
        	}
		}
        List<SportNewsInfo> results = mapper.selectByExample(example);        
		
		return results;
	}
	
	
	public int countNewsByParams(SportNewsInfoDto newsDto){
		SportNewsInfoExample example = new SportNewsInfoExample();
		SportNewsInfoExample.Criteria criteria = example.createCriteria();
        assemblyParams(newsDto, criteria);
		return mapper.countByExample(example);
	}
	
	private void assemblyParams(SportNewsInfoDto newsDto,SportNewsInfoExample.Criteria criteria) {
		if (StringUtils.isNotBlank(newsDto.getId())){
    		criteria.andIdEqualTo(newsDto.getId());
    	}
    	if (StringUtils.isNotBlank(newsDto.getTitle())){
    		criteria.andTitleLike("%" +newsDto.getTitle()+"%");
    	}

    	if(newsDto.getReleaseStat()!=null){
    		criteria.andReleaseStatEqualTo(newsDto.getReleaseStat());
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
		SportNewsInfoExample example = new SportNewsInfoExample();
		SportNewsInfoExample.Criteria criteria = example.createCriteria();
        assemblyParams(params, criteria);
		return mapper.countByExample(example);
	}
	
	public List<SportNewsInfo> findIndexNews(Integer size){
		SportNewsInfoExample example = new SportNewsInfoExample();
		SportNewsInfoExample.Criteria criteria = example.createCriteria();
		criteria.andMarkedEqualTo(1);
		criteria.andStatEqualTo(1);
        if(null != size && size.intValue() > 0){
			example.setOrderByClause(" create_time desc limit 0," + size);
		}else{
			example.setOrderByClause(" create_time desc limit 0,3");
		}
        List<SportNewsInfo> results = mapper.selectByExample(example);
		return results;
	}

	/**
	 * 组装查询参数
	 * 
	 * @param params
	 * @param criteria
	 */
	private void assemblyParams(Map<String, Object> params,
			SportNewsInfoExample.Criteria criteria) {
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
        	if (params.containsKey("releaseStat")){
        		criteria.andReleaseStatEqualTo(Integer.parseInt(""+params.get("releaseStat")));
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
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}
	/**
	 * 统计选中状态的新条数
	 * @param map
	 */
	public  List<SportNewsInfo> countByMarked(Map<String, Object> map) {
		SportNewsInfoExample example = new SportNewsInfoExample();
		SportNewsInfoExample.Criteria criteria = example.createCriteria();
        assemblyParams(map, criteria);
        return mapper.selectByExample(example);
	}
}