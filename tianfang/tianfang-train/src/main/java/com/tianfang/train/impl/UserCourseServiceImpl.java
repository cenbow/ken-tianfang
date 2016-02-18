package com.tianfang.train.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.JsonUtil;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.train.dao.CourseClassDao;
import com.tianfang.train.dao.TrainingCourseDao;
import com.tianfang.train.dao.TrainingUserDao;
import com.tianfang.train.dao.UserCourseDao;
import com.tianfang.train.dao.UserCourseDayDao;
import com.tianfang.train.dto.CourseClassDtoX;
import com.tianfang.train.dto.MineCollegeDto;
import com.tianfang.train.dto.OpenClassUserCourseDto;
import com.tianfang.train.dto.OpenClassUserCourseForPageDto;
import com.tianfang.train.dto.PayResultDto;
import com.tianfang.train.dto.RollBook;
import com.tianfang.train.dto.TrainingUserDto;
import com.tianfang.train.dto.UserCourseDto;
import com.tianfang.train.pojo.TrainingUser;
import com.tianfang.train.pojo.UserCourse;
import com.tianfang.train.pojo.UserCourseDay;
import com.tianfang.train.service.IUserCourseService;

@Service
public class UserCourseServiceImpl implements IUserCourseService {

	@Autowired
	private UserCourseDao userCourseDao;
	@Autowired
	private TrainingUserDao userDao;
	@Autowired
	private CourseClassDao courseClassDao;
	@Autowired
	private UserCourseDayDao dayDao;
	@Autowired
	private TrainingCourseDao courseDao;
	
	@Override
	public List<UserCourseDto> findUserCourseByClassId(String classId, int status, int isPayed) {

		checkExceptionForClassId(classId);
		
		List<UserCourse> list = userCourseDao.findUserCourseByClassId(classId, status, isPayed);
		
		List<UserCourseDto> dtos = changToDto(list);
		
		return dtos;
	}

	@Override
	public List<RollBook> findRollBookByClassId(String classId){
		checkExceptionForClassId(classId);
		
		List<UserCourseDay> days = dayDao.findRollBookByDate(classId, new Date());
		
		// 如果当天点过名了
		if (null != days && days.size() > 0){
			
			List<RollBook> dtos = new ArrayList<RollBook>(days.size());
			for (UserCourseDay day : days){
				RollBook dto = new RollBook();
				
				dto.setId(day.getId());
				dto.setClassId(day.getClassId());
				dto.setCourseId(day.getCourseId());
				dto.setName(day.getName());
				dto.setOrderId(day.getId());
				dto.setUserId(day.getUserId());
				dto.setOpenId(day.getOpenId());
				dto.setType(day.getType());
				
				dtos.add(dto);
			}
			
			return dtos;
		}else{

			List<UserCourse> list = userCourseDao.findUserCourseByClassId(classId, 1);
			
			if (null != list && list.size() > 0){
				List<RollBook> dtos = new ArrayList<RollBook>(list.size());
				
				for (UserCourse uc : list){
					if (uc.getLeftTime().intValue() == 0){
						throw new RuntimeException("对不起,该课程的课时已经上完了!");
					}
					
					RollBook dto = new RollBook();
					
					dto.setClassId(uc.getClassId());
					dto.setCourseId(uc.getCourseId());
					// 用户类型：1表示成人；2表示小孩
					if (null != uc.getUtype() && uc.getUtype().intValue() == 2){
						dto.setName(uc.getCname());
					}else{
						dto.setName(uc.getPname());
					}
					dto.setOrderId(uc.getId());
					dto.setUserId(uc.getUserId());
					dto.setOpenId(uc.getOpenId());
					
					dtos.add(dto);
				}
				
				return dtos;
			}
		}
		
		return null;
	}
	
