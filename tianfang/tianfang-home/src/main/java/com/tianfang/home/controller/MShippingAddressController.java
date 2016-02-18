package com.tianfang.home.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.business.dto.SportAddressesDto;
import com.tianfang.business.service.ISportAddressesService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.Response;
import com.tianfang.order.dto.SportMShippingAddressDto;
import com.tianfang.order.dto.SportMShoppingCartDto;
import com.tianfang.order.service.ISportMShippingAddressService;
/**
 * 商品-收货地址模块
 * @author mr
 *
 */
@Controller
@RequestMapping("/m_shipping")
public class MShippingAddressController extends BaseController {

	@Autowired
	private ISportAddressesService addressService;
	@Autowired
	private ISportMShippingAddressService shippingAddressService;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/toShipping")
	public ModelAndView toShippingAdd(SportMShippingAddressDto shipping,String id){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		String uId = this.getSessionUserId();					//当前用户id
		shipping.setUserId(uId);
		String key = uId+"Card";
		//用户收货地址的集合
		List<SportMShippingAddressDto> shipping_lis = shippingAddressService.findAll(shipping);
		SportMShippingAddressDto s_Marked = new SportMShippingAddressDto();
 		for (SportMShippingAddressDto sport_S : shipping_lis) {
			if(sport_S.getMaker() == 1){
				s_Marked = sport_S;
			}
		}
		
		//获取省份
		SportAddressesDto shi = new  SportAddressesDto();
		shi.setLevel(1);
		List<SportAddressesDto> dicLis = addressService.getDistrict(shi);
		
		//获取购物车信息
		List<SportMShoppingCartDto> carList = new ArrayList<SportMShoppingCartDto>();
		//购物车选中id
		@SuppressWarnings("unchecked")
		Map<String,Object> map =  (Map<String, Object>) redisTemplate.opsForValue().get(key);
		if(map!=null){
			Iterator entries = map.entrySet().iterator();
			//遍历购物车map 转换list
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				SportMShoppingCartDto shopping= (SportMShoppingCartDto) entry.getValue();
				//获取选中元素 
				if(id.contains(shopping.getId())){
					carList.add(shopping);
				}
			}
		}
		try {
			mv.addObject("smarked",s_Marked);
			mv.addObject("user",uId);
			mv.addObject("carList",carList);
			mv.addObject("shippingLis",shipping_lis);
			mv.addObject("dicLis", dicLis);
			mv.setViewName("/m_order/estore_addr");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/getAddressByCriteria")
	public Response<List<SportAddressesDto>> getAddressByCriteria(SportAddressesDto addresses){
		Response<List<SportAddressesDto>> result = new Response<List<SportAddressesDto>>();
		List<SportAddressesDto> dicLis = addressService.getDistrict(addresses);
		try {
			result.setData(dicLis);
			result.setStatus(DataStatus.ENABLED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/save")
	public Map<String,Object> save(SportMShippingAddressDto shippingAdd){
		String uId = this.getSessionUserId();
		if(uId==null){
			return MessageResp.getMessage(false,"请先登录,在做操作~~~");
		}
		shippingAdd.setUserId(uId);
		long stat = shippingAddressService.save(shippingAdd);
		if(stat > 0){
			return MessageResp.getMessage(true,"添加成功~~~");
		}
		return MessageResp.getMessage(false,"添加失败~~~");
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String,Object> delete(String id){
		long stat = shippingAddressService.delete(id);
		if(stat>0){
			return MessageResp.getMessage(true,"删除成功~~~");
		}
		return MessageResp.getMessage(false,"添加失败~~~");
	}
}
