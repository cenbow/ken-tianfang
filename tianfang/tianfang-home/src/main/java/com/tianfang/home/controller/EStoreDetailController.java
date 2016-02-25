package com.tianfang.home.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.redis.RedisService;
import com.tianfang.common.util.StringUtils;
import com.tianfang.home.util.SessionUtil;
import com.tianfang.order.dto.SportMEvaluateDto;
import com.tianfang.order.dto.SportMOrderInfoDto;
import com.tianfang.order.dto.SportMProductSpuDto;
import com.tianfang.order.dto.SportMShoppingCartDto;
import com.tianfang.order.dto.SportMSpecProductDto;
import com.tianfang.order.dto.SportMSpecValuesDto;
import com.tianfang.order.dto.SportMUserOrderDto;
import com.tianfang.order.service.ISportMEvaluateService;
import com.tianfang.order.service.ISportMOrderService;
import com.tianfang.order.service.ISportMProductSkuService;
import com.tianfang.order.service.ISportMProductSpuService;
import com.tianfang.user.dto.LoginUserDto;

@Controller
@RequestMapping("/estore/detail")
public class EStoreDetailController extends BaseController{

	@Autowired
	private ISportMProductSpuService iSportMProductSpuService;
	
	@Autowired
	private ISportMProductSkuService iSportMProductSkuService;
	
	@Autowired
	private ISportMOrderService iSportMOrderService;
	
	@Autowired
	private ISportMEvaluateService iSportMEvaluateService;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * redis 添加
	 * @param spec
	 */
	@RequestMapping(value = "/addRedis")
	public void addRedis(SportMShoppingCartDto sportMShoppingCartDto){
		String key = this.getSessionUserId();
		redisTemplate.opsForValue().set(key, sportMShoppingCartDto);
	}
	
	@RequestMapping(value = "/saveOrderStatus")
	@ResponseBody
	public Map<String, Object> saveOrderStatus(String orderId,Integer orderStatus) {
		Integer result = iSportMOrderService.saveOrderStatus(orderId, orderStatus);
		if (null != result && result == 1) {
			return MessageResp.getMessage(true, "修改成功！");
		}
		return MessageResp.getMessage(true, "修改失败！");
	}
	
	@RequestMapping(value = "/saveEvaluate")
	@ResponseBody
	public Map<String, Object> saveEvaluate(SportMEvaluateDto sportMEvaluateDto) {
		if  (StringUtils.isBlank(sportMEvaluateDto.getPic())) {
			sportMEvaluateDto.setPic(null);
		}
		Integer result = iSportMEvaluateService.saveEvaluate(sportMEvaluateDto);
		if (null != result && result == 1) {
			return MessageResp.getMessage(true, "评论成功！");
		}
		return MessageResp.getMessage(true, "评论失败！");
	}
	
	@RequestMapping(value = "/userOrder")
	public ModelAndView findOrderByUser(ExtPageQuery page,HttpSession session) {
		ModelAndView mv = this.getModelAndView();
		// 获取当前登录用户Id
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		// 只允许登录用户进行操作
		if (loginUserDto == null) {
			mv.addObject("status", "500");
			mv.addObject("message", "用户没有登录!");
//					return new ModelAndView("redirect:/index.htm");
			return new ModelAndView("redirect:/index.htm");
		}
		String userId =loginUserDto.getId();
		PageResult<SportMUserOrderDto> sportMUserOrderDtos = iSportMOrderService.findOrderByUser(userId, page.changeToPageQuery());
		mv.addObject("pageList", sportMUserOrderDtos);
		mv.addObject("userId", userId);
		mv.addObject("userInfo",getSportUserByCache(userId));
		mv.setViewName("/m_order/usercenter_myorders");
		return mv;
	}
	
