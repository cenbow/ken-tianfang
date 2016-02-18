package com.tianfang.home.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.company.dto.SportCompanyEventDto;
import com.tianfang.company.dto.SportCompanyRelatedDto;
import com.tianfang.company.service.CompanyRelatedService;
import com.tianfang.company.service.ISportCompanyEventService;

/**		
 * <p>Title: CompanyController </p>
 * <p>Description: 类描述:公司简介/大事纪要/诚聘精英/联系我们</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2015年12月4日下午4:05:38
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping(value = "/company")
public class CompanyController extends BaseController {
	
	private final static int PAGE_SIZE = 5;
	
	@Autowired
	private CompanyRelatedService companyService;
	@Autowired
	private ISportCompanyEventService eventService;
	
	/**
	 * 公司简介
	 * @return
	 * @author xiang_wang
	 * 2015年12月4日下午4:35:35
	 */
	@RequestMapping("/synopsis")
	public ModelAndView synopsis(){
		SportCompanyRelatedDto param = new SportCompanyRelatedDto();
		param.setType(1);
		List<SportCompanyRelatedDto> datas = companyService.findCompanies(param);
		ModelAndView mv = getModelAndView();
		if (!CollectionUtils.isEmpty(datas)){
			mv.addObject("data", datas.get(0));
		}
		mv.setViewName("/company/aboutus_company");
		return mv;
	}
	
	/**
	 * 公司大事纪要
	 * @param params
	 * @param page
	 * @return
	 * @author xiang_wang
	 * 2015年12月4日下午4:39:29
	 */
	@RequestMapping("/event")
	public ModelAndView event(SportCompanyEventDto params, PageQuery page){
		page.setPageSize(PAGE_SIZE);
		params.setReleaseStat(DataStatus.ENABLED);
		PageResult<SportCompanyEventDto> pageList = eventService.findPage(params, page);
		ModelAndView mv = getModelAndView();
		mv.addObject("pageList",pageList);
		mv.setViewName("/company/aboutus_event");
		return mv;
	}
	
	/**
	 * 诚聘英才
	 * @param params
	 * @param page
	 * @return
	 * @author xiang_wang
	 * 2015年12月4日下午5:08:18
	 */
	@RequestMapping("/recruit")
	public ModelAndView recruit(SportCompanyRelatedDto params, PageQuery page){
		page.setPageSize(PAGE_SIZE);
		params.setType(2);
		PageResult<SportCompanyRelatedDto> pageList = companyService.getCRInfoList(params, page);
		ModelAndView mv = getModelAndView();
		mv.addObject("pageList",pageList);
		mv.setViewName("/company/aboutus_recruit");
		return mv;
	}
	
	/**
	 * 联系我们
	 * @param params
	 * @param page
	 * @return
	 * @author xiang_wang
	 * 2015年12月4日下午5:32:28
	 */
	@RequestMapping("/contactus")
	public ModelAndView contactus(){
		SportCompanyRelatedDto param = new SportCompanyRelatedDto();
		param.setType(3);
		List<SportCompanyRelatedDto> datas = companyService.findCompanies(param);
		ModelAndView mv = getModelAndView();
		if (!CollectionUtils.isEmpty(datas)){
			mv.addObject("data", datas.get(0));
		}
		mv.setViewName("/company/aboutus_contactus");
		return mv;
	}
}