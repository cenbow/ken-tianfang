package com.tianfang.user.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public interface IEmailSendService {
	
	@Async
	public void sendEmail(int randomNumber,String email,String content, String from_, String subject);
}
