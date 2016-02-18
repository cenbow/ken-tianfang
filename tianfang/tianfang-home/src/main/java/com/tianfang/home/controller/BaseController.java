package com.tianfang.home.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.business.dto.SportAddressesDto;
import com.tianfang.business.dto.SportFutureStarBlogDto;
import com.tianfang.business.dto.SportHomeDto;
import com.tianfang.business.dto.SportTeamDto;
import com.tianfang.business.service.ISportAddressesService;
import com.tianfang.business.service.ISportFutureStarBlogService;
import com.tianfang.business.service.ISportHomeMenuService;
import com.tianfang.business.service.ITeamService;
import com.tianfang.business.service.SportHomeService;
import com.tianfang.common.constants.PlayerPosition;
import com.tianfang.common.constants.SessionConstants;
import com.tianfang.user.dto.LoginUserDto;
import com.tianfang.user.dto.SportUserReqDto;
import com.tianfang.user.dto.TrainerLevel;
import com.tianfang.user.service.IUserService;

public class BaseController {
	protected Logger logger = Logger.getLogger(BaseController.class);
	private final static int SHOW_BLOG_SIZE = 5; // 显示博文个数
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private ITeamService iTeamService;

	@Autowired
	private IUserService iUserService;

	@Autowired
	private ISportFutureStarBlogService blogService;

	@Autowired
	private SportHomeService sportHomeService;

	@Autowired
	private ISportAddressesService iSportAddressesService;

	@Autowired
	private ISportHomeMenuService iSportHomeMenuService;

