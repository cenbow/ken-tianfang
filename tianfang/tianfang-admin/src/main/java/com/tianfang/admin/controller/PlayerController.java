package com.tianfang.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
	 * 此类描述的是：用户（会员）信息表 操作类
	 * @author: cwftalus@163.com
	 * @version: 2015年7月16日 下午1:39:40
 */
@Controller
@RequestMapping(value = "/player")
public class PlayerController extends BaseController{
	
/*	protected static final Log logger = LogFactory.getLog(PlayerController.class);
	
	@Autowired
	private IMenuService iMenuService;
	
	@Autowired
	private ITrainMenuService iTrainMenuService;
	
	@RequestMapping("text.do")
	@ResponseBody
	public List<MenuRespDto> index(HttpServletRequest request, HttpSession session){
	
		TrainingAdminDto user = (TrainingAdminDto) session.getAttribute("admin"); // 当前登录用户
		List<MenuRespDto> menuList = iTrainMenuService.findMenuByAdminId(user.getId());
//		mv.setViewName("/admin/index");
//		mv.addObject("menuList", menuList);
		
//		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
//		ListResult<AdminMenuFunctionDto> result = iMenuService.findAll();
//		
////		HashMap<String,String> map = new HashMap<String,String>();
////		map.put("aaa", "aaa");
////		map.put("ddd", "ddd");
////		request.setAttribute("info", map);
//		
//		mv.setViewName("/menu/index");
//		mv.addObject("result", result);
		return menuList;
	}*/

	

}
