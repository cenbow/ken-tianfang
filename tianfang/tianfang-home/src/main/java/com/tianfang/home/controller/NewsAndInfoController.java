/**
 * 
 */
package com.tianfang.home.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.business.dto.SportInformationReqDto;
import com.tianfang.business.dto.SportInformationRespDto;
import com.tianfang.business.service.ISportInfoService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.news.dto.SportNewsInfoDto;
import com.tianfang.news.service.ISportNewsInfoService;

/**
 * 专业咨询、媒体中心
 * @author wk.s
 * 2015年12月7日
 */
@Controller
@RequestMapping("newAndInfo")
public class NewsAndInfoController extends BaseController {

	private final static int PAGESIZE = 8;
	@Autowired
	private ISportInfoService iSportInfoService;
	@Autowired
	private ISportNewsInfoService newsInfoService;
	
	@RequestMapping("/list")
	public ModelAndView findNewsInfo(SportInformationReqDto dto, PageQuery page, String flag){
		ModelAndView mv = getModelAndView();
		page.setPageSize(PAGESIZE);
		dto.setReleaseStat(DataStatus.ENABLED);
		// 专业咨询
		dto.setType(10);
		PageResult<SportInformationRespDto> pageList1 = iSportInfoService.findPage(dto, page);//.findNewsByParams(dto, page.getCurrPage(),page.getPageSize());
		// 媒体中心

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderType", dto.getOrderType());
		map.put("releaseStat", DataStatus.ENABLED);
		PageResult<SportNewsInfoDto> pageList2 = newsInfoService.findNewsByParams(map, page.getCurrPage(),page.getPageSize());

		mv.addObject("pageList1", pageList1);
		mv.addObject("pageList2", pageList2);
		mv.setViewName("/info/news_and_info");
		return mv;
	} 
}
