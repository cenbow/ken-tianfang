package com.tianfang.train.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.TrainingAddressDto;
import com.tianfang.train.dto.TrainingCourseDtoX;
import com.tianfang.train.dto.TrainingTimeDistrictDto;
import com.tianfang.train.dto.TrainingUserDto;

//import com.juju.sport.admin.manger.dto.AdminMenuFunctionDto;

public interface ITrainUserService {

	/**
	 * findPage：一句话描述方法功能
	 * 
	 * @param trainingUserDTO
	 * @param page
	 * @return
	 * @exception
	 * @author Administrator
	 * @date 2015年9月1日 下午4:16:36
	 */
	PageResult<TrainingUserDto> findPage(TrainingUserDto trainingUserDTO,
			PageQuery page);

	/***
	 * 查询所有用户记录
	 * 
	 * @param trainingUserDtO
	 * @param page
	 * @return
	 */
	PageResult<TrainingUserDto> findAllUser(TrainingUserDto trainingUserDto,
			PageQuery page);

	/***
	 * 后台管理用户记录查询所在地
	 * 
	 * @param trainingUserDto
	 * @return
	 */
	List<TrainingUserDto> findLocation(TrainingUserDto trainingUserDto);

	/**
	 * 报名课程
	 * 
	 * @param pname
	 * @param cname
	 * @param mobile
	 * @throws Exception
	 */
	TrainingUserDto applyCourse(TrainingUserDto trainingUserDto)
			throws Exception;

	/***
	 * 删除用户（暂不需要）
	 * 
	 * @return
	 */
	public Object deleteUser(String id);

	/***
	 * 修改用户
	 * 
	 * @param trainingUserDto
	 * @return
	 */
	public Object updateUser(TrainingUserDto trainingUserDto) throws Exception;

	public TrainingUserDto getUser(String id);

	/***
	 * 下拉框查询课程名称
	 * 
	 * @param trainingCourseDtoX
	 * @return
	 */
	public List<TrainingCourseDtoX> findCourseName(
			TrainingCourseDtoX trainingCourseDtoX);

	/***
	 * 下拉框查询场地
	 * 
	 * @param id
	 * @return
	 */
	public List<TrainingAddressDto> findAddress(
			TrainingAddressDto trainingAddressDto);

	/***
	 * 下拉框查询场地时间
	 * 
	 * @param trainingTimeDistrictDto
	 * @return
	 */
	public List<TrainingTimeDistrictDto> findAddressTime(
			TrainingTimeDistrictDto trainingTimeDistrictDto);
	
	/**
	 * 根据openID查询用户信息
	 * @param openID
	 * @return
	 * @2015年9月28日
	 */
	public TrainingUserDto getUIByOpenID(String openID);
	
	/**
	 * 保存用户信息
	 * @param dto
	 * @return
	 * @2015年9月28日
	 */
	public boolean addUserInfo(TrainingUserDto dto);
}
