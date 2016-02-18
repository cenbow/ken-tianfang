package com.tianfang.home.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.business.dto.AlbumDto;
import com.tianfang.business.dto.AlbumPictureDto;
import com.tianfang.business.dto.SportFutureStarBlogDto;
import com.tianfang.business.dto.SportFutureStarDto;
import com.tianfang.business.dto.SportVideoDto;
import com.tianfang.business.service.IAlbumPicService;
import com.tianfang.business.service.IAlbumService;
import com.tianfang.business.service.ISportFutureStarBlogService;
import com.tianfang.business.service.ISportFutureStarService;
import com.tianfang.business.service.ISportVideoService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.constants.FuturestarCons;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.home.dto.PhotoDto;
import com.tianfang.home.dto.PhotosDto;
import com.tianfang.news.dto.CoachTrainDto;
import com.tianfang.news.service.ICoachTrainService;
import com.tianfang.user.dto.Lecturer;
import com.tianfang.user.dto.SportUserReqDto;
import com.tianfang.user.service.IUserService;

/**
 * 顶级教练展示/教练博文展示操作
 * 
 * @author xiang_wang
 *
 * 2015年11月30日上午9:49:58
 */
@Controller
@RequestMapping(value = "/coach")
public class CoachSayController extends BaseController {
	@Autowired
	private IUserService userService;
	@Autowired
	private ISportFutureStarBlogService blogService;
	@Autowired
	private ISportFutureStarService starService;
	
	@Autowired
	private ISportVideoService iSportVideoService;
	
	@Autowired
	private IAlbumPicService iAlbumPicService;
	
	@Autowired
	private IAlbumService iAlbumService;
	
	@Autowired
	private ICoachTrainService iCoachTrainService;
	
	private final static int PAGESIZE = 6;
	
	/**
	 * 顶级教练首页展示
	 * @return
	 * @author xiang_wang
	 * 2015年11月30日上午10:44:39
	 */
	@RequestMapping("/index")
	public ModelAndView showAllCoach(){
		ModelAndView mv = getModelAndView();
		List<SportUserReqDto> datas = userService.findAllCoach();
		mv.addObject("tops",  this.getSportTopCoachsByCache(datas));
		mv.addObject("datas", datas);
		
		//课程内容 学院理念
		SportFutureStarDto params = new SportFutureStarDto();
//		params.setType(FuturestarCons.three);
		List<SportFutureStarDto> dataList = starService.findFutureStarByParams(params);
		mv.addObject("dataList", dataList);
		
		mv.setViewName("/coachsay/futurestar_college");
		return mv;
	}
	
	
	@RequestMapping(value="/star/{starId}")
	public ModelAndView eliteDetails(@PathVariable String starId){
		ModelAndView mv = getModelAndView();
		
		//获取精英训练营的数据
		SportFutureStarDto params = new SportFutureStarDto();
		params.setId(starId);
		List<SportFutureStarDto> dataList = starService.findFutureStarByParams(params);
		SportFutureStarDto data = null;
		if(!dataList.isEmpty()){
			data = dataList.get(0);
		}
		mv.addObject("data", data);
		
		//课程内容 学院理念
		params = new SportFutureStarDto();
//		params.setType(FuturestarCons.three);
		List<SportFutureStarDto> resultList = starService.findFutureStarByParams(params);
		mv.addObject("dataList", resultList);
		
		mv.setViewName("/coachsay/detail");
		return mv;
	}
	
	/**
	 * 教练详细页面
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2015年11月30日上午11:08:22
	 */
	@RequestMapping("/index/{userId}")
	public ModelAndView coachDetail(@PathVariable String userId, PageQuery page){
		SportFutureStarBlogDto params = new SportFutureStarBlogDto();
		params.setUserId(userId);
		page.setPageSize(PAGESIZE);
		PageResult<SportFutureStarBlogDto> result = blogService.findPage(params, page);
		ModelAndView mv = getUserModelAndView(userId);
		mv.addObject("tops", this.getSportTopCoachsByCache());
		mv.addObject("pageList", result);
		mv.setViewName("/coachsay/futurestar_college_coachsay");
		return mv;
	}
	
	/**
	 * 教练博文信息
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2015年11月30日下午3:16:04
	 */
	@RequestMapping("/blog")
	public ModelAndView blog(String id){
		SportFutureStarBlogDto blog = blogService.findById(id);
		if (null == blog){
			throw new RuntimeException("对不起,教练博文对象不存在!");
		}
		blogService.updateRead(id);
		ModelAndView mv = getUserModelAndView(blog.getUserId());
		mv.addObject("blog", blog);
		mv.addObject("blogs", this.getSportBlogByCache(blog.getUserId()));
		mv.setViewName("/coachsay/futurestar_college_coachsay_detail");
		return mv;
	}
	
