package com.tianfang.home.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.business.dto.AlbumPictureDto;
import com.tianfang.business.dto.SportFutureStarDto;
import com.tianfang.business.dto.SportVideoDto;
import com.tianfang.business.service.IAlbumPicService;
import com.tianfang.business.service.ISportFutureStarService;
import com.tianfang.business.service.ISportVideoService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.constants.FuturestarCons;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;

/**
 * 未来之星(主题活动)前台页面
 * 
 * @author xiang_wang
 *
 * 2015年11月24日下午2:46:30
 */
@Controller
public class FutureStarController extends BaseController{
	protected static final Log logger = LogFactory.getLog(FutureStarController.class);
	private static final int SHOW_SIZE = 5;
	@Autowired
	private ISportFutureStarService starService;
	@Autowired
	private IAlbumPicService iAlbumPicService;
	@Autowired
	private ISportVideoService iSportVideoService;
	@Autowired
	private ISportVideoService sportVideoSerVice;
	
	
	/**
	 * 前台未来之星首页
	 * @return
	 * @author xiang_wang
	 * 2015年11月24日下午2:53:25
	 */
	@RequestMapping(value="/star/index")
	public ModelAndView starIndex(){
		List<SportFutureStarDto> stars = starService.findIndexFutureStars(0, SHOW_SIZE);
		ModelAndView mv = getModelAndView();
		mv.addObject("datas", stars);
		mv.setViewName("/futurestar/star");
		return mv;
	}
	
	/**
	 * 前台主题活动首页
	 * @return
	 * @author xiang_wang
	 * 2015年11月24日下午2:53:25
	 */
	@RequestMapping(value="/subject/index")
	public ModelAndView subjectIndex(){
		List<SportFutureStarDto> stars = starService.findIndexFutureStars(1, SHOW_SIZE);
		ModelAndView mv = getModelAndView();
		mv.addObject("datas", stars);
		mv.setViewName("/futurestar/subject");
		return mv;
	}
	
	/**
	 * 前台未来之星详情页
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2015年11月24日下午2:53:27
	 */
	@RequestMapping(value="/star/detail")
	public ModelAndView detail(@RequestParam(required=true) String id){
		SportFutureStarDto star = starService.getFutureStarById(id);
		ModelAndView mv = getModelAndView();
		mv.addObject("data", star);
		mv.setViewName("/futurestar/futurestar_detail");
		return mv;
	}
	
	@RequestMapping(value="/subject/detail")
	public ModelAndView subjectDetail(@RequestParam(required=true) String id){
		SportFutureStarDto star = starService.getFutureStarById(id);
		ModelAndView mv = getModelAndView();
		mv.addObject("data", star);
		mv.setViewName("/futurestar/subject_detail");
		return mv;
	}
	
	/**
	 * 未来之星 模板页面
	 * @return
	 * @author xiang_wang
	 * 2015年11月24日下午2:53:25
	 */
	@RequestMapping(value="/star/elite")
	public ModelAndView elite(){
		ModelAndView mv = getModelAndView();
		
		//获取精英训练营的数据
		SportFutureStarDto params = new SportFutureStarDto();
		params.setType(FuturestarCons.two);
		List<SportFutureStarDto> dataList = starService.findFutureStarByParams(params);
		mv.addObject("dataList", dataList);
		mv.setViewName("/futurestar/futurestar_elite");
		return mv;
	}
	
	@RequestMapping(value="/star/{starId}")
	public ModelAndView eliteDetails(@PathVariable String starId, @RequestParam(defaultValue="1") int currPage){
		ModelAndView mv = getModelAndView();
		
		//获取精英训练营的数据
		SportFutureStarDto params = new SportFutureStarDto();
		params.setType(DataStatus.ISUSER);
		params.setId(starId);
		List<SportFutureStarDto> dataList = starService.findFutureStarByParams(params);
		SportFutureStarDto data = null;
		if(!dataList.isEmpty()){
			data = dataList.get(0);
		}
		mv.addObject("data", data);
		mv.setViewName("/futurestar/subject_detail");
		return mv;
	}
	
//	futurestar_footballfan
	/**
	 * 
		 * 此方法描述的是：炫我足球范
		 * @author: cwftalus@163.com
		 * @version: 2015年11月27日 上午10:22:19
	 */
	@RequestMapping(value="/star/footballfan")
	public ModelAndView footballfan(@RequestParam(defaultValue="1") int picPage,@RequestParam(defaultValue="1") int videoPage){
		ModelAndView mv = getModelAndView();
		
		//列出相片
		AlbumPictureDto albumPictureDto = new AlbumPictureDto();
		PageQuery pagePic = new PageQuery(6);
		pagePic.setCurrPage(picPage);
		albumPictureDto.setPicStatus(DataStatus.ENABLED);
		PageResult<AlbumPictureDto> pageList = iAlbumPicService.findTeamAlbumPicByPage(albumPictureDto, pagePic);
		
		//列出视频
		PageQuery videoPic = new PageQuery(6);
		videoPic.setCurrPage(videoPage);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("videoStatus", DataStatus.ENABLED);//审核成功的视频
		PageResult<SportVideoDto> dataList = iSportVideoService.getCriteriaPage(videoPic, map);
		
		mv.addObject("pageList", pageList);
		mv.addObject("dataList", dataList);
		mv.setViewName("/futurestar/futurestar_footballfan");
		return mv;
	}
	
	@RequestMapping("/star/ajaxFootBallFanPic")
	@ResponseBody
	public Response<PageResult<AlbumPictureDto>> footBallFanByPic(@RequestParam(defaultValue="1") int picPage){
		Response<PageResult<AlbumPictureDto>> result = new Response<PageResult<AlbumPictureDto>>();
		
		AlbumPictureDto albumPictureDto = new AlbumPictureDto();
		PageQuery pagePic = new PageQuery(6);
		pagePic.setCurrPage(picPage);
		albumPictureDto.setPicStatus(DataStatus.ENABLED);
		PageResult<AlbumPictureDto> pageList = iAlbumPicService.findTeamAlbumPicByPage(albumPictureDto, pagePic);
		result.setData(pageList);
		return result;
	}
	
	@RequestMapping("/star/footBallFanByVideo")
	@ResponseBody
	public Response<PageResult<SportVideoDto>> footBallFanByVideo(@RequestParam(defaultValue="1") int videoPage){
		Response<PageResult<SportVideoDto>> result = new Response<PageResult<SportVideoDto>>();
		
		PageQuery videoPic = new PageQuery(6);
		videoPic.setCurrPage(videoPage);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("videoStatus", DataStatus.ENABLED);//审核成功的视频
		PageResult<SportVideoDto> dataList = iSportVideoService.getCriteriaPage(videoPic, map);
		
		result.setData(dataList);
		return result;
	}
}