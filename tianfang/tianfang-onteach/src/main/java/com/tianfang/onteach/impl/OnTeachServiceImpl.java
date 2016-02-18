package com.tianfang.onteach.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.onteach.dao.OnTeachDao;
import com.tianfang.onteach.dto.OnTeachDto;
import com.tianfang.onteach.pojo.SportOnlineTeach;
import com.tianfang.onteach.service.IOnTeachService;

@Service
public class OnTeachServiceImpl implements IOnTeachService{

	@Autowired
	private OnTeachDao onTeachDao;
	
	@Override
	public PageResult<OnTeachDto> findOnTeachByParams(OnTeachDto params,
			int currPage, int pageSize) {
		int total = onTeachDao.countByParams(params);
		PageQuery page = new PageQuery(total, currPage, pageSize);
		List<SportOnlineTeach> dataList = onTeachDao.findOnTeachByParams(params, page);
		
		List<OnTeachDto> resultList = BeanUtils.createBeanListByTarget(dataList, OnTeachDto.class);
		PageResult<OnTeachDto> result = new PageResult<OnTeachDto>(page, resultList);
		return result;
	}

	@Override
	public void insert(OnTeachDto params) {
		SportOnlineTeach record = BeanUtils.createBeanByTarget(params, SportOnlineTeach.class);
		onTeachDao.insertSelective(record);
	}

	@Override
	public void update(OnTeachDto params) {
		SportOnlineTeach record = BeanUtils.createBeanByTarget(params, SportOnlineTeach.class);
		onTeachDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public OnTeachDto findObjectById(String id) {
		SportOnlineTeach onlineTeach = onTeachDao.selectByPrimaryKey(id);
		if(onlineTeach==null){
			return null;
		}
		OnTeachDto record = BeanUtils.createBeanByTarget(onlineTeach, OnTeachDto.class);
		return record;
	}

	@Override
	public void updateClickCount(String oId) {
		// TODO Auto-generated method stub
		onTeachDao.updateClickCount(oId);
	}

	@Override
	public void updateStatus(String ids) {
        String[] idStr = ids.split(",");
        if (idStr.length>0) {
            for (String id : idStr) {
            	SportOnlineTeach onlineTeach = onTeachDao.selectByPrimaryKey(id);
            	onlineTeach.setStat(DataStatus.DISABLED);
            	onTeachDao.updateByPrimaryKeySelective(onlineTeach);
            }
        } else {
        	SportOnlineTeach onlineTeach = onTeachDao.selectByPrimaryKey(ids);
        	onlineTeach.setStat(DataStatus.DISABLED);
        	onTeachDao.updateByPrimaryKeySelective(onlineTeach);
        }
    }

	@Override
	public void release(String id, Integer releaseStat) {   
		SportOnlineTeach onlineTeach = onTeachDao.selectByPrimaryKey(id);
        if (null != onlineTeach) {
        	onlineTeach.setStat(releaseStat);
        	onTeachDao.updateByPrimaryKeySelective(onlineTeach);
        }
    }
	
	@Override
	public PageResult<OnTeachDto> findOnTeachcolumnNameByParams(int currPage, int pageSize) {
		int total = onTeachDao.countcolumnNameByParams();
		PageQuery page = new PageQuery(total, currPage, pageSize);
		List<SportOnlineTeach> dataList = onTeachDao.findOnTeachcolumnNameByParams(page);
		
		List<OnTeachDto> resultList = BeanUtils.createBeanListByTarget(dataList, OnTeachDto.class);
		PageResult<OnTeachDto> result = new PageResult<OnTeachDto>(page, resultList);
		return result;
	}
	
}
