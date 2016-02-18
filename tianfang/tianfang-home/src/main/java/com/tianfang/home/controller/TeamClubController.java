package com.tianfang.home.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.business.dto.AlbumDto;
import com.tianfang.business.dto.AlbumPictureDto;
import com.tianfang.business.dto.SportAddressesDto;
import com.tianfang.business.dto.SportHonorReqDto;
import com.tianfang.business.dto.SportHonorRespDto;
import com.tianfang.business.dto.SportNoticeDto;
import com.tianfang.business.dto.SportRaceDto;
import com.tianfang.business.dto.SportTeamDto;
import com.tianfang.business.dto.SportTeamMembersDto;
import com.tianfang.business.dto.SportVideoDto;
import com.tianfang.business.enums.RaceType;
import com.tianfang.business.service.IAlbumPicService;
import com.tianfang.business.service.IAlbumService;
import com.tianfang.business.service.ISportAddressesService;
import com.tianfang.business.service.ISportHonorService;
import com.tianfang.business.service.ISportNoticeService;
import com.tianfang.business.service.ISportRaceService;
import com.tianfang.business.service.ISportTeamMembService;
import com.tianfang.business.service.ISportVideoService;
import com.tianfang.business.service.ITeamService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.home.dto.RaceStatus;
import com.tianfang.user.dto.LoginUserDto;


/**
 * 
	 * 此类描述的是：球队信息处理
	 * @author: cwftalus@163.com
	 * @version: 2015年11月21日 下午1:23:23
 */
@Controller
@RequestMapping(value = "/tc")
public class TeamClubController extends BaseController{
	
	private final static int PAGESIZE = 10;
	
	//区域信息
	@Autowired
	private ISportAddressesService addressService;
	@Autowired
	private ISportRaceService raceService;
	@Autowired
	private ISportNoticeService noticeService;
	//赛事预告
	@Autowired
	private ISportRaceService iSportRaceService;
	//荣誉
	@Autowired
	private ISportHonorService iSportHonorService;
	//相册
	@Autowired
	private IAlbumService iAlbumService;
	//相片
	@Autowired
	private IAlbumPicService iAlbumPicService;
	//球队成员
	@Autowired
	private ISportTeamMembService iSportTeamMembService;
	//视频
	@Autowired
	private ISportVideoService sportVideoSerVice;
	@Autowired
	private ITeamService teamService;
	
	private Map<String, Object> AllTeamMap = new HashMap<String, Object>(2); // 用于缓存所有球队
	
	@RequestMapping(value="/index/{teamId}")
	public ModelAndView index(@PathVariable String teamId){
		//本页面后续静态化处理
		HashMap<String,Object> objMap = new HashMap<String,Object>();
		//球队信息
		ModelAndView mv = getModelAndView(teamId);
		//公告数据
		SportNoticeDto params = new SportNoticeDto();
		params.setTeamId(teamId);
		List<SportNoticeDto> noticeList = noticeService.findNoticeByParams(params);
		objMap.put("noticeList", noticeList);
		//比赛预告
		SportRaceDto furteSportRace = new SportRaceDto();
		furteSportRace.setTeamId(teamId);
		furteSportRace.setRaceStatus(RaceStatus.UNSTART.getIndex());//raceStatus
		List<SportRaceDto> raceList = iSportRaceService.findRaceByParams(furteSportRace);
		objMap.put("raceList", raceList);
		//历史成绩
		SportRaceDto historySportRace = new SportRaceDto();
		historySportRace.setTeamId(teamId);
		historySportRace.setRaceStatus(RaceStatus.END.getIndex());//raceStatus
		PageResult<SportRaceDto> historyRaceList = iSportRaceService.findRaceByParams(historySportRace,1,PAGESIZE);// findRaceByParams(historySportRace);
		objMap.put("historyRaceList", historyRaceList);
		//相片
		AlbumPictureDto albumPictureDto = new AlbumPictureDto();
		albumPictureDto.setTeamId(teamId);
		albumPictureDto.setLimit(PAGESIZE*2);
		List<AlbumPictureDto> albumPicList = iAlbumPicService.findTeamAlbumPic(albumPictureDto);
		objMap.put("albumPicList", albumPicList);
		//球队胜率
		
		//视频
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("teamId", teamId);
		PageQuery pageQ = new PageQuery(1);
		PageResult<SportVideoDto> pageList = sportVideoSerVice.getCriteriaPage(pageQ, map);
		SportVideoDto videoDto = null;
		if(pageList.getResults()!=null && pageList.getResults().size()>0){
			videoDto =  pageList.getResults().get(0);
		}
		objMap.put("videoDto", videoDto);
		
		//荣誉
		SportHonorReqDto sportHonorReqDto = new SportHonorReqDto();
		sportHonorReqDto.setTeamId(teamId);
		PageQuery page = new PageQuery(PAGESIZE);
		PageResult<SportHonorRespDto> honorList = iSportHonorService.findPage(sportHonorReqDto, page);
		objMap.put("honorList", honorList);
		
		mv.addObject("objMap", objMap);
		mv.addObject("types", RaceType.getValus());
		mv.setViewName("/team/youth-index");
		return mv;
	}
	/**
	 * 
		 * 此方法描述的是：球队荣誉页面
		 * @author: cwftalus@163.com
		 * @version: 2015年11月23日 上午10:38:39
	 */
	@RequestMapping(value="/honor/{teamId}")
	public ModelAndView honor(@PathVariable String teamId,SportHonorReqDto sportHonorReqDto,PageQuery page){
		ModelAndView mv = getModelAndView(teamId);
		page.setPageSize(PAGESIZE);
		sportHonorReqDto.setTeamId(teamId);
		PageResult<SportHonorRespDto> pageList = iSportHonorService.findPage(sportHonorReqDto, page);

		mv.addObject("pageList",pageList);
		mv.setViewName("/team/youth-honor");
		return mv;
	}
	
	/** 此方法描述的是：增加球队荣誉页面
	 * @author YIn
	 * @time:2015年11月27日 下午3:22:20
	 * @param teamId
	 * @param sportHonorReqDto
	 * @param page
	 * @return
	 */
	
	@RequestMapping(value="/addTeamHonor")
	public Map<String ,Object> honor(SportHonorReqDto sportHonorReqDto){
		logger.info("添加球队荣誉~~~");
		long statu = iSportHonorService.save(sportHonorReqDto);
		if(statu == 0){
			return MessageResp.getMessage(false,"添加失败~~~");
		}
		return MessageResp.getMessage(true,"添加成功~~~");
	}

	/**
	 * 
		 * 此方法描述的是：球队视频页面
		 * @author: cwftalus@163.com
		 * @version: 2015年11月23日 上午10:38:39
	 */
	@RequestMapping(value="/video/{teamId}")
	public ModelAndView video(@PathVariable String teamId,SportVideoDto sportVideoDto,PageQuery page){
		ModelAndView mv = getModelAndView(teamId);
		Map<String, Object> map = new HashMap<String, Object>();
		sportVideoDto.setTeamId(teamId);
		byCriteriaPage(sportVideoDto,map);
		page.setPageSize(6);
		PageResult<SportVideoDto> pageList = sportVideoSerVice.getCriteriaPage(page,map);
		mv.addObject("pageList",pageList);
		mv.setViewName("/team/youth_video");
		return mv;
	}
	
	
	/**
	 * 
		 * 此方法描述的是：球队相册页面
		 * @version: 2015年11月23日 上午10:38:39
		 * @author: cwftalus@163.com
	 */
	@RequestMapping(value="/pic/{teamId}")
	public ModelAndView pic(@PathVariable String teamId,AlbumDto dto, PageQuery page){
		ModelAndView mv = getModelAndView(teamId);
		dto.setTeamId(teamId);
		page.setPageSize(PAGESIZE);
		PageResult<AlbumDto> pageList = iAlbumService.findPage(dto, page);
		
		mv.addObject("pageList",pageList);
		
		mv.addObject("albumList",iAlbumService.findAlbumList(dto));
		mv.setViewName("/team/youth-pic");
		return mv;
	}
	
	
	public void byCriteriaPage(SportVideoDto videoDto,Map<String,Object> map){
		if(!StringUtils.isEmpty(videoDto.getVideoName())){
			map.put("name", videoDto.getVideoName());
		}
		if(!StringUtils.isEmpty(videoDto.getPublishPeople())){
			map.put("publishPeople", videoDto.getPublishPeople());
		}
		if(!StringUtils.isEmpty(videoDto.getTeamId())){
			map.put("teamId", videoDto.getTeamId());
		}
		if(!StringUtils.isEmpty(videoDto.getGameId())){
			map.put("gameId", videoDto.getGameId());
		}
		
		map.put("videoStatus",DataStatus.ENABLED);//前台的视频必须是审核通过
	}	
	/**
	 * 
		 * 此方法描述的是：球队公告页面
		 * @author: cwftalus@163.com
		 * @version: 2015年11月23日 上午10:38:39
	 */
	@RequestMapping(value="/notice/{teamId}")
	public ModelAndView notice(@PathVariable String teamId, @RequestParam(defaultValue="1") int currPage){
		ModelAndView mv = getModelAndView(teamId);
		SportNoticeDto params = new SportNoticeDto();
		params.setTeamId(teamId);
		PageResult<SportNoticeDto> pageList = noticeService.findNoticeByParams(params, currPage, PAGESIZE);
		
		mv.addObject("pageList",pageList);
		mv.setViewName("/team/youth-notice");
		return mv;
	}
	
