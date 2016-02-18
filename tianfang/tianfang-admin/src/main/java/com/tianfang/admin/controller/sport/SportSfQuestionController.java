package com.tianfang.admin.controller.sport;

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
import com.tianfang.business.dto.SportSfAnswerDto;
import com.tianfang.business.dto.SportSfQuestionDto;
import com.tianfang.business.service.ISportSfAnswerService;
import com.tianfang.business.service.ISportSfQuestionService;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;

@Controller
@RequestMapping("/question")
public class SportSfQuestionController extends BaseController {
	protected static final Log logger = LogFactory.getLog(SportSfQuestionController.class);

	@Autowired
	private ISportSfQuestionService iSportSfQuestion;
	@Autowired
	private ISportSfAnswerService iSportSfAnswer;
	
	@RequestMapping(value="/selectPageAll")
	public ModelAndView selectPageAll(SportSfQuestionDto sportSfQuestion,ExtPageQuery page){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<SportSfQuestionDto> pageResult =iSportSfQuestion.selectPageAll(sportSfQuestion,page.changeToPageQuery());
		mv.addObject("pageList", pageResult);
		mv.setViewName("/sport/feedback/list");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="/save")
	public Map<String,Object> save(SportSfQuestionDto sportSfQuestion){
		long stat = iSportSfQuestion.save(sportSfQuestion);
		if(stat>0){
			return MessageResp.getMessage(true,"添加成功~~~");
		}
		return MessageResp.getMessage(false,"添加失败~~~");
	}
	
	@ResponseBody
	@RequestMapping(value="/edit")
	public Map<String,Object> edit(SportSfQuestionDto sportSfQuestion){
		long stat = iSportSfQuestion.update(sportSfQuestion);
		if(stat>0){
			return MessageResp.getMessage(true,"修改成功~~~");
		}
		return MessageResp.getMessage(false,"修改失败~~~");
	}
	
	@ResponseBody
	@RequestMapping(value="/delete")
	public Map<String,Object> delete(String ids){
		long stat = iSportSfQuestion.delete(ids);
		if(stat>0){
			return MessageResp.getMessage(true,"修改成功~~~");
		}
		return MessageResp.getMessage(false,"修改失败~~~");
	}
	
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd(){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("/sport/feedback/add");
			mv.addObject("pd", pd);
			mv.addObject("msg","save");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping(value="/toEdit")
	public ModelAndView toEdit(SportSfQuestionDto sportSfQuestion){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		SportSfQuestionDto result = iSportSfQuestion.selectById(sportSfQuestion.getId());
		try {
			mv.setViewName("/sport/feedback/edit");
			mv.addObject("result",result);
			mv.addObject("msg","edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	@RequestMapping(value="/toAnswerInfo")
	public ModelAndView toAnswerInfo(SportSfQuestionDto sportSfQuestion,ExtPageQuery page){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//获取答案集合
		SportSfAnswerDto sportSfAnswerDto = new SportSfAnswerDto();
		sportSfAnswerDto.setQuestion(sportSfQuestion.getId());
		List<SportSfAnswerDto> lis = iSportSfAnswer.selectList(sportSfAnswerDto);
		//问题对象
		SportSfQuestionDto question =iSportSfQuestion.selectById(sportSfQuestion.getId());
		
		try {
			mv.setViewName("/sport/feedback/questionInfo");
			mv.addObject("lis",lis);
			mv.addObject("question",question);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
}
