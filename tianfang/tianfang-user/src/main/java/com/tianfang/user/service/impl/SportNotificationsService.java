package com.tianfang.user.service.impl;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.util.BeanUtils;
import com.tianfang.user.dao.SportNotificationsDao;
import com.tianfang.user.dto.SportNotificationsDto;
import com.tianfang.user.pojo.SportNotifications;
import com.tianfang.user.service.ISportNotificationsService;

@Service
public class SportNotificationsService implements ISportNotificationsService {

	@Autowired
	private SportNotificationsDao sportNotificationsDao;
	
	@Override
	public long save(SportNotificationsDto notificationsDto) {
		SportNotifications notifications = BeanUtils.createBeanByTarget(notificationsDto, SportNotifications.class);
		return  sportNotificationsDao.saveNotif(notifications);
	}

	@Override
	public long update(SportNotificationsDto notificationsDto) {
		SportNotifications notifications = BeanUtils.createBeanByTarget(notificationsDto, SportNotifications.class);
		return sportNotificationsDao.updaNptif(notifications);
	}

	@Override
	public SportNotificationsDto getByCriteria(SportNotificationsDto oldObj) {
		return  sportNotificationsDao.getCriteria(oldObj).size() > 0 ?(SportNotificationsDto)BeanUtils.createBeanByTarget(sportNotificationsDao.getCriteria(oldObj).get(0), SportNotificationsDto.class):null;
	}
	

}
