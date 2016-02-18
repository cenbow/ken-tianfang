/**
 * 
 */
package com.tianfang.business.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tianfang.business.dto.SportInformationReqDto;
import com.tianfang.business.dto.SportInformationRespDto;
import com.tianfang.business.mapper.SportInformationExMapper;
import com.tianfang.business.mapper.SportInformationMapper;
import com.tianfang.business.pojo.SportInformation;
import com.tianfang.business.pojo.SportInformationExample;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.news.pojo.SportNewsInfo;
import com.tianfang.news.pojo.SportNewsInfoExample;

/**		
 * <p>Title: SportInfoDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年11月16日 下午5:05:44	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年11月16日 下午5:05:44</p>
 * <p>修改备注：</p>
 */
@Repository
public class SportInfoDao extends MyBatisBaseDao<SportInformation>
{
    @Autowired
    @Getter
    private SportInformationMapper mapper;

    @Autowired
    @Getter
    private SportInformationExMapper exMapper;
    
    public Integer pageRankingMax() {
	    return exMapper.pageRankingMax();
	}
    
    public List<SportInformationRespDto> findPage(SportInformationReqDto sportInformationReqDto, PageQuery page) {
        SportInformationExample example = new SportInformationExample();
        SportInformationExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(sportInformationReqDto.getLabel())) {
            criteria.andLabelLike("%"+sportInformationReqDto.getLabel()+"%");
        }
        if (StringUtils.isNotBlank(sportInformationReqDto.getTitle())) {
            criteria.andTitleLike("%"+sportInformationReqDto.getTitle()+"%");
        }
        if (null != page) {
        	
			int i = sportInformationReqDto.getOrderType();
        	switch (i) {
			// orderType=1 表示根据marked desc和 create_time desc排序
			case 1:
				example.setOrderByClause(" marked desc, create_time desc limit " + page.getStartNum() + "," + page.getPageSize());break;
			//orderType=2 表示根据page_ranking desc 排序
			case 2:
				example.setOrderByClause(" ISNULL(page_ranking) ASC, page_ranking ASC, create_time DESC limit " + page.getStartNum() + "," + page.getPageSize());break;
			// 默认create_time desc排序
			default:	
				example.setOrderByClause(" ISNULL(page_ranking) ASC, page_ranking ASC, create_time DESC limit "+page.getStartNum() +"," + page.getPageSize());
			}
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (null != sportInformationReqDto.getType() && sportInformationReqDto.getType() == 10) {
        	criteria.andReleaseStatEqualTo(DataStatus.ENABLED);
        }
        List<SportInformationRespDto> result = BeanUtils.createBeanListByTarget(mapper.selectByExample(example), SportInformationRespDto.class);
        return result;
    }

    public long count(SportInformationReqDto sportInformationReqDto) {
        SportInformationExample example = new SportInformationExample();
        SportInformationExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(sportInformationReqDto.getLabel())) {
            criteria.andLabelLike("%"+sportInformationReqDto.getLabel()+"%");
        }
        if (StringUtils.isNotBlank(sportInformationReqDto.getTitle())) {
            criteria.andTitleLike("%"+sportInformationReqDto.getTitle()+"%");
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.countByExample(example);
    }
    
    public List<SportInformation> findCountByMarked(String id, Integer marked) {
        SportInformationExample example = new SportInformationExample();
        SportInformationExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(id)) {
            criteria.andIdEqualTo(id);
        }
        if (null != marked) {
            criteria.andMarkedEqualTo(marked);
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }
    
    public Object save(SportInformation sportInformation) {
        return mapper.insert(sportInformation);
    }
    
    public SportInformation selectById(String id){
        return mapper.selectByPrimaryKey(id);
    }

    public SportInformation findByPostionId(Integer positionId) {
    	SportInformationExample example = new SportInformationExample();
        SportInformationExample.Criteria criteria = example.createCriteria();
        if (null != positionId) {
            criteria.andPageRankingEqualTo(positionId);
        }      
        List<SportInformation> results = mapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results.get(0);
    }
    
	public List<SportInformation> findInfoByParams(HashMap<String, Object> infoParams) {
        SportInformationExample example = new SportInformationExample();
        SportInformationExample.Criteria criteria = example.createCriteria();
        assemblyParams(infoParams, criteria);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        List<SportInformation> results = mapper.selectByExample(example);        
		return results;
	}
	
	private void assemblyParams(Map<String, Object> params,SportInformationExample.Criteria criteria) {
		if (null != params) {
        	if (params.containsKey("marked")){
        		criteria.andMarkedEqualTo(Integer.parseInt(""+params.get("marked")));
        	}
        	if (params.containsKey("releaseStat")){
        		criteria.andReleaseStatEqualTo(Integer.parseInt(""+params.get("releaseStat")));
        	}
        }
	}
}