	@RequestMapping(value = "/findUserOrderByPage")
	@ResponseBody
	public Response<PageResult<SportMUserOrderDto>> findUserOrderByPage(@RequestParam(defaultValue="1") int page,String userId){
		Response<PageResult<SportMUserOrderDto>> result = new Response<PageResult<SportMUserOrderDto>>();
		PageQuery pages = new ExtPageQuery().changeToPageQuery();
		pages.setCurrPage(page);
		PageResult<SportMUserOrderDto> spPageResult = iSportMOrderService.findOrderByUser(userId, pages);
		result.setData(spPageResult);
		return result;
	}
	
	@RequestMapping(value = "/getProduct")
	public ModelAndView findById(String id,ExtPageQuery page) {
		SportMProductSpuDto sportMProductSpuDto = iSportMProductSpuService.findProductById(id);
		List<SportMSpecProductDto> sportMSpecProductDtos =  iSportMProductSkuService.findAllSkuByProductId(id);
		PageResult<SportMOrderInfoDto>  sportMOrderInfoDtos = iSportMProductSpuService.selectOrderBySpu(id, page.changeToPageQuery());
		PageResult<SportMEvaluateDto>  sportMEvaluateDtos = iSportMProductSpuService.selectEvaluate(id, null, null,page.changeToPageQuery());
		PageResult<SportMEvaluateDto>  sportMEvaluateDtosGood = iSportMProductSpuService.selectEvaluate(id, 1,null, page.changeToPageQuery());
		PageResult<SportMEvaluateDto>  sportMEvaluateDtosIn = iSportMProductSpuService.selectEvaluate(id, 2,null, page.changeToPageQuery());
		PageResult<SportMEvaluateDto>  sportMEvaluateDtosPic = iSportMProductSpuService.selectEvaluate(id, null,"1", page.changeToPageQuery());
		ModelAndView mv = this.getModelAndView();
		String[] pics = null;
		if(null != sportMProductSpuDto && StringUtils.isNotBlank(sportMProductSpuDto.getPic())) {
			pics = sportMProductSpuDto.getPic().split(",");
		}
		mv.addObject("sportMProductSpuDto", sportMProductSpuDto);
		mv.addObject("pics", pics);
		mv.addObject("specVaules", sportMSpecProductDtos);
		mv.addObject("sportMOrderInfoDtos", sportMOrderInfoDtos);
		mv.addObject("sportMEvaluateDtos", sportMEvaluateDtos);
		mv.addObject("sportMEvaluateDtosGood", sportMEvaluateDtosGood);
		mv.addObject("sportMEvaluateDtosIn", sportMEvaluateDtosIn);
		mv.addObject("sportMEvaluateDtosPic", sportMEvaluateDtosPic);
		mv.setViewName("/m_order/estore_detail");
		return mv;
	}
	
	@RequestMapping(value = "/findByPage")
	@ResponseBody
	public Response<PageResult<SportMEvaluateDto>> findByPage(@RequestParam(defaultValue="1") int page,String id,Integer evaluateStatus,String pic) {
		Response<PageResult<SportMEvaluateDto>> result = new Response<PageResult<SportMEvaluateDto>>();
		PageQuery pages = new ExtPageQuery().changeToPageQuery();
		pages.setCurrPage(page);
		PageResult<SportMEvaluateDto>  sportMEvaluateDtos = iSportMProductSpuService.selectEvaluate(id, evaluateStatus, pic,pages);
		result.setData(sportMEvaluateDtos);
		return result;
	} 	
	
	@RequestMapping(value = "/findOrderByPage")
	@ResponseBody
	public Response<PageResult<SportMOrderInfoDto>> findOrderByPage (@RequestParam(defaultValue="1") int page,String id) {
		Response<PageResult<SportMOrderInfoDto>> result = new Response<PageResult<SportMOrderInfoDto>>();
		PageQuery pages = new ExtPageQuery().changeToPageQuery();
		pages.setCurrPage(page);
		PageResult<SportMOrderInfoDto>  sportMOrderInfoDtos = iSportMProductSpuService.selectOrderBySpu(id, pages);
		result.setData(sportMOrderInfoDtos);
		return result;
	}
	
	@RequestMapping(value = "/findpaysucc")
	public ModelAndView returnSucc(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("/m_order/pay_success");
		return mv;
	}
}
