package com.tianfang.admin.controller.order;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.order.dto.SportMShippingAddressDto;
import com.tianfang.order.service.ISportMShippingAddressService;

@Controller
@RequestMapping("/product/address")
public class SportMShippingAddressController extends BaseController{

	public static Log logger = LogFactory.getLog(SportMShippingAddressController.class);
	
	@Autowired
	private ISportMShippingAddressService iSportMShippingAddressService;
	
	@RequestMapping("/findPage")
	public ModelAndView findPage(SportMShippingAddressDto sportMShippingAddressDto,ExtPageQuery page) {
		PageResult<SportMShippingAddressDto> results = iSportMShippingAddressService.findPage(sportMShippingAddressDto, page.changeToPageQuery());
		ModelAndView mv = this.getModelAndView(getSessionUserId());
		mv.addObject("pageList", results);
		mv.addObject("data", sportMShippingAddressDto);
		mv.setViewName("/sport/address/list");
		return mv;
	}
}
