/**
 * 
 */
package com.tianfang.admin.controller.sport;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.business.dto.SportHomeDto;
import com.tianfang.business.service.SportHomeService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;

/**
 * 
 * @author wk.s
 * @date 2015年11月17日
 */

@Controller
@RequestMapping("/homeManager")
public class SportHomeController extends BaseController{

	@Resource
	private SportHomeService sportHomeService;

	/**
	 * 跳转到新增页面
	 * @return
	 */
	@RequestMapping("/goAddSHome")
	public ModelAndView goAdd(){
		ModelAndView mv = getModelAndView();
		mv.setViewName("/sport/home/home_add");
		return mv;
	}
	
	/**
	 * 新增sporthome数据
	 * <br>新增的动作来源于数据源的mark操作，只对外提供新增轮播图的接口</br>
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addSHome")
	public Response<String> addSHome(SportHomeDto dto){
		Response<String> resp = new Response<String>();
		boolean flag = sportHomeService.addHomeRecord(dto);
		if(flag){
			resp.setMessage("新增成功~");
			resp.setStatus(DataStatus.HTTP_SUCCESS);
		}else{
			resp.setMessage("新增失败~");
			resp.setStatus(DataStatus.HTTP_FAILE);
		}
		return resp;
	}
	
	/**
	 * 删除首页表记录
	 * @param hid
	 * @return
	 * @2015年11月17日
	 */
	@ResponseBody
	@RequestMapping("/deleteSHome")
	public Response<String> deleteRecord(String id){
		
		Response<String> resp = new Response<String>();
		boolean flag = sportHomeService.deleteHRecord(id);
		if(flag){
			resp.setMessage("删除成功~");
			resp.setStatus(DataStatus.HTTP_SUCCESS);
		}else{
			resp.setMessage("删除失败~");
			resp.setStatus(DataStatus.HTTP_FAILE);
		}
		return resp;
	}
	
	/**
	 * 跳转到更新页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/goUpdateSHome")
	public ModelAndView goEdit(String id){
		
		ModelAndView mv = getModelAndView();
		SportHomeDto dto = new SportHomeDto();
		dto.setId(id);
		List<SportHomeDto> dtoLst = sportHomeService.findNRecords(dto, null);
		if((dtoLst != null) && (dtoLst.size() == 1)){
			dto = dtoLst.get(0);
		}
		mv.addObject("dto", dto);
		mv.setViewName("/sport/home/home_edit");
		return mv;
	}
	/**
	 * 通过hid修改sporthome信息
	 * @param dto
	 * @return
	 * @2015年11月18日
	 */
	@ResponseBody
	@RequestMapping("/updateHR")
	public Response<String> updateRecord(SportHomeDto dto){
		Response<String> resp = new Response<String>();
		boolean flag = sportHomeService.updateHRecord(dto);
		if(flag){
			resp.setMessage("更新成功~");
			resp.setStatus(DataStatus.HTTP_SUCCESS);
		}else{
			resp.setMessage("更新失败~");
			resp.setStatus(DataStatus.HTTP_FAILE);
		}
		return resp;
	}
	
	/**
	 * 跳转到首页
	 * @param dto
	 * @param page
	 * @return
	 * @2015年11月17日
	 */
	@RequestMapping("/home")
	public ModelAndView homePage(SportHomeDto dto, PageQuery page, String title, String type){
		
		ModelAndView mv = getModelAndView(getSessionUserId());
		if((title != null) && (!title.equals(""))){
			dto.setTitle(title);
		}else{
			dto.setTitle(null);
		}
		if((type != null) && (!type.equals(""))){
			dto.setType(Integer.parseInt(type));
		}else{
			dto.setType(null);
		}
		PageResult<SportHomeDto> hlst = findRecord(dto, page);
		mv.setViewName("/sport/home/home_list"); 
		mv.addObject("pageList", hlst);
		mv.addObject("dto", dto);
		mv.addObject("type", type);
		return mv;
	}
	
	/**
	 * 查询home表数据（兼容分页）
	 * @param hid
	 * @param page
	 * @return
	 * @2015年11月17日
	 */
	public PageResult<SportHomeDto> findRecord(SportHomeDto dto, PageQuery page){
		
		if(page == null){
			page = new PageQuery();
			page.setCurrPage(1);
		}
		page.setPageSize(10);
		PageResult<SportHomeDto> hlst = sportHomeService.findHRecords(dto, page);
		return hlst;
	}
	
}