	public SportTeamDto getSportTeamByCache(String teamId){	
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		hashMap.put("id", teamId);
		logger.info("teamId"+teamId);
		//		String keyCode = "SPORTTEAM"+teamId;
		SportTeamDto sportTeamDto = null;
		//		if(redisTemplate.opsForValue().get(keyCode)!=null){
		//			sportTeamDto = (SportTeamDto)redisTemplate.opsForValue().get(keyCode);
		//		}else{
		sportTeamDto = iTeamService.selectById(hashMap);
		//			redisTemplate.opsForValue().set(keyCode, sportTeamDto, 1, TimeUnit.HOURS);
		//		}
		return sportTeamDto;
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

	/**
	 * 获取缓存中用户信息
	 * @param userId
	 * @return
	 * @author xiang_wang
	 * 2015年11月30日下午2:35:39
	 */
	public SportUserReqDto getSportUserByCache(String userId){
		if(StringUtils.isEmpty(userId)){
			return null;
		}
		String keyCode = "SPORTUSER"+userId;
		SportUserReqDto sportUserDto = null;
		//		if(redisTemplate.opsForValue().get(keyCode)!=null){
		//			sportUserDto = (SportUserReqDto)redisTemplate.opsForValue().get(keyCode);
		//		}else{
		sportUserDto = iUserService.selectUserAccountByUserId(userId);
		//			redisTemplate.opsForValue().set(keyCode, sportUserDto, 1, TimeUnit.HOURS);
		//		}
		return sportUserDto;
	}

	/**
	 * 从缓存中查询用户博文集合(根据阅读量降序排序)
	 * @param userId
	 * @return
	 * @author xiang_wang
	 * 2015年11月30日下午3:47:50
	 */
	@SuppressWarnings("unchecked")
	public List<SportFutureStarBlogDto> getSportBlogByCache(String userId){
		//		String keyCode = "SPORTUSERBLOG"+userId;
		List<SportFutureStarBlogDto> blogs = null;
		//		if(redisTemplate.opsForValue().get(keyCode)!=null){
		//			blogs = (List<SportFutureStarBlogDto>)redisTemplate.opsForValue().get(keyCode);
		//		}else{
		blogs = blogService.findBlogsByUserId(userId, SHOW_BLOG_SIZE);
		//			redisTemplate.opsForValue().set(keyCode, blogs, 1, TimeUnit.HOURS);
		//		}

		return blogs;
	}

	/**
	 * 从缓存中查询顶级教练信息集合
	 * @param datas
	 * @return
	 * @author xiang_wang
	 * 2015年11月30日下午3:53:12
	 */
	@SuppressWarnings("unchecked")
	public List<SportUserReqDto> getSportTopCoachsByCache(List<SportUserReqDto> datas){
		String keyCode = "SPORTTOPCOACHS";
		List<SportUserReqDto> tops = null;
		if(redisTemplate.opsForValue().get(keyCode)!=null){
			tops = (List<SportUserReqDto>)redisTemplate.opsForValue().get(keyCode);
		}else{
			if (null != datas && datas.size() > 0){
				tops = new ArrayList<SportUserReqDto>();
				for (SportUserReqDto data : datas){
					if (null != data.getTrainerLevel() && data.getTrainerLevel() == TrainerLevel.TOP.getIndex()){
						tops.add(data);
					}
				}
				redisTemplate.opsForValue().set(keyCode, tops, 1, TimeUnit.HOURS);
			}
		}

		return tops;
	}

	/**
	 * 从缓存中查询顶级教练信息集合
	 * @return
	 * @author xiang_wang
	 * 2015年11月30日下午3:54:26
	 */
	@SuppressWarnings("unchecked")
	public List<SportUserReqDto> getSportTopCoachsByCache(){
		//		String keyCode = "SPORTTOPCOACHS";
		List<SportUserReqDto> tops = null;
		//		if(redisTemplate.opsForValue().get(keyCode)!=null){
		//			tops = (List<SportUserReqDto>)redisTemplate.opsForValue().get(keyCode);
		//		}else{
		tops = iUserService.findTopCoach();
		//			redisTemplate.opsForValue().set(keyCode, tops, 1, TimeUnit.HOURS);
		//		}

		return tops;
	}

	public ModelAndView getModelAndView(String teamId){
		ModelAndView mv = new ModelAndView();
		SportTeamDto sportTeamDto = getSportTeamByCache(teamId);
		//判断当前球队是否属于当前用户
		if(sportTeamDto!=null && getSessionUserId()!=null){
			logger.info("ownId"+sportTeamDto.getOwnerId()+"sessionId"+getSessionUserId());
			if(Objects.equal(sportTeamDto.getOwnerId(),getSessionUserId())){
				sportTeamDto.setOwner(true);
			}			
		}
		mv.addObject("sportTeamDto",sportTeamDto);
		mv.addObject("user", getUserAccountByUserId());
		mv.addObject("teamId",getTeamIdByUserId());
		mv.addObject("partnerList",loadPartnerList());
		mv.addObject("playerPosition",PlayerPosition.map);
		mv.addObject("formationMap",PlayerPosition.formationMap);
		mv.addObject("addressMap",loadMapAddress());
		mv.addObject("s_menu",iSportHomeMenuService.findSportHomeMenuList(null));
		return mv;
	}

	public ModelAndView getModelAndView(){
		ModelAndView mv = new ModelAndView();
//		mv.addObject("user", getUserAccountByUserId());
		mv.addObject("user", getSportUserByCache(getSessionUserId()));
		mv.addObject("teamId",getTeamIdByUserId());
		mv.addObject("partnerList",loadPartnerList());
		mv.addObject("playerPosition",PlayerPosition.map);
		mv.addObject("formationMap",PlayerPosition.formationMap);
		mv.addObject("addressMap",loadMapAddress());
		mv.addObject("s_menu",iSportHomeMenuService.findSportHomeMenuList(null));
		return mv;
	}

	public ModelAndView getUserModelAndView(String userId){
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", getSportUserByCache(userId));
		mv.addObject("teamId",getTeamIdByUserId());
		mv.addObject("partnerList",loadPartnerList());
		mv.addObject("playerPosition",PlayerPosition.map);
		mv.addObject("formationMap",PlayerPosition.formationMap);
		mv.addObject("addressMap",loadMapAddress());
		mv.addObject("s_menu",iSportHomeMenuService.findSportHomeMenuList(null));
		return mv;
	}

	public LoginUserDto getUserAccountByUserId(){
		LoginUserDto user = (LoginUserDto)getRequest().getSession().getAttribute(SessionConstants.LOGIN_USER_INFO);
		if(user==null){
			return null;
		}
		return user;		
	}

	public String getSessionUserId(){
		LoginUserDto user = (LoginUserDto)getRequest().getSession().getAttribute(SessionConstants.LOGIN_USER_INFO);
		if(user==null){
			return null;
		}
		return user.getId();
	}

	/**
	 * 查看當前登錄用戶是否存在球隊
	 */

	public String getTeamIdByUserId(){
		SportTeamDto teamDto = new SportTeamDto();
		if(StringUtils.isEmpty(getSessionUserId())){
			return null;
		}
		teamDto.setOwnerId(getSessionUserId());
		List<SportTeamDto> dataList = iTeamService.queryTeamByParams(teamDto);
		if(dataList!=null && dataList.size()>0){
			return dataList.get(0).getId();
		}else{
			return null;
		}
	}

	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	public Map<String,String> loadMapAddress(){
		String keyCode = "china_ddress";
		Map<String,String> dataMap = null;
		if(redisTemplate.opsForValue().get(keyCode)!=null){
			dataMap = (HashMap<String,String>)redisTemplate.opsForValue().get(keyCode);
		}else{
			List<SportAddressesDto>	dataList = iSportAddressesService.getAddresses("");
			dataMap = new HashMap<String, String>();
			for(SportAddressesDto dto : dataList){
				dataMap.put(String.valueOf(dto.getId()),dto.getName());
			}			
			redisTemplate.opsForValue().set(keyCode, dataMap, 24, TimeUnit.HOURS);
		}		
		return dataMap;
	}

}