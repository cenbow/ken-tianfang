package com.tianfang.admin.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.admin.dao.SportMenuDao;
import com.tianfang.admin.dto.MenuRespDto;
import com.tianfang.admin.dto.SportMenuAuthRespDto;
import com.tianfang.admin.dto.SportMenuRespDto;
import com.tianfang.admin.pojo.SportAuthorization;
import com.tianfang.admin.pojo.SportMenu;
import com.tianfang.admin.service.ISportMenuService;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.Collections3;

/**
 * 
 * @author pengpeng
 *
 */
@Service
public class ISportMenuServiceImpl implements ISportMenuService {

    @Autowired
    private SportMenuDao sportMenuDao;
    
    
//    @Override
    public List<SportMenu> findByleftMenu(String leftMenu)
    {
        // TODO 添加方法注释
        return sportMenuDao.findByleftMenu(leftMenu);
    }

    
//    @Override
    public List<SportMenu> findByLeaf(String leaf)
    {
        // TODO 添加方法注释
        return sportMenuDao.findByleaf(leaf);
    }

    public List<SportMenuRespDto> findAdminMenuByAdminId(String id){
        List<SportMenu> permLists = sportMenuDao.findMenuByAdminId(id);//用户拥有的权限、
        List<SportMenuRespDto> trainingMenuRespDto = BeanUtils.createBeanListByTarget(permLists, SportMenuRespDto.class);
        return trainingMenuRespDto;
    }
    
    public List<SportMenuAuthRespDto> getAdminAuthById(String adminId) {
        List<SportMenuAuthRespDto> trainingMenuAuthRespDtos = new ArrayList<SportMenuAuthRespDto>();
        List<SportMenu> trainingMenuLeaf = sportMenuDao.findByleaf("否");
        for (SportMenu trainingMenu:trainingMenuLeaf) {
            SportMenuAuthRespDto trainingMenuAuthRespDto = BeanUtils.createBeanByTarget(trainingMenu, SportMenuAuthRespDto.class);
            SportAuthorization trainingAuthorization = sportMenuDao.findByAdminIdAndMenuId(adminId,trainingMenu.getId());
            List<SportMenu> trainingMenuChild = sportMenuDao.findByParentId(trainingMenu.getId());
            if (null == trainingAuthorization) {
                List<SportMenuAuthRespDto> trainingmenuAuthRespDtos = BeanUtils.createBeanListByTarget(trainingMenuChild, SportMenuAuthRespDto.class);
                for (SportMenuAuthRespDto trainingmenuAuthRespDto : trainingmenuAuthRespDtos) {
                    trainingmenuAuthRespDto.setChecked(false);
                }
                trainingMenuAuthRespDto.setNodes(trainingmenuAuthRespDtos);
            }
            if(null != trainingAuthorization) {
                List<SportMenuAuthRespDto> trainingmenuAuthRespDtos = BeanUtils.createBeanListByTarget(trainingMenuChild, SportMenuAuthRespDto.class);
                for (SportMenuAuthRespDto trainingmenuAuthRespDto : trainingmenuAuthRespDtos) {
                    SportAuthorization trainingauthorization = sportMenuDao.findByMenuId(trainingmenuAuthRespDto.getId());
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
    
    public List<MenuRespDto> findMenuByAdminId(String id) {
    	List<SportMenu> permLists = sportMenuDao.findMenuByAdminId(id);//用户拥有的权限
    	List<SportMenu> list1 = findByLeaf("否"); //左侧一级菜单判断
//    	List<SportMenu> list2 =  Collections3.intersection(permLists, list1);//用户拥有的左侧一级菜单
    	List<SportMenu> list2 = new ArrayList<SportMenu>();
//    	Map<String, SportMenu> permListsMap = new HashMap<>();
//    	for (SportMenu SportMenu : permLists) {
//    		permListsMap.put(SportMenu.getId(), SportMenu);
//    	}
//    	Map<String, SportMenu> list1Map = new HashMap<>();
//    	for (SportMenu sportMenu : list1) {
//    		list1Map.put(sportMenu.getId(), sportMenu);
//    	}
//    	for (String permListsMapId:permListsMap.keySet()) {
//    		if (list1Map.containsKey(permListsMapId)) {
//    			SportMenu sportMenu =permListsMap.get(permListsMapId);
//    			list2.add(sportMenu);
//        	}
//    	}    	
    	for (SportMenu sportMenu:permLists) {
    		for (SportMenu sportmenu:list1) {
    			if (sportMenu.getId().equals(sportmenu.getId())) {
    				list2.add(sportMenu);
    				list1.remove(sportmenu);
    				break;
    			}
    		}
    	}    	
    	List<SportMenu> list3 = findByLeaf("是"); //左侧所有二级菜单判断
//    	List<SportMenu> list4 = Collections3.intersection(list3, permLists);//用户拥有的左侧二级菜单
    	List<SportMenu> list4 = new ArrayList<SportMenu>();
//    	Map<String, SportMenu> list3Map = new HashMap<>();
//    	for (SportMenu sportMenu : list3) {
//    		list3Map.put(sportMenu.getId(), sportMenu);
//    	}
//    	for (String permListsMapId:permListsMap.keySet()) {
//    		if (list3Map.containsKey(permListsMapId)) {
//    			SportMenu sportMenu =permListsMap.get(permListsMapId);
//    			list4.add(sportMenu);
//        	}
//    	}
    	for (SportMenu sportMenu:permLists) {
    		for (SportMenu sportmenu:list3) {
    			if (sportMenu.getId().equals(sportmenu.getId())) {
    				list4.add(sportMenu);
//    				list3.remove(sportmenu);
    			}
    		}
    	}    	
    	List<MenuRespDto> permissionDTOListOut = new ArrayList<MenuRespDto>();    	
    	for (SportMenu sportMenu:list2) {
    		MenuRespDto menuRespDto = new MenuRespDto();
    		SportMenuRespDto sportMenuRespDto = BeanUtils.createBeanByTarget(sportMenu, SportMenuRespDto.class);
    		List<SportMenuRespDto> sportMenuRespDtos = new ArrayList<SportMenuRespDto>();
    		for (SportMenu sportmenu:list4){    			
    			if (sportMenu.getId().equals(sportmenu.getParentId())) {    				
    				SportMenuRespDto sportmenuRespDto = BeanUtils.createBeanByTarget(sportmenu, SportMenuRespDto.class);
    				sportMenuRespDtos.add(sportmenuRespDto);
//    				list4.remove(sportmenu);
    			}
    		}
    		menuRespDto.setParant(sportMenuRespDto);
    		menuRespDto.setLeaf(sportMenuRespDtos); 
    		permissionDTOListOut.add(menuRespDto);
    	}    	
    	return permissionDTOListOut;
    }
    
//    @Override
    public List<MenuRespDto> findMenuByAdminIds(String id){
        List<SportMenu> permLists = sportMenuDao.findMenuByAdminId(id);//用户拥有的权限
        List<String> permListss = new ArrayList<String>();
        for (SportMenu trainingMenu : permLists) {
            permListss.add(trainingMenu.getId());
        }
        List<SportMenu> list1 = findByleftMenu("是"); //左侧菜单判断
        List<String> list11 = new ArrayList<String>();
        for (SportMenu trainingMenu : list1) {
            list11.add(trainingMenu.getId());
        }
        List<SportMenu> list2 = findByLeaf("是");  //顶级菜单叶子判断
        List<String> list22 = new ArrayList<String>();
        for (SportMenu trainingMenu : list2) {
            list22.add(trainingMenu.getId());
        }
        List<String> list33 = Collections3.subtract(list11, list22);//左侧顶级菜单
        List<String> list3 = Collections3.intersection(list33, permListss);//用户拥有的左侧顶级菜单
        List<MenuRespDto> permissionDTOListOut = new ArrayList<MenuRespDto>();
        for(String  permission:list3){
            List<SportMenu> list44 =findByparentId(permission);
            List<String> list44s = new ArrayList<String>();
            for (SportMenu trainingMenu : list44) {
                list44s.add(trainingMenu.getId());
            }
            List<String> list4 = Collections3.intersection(list44s, permListss);
            SportMenu permissions = sportMenuDao.selectByPrimaryKey(permission);
            List<SportMenu> list4s = new ArrayList<SportMenu>();
            for (String menuId :list4){
                SportMenu trainingMenus = sportMenuDao.selectByPrimaryKey(menuId);
                list4s.add(trainingMenus);
            }
            MenuRespDto permissionDTO1 = new   MenuRespDto();
            SportMenuRespDto trainingMenuRespDto = BeanUtils.createBeanByTarget(permissions, SportMenuRespDto.class);
            List<SportMenuRespDto> trainingMenuRespDtoList = BeanUtils.createBeanListByTarget(list4s, SportMenuRespDto.class);
            permissionDTO1.setParant(trainingMenuRespDto);
            permissionDTO1.setLeaf(trainingMenuRespDtoList);
            permissionDTOListOut.add(permissionDTO1);
        }
        return permissionDTOListOut;
    }
    
//    @Override
    public List<SportMenu> findByparentId(String parentId)
    {
        // TODO 添加方法注释
        return sportMenuDao.findByParentId(parentId);
    }
	
//    @Override
    public List<SportMenuRespDto> findAll()
    {
        // TODO 添加方法注释
        return sportMenuDao.findAll();
    }
    
}
