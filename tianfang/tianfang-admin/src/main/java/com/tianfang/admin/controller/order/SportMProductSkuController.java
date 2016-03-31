package com.tianfang.admin.controller.order;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.dto.SportMBrandDto;
import com.tianfang.order.dto.SportMCategoryDto;
import com.tianfang.order.dto.SportMProductSkuDto;
import com.tianfang.order.dto.SportMProductSkuSpecValuesDto;
import com.tianfang.order.dto.SportMProductSpuDto;
import com.tianfang.order.dto.SportMSpecDto;
import com.tianfang.order.dto.SportMSpecValuesDto;
import com.tianfang.order.dto.SportMTypeDto;
import com.tianfang.order.service.ISportMProductSkuService;
import com.tianfang.order.service.ISportMProductSpuService;

@Controller
@RequestMapping("/sport/sku")
public class SportMProductSkuController extends BaseController{

	@Autowired
	private ISportMProductSkuService iSportMProductSkuService;
	
	@Autowired
	private ISportMProductSpuService iSportMProductSpuService;
	@SuppressWarnings("rawtypes")
	@Autowired
	private SportRedisController redisController;
	
	protected static final Log logger = LogFactory.getLog(SportMProductSkuController.class);
	
	@RequestMapping("/findPage")
	public ModelAndView findPage(SportMProductSkuDto sportMProductSkuDto,ExtPageQuery page) {
		PageResult<SportMProductSkuDto> result = iSportMProductSkuService.findPage(sportMProductSkuDto, page.changeToPageQuery());
		ModelAndView mv= this.getModelAndView(this.getSessionUserId());
		mv.addObject("pageList", result);
		mv.addObject("data", sportMProductSkuDto);
		mv.setViewName("/sport/sku/list");
		return mv;
	}
	
	@RequestMapping("/openAddView")
	public ModelAndView openAddView() {
		ModelAndView mv= this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<SportMProductSpuDto> spu= iSportMProductSkuService.findAllSpu();
		List<SportMBrandDto> brand = iSportMProductSkuService.findAllBrand();
		List<SportMTypeDto> type = iSportMProductSkuService.findAllType();
		List<SportMSpecDto> spec = iSportMProductSkuService.findAllSpec();
		mv.addObject("spu", spu);
		mv.addObject("brand", brand);
		mv.addObject("type", type);
		mv.addObject("spec", spec);
		mv.addObject("pd", pd);
		mv.addObject("msg", "save");
		mv.setViewName("/sport/sku/add");
		return mv;
	}
	
	@RequestMapping("/findSpecByProductId")
	@ResponseBody
	public Map<String, Object> findSpecByProductId() {
		PageData pd = new PageData();
		pd = this.getPageData();
		String productId = pd.getString("productId");
		SportMProductSpuDto sportMProductSpuDto = iSportMProductSpuService.findProductById(productId);
		List<SportMSpecDto> sportMSpecValuesDtos = new ArrayList<SportMSpecDto>();
		if (null != sportMProductSpuDto && StringUtils.isNotBlank(sportMProductSpuDto.getTypeId())) {
			sportMSpecValuesDtos = iSportMProductSkuService.findSpecByTypeId(sportMProductSpuDto.getTypeId());
		}
		Map<String, Object>	map = new HashMap<String, Object>();
		map.put("spec", sportMSpecValuesDtos);
		return map;
	}
	
	@RequestMapping("/findByTypeId")
	@ResponseBody
	public Map<String, Object> findCategoryByTypeId() {
		PageData pd = new PageData();
		pd = this.getPageData();
		String typeId = pd.getString("typeId");
		List<SportMCategoryDto> categoryDtos = iSportMProductSkuService.findByTypeId(typeId);
		List<SportMSpecDto> sportMSpecValuesDtos = iSportMProductSkuService.findSpecByTypeId(typeId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("category", categoryDtos);
		map.put("spec", sportMSpecValuesDtos);
		return map;
	}
	
	@RequestMapping("/findValueBySpecId")
	@ResponseBody
	public List<SportMSpecValuesDto> findValueBySpecId() {
		PageData pd = new PageData();
		pd = this.getPageData();
		String specId = pd.getString("specId");
		List<SportMSpecValuesDto> sportMSpecValuesDtos = iSportMProductSkuService.findValueBySpecId(specId);
		return sportMSpecValuesDtos;
	}
	
	@RequestMapping("/openEditView")
	public ModelAndView openEditView() {
		ModelAndView mv= this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String id = pd.getString("id");
		SportMProductSkuDto sportMProductSkuDto = iSportMProductSkuService.findById(id);
		List<SportMProductSpuDto> spu= iSportMProductSkuService.findAllSpu();
		/*List<SportMBrandDto> brand = iSportMProductSkuService.findAllBrand();
		List<SportMTypeDto> type = iSportMProductSkuService.findAllType();
		List<SportMCategoryDto> categoryDtos = iSportMProductSkuService.findByTypeId(sportMProductSkuDto.getTypeId());*/
		List<SportMSpecValuesDto> sportmSpecValuesDtos = iSportMProductSkuService.findValueBySpecId(sportMProductSkuDto.getSpecId());
		List<SportMSpecDto> sportMSpecValuesDtos = iSportMProductSkuService.findSpecByTypeId(sportMProductSkuDto.getTypeId());
		List<SportMProductSkuSpecValuesDto> sportMProductSkuSpecValuesDto = iSportMProductSkuService.selectSkuSpecValues(id);
		for (SportMProductSkuSpecValuesDto sportmProductSkuSpecValuesDto : sportMProductSkuSpecValuesDto) {
			List<SportMSpecValuesDto> sportmspecValuesDtos = iSportMProductSkuService.findValueBySpecId(sportmProductSkuSpecValuesDto.getSpecId());
			sportmProductSkuSpecValuesDto.setSportMSpecValuesDtos(sportmspecValuesDtos);
		}
		mv.addObject("spu", spu);
		mv.addObject("sku", sportMProductSkuDto);
		/*mv.addObject("brand", brand);
		mv.addObject("type", type);
		mv.addObject("category", categoryDtos);*/
		mv.addObject("specValues", sportmSpecValuesDtos);
		mv.addObject("spec", sportMSpecValuesDtos);		
		mv.addObject("pd", pd);
		mv.addObject("data", sportMProductSkuDto);
		mv.addObject("skuSpecValue", sportMProductSkuSpecValuesDto);
		mv.setViewName("/sport/sku/edit");
		return mv;
	}
	
	@RequestMapping("/getSpecName")
	@ResponseBody
	public Map<String, Object> getSpecName() {
		PageData pd = new PageData();
		pd = this.getPageData();
		String id = pd.getString("id");
		SportMProductSkuDto sportMProductSkuDto = iSportMProductSkuService.findById(id);
		List<SportMSpecDto> sportMSpecValuesDtos = iSportMProductSkuService.findSpecByTypeId(sportMProductSkuDto.getTypeId());
		List<SportMProductSkuSpecValuesDto> sportMProductSkuSpecValuesDto = iSportMProductSkuService.selectSkuSpecValues(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sportMSpecValuesDtos", sportMSpecValuesDtos);
		map.put("sportMProductSkuSpecValuesDto", sportMProductSkuSpecValuesDto);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(SportMProductSkuDto sportMProductSkuDto) {
		String[] idStr = sportMProductSkuDto.getIds().split(",");
		List<SportMSpecValuesDto> specValuesDtos = new ArrayList<SportMSpecValuesDto>(); 
		for (String ids : idStr) {
			String[] id = ids.split("\\*");
			SportMSpecValuesDto specValuesDto = new SportMSpecValuesDto();
			specValuesDto.setSpecId(id[0]);
			specValuesDto.setId(id[1]);
			specValuesDtos.add(specValuesDto);
		}		
		if (null != specValuesDtos && specValuesDtos.size()>0) {			
			for (SportMSpecValuesDto sportMSpecValuesDto : specValuesDtos) {
				int i = 0;
				for (SportMSpecValuesDto sportmSpecValuesDto : specValuesDtos) {
					if (sportMSpecValuesDto.getSpecId().equals(sportmSpecValuesDto.getSpecId())){
						i +=1;
					}
					if (i >= 2) {
						return MessageResp.getMessage(false,"不能添加相同的规格");
					}
					
				}
			}
		}
		
		Integer result = iSportMProductSkuService.save(sportMProductSkuDto,specValuesDtos);
		if (result == 1) {
			redisController.addRedis(iSportMProductSkuService.findAllSku());
            return MessageResp.getMessage(true,"操作成功");
        }
        return MessageResp.getMessage(false,"操作失败");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/detele")
	@ResponseBody
	public Map<String, Object> delete(String ids) {
		if (StringUtils.isBlank(ids)) {
			return MessageResp.getMessage(false, "请求参数不能为空~") ;
		}
		iSportMProductSkuService.delete(ids);
		redisController.addRedis(iSportMProductSkuService.findAllSku());
		return MessageResp.getMessage(true, "删除成功！");
	}
}