	/**
	 * 
		 * 此方法描述的是：教练员培训页面
		 * @author: cwftalus@163.com
		 * @version: 2015年12月24日 上午11:24:38
	 */
	@RequestMapping("/trains")
	public ModelAndView trains(String id){
		ModelAndView mv = getModelAndView();
		
		//教练列表
		SportUserReqDto paramCos = new SportUserReqDto();
		paramCos.setLecturer(Lecturer.REALTRUE.getIndex());
		List<SportUserReqDto> coachList = userService.findCoachByParams(paramCos);
		mv.addObject("coachList", coachList);
		
		//培训专栏信息
		CoachTrainDto coachTrainDto  = new CoachTrainDto();
		coachTrainDto.setReleaseStat(DataStatus.ENABLED);
		List<CoachTrainDto> trainList = iCoachTrainService.findCoachTrainByParams(coachTrainDto);
		mv.addObject("trainList", trainList);
		
		//课程内容 学院理念
		SportFutureStarDto params = new SportFutureStarDto();
//		params.setType(FuturestarCons.three);
		List<SportFutureStarDto> resultList = starService.findFutureStarByParams(params);
		mv.addObject("dataList", resultList);
		
		//教练员培训队员的视频
		PageQuery page = new PageQuery();
		page.setPageSize(PAGESIZE);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("videoType", FuturestarCons.TRAINTYPE);
		map.put("videoStatus", DataStatus.ENABLED);
		PageResult<SportVideoDto> pageVideoList = iSportVideoService.getCriteriaPage(page, map);
		
		//教练员培训队员的图片
//		AlbumPictureDto albumPictureDto = new AlbumPictureDto();
//		albumPictureDto.setPicType(FuturestarCons.TRAINTYPE);
//		albumPictureDto.setPicStatus(DataStatus.ENABLED);
//		PageResult<AlbumPictureDto> pagePicList = iAlbumPicService.findTeamAlbumPicByPage(albumPictureDto, page);
		
		AlbumDto albumDto = new AlbumDto(); 
		albumDto.setPicType(FuturestarCons.TRAINTYPE);
		PageResult<AlbumDto> pagePicList =  iAlbumService.findPage(albumDto, page);//.findTeamAlbumPicByPage(albumPictureDto, page);
		
		mv.addObject("pageVideoList", pageVideoList);
		mv.addObject("pagePicList", pagePicList);
		mv.setViewName("/coachsay/futurestar_college_training");
		return mv;
	}
	
	
	@RequestMapping("/fpicList")
	@ResponseBody
	public PhotosDto fpicList(String albumId){
		PhotosDto photos = new PhotosDto();
		AlbumDto albumDto = iAlbumService.getAlbumById(albumId);
		photos.setTitle(albumDto.getTitle());

		AlbumPictureDto albumPictureDto = new AlbumPictureDto();
		albumPictureDto.setPicType(FuturestarCons.TRAINTYPE);
		albumPictureDto.setPicStatus(DataStatus.ENABLED);
		albumPictureDto.setAlbumId(albumId);
		List<AlbumPictureDto> dataList = iAlbumPicService.findTeamAlbumPic(albumPictureDto);
		Iterator<AlbumPictureDto> its = dataList.iterator();
		List<PhotoDto> photoList = new ArrayList<PhotoDto>();
		while(its.hasNext()){
			PhotoDto pDto = new PhotoDto();
			AlbumPictureDto to = its.next();
			pDto.setPid(to.getId());
			pDto.setSrc(to.getPic());
			pDto.setThumb(to.getPic());
			photoList.add(pDto);
		}
		photos.setData(photoList);
		return photos; 
	}
	
	@RequestMapping("/trains/details")
	public ModelAndView trains_details(String trainId){
		ModelAndView mv = getModelAndView();
		CoachTrainDto data = iCoachTrainService.selectCoachTrainById(trainId);
		data.setMicroPic("");
		mv.addObject("data", data);
		mv.setViewName("/coachsay/train_details");
		return mv;
	}
	
	
	@RequestMapping("/trains/coachdetails")
	public ModelAndView coachdetails(String coachId){
		ModelAndView mv = getModelAndView();
		SportUserReqDto data = userService.selectUserAccountByUserId(coachId);
		data.setContent(data.getDescription());
		mv.addObject("data", data);
		mv.setViewName("/coachsay/train_details");
		return mv;
	}	
}