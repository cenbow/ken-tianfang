package com.tianfang.home.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.business.dto.SportHomeMenuDto;
import com.tianfang.business.enums.ActivityTypeEnums;
import com.tianfang.business.service.ISportHomeMenuService;

/**
 * 
	 * 此类描述的是：针对单页的导航信息
	 * @author: cwftalus@163.com
	 * @version: 2015年12月15日 下午2:21:14
 */

@Controller
public class MenuController extends BaseController{	
	@Autowired
	private ISportHomeMenuService iSportHomeMenuService;
	
	@RequestMapping("/SportStore")
	public ModelAndView SportStore(){
		ModelAndView mv = getModelAndView();
		
		mv.setViewName("/menu/sportStore");
		return mv;
	}
	
	@RequestMapping("/transfermarket")
	public ModelAndView Transfermarket(){
		ModelAndView mv = getModelAndView();
		
		mv.setViewName("/menu/transfermarket");
		return mv;
	}
	
	@RequestMapping("/ydcp")
	public ModelAndView yidongchanp(){
		ModelAndView mv = getModelAndView();
		
		mv.setViewName("/menu/yidongchanp");
		return mv;
	}
	
	@RequestMapping("/menu/detail")
	public ModelAndView detail(String id){
		ModelAndView mv = getModelAndView();
		SportHomeMenuDto dto = iSportHomeMenuService.findById(id);
		String viewName = ActivityTypeEnums.getView(dto.getActivityType());
		List<SportHomeMenuDto> datas = iSportHomeMenuService.findByParentId(id);
		if (null != datas && datas.size() > 0){
			mv.addObject("datas", datas);	
		}
		mv.addObject("data", dto);
		mv.setViewName(viewName);
		return mv;
	}
}