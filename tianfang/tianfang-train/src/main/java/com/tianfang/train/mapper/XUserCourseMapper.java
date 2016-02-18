package com.tianfang.train.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tianfang.train.dto.MineCollegeDto;
import com.tianfang.train.dto.OpenClassUserCourseDto;
import com.tianfang.train.dto.OpenClassUserCourseForPageDto;
import com.tianfang.train.dto.UserCourseDto;
import com.tianfang.train.dto.UserCourseMessageDto;
import com.tianfang.train.pojo.UserCourse;

public interface XUserCourseMapper {

	
	Integer applyCourse(UserCourse userCourse);
	
	/**
	 * 更新订单中已使用课时和未使用课时
	 * 
	 * @param classId
	 */
	void updateUsedTime(@Param("classId")String classId);
	
	/**
	 * 根据第三方平台id查询.该用户所参加的课程的考勤
	 * 
	 * @param openId
	 * @return
	 */
	List<MineCollegeDto> findMineCollegeByOpenId(@Param("openId")String openId);

	List<MineCollegeDto> findMineCollegeByUserId(@Param("userId")String userId);
	
	/**
	 * 根据第三方平台id分页查询.该用户所参加的课程的考勤
	 * @author xiang_wang
	 * 2015年10月10日下午4:27:42
	 */
	List<MineCollegeDto> findPageMineCollegeByOpenId(Map<String, Object> param);
	
	/**
	 * 查询第三方平台id查询.该用户所参加的课程数目
	 * @author xiang_wang
	 * 2015年10月10日下午4:42:42
	 */
	int countMineCollegeByOpenId(@Param("openId")String openId);
	
	List<OpenClassUserCourseDto> findUserOpenClassInfo(String id);

	/**
	 * 根据订单号查询用户课程信息
	 * @param orderno
	 * @return
	 */
	UserCourseDto getUserCourseClassInfoByOrderNo(String orderno);

	List<OpenClassUserCourseDto> findUserOpenClassInfoByOrderNo(String orderNo);

	Integer applyCourse(UserCourseDto dto);

	/**
	 * 根据Id查询用户课程信息
	 * @param orderId
	 * @return
	 */
	UserCourseDto getUserCourseClassInfoById(String orderId);

	/**
	 * 分页查询用户课程消息
	 * @param example
	 * @return
	 */
	List<UserCourseMessageDto> findCourseMessage(Map<String, Object> map);
	/**
	 * --课程定金支付成功,更新折扣信息  网页V2.0报名,以及我的消息中支付定金  
	 * @param orderNo
	 * @return
	 */
	List<OpenClassUserCourseForPageDto> findUserOpenClassInfoPageByOrderNo(
			String orderNo);
}