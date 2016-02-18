package com.tianfang.user.service;

import com.tianfang.user.dto.SportNotificationsDto;

public interface ISportNotificationsService {

	public long save(SportNotificationsDto notifications);
	
	public long update(SportNotificationsDto notifications);

	public SportNotificationsDto getByCriteria(SportNotificationsDto oldObj);
}