	@Override
	@Transactional
	public PayResultDto toPay(String id, String birthday, String school) {
//		if (null == id || id.intValue() <= 0){
		if (StringUtils.isEmpty(id)){
			throw new RuntimeException("对不起,用户课程关系主键id为空!");
		}
		
		int status = 1;
		boolean isChild = false;
		long updateTime = new Date().getTime()/1000;
		
		UserCourse entity = userCourseDao.getUserCourseById(id);
		
		Integer isPayed = entity.getIsPayed(); // 学费是否已支付：1表示已支付；0表示未支付
		if (null != isPayed && isPayed.intValue() == 1){
			throw new RuntimeException("对不起,该课程费用已支付!");
		}
		String cname = entity.getCname();	// 孩子姓名
		if (null != cname && !"".equals(cname.trim())){
			isChild = true;
			if((null == birthday || "".equals(birthday.trim())) && (null == school || "".equals(school.trim()))){
				status = 2;
			}
			
			if (status != 2){
				
				entity.setBirthday(birthday);
				entity.setOriginSchool(school.trim());
				entity.setUpdateTime(updateTime);
			}
		}
		
		if (status == 1){
			entity.setIsPayed(1);
			entity.setUpdateTime(updateTime);
			userCourseDao.getMapper().updateByPrimaryKey(entity);
		}
		PayResultDto dto = new PayResultDto(id, status, isChild, entity.getPname(), cname, entity.getTotoFee());
		
		return dto;
	}
	
	@Override
	public void insertCompleteRoll(String json) {
		List<RollBook> datas = JSON.parseArray(json, RollBook.class);
		if (null == datas || datas.size() == 0){
			throw new RuntimeException("对不起,点名数据出现异常!");
		}
		String classId = datas.get(0).getClassId();
		
		List<UserCourseDay> days = dayDao.findRollBookByDate(classId, new Date());
		
		insertCompleteRoll(datas, days);
	}
	
	@Transactional
	public void insertCompleteRoll(List<RollBook> datas, List<UserCourseDay> days){
		Map<String, UserCourseDay> map = returnDayMap(days);
		
		long updateTime = new Date().getTime()/1000;
		List<UserCourseDay> list = new ArrayList<UserCourseDay>();
		for (RollBook data : datas){
			if (null != days && days.size() > 0){
//				if (null == data.getId() || data.getId().intValue() == 0){
				if (StringUtils.isEmpty(data.getId())){
					throw new RuntimeException("对不起,修改点名记录信息时,主键id为空!");
				}
			}
			
			UserCourseDay l = new UserCourseDay();
			l.setId(UUIDGenerator.getUUID());
			l.setClassId(data.getClassId());
			l.setCourseId(data.getCourseId());
			l.setName(data.getName());
			l.setOrderId(data.getOrderId());
			l.setStatus(1);
			l.setUserId(data.getUserId());
			l.setOpenId(data.getOpenId());
			if (null != data.getId() && null != map && map.containsKey(data.getId())){
				if (data.getType().intValue() == map.get(data.getId()).getType()){
					continue;
				}
				l.setId(data.getId());
				l.setCreateTime(map.get(l.getId()).getCreateTime());
				l.setUpdateTime(updateTime);
				l.setId(map.get(l.getId()).getId());
				
			}else{
				l.setCreateTime(updateTime);
			}
			l.setType(data.getType());
			
			list.add(l);
		}
		
		//新增点名或者更新点名记录
		if (null == days || days.size() == 0){
			dayDao.insertList(list);
			
			String classId = datas.get(0).getClassId();
			// 更新订单中的used_time和left_time字段
			userCourseDao.updateUsedTime(classId);
		}else{
			if (null != list && list.size() > 0) dayDao.updateList(list);
		}
	}

	private Map<String, UserCourseDay> returnDayMap(List<UserCourseDay> days) {
		if (null != days && days.size() > 0){
			Map<String, UserCourseDay> map = new HashMap<String, UserCourseDay>(days.size());
			for (UserCourseDay day : days){
				map.put(day.getId(), day);
			}
			
			return map;
		}
		
		return null;
	}
	
	private List<UserCourseDto> changToDto(List<UserCourse> list) {
		if (null != list && list.size() > 0){
			List<UserCourseDto> dtos = new ArrayList<UserCourseDto>();
			
			for (UserCourse user : list){
				dtos.add(changToDto(user));
			}
			
			return dtos;
		}
		
		return null;
	}
	
	private UserCourseDto changToDto(UserCourse userCourse) {
		if (userCourse == null) {
			return null;
		}
		UserCourseDto dto = new UserCourseDto();
		BeanUtils.copyProperties(userCourse, dto);
		return dto;
	}
	@Override
	@Transactional
	public UserCourseDto saveAndUpdateApplyCourse(UserCourseDto userCourseDto) {
		UserCourseDto reDto = userCourseDto;
		List<CourseClassDtoX> list =  courseClassDao.findAvailableCourseClassByClassId(userCourseDto.getClassId());
		if(list != null && list.size()>0){
			reDto = userCourseDao.saveAndUpdateApplyCourse(userCourseDto);
			if(null != reDto.getId()){
				userCourseDao.addOneActualStudent(reDto.getClassId());
				courseDao.addOneActualStudent(reDto.getCourseId());
				
			}
		}
		return reDto;
	}
	
