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
import com.tianfang.business.dto.SportSfAnswerDto;
import com.tianfang.business.service.ISportSfAnswerService;
import com.tianfang.business.service.ISportSfQuestionService;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;

/**
 * 意见反馈---答案
 * @author Mr
 *
 */
@Controller
@RequestMapping("/answer")
public class SportSfAnswerController extends BaseController {
	
	protected static final Log logger = LogFactory.getLog(SportSfAnswerController.class);
	
	@Autowired
	private ISportSfAnswerService iSportSfAnswer;
	
	@Autowired
	private ISportSfQuestionService iSportSfQuestion;
	
	
	@ResponseBody
	@RequestMapping(value="/save")
	public Map<String,Object> save(SportSfAnswerDto sportSfAnswerDto){
		List<SportSfAnswerDto> lis = iSportSfAnswer.selectList(sportSfAnswerDto);
		if(lis.size()>0){
			return MessageResp.getMessage(false,"答案已经绑定~~~");
		}
		long stat =iSportSfAnswer.save(sportSfAnswerDto);
		if(stat>0){
			return MessageResp.getMessage(true,"添加成功~~~");
		}
		return MessageResp.getMessage(false,"添加失败~~~");
	}
	
	@ResponseBody
	@RequestMapping(value="/edit")
	public Map<String,Object> edit(SportSfAnswerDto sportSfAnswerDto){
		long stat = iSportSfAnswer.update(sportSfAnswerDto);
		if(stat>0){
			return MessageResp.getMessage(true,"修改成功~~~");
		}
		return MessageResp.getMessage(false,"修改失败~~~");
	}
	
	@ResponseBody
	@RequestMapping(value="/delete")
	public Map<String,Object> delete(String ids){
		long stat = iSportSfAnswer.delete(ids);
		if(stat>0){
			return MessageResp.getMessage(true,"删除成功~~~");
		}
		return MessageResp.getMessage(false,"删除失败~~~");
	}
	
	@RequestMapping(value="/selectPageAll")
	public ModelAndView selectPageAll(SportSfAnswerDto sportSfAnswerDto,ExtPageQuery page){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<SportSfAnswerDto> pageResult = iSportSfAnswer.selectPageAll(sportSfAnswerDto,page.changeToPageQuery());
		try {
			mv.addObject("pageList", pageResult);
			mv.setViewName("/sport/feedback/answer/list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
}
