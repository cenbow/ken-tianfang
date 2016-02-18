package com.tianfang.admin.handler;

import org.springframework.stereotype.Service;

/**
 * 初始化管理员账号信息，包括权限等。
 * @author rkzhang
 */
@Service
public class AdminUserAccountPump implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

//	private static final int PAGE_SIZE = 20;
//
//	protected static final Log logger = LogFactory.getLog(AdminUserAccountPump.class);
//	
//	@Autowired
//	private IAdminAccountLoginService loginService;
//	
//	@Autowired
//	private IAdminAccountService accountService;
//	
//	@Override
//	public void run() {
//		logger.info("prepare admin account info --- ");
//		long total = accountService.countAll();
//		
//		PageQuery page = new PageQuery(total, PAGE_SIZE);
//		PageResult<AdminAccountDto> accounts = accountService.findAllByPage(page);
//		while(!CollectionUtils.isEmpty(accounts.getResults())){
//			for(AdminAccountDto account : accounts.getResults()){
//				try{
//					AdminUserLoginInfoDto loginInfo = loginService.initAdminUserLoginInfo(account.getUserName());
//					logger.debug("push to cash --- " + loginInfo);
//					Thread.sleep(200);
//				}catch(Throwable e){
//					e.printStackTrace();
//					continue;
//				}
//			}
//			
//			page.nextPage();
//			accounts = accountService.findAllByPage(page);
//		}
//	}

}