	private void checkExceptionForClassId(String classId) {
//		if (null == classId || classId.intValue() <= 0){
		if(StringUtils.isEmpty(classId)){
			throw new RuntimeException("对不起,课程小节id为空!");
		}
	}
	

	@Override
	public Boolean updateUserDepositSuccess(String userCourseId,
			BigDecimal totalFee,String tradeno) {
		return userCourseDao.updateUserDepositSuccess(userCourseId, totalFee,tradeno);
	}

	@Override
	public List<OpenClassUserCourseDto> findUserOpenClassInfoByOrderNo(UserCourseDto para) {
		return userCourseDao.findUserOpenClassInfoByOrderNo(para);
		
	}

	@Override
	public List<MineCollegeDto> findMineCollegeByOpenId(String openId) {
		if (null == openId || "".equals(openId.trim())){
			throw new RuntimeException("对不起,第三方平台id为空!");
		}
		return userCourseDao.findMineCollegeByOpenId(openId);
	}
	
	@Override
	public PageResult<MineCollegeDto> findMineCollegeByOpenId(String openId, Integer currPage, Integer pageSize) {
		if (null == openId || "".equals(openId.trim())){
			throw new RuntimeException("对不起,第三方平台id为空!");
		}
		
		int total = userCourseDao.countMineCollegeByOpenId(openId);
		currPage = currPage == null? 1 : currPage.intValue() > 0 ? currPage : 1;
		pageSize = pageSize == null? 10 : pageSize.intValue() > 0 ? pageSize : 10;
		PageQuery page = new PageQuery(total, currPage, pageSize);
		List<MineCollegeDto> dtos = null;
		if (total > 0){
			dtos = userCourseDao.findMineCollegeByOpenId(openId, page);
		}
		PageResult<MineCollegeDto> result = new PageResult<MineCollegeDto>(page, dtos);
		return result;
	}
	
	@Override
	public List<MineCollegeDto> findMineCollegeByUserId(String userId) {
		if (null == userId || "".equals(userId.trim())){
			throw new RuntimeException("对不起,用户id为空!");
		}
		return userCourseDao.findMineCollegeByUserId(userId);
	}
	
	public TrainingUserDto weixinLogin(String openId){
		TrainingUserDto res = new TrainingUserDto();
		TrainingUserDto tudtoX01 = userCourseDao.getWXUser(openId);
		if(tudtoX01 == null){
			TrainingUser tu = new TrainingUser();
			tu.setId(UUIDGenerator.getUUID());
			tu.setStatus(1);
			tu.setOpenId(openId);
			tu.setPname("juju_" + JsonUtil.parseTimestamp(System.currentTimeMillis() / 1000, "yyyyMMdd"));
			tu.setCreateTime(System.currentTimeMillis() / 1000);
			tu.setLastLoginTime(System.currentTimeMillis() / 1000);
			boolean flag = userCourseDao.addUser(tu);
			if(flag == true){
				TrainingUserDto tudtoX02 = userCourseDao.getWXUser(openId);
				res = tudtoX02;
			}
		}else{
			res = tudtoX01;
		}
		res.setFmCreateTime(JsonUtil.parseTimestamp(res.getCreateTime(), "yyyy-MM-dd"));
		res.setFmLastLoginTime(JsonUtil.parseTimestamp(res.getLastLoginTime(), "yyyy-MM-dd HH:mm:ss"));
		updateLoginTime(res.getId());
		return res;
	}

	@Override
	public UserCourseDto getUserCourseClassInfoByOrderNo(String orderno) {
		return userCourseDao.getUserCourseClassInfoByOrderNo(orderno);
	}

	@Override
	public UserCourseDto getUserCourseClassInfoByOrderId(String orderId) {
		return userCourseDao.getUserCourseClassInfoByOrderId(orderId);
		
	}

	@Override
	public List<OpenClassUserCourseForPageDto> findUserOpenClassInfoPageByOrderNo(
			UserCourseDto para) {
		return userCourseDao.findUserOpenClassInfoPageByOrderNo(para);
	}
	
	public boolean updateLoginTime(String userID){
		return userDao.updateLoginTime(userID);
	}
}