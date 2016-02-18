package com.tianfang.admin.controller.sport;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.onteach.dto.OnlineTeachColumnDto;
import com.tianfang.onteach.service.IOnTeachColumnService;

@Controller
@RequestMapping(value = "/sport/column")
public class OnTeachColumnController extends BaseController{

	@Autowired
	private IOnTeachColumnService iOnTeachColumnService;
	
	@RequestMapping(value="/list")
	public ModelAndView findPage(OnlineTeachColumnDto params, ExtPageQuery page){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageQuery query = page.changeToPageQuery();
		PageResult<OnlineTeachColumnDto> result= iOnTeachColumnService.findOnTeachByParams(params, query.getCurrPage(), query.getPageSize());
		mv.addObject("pageList", result);
		mv.addObject("params",params);		
		mv.setViewName("/sport/onlinecolumn/list");
		return mv;
	}
	
	@RequestMapping(value="/add")
	public ModelAndView add(OnlineTeachColumnDto params){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		mv.setViewName("/sport/onlinecolumn/add");
		return mv;
	}
	
	@RequestMapping(value="/edit")
	public ModelAndView edit(OnlineTeachColumnDto params){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		OnlineTeachColumnDto onteach = iOnTeachColumnService.findObjectById(params.getId());
		mv.addObject("onteach", onteach);
		mv.setViewName("/sport/onlinecolumn/edit");
		return mv;
	}
	
	@RequestMapping(value="/save")
	@ResponseBody
	public Response<String> save(OnlineTeachColumnDto params){
		Response<String> result = new Response<String>();
		iOnTeachColumnService.insert(params);
		return result;
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public Response<String> update(OnlineTeachColumnDto params){
		Response<String> result = new Response<String>();
		iOnTeachColumnService.update(params);
		return result;
	}
	
    @RequestMapping("/updateStatus")
    @ResponseBody
    public Map<String, Object> updateStatus(String ids) {
        if(ids == null || "".equals(ids)){
            return MessageResp.getMessage(false, "请求参数不能为空~") ;
        }
        iOnTeachColumnService.updateStatus(ids);
        return MessageResp.getMessage(true, "删除成功！");
    }
    
    @RequestMapping("/release")
    @ResponseBody
    public Map<String, Object> release(String id,Integer releaseStat) {
        if(id == null || "".equals(id) || null == releaseStat){
            return MessageResp.getMessage(false, "请求参数不能为空~") ;
        }
        iOnTeachColumnService.release(id, releaseStat);
        return MessageResp.getMessage(true, "修改成功！");
    }
	
}