	/**
	 * 跳转球队公告新增页面
	 * @param teamId
	 * @return
	 * @author xiang_wang
	 * 2015年11月30日上午9:07:19
	 */
	@RequestMapping(value="/notice/{teamId}/add")
	public ModelAndView noticeAdd(@PathVariable String teamId){
		ModelAndView mv = getModelAndView(teamId);
		mv.setViewName("/team/notice-add");
		return mv;
	}
	
	/**
	 * 新增球队公告
	 * @param teamId
	 * @param dto
	 * @return
	 * @author xiang_wang
	 * 2015年11月30日上午9:07:01
	 */
	@RequestMapping(value="/notice/{teamId}/save")
	@ResponseBody
	public Map<String, Object> noticeSave(@PathVariable String teamId, SportNoticeDto dto){
		LoginUserDto user = getUserAccountByUserId();
		SportTeamDto sportTeamDto = getSportTeamByCache(teamId);
		
		try {
			dto.setPublisher(user.getUserAccount());
			dto.setTeamId(teamId);
			dto.setTeamName(sportTeamDto.getName());
			noticeService.saveNotice(dto);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return MessageResp.getMessage(false,e.getMessage());
		}
		
		return MessageResp.getMessage(true,"保存成功~");
	}
	
	/**
	 * 跳转球队公告编辑页面
	 * @param teamId
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2015年11月30日上午9:08:17
	 */
	@RequestMapping(value="/notice/{teamId}/edit")
	public ModelAndView noticeEdit(@PathVariable String teamId, String id){
		ModelAndView mv = getModelAndView(teamId);
		SportNoticeDto notice = noticeService.getNoticeById(id);
		mv.addObject("notice", notice);
		mv.setViewName("/team/notice-edit");
		return mv;
	}
	
	/**
	 * 更新球队公告信息
	 * @param teamId
	 * @param dto
	 * @return
	 * @author xiang_wang
	 * 2015年11月30日上午9:10:26
	 */
	@RequestMapping(value="/notice/{teamId}/update")
	@ResponseBody
	public Map<String, Object> noticeUpdate(@PathVariable String teamId, SportNoticeDto dto){
		LoginUserDto user = getUserAccountByUserId();
		SportTeamDto sportTeamDto = getSportTeamByCache(teamId);
		try {
			dto.setPublisher(user.getUserAccount());
			dto.setTeamId(teamId);
			dto.setTeamName(sportTeamDto.getName());
			noticeService.updateNotice(dto);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return MessageResp.getMessage(false,e.getMessage());
		}
		
		return MessageResp.getMessage(true,"修改成功~");
	}
	
	/**
	 * 跳转球队公告删除操作
	 * @param teamId
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2015年12月3日上午10:40:44
	 */
	@RequestMapping(value="/notice/{teamId}/delete")
	@ResponseBody
	public Map<String, Object> noticeDelete(@PathVariable String teamId, String id){
		try {
			noticeService.deleteNotice(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return MessageResp.getMessage(false,e.getMessage());
		}
		
		return MessageResp.getMessage(true,"删除成功~");
	}
	
	/**
	 * 球队比赛预告
	 * @param teamId 球队id
	 * @return
	 * @author xiang_wang
	 * 2015年11月23日上午9:12:20
	 */
	@RequestMapping(value="/herald/{teamId}")
	public ModelAndView herald(@PathVariable String teamId, @RequestParam(defaultValue="1") int currPage){
		SportRaceDto params = new SportRaceDto();
		params.setTeamId(teamId);
		params.setRaceStatus(RaceStatus.UNSTART.getIndex()); 	// 比赛未开始
		PageResult<SportRaceDto> result = raceService.findRaceByParams(params, currPage, PAGESIZE);
		
		List<SportTeamDto> allTeam = getCacheAllTeam(1800000L);
		
		ModelAndView mv = getModelAndView(teamId);
		mv.addObject("pageList", result);
		mv.addObject("teamId",teamId);
		mv.addObject("allTeam", allTeam);
		mv.addObject("types", RaceType.getValus());
		mv.setViewName("/team/youth-herald");
		
		return mv;
	}
	
	/**
	 * 球队比赛预告信息修改操作
	 * @param teamId
	 * @param dto
	 * @return
	 * @author xiang_wang
	 * 2015年11月30日上午9:36:21
	 */
	@RequestMapping(value="/herald/{teamId}/update")
	@ResponseBody
	public Map<String, Object> heraldUpdate(@PathVariable String teamId, SportRaceDto dto){
		try {
			dto.setHomeTeamNumber(0);
			dto.setVsTeamNumber(0);
			dto.setRaceStatus(RaceStatus.UNSTART.getIndex());
			raceService.updateRace(dto);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return MessageResp.getMessage(false,e.getMessage());
		}
		
		return MessageResp.getMessage(true,"修改成功~");
	}
	
	/**
	 * 球队比赛预告信息删除操作
	 * @param teamId
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2015年12月1日下午4:25:57
	 */
	@RequestMapping(value="/herald/{teamId}/delete")
	@ResponseBody
	public Map<String, Object> heraldDelete(@PathVariable String teamId, String id){
		try {
			raceService.deleteRace(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return MessageResp.getMessage(false,e.getMessage());
		}
		
		return MessageResp.getMessage(true,"删除成功~");
	}
	
	/**
	 * 球队历史战绩
	 * @param teamId 球队id
	 * @return
	 * @author xiang_wang
	 * 2015年11月23日上午9:12:20
	 */
	@RequestMapping(value="/history/{teamId}")
	public ModelAndView history(@PathVariable String teamId, @RequestParam(defaultValue="1") int currPage){
		ModelAndView mv = getModelAndView(teamId);
		
		SportRaceDto params = new SportRaceDto();
		params.setTeamId(teamId);
		params.setRaceStatus(RaceStatus.END.getIndex()); 	// 比赛已结束
		PageResult<SportRaceDto> result = raceService.findRaceByParams(params, currPage, PAGESIZE);
		mv.addObject("pageList", result);
		mv.addObject("types", RaceType.getValus());
		mv.setViewName("/team/youth-history");
		return mv;
	}
	
	/**
	 * 根据id查询球队	 
	 * @param sdto
	 * @return
	 */
	@RequestMapping("/team/{teamId}")
	public ModelAndView getTeamInfoById(@PathVariable String teamId){
		
		ModelAndView mv = getModelAndView(teamId);
		SportTeamDto team = iSportTeamMembService.findTeamById(teamId);
		if(team.getWin()!=null){
			Double win = Double.parseDouble(String.valueOf(team.getWin()));
			Double total = Double.parseDouble(String.valueOf((team.getWin() + team.getDrew() + team.getLost())));;
			Double winRate = win / total;
			team.setWinRate(winRate);
		}
		
		team.setSetUpTimeVo(DateUtils.format(team.getSetUpTime(), "yyyy-MM-dd"));
		mv.addObject("team", team);
		if(team != null){
			SportTeamMembersDto mdto = new SportTeamMembersDto();
			mdto.setTid(teamId);
			List<SportTeamMembersDto> result = iSportTeamMembService.findMembers(mdto);
			mv.addObject("members", result);
		}
		mv.setViewName("/team/youth-team-info");
		return mv;
	}
	/**
	 * 
		 * 此方法描述的是：球队管理界面
		 * @author: cwftalus@163.com
		 * @version: 2015年12月2日 下午6:59:32
	 */
	@RequestMapping("/team/mg/{teamId}")
	public ModelAndView teamManage(@PathVariable String teamId){		
		ModelAndView mv = getModelAndView(teamId);
		//获取全部区县数据
		List<SportAddressesDto> lis = new ArrayList<SportAddressesDto>();
		SportAddressesDto addresses =new SportAddressesDto();
		addresses.setParentId("1");
		lis = addressService.getDistrict(addresses);
		
		mv.addObject("result", lis);
		
		//获取该该队伍的人员信息
		SportTeamMembersDto teamMemberDto = new SportTeamMembersDto();
		teamMemberDto.setTid(teamId);
		List<SportTeamMembersDto> members = iSportTeamMembService.findMembers(teamMemberDto);
		if(members.isEmpty()){
			members = null;
		}
 		mv.addObject("members",members);
 		//回显队徽
 		SportTeamDto team = iSportTeamMembService.findTeamById(teamId);
 		mv.addObject("team",team);
		mv.setViewName("/team/youth-team-edit");
		return mv;
	}
	
	/**
	 * 获取自定义缓存中的所有队伍信息
	 * @param time 自定义缓存时间(毫秒)
	 * @return
	 * @author xiang_wang
	 * 2015年12月1日下午2:48:57
	 */
	@SuppressWarnings("unchecked")
	private List<SportTeamDto> getCacheAllTeam(long time) {
		List<SportTeamDto> allTeam = null;
		long now = System.currentTimeMillis();
		if (AllTeamMap.containsKey("allTeam") && now - (Long)AllTeamMap.get("time") <= time){
			allTeam = (List<SportTeamDto>)AllTeamMap.get("allTeam");
		}else{
			allTeam = teamService.getAllTeam();
			AllTeamMap.put("allTeam", allTeam);
			AllTeamMap.put("time", System.currentTimeMillis());
		}
		return allTeam;
	}
}