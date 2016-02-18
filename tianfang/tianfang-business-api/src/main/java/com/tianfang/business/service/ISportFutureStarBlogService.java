/**
 * 
 */
package com.tianfang.business.service;

import java.util.List;

import com.tianfang.business.dto.SportFutureStarBlogDto;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;

/**		
 * <p>Title: ISportFutureStarBlogService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年11月27日 下午4:51:10	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年11月27日 下午4:51:10</p>
 * <p>修改备注：</p>
 */
public interface ISportFutureStarBlogService
{
    public PageResult<SportFutureStarBlogDto> findPage(SportFutureStarBlogDto sportFutureStarBlogDto,PageQuery page);
    
    public Object save(SportFutureStarBlogDto sportFutureStarBlogDto);
    
    public SportFutureStarBlogDto findById(String id);
    
    public Object edit(SportFutureStarBlogDto sportFutureStarBlogDto);

    public Object delete(String ids);
    
    /**
     * 根据用户id查询博文(根据博文的阅读量降序,创建时间降序排序)
     * @param userId 用户id
     * @param size	  查询的个数(若该参数为空,或者小于等于则,默认查询10条数据)
     * @return
     * @author xiang_wang
     * 2015年11月30日下午3:31:24
     */
    List<SportFutureStarBlogDto> findBlogsByUserId(String userId, Integer size);
    
    /**
     * 教练博文阅读量+1
     * @param id
     * @author wangxiang
     * 2015年11月30日下午5:45:11
     */
    void updateRead(String id);
    
    /**
     * 教练博文分享+1
     * @param id
     * @author wangxiang
     * 2015年11月30日下午5:45:41
     */
    void updateShare(String id);
    
    /**
     * 教练博文分享+1
     * @param id
     * @author wangxiang
     * 2015年11月30日下午5:45:57
     */
    void updateLike(String id);
}