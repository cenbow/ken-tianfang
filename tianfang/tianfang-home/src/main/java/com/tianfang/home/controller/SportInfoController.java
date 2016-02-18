package com.tianfang.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.business.dto.SportInformationReqDto;
import com.tianfang.business.dto.SportInformationRespDto;
import com.tianfang.business.service.ISportInfoService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.news.dto.SportNewsInfoDto;

/**
 * 
	 * 此类描述的是：资讯模块
	 * @author: cwftalus@163.com
	 * @version: 2015年12月4日 下午4:14:34
 */
@Controller
@RequestMapping("/info")
public class SportInfoController extends BaseController{
	
	private final static int PAGESIZE = 10;
	
	@Autowired
	private ISportInfoService iSportInfoService;
	
	@RequestMapping("/list")
	public ModelAndView findNewsInfo(SportInformationReqDto dto, PageQuery page){
		ModelAndView mv = getModelAndView();
		page.setPageSize(PAGESIZE);
		dto.setReleaseStat(DataStatus.ENABLED);
		PageResult<SportInformationRespDto> pageList = iSportInfoService.findPage(dto, page);//.findNewsByParams(dto, page.getCurrPage(),page.getPageSize());
		mv.addObject("pageList", pageList);
		mv.setViewName("/info/infoList");
		return mv;
	}
	
	
	@RequestMapping("/details/{infoId}")
	public ModelAndView findNewsInfo(@PathVariable String infoId){
		ModelAndView mv = getModelAndView();
		SportInformationRespDto info = iSportInfoService.findById(infoId);
		mv.addObject("news",info);
		mv.setViewName("/info/info_detail");
		return mv;
	}
}
