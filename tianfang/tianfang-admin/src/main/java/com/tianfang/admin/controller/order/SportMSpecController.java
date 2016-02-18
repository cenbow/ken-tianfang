package com.tianfang.admin.controller.order;

import java.util.List;
import java.util.Map;

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
import com.tianfang.order.dto.SportMSpecDto;
import com.tianfang.order.dto.SportMSpecValuesDto;
import com.tianfang.order.dto.SportMTypeDto;
import com.tianfang.order.service.ISportMProductSkuService;
import com.tianfang.order.service.ISportMSpecService;
import com.tianfang.order.service.ISportMSpecValuesService;
import com.tianfang.order.service.ISportMTypeService;

/**
 * 规格or规格明细
 * @author mr.w
 */
@Controller
@RequestMapping("/spec")
public class SportMSpecController extends BaseController {
	@Autowired
	private ISportMSpecService iSportMspecServie; 
	@Autowired
	private ISportMSpecValuesService iSportMSpecValuesService;
	@Autowired
	private ISportMTypeService iSportMTypeService;
	@SuppressWarnings("rawtypes")
	@Autowired
	private SportRedisController redisController;
	@Autowired
	private ISportMProductSkuService iSportMProductSkuService;
	
	/**
	 * 新增规格
	 * @param sportMSpec
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/save")
	public Map<String,Object> save(SportMSpecDto sportMSpec){
		long stat = iSportMspecServie.save(sportMSpec);
		if(stat > 0){
			redisController.addRedis(iSportMspecServie.selectAll());
			return MessageResp.getMessage(true,"添加成功~~~");
		}
		return MessageResp.getMessage(false,"添加失败~~~");
	}
	/**
	 * 修改规格
	 * @param sportMSpec
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/edit")
	public Map<String,Object> edit(SportMSpecDto sportMSpec){
		long stat = iSportMspecServie.edit(sportMSpec);
		if(stat > 0){
			redisController.addRedis(iSportMspecServie.selectAll());
			return MessageResp.getMessage(true,"修改成功~~~");
		}
		return MessageResp.getMessage(false,"修改失败~~~");
	}
	/**
	 * 删除规格
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String,Object> delete(String id){
		long stat = iSportMspecServie.delete(id);
		if(stat > 0){
			redisController.addRedis(iSportMspecServie.selectAll());
			return MessageResp.getMessage(true,"删除成功~~~");
		}
		return MessageResp.getMessage(false,"删除失败~~~");
	}
	/**
	 * 查询规格信息
	 * @param sportMSpec
	 * @param page
	 * @return
	 */
	@RequestMapping("/selectPageAll")
    public ModelAndView selectPageAll(SportMSpecDto sportMSpec,ExtPageQuery page){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<SportMSpecDto> pageList = iSportMspecServie.selectPageAll(sportMSpec,page.changeToPageQuery());
		try {
			mv.addObject("pageList",pageList);
			mv.addObject("sportMSpec",sportMSpec);
			mv.setViewName("/sport/order_spec/list");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return mv ;
    }
	/**
	 * 去新增规格页面
	 * @return
	 */
	@RequestMapping("/toAdd")
	public ModelAndView toAdd(){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		List<SportMTypeDto> mtype = iSportMTypeService.selectMTypeAll();  //获取所有类型信息
		try {
			mv.addObject("mtype",mtype);
			mv.setViewName("/sport/order_spec/add");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 * 去修改规格页面
	 * @param sportMSpec
	 * @return
	 */
	@RequestMapping("/toEdit")
	public ModelAndView doEdit(SportMSpecDto sportMSpec){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		SportMSpecDto spec= iSportMspecServie.selectById(sportMSpec.getId());    //获取修改规格对象
		SportMSpecValuesDto specVal = new SportMSpecValuesDto();
		specVal.setSpecId(sportMSpec.getId());
		List<SportMSpecValuesDto> lisVal = iSportMSpecValuesService.selectByCreate(specVal);  //对应规格明细信息
		try {
			mv.addObject("lisVal",lisVal);
			mv.addObject("spec",spec);
			mv.setViewName("/sport/order_spec/edit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 去修改规格页面
	 * @param sportMSpec
	 * @return
	 */
	@RequestMapping("/toSpacInfo")
	public ModelAndView toSpacInfo(SportMSpecDto sportMSpec){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		SportMSpecDto spec= iSportMspecServie.selectById(sportMSpec.getId());    //获取修改规格对象
		SportMSpecValuesDto specVal = new SportMSpecValuesDto();
		specVal.setSpecId(sportMSpec.getId());
		List<SportMSpecValuesDto> lisVal = iSportMSpecValuesService.selectByCreate(specVal);  //对应规格明细信息
		try {
			mv.addObject("lisVal",lisVal);
			mv.addObject("spec",spec);
			mv.setViewName("/sport/order_spec/spec_valueInfo");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 新增规格明细
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveSpecInfo")
	public Map<String,Object> saveSpecInfo(SportMSpecValuesDto sportMSpecValue){
		long stat = iSportMSpecValuesService.save(sportMSpecValue);
		if(stat >0){
			return MessageResp.getMessage(true,"新增成功~~~");
		}
		return MessageResp.getMessage(false,"新增失败~~~");
	}
	
	/**
	 * 删除规格明细
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteSpecInfo")
	public Map<String,Object> deleteSpecInfo(String id){
		long stat = iSportMSpecValuesService.delete(id);
		if(stat >0){
			return MessageResp.getMessage(true,"删除成功~~~");
		}
		return MessageResp.getMessage(false,"新增失败~~~");
	}
	
	/**
	 * 删除规格明细
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editSpecInfo")
	public Map<String,Object> editSpecInfo(SportMSpecValuesDto sportMSpecValue){
		long stat = iSportMSpecValuesService.update(sportMSpecValue);
		if(stat >0){
			return MessageResp.getMessage(true,"删除成功~~~");
		}
		return MessageResp.getMessage(false,"新增失败~~~");
	}
}
