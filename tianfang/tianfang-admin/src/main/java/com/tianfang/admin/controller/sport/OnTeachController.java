package com.tianfang.admin.controller.sport;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.onteach.dto.OnTeachDto;
import com.tianfang.onteach.dto.OnlineTeachColumnDto;
import com.tianfang.onteach.service.IOnTeachColumnService;
import com.tianfang.onteach.service.IOnTeachService;

@Controller
@RequestMapping(value = "/sport/onteach")
public class OnTeachController extends BaseController{

	@Autowired
	private IOnTeachService iOnTeachService;
	
	@Autowired
	private IOnTeachColumnService iOnTeachColumnService;
	
	@RequestMapping(value="/list")
	public ModelAndView findPage(OnTeachDto params, ExtPageQuery page){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageQuery query = page.changeToPageQuery();
		PageResult<OnTeachDto> result= iOnTeachService.findOnTeachByParams(params, query.getCurrPage(), query.getPageSize());
		
		OnlineTeachColumnDto dto = new OnlineTeachColumnDto();
		dto.setStat(DataStatus.ENABLED);
		List<OnlineTeachColumnDto> dataList = iOnTeachColumnService.findColumnsBy(dto);
		mv.addObject("dataList", dataList);
		mv.addObject("pageList", result);
		mv.addObject("params",params);
		mv.setViewName("/sport/online/list");
		return mv;
	}
	
	@RequestMapping(value="/add")
	public ModelAndView add(OnTeachDto params){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		
		OnlineTeachColumnDto dto = new OnlineTeachColumnDto();
		dto.setStat(DataStatus.ENABLED);
		List<OnlineTeachColumnDto> dataList = iOnTeachColumnService.findColumnsBy(dto);
		
		mv.addObject("dataList", dataList);
		mv.setViewName("/sport/online/add");
		return mv;
	}
	
	@RequestMapping(value="/edit")
	public ModelAndView edit(OnTeachDto params){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		
		OnlineTeachColumnDto dto = new OnlineTeachColumnDto();
		dto.setStat(DataStatus.ENABLED);
		List<OnlineTeachColumnDto> dataList = iOnTeachColumnService.findColumnsBy(dto);
		
		mv.addObject("dataList", dataList);
		
		OnTeachDto onteach = iOnTeachService.findObjectById(params.getId());
		mv.addObject("onteach", onteach);
		mv.setViewName("/sport/online/edit");
		return mv;
	}
	
	@RequestMapping(value="/save")
	@ResponseBody
	public Response<String> save(OnTeachDto params){
		Response<String> result = new Response<String>();
		iOnTeachService.insert(params);
		return result;
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public Response<String> update(OnTeachDto params){
		Response<String> result = new Response<String>();
		iOnTeachService.update(params);
		return result;
	}
	
    @RequestMapping("/updateStatus")
    @ResponseBody
    public Map<String, Object> updateStatus(String ids) {
        if(ids == null || "".equals(ids)){
            return MessageResp.getMessage(false, "请求参数不能为空~") ;
        }
        iOnTeachService.updateStatus(ids);
        return MessageResp.getMessage(true, "删除成功！");
    }
    
    @RequestMapping("/release")
    @ResponseBody
    public Map<String, Object> release(String id,Integer releaseStat) {
        if(id == null || "".equals(id) || null == releaseStat){
            return MessageResp.getMessage(false, "请求参数不能为空~") ;
        }
        iOnTeachService.release(id, releaseStat);
        return MessageResp.getMessage(true, "修改成功！");
    }
	
}
