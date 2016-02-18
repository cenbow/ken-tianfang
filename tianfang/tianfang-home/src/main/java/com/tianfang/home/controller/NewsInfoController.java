/**
 * 
 */
package com.tianfang.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.news.dto.SportNewsInfoDto;
import com.tianfang.news.service.ISportNewsInfoService;

/**
 * @author wk.s
 *
 */
@Controller
@RequestMapping("/news")
public class NewsInfoController extends BaseController {

	private final static int PAGESIZE = 10;
	
	@Autowired
	private ISportNewsInfoService newsInfoService;
	
	/**
	 * 查询所有新闻信息
	 * @return
	 */
	@RequestMapping("/findNewsInfo")
	public ModelAndView findNewsInfo(SportNewsInfoDto dto, PageQuery page){
		ModelAndView mv = getModelAndView();
		page.setPageSize(PAGESIZE);
		dto.setReleaseStat(DataStatus.ENABLED);
		PageResult<SportNewsInfoDto> pageList = newsInfoService.findNewsByParams(dto, page.getCurrPage(),page.getPageSize());
		mv.addObject("pageList", pageList);
		mv.setViewName("/news/news");
		return mv;
	}
	
	
	/**
	 * 新闻详情页面
	 * @return
	 */
	@RequestMapping("/details/{newsId}")
	public ModelAndView findNewsInfo(@PathVariable String newsId){
		
		ModelAndView mv = getModelAndView();
		SportNewsInfoDto news = newsInfoService.getNewsById(newsId);
		mv.addObject("news",news);
		mv.setViewName("/news/news_detail");
		return mv;
	}
}
