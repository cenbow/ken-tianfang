package com.tianfang.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tianfang.user.dto.SportUserReqDto;

/**
 *前台用户接口
 * @author YIn
 * @time:2015年11月19日 下午4:36:14
 * @ClassName: IUserService
 * @Description: TODO
 * @
 */
@Service
public interface IUserService
{
	//检查用户名是否重复
	int checkRepeat(SportUserReqDto sportUserReqDto);
    //注册用户
	SportUserReqDto register(SportUserReqDto sportUserReqDto);
	//查询登录名和密码是否正确
	SportUserReqDto checkUser(SportUserReqDto sportUserReqDto);
	//通过id查询用户信息
	SportUserReqDto selectUserAccountByUserId(String userAccountId);
	//更新用户信息
	int updateUserAccount(SportUserReqDto sportUserReqDto);
	
	/**
	 * 查询所有教练(排序按trainer_level升序,create_time升序)
	 * @return
	 * @author xiang_wang
	 * 2015年11月30日上午10:10:24
	 */
	List<SportUserReqDto> findAllCoach();
	
	/**
	 * 查询所有顶级教练
	 * @return
	 * @author xiang_wang
	 * 2015年11月30日下午1:34:58
	 */
	List<SportUserReqDto> findTopCoach();
	/**
	 * 
		 * 此方法描述的是：根据条件查询教练
		 * @author: cwftalus@163.com
		 * @version: 2015年12月25日 下午4:03:26
	 */
	List<SportUserReqDto> findCoachByParams(SportUserReqDto params);
}