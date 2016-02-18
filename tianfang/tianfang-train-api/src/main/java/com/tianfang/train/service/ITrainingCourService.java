/**
 * 
 */
package com.tianfang.train.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.CollegelessonDetailDto;
import com.tianfang.train.dto.CollegelessonDto;
import com.tianfang.train.dto.CourseClassDtoX;
import com.tianfang.train.dto.CourseTagDto;
import com.tianfang.train.dto.TrainingCourseDto;
import com.tianfang.train.dto.TrainingCourseDtoX;
import com.tianfang.train.dto.TrainingCourseReqDto;
import com.tianfang.train.dto.TrainingDistrictDtoX;
import com.tianfang.train.dto.TrainingSpaceManagerDto;

/**
 * 课程相关
 * 
 * @author wk.s
 * @date 2015年9月1日
 */
public interface ITrainingCourService {

	/**
	 * 
		 * 此方法描述的是：学院-->课程介绍 培训课程数据展示
		 * @author: cwftalus@163.com
		 * @version: 2015年10月10日 上午9:29:22
	 */
	public PageResult<CollegelessonDto> findByPage(PageQuery page,Integer marked);
	
	/***
	 * 分页查看课程信息
	 * 
	 * @param trainingCourseDto
	 * @param page
	 * @return
	 */
	public PageResult<TrainingCourseDtoX> findByPage(
			TrainingCourseDtoX trainingCourseDto, PageQuery page);

	/***
	 * 下拉框查询所有课程标签
	 * 
	 * @param courseTagDto
	 * @return
	 */
	public List<CourseTagDto> findCourseTag(CourseTagDto courseTagDto);

	/**
	 * 获取可报名课程列表
	 * 
	 * @param ava
	 * <br>
	 * ava=1表示只查询报名未满的课程,ava=0表示查询所有
	 * @param marked
	 * <br>
	 * marked=1表示加精，0表示不加精
	 * @return
	 * @2015年9月5日
	 */
	public List<TrainingCourseDto> getACourseLst(String ava, String marked);

	/**
	 * 通过课程Id查询课程详情
	 * 
	 * @param courseId
	 * @return
	 * @2015年9月2日
	 */
	public TrainingCourseDtoX getTrainCourse(String id,String courseClassId);
	
	public TrainingCourseDtoX getCourseDet(String courseId);

	/**
	 * 查询所有有效的区域（比如普陀区、徐汇区）
	 * 
	 * @return
	 * @2015年9月2日
	 */
	public List<TrainingDistrictDtoX> getADistrict();

	/**
	 * 通过courseId、districtId查询可报名的课程（小节）
	 * 
	 * @return
	 * @2015年9月2日
	 */
	public List<CourseClassDtoX> getClassByCDId(String courseId, String districtId);
	
	/**
	 * 根据条件查询课程小节信息，
	 * 并根据小节上课所在地进行分组
	 * @param courseId
	 * @param districtId
	 * @return
	 * @2015年10月11日
	 */
	public List<CourseClassDtoX> getClassGroup(String courseId, String districtId);
	
	/**
	 * 根据courseId和addressId查询课程小节信息
	 * 
	 * @param courseId
	 * @param districtId
	 * @return
	 * @2015年10月11日
	 */
	public List<CourseClassDtoX> getClassList(String courseId, String addressId);
	
	/**
	 * 通过courseId和classId查询课程（小节）
	 * 
	 * @param couserId
	 * @param addressId
	 * @return
	 * @2015年9月5日
	 */
	public List<CourseClassDtoX> getClassByCAId(String couserId, String addressId);

	/**
	 * 根据条件更新订单信息
	 * 
	 * @param orderNo
	 * @param tradeNo
	 * @param status
	 * @return
	 * @2015年9月5日
	 */
	public boolean updateOrder(String orderNo, String tradeNo, Integer status);
    /**
     * 根据课程查询对应的场地
     * @author YIn
     * @time:2015年9月6日 下午5:38:16
     * @param courseId
     * @return
     */
	public List<TrainingSpaceManagerDto> findSpaceByCourseId(String courseId);

	/***
	 * 删除课程信息
	 * 
	 * @param id
	 * @return
	 */
	public Object deleteCourseInfo(String id,String courseClassIds);
	
	/**
	 * 通过课程Id查询课程（小节）列表
	 * @param courseId
	 * @return
	 * @2015年9月7日
	 */
	public List<CourseClassDtoX> getClassesByCId(String courseId);
	
//	public TrainingCourseReqDto save(TrainingCourseReqDto trainingCourseDtoX, Integer addressId,String timeDistrictIds) throws Exception ;
	
	public TrainingCourseReqDto edit(TrainingCourseReqDto trainingCourseDtoX,  String jsonClasss) throws Exception ;

	public List<TrainingCourseDto> findAllCourse();  


    
    /**     
     * save：一句话描述方法功能
     * @param trainingCourseDtoX
     * @param addressId
     * @param timeDistrictIds
     * @param courseClassReqDto
     * @return
     * @throws Exception
     * @exception	
     * @author Administrator
     * @date 2015年9月11日 上午11:18:07	 
     */
    public TrainingCourseReqDto save(TrainingCourseReqDto trainingCourseDtoX, String jsonClasss) throws Exception;

    public TrainingCourseDtoX getTrainingCourse(String courseId);
    
    /**
     * 网页版,课程介绍详情信息
     * @author xiang_wang
     * 2015年10月11日下午3:01:54
     */
    public CollegelessonDetailDto getLessonDetail(String courseId, String addressId) throws Exception;

    /**
     * 
    	 * 此方法描述的是：
    	 * @author: cwftalus@163.com
    	 * @version: 2015年10月14日 下午3:09:38
     */
	public TrainingCourseDtoX findUserCourceBy(String classId);
}
