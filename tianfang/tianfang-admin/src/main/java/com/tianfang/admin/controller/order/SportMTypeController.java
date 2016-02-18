package com.tianfang.admin.controller.order;

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
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.order.dto.SportMTypeDto;
import com.tianfang.order.service.ISportMTypeService;

@Controller
@RequestMapping(value = "/sport/type")
public class SportMTypeController extends BaseController{
	
	protected static final Log logger = LogFactory.getLog(SportMTypeController.class);
	
	@Autowired
	private ISportMTypeService iSportMTypeService;
	@SuppressWarnings("rawtypes")
	@Autowired
	private SportRedisController redisController;
	
	/**
	 * 类型管理列表
	 * @param sportMTypeDto
	 * @param page
	 * @return
	 */
	@RequestMapping("/findPage")
	public ModelAndView findPage(SportMTypeDto sportMTypeDto,ExtPageQuery page) {
		PageResult<SportMTypeDto> result = iSportMTypeService.findPage(sportMTypeDto, page.changeToPageQuery());
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		mv.addObject("pageList", result);
		mv.addObject("newDto", sportMTypeDto);
		mv.setViewName("/sport/type/list");
		return mv;
	}
	
	@RequestMapping("/openAddView")
	public ModelAndView openAddView() {
		logger.info("打开新增类型页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.setViewName("/sport/type/add");
		return mv;
	}
	
	@RequestMapping("/openEditView")
	public ModelAndView openEditView() {
		logger.info("打开编辑类型页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String id = pd.getString("id");
		SportMTypeDto sportMTypeDto = iSportMTypeService.findById(id);
		mv.addObject("data", sportMTypeDto);
		mv.addObject("pd", pd);
		mv.setViewName("/sport/type/edit");
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(SportMTypeDto sportMTypeDto) {
		Integer result = iSportMTypeService.save(sportMTypeDto);
		if (result == 1) {
			redisController.addRedis(iSportMTypeService.selectMTypeAll());
			return MessageResp.getMessage(true,"操作成功");
	    }
	    return MessageResp.getMessage(false,"操作失败");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(String ids) {
		if(ids == null || "".equals(ids)){
            return MessageResp.getMessage(false, "请求参数不能为空~") ;
        }
		iSportMTypeService.delete(ids);
		redisController.addRedis(iSportMTypeService.selectMTypeAll());
		return MessageResp.getMessage(true, "删除成功！");
	}
}
