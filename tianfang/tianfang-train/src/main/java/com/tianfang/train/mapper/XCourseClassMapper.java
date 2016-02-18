package com.tianfang.train.mapper;

import java.util.List;
import java.util.Map;

import com.tianfang.train.dto.CollegelessonClassDto;
import com.tianfang.train.dto.CourseClassAdressTimeDto;
import com.tianfang.train.dto.CourseClassDtoX;

public interface XCourseClassMapper {    
    /**
     * 通过课程Id：courseId、区域Id：districtId查询可报名的地点
     * @param map
     * @return
     * @2015年9月2日
     */
    List<CourseClassDtoX> getClassByCDId(Map<String, Object> map);

    /**
     * 报名学生人数Actual_Student加1
     * @param classId
     */
	void addOneActualStudent(String classId);
	
	/**
	 * 
	 * @author YIn
	 * @time:2015年9月11日 上午11:31:55
	 * @param courseId
	 */
	void addActualStudent(String courseId);
	
	/**
	 * 根据课程ID查询出该课程,且课程报名人数未满
	 * @param id
	 * @return
	 */
	List<CourseClassDtoX> findAvailableCourseClassByClassId(String id);

	/**
	 * 获取报名未满的class的course  address time 信息
	 * @param id
	 * @return
	 */
	CourseClassAdressTimeDto getAvailableCourseClassAdressTimeInfoByClassid(String id);
	
	/**
	 * 通过courseId和classId查询课程（小节）
	 * @param map
	 * @return
	 * @2015年9月5日
	 */
	List<CourseClassDtoX> getClassByCAId(Map<String, Object> map);
	
	/**
	 * 获取指定课程的总价
	 * @param map
	 * @return
	 * @2015年9月7日
	 */
	CourseClassDtoX getSumPrice(Map<String, Object> map);
	
	/**
	 * 通过courseId查询课程（小节）列表
	 * @param map
	 * @return
	 * @2015年9月7日
	 */
	List<CourseClassDtoX> getClassesByCId(Map<String, Object> map);
	
	/**
	 * 通过classId查询课程（小节）详情
	 * @param classId
	 * @return
	 * @2015年9月7日
	 */
	CourseClassDtoX getCCDet(Map<String, Object> map);
	
	/**
	 * 根据条件查询课程小节信息，
	 * 并根据小节上课所在地进行分组
	 * @param courseId
	 * @param districtId
	 * @return
	 * @2015年10月11日
	 */
	List<CourseClassDtoX> getClassByCDGroupByAID(Map<String, Object> map);
	
	/**
	 * 根据courseId和addressId查询小节信息
	 * @param map
	 * @return
	 * @2015年10月11日
	 */
	List<CourseClassDtoX> getClassByAddressID(Map<String, Object> map);
	
	/**
	 * 根据参数查询课程(小节)信息
	 * @param param(courseId:课程Id, addressId:地区id)
	 * @author xiang_wang
	 * 2015年10月11日下午3:15:49
	 */
	List<CollegelessonClassDto> queryLessonClass(Map<String, Object> param);
	
	/**
	 * 根据条件查询课程在某一地点的最早、最迟开课时间
	 * @param param
	 * @return
	 * @2015年10月12日
	 */
	CourseClassDtoX getMinMaxOpenDate(Map<String, Object> param);
}