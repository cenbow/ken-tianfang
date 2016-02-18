/**
 * 
 */
package com.tianfang.admin.controller.sport;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.company.dto.SportCompanyRelatedDto;
import com.tianfang.company.service.CompanyRelatedService;

/**
 * @author wk.s
 * 2015年12月4日
 */
@Controller
@RequestMapping("companyRelated")
public class SportCompanyRController extends BaseController{

	@Resource
	private CompanyRelatedService comRService;
	
	/**
	 * 根据条件分页查询数据
	 * @param param
	 * @param page
	 * @return
	 */
	@RequestMapping("findPage")
	public ModelAndView findPage(SportCompanyRelatedDto param, PageQuery page){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		page.setPageSize(10);
		PageResult<SportCompanyRelatedDto> pageList = comRService.getCRInfoList(param, page);
		mv.addObject("param", param);
		mv.addObject("pageList", pageList);
		mv.setViewName("/sport/company/company_list");
		return mv;
	}

	/**
	 * 跳转到新增页面
	 * @return
	 */
	@RequestMapping("gotToAddView")
	public ModelAndView gotToAddView(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/sport/company/company_add");
		return mv;
	}
	
	/**
	 * 新增数据
	 * @param data
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addCRData")
	public Response<String> addComRData(SportCompanyRelatedDto data){
		Response<String> resp = new Response<String>();
		boolean flag = comRService.addCRData(data);
		if(flag){
			resp.setStatus(DataStatus.HTTP_SUCCESS);
			resp.setMessage("新增数据成功~");
		}else{
			resp.setStatus(DataStatus.HTTP_FAILE);
			resp.setMessage("新增数据失败~");
		}
		return resp;
	}
	
	/**
	 * 跳转到编辑页面
	 * @return
	 */
	@RequestMapping("gotToUpdateView")
	public ModelAndView gotToUpdateView(@RequestParam String id){
		ModelAndView mv = new ModelAndView();
		SportCompanyRelatedDto dto = comRService.getCRInfoById(id);
		mv.addObject("dto", dto);
		mv.setViewName("/sport/company/company_update");
		return mv;
	}
	
	/**
	 * 根据id更新数据
	 * @param data
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateRData")
	public Response<String> updateRData(SportCompanyRelatedDto data){
		Response<String> resp = new Response<String>();
		boolean flag = comRService.updateCRDataById(data);
		if(flag){
			resp.setStatus(DataStatus.HTTP_SUCCESS);
			resp.setMessage("新增数据成功~");
		}else{
			resp.setStatus(DataStatus.HTTP_FAILE);
			resp.setMessage("新增数据失败~");
		}
		return resp;
	}
	
	/**
	 * 根据id逻辑删除数据
	 * @param data
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteRData")
	public Response<String> deleteRData(SportCompanyRelatedDto data){
		Response<String> resp = new Response<String>();
		String ids = data.getId(); // 传过来ids封装在id字段里
		boolean flag = comRService.deleteByIds(ids);
		if(flag){
			resp.setStatus(DataStatus.HTTP_SUCCESS);
			resp.setMessage("新增数据成功~");
		}else{
			resp.setStatus(DataStatus.HTTP_FAILE);
			resp.setMessage("新增数据失败~");
		}
		return resp;
	}
}
