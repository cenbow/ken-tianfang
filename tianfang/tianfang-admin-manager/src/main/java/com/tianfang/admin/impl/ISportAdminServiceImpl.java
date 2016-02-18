package com.tianfang.admin.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tianfang.admin.dao.SportAdminDao;
import com.tianfang.admin.dao.SportMenuDao;
import com.tianfang.admin.dto.SportAdminDto;
import com.tianfang.admin.dto.SportMenuRespDto;
import com.tianfang.admin.pojo.SportAdmin;
import com.tianfang.admin.pojo.SportAuthorization;
import com.tianfang.admin.service.ISportAdminService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.UUIDGenerator;

/**
 * 
 *
 */
@Service
public class ISportAdminServiceImpl implements ISportAdminService {

    @Autowired
    private SportAdminDao sportAdminDao;
    
    @Autowired
    private SportMenuDao sportMenuDao;

    
     /** 
     * (non-Javadoc)   
     * @see org.juju.sport.train.service.ITrainAdminService#findByAccount(java.lang.String)   
     */
    @Override
    public Object adminLogin(String account, String passWord)
    {
        // TODO 添加方法注释
        SportAdmin sportAdmin = sportAdminDao.findByAccountPwd(account,null);
        if (null == sportAdmin) {
            return 0;
        }
        SportAdmin trainingadmin = sportAdminDao.findByAccountPwd(account,passWord);
        if (null == trainingadmin) {
            return 1;
        }else{
            SportAdminDto trainingAdminDto = BeanUtils.createBeanByTarget(trainingadmin, SportAdminDto.class);
            return trainingAdminDto;
        }
    }
    
    public Integer updateUser(SportAdminDto user) {
        SportAdmin sportAdmin = sportAdminDao.findByAccountPwd(user.getAccount(),null);
        if (null != sportAdmin) {
            if(!sportAdmin.getId().equals(user.getId())) {
                return 0;
            }            
        }
        SportAdmin trainingadmin = sportAdminDao.selectByPrimaryKey(user.getId().toString());
        trainingadmin.setAccount(user.getAccount());
        trainingadmin.setPassWord(user.getPassWord());
        trainingadmin.setUpdateTime(new Date());
        sportAdminDao.updateByPrimaryKey(trainingadmin);
        return 1;
    }
    
