package com.tianfang.train.service;

import java.math.BigDecimal;
import java.util.List;

import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.MineCollegeDto;
import com.tianfang.train.dto.OpenClassUserCourseDto;
import com.tianfang.train.dto.OpenClassUserCourseForPageDto;
import com.tianfang.train.dto.PayResultDto;
import com.tianfang.train.dto.RollBook;
import com.tianfang.train.dto.TrainingUserDto;
import com.tianfang.train.dto.UserCourseDto;

/**
 * 用户、课程关系接口
 * 
 * @author xiang_wang
 *
 * 2015年9月2日
 */
public interface IUserCourseService {

	/**
	 * 根据课程小节id查询用户课程关系信息
	 * 
	 * @param classId  training_course_class表主键id
	 * @param status	是否有效：1表示有效；0表示无效; -1表示全部
	 * @param isPayed	学费是否已支付：1表示已支付；0表示未支付; -1表示全部
	 * @return
	 */
	public List<UserCourseDto> findUserCourseByClassId(String classId, int status, int isPayed);
	
	/**
	 * 根据课程小节id生成点名册(如果当天点过名了,生成带状态的点名册)
	 * ps:如果今天已经点过名,客户来报名,点名册中不会出现今天报名客户的信息,但是以后都会有,如果是在点名前完成报名操作,点名册中会有客户信息
	 * @param classId
	 * @return
	 */
	public List<RollBook> findRollBookByClassId(String classId);
	
	/**
	 * 支付课程费用
	 * 
	 * @param id 		用户,课程关系主键id
	 * @param birthday 	孩子出生日期
	 * @param school	孩子原学习
	 * @return 0-支付失败,1-支付成功,2-登记孩子出生日期和孩子原学校
	 */
	public PayResultDto toPay(String id, String birthday, String school);
	
	/**
	 * 点名完成
	 * 
	 * @param json [{"name":"gg","orderId":1,"userId":1,"courseId":2,"classId":1,"type":3},{"name":"lucy","orderId":5,"userId":null,"courseId":1,"classId":1,"type":3}]
	 */
	public void insertCompleteRoll(String json);
	
	/**
	 * 报名课程
	 */
	UserCourseDto saveAndUpdateApplyCourse(UserCourseDto userCourseDto);
	
	
	/**
	 * 更新用户支付定金状态
	 * @param userCourseId
	 * @param totalFee
	 * @return
	 */
	Boolean updateUserDepositSuccess(String userCourseId,BigDecimal totalFee,String tradeno);

	/**
	 * 根据条件查询UserCourseDto列表
	 * @param para
	 * @return
	 */
	List<OpenClassUserCourseDto> findUserOpenClassInfoByOrderNo(UserCourseDto para);
	
	/**
	 * 根据第三方平台id查询我所参加的课程的考勤
	 * 
	 * @param openId
	 * @return
	 */
	List<MineCollegeDto> findMineCollegeByOpenId(String openId);
	
	/**
	 * 根据第三方平台id分页查询我所参加的课程的考勤
	 * 
	 * @param openId
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	PageResult<MineCollegeDto> findMineCollegeByOpenId(String openId, Integer currPage, Integer pageSize);
	
	/**
	 * 根据用户id查询我所参加的课程的考勤
	 * 
	 * @param userId
	 * @return
	 */
	List<MineCollegeDto> findMineCollegeByUserId(String userId);
	
	/**
	 * 微信用户登录：1，先查询该openId是否已存在，存在，则返回用户信息；不存在，新增并返回用户信息
	 * @param openId
	 * @return
	 * @2015年9月7日
	 */
	public TrainingUserDto weixinLogin(String openId);

	public UserCourseDto getUserCourseClassInfoByOrderNo(String orderno);

	public UserCourseDto getUserCourseClassInfoByOrderId(String orderId);

	/**
	 * --课程定金支付成功,更新折扣信息  网页V2.0报名,以及我的消息中支付定金  
	 * @param para
	 * @return
	 */
	public java.util.List<OpenClassUserCourseForPageDto> findUserOpenClassInfoPageByOrderNo(
			UserCourseDto para);
	
	/**
	 * 根据userID更新用户登录时间
	 * @param userID
	 * @return
	 * @2015年10月8日
	 */
	public boolean updateLoginTime(String userID);
}