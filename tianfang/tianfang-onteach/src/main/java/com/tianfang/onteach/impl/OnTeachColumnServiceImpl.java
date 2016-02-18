package com.tianfang.onteach.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.onteach.dao.OnTeachColumnDao;
import com.tianfang.onteach.dto.OnlineTeachColumnDto;
import com.tianfang.onteach.pojo.SportOnlineTeachColumn;
import com.tianfang.onteach.service.IOnTeachColumnService;

@Service
public class OnTeachColumnServiceImpl implements IOnTeachColumnService{

	@Autowired
	private OnTeachColumnDao onTeachColumnDao;
	
	@Override
	public PageResult<OnlineTeachColumnDto> findOnTeachByParams(OnlineTeachColumnDto params,
			int currPage, int pageSize) {
		int total = onTeachColumnDao.countByParams(params);
		PageQuery page = new PageQuery(total, currPage, pageSize);
		List<SportOnlineTeachColumn> dataList = onTeachColumnDao.findOnTeachByParams(params, page);
		
		List<OnlineTeachColumnDto> resultList = BeanUtils.createBeanListByTarget(dataList, OnlineTeachColumnDto.class);
		PageResult<OnlineTeachColumnDto> result = new PageResult<OnlineTeachColumnDto>(page, resultList);
		return result;
	}

	@Override
	public void insert(OnlineTeachColumnDto params) {
		SportOnlineTeachColumn record = BeanUtils.createBeanByTarget(params, SportOnlineTeachColumn.class);
		onTeachColumnDao.insertSelective(record);
	}

	@Override
	public void update(OnlineTeachColumnDto params) {
		SportOnlineTeachColumn record = BeanUtils.createBeanByTarget(params, SportOnlineTeachColumn.class);
		onTeachColumnDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public OnlineTeachColumnDto findObjectById(String id) {
		SportOnlineTeachColumn onlineTeach = onTeachColumnDao.selectByPrimaryKey(id);
		if(onlineTeach==null){
			return null;
		}
		OnlineTeachColumnDto record = BeanUtils.createBeanByTarget(onlineTeach, OnlineTeachColumnDto.class);
		return record;
	}

	@Override
	public void updateStatus(String ids) {
        String[] idStr = ids.split(",");
        if (idStr.length>0) {
            for (String id : idStr) {
            	SportOnlineTeachColumn onlineTeach = onTeachColumnDao.selectByPrimaryKey(id);
            	onlineTeach.setStat(DataStatus.DISABLED);
            	onTeachColumnDao.updateByPrimaryKeySelective(onlineTeach);
            }
        } else {
        	SportOnlineTeachColumn onlineTeach = onTeachColumnDao.selectByPrimaryKey(ids);
        	onlineTeach.setStat(DataStatus.DISABLED);
        	onTeachColumnDao.updateByPrimaryKeySelective(onlineTeach);
        }
    }

	@Override
	public void release(String id, Integer releaseStat) {   
		SportOnlineTeachColumn onlineTeach = onTeachColumnDao.selectByPrimaryKey(id);
        if (null != onlineTeach) {
        	onlineTeach.setStat(releaseStat);
        	onTeachColumnDao.updateByPrimaryKeySelective(onlineTeach);
        }
    }

	@Override
	public List<OnlineTeachColumnDto> findColumnsBy(OnlineTeachColumnDto dto) {
		List<SportOnlineTeachColumn> dataList = onTeachColumnDao.findColumnsBy(dto);
		List<OnlineTeachColumnDto> resultList = BeanUtils.createBeanListByTarget(dataList, OnlineTeachColumnDto.class);
		return resultList;
	}
	
}
