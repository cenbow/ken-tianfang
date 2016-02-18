package com.tianfang.admin.handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserAccountRedisHandler {

	protected static final Log logger = LogFactory.getLog(AdminUserAccountRedisHandler.class);
	
	@Autowired
	private AdminUserAccountPump accountPump;
	
	public synchronized void reflushAll(){
		ExecutorService es = Executors.newCachedThreadPool();
		es.execute(accountPump);
	}
	
	
}
