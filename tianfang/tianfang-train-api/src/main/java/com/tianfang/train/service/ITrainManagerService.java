package com.tianfang.train.service;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.TrainInfoDto;

public interface ITrainManagerService {

	PageResult<TrainInfoDto> findByPage(TrainInfoDto trainInfoDto, PageQuery page);

	void save(TrainInfoDto trainInfoDto);

	void deleteById(String id);

	void update(TrainInfoDto trainInfoDto);

	int updateContext(TrainInfoDto trainInfoDto);

}
