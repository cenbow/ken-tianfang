package com.tianfang.home.controller;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.common.model.MessageResp;
import com.tianfang.order.dto.SportMOrderDto;
import com.tianfang.order.dto.SportMOrderToolsDto;
import com.tianfang.order.service.ISportMOrderInfoService;
import com.tianfang.order.service.ISportMOrderService;

@Controller
@RequestMapping("/order")
public class SportMOrderController extends BaseController{

	protected static final Log logger = LogFactory.getLog(SportMOrderController.class);
	
	@Autowired
	private ISportMOrderService iSportMOrderService;

	@Autowired
	private ISportMOrderInfoService iSportMOrderInfoService;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@RequestMapping("/user/delete")
	@ResponseBody
	public Map<String, Object> orderDelete(String orderId) {
		Integer result = iSportMOrderService.orderDelete(orderId);
		if (null != result && result == 1) {
			return MessageResp.getMessage(true, "删除成功！");
		}
		return MessageResp.getMessage(false, "删除失败！");
	}
	
	
	
	@SuppressWarnings({"unchecked" })
	@RequestMapping("/addOrder")
	public ModelAndView addOrder(SportMOrderToolsDto tools){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		String key = this.getSessionUserId()+"Card";
		String[] carId = tools.getCarId().split(",");
		if(this.getSessionUserId()==null || this.getSessionUserId().equals("")){
			return mv;
		}
		tools.setUserId(this.getSessionUserId());
		//生成订单  返回订单信息
		SportMOrderDto order= iSportMOrderService.addOrder(tools);
		if(order!=null){
			//删除购物车
			Map<String,Object> map =  (Map<String, Object>) redisTemplate.opsForValue().get(key);
			for (int i = 0; i < carId.length; i++) {
				map.remove(carId[i]);
			}
			redisTemplate.opsForValue().set(key, map);
		}
		
		mv.addObject("order",order);
		mv.addObject("user",getSportUserByCache(this.getSessionUserId()));
		mv.setViewName("/m_order/pay-ready");
		return mv;
	}
}
