package com.tianfang.user.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.user.dto.SportNotificationsDto;
import com.tianfang.user.mapper.SportNotificationsMapper;
import com.tianfang.user.pojo.SportNotifications;
import com.tianfang.user.pojo.SportNotificationsExample;
import com.tianfang.user.pojo.SportNotificationsExample.Criteria;
@Repository
public class SportNotificationsDao extends MyBatisBaseDao<SportNotifications> {

	@Autowired
	@Getter
	private SportNotificationsMapper mapper;
	@Override
	public Object getMapper() {
		return null;
	}

	public long saveNotif(SportNotifications notifications) {
		long stat =0;
		notifications.setId(UUID.randomUUID()+"");
		notifications.setCreateTime(new Date());
		notifications.setStat(DataStatus.ENABLED);
		if(notifications.getUpPasswordStat()==null || notifications.getUpPasswordStat().equals("")){
			notifications.setUpPasswordStat(3);
		}
		if(notifications.getNonlocalLoginStat()==null || notifications.getNonlocalLoginStat().equals("")){
			notifications.setNonlocalLoginStat(3);
		}
		if(notifications.getLoginStat()==null || notifications.getLoginStat().equals("")){
			notifications.setLoginStat(3);
		}
		try {
			stat=mapper.insert(notifications);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}

	public long updaNptif(SportNotifications notifications) {
		long stat =0;
		SportNotifications oldObj=	mapper.selectByPrimaryKey(notifications.getId());
		if(notifications.getCreateTime()==null || notifications.getCreateTime().equals("")){
			notifications.setCreateTime(oldObj.getCreateTime());
		}
		if(notifications.getEmail()==null || notifications.getEmail().equals("")){
			notifications.setEmail(oldObj.getEmail());
		}
		if(notifications.getUpPasswordStat()==null || notifications.getUpPasswordStat().equals("")){
			notifications.setUpPasswordStat(oldObj.getUpPasswordStat());
		}
		if(notifications.getNonlocalLoginStat()==null || notifications.getNonlocalLoginStat().equals("")){
			notifications.setNonlocalLoginStat(oldObj.getNonlocalLoginStat());
		}
		if(notifications.getLoginStat()==null || notifications.getLoginStat().equals("")){
			notifications.setLoginStat(oldObj.getLoginStat());
		}
		notifications.setLastUpdateTime(new Date());
		notifications.setStat(DataStatus.ENABLED);
		try {
			stat= mapper.updateByPrimaryKey(notifications);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}

	public List<SportNotifications> getCriteria(SportNotificationsDto  notifications) {
		SportNotificationsExample example = new SportNotificationsExample();
		Criteria criteria =example.createCriteria();
		if(notifications.getOwnerId()!=null &&! notifications.getOwnerId().equals("")){
			criteria.andOwnerIdEqualTo(notifications.getOwnerId());
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}

}
