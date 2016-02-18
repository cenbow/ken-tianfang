package com.tianfang.admin.controller.order;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.admin.controller.Const;
import com.tianfang.admin.controller.PageData;
import com.tianfang.admin.dto.SportAdminDto;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.order.dto.SportMBrandDto;
import com.tianfang.order.service.ISportMBrandService;

@Controller
@RequestMapping(value = "/sport/brand")
public class SportMBrandController extends BaseController{

	protected static final Log logger = LogFactory.getLog(SportMBrandController.class); 
	
	@Autowired
	private ISportMBrandService iSportMBrandService;
	@SuppressWarnings("rawtypes")
	@Autowired
	private SportRedisController redisController;
	
	/**
	 * 品牌列表页
	 * @param sportMBrandDto
	 * @param page
	 * @return
	 */
	@RequestMapping("/findPage.do")
	public ModelAndView findPage(SportMBrandDto sportMBrandDto,ExtPageQuery page) {
		PageResult<SportMBrandDto> result = iSportMBrandService.findPage(sportMBrandDto, page.changeToPageQuery());
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		mv.addObject("pageList", result);
		mv.addObject("newDto", sportMBrandDto);
		mv.setViewName("/sport/brand/list");
		return mv;
	}
	
	/**
	 * 打开新增品牌页面
	 * @return
	 */
	@RequestMapping("/openAddView")
	public ModelAndView openAddView() {
		logger.info("打开新增品牌页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("/sport/brand/add");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}
	
	@RequestMapping("/openEditView")
	public ModelAndView openEditView() {
		logger.info("打开编辑品牌页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String id = pd.getString("id");
		SportMBrandDto sportMBrandDto = iSportMBrandService.findById(id);
		mv.setViewName("/sport/brand/edit");
		mv.addObject("data", sportMBrandDto);
		mv.addObject("pd", pd);
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(SportMBrandDto sportMBrandDto,HttpSession session) {
		SportAdminDto trainAdmin = (SportAdminDto) session.getAttribute(Const.SESSION_USER);
        if (null == trainAdmin) {
            return MessageResp.getMessage(false,"当前登录用户超时，请重新登录");
        }
        Integer result = iSportMBrandService.save(sportMBrandDto);
        if (result == 1) {
        	redisController.addRedis(iSportMBrandService.selectAll());
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
		iSportMBrandService.delete(ids);
		redisController.addRedis(iSportMBrandService.selectAll());
		return MessageResp.getMessage(true, "删除成功！");
	}
}
