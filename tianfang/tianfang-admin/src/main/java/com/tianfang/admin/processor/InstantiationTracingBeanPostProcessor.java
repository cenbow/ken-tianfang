package com.tianfang.admin.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 
	 * 此类描述的是：spring 全部加载bean之后 进行计算处理
	 * @author: cwftalus@163.com
	 * @version: 2015年11月18日 上午10:32:53
 */
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent>{
	protected static final Log logger = LogFactory
			.getLog(InstantiationTracingBeanPostProcessor.class);
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("-----------------start-----------------");
		

		
		logger.info("-----------------end-----------------");
	}

}
