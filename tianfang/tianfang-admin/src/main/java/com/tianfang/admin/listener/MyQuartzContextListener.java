package com.tianfang.admin.listener;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

import com.tianfang.admin.quartz.QuartzJob;

public class MyQuartzContextListener extends QuartzInitializerListener{
	protected static final Log logger = LogFactory.getLog(MyQuartzContextListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		super.contextInitialized(sce);
		ServletContext servletContext = sce.getServletContext();
		//scheduler factory
		StdSchedulerFactory sFactory = (StdSchedulerFactory)servletContext.getAttribute(QUARTZ_FACTORY_KEY);
		Scheduler scheduler = null;
		try {
			scheduler = sFactory.getScheduler();
			//定义一个JobDetail 
			JobDetail jobDetail = new JobDetail("autoRunJobDetail", "autoRunGroup", QuartzJob.class);
			// 将ServletContext对象放到map中，然后从job中取出来，从而取得路径
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("servletContext", servletContext);
			//将servlet上下文添加到JobDataMap中
			JobDataMap dateMap = new JobDataMap(map);
			jobDetail.setJobDataMap(dateMap);
			//触发器
//			Trigger trigger = new CronTrigger("autoRunCronTrigger", "autoRunCronTrigger", "10,15,20,25,30,35,40,45,50,55 * * * * ?");
			Trigger trigger = new CronTrigger("autoRunCronTrigger", "autoRunCronTrigger", "0 0 0-23 * * ?");//整点执行
			//关联任务和触发器
			scheduler.scheduleJob(jobDetail, trigger);
			//开启调度
			scheduler.start();
		} catch (SchedulerException e) {
			logger.error("调度器 MyQuartzContextListener", e);
			e.printStackTrace();
		} catch (ParseException e) {
			logger.error("调度器 CronTrigger表达式解析错误", e);
			e.printStackTrace();
		}
	}
}
