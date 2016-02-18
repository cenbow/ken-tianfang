package com.tianfang.train.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.train.dao.TrainingMessageInfoDao;
import com.tianfang.train.dto.TrainingMessageInfoDto;
import com.tianfang.train.dto.UserCourseDto;
import com.tianfang.train.dto.UserCourseMessageDto;
import com.tianfang.train.pojo.TrainingMessageInfo;
import com.tianfang.train.pojo.UserCourse;
import com.tianfang.train.service.ITrainingMessageInfosService;

@Service
public class TrainingMessageInfoServiceImpl implements
		ITrainingMessageInfosService {
	@Resource
	private TrainingMessageInfoDao trainingMessageInfoDao;

	@Override
	public PageResult<TrainingMessageInfoDto> findPage(
			TrainingMessageInfoDto trainingMessageInfoDto, PageQuery page) {
		List<TrainingMessageInfoDto> results = trainingMessageInfoDao.findPage(
				trainingMessageInfoDto, page);
		if (null != results && results.size() > 0){
			for (TrainingMessageInfoDto trainingMessageInfoDto2 : results) {
				trainingMessageInfoDto2.setReleaseDate(DateUtils.format(
						trainingMessageInfoDto2.getCreateTime(),
						"yyyy-MM-dd HH:mm:ss"));
			}
		}
		
		page.setTotal(trainingMessageInfoDao.count(trainingMessageInfoDto));
		return new PageResult<TrainingMessageInfoDto>(page, results);
	}

	@Override
	public Object insertMessageInfo(
			TrainingMessageInfoDto trainingMessageInfoDto) {
		TrainingMessageInfo trainingMessageInfo = BeanUtils.createBeanByTarget(
				trainingMessageInfoDto, TrainingMessageInfo.class);
		int flag = trainingMessageInfoDao.save(trainingMessageInfo);
		if (flag > 0) {
			return 1;
		}
		return 0;
	}

	@Override
	public Object deleteMessageInfoById(String delId) {
		String[] idArr = delId.split(",");
		for (String id : idArr) {
			TrainingMessageInfo trainingMessageInfo = trainingMessageInfoDao
					.findById(id);
			if (trainingMessageInfo == null) {
				return 0;
			}
			trainingMessageInfo.setLastUpdateTime(new Date());
			trainingMessageInfo.setStat(DataStatus.DISABLED);
			trainingMessageInfoDao.updateByPrimaryKey(trainingMessageInfo);
		}
		return 1;
	}

	@Override
	public Object updateMessageInfo(
			TrainingMessageInfoDto trainingMessageInfoDto) {
		TrainingMessageInfo trainingMessageInfo = BeanUtils.createBeanByTarget(
				trainingMessageInfoDto, TrainingMessageInfo.class);
		int flag = trainingMessageInfoDao.edit(trainingMessageInfo);
		if (flag > 0) {
			return 1;
		}
		return 0;
	}

	@Override
	public TrainingMessageInfoDto getMessageInfo(String id) {
		TrainingMessageInfoDto trainingMessageInfoDto = BeanUtils
				.createBeanByTarget(trainingMessageInfoDao.findById(id),
						TrainingMessageInfoDto.class);
		return trainingMessageInfoDto;
	}

	@Override
	public PageResult<UserCourseMessageDto> findCourseMessage(
			UserCourseDto userCourseDto, PageQuery page) throws ParseException {
		UserCourse userCourse = BeanUtils.createBeanByTarget(userCourseDto,
				UserCourse.class);
		List<UserCourseMessageDto> userCourseList = trainingMessageInfoDao
				.findCourseMessage(userCourse, page);
		
		List<TrainingMessageInfo> dataList = trainingMessageInfoDao.findAll(); 
		if (null != userCourseList && userCourseList.size() >0){
			for(UserCourseMessageDto dto : userCourseList){
				String open_date = dto.getOpen_date().substring(0, 10);
				String open_time = dto.getOpen_time()+":00";
				String open_date_time = open_date+" "+open_time;
				SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.YMD_DASH_WITH_TIME);
				Date open_date_time_date = sdf.parse(open_date_time);
				if(open_date_time_date.getTime()<=new Date().getTime()){
					dto.setOpen_status(1);
				}
			}
		}
		
		if (null != dataList && dataList.size() > 0){
			for(TrainingMessageInfo tminfo : dataList){
				UserCourseMessageDto umDto = new UserCourseMessageDto();
				umDto.setCourse_name(tminfo.getTitle());
				umDto.setOpen_status(DataStatus.HTTP_FAILE);
				umDto.setDescription(tminfo.getContent());
				umDto.setApply_time(DateUtils.format(tminfo.getCreateTime(),DateUtils.YMD_DASH_WITH_TIME));
				
				userCourseList.add(umDto);
			}
		}
		
		long total = trainingMessageInfoDao.selectAllUserCourseMessages(userCourse);
		page.setTotal(total);
		return new PageResult<UserCourseMessageDto>(page, userCourseList);
	}

}
