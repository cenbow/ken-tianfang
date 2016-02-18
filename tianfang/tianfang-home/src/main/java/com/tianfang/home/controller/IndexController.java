/**
 * 
 */
package com.tianfang.home.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.business.dto.SportHomeDto;
import com.tianfang.business.dto.SportInformationRespDto;
import com.tianfang.business.service.ISportHomeMenuService;
import com.tianfang.business.service.ISportInfoService;
import com.tianfang.business.service.SportHomeService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.news.dto.SportNewsInfoDto;
import com.tianfang.news.service.ISportNewsInfoService;

/**
 * @author wk.s
 *
 */

@Controller
public class IndexController extends BaseController{

	@Autowired
	private SportHomeService sportHomeService;
	
	@Autowired
	private ISportNewsInfoService iSportNewsInfoService;
	
	@Autowired
	private ISportInfoService iSportInfoService;
		
	/**
	 * 跳转到主页
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView homePage(){
		ModelAndView mv = getModelAndView();
		//首页信息
		SportHomeDto dto = new SportHomeDto();
		List<SportHomeDto> homeList = sportHomeService.findNRecords(dto, null);
		
		//新闻
		HashMap<String,Object> newsParams = new HashMap<String,Object>();
		newsParams.put("marked",DataStatus.ENABLED);
		newsParams.put("releaseStat",DataStatus.ENABLED);
		List<SportNewsInfoDto> newsList = iSportNewsInfoService.findNewsByParams(newsParams);
		
		//资讯
		HashMap<String,Object> infoParams = new HashMap<String,Object>();
		infoParams.put("marked",DataStatus.ENABLED);
		infoParams.put("releaseStat",DataStatus.ENABLED);
		List<SportInformationRespDto> infoList = iSportInfoService.findInfoByParams(infoParams);
		
		mv.addObject("homeList",homeList);
		mv.addObject("newsList",newsList);
		mv.addObject("infoList",infoList);

		mv.setViewName("/index");
		return mv;
		
//		SportHomeDto dto = new SportHomeDto();
//		dto.setStat(1);
//		// 轮播图
//		dto.setType(1);
//		List<SportHomeDto> orderLst = sportHomeService.findNRecords(dto, null);
//		// 青训对战(单条记录)
//		dto.setType(2);
//		List<SportHomeDto> trainLst = sportHomeService.findNRecords(dto, null);
//		// 足球学院(单条记录)
//		dto.setType(3);
//		List<SportHomeDto> academyLst = sportHomeService.findNRecords(dto, null);
//		// 专业咨询(单条记录)
//		dto.setType(4);
//		List<SportHomeDto> profLst = sportHomeService.findNRecords(dto, null);
//		// 视频(单条记录)
//		dto.setType(5);
//		List<SportHomeDto> videoLst = sportHomeService.findNRecords(dto, null);
//		// 新闻	
//		SportHomeDto newsDto = new SportHomeDto();
//		newsDto.setType(6);
//		List<SportHomeDto> newsLst = sportHomeService.findNRecords(newsDto, 3);
//		// 活动(单条记录)
//		dto.setType(7);
//		List<SportHomeDto> actLst = sportHomeService.findNRecords(dto, null);
//		// 合作伙伴
//		dto.setType(8);
//		List<SportHomeDto> cooperLst = sportHomeService.findNRecords(dto, null);
//		
//		if((orderLst != null) && (orderLst.size() != 0)){
//			mv.addObject("orderLst",  orderLst);
//		}
//		if((cooperLst != null) && (cooperLst.size() != 0)){
//			mv.addObject("cooperLst",  cooperLst);
//		}
//		if((trainLst != null) && (trainLst.size() != 0)){
//			mv.addObject("train",  trainLst.get(0));
//		}
//		if((academyLst != null) && (academyLst.size() != 0)){
//			mv.addObject("academy",  academyLst.get(0));
//		}
//		if((profLst != null) && (profLst.size() != 0)){
//			mv.addObject("prof",  profLst.get(0));
//		}
//		if((videoLst != null) && (videoLst.size() != 0)){
//			mv.addObject("video",  videoLst.get(0));
//		}
//		if((newsLst != null) && (newsLst.size() != 0)){
//			mv.addObject("newsLst",  newsLst);
//		}
//		if((actLst != null) && (actLst.size() != 0)){
//			mv.addObject("act",  actLst.get(0));
//		}

	}
}
