package com.tianfang.train.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.train.dao.TrainInfoDao;
import com.tianfang.train.dto.TrainInfoDto;
import com.tianfang.train.pojo.Training;
import com.tianfang.train.service.ITrainManagerService;

@Service
public class TrainManagerServiceImpl implements ITrainManagerService {
 
	@Autowired
	private TrainInfoDao trainInfoDao;
	
	
	@Override
	public PageResult<TrainInfoDto> findByPage(TrainInfoDto trainInfoDto, PageQuery page) {
		List<Training> results = trainInfoDao.findByPage(trainInfoDto, page);
		
		List<TrainInfoDto> dtoResults = new ArrayList<TrainInfoDto>();
		for(Training res : results){
			TrainInfoDto trainDto = BeanUtils.createBeanByTarget(res, TrainInfoDto.class);
			trainDto.setReleaseTime(DateUtils.format(res.getReleaseTime(), "yyyy-MM-dd"));
			dtoResults.add(trainDto);
		}

		long total = trainInfoDao.count(trainInfoDto);
		page.setTotal(total);
		return new PageResult<TrainInfoDto>(page, dtoResults);
	}

	@Override
	public void save(TrainInfoDto trainInfoDto) {
		 
		 Training training = BeanUtils.createBeanByTarget(trainInfoDto, Training.class);
		 training.setReleaseTime(DateUtils.parse(trainInfoDto.getReleaseTime(), "yyyy-MM-dd"));
//		 training.setCreateTime(new Date());
		 trainInfoDao.insert(training);
	}

	@Override
	public void deleteById(String id) {
		
		trainInfoDao.logicDeleteById(id);
	}

	@Override
	public void update(TrainInfoDto trainInfoDto) {
		Training training = BeanUtils.createBeanByTarget(trainInfoDto, Training.class);
		trainInfoDao.updateByPrimaryKeySelective(training);
	}

	@Override
	public int updateContext(TrainInfoDto trainInfoDto) {
		Training training = BeanUtils.createBeanByTarget(trainInfoDto, Training.class);
		return trainInfoDao.updateByPrimaryKeySelective(training);
	}

}