    public PageResult<SportAdminDto> findPage(SportAdminDto trainingAdminDto,PageQuery page) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<SportAdminDto> trainingAdminDtos = BeanUtils.createBeanListByTarget(sportAdminDao.findPage(trainingAdminDto, page), SportAdminDto.class);
        for (SportAdminDto trainingadminDto : trainingAdminDtos) {
            if (null != trainingadminDto.getCreateTime()) {
                trainingadminDto.setCreateTime(trainingadminDto.getCreateTime()*1000);
                Date date = new Date(trainingadminDto.getCreateTime());
                trainingadminDto.setCreateDate(sdf.format(date));
            }
        }
        page.setTotal(sportAdminDao.count(trainingAdminDto));
        return new PageResult<SportAdminDto>(page, trainingAdminDtos);
    }
    
    public Object save(SportAdminDto trainingAdminDto) {
        SportAdmin trainingadmin = sportAdminDao.findByAccountPwd(trainingAdminDto.getAccount(), null);
        if (null != trainingadmin) {
            return 0;//此用户名已经存在
        }
        SportAdmin sportAdmin = BeanUtils.createBeanByTarget(trainingAdminDto, SportAdmin.class);
        sportAdmin.setId(UUIDGenerator.getUUID());
        sportAdmin.setCreateTime(new Date());
        sportAdmin.setStat(DataStatus.ENABLED);
        sportAdminDao.insert(sportAdmin);
        return 1;
    }
    
    public Object save(SportAdminDto trainingAdminDto,String jsonClasss) {
        List<SportMenuRespDto> menuRespDtos = new Gson().fromJson(jsonClasss, new TypeToken<List<SportMenuRespDto>>(){}.getType()); 
        SportAdmin trainingadmin = sportAdminDao.findByAccountPwd(trainingAdminDto.getAccount(), null);
        if (null != trainingadmin) {
            return 0;//此用户名已经存在
        }
        SportAdmin sportAdmin = BeanUtils.createBeanByTarget(trainingAdminDto, SportAdmin.class);
        sportAdmin.setId(UUIDGenerator.getUUID());
        sportAdmin.setCreateTime(new Date());
        sportAdmin.setStat(DataStatus.ENABLED);
        sportAdminDao.insert(sportAdmin);
        for (SportMenuRespDto trainingMenuRespDto : menuRespDtos) {
            SportAuthorization trainingAuthorization = new SportAuthorization();
            trainingAuthorization.setAdminId(sportAdmin.getId());
            trainingAuthorization.setId(UUIDGenerator.getUUID());
            trainingAuthorization.setMenuId(trainingMenuRespDto.getId());
            trainingAuthorization.setCreateTime(new Date());
            trainingAuthorization.setStat(DataStatus.ENABLED);
            sportMenuDao.save(trainingAuthorization);
        }
        return 1;
    }
    
    public Object edit(SportAdminDto trainingAdminDto) {
        SportAdmin sportAdmin = BeanUtils.createBeanByTarget(trainingAdminDto, SportAdmin.class);
        sportAdmin.setUpdateTime(new Date());
        sportAdmin.setStat(DataStatus.ENABLED);
        sportAdminDao.updateByPrimaryKeySelective(sportAdmin);
        return 1;
    }
    
    public Object edit(SportAdminDto trainingAdminDto,String jsonClasss) {
        List<SportMenuRespDto> menuRespDtos = new Gson().fromJson(jsonClasss, new TypeToken<List<SportMenuRespDto>>(){}.getType()); 
        SportAdmin sportAdmin = BeanUtils.createBeanByTarget(trainingAdminDto, SportAdmin.class);
        sportAdmin.setUpdateTime(new Date());
        sportAdmin.setStat(DataStatus.ENABLED);
        sportAdminDao.updateByPrimaryKeySelective(sportAdmin);
        List<SportAuthorization> trainingAuthorizationList = sportMenuDao.findByAdminId(trainingAdminDto.getId());
        for (SportAuthorization trainingAuthorization : trainingAuthorizationList) {
            trainingAuthorization.setStat(DataStatus.DISABLED);
            trainingAuthorization.setLastUpdateTime(new Date());
            sportMenuDao.update(trainingAuthorization);
        }
        for (SportMenuRespDto sportMenuRespDto : menuRespDtos) {
            SportAuthorization sportAuthorization = sportMenuDao.findByAdminMenuIds(trainingAdminDto.getId(), sportMenuRespDto.getId());
            if (null != sportAuthorization) {
            	sportAuthorization.setStat(DataStatus.ENABLED);
            	sportAuthorization.setLastUpdateTime(new Date());
                sportMenuDao.update(sportAuthorization);
            }
            if (null == sportAuthorization) {
                sportAuthorization = new SportAuthorization();
                sportAuthorization.setId(UUIDGenerator.getUUID());
                sportAuthorization.setAdminId(trainingAdminDto.getId());
                sportAuthorization.setMenuId(sportMenuRespDto.getId());
                sportAuthorization.setCreateTime(new Date());
                sportAuthorization.setStat(DataStatus.ENABLED);
                sportMenuDao.save(sportAuthorization);
            }
        }
        return 1;
    }
    
    public Object distributionAuth(String adminId,String menuIds) {
        String[] idArr = menuIds.split(",");
        List<SportAuthorization> trainingAuthorizationList = sportMenuDao.findByAdminId(adminId);
        for (SportAuthorization trainingAuthorization : trainingAuthorizationList) {
            trainingAuthorization.setStat(DataStatus.DISABLED);
            trainingAuthorization.setLastUpdateTime(new Date());
            sportMenuDao.update(trainingAuthorization);
        }
        for (String id : idArr) {
            SportAuthorization trainingAuthorization = sportMenuDao.findByAdminMenuIds(adminId, id);
            if (null != trainingAuthorization) {
                trainingAuthorization.setStat(DataStatus.ENABLED);
                trainingAuthorization.setLastUpdateTime(new Date());
                sportMenuDao.update(trainingAuthorization);
            }
            if (null == trainingAuthorization) {
                SportAuthorization sportAuthorization = new SportAuthorization();
                sportAuthorization.setId(UUIDGenerator.getUUID());
                sportAuthorization.setAdminId(adminId);
                sportAuthorization.setMenuId(id);
                sportAuthorization.setCreateTime(new Date());
                sportAuthorization.setStat(DataStatus.ENABLED);
                sportMenuDao.save(sportAuthorization);
            }
        }
        return 1;
    }
    
    public SportAdminDto getAdmin(String id) {
        SportAdmin sportAdmin = sportAdminDao.selectByPrimaryKey(id);
        SportAdminDto trainingAdminDto = BeanUtils.createBeanByTarget(sportAdmin, SportAdminDto.class);
        return trainingAdminDto;
    }
    
    public Object delAdIds(String  delAdIds) {
        String[] idArr = delAdIds.split(",");
        for (String id : idArr) {
            SportAdmin sportAdmin = sportAdminDao.selectByPrimaryKey(id);
            if (null == sportAdmin) {
                return 0;//无此条记录
            }
            sportAdmin.setUpdateTime(new Date());
            sportAdmin.setStat(DataStatus.DISABLED);
            sportAdminDao.updateByPrimaryKey(sportAdmin);
        }
        return 1;
    }
}
