package com.tianfang.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tianfang.admin.dto.MenuRespDto;
import com.tianfang.admin.dto.SportMenuAuthRespDto;
import com.tianfang.admin.dto.SportMenuRespDto;

@Service
public interface ISportMenuService {



    
    /**     
     * findMenuByAdminId：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年9月22日 上午11:20:23	 
     */
    List<MenuRespDto> findMenuByAdminId(String id);
    
    public List<SportMenuRespDto> findAll();
    
    public List<SportMenuRespDto> findAdminMenuByAdminId(String id);
    
    public List<SportMenuAuthRespDto> getAdminAuthById(String adminId);

}
