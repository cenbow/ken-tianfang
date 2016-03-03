package com.tianfang.admin.controller.order;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.order.dto.SportMCategoryDto;
import com.tianfang.order.dto.SportMSpecDto;
import com.tianfang.order.dto.SportMTypeDto;
import com.tianfang.order.dto.SportMTypeSpecDto;
import com.tianfang.order.dto.SportTypeSpecExDto;
import com.tianfang.order.service.ISportMCategoryService;
import com.tianfang.order.service.ISportMSpecService;
import com.tianfang.order.service.ISportMTypeService;
import com.tianfang.order.service.ISportMTypeSpecService;

/**
 * 规格类型关联信息
 * @author mr.w
 */
@Controller
@RequestMapping("/typeSpec")
public class SportMTypeSpecController extends BaseController {

	@Autowired
	private ISportMTypeSpecService iSportMTypeSpecService;
	@Autowired
	private ISportMSpecService iSportMspecServie; 
	@Autowired
	private ISportMTypeService iSportMTypeService;
	@SuppressWarnings("rawtypes")
	@Autowired
	private SportRedisController redisController;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	
	@RequestMapping("/selectTypeSpec")
	public ModelAndView selectTypeSpec(SportTypeSpecExDto spexDto,ExtPageQuery page){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<SportTypeSpecExDto> pageList = iSportMTypeSpecService.selectTypeSpec(spexDto,page.changeToPageQuery());
		try {
			mv.addObject("spexDto", spexDto);
			mv.addObject("pageList",pageList);
			mv.setViewName("/sport/order_type_spec/list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String,Object> delete(String id){
		long stat = iSportMTypeSpecService.delete(id);
		if (stat >0) {
			redisController.addRedis(iSportMTypeSpecService.selectAll());
			return MessageResp.getMessage(true,"删除成功");
	    }
	    return MessageResp.getMessage(false,"删除失败");
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Map<String,Object> save(SportMTypeSpecDto mTypeSpec){
		List<SportMTypeSpecDto> obj = iSportMTypeSpecService.getByCriteria(mTypeSpec);
		if(obj.size()>0){
			return MessageResp.getMessage(true, "数据已经存在");
		}
		long stat = iSportMTypeSpecService.save(mTypeSpec);
		if(stat > 0){
			return MessageResp.getMessage(true, "新增成功");
		}
		return MessageResp.getMessage(false, "新增失败");
	}
	
	@RequestMapping("/toSave")
	public ModelAndView toSaveST(SportMTypeSpecDto mTypeSpec){
		ModelAndView mv= this.getModelAndView(this.getSessionUserId());
		/*List<SportMTypeDto> typeLis= (List<SportMTypeDto>) redisTemplate.opsForValue().get("SportMTypeDto") ;
		if(typeLis==null || typeLis.size()<1){*/
		List<SportMTypeDto> typeLis= iSportMTypeService.selectMTypeAll();
		/*	if(typeLis!=null){
				redisController.addRedis(typeLis);
			}
		}*/
		/*List<SportMSpecDto> specLis = (List<SportMSpecDto>) redisTemplate.opsForValue().get("SportMSpecDto");
		if(specLis==null || specLis.size()<1){*/
		List<SportMSpecDto> specLis = iSportMspecServie.selectAll();
			/*if(specLis!=null){
				redisController.addRedis(specLis);
			}
		}*/
		try {
			mv.addObject("typeLis",typeLis);
			mv.addObject("specLis",specLis);
			mv.setViewName("/sport/order_type_spec/add");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
}
