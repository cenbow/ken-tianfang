package com.tianfang.admin.controller.order;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.admin.controller.PageData;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.dto.SportMCategoryDto;
import com.tianfang.order.dto.SportMTypeDto;
import com.tianfang.order.service.ISportMCategoryService;

@Controller
@RequestMapping("/sport/category")
public class SportMCategoryController extends BaseController{

	protected static final Log logger = LogFactory.getLog(SportMCategoryController.class);
	
	@Autowired
	private ISportMCategoryService iSportMCategoryService;
	@SuppressWarnings("rawtypes")
	@Autowired
	private SportRedisController redisController;
	
	@RequestMapping("/findPage")
	public ModelAndView findPage(SportMCategoryDto sportMCategoryDto,ExtPageQuery page) {
		PageResult<SportMCategoryDto> result = iSportMCategoryService.findPage(sportMCategoryDto, page.changeToPageQuery());
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		List<SportMTypeDto> sportMTypeDtos = iSportMCategoryService.getAllType();
		List<SportMCategoryDto> sportMCategoryDtos = iSportMCategoryService.findAllCategory();
		mv.addObject("category", sportMCategoryDtos);
		mv.addObject("type", sportMTypeDtos);
		mv.addObject("data", sportMCategoryDto);
		mv.addObject("pageList", result);
		mv.setViewName("/sport/category/list");
		return mv;
	}
	
	@RequestMapping("/openAddView")
	public ModelAndView openAddView() {
		logger.info("打开新增分类页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<SportMTypeDto> sportMTypeDtos = iSportMCategoryService.getAllType();
		List<SportMCategoryDto> sportMCategoryDtos = iSportMCategoryService.findAllCategory();
		mv.addObject("category", sportMCategoryDtos);
		mv.addObject("page", sportMTypeDtos);
		mv.setViewName("/sport/category/add");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);		
		return mv;
	}
	
	@RequestMapping("/openEditView")
	public ModelAndView openEditView() {
		logger.info("打开编辑分类页面");
		ModelAndView mv =this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String id = pd.getString("id");
		SportMCategoryDto sportMCategoryDto = iSportMCategoryService.findById(id);
		List<SportMTypeDto> sportMTypeDtos = iSportMCategoryService.getAllType();
		List<SportMCategoryDto> sportMCategoryDtos = iSportMCategoryService.findAllCategory();
		mv.addObject("category", sportMCategoryDtos);
		mv.addObject("page", sportMTypeDtos);
		mv.setViewName("/sport/category/edit");
		mv.addObject("data", sportMCategoryDto);
		mv.addObject("pd", pd);
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(SportMCategoryDto sportMCategoryDto) {
		Integer result = iSportMCategoryService.save(sportMCategoryDto);
		if (result == 1) {
			redisController.addRedis(iSportMCategoryService.findAllCategory());
            return MessageResp.getMessage(true,"操作成功");
        }
        return MessageResp.getMessage(false,"操作失败");
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(String ids) {
		if (StringUtils.isBlank(ids)) {
			return MessageResp.getMessage(false, "请求参数不能为空~") ;
		}
		SportMCategoryDto sportMCategoryDto = (SportMCategoryDto) iSportMCategoryService.delete(ids);
		if (DataStatus.DISABLED == sportMCategoryDto.getDeleteStat()) {
			return MessageResp.getMessage(false, "删除失败，分类'"+sportMCategoryDto.getCategoryName()+"'已被使用不能删除");
		}
		if (DataStatus.ENABLED == sportMCategoryDto.getDeleteStat()) {
			redisController.addRedis(iSportMCategoryService.findAllCategory());
			return MessageResp.getMessage(true, "删除成功！");
		}
		return MessageResp.getMessage(false, "未知错误！");
	}
}
