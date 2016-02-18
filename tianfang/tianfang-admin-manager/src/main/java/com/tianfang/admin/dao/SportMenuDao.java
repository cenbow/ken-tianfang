package com.tianfang.admin.dao;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tianfang.admin.dto.SportMenuRespDto;
import com.tianfang.admin.mapper.SportAuthorizationExMapper;
import com.tianfang.admin.mapper.SportAuthorizationMapper;
import com.tianfang.admin.mapper.SportMenuMapper;
import com.tianfang.admin.pojo.SportAuthorization;
import com.tianfang.admin.pojo.SportAuthorizationExample;
import com.tianfang.admin.pojo.SportMenu;
import com.tianfang.admin.pojo.SportMenuExample;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;

@Repository
public class SportMenuDao extends MyBatisBaseDao<SportMenu>{

	@Autowired
	@Getter
	private SportMenuMapper mapper;
	
	@Autowired
	@Getter
	private SportAuthorizationMapper authMapper;
	
	@Autowired
    @Getter
    private SportAuthorizationExMapper authExmapper;
	

	public List<SportMenuRespDto> findAll() {
	    SportMenuExample example = new SportMenuExample();
	    SportMenuExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		example.setOrderByClause(" orderby asc ");
		List<SportMenu>  trainingMenuList = mapper.selectByExample(example);
		List<SportMenuRespDto> trainingMenuRespDTOs = new ArrayList<SportMenuRespDto>();
        if (trainingMenuList.size()>0) {
            trainingMenuRespDTOs= BeanUtils.createBeanListByTarget(trainingMenuList, SportMenuRespDto.class);
            return trainingMenuRespDTOs;
        }
		return trainingMenuRespDTOs;
	} 
	
	public List<SportMenu> findByleftMenu(String leftMenu) {
	    SportMenuExample example = new SportMenuExample();
        SportMenuExample.Criteria criteria = example.createCriteria();
        criteria.andLeftMenuEqualTo(leftMenu);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause(" orderby asc ");
        List<SportMenu>  trainingMenuList = mapper.selectByExample(example);
//        List<SportMenuRespDto> trainingMenuRespDTOs = new ArrayList<SportMenuRespDto>();
//        if (trainingMenuList.size()>0) {
//            trainingMenuRespDTOs= BeanUtils.createBeanListByTarget(trainingMenuList, SportMenuRespDto.class);
//            return trainingMenuRespDTOs;
//        }
        return trainingMenuList;
	}
	
	public List<SportMenu> findByleaf(String leaf) {
        SportMenuExample example = new SportMenuExample();
        SportMenuExample.Criteria criteria = example.createCriteria();
        criteria.andLeafEqualTo(leaf);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause(" orderby asc ");
        List<SportMenu>  trainingMenuList = mapper.selectByExample(example);
//        List<SportMenuRespDto> trainingMenuRespDTOs = new ArrayList<SportMenuRespDto>();
//        if (trainingMenuList.size()>0) {
//            trainingMenuRespDTOs= BeanUtils.createBeanListByTarget(trainingMenuList, SportMenuRespDto.class);
//            return trainingMenuRespDTOs;
//        }
        return trainingMenuList;
    }
    
	public List<SportMenu> findByParentId(String ParentId) {
        SportMenuExample example = new SportMenuExample();
        SportMenuExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(ParentId);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause(" orderby asc ");
        List<SportMenu>  trainingMenuList = mapper.selectByExample(example);
//        List<SportMenuRespDto> trainingMenuRespDTOs = new ArrayList<SportMenuRespDto>();
//        if (trainingMenuList.size()>0) {
//            trainingMenuRespDTOs= BeanUtils.createBeanListByTarget(trainingMenuList, SportMenuRespDto.class);
//            return trainingMenuRespDTOs;
//        }
        return trainingMenuList;
    }
	
	public List<SportMenu> findMenuByAdminId(String id) {
	    return authExmapper.findMenuByAdminId(id);
	}	
	
	public Integer save(SportAuthorization trainingAuthorization) {
	    return authMapper.insert(trainingAuthorization);
	}
	public Integer update(SportAuthorization trainingAuthorization) {
        return authMapper.updateByPrimaryKeySelective(trainingAuthorization);
    }
	public List<SportAuthorization> findByAdminId(String adminId) {
	    SportAuthorizationExample example = new SportAuthorizationExample();
	    SportAuthorizationExample.Criteria criteria = example.createCriteria();
	    criteria.andAdminIdEqualTo(adminId);
	    criteria.andStatEqualTo(DataStatus.ENABLED);
	    return authMapper.selectByExample(example);
	}
	
	public SportAuthorization findByMenuId(String menuId) {
	    SportAuthorizationExample example = new SportAuthorizationExample();
        SportAuthorizationExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(menuId)) {
            criteria.andMenuIdEqualTo(menuId);
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        List<SportAuthorization> results = authMapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results.get(0);
	}
	public SportAuthorization findByAdminIdAndMenuId(String adminId,String menuId) {
        SportAuthorizationExample example = new SportAuthorizationExample();
        SportAuthorizationExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(adminId)) {
            criteria.andAdminIdEqualTo(adminId);
        }
        if (StringUtils.isNotBlank(menuId)) {
            criteria.andMenuIdEqualTo(menuId);
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        List<SportAuthorization> results = authMapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results.get(0);
    }
	
	public SportAuthorization findByAdminMenuIds(String adminId,String menuId) {
	    SportAuthorizationExample example = new SportAuthorizationExample();
        SportAuthorizationExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(adminId)) {
            criteria.andAdminIdEqualTo(adminId);
        }
        if (StringUtils.isNotBlank(menuId)) {
            criteria.andMenuIdEqualTo(menuId);
        }        
        List<SportAuthorization> results = authMapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results.get(0);
	}
}
