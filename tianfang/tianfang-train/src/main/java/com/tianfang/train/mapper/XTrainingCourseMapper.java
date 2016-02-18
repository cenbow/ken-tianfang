/**
 * 
 */
package com.tianfang.train.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tianfang.train.dto.TrainingCourseDtoX;

/**
 * 
 * @author wk.s
 * @date 2015年9月2日
 */
public interface XTrainingCourseMapper {
    
    /**
     * 查询可报名课程列表
     * @param map
     * <br>ava=1表示查询可报名，ava=0表示查询所有
     * @return
     * @2015年9月5日
     */
    List<TrainingCourseDtoX> getACourseLst(Map<String, Object> map);
    
    /**
     * 通过courseId查询课程详情
     * @param map
     * @return
     * @2015年9月2日
     */
    TrainingCourseDtoX getCourseById(Map<String, Object> map);
    
    TrainingCourseDtoX getCourse(Map<String, Object> map);

	TrainingCourseDtoX findUserCourceBy(@Param("classId") String classId);
}
