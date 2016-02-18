/**
 * 
 */
package com.tianfang.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.business.dao.SportFutureStarBlogDao;
import com.tianfang.business.dto.SportFutureStarBlogDto;
import com.tianfang.business.pojo.SportFutureStarBlog;
import com.tianfang.business.service.ISportFutureStarBlogService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;

/**		
 * <p>Title: SportFutureStarBlogServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年11月27日 下午4:51:46	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年11月27日 下午4:51:46</p>
 * <p>修改备注：</p>
 */
@Service
public class SportFutureStarBlogServiceImpl implements ISportFutureStarBlogService
{
    @Autowired
    private SportFutureStarBlogDao sportFutureStarBlogDao;    
    
    
    public PageResult<SportFutureStarBlogDto> findPage(SportFutureStarBlogDto sportFutureStarBlogDto,PageQuery page) {
        List<SportFutureStarBlogDto> results = sportFutureStarBlogDao.findPage(sportFutureStarBlogDto, page);
        page.setTotal(sportFutureStarBlogDao.count(sportFutureStarBlogDto));
        return new PageResult<SportFutureStarBlogDto>(page, results);
    }
    
    
    public Object save(SportFutureStarBlogDto sportFutureStarBlogDto) {
        SportFutureStarBlog sportFutureStarBlog = BeanUtils.createBeanByTarget(sportFutureStarBlogDto, SportFutureStarBlog.class);
        sportFutureStarBlog.setReleaseStat(DataStatus.DISABLED);
        sportFutureStarBlog.setLikeAmount(DataStatus.DISABLED);
        sportFutureStarBlog.setShareAmount(DataStatus.DISABLED);
        sportFutureStarBlog.setShareAmount(DataStatus.DISABLED);
        return sportFutureStarBlogDao.insert(sportFutureStarBlog);
    }
    
    public SportFutureStarBlogDto findById(String id) {
    	checkIdException(id);
        SportFutureStarBlog sportFutureStarBlog = sportFutureStarBlogDao.selectByPrimaryKey(id);
        SportFutureStarBlogDto sportFutureStarBlogDto = BeanUtils.createBeanByTarget(sportFutureStarBlog, SportFutureStarBlogDto.class);
        return sportFutureStarBlogDto;
    }

    public Object edit(SportFutureStarBlogDto sportFutureStarBlogDto) {
        SportFutureStarBlog sportFutureStarBlog = BeanUtils.createBeanByTarget(sportFutureStarBlogDto, SportFutureStarBlog.class);
        return sportFutureStarBlogDao.updateByPrimaryKeySelective(sportFutureStarBlog);        
    }
    
    public Object delete(String ids) {
        String[] idStr = ids.split(",");
        if (idStr.length > 0) {
            for (String id:idStr) {
                SportFutureStarBlog sportFutureStarBlog = sportFutureStarBlogDao.selectByPrimaryKey(id);
                sportFutureStarBlog.setStat(DataStatus.DISABLED);
                sportFutureStarBlogDao.updateByPrimaryKeySelective(sportFutureStarBlog);
            }
        } else {
            SportFutureStarBlog sportFutureStarBlog = sportFutureStarBlogDao.selectByPrimaryKey(ids);
            sportFutureStarBlog.setStat(DataStatus.DISABLED);
            sportFutureStarBlogDao.updateByPrimaryKeySelective(sportFutureStarBlog);
        }
        return null;
    }

	@Override
	public List<SportFutureStarBlogDto> findBlogsByUserId(String userId,
			Integer size) {
		if (StringUtils.isBlank(userId)){
			throw new RuntimeException("对不起,用户id为空!");
		}
		if (null == size || size.intValue() <= 0){
			size = 10;
		}
		return sportFutureStarBlogDao.findBlogsByUserId(userId, size);
	}


	@Override
	public synchronized void updateRead(String id) {
		checkIdException(id);
		sportFutureStarBlogDao.updateRead(id);
	}


	@Override
	public synchronized void updateShare(String id) {
		checkIdException(id);
		sportFutureStarBlogDao.updateShare(id);
	}


	@Override
	public synchronized void updateLike(String id) {
		checkIdException(id);
		sportFutureStarBlogDao.updateLike(id);
	}
	
	private void checkIdException(String id) {
		if (StringUtils.isBlank(id)){
    		throw new RuntimeException("对不起,教练博文主键id为空!");
    	}
	}
}