package com.tianfang.train.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.train.dao.TrainingAdminDao;
import com.tianfang.train.dao.TrainingMenuDao;
import com.tianfang.train.dto.TrainingAdminDto;
import com.tianfang.train.dto.TrainingMenuRespDto;
import com.tianfang.train.pojo.TrainingAdmin;
import com.tianfang.train.pojo.TrainingAuthorization;
import com.tianfang.train.service.ITrainAdminService;

/**
 * 
 *
 */
@Service
public class ITrainAdminServiceImpl implements ITrainAdminService {

    @Autowired
    private TrainingAdminDao trainingAdminDao;
    
    @Autowired
    private TrainingMenuDao trainingMenuDao;

    
     /** 
     * (non-Javadoc)   
     * @see org.juju.sport.train.service.ITrainAdminService#findByAccount(java.lang.String)   
     */
    @Override
    public Object adminLogin(String account, String passWord)
    {
        // TODO 添加方法注释
        TrainingAdmin trainingAdmin = trainingAdminDao.findByAccountPwd(account,null);
        if (null == trainingAdmin) {
            return 0;
        }
        TrainingAdmin trainingadmin = trainingAdminDao.findByAccountPwd(account,passWord);
        if (null == trainingadmin) {
            return 1;
        }else{
            TrainingAdminDto trainingAdminDto = BeanUtils.createBeanByTarget(trainingadmin, TrainingAdminDto.class);
            return trainingAdminDto;
        }
    }
    
    public Integer updateUser(TrainingAdminDto user) {
        TrainingAdmin trainingAdmin = trainingAdminDao.findByAccountPwd(user.getAccount(),null);
        if (null != trainingAdmin) {
            if(!trainingAdmin.getId().equals(user.getId())) {
                return 0;
            }            
        }
        TrainingAdmin trainingadmin = trainingAdminDao.selectByPrimaryKey(user.getId().toString());
        trainingadmin.setAccount(user.getAccount());
        trainingadmin.setPassWord(user.getPassWord());
        trainingadmin.setUpdateTime(new Date().getTime());
        trainingAdminDao.updateByPrimaryKey(trainingadmin);
        return 1;
    }
    
