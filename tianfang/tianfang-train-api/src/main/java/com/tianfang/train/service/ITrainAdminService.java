package com.tianfang.train.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.TrainingAdminDto;
import com.tianfang.train.dto.TrainingMenuAuthRespDto;



//import com.juju.sport.admin.manger.dto.AdminMenuFunctionDto;

@Service
public interface ITrainAdminService {
    
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
    
    public Integer updateUser(TrainingAdminDto user);
    
    public PageResult<TrainingAdminDto> findPage(TrainingAdminDto trainingAdminDto,PageQuery page);
    
    public Object save(TrainingAdminDto trainingAdminDto);
    
    public Object save(TrainingAdminDto trainingAdminDto,String jsonClasss);
    
    public Object delAdIds(String  delAdIds);
    
    public TrainingAdminDto getAdmin(String id);
    
    public Object edit(TrainingAdminDto trainingAdminDto); 
    
    public Object edit(TrainingAdminDto trainingAdminDto,String jsonClasss);   
    
    public Object distributionAuth(String adminId,String menuIds);
    
}
