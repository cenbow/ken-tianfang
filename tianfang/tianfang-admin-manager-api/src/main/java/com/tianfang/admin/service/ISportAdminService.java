package com.tianfang.admin.service;

import org.springframework.stereotype.Service;

import com.tianfang.admin.dto.SportAdminDto;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;


@Service
public interface ISportAdminService {
    
    /**     
     * findByAccount：一句话描述方法功能
     * @param account
     * @param passWord
     * @param request
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年9月1日 下午7:22:30	 
     */
    public Object adminLogin(String account, String passWord);
    
    public Integer updateUser(SportAdminDto user);
    
    public PageResult<SportAdminDto> findPage(SportAdminDto trainingAdminDto,PageQuery page);
    
    public Object save(SportAdminDto trainingAdminDto);
    
    public Object save(SportAdminDto trainingAdminDto,String jsonClasss);
    
    public Object delAdIds(String  delAdIds);
    
    public SportAdminDto getAdmin(String id);
    
    public Object edit(SportAdminDto trainingAdminDto); 
    
    public Object edit(SportAdminDto trainingAdminDto,String jsonClasss);   
    
    public Object distributionAuth(String adminId,String menuIds);
    
}
