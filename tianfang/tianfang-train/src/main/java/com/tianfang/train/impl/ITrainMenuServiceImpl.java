package com.tianfang.train.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.Collections3;
import com.tianfang.train.dao.TrainingMenuDao;
import com.tianfang.train.dto.MenuRespDto;
import com.tianfang.train.dto.TrainingMenuAuthRespDto;
import com.tianfang.train.dto.TrainingMenuRespDto;
import com.tianfang.train.pojo.TrainingAuthorization;
import com.tianfang.train.pojo.TrainingMenu;
import com.tianfang.train.service.ITrainMenuService;

/**
 * 
 * @author pengpeng
 *
 */
@Service
public class ITrainMenuServiceImpl implements ITrainMenuService {

    @Autowired
    private TrainingMenuDao trainingMenuDao;
    
    
//    @Override
    public List<TrainingMenu> findByleftMenu(String leftMenu)
    {
        // TODO 添加方法注释
        return trainingMenuDao.findByleftMenu(leftMenu);
    }

    
//    @Override
    public List<TrainingMenu> findByLeaf(String leaf)
    {
        // TODO 添加方法注释
        return trainingMenuDao.findByleaf(leaf);
    }

    public List<TrainingMenuRespDto> findAdminMenuByAdminId(String id){
        List<TrainingMenu> permLists = trainingMenuDao.findTrainingMenuByAdminId(id);//用户拥有的权限、
        List<TrainingMenuRespDto> trainingMenuRespDto = BeanUtils.createBeanListByTarget(permLists, TrainingMenuRespDto.class);
        return trainingMenuRespDto;
    }
    
    public List<TrainingMenuAuthRespDto> getAdminAuthById(String adminId) {
        List<TrainingMenuAuthRespDto> trainingMenuAuthRespDtos = new ArrayList<TrainingMenuAuthRespDto>();
        List<TrainingMenu> trainingMenuLeaf = trainingMenuDao.findByleaf("否");
        for (TrainingMenu trainingMenu:trainingMenuLeaf) {
            TrainingMenuAuthRespDto trainingMenuAuthRespDto = BeanUtils.createBeanByTarget(trainingMenu, TrainingMenuAuthRespDto.class);
            TrainingAuthorization trainingAuthorization = trainingMenuDao.findByAdminIdAndMenuId(adminId,trainingMenu.getId());
            List<TrainingMenu> trainingMenuChild = trainingMenuDao.findByParentId(trainingMenu.getId());
            if (null == trainingAuthorization) {
                List<TrainingMenuAuthRespDto> trainingmenuAuthRespDtos = BeanUtils.createBeanListByTarget(trainingMenuChild, TrainingMenuAuthRespDto.class);
                for (TrainingMenuAuthRespDto trainingmenuAuthRespDto : trainingmenuAuthRespDtos) {
                    trainingmenuAuthRespDto.setChecked(false);
                }
                trainingMenuAuthRespDto.setNodes(trainingmenuAuthRespDtos);
            }
            if(null != trainingAuthorization) {
                List<TrainingMenuAuthRespDto> trainingmenuAuthRespDtos = BeanUtils.createBeanListByTarget(trainingMenuChild, TrainingMenuAuthRespDto.class);
                for (TrainingMenuAuthRespDto trainingmenuAuthRespDto : trainingmenuAuthRespDtos) {
                    TrainingAuthorization trainingauthorization = trainingMenuDao.findByMenuId(trainingmenuAuthRespDto.getId());
                    if (null != trainingauthorization) {
                        trainingmenuAuthRespDto.setChecked(true);
                    }else {
                        trainingmenuAuthRespDto.setChecked(false);
                    }
                } 
                trainingMenuAuthRespDto.setChecked(true);
                trainingMenuAuthRespDto.setNodes(trainingmenuAuthRespDtos);                
            }
            trainingMenuAuthRespDtos.add(trainingMenuAuthRespDto);
        }
        return trainingMenuAuthRespDtos;
    }
    
//    @Override
    public List<MenuRespDto> findMenuByAdminId(String id){
        List<TrainingMenu> permLists = trainingMenuDao.findTrainingMenuByAdminId(id);//用户拥有的权限
        List<String> permListss = new ArrayList<String>();
        for (TrainingMenu trainingMenu : permLists) {
            permListss.add(trainingMenu.getId());
        }
        List<TrainingMenu> list1 = findByleftMenu("是"); //左侧菜单判断
        List<String> list11 = new ArrayList<String>();
        for (TrainingMenu trainingMenu : list1) {
            list11.add(trainingMenu.getId());
        }
        List<TrainingMenu> list2 = findByLeaf("是");  //顶级菜单叶子判断
        List<String> list22 = new ArrayList<String>();
        for (TrainingMenu trainingMenu : list2) {
            list22.add(trainingMenu.getId());
        }
        List<String> list33 = Collections3.subtract(list11, list22);//左侧顶级菜单
        List<String> list3 = Collections3.intersection(list33, permListss);//用户拥有的左侧顶级菜单
        List<MenuRespDto> permissionDTOListOut = new ArrayList<MenuRespDto>();
        for(String  permission:list3){
            List<TrainingMenu> list44 =findByparentId(permission);
            List<String> list44s = new ArrayList<String>();
            for (TrainingMenu trainingMenu : list44) {
                list44s.add(trainingMenu.getId());
            }
            List<String> list4 = Collections3.intersection(list44s, permListss);
            TrainingMenu permissions = trainingMenuDao.selectByPrimaryKey(permission);
            List<TrainingMenu> list4s = new ArrayList<TrainingMenu>();
            for (String menuId :list4){
                TrainingMenu trainingMenus = trainingMenuDao.selectByPrimaryKey(menuId);
                list4s.add(trainingMenus);
            }
            MenuRespDto permissionDTO1 = new   MenuRespDto();
            TrainingMenuRespDto trainingMenuRespDto = BeanUtils.createBeanByTarget(permissions, TrainingMenuRespDto.class);
            List<TrainingMenuRespDto> trainingMenuRespDtoList = BeanUtils.createBeanListByTarget(list4s, TrainingMenuRespDto.class);
            permissionDTO1.setParant(trainingMenuRespDto);
            permissionDTO1.setLeaf(trainingMenuRespDtoList);
            permissionDTOListOut.add(permissionDTO1);
        }
        return permissionDTOListOut;
    }
    
//    @Override
    public List<TrainingMenu> findByparentId(String parentId)
    {
        // TODO 添加方法注释
        return trainingMenuDao.findByParentId(parentId);
    }
	
//    @Override
    public List<TrainingMenuRespDto> findAll()
    {
        // TODO 添加方法注释
        return trainingMenuDao.findAll();
    }
    
}
