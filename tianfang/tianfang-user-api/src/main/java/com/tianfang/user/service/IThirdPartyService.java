package com.tianfang.user.service;

import org.springframework.stereotype.Service;

import com.tianfang.user.dto.SportUserReqDto;


/**
 * 第三方登录
 * @author YIn
 * @time:2015年11月20日 上午11:19:04
 * @ClassName: IThirdPartyService
 * @Description: TODO
 * @
 */

@Service
public interface IThirdPartyService {

	// 获取QQ是否存在账号中,如果不存在,创建一个QQ登录的账号
	public SportUserReqDto selectUserAccountByQQ(String openid);

	// 获取微信是否存在账号中,如果不存在,创建一个微信登录的账号
	public SportUserReqDto selectUserAccountByWeiXin(String openid);

	// 获取新浪是否存在账号中,如果不存在,创建一个新浪登录的账号
	public SportUserReqDto selectUserAccountBySina(String openid);

	// 根据openid检查账号中是否绑定有QQ
	public SportUserReqDto selectOpenidByQQ(String openid);

	// 根据openid检查账号中是否绑定有新浪
	public SportUserReqDto selectOpenidBySina(String openid);

	// 根据openid检查账号中是否绑定有微信
	public SportUserReqDto selectOpenidByWeixin(String openid);

}
