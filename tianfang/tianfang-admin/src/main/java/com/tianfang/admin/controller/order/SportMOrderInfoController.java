package com.tianfang.admin.controller.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.order.dto.SportMOrderInfoDto;
import com.tianfang.order.service.ISportMOrderInfoService;
/**
 * 订单详情
 * @author mr
 *
 */
@Controller
@RequestMapping("/m/orderInfo")
public class SportMOrderInfoController extends BaseController {
	
	@Autowired
	private ISportMOrderInfoService iSportMOrderInfoService;
	
	
	public ModelAndView selectOrderInfo(SportMOrderInfoDto orderInfoDto,ExtPageQuery page){
		ModelAndView mv= this.getModelAndView(this.getSessionUserId());
		PageResult<SportMOrderInfoDto> pageList = iSportMOrderInfoService.selectOrderInfo(orderInfoDto,page.changeToPageQuery());
		try {
			mv.addObject("pageList",pageList);
			mv.addObject("orderInfoDto",orderInfoDto);
			mv.setViewName("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	@RequestMapping("/selectOrderInfo")
	public ModelAndView findOrderInfo(SportMOrderInfoDto orderInfoDto,ExtPageQuery page){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<SportMOrderInfoDto> pageList = iSportMOrderInfoService.findOrderInfo(orderInfoDto,page.changeToPageQuery());
		try {
			mv.addObject("pageList", pageList);
			mv.addObject("orderInfoDto", orderInfoDto);
			mv.setViewName("/sport/my_order/orderInfoList");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
}
