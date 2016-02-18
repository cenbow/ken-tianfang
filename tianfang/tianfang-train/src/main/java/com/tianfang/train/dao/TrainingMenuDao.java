package com.tianfang.train.dao;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.TrainingMenuRespDto;
import com.tianfang.train.mapper.TrainingAuthorizationExMapper;
import com.tianfang.train.mapper.TrainingAuthorizationMapper;
import com.tianfang.train.mapper.TrainingMenuMapper;
import com.tianfang.train.pojo.TrainingAuthorization;
import com.tianfang.train.pojo.TrainingAuthorizationExample;
import com.tianfang.train.pojo.TrainingMenu;
import com.tianfang.train.pojo.TrainingMenuExample;

@Repository
public class TrainingMenuDao extends MyBatisBaseDao<TrainingMenu>{

	@Autowired
	@Getter
	private TrainingMenuMapper mapper;
	
	@Autowired
	@Getter
	private TrainingAuthorizationMapper authMapper;
	
	@Autowired
    @Getter
    private TrainingAuthorizationExMapper authExmapper;
	

	public List<TrainingMenuRespDto> findAll() {
	    TrainingMenuExample example = new TrainingMenuExample();
	    TrainingMenuExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(DataStatus.ENABLED);
		example.setOrderByClause(" orderby asc ");
		List<TrainingMenu>  trainingMenuList = mapper.selectByExample(example);
		List<TrainingMenuRespDto> trainingMenuRespDTOs = new ArrayList<TrainingMenuRespDto>();
        if (trainingMenuList.size()>0) {
            trainingMenuRespDTOs= BeanUtils.createBeanListByTarget(trainingMenuList, TrainingMenuRespDto.class);
            return trainingMenuRespDTOs;
        }
		return trainingMenuRespDTOs;
	} 
	
	public List<TrainingMenu> findByleftMenu(String leftMenu) {
	    TrainingMenuExample example = new TrainingMenuExample();
        TrainingMenuExample.Criteria criteria = example.createCriteria();
        criteria.andLeftMenuEqualTo(leftMenu);
        criteria.andStatusEqualTo(DataStatus.ENABLED);
        example.setOrderByClause(" orderby asc ");
        List<TrainingMenu>  trainingMenuList = mapper.selectByExample(example);
//        List<TrainingMenuRespDto> trainingMenuRespDTOs = new ArrayList<TrainingMenuRespDto>();
//        if (trainingMenuList.size()>0) {
//            trainingMenuRespDTOs= BeanUtils.createBeanListByTarget(trainingMenuList, TrainingMenuRespDto.class);
//            return trainingMenuRespDTOs;
//        }
        return trainingMenuList;
	}
	
	public List<TrainingMenu> findByleaf(String leaf) {
        TrainingMenuExample example = new TrainingMenuExample();
        TrainingMenuExample.Criteria criteria = example.createCriteria();
        criteria.andLeafEqualTo(leaf);
        criteria.andStatusEqualTo(DataStatus.ENABLED);
        example.setOrderByClause(" orderby asc ");
        List<TrainingMenu>  trainingMenuList = mapper.selectByExample(example);
//        List<TrainingMenuRespDto> trainingMenuRespDTOs = new ArrayList<TrainingMenuRespDto>();
//        if (trainingMenuList.size()>0) {
//            trainingMenuRespDTOs= BeanUtils.createBeanListByTarget(trainingMenuList, TrainingMenuRespDto.class);
//            return trainingMenuRespDTOs;
//        }
        return trainingMenuList;
    }
    
	public List<TrainingMenu> findByParentId(String ParentId) {
        TrainingMenuExample example = new TrainingMenuExample();
        TrainingMenuExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(ParentId);
        criteria.andStatusEqualTo(DataStatus.ENABLED);
        example.setOrderByClause(" orderby asc ");
        List<TrainingMenu>  trainingMenuList = mapper.selectByExample(example);
//        List<TrainingMenuRespDto> trainingMenuRespDTOs = new ArrayList<TrainingMenuRespDto>();
//        if (trainingMenuList.size()>0) {
//            trainingMenuRespDTOs= BeanUtils.createBeanListByTarget(trainingMenuList, TrainingMenuRespDto.class);
//            return trainingMenuRespDTOs;
//        }
        return trainingMenuList;
    }
	
	public List<TrainingMenu> findTrainingMenuByAdminId(String id) {
	    return authExmapper.findTrainingMenuByAdminId(id);
	}	
	
	public Integer save(TrainingAuthorization trainingAuthorization) {
	    return authMapper.insert(trainingAuthorization);
	}
	public Integer update(TrainingAuthorization trainingAuthorization) {
        return authMapper.updateByPrimaryKeySelective(trainingAuthorization);
    }
	public List<TrainingAuthorization> findByAdminId(String adminId) {
	    TrainingAuthorizationExample example = new TrainingAuthorizationExample();
	    TrainingAuthorizationExample.Criteria criteria = example.createCriteria();
	    criteria.andAdminIdEqualTo(adminId);
	    criteria.andStatusEqualTo(DataStatus.ENABLED);
	    return authMapper.selectByExample(example);
	}
	
	public TrainingAuthorization findByMenuId(String menuId) {
	    TrainingAuthorizationExample example = new TrainingAuthorizationExample();
        TrainingAuthorizationExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(menuId)) {
            criteria.andMenuIdEqualTo(menuId);
        }
        criteria.andStatusEqualTo(DataStatus.ENABLED);
        List<TrainingAuthorization> results = authMapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results.get(0);
	}
	public TrainingAuthorization findByAdminIdAndMenuId(String adminId,String menuId) {
        TrainingAuthorizationExample example = new TrainingAuthorizationExample();
        TrainingAuthorizationExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(adminId)) {
            criteria.andAdminIdEqualTo(adminId);
        }
        if (StringUtils.isNotBlank(menuId)) {
            criteria.andMenuIdEqualTo(menuId);
        }
        criteria.andStatusEqualTo(DataStatus.ENABLED);
        List<TrainingAuthorization> results = authMapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results.get(0);
    }
	
	public TrainingAuthorization findByAdminMenuIds(String adminId,String menuId) {
	    TrainingAuthorizationExample example = new TrainingAuthorizationExample();
        TrainingAuthorizationExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(adminId)) {
            criteria.andAdminIdEqualTo(adminId);
        }
        if (StringUtils.isNotBlank(menuId)) {
            criteria.andMenuIdEqualTo(menuId);
        }        
        List<TrainingAuthorization> results = authMapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results.get(0);
	}
}
