package com.tianfang.train.mapper;

import java.util.List;

import com.tianfang.train.dto.TrainingDistrictDtoX;

public interface XTrainingDistrictMapper {
    /**
     * 获取所有有效区域，比如普陀区、徐汇区
     * @return
     * @2015年9月2日
     */
    List<TrainingDistrictDtoX> getADistrict();
}