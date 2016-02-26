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
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.dto.SportMBrandDto;
import com.tianfang.order.dto.SportMCategoryDto;
import com.tianfang.order.dto.SportMProductSkuDto;
import com.tianfang.order.dto.SportMProductSpuDto;
import com.tianfang.order.dto.SportMSpecDto;
import com.tianfang.order.dto.SportMSpecValuesDto;
import com.tianfang.order.dto.SportMTypeDto;
import com.tianfang.order.service.ISportMProductSkuService;
import com.tianfang.order.service.ISportMProductSpuService;

@Controller
@RequestMapping("/product/spu")
public class SportMProductSpuController extends BaseController {

	@Autowired
	private ISportMProductSpuService iProductSpu;
	
	@Autowired
	private ISportMProductSkuService iSportMProductSkuService;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private SportRedisController redisController;
	
	@RequestMapping("/selectPageAll")
	public ModelAndView selectPageAll(SportMProductSpuDto spu,ExtPageQuery page){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<SportMProductSpuDto> pageList = iProductSpu.selectPageAll(spu,page.changeToPageQuery());
		try {
			mv.setViewName("/sport/product_spu/list");
			mv.addObject("spu", spu);
			mv.addObject("pageList", pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/save")
	public Map<String,Object> save(SportMProductSpuDto spu){
		long stat = iProductSpu.save(spu);
		if(stat >0){
			redisController.addRedis(iSportMProductSkuService.findAllSpu());
			return MessageResp.getMessage(true, "新增成功~~");
		}
		return MessageResp.getMessage(false, "新增失败~~");
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/edit")
	public Map<String,Object> edit(SportMProductSpuDto spu){
		if (StringUtils.isBlank(spu.getPic())) {
			spu.setPic(null);
		}
		if (StringUtils.isBlank(spu.getThumbnail())) {
			spu.setThumbnail(null);
		}
		long stat = iProductSpu.edit(spu);
		if(stat >0){
			redisController.addRedis(iSportMProductSkuService.findAllSpu());
			return MessageResp.getMessage(true, "修改成功~~");
		}
		return MessageResp.getMessage(false, "修改失败~~");
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/delete")	
	public Map<String,Object> delete(String id){
		long stat = iProductSpu.delete(id);
		if(stat >0){
			//System.out.println(iSportMProductSkuService.findAllSpu().size());
			redisController.addRedis(iSportMProductSkuService.findAllSpu());
			return MessageResp.getMessage(true, "删除成功~~");
		}
		return MessageResp.getMessage(false, "删除成功~~");
	}
	
	@RequestMapping("/openAddView")
	public ModelAndView openAddView() {
		ModelAndView mv= this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
	//	List<SportMProductSpuDto> spu= iSportMProductSkuService.findAllSpu();
		//SportMProductSpuDto spu= iSportMProductSkuService.selectById(id);
		List<SportMBrandDto> brand = iSportMProductSkuService.findAllBrand();
		List<SportMTypeDto> type = iSportMProductSkuService.findAllType();
	//	mv.addObject("spu", spu);
		mv.addObject("brand", brand);
		mv.addObject("type", type);
		mv.addObject("pd", pd);
		mv.addObject("msg", "save");
		mv.setViewName("/sport/product_spu/add");
		return mv;
	}
	
	@RequestMapping("/openEditView")
	public ModelAndView openEditView() {
		ModelAndView mv= this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String id = pd.getString("id");
		SportMProductSpuDto spu= iSportMProductSkuService.selectById(id);
		List<SportMBrandDto> brand = iSportMProductSkuService.findAllBrand();
		List<SportMTypeDto> type = iSportMProductSkuService.findAllType();
		List<SportMCategoryDto> categoryDtos = iSportMProductSkuService.findByTypeId(spu.getTypeId());
		List<SportMSpecDto> sportMSpecValuesDtos = iSportMProductSkuService.findSpecByTypeId(spu.getTypeId());
		mv.addObject("spu", spu);
		mv.addObject("brand", brand);
		mv.addObject("type", type);
		mv.addObject("category", categoryDtos);
		mv.addObject("spec", sportMSpecValuesDtos);
		mv.addObject("pd", pd);
		mv.addObject("data", spu);
		mv.setViewName("/sport/product_spu/edit");
		return mv;
	}
}
