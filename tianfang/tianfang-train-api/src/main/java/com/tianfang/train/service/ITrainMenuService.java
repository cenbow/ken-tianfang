package com.tianfang.train.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tianfang.train.dto.MenuRespDto;
import com.tianfang.train.dto.TrainingMenuAuthRespDto;
import com.tianfang.train.dto.TrainingMenuRespDto;
//import com.juju.sport.admin.manger.dto.AdminMenuFunctionDto;

@Service
public interface ITrainMenuService {



    
    /**     
     * findTrainingMenuByAdminId：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年9月22日 上午11:20:23	 
     */
    List<MenuRespDto> findMenuByAdminId(String id);
    
    public List<TrainingMenuRespDto> findAll();
    
    public List<TrainingMenuRespDto> findAdminMenuByAdminId(String id);
    
    public List<TrainingMenuAuthRespDto> getAdminAuthById(String adminId);

}
