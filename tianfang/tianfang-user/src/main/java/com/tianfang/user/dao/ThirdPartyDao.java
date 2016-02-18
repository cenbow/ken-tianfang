package com.tianfang.user.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tianfang.user.mapper.SportUserMapper;
import com.tianfang.user.pojo.SportUser;
import com.tianfang.user.pojo.SportUserExample;

/**
 * 第三方登录,注册数据层
 * 
 * @author 张亚伟
 *
 */
@Repository
public class ThirdPartyDao {

	@Autowired
	@Getter
	private SportUserMapper mapper;

	// 获取QQ登录信息
	public SportUser selectUserAccountByQQ(String openid) {
		SportUserExample example = new SportUserExample();
		SportUserExample.Criteria criteria = example.createCriteria();
		criteria.andQqEqualTo(openid);
		criteria.andStatEqualTo(1);
		List<SportUser> list = mapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	// 获取微信登录信息
	public SportUser selectUserAccountByWeiXin(String openid) {
		SportUserExample example = new SportUserExample();
		SportUserExample.Criteria criteria = example.createCriteria();
		criteria.andWeixinEqualTo(openid);
		criteria.andStatEqualTo(1);
		List<SportUser> list = mapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	// 获取微信登录信息
	public SportUser selectUserAccountBySina(String openid) {
		SportUserExample example = new SportUserExample();
		SportUserExample.Criteria criteria = example.createCriteria();
		criteria.andSinaEqualTo(openid);
		List<SportUser> list = mapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}
}
