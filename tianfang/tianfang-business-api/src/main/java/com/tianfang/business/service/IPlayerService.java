package com.tianfang.business.service;

import org.springframework.stereotype.Service;

import com.tianfang.business.dto.PlayerDto;
/**
 * 
	 * 此类描述的是：用户信息接口方法类
	 * @author: cwftalus@163.com
	 * @version: 2015年7月16日 下午2:27:08
 */
@Service("iPlayerService")
public interface IPlayerService {
	/**
	 * 
		 * 此方法描述的是：根据用户Id查询用户基本信息
		 * @author: cwftalus@163.com
		 * @version: 2015年7月16日 下午2:22:31
	 */
	public PlayerDto loadPlayerInfoByUserId(Integer userId);
	/**
	 * 
		 * 此方法描述的是：个人信息修改方法
		 * @author: cwftalus@163.com
		 * @version: 2015年7月16日 下午6:07:17
	 */
	public void modifyPlayerInfoByUserId(PlayerDto playerDto);
}
