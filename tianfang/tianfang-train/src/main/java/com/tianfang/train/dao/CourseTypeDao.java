package com.tianfang.train.dao;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.tianfang.train.dto.CourseTypeDtoX;
import com.tianfang.train.mapper.XCourseTypeMapper;

/**
 * training_course_type dao
 * 
 * @author wk.s
 * @date 2015年9月1日
 */
@Repository
public class CourseTypeDao {

	@Resource
	private XCourseTypeMapper mapper;
	private Logger log = Logger.getLogger(getClass());
	
	/**
	 * 根据id查询课程类型数据
	 * @param id
	 * @return
	 * @2015年9月1日
	 */
	public CourseTypeDtoX getCTByID(Integer id){
		CourseTypeDtoX ct = null;
		Map<String, Object> map = new HashMap<String, Object>(1);
		try {
			map.put("id", id);
			ct = mapper.getCTByID(map);
		} catch (Exception e) {
			log.error("查询课程类型发生异常", e);
		}
		return ct;
	}
}
