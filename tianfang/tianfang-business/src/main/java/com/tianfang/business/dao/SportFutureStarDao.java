package com.tianfang.business.dao;

import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tianfang.business.dto.SportFutureStarDto;
import com.tianfang.business.mapper.SportFutureStarExMapper;
import com.tianfang.business.mapper.SportFutureStarMapper;
import com.tianfang.business.pojo.SportFutureStar;
import com.tianfang.business.pojo.SportFutureStarExample;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;

@Repository
public class SportFutureStarDao extends MyBatisBaseDao<SportFutureStar> {
	
	@Getter
	@Autowired
	private SportFutureStarMapper mapper;
	
    @Autowired
    private SportFutureStarExMapper exMapper;
    
    public Integer pageRankingMax() {
	    return exMapper.pageRankingMax();
	}
    
    public SportFutureStar findByPostionId(Integer positionId) {
    	SportFutureStarExample example = new SportFutureStarExample();
    	SportFutureStarExample.Criteria criteria = example.createCriteria();
        if (null != positionId) {
            criteria.andPageRankingEqualTo(positionId);
        }       
        List<SportFutureStar> results = mapper.selectByExample(example);  
        return CollectionUtils.isEmpty(results) ? null : results.get(0);
	}
	
	public void deleteFutureStar(String id) {
		SportFutureStar FutureStar = mapper.selectByPrimaryKey(id);
		if (null == FutureStar) {
			throw new RuntimeException("对不起,未来之星对象不存在!");
		}
		FutureStar.setStat(DataStatus.DISABLED);
		mapper.updateByPrimaryKey(FutureStar);
	}

	public List<SportFutureStar> findFutureStarByParams(SportFutureStarDto dto) {
		SportFutureStarExample example = new SportFutureStarExample();
		SportFutureStarExample.Criteria criteria = example.createCriteria();
		assemblyParams(dto, criteria);
		
		List<SportFutureStar> results = mapper.selectByExample(example);
		return results;
	}

	public List<SportFutureStar> findFutureStarByParams(SportFutureStarDto dto, PageQuery page) {
		SportFutureStarExample example = new SportFutureStarExample();
		SportFutureStarExample.Criteria criteria = example.createCriteria();
		assemblyParams(dto, criteria);
		if(page!=null){
			example.setOrderByClause("page_ranking asc, create_time desc limit "+page.getStartNum()+","+page.getPageSize());
		}
		List<SportFutureStar> results = mapper.selectByExample(example);
		return results;
	}

	public int countFutureStarByParams(SportFutureStarDto dto) {
		SportFutureStarExample example = new SportFutureStarExample();
		SportFutureStarExample.Criteria criteria = example.createCriteria();
		assemblyParams(dto, criteria);
		return mapper.countByExample(example);
	}
	
	private void assemblyParams(SportFutureStarDto dto,
			SportFutureStarExample.Criteria criteria) {
		if (null != dto){
			if (StringUtils.isNotBlank(dto.getId())) {
				criteria.andIdEqualTo(dto.getId());
			}
			if (StringUtils.isNotBlank(dto.getTitle())) {
				criteria.andTitleLike("%" + dto.getTitle() + "%");
			}
			if (StringUtils.isNotBlank(dto.getSummary())) {
				criteria.andSummaryLike("%" + dto.getSummary() + "%");
			}
			if (StringUtils.isNotBlank(dto.getButton())) {
				criteria.andButtonLike("%" + dto.getButton() + "%");
			}
			if (null != dto.getType()){
				criteria.andTypeEqualTo(dto.getType());
			}

			if (dto.getStat() != null) {
				if (dto.getStat().intValue() == 0 || dto.getStat().intValue() == 1) {
					criteria.andStatEqualTo(dto.getStat());
				}
			} else {
				criteria.andStatEqualTo(DataStatus.ENABLED);
			}

			if (StringUtils.isNotBlank(dto.getCreateTimeS())
					&& StringUtils.isNotBlank(dto.getCreateTimeE())) {
				criteria.andCreateTimeBetween(
						DateUtils.StringToDate(dto.getCreateTimeS(), "yyyy-MM-dd"),
						new Date(DateUtils.StringToDate(dto.getCreateTimeE(), "yyyy-MM-dd").getTime()+24*60*60*1000L-1)
						);
			}
		}else{
			criteria.andStatEqualTo(DataStatus.ENABLED);
		}
		
	}
}