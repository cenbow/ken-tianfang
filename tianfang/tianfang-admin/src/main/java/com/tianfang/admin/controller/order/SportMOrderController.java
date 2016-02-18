package com.tianfang.admin.controller.order;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.order.dto.SportMOrderDto;
import com.tianfang.order.service.ISportMOrderService;
/**
 * 商品订单
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/m/order")
public class SportMOrderController extends BaseController {
	@Autowired
	private ISportMOrderService iSportMOrderService;
	
	@RequestMapping("/selectOrder")
	public ModelAndView selectOrder(SportMOrderDto orderDto,ExtPageQuery page){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<SportMOrderDto> pagelist = iSportMOrderService.selectOrder(orderDto,page.changeToPageQuery());
		try {
			mv.addObject("pageList", pagelist);
			mv.addObject("orderDto",orderDto);
			mv.setViewName("/sport/my_order/list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping("/selectAllOrder")
	public ModelAndView selectAllOreder(SportMOrderDto orderDto,ExtPageQuery page){
		ModelAndView mv= this.getModelAndView(this.getSessionUserId());
		PageResult<SportMOrderDto> pageList = iSportMOrderService.selectAllOrder(orderDto,page.changeToPageQuery());
		try {
			mv.addObject("pageList",pageList);
			mv.addObject("orderDto",orderDto);
			mv.setViewName("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	public Map<String,Object> save(SportMOrderDto orderDto){
		long stat = iSportMOrderService.save(orderDto);
		if(stat>0){
			return MessageResp.getMessage(true,"添加成功");
		}
		return MessageResp.getMessage(false,"添加失败");
	}
}
