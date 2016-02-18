/**
 * 
 */
package com.tianfang.home.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.business.dto.SportSfAnswerDto;
import com.tianfang.business.dto.SportSfQuestionDto;
import com.tianfang.business.dto.SportSfResultDto;
import com.tianfang.business.dto.SportSuggestionUserDto;
import com.tianfang.business.enums.SuggestionEnums;
import com.tianfang.business.service.ISportSfAnswerService;
import com.tianfang.business.service.ISportSfQuestionService;
import com.tianfang.business.service.ISportSfResultService;
import com.tianfang.business.service.ISportSuggestionUserService;
import com.tianfang.common.model.MessageResp;

/**		
 * <p>Title: FeedBackController </p>
 * <p>Description: 类描述:用户反馈</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2015年12月29日下午3:37:25
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("suggest")
public class SuggestController extends BaseController {
	
	@Autowired
	private ISportSfQuestionService squestionService;
	@Autowired
	private ISportSfAnswerService answerService;
	@Autowired
	private ISportSuggestionUserService suggestionUserService;
	@Autowired
	private ISportSfResultService resultService;
	
	private Map<String, Long> cacheMap = new HashMap<String, Long>(); // 记录session上次操作时间
	private final long CACHE_TIME = 30*60*1000L;						 // 设置2次提交间隔时间为半小时
	
	/**
	 * 用户调查问卷
	 * @param sportSfQuestionDto
	 * @return
	 * @author xiang_wang
	 * 2015年12月29日下午3:37:20
	 */
	@RequestMapping
	public ModelAndView list(SportSfQuestionDto sportSfQuestionDto){
		List<SportSfQuestionDto> datas = assemblyQuestion(sportSfQuestionDto);
		
		ModelAndView mv = getModelAndView();
		mv.addObject("datas", datas);
		mv.addObject("types", SuggestionEnums.getValus());
		mv.setViewName("/suggest");
		
		return mv;
	}
	
	/**
	 * 提交用户意见反馈
	 * @param request
	 * @param qids
	 * @param userdto
	 * @return
	 * @author xiang_wang
	 * 2015年12月30日上午11:01:18
	 */
	@RequestMapping("submit")
	@ResponseBody
	public Map<String,Object> submit(HttpServletRequest request, String[] qids, SportSuggestionUserDto userdto){
		long now = System.currentTimeMillis();
		String sessionId = request.getSession().getId();
		if (cacheMap.containsKey(sessionId)){
			Long old = cacheMap.get(sessionId);
			if (old - now < CACHE_TIME){
				return MessageResp.getMessage(false, "亲~刚提交过了,休息一会再提交~"); 
			}
		}
		
		List<SportSfResultDto> results = assemblyResult(request, qids);
		try {
			suggestionUserService.submitSuggestion(userdto, results);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageResp.getMessage(false, "提交失败!");
		}
		
		cacheMap.put(sessionId, now);
		return MessageResp.getMessage(true,"提交成功!");
	}

	private List<SportSfResultDto> assemblyResult(HttpServletRequest request,
			String[] qids) {
		List<SportSfResultDto> results = new ArrayList<SportSfResultDto>();
		for (String qid : qids){
			String[] aids = request.getParameterValues("qid_"+qid);
			SportSfResultDto result = new SportSfResultDto();
			result.setSfQuestion(qid);
			result.setSfAnswer(arrayToString(aids));
			
			results.add(result);
		}
		return results;
	}
	
	private String arrayToString(String[] arr){
		StringBuffer sb = new StringBuffer();
		if (null != arr && arr.length > 0){
			for (String str : arr){
				sb.append(str).append(",");
			}
			return sb.substring(0, sb.length()-1);
		}
		return sb.toString();
	}
	
	private List<SportSfQuestionDto> assemblyQuestion( SportSfQuestionDto sportSfQuestionDto) {
		List<SportSfQuestionDto> datas = squestionService.selectAll(sportSfQuestionDto);
		List<SportSfAnswerDto> answers = answerService.selectAll();
		if (null != datas && datas.size() > 0){
			if (null != answers && answers.size() > 0){
				Map<String, List<SportSfAnswerDto>> map = new HashMap<String, List<SportSfAnswerDto>>();
				String key;
				List<SportSfAnswerDto> list;
				for (SportSfAnswerDto answer : answers){
					if (null != answer){
						key = answer.getQuestion();
						if (map.containsKey(key)){
							map.get(key).add(answer);
						}else{
							list = new ArrayList<SportSfAnswerDto>();
							list.add(answer);
							map.put(key, list);
						}
					}
				}
				
				for (SportSfQuestionDto data : datas){
					data.setSfAnswers(map.get(data.getId()));
				}
			}
		}
		return datas;
	}
}