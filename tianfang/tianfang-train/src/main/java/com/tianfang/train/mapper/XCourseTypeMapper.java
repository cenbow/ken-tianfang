package com.tianfang.train.mapper;

import java.util.Map;

import com.tianfang.train.dto.CourseTypeDtoX;

public interface XCourseTypeMapper {    
    /**
     * 通过ID查询课程类型信息
     * @return
     * @2015年9月2日
     */
    CourseTypeDtoX getCTByID(Map<String, Object> map);
}
