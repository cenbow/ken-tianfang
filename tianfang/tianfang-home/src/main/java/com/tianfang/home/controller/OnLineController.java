package com.tianfang.home.controller;

import javax.servlet.http.HttpServletRequest;

import org.elasticsearch.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.onteach.dto.OnTeachDto;
import com.tianfang.onteach.dto.OnlineTeachColumnDto;
import com.tianfang.onteach.service.IOnTeachColumnService;
import com.tianfang.onteach.service.IOnTeachService;
import com.tianfang.user.dto.SportUserReqDto;
import com.tianfang.user.dto.TrainerLevel;

@Controller
@RequestMapping("/online")
public class OnLineController extends BaseController{

	@Autowired
	private IOnTeachService iOnTeachService;
	
	@Autowired
	private IOnTeachColumnService iOnTeachColumnService;
	
	@RequestMapping("/goalie")
	public ModelAndView goalie(OnTeachDto params, PageQuery page){
		ModelAndView mv = getModelAndView();
		mv.setViewName("/online/goalie");
		return mv;
	}
	
	
	@RequestMapping("/index")
	public ModelAndView index(OnlineTeachColumnDto params, PageQuery page){
		ModelAndView mv = getModelAndView();
		page.setPageSize(6);
		params.setStat(DataStatus.ENABLED);
//		PageResult<OnTeachDto> pageList = iOnTeachService.findOnTeachByParams(params, page.getCurrPage(), page.getPageSize());
		PageResult<OnlineTeachColumnDto> pageList = iOnTeachColumnService.findOnTeachByParams(params, page.getCurrPage(), page.getPageSize());
		mv.addObject("pageList", pageList);
		mv.setViewName("/online/index");
		return mv;
	}
	
	@RequestMapping(value="/columnlist")
	public ModelAndView columnlist(OnTeachDto params, PageQuery page){
		ModelAndView mv = getModelAndView();
		page.setPageSize(6);
		params.setStat(DataStatus.ENABLED);
		params.setCourseType(DataStatus.CARD_USR);//视频
		PageResult<OnTeachDto> dataList = iOnTeachService.findOnTeachByParams(params, page.getCurrPage(), page.getPageSize());
		mv.addObject("dataList", dataList);
		
		params.setCourseType(DataStatus.CODE_USR);//文档
		PageResult<OnTeachDto> pageList = iOnTeachService.findOnTeachByParams(params, page.getCurrPage(), page.getPageSize());
		mv.addObject("pageList", pageList);
		
		mv.addObject("params", params);
		mv.setViewName("/online/columnlist");
		return mv;
	}
	
	
	@RequestMapping("/ajaxPage")
	@ResponseBody
	public Response<PageResult<OnTeachDto>> footBallFanByPic(OnTeachDto params,@RequestParam(defaultValue="1") int picPage){
		Response<PageResult<OnTeachDto>> result = new Response<PageResult<OnTeachDto>>();
		PageQuery pagePic = new PageQuery(6);
		pagePic.setCurrPage(picPage);
		params.setStat(DataStatus.ENABLED);
		PageResult<OnTeachDto> pageList = iOnTeachService.findOnTeachByParams(params, pagePic.getCurrPage(), pagePic.getPageSize());
		result.setData(pageList);
		return result;
	}
	
	@RequestMapping("/iframe")
	public ModelAndView iframe(String vId){
		ModelAndView mv = getModelAndView();

		OnTeachDto teachDto = iOnTeachService.findObjectById(vId);
		
		mv.addObject("teachDto", teachDto);
		mv.setViewName("/online/iframe");
		return mv;
	}
	
	@RequestMapping("/view")
	public ModelAndView view(String vId){
		ModelAndView mv = getModelAndView();

		OnTeachDto teachDto = iOnTeachService.findObjectById(vId);
		
		mv.addObject("teachDto", teachDto);
		mv.setViewName("/online/view");
		return mv;
	}
	
	@RequestMapping(value="/clickCount")
	@ResponseBody
	public String clickCount(String oId){
		iOnTeachService.updateClickCount(oId);
		return "";
	}
	
	/**
	 * 跳转到视频播页面
	 * @param id  videoId
	 * @return
	 */
	@RequestMapping("/toVideo")
	public ModelAndView toVideo(String id,HttpServletRequest request){
		ModelAndView mv = getModelAndView();
		OnTeachDto onteachDto = iOnTeachService.findObjectById(id);
		
		SportUserReqDto userDto = getSportUserByCache(getSessionUserId());
		String videoUrl = "";
		if(userDto!=null){
			if(Objects.equal(userDto.getTrainerLevel(), TrainerLevel.VIP.getIndex())){
				videoUrl = onteachDto.getCourseFullUrl();
			}else{
				videoUrl = onteachDto.getCourseUrl();
			}
		}else{
			videoUrl = onteachDto.getCourseUrl();
		}
		onteachDto.setVideo("vcastr_file="+videoUrl.replace("\\", "/"));
		mv.addObject("result", onteachDto);
		mv.setViewName("video_details");
		return mv;
	}

}
