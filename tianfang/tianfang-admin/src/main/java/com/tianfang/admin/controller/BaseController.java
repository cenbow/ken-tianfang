package com.tianfang.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.dto.MenuRespDto;
import com.tianfang.admin.dto.SportAdminDto;
import com.tianfang.admin.service.ISportMenuService;
import com.tianfang.business.dto.SportHomeDto;
import com.tianfang.business.service.SportHomeService;
import com.tianfang.common.constants.PlayerPosition;
import com.tianfang.common.util.PropertiesUtils;
import com.tianfang.common.util.UUIDGenerator;


public class BaseController {
	
	protected Logger logger = Logger.getLogger(BaseController.class);

	private static final long serialVersionUID = 6357869213649815390L;
	
	@Autowired
	private ISportMenuService iSportMenuService;
	
	@Autowired
	private SportHomeService sportHomeService;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 得到最左边的菜单列表
	 */
	
	public List<MenuRespDto> getLeftMenuListByCache(String userId){
//		String keyCode = CacheKey.CACHE_ADMIN_LEFT_MENU+userId;
		List<MenuRespDto> dataList = new ArrayList<MenuRespDto>();
//		if(redisTemplate.opsForValue().get(keyCode)!=null){
//			dataList = (List<MenuRespDto>)redisTemplate.opsForValue().get(keyCode);
//		}else{
			dataList = iSportMenuService.findMenuByAdminId(userId);
//			redisTemplate.opsForValue().set(keyCode, dataList, 1, TimeUnit.HOURS);
//		}
		return dataList;
	}
	
	/**
	 * 得到PageData
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		ModelAndView mv = new ModelAndView();
		mv.addObject("leftUrl",getRequest().getRequestURI());
		mv.addObject("pId",getRequest().getParameter("pId"));
		mv.addObject("positionMap", PlayerPosition.map);
		mv.addObject("formationMap", PlayerPosition.formationMap);
		mv.addObject("wwwdomain", PropertiesUtils.getProperty("wwwdomain"));
		String sessionId = this.getRequest().getSession().getId();
		mv.addObject("sessionId", sessionId);
		return mv;
	}
	
	public ModelAndView getModelAndView(String userId){
		ModelAndView mv = new ModelAndView();
		mv.addObject("leftUrl",getRequest().getRequestURI());
		mv.addObject("pId",getRequest().getParameter("pId"));
		mv.addObject("menuList",getLeftMenuListByCache(userId));
		mv.addObject("positionMap", PlayerPosition.map);
		mv.addObject("formationMap", PlayerPosition.formationMap);
		mv.addObject("wwwdomain", PropertiesUtils.getProperty("wwwdomain"));
		String sessionId = this.getRequest().getSession().getId();
		mv.addObject("sessionId", sessionId);
		return mv;
	}
	
	public String getSessionUserId(){
		SportAdminDto user = (SportAdminDto)getRequest().getSession().getAttribute(Const.SESSION_USER);
		if(user==null){
			return "";
		}
		return user.getId();
	}	
	
	public String getSessionUserName(){
		SportAdminDto user = (SportAdminDto)getRequest().getSession().getAttribute(Const.SESSION_USER);
		if(user==null){
			return "";
		}
		return user.getAccount();
	}	
	
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		return request;
	}

	/**
	 * 得到32位的uuid
	 * @return
	 */
	public String get32UUID(){
		
		return UUIDGenerator.getUUID32Bit();
	}
	
	/**
	 * 得到分页列表的信息 
	 */
	public Page getPage(){
		
		return new Page();
	}
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}
	
	/**
	 * 
	 * 此方法描述的是：底部合作伙伴
	 * @author: cwftalus@163.com
	 * @version: 2015年12月4日 下午1:45:24
	 */
	public List<SportHomeDto> loadPartnerList(){
		SportHomeDto dto = new SportHomeDto();
		dto.setType(8);
		List<SportHomeDto> dataList = sportHomeService.findNRecords(dto, null);
		return dataList;
	}
}
