/**
 * 
 */
package com.tianfang.business.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tianfang.business.dto.SportInformationReqDto;
import com.tianfang.business.dto.SportInformationRespDto;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;

/**		
 * <p>Title: ISportInfoService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年11月16日 下午5:02:11	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年11月16日 下午5:02:11</p>
 * <p>修改备注：</p>
 */
@Service
public interface ISportInfoService
{
    /**
     *     
     * findPage：咨询列表
     * @param sportInformationReqDto
     * @param page
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月17日 上午11:54:05
     */
    public PageResult<SportInformationRespDto> findPage(SportInformationReqDto sportInformationReqDto,PageQuery page);
    
    /**
     * 
     * save：新增咨询
     * @param sportInformationReqDto
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月17日 上午11:54:18
     */
    public SportInformationRespDto save(SportInformationReqDto sportInformationReqDto);
    
    /**
     * 
     * findById：根据id查找咨询
     * @param id
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月17日 下午3:11:16
     */
    public SportInformationRespDto findById(String id);
    
    /**
     * 
     * edit：修改咨询
     * @param sportInformationReqDto
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月17日 下午3:20:45
     */
    public SportInformationRespDto edit(SportInformationReqDto sportInformationReqDto);
    
    /**
     * 
     * delete：删除咨询
     * @param ids
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月17日 下午4:21:35
     */
    public Object delete(String ids);
    
    /**
     * 
     * release：修改发布状态
     * @param id
     * @param releaseStat
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月18日 下午3:20:08
     */
    public Object release(String id,Integer releaseStat);

	public List<SportInformationRespDto> findInfoByParams(HashMap<String, Object> infoParams);
	
	public int pageRanking(String id, Integer positionId);
}

