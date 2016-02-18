/**
 * 
 */
package com.tianfang.business.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.Getter;

import com.tianfang.business.dto.SportFutureStarBlogDto;
import com.tianfang.business.mapper.SportFutureStarBlogExMapper;
import com.tianfang.business.mapper.SportFutureStarBlogMapper;
import com.tianfang.business.pojo.SportFutureStarBlog;
import com.tianfang.business.pojo.SportFutureStarBlogExample;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;

/**		
 * <p>Title: SportFutureStarBlogDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年11月27日 下午4:38:07	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年11月27日 下午4:38:07</p>
 * <p>修改备注：</p>
 */
@Repository
public class SportFutureStarBlogDao extends MyBatisBaseDao<SportFutureStarBlog>
{

    @Getter
    @Autowired
    private SportFutureStarBlogMapper mapper;
    
    @Autowired
    private SportFutureStarBlogExMapper exMapper;
    

    public List<SportFutureStarBlogDto> findPage(SportFutureStarBlogDto sportFutureStarBlogDto,PageQuery page) {
        SportFutureStarBlogExample example = new SportFutureStarBlogExample();
        SportFutureStarBlogExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(sportFutureStarBlogDto.getUserId())) {
            criteria.andUserIdEqualTo(sportFutureStarBlogDto.getUserId());
        }
        if (StringUtils.isNotBlank(sportFutureStarBlogDto.getTitle())) {
            criteria.andTitleLike("%"+sportFutureStarBlogDto.getTitle()+"%");
        }
        if(page!=null){
            example.setOrderByClause("page_ranking asc, create_time desc limit "+page.getStartNum()+","+page.getPageSize());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        List<SportFutureStarBlog> result = mapper.selectByExample(example);
        return BeanUtils.createBeanListByTarget(result, SportFutureStarBlogDto.class);
    }
    
    public long count(SportFutureStarBlogDto sportFutureStarBlogDto) {
        SportFutureStarBlogExample example = new SportFutureStarBlogExample();
        SportFutureStarBlogExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(sportFutureStarBlogDto.getUserId())) {
            criteria.andUserIdEqualTo(sportFutureStarBlogDto.getUserId());
        }
        if (StringUtils.isNotBlank(sportFutureStarBlogDto.getTitle())) {
            criteria.andTitleLike("%"+sportFutureStarBlogDto.getTitle()+"%");
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.countByExample(example);
     }
    
    public List<SportFutureStarBlogDto> findBlogsByUserId(String userId, Integer size){
    	 SportFutureStarBlogExample example = new SportFutureStarBlogExample();
         SportFutureStarBlogExample.Criteria criteria = example.createCriteria();
         criteria.andUserIdEqualTo(userId);
         example.setOrderByClause("read_amount desc, create_time desc limit 0,"+size);
         criteria.andStatEqualTo(DataStatus.ENABLED);
         List<SportFutureStarBlog> result = mapper.selectByExample(example);
         return BeanUtils.createBeanListByTarget(result, SportFutureStarBlogDto.class);
    }

	public void updateRead(String id) {
		exMapper.updateRead(id);
	}

	public void updateShare(String id) {
		exMapper.updateShare(id);
	}

	public void updateLike(String id) {
		exMapper.updateLike(id);
	}
}