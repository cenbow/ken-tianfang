package com.tianfang.admin.controller.sport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.admin.controller.PageData;
import com.tianfang.business.dto.SportSfAnswerDto;
import com.tianfang.business.dto.SportSfResultDto;
import com.tianfang.business.dto.SportSfResultExDto;
import com.tianfang.business.service.ISportSfAnswerService;
import com.tianfang.business.service.ISportSfResultService;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;

/**
 * 意见反馈---答案
 * @author Mr
 *
 */
@Controller
@RequestMapping("/result")
public class SportSfResultController extends BaseController {
	
	@Autowired
	private ISportSfResultService sfResultService;
	@Autowired
	private ISportSfAnswerService iSportSfAnswer;
	
	@RequestMapping("/save")
	@ResponseBody
	public Map<String,Object> save(SportSfResultDto resultDto){
		long stat = sfResultService.save(resultDto);
		if(stat>0){
			return MessageResp.getMessage(true,"修改成功~~~");
		}
		return MessageResp.getMessage(false,"修改失败~~~");
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String,Object> edit(SportSfResultDto resultDto){
		long stat = sfResultService.update(resultDto);
		if(stat>0){
			return MessageResp.getMessage(true,"修改成功~~~");
		}
		return MessageResp.getMessage(false,"修改失败~~~");
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String,Object> delete(String ids){
		long stat = sfResultService.delete(ids);
		if(stat>0){
			return MessageResp.getMessage(true,"修改成功~~~");
		}
		return MessageResp.getMessage(false,"修改失败~~~");
	}
	
	@RequestMapping("/selectPageAll")
	public ModelAndView selectPageAll(SportSfResultExDto resultDto,ExtPageQuery page){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		Map params = new HashMap<String, Object>();
		params.put("sfType", resultDto.getSfType());
		params.put("sfUname", resultDto.getSfUname());
		params.put("sfPhone", resultDto.getSfPhone());
		params.put("sfEmail", resultDto.getSfEmail());
		
		PageResult<SportSfResultExDto> pageList = sfResultService.selectOrSfCriteria(resultDto,page.changeToPageQuery());		
		try {
			mv.setViewName("/sport/feedback/result/list");
			mv.addObject("pageList",pageList);
			mv.addObject("params", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping(value="/toAnswerName")
	public ModelAndView toAdd(){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("/sport/feedback/result/answerNameInfo");
			mv.addObject("pd", pd);
			mv.addObject("msg","save");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping(value="/answerInfo")
	public ModelAndView toEdit(String answerId){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		List<SportSfAnswerDto> lis = new ArrayList<SportSfAnswerDto>();
		String[] aid = answerId.split(",");
		for (String str : aid) {
			lis.add(iSportSfAnswer.selectById(str));
		}
		try {
			mv.setViewName("/sport/feedback/result/answerNameInfo");
			mv.addObject("lis",lis);
			mv.addObject("msg","edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
}
