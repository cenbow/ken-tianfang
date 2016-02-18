package com.tianfang.train.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lombok.Getter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.train.dto.MineCollegeDto;
import com.tianfang.train.dto.OpenClassUserCourseDto;
import com.tianfang.train.dto.OpenClassUserCourseForPageDto;
import com.tianfang.train.dto.TrainingUserDto;
import com.tianfang.train.dto.UserCourseDto;
import com.tianfang.train.mapper.UserCourseMapper;
import com.tianfang.train.mapper.XCourseClassMapper;
import com.tianfang.train.mapper.XTrainingUserMapper;
import com.tianfang.train.mapper.XUserCourseMapper;
import com.tianfang.train.pojo.TrainingUser;
import com.tianfang.train.pojo.UserCourse;
import com.tianfang.train.pojo.UserCourseExample;

@Repository
public class UserCourseDao extends MyBatisBaseDao<UserCourse> {

	@Autowired
	@Getter
	private UserCourseMapper mapper;
	@Autowired
	@Getter
	private XUserCourseMapper xmapper;
	@Autowired
	@Getter
	private XCourseClassMapper classMapper;
	@Resource
	private XTrainingUserMapper tuMapper;
	private Logger log = Logger.getLogger(getClass());
	
	/**
	 * 根据课程小节id查询订单信息
	 * 
	 * @param classId  training_course_class表主键id
	 * @param status	是否有效：1表示有效；0表示无效; -1表示全部
	 * @param isPayed	学费是否已支付：1表示已支付；0表示未支付; -1表示全部
	 * @return
	 */
	public List<UserCourse> findUserCourseByClassId(String classId, int status, int isPayed){
		
		UserCourseExample example = new UserCourseExample();
		UserCourseExample.Criteria criteria = example.createCriteria();
    	criteria.andClassIdEqualTo(classId);
    	if (status >= 0){
    		criteria.andStatusEqualTo(status);
    	}
    	if (isPayed >= 0){
    		criteria.andIsPayedEqualTo(0);
    	}
    	
    	List<UserCourse> results = mapper.selectByExample(example);
		
		return results;
	}
	
	public List<UserCourse> findUserCourseByClassId(String classId, Integer isPayed){
		
		UserCourseExample example = new UserCourseExample();
		UserCourseExample.Criteria criteria = example.createCriteria();
    	criteria.andClassIdEqualTo(classId);
    	criteria.andIsPayedEqualTo(1);
    	criteria.andStatusEqualTo(1);
    	List<UserCourse> results = mapper.selectByExample(example);
		
		return results;
	}
	
	public UserCourse getUserCourseById(String id){
		UserCourse entity = mapper.selectByPrimaryKey(id);
		
		return entity;
	}
	public Integer applyCourse(UserCourse userCourse) {
		
		
		return xmapper.applyCourse(userCourse);
		
	}
	
	public UserCourseDto saveAndUpdateApplyCourse(UserCourseDto dto) {
//		UserCourse userCourse = BeanUtils.createBeanByTarget(dto, UserCourse.class);
		dto.setId(UUIDGenerator.getUUID());
		xmapper.applyCourse(dto);
//		if(reInt>0){
//			dto.setId(userCourse.getId());
//		}		
		return dto;
	}
	
	public void addOneActualStudent(String id){
		classMapper.addOneActualStudent(id);

	}
	
	/**
	 * @author YIn
	 * @time:2015年9月11日 上午11:28:09
	 */
	public void addActualStudent(String courseId) {
		classMapper.addActualStudent(courseId);
		
	}
	
	/**
	 * 用户定金支付成功
	 * @param userCourseId
	 */
	public Boolean  updateUserDepositSuccess(String userCourseId,BigDecimal totalFee,String tradeno){
		Boolean re = false;
		UserCourse userCourse = new UserCourse();
		userCourse.setId(userCourseId);
		userCourse.setTotoFee(totalFee);
		userCourse.setDepositIspayed(1);
		userCourse.setUpdateTime(new Date().getTime()/1000);
		userCourse.setTradeNo(tradeno);
		int row = mapper.updateByPrimaryKeySelective(userCourse);
		if(row>0){
			re = true;
		}
		return re;
	}

	public List<OpenClassUserCourseDto> findUserOpenClassInfoByOrderNo(UserCourseDto para) {
//		List<UserCourseDto> reList = new ArrayList<UserCourseDto>();
//		
//		UserCourseExample example = new UserCourseExample();
//		UserCourseExample.Criteria criteria = example.createCriteria();
//		if(null != para.getId()){
//			criteria.andIdEqualTo(para.getId());
//		}
//		List<UserCourse> userCourseList = mapper.selectByExample(example);
		List<OpenClassUserCourseDto> dtos = xmapper.findUserOpenClassInfoByOrderNo(para.getOrderNo());
//		if(userCourseList != null & userCourseList.size()>0){
//			reList = BeanUtils.createBeanListByTarget(userCourseList, UserCourseDto.class);
//		}
		return dtos;
	}
	
	public void updateUsedTime(String classId){
		xmapper.updateUsedTime(classId);
	}
	

	public List<MineCollegeDto> findMineCollegeByOpenId(String openId){
		return xmapper.findMineCollegeByOpenId(openId);
	}
	
	public List<MineCollegeDto> findMineCollegeByOpenId(String openId, PageQuery page){
		Map<String, Object> param = new HashMap<String, Object>(3);
		param.put("openId", openId);
		param.put("start", page.getStartNum());
		param.put("count", page.getPageSize());
		
		return xmapper.findPageMineCollegeByOpenId(param);
	}
	
	public int countMineCollegeByOpenId(String openId){
		return xmapper.countMineCollegeByOpenId(openId);
	}
	
	public List<MineCollegeDto> findMineCollegeByUserId(String userId){
		return xmapper.findMineCollegeByUserId(userId);
	}
	
	/**
	 * 根据openId查询微信用户
	 * @param openId
	 * @return
	 * @2015年9月7日
	 */
	public TrainingUserDto getWXUser(String openId)
	{
		TrainingUserDto tudto = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>(1);
			map.put("openId", openId);
			tudto = tuMapper.getWXLogin(map);
		} catch (Exception e) {
			log.error("根据openId查询微信用户信息发生异常", e);
		}
		return tudto;
	}
	
	/**
	 * 添加用户信息
	 * @param tu
	 * @return
	 * @2015年9月7日
	 */
	public Boolean addUser(TrainingUser tu){
		boolean flag = false;
		try {
			Integer count = tuMapper.addUser(tu);
			if(count == 1){
				flag = true;
			}
		} catch (Exception e) {
			log.error("新增用户信息发生异常", e);
		}
		return flag;
	}

	public UserCourseDto getUserCourseClassInfoByOrderNo(String orderno) {
		return xmapper.getUserCourseClassInfoByOrderNo(orderno);
	}

	public UserCourseDto getUserCourseClassInfoByOrderId(String orderId) {
		return  xmapper.getUserCourseClassInfoById(orderId);
	}

	/**
	 * --课程定金支付成功,更新折扣信息  网页V2.0报名,以及我的消息中支付定金  
	 * @param para
	 * @return
	 */
	public List<OpenClassUserCourseForPageDto> findUserOpenClassInfoPageByOrderNo(
			UserCourseDto para) {
		List<OpenClassUserCourseForPageDto> dtos = xmapper.findUserOpenClassInfoPageByOrderNo(para.getOrderNo());

		return dtos;
	}
}