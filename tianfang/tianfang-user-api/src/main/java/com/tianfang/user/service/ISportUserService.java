/**
 * 
 */
package com.tianfang.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.user.dto.SportUserReqDto;
import com.tianfang.user.dto.SportUserRespDto;

/**		
 * <p>Title: ISportUserService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年11月18日 下午5:18:55	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年11月18日 下午5:18:55</p>
 * <p>修改备注：</p>
 */
@Service
public interface ISportUserService
{
    /**
     * 
     * findPage：用户列表
     * @param sportUserReqDto
     * @param page
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月18日 下午5:28:21
     */
    public PageResult<SportUserRespDto> findPage(SportUserReqDto sportUserReqDto, PageQuery page);
    
    public Object visible(String id, Integer visibleStat); 
    
    public Object audit(String id, Integer auditStat);
    
    public Object delete(String ids);
    
    public Integer mpUpdateUser(String mobile,String password);
    
    public Integer emailUpdateUser(String email,String password);
    
    public SportUserRespDto getSportUser(String id) ;

    public Object save(SportUserReqDto sportUserReqDto) ;
    
    public Object edit(SportUserReqDto sportUserReqDto);
    
    public List<SportUserRespDto> findByUType(Integer utype);
    
    public Integer findByEmailOrSMS(String email,String mobile) ;
    
    /**
     * 修改主讲人状态
     * @author YIn
     * @time:2015年12月25日 下午3:34:04
     * @param id
     * @param lecturerStat
     */
	public int changeLecturer(String id, Integer lecturerStat);

	public int setUserOp(String id, Integer trainerLevel);
}