    public PageResult<TrainingAdminDto> findPage(TrainingAdminDto trainingAdminDto,PageQuery page) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<TrainingAdminDto> trainingAdminDtos = BeanUtils.createBeanListByTarget(trainingAdminDao.findPage(trainingAdminDto, page), TrainingAdminDto.class);
        for (TrainingAdminDto trainingadminDto : trainingAdminDtos) {
            if (null != trainingadminDto.getCreateTime()) {
                trainingadminDto.setCreateTime(trainingadminDto.getCreateTime()*1000);
                Date date = new Date(trainingadminDto.getCreateTime());
                trainingadminDto.setCreateDate(sdf.format(date));
            }
        }
        page.setTotal(trainingAdminDao.count(trainingAdminDto));
        return new PageResult<TrainingAdminDto>(page, trainingAdminDtos);
    }
    
    public Object save(TrainingAdminDto trainingAdminDto) {
        TrainingAdmin trainingadmin = trainingAdminDao.findByAccountPwd(trainingAdminDto.getAccount(), null);
        if (null != trainingadmin) {
            return 0;//此用户名已经存在
        }
        TrainingAdmin trainingAdmin = BeanUtils.createBeanByTarget(trainingAdminDto, TrainingAdmin.class);
        trainingAdmin.setId(UUIDGenerator.getUUID());
        trainingAdmin.setCreateTime(new Date().getTime()/1000);
        trainingAdmin.setStatus(DataStatus.ENABLED);
        trainingAdminDao.insert(trainingAdmin);
        return 1;
    }
    
    public Object save(TrainingAdminDto trainingAdminDto,String jsonClasss) {
        List<TrainingMenuRespDto> menuRespDtos = new Gson().fromJson(jsonClasss, new TypeToken<List<TrainingMenuRespDto>>(){}.getType()); 
        TrainingAdmin trainingadmin = trainingAdminDao.findByAccountPwd(trainingAdminDto.getAccount(), null);
        if (null != trainingadmin) {
            return 0;//此用户名已经存在
        }
        TrainingAdmin trainingAdmin = BeanUtils.createBeanByTarget(trainingAdminDto, TrainingAdmin.class);
        trainingAdmin.setId(UUIDGenerator.getUUID());
        trainingAdmin.setCreateTime(new Date().getTime()/1000);
        trainingAdmin.setStatus(DataStatus.ENABLED);
        trainingAdminDao.insert(trainingAdmin);
        for (TrainingMenuRespDto trainingMenuRespDto : menuRespDtos) {
            TrainingAuthorization trainingAuthorization = new TrainingAuthorization();
            trainingAuthorization.setAdminId(trainingAdmin.getId());
            trainingAuthorization.setId(UUIDGenerator.getUUID());
            trainingAuthorization.setMenuId(trainingMenuRespDto.getId());
            trainingAuthorization.setCreateTime(new Date().getTime()/1000);
            trainingAuthorization.setStatus(DataStatus.ENABLED);
            trainingMenuDao.save(trainingAuthorization);
        }
        return 1;
    }
    
    public Object edit(TrainingAdminDto trainingAdminDto) {
        TrainingAdmin trainingAdmin = BeanUtils.createBeanByTarget(trainingAdminDto, TrainingAdmin.class);
        trainingAdmin.setUpdateTime(new Date().getTime()/1000);
        trainingAdmin.setStatus(DataStatus.ENABLED);
        trainingAdminDao.updateByPrimaryKeySelective(trainingAdmin);
        return 1;
    }
    
    public Object edit(TrainingAdminDto trainingAdminDto,String jsonClasss) {
        List<TrainingMenuRespDto> menuRespDtos = new Gson().fromJson(jsonClasss, new TypeToken<List<TrainingMenuRespDto>>(){}.getType()); 
        TrainingAdmin trainingAdmin = BeanUtils.createBeanByTarget(trainingAdminDto, TrainingAdmin.class);
        trainingAdmin.setUpdateTime(new Date().getTime()/1000);
        trainingAdmin.setStatus(DataStatus.ENABLED);
        trainingAdminDao.updateByPrimaryKeySelective(trainingAdmin);
        List<TrainingAuthorization> trainingAuthorizationList = trainingMenuDao.findByAdminId(trainingAdminDto.getId());
        for (TrainingAuthorization trainingAuthorization : trainingAuthorizationList) {
            trainingAuthorization.setStatus(DataStatus.DISABLED);
            trainingAuthorization.setLastUpdateTime(new Date().getTime()/1000);
            trainingMenuDao.update(trainingAuthorization);
        }
        for (TrainingMenuRespDto trainingMenuRespDto : menuRespDtos) {
            TrainingAuthorization trainingAuthorization = trainingMenuDao.findByAdminMenuIds(trainingAdminDto.getId(), trainingMenuRespDto.getId());
            if (null != trainingAuthorization) {
                trainingAuthorization.setStatus(DataStatus.ENABLED);
                trainingAuthorization.setLastUpdateTime(new Date().getTime()/1000);
                trainingMenuDao.update(trainingAuthorization);
            }
            if (null == trainingAuthorization) {
                TrainingAuthorization trainingauthorization = new TrainingAuthorization();
                trainingauthorization.setId(UUIDGenerator.getUUID());
                trainingauthorization.setAdminId(trainingAdminDto.getId());
                trainingauthorization.setMenuId(trainingMenuRespDto.getId());
                trainingauthorization.setCreateTime(new Date().getTime()/1000);
                trainingauthorization.setStatus(DataStatus.ENABLED);
                trainingMenuDao.save(trainingauthorization);
            }
        }
        return 1;
    }
    
    public Object distributionAuth(String adminId,String menuIds) {
        String[] idArr = menuIds.split(",");
        List<TrainingAuthorization> trainingAuthorizationList = trainingMenuDao.findByAdminId(adminId);
        for (TrainingAuthorization trainingAuthorization : trainingAuthorizationList) {
            trainingAuthorization.setStatus(DataStatus.DISABLED);
            trainingAuthorization.setLastUpdateTime(new Date().getTime()/1000);
            trainingMenuDao.update(trainingAuthorization);
        }
        for (String id : idArr) {
            TrainingAuthorization trainingAuthorization = trainingMenuDao.findByAdminMenuIds(adminId, id);
            if (null != trainingAuthorization) {
                trainingAuthorization.setStatus(DataStatus.ENABLED);
                trainingAuthorization.setLastUpdateTime(new Date().getTime()/1000);
                trainingMenuDao.update(trainingAuthorization);
            }
            if (null == trainingAuthorization) {
                TrainingAuthorization trainingauthorization = new TrainingAuthorization();
                trainingauthorization.setId(UUIDGenerator.getUUID());
                trainingauthorization.setAdminId(adminId);
                trainingauthorization.setMenuId(id);
                trainingauthorization.setCreateTime(new Date().getTime()/1000);
                trainingauthorization.setStatus(DataStatus.ENABLED);
                trainingMenuDao.save(trainingauthorization);
            }
        }
        return 1;
    }
    
    public TrainingAdminDto getAdmin(String id) {
        TrainingAdmin trainingAdmin = trainingAdminDao.selectByPrimaryKey(id);
        TrainingAdminDto trainingAdminDto = BeanUtils.createBeanByTarget(trainingAdmin, TrainingAdminDto.class);
        return trainingAdminDto;
    }
    
    public Object delAdIds(String  delAdIds) {
        String[] idArr = delAdIds.split(",");
        for (String id : idArr) {
            TrainingAdmin trainingAdmin = trainingAdminDao.selectByPrimaryKey(id);
            if (null == trainingAdmin) {
                return 0;//无此条记录
            }
            trainingAdmin.setUpdateTime(new Date().getTime()/1000);
            trainingAdmin.setStatus(DataStatus.DISABLED);
            trainingAdminDao.updateByPrimaryKey(trainingAdmin);
        }
        return 1;
    }
}
