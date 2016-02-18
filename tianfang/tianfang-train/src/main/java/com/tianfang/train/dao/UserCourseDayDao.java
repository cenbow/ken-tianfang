package com.tianfang.train.dao;

import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.mapper.UserCourseDayExMapper;
import com.tianfang.train.mapper.UserCourseDayMapper;
import com.tianfang.train.pojo.UserCourseDay;
import com.tianfang.train.pojo.UserCourseDayExample;

@Repository
public class UserCourseDayDao extends MyBatisBaseDao<UserCourseDay>{

	@Autowired
	@Getter
	private UserCourseDayMapper mapper;
	
	@Autowired
	private UserCourseDayExMapper exMapper;
	
	
	/**
	 * 获得该天的该课程的点名记录
	 * 
	 * @param classId
	 * @param date
	 * @return
	 */
	public List<UserCourseDay> findRollBookByDate(String classId, Date date){
		
		long startTime = DateUtils.parse(DateUtils.format(date, DateUtils.YMD_DASH), DateUtils.YMD_DASH).getTime()/1000;
		long endTime = startTime + 86399;
		
		UserCourseDayExample example = new UserCourseDayExample();
		UserCourseDayExample.Criteria criteria = example.createCriteria();
		criteria.andClassIdEqualTo(classId);
    	criteria.andCreateTimeBetween(startTime, endTime);
    	criteria.andStatusEqualTo(1);
    	List<UserCourseDay> results = mapper.selectByExample(example);
    	
		return results;
	}
	
	public void insertList(List<UserCourseDay> list){
		checkExceptionForList(list);
		
		exMapper.insertList(list);
	}

	public void updateList(List<UserCourseDay> list){
		checkExceptionForList(list);
		for (UserCourseDay day : list){
//			if (null != day && null != day.getId() && day.getId().intValue() > 0){
			if(!StringUtils.isEmpty(day.getId())){
				exMapper.update(day);
			}
		}
	}
	
	private void checkExceptionForList(List<UserCourseDay> list) {
		if (null == list || list.size() == 0){
			throw new RuntimeException("对不起,点名记录为空!");
		}
	}
}