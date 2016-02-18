package com.tianfang.train.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.train.dao.TrainingCourseDao;
import com.tianfang.train.dao.TrainingUserDao;
import com.tianfang.train.dto.TrainingAddressDto;
import com.tianfang.train.dto.TrainingCourseDtoX;
import com.tianfang.train.dto.TrainingTimeDistrictDto;
import com.tianfang.train.dto.TrainingUserDto;
import com.tianfang.train.pojo.TrainingUser;
import com.tianfang.train.service.ITrainUserService;

/**
 * 
 * @author pengpeng
 *
 */
@Service
public class ITrainUserServiceImpl implements ITrainUserService {

	@Resource
	private TrainingUserDao trainingUserDao;

	@Resource
	private TrainingCourseDao trainingCourseDao;

	@Override
	public PageResult<TrainingUserDto> findPage(
			TrainingUserDto trainingUserDto, PageQuery page) {
		List<TrainingUserDto> results = trainingUserDao.findPage(
				trainingUserDto, page);
		page.setTotal(trainingUserDao.count());
		return new PageResult<TrainingUserDto>(page, results);
	}

	@Override
	public PageResult<TrainingUserDto> findAllUser(
			TrainingUserDto trainingUserDto, PageQuery page) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<TrainingUserDto> results = trainingUserDao.findAllUser(
				trainingUserDto, page);
		if (results != null && results.size() > 0) {
			for (TrainingUserDto trainingUserDto2 : results) {
				if (trainingUserDto2.getBirthday() == null) {
					continue;
				}
				Date date = new Date(trainingUserDto2.getBirthday() * 1000);
				trainingUserDto2.setBirthdayStr(sdf.format(date));
			}
		}
		page.setTotal(trainingUserDao.counts(trainingUserDto));
		return new PageResult<TrainingUserDto>(page, results);
	}

	@Override
	public List<TrainingUserDto> findLocation(TrainingUserDto trainingUserDto) {
		return trainingUserDao.findLocation(trainingUserDto);
	}

	@Override
	public TrainingUserDto applyCourse(TrainingUserDto trainingUserDto)
			throws Exception {

		return trainingUserDao.applyCourse(trainingUserDto);

	}

	@Override
	public Object deleteUser(String delId) {
		boolean flag[] = new boolean[delId.length()];
		String[] idArr = delId.split(",");
		for (String id : idArr) {
			TrainingUser trainingUser = trainingUserDao.findById(id);
			if (trainingUser == null) {
				return 0; // 无此用户
			}
			flag = trainingUserDao.deleteUser(id);
		}
		for (int i = 0; i < delId.length(); i++) {
			if (flag[i] == true) {
				return 1; // 删除成功
			} else {
				return 2; // 删除失败
			}
		}
		return 1;
	}

	@Override
	public List<TrainingCourseDtoX> findCourseName(
			TrainingCourseDtoX trainingCourseDtoX) {
		return trainingCourseDao.findCourseName(trainingCourseDtoX);
	}

	@Override
	public List<TrainingAddressDto> findAddress(
			TrainingAddressDto trainingAddressDto) {
		return trainingUserDao.findAddress(trainingAddressDto);
	}

	@Override
	public List<TrainingTimeDistrictDto> findAddressTime(
			TrainingTimeDistrictDto trainingTimeDistrictDto) {
		return trainingUserDao.findAddressTime(trainingTimeDistrictDto);
	}

	@Override
	public Object updateUser(TrainingUserDto trainingUserDto) throws Exception {
		TrainingUser trainingUser = trainingUserDao.findById(trainingUserDto
				.getId());
		if (trainingUser == null) {
			return 0;
		}
		int count = trainingUserDao.updateUser(trainingUserDto);
		if (count == 1) {
			return 1; // 编辑成功
		} else {
			return 2; // 编辑失败
		}
	}

	@Override
	public TrainingUserDto getUser(String id) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		TrainingUserDto trainingUserDto = BeanUtils.createBeanByTarget(
				trainingUserDao.findById(id), TrainingUserDto.class);
		if (trainingUserDto.getBirthday() == null) {
			return trainingUserDto;
		}
		Date date = new Date(trainingUserDto.getBirthday() * 1000);
		trainingUserDto.setBirthdayStr(sdf.format(date));
		return trainingUserDto;
	}

	@Override
	public TrainingUserDto getUIByOpenID(String openID) {
		return trainingUserDao.getUIByOpenID(openID);
	}

	@Override
	public boolean addUserInfo(TrainingUserDto dto) {
		return trainingUserDao.addUserInfo(dto);
	}
	
}
