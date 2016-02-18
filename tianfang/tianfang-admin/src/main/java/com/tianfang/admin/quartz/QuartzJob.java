package com.tianfang.admin.quartz;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.ContextLoader;

import com.tianfang.admin.processor.InstantiationTracingBeanPostProcessor;
import com.tianfang.admin.service.ISportMenuService;
import com.tianfang.common.freemarker.FreeMarkerUtil;
import com.tianfang.common.util.PropertiesUtils;

import freemarker.template.TemplateException;

public class QuartzJob implements Job{
	
	protected static final Log logger = LogFactory
			.getLog(QuartzJob.class);
	
	@Autowired
	private ISportMenuService iSportMenuService;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ServletContext sevletContext = null;
		//job data
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		//提取servlet context 对象
		sevletContext = (ServletContext)jobDataMap.get("servletContext");
		logger.info("-----------------start-----------------");
		String freemarkUrl = PropertiesUtils.getProperty("freemark.url");
		String html = freemarkUrl +File.separator +"index.html";
		Map<String, Serializable> map = new HashMap<String, Serializable>();
		map.put("index", "111111");
		try {
            FreeMarkerUtil.writeTo(sevletContext,map, "/WEB-INF/view/template/", "index.htm", html);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TemplateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }		
		logger.info("-----------------end-----------------");
	}
}
