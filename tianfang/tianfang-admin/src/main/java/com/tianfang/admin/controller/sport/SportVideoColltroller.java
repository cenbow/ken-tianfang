package com.tianfang.admin.controller.sport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.admin.controller.PageData;
import com.tianfang.business.dto.SportTeamDto;
import com.tianfang.business.dto.SportVideoDto;
import com.tianfang.business.service.ISportVideoService;
import com.tianfang.business.service.ITeamService;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.StringUtils;

@Controller
@RequestMapping("/video")
public class SportVideoColltroller extends BaseController {

	protected static final Log logger = LogFactory.getLog(SportVideoColltroller.class);
	@Autowired
	private ISportVideoService sportVideoSerVice;
	@Autowired
	private ITeamService sportTeamService;
	@Autowired
	private ITeamService iteamSevice;
	
	@RequestMapping(value="/videoPage")
	public ModelAndView getVideoPage(ExtPageQuery page,SportVideoDto videoDto){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		Map<String, Object> map = new HashMap<String, Object>();
		byCriteriaPage(videoDto,map);
		PageResult<SportVideoDto> pageList = sportVideoSerVice.getCriteriaPage(page.changeToPageQuery(),map);
		List<SportTeamDto> teamDto = sportTeamService.getAllTeam();
		mv.addObject("pageList", pageList);
		mv.addObject("pd",teamDto);
		mv.addObject("newDto",map);
		mv.setViewName("sport/video/video_list");
		return mv;
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
		sportVideo.setVideo(sportVideo.getVideo().replace("\\", "/"));
		mv.addObject("result", sportVideo);
		mv.setViewName("sport/video/video_details");
		return mv;
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
		if(!StringUtils.isEmpty(videoDto.getVideoStatus()+"")){
			map.put("videoStatus", videoDto.getVideoStatus());
		}
		if(videoDto.getVideoType()!=null){
			map.put("videoType", videoDto.getVideoType());
		}
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
		if (StringUtils.isBlank(videoDto.getVideo())) {
			videoDto.setVideo(null);
		}
		if (StringUtils.isBlank(videoDto.getVideoThumb())) {
			videoDto.setVideoThumb(null);
		}
		if (StringUtils.isBlank(videoDto.getTeamId())) {
			videoDto.setTeamId(null);
		}
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
			return MessageResp.getMessage(false,"添加失败~~~");
		}
		return MessageResp.getMessage(true,"添加成功~~~");
	}
	/**
	 * 去新增页面
	 * @return
	 */
	@RequestMapping("/toAdd")
	public ModelAndView toAdd(){
		logger.info("新增视频页面 ");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("/sport/video/video_add");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 去修改页面
	 * @return
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id){
		logger.info("修改视频页面 id="+id);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("id", id);
	    SportVideoDto sport = sportVideoSerVice.selectById(map);
	    List<SportTeamDto> sportTeamDtos = iteamSevice.getAllTeam();
		try {
			mv.setViewName("/sport/video/video_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", sport);
			mv.addObject("sportTeamDtos", sportTeamDtos);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
}
