package com.tianfang.home.controller;

import java.util.Iterator;
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
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.dto.SportMOrderDto;
import com.tianfang.order.dto.SportMOrderToolsDto;
import com.tianfang.order.dto.SportMShoppingCartDto;
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
			if(StringUtils.isNotBlank(order.getSkuId())){
				//删除购物车
				Map<String,Object> map =  (Map<String, Object>) redisTemplate.opsForValue().get(key);
				//通过skuId 找到对应的无效的 某一条商品记录 删除
				Iterator entries = map.entrySet().iterator();
				while (entries.hasNext()) {
					Map.Entry entry = (Map.Entry) entries.next();
					SportMShoppingCartDto shopping= (SportMShoppingCartDto) entry.getValue();
					if(shopping.getSkuId().equals(order.getSkuId())){
						map.remove(shopping.getId());
					}
				}
				redisTemplate.opsForValue().set(key, map);
				mv.addObject("msg","您购买的"+order.getSkuName()+"商品，已经下架，请重新购买！");
				mv.setViewName("/m_order/pay-ready");
				return mv;
			}
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
	
	@RequestMapping("/payOrder")
	public ModelAndView payOrder(String id) {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		SportMOrderDto order= iSportMOrderService.findOrderById(id, null);
		mv.addObject("order",order);
		mv.addObject("user",getSportUserByCache(this.getSessionUserId()));
		mv.setViewName("/m_order/pay-ready");
		return mv;
	}
	
}
