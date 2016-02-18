package com.tianfang.user.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public interface ISmsSendService {
	
	@Async
	public String sendSms(int randomNumber,String mobilePhone,String content);
}
