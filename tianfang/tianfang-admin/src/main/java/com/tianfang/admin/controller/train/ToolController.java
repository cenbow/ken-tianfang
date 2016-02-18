package com.tianfang.admin.controller.train;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.admin.controller.PageData;


/** 
 * 类名称：ToolController
 * 创建人：FH 
 * 创建时间：2015年4月4日
 * @version
 */
@Controller
@RequestMapping(value="/tool")
public class ToolController extends BaseController {
	
	
	/**
	 * 地图页面
	 */
	@RequestMapping(value="/map")
	public ModelAndView map() throws Exception{
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("train_space/map");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 获取地图坐标页面
	 */
	@RequestMapping(value="/mapXY")
	public ModelAndView mapXY() throws Exception{
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("train_space/mapXY");
		mv.addObject("pd", pd);
		return mv;
	}
	
	
}
