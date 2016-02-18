package com.tianfang.home.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.business.dto.SportVideoDto;
import com.tianfang.business.service.ISportVideoService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.StringUtils;
import com.tianfang.home.util.SessionUtil;
import com.tianfang.user.dto.LoginUserDto;
import com.tianfang.user.dto.SportUserReqDto;
import com.tianfang.user.service.IUserService;
/**
 * 视频信息
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/video")
public class SportVideoController extends BaseController{
	
	@Autowired
	private ISportVideoService sportVideoSerVice;
	@Autowired
	private IUserService iUserService;	
	
	protected static final Log logger = LogFactory.getLog(SportVideoController.class);
	/**
	 * 分页显示视频信息
	 * @param page
	 * @param videoDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllVideoPage")
	public Response<PageResult<SportVideoDto>> getAllVideoPage(ExtPageQuery page,SportVideoDto videoDto){
		Response<PageResult<SportVideoDto>> result =new Response<PageResult<SportVideoDto>>();
		videoDto.setVideoStatus(1);  //通过审批的视频信息  
		Map<String, Object> map = new HashMap<String, Object>();
		byCriteriaPage(videoDto,map);
		PageResult<SportVideoDto> pageList = sportVideoSerVice.getCriteriaPage(page.changeToPageQuery(),map);
		result.setData(pageList);
		return result;
	}
	
	/**
	 * 新增
	 * @param videoDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addVideo")
	public Map<String,Object> insertVideo(SportVideoDto videoDto){
		long stat = sportVideoSerVice.insertVideo(videoDto);
		if(stat==0){
			return MessageResp.getMessage(false,"添加失败~~~");
		}
		return MessageResp.getMessage(true,"添加成功~~~");
	}
	/**
	 * 修改
	 * @param videoDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editVideo")
	public Map<String,Object> editVideo(SportVideoDto videoDto){
		long stat = sportVideoSerVice.editVideo(videoDto);
		if(stat==0){
			return MessageResp.getMessage(false,"修改失败~~~");
		}
		return MessageResp.getMessage(true,"修改成功~~~");
	}
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delVideo")
	public Map<String,Object> delVideo(String ids){
		long stat = sportVideoSerVice.delVideo(ids);
		if(stat == 0){
			return MessageResp.getMessage(false,"删除失败~~~");
		}
		return MessageResp.getMessage(true,"删除成功~~~");
	}
	/**
	 * 检测条件
	 * @param teamDto
	 * @param map
	 */
	public void byCriteriaPage(SportVideoDto videoDto,Map<String,Object> map){
		if(!StringUtils.isEmpty(videoDto.getVideoName())){
			map.put("videoName", videoDto.getVideoName());
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
		if(!StringUtils.isEmpty(videoDto.getVideoStatus()+"")){
			map.put("videoStatus", videoDto.getVideoStatus());
		}
		if(!StringUtils.isEmpty(videoDto.getUserId())){
			map.put("userId", videoDto.getUserId());
		}
		if(videoDto.getVideoType()!=null){
			map.put("videoType", videoDto.getVideoType());
		}		
	}
	/**
	 * 跳转到视频播页面
	 * @param id  videoId
	 * @return
	 */
	@RequestMapping("/toVideo")
	public ModelAndView toVideo(String id,HttpServletRequest request){
		ModelAndView mv = getModelAndView();
		Map<String,Object> map = new  HashMap<String, Object>();
		map.put("id", id);
		SportVideoDto sportVideo = sportVideoSerVice.selectById(map);
		sportVideo.setVideo("vcastr_file="+sportVideo.getVideo().replace("\\", "/"));
		mv.addObject("result", sportVideo);
		mv.setViewName("video_details");
		return mv;
	}
	
	/**前台个人中心视屏列表显示
	 * @author YIn
	 * @time:2015年12月1日 下午4:23:37
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/myVidList")
	public ModelAndView myVidList(PageQuery page,HttpSession session) {
		ModelAndView mv = getModelAndView();
		// 获取当前登录用户Id
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		// 只允许登录用户进行操作
		if (loginUserDto == null) {
			mv.addObject("status", "500");
			mv.addObject("message", "用户没有登录!");
//			return new ModelAndView("redirect:/index.htm");
			return new ModelAndView("redirect:/index.htm");
		}
		String userId =loginUserDto.getId();
		//String userId = "17b5c436-1ab7-437e-8376-d98075b50a22";
		SportVideoDto dto = new SportVideoDto();
		Map<String, Object> map = new HashMap<String, Object>();
		dto.setVideoStatus(1);   //通过审核的视屏
		dto.setUserId(userId);
		byCriteriaPage(dto,map);
		page.setPageSize(8);
		PageResult<SportVideoDto> sportVideoDto = sportVideoSerVice.getCriteriaPage(page,map);
		SportUserReqDto userInfo= iUserService.selectUserAccountByUserId(loginUserDto.getId());
		if(sportVideoDto != null){
			mv.addObject("userInfo", userInfo);
			mv.addObject("status", "200");
			mv.addObject("message", "查询成功!");
			mv.addObject("pageList", sportVideoDto);
			mv.setViewName("/usercenter_myvid");
		}else{
			mv.addObject("status", "500");
			mv.addObject("message", "没有查到视频!");
		}
		return mv;
	}

	
	/**
	 * 
		 * 此方法描述的是：視頻中心
		 * @author: cwftalus@163.com
		 * @version: 2015年12月3日 上午9:43:49
	 */
//	@RequestMapping(value="/videoCenter")
//	public ModelAndView videoCenter(PageQuery page,Integer videoType){
//		ModelAndView mv = getModelAndView();
//		page.setPageSize(6);
//		
//		SportVideoDto dto = new SportVideoDto();
//		dto.setVideoStatus(1);   //通过审核的视屏
//		dto.setVideoType(videoType);
//		Map<String, Object> map = new HashMap<String, Object>();
//		byCriteriaPage(dto,map);
//		
//		PageResult<SportVideoDto> pageList = sportVideoSerVice.getCriteriaPage(page, map);
//		mv.addObject("pageList", pageList);
//		
//		//查询top10视频信息
//		List<SportVideoDto> dataList = sportVideoSerVice.findVideoByTop(10);
//		mv.addObject("dataList", dataList);
//		
//		mv.setViewName("/videoCenter");
//		return mv;
//	}
	
	/**
	 * 视频中心
	 * @param page
	 * @param videoType
	 * @return
	 */
	@RequestMapping(value="/videoCenter")
	public ModelAndView videoCenter(PageQuery page, SportVideoDto dto){
		ModelAndView mv = getModelAndView();
		page.setPageSize(6);
		dto.setVideoStatus(1);   //通过审核的视屏
		Map<String, Object> map = new HashMap<String, Object>();
		byCriteriaPage(dto,map);
		
		PageResult<SportVideoDto> pageList = sportVideoSerVice.getCriteriaPage(page, map);
		mv.addObject("pageList", pageList);
		
		//查询top10视频信息
		List<SportVideoDto> dataList = sportVideoSerVice.findVideoByTop(10,DataStatus.ENABLED);
		mv.addObject("dataList", dataList);
		
		mv.setViewName("/videoCenter");
		return mv;
	}
	
	/**
	 * 
		 * 此方法描述的是：点击数量计算
		 * @author: cwftalus@163.com
		 * @version: 2015年12月3日 上午10:27:37
	 */
	@RequestMapping(value="/clickCount")
	@ResponseBody
	public String clickCount(String videoId){
		sportVideoSerVice.updateClickCount(videoId);
		return "";
	}
	
	/**
	 * ajax根据参数分页查询视频
	 * @param dto
	 * @param currPage
	 * @return
	 * @author xiang_wang
	 * 2015年12月21日下午2:36:15
	 */
	@ResponseBody
	@RequestMapping(value="/search")
	public PageResult<SportVideoDto> search(SportVideoDto dto, ExtPageQuery page){
		Map<String, Object> param = assemblyParams(dto);
		PageResult<SportVideoDto> videos = sportVideoSerVice.getCriteriaPage(page.changeToPageQuery(), param);
		return videos;
	}
	
	private Map<String, Object> assemblyParams(SportVideoDto dto){
		Map<String, Object> result = new HashMap<String, Object>();
		if(null != dto){
			if(null != dto.getId() && !dto.getId().trim().equals("")){
				result.put("id", dto.getId());
			}
			if(null != dto.getVideoName() && !dto.getVideoName().trim().equals("")){
				result.put("videoName", dto.getVideoName().trim());
			}
			if(null != dto.getPublishPeople() && !dto.getPublishPeople().trim().equals("")){
				result.put("publishPeople", dto.getPublishPeople().trim());
			}
			if(null != dto.getTeamId() && !dto.getTeamId().trim().equals("")){
				result.put("teamId", dto.getTeamId().trim());
			}
			if(null != dto.getGameId() && !dto.getGameId().trim().equals("")){
				result.put("gameId", dto.getGameId().trim());
			}
			if(null != dto.getVideoStatus()){
				result.put("videoStatus", dto.getVideoStatus());
			}
			if(null != dto.getUserId() && !dto.getUserId().trim().equals("")){
				result.put("userId", dto.getUserId().trim());
			}
			if(null != dto.getVideoType()){
				result.put("videoType", dto.getVideoType());
			}
		}
		return result;
	}
}