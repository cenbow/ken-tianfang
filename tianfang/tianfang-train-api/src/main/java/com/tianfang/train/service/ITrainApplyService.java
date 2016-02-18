package com.tianfang.train.service;

import org.springframework.stereotype.Service;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.TrainApplyDto;

@Service
public interface ITrainApplyService {

	PageResult<TrainApplyDto> findByPage(TrainApplyDto trainApplyDto,
			PageQuery changeToPageQuery);
	
	PageResult<TrainApplyDto> findApplyByPage(TrainApplyDto trainApplyDto,
			PageQuery changeToPageQuery);
	
	void save(TrainApplyDto trainApplyDto);

	int update(TrainApplyDto trainApplyDto);

	Integer delByIds(String ids);

	TrainApplyDto getApplyById(String id);

}
