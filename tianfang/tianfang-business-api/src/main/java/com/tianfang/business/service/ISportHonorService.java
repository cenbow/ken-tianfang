/**
 * 
 */
package com.tianfang.business.service;

import org.springframework.stereotype.Service;

import com.tianfang.business.dto.SportHonorReqDto;
import com.tianfang.business.dto.SportHonorRespDto;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;

/**		
 * <p>Title: ISportHonorService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年11月13日 下午5:23:51	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年11月13日 下午5:23:51</p>
 * <p>修改备注：</p>
 */
@Service
public interface ISportHonorService
{
    /**
     * 
     * findPage：分页查询战队荣誉
     * @param sportHonorReqDto
     * @param page
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月13日 下午5:33:10
     */
    public PageResult<SportHonorRespDto> findPage(SportHonorReqDto sportHonorReqDto,PageQuery page);
    
    /**
     * 
     * save：新增球队荣誉
     * @param sportHonorReqDto
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月16日 上午9:51:42
     */
    public Integer save(SportHonorReqDto sportHonorReqDto);
    
    /**
     * 
     * findById：根据id查询
     * @param id
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月16日 上午11:42:25
     */
    public SportHonorRespDto findById(String id);
    
    /**
     * 
     * update：更新
     * @param sportHonorReqDto
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月16日 下午1:27:20
     */
    public Integer update(SportHonorReqDto sportHonorReqDto); 
    
    /**
     * 
     * delete：删除荣誉记录
     * @param ids
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月16日 下午2:33:04
     */
    public Object delete(String ids);
    
    /**
     * 
     * examine：改变审核状态
     * @param id
     * @param honorStatus
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月16日 下午4:02:33
     */
    public Object examine(String id,Integer honorStatus) ;
}

