package com.tianfang.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.util.BeanUtils;
import com.tianfang.user.constants.UserTypeEnum;
import com.tianfang.user.dao.ThirdPartyDao;
import com.tianfang.user.dto.SportUserReqDto;
import com.tianfang.user.pojo.SportUser;
import com.tianfang.user.service.ISmsSendService;
import com.tianfang.user.service.IThirdPartyService;
import com.tianfang.user.service.IUserService;


/**
 * 
 * @author YIn
 * @time:2015年11月22日 下午12:04:19
 * @ClassName: ThirdPartyServiceImpl
 * @Description: TODO
 * @
 */
@Service
public class ThirdPartyServiceImpl implements IThirdPartyService {

	@Autowired
	private ThirdPartyDao thirdPartyDao;
	@Autowired
	private IUserService iUserService;
	@Autowired
	private ISmsSendService iSmsSendService;

	/**
	 * 获取QQ是否存在账号中,如果不存在,创建一个QQ登录的账号
	 * @param openid
	 * QQ唯一标识符
	 */
	public SportUserReqDto selectUserAccountByQQ(String openid) {

		// 获取数据库中的用户
		SportUser sportUser = thirdPartyDao.selectUserAccountByQQ(openid);

		// 创建用户的dto对象
		SportUserReqDto sportUserReqDto = new SportUserReqDto();
		
		// 判断数据库中是否存在QQ的登录信息,如果存在,直接返回,如果不存在,创建一个QQ登录信息账号并返回
		if (sportUser == null) {
			// 创建QQ登录账号
			sportUserReqDto.setQq(openid);
			sportUserReqDto.setUtype((UserTypeEnum.NORMALUSER.getIndex()));
			SportUserReqDto dto = iUserService.register(sportUserReqDto);
			return dto;
		} else {
			BeanUtils.copyProperties(sportUser, sportUserReqDto);
			return sportUserReqDto;
		}

	}

	/**
	 * 获取微信是否存在账号中,如果不存在,创建一个微信登录的账号
	 * @param openid
	 * 微信唯一标识符
	 */
	@Override
	public SportUserReqDto selectUserAccountByWeiXin(String openid) {
		// 获取数据库中的用户
		SportUser sportUser = thirdPartyDao
				.selectUserAccountByWeiXin(openid);

		// 创建用户的dto对象
		SportUserReqDto sportUserReqDto = new SportUserReqDto();

		// 判断数据库中是否存在登录信息,如果存在,直接返回,如果不存在,创建一个登录账号并返回
		if (sportUser == null) {
			// 创建登录账号
			sportUserReqDto.setWeixin(openid);
			sportUserReqDto.setUtype(UserTypeEnum.NORMALUSER.getIndex());
			SportUserReqDto dto = iUserService.register(sportUserReqDto);
			return dto;
		} else {
			BeanUtils.copyProperties(sportUser, sportUserReqDto);
			return sportUserReqDto;
		}
	}

	/**
	 * 获取新浪账号是否存在账号中,如果不存在,创建一个新浪登录的账号
	 * @param openid
	 *  新浪唯一标识符
	 */
	public SportUserReqDto selectUserAccountBySina(String openid) {

		// 获取数据库中的用户
		SportUser sportUser = thirdPartyDao.selectUserAccountBySina(openid);

		// 创建用户的dto对象
		SportUserReqDto sportUserReqDto = new SportUserReqDto();
		// 判断数据库中是否存在登录信息,如果存在,直接返回,如果不存在,创建一个登录账号并返回
		if (sportUser == null) {
			// 创建登录账号
			sportUserReqDto.setSina(openid);
			sportUserReqDto.setUtype(UserTypeEnum.NORMALUSER.getIndex());
			SportUserReqDto dto = iUserService.register(sportUserReqDto);
			return dto;
		} else {
			BeanUtils.copyProperties(sportUser, sportUserReqDto);
			return sportUserReqDto;
		}

	}

	@Override
	public SportUserReqDto selectOpenidByQQ(String openid) {
		SportUser sportUser = thirdPartyDao.selectUserAccountByQQ(openid);
		if (sportUser!=null) {
			// 创建用户的dto对象
			SportUserReqDto sportUserReqDto = new SportUserReqDto();
			BeanUtils.copyProperties(sportUser, sportUserReqDto);
			return sportUserReqDto;
		}
		return null;
	}

	@Override
	public SportUserReqDto selectOpenidBySina(String openid) {
		SportUser sportUser = thirdPartyDao.selectUserAccountBySina(openid);
		// 创建用户的dto对象
		SportUserReqDto sportUserReqDto = new SportUserReqDto();
		if (sportUser!=null) {
			BeanUtils.copyProperties(sportUser, sportUserReqDto);
			return sportUserReqDto;
		}else
		{
			return null;
		}
	}

	@Override
	public SportUserReqDto selectOpenidByWeixin(String openid) {
		SportUser sportUser = thirdPartyDao.selectUserAccountByWeiXin(openid);
		if (sportUser!=null) {
			// 创建用户的dto对象
			SportUserReqDto sportUserReqDto = new SportUserReqDto();
			BeanUtils.copyProperties(sportUser, sportUserReqDto);
			return sportUserReqDto;
		}
		return null;
	}

}
