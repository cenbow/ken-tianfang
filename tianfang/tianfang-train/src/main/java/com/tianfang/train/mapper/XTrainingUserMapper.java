package com.tianfang.train.mapper;

import java.util.Map;

import com.tianfang.train.dto.TrainingUserDto;
import com.tianfang.train.pojo.TrainingUser;

public interface XTrainingUserMapper {
	Integer applyCourse(TrainingUser user);

	/**
	 * 根据条件更新订单信息
	 * 
	 * @param orderNo
	 * @param tradeNo
	 * @param state
	 * @return
	 * @2015年9月5日
	 */
	boolean updateOrder(Map<String, Object> map);

	/**
	 * 通过openId查询用户信息
	 * 
	 * @param map
	 * @return
	 * @2015年9月7日
	 */
	TrainingUserDto getWXLogin(Map<String, Object> map);

	/**
	 * 添加用户信息
	 * 
	 * @param tu
	 * @return
	 * @2015年9月7日
	 */
	Integer addUser(TrainingUser tu);

	/**
	 * 根据id删除用户记录信息
	 * 
	 * @param map
	 * @return
	 */
	boolean deleteUser(Map<String, Object> map);

	/***
	 * 修改用户记录信息
	 * 
	 * @param map
	 * @return
	 */
	Integer updateUser(Map<String, Object> map);
	
	/**
	 * 通过openID查询用户信息
	 * @param openID
	 * @return
	 * @2015年9月28日
	 */
	TrainingUserDto getUIByOpenID(String openID);
	
	/**
	 * 根据id更新用户登录时间
	 * @param map
	 * @return
	 * @2015年10月8日
	 */
	boolean updateLoginTime(Map<String, Object> map);
}
