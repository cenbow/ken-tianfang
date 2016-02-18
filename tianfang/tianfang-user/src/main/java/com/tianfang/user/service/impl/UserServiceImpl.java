package com.tianfang.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tianfang.common.digest.MD5Coder;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.user.dao.UserDao;
import com.tianfang.user.dto.SportUserReqDto;
import com.tianfang.user.pojo.SportUser;
import com.tianfang.user.service.IUserService;

/**
 * 前端用户业务操作类
 * @author YIn
 * @time:2015年11月19日 下午4:48:51
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @
 */
@Service
public class UserServiceImpl implements IUserService
{
    @Autowired
    @Getter
    private UserDao userDao;

    /**
	 * @author YIn
	 * @time:2015年11月19日 下午4:50:07
	 */
	@Override
	public int checkRepeat(SportUserReqDto sportUserReqDto) {
		SportUser user = new SportUser();
		BeanUtils.copyProperties(sportUserReqDto, user);
		return userDao.checkRepeat(user);
	}

	/**
	 * @author YIn
	 * @time:2015年11月19日 下午5:45:46
	 */
	@Override
	public SportUserReqDto register(SportUserReqDto sportUserReqDto) {
		sportUserReqDto.setId(UUIDGenerator.getUUID());
		SportUser user = new SportUser();
		BeanUtils.copyProperties(sportUserReqDto, user);
		int flag = userDao.insertSelective(user);
		 if(flag > 0){
			 return sportUserReqDto;
		 }else{
			 return null;
		 }
	}

	/**
	 * @author YIn
	 * @time:2015年11月19日 下午6:57:28
	 */
	@Override
	public SportUserReqDto checkUser(SportUserReqDto sportUserReqDto) {
		String pwd = sportUserReqDto.getPassword();
		try {
			MD5Coder.encodeMD5Hex(pwd);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		sportUserReqDto.setPassword(pwd);
		SportUser user = userDao.findByNameAndPassword(sportUserReqDto);
		if(user == null){
			return null;
		}else{
			SportUserReqDto dto = new SportUserReqDto();
			BeanUtils.copyProperties(user, dto);
			return dto;
		}
	}
	
	/**
	 * @author YIn
	 * @time:2015年11月22日 上午11:27:23
	 */
	@Override
	public SportUserReqDto selectUserAccountByUserId(String userAccountId) {
		SportUser user = userDao.selectByPrimaryKey(userAccountId);
		SportUserReqDto sportUserReqDto = new SportUserReqDto();
		BeanUtils.copyProperties(user, sportUserReqDto);
		return sportUserReqDto;
		
		
	}

	/**
	 * @author YIn
	 * @time:2015年11月22日 上午11:27:23
	 */
	@Override
	public int updateUserAccount(SportUserReqDto sportUserReqDto) {
		SportUser user = new SportUser();
		BeanUtils.copyProperties(sportUserReqDto, user);
		int flag = userDao.updateByObj(user);
		return flag;
	}

	@Override
	public List<SportUserReqDto> findAllCoach() {
		List<SportUser> list = userDao.findAllCoach();
		if (!CollectionUtils.isEmpty(list)){
			List<SportUserReqDto> dtos = new ArrayList<SportUserReqDto>(list.size());
			SportUserReqDto dto;
			for (SportUser user : list){
				dto = new SportUserReqDto();
				BeanUtils.copyProperties(user, dto);
				dtos.add(dto);
			}
			return dtos;
		}
		return null;
	}

	@Override
	public List<SportUserReqDto> findTopCoach() {
		List<SportUser> list = userDao.findTopCoach();
		if (!CollectionUtils.isEmpty(list)){
			List<SportUserReqDto> dtos = new ArrayList<SportUserReqDto>(list.size());
			SportUserReqDto dto;
			for (SportUser user : list){
				dto = new SportUserReqDto();
				BeanUtils.copyProperties(user, dto);
				dtos.add(dto);
			}
			return dtos;
		}
		return null;
	}
	
	public List<SportUserReqDto> findCoachByParams(SportUserReqDto params){
		List<SportUser> dataList = userDao.findCoachByParams(params);
		return BeanUtils.createBeanListByTarget(dataList, SportUserReqDto.class);
	}
}