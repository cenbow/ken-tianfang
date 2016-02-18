package com.tianfang.admin.controller.train;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.constants.MessageTypeEnum;
import com.tianfang.train.dto.TrainingMessageInfoDto;
import com.tianfang.train.service.ITrainingMessageInfosService;

@Controller
@RequestMapping(value = "/message")
public class TrainingMessageInfoController extends BaseController {
	protected static final Log logger = LogFactory
			.getLog(TrainingMessageInfoController.class);

	@Autowired
	private ITrainingMessageInfosService trainingMessageInfosService;

	/***
	 * 分页查询消息详情
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "findPage.do")
	public ModelAndView findPage(TrainingMessageInfoDto query, ExtPageQuery page) {
		logger.info("TrainingMessageInfoCont" + "roller query:" + query);
		PageResult<TrainingMessageInfoDto> result = trainingMessageInfosService
				.findPage(query, page.changeToPageQuery());
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		mv.addObject("pageList", result);
		mv.addObject("query", query);
		mv.addObject("types", MessageTypeEnum.messageTypeMap);
		mv.setViewName("/message/list");
		return mv;
	}

	/***
	 * 删除消息信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteMessageInfo.do")
	@ResponseBody
	public Map<String, Object> deleteMessageInfo(String id) {
		if (StringUtils.isBlank(id)) {
			return MessageResp.getError("请求参数不能为空！");
		}
		Integer resObject = (Integer) trainingMessageInfosService
				.deleteMessageInfoById(id);
		if (resObject == 0) {
			return MessageResp.getError("无此条记录~");
		}
		if (resObject == 1) {
			return MessageResp.getSuccess("删除成功！");
		}
		return MessageResp.getError("未知错误！");

	}

	/**
	 * 跳转新增页面
	 * @return
	 */
	@RequestMapping(value = "goAdd.do")
	public ModelAndView add() {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		mv.setViewName("/message/add");
		return mv;
	}
	
	/***
	 * 添加消息信息
	 * 
	 * @param trainingMessageInfoDto
	 * @return
	 */
	@RequestMapping(value = "insertMessageInfo.do")
	@ResponseBody
	public Map<String, Object> insertMessageInfo(
			TrainingMessageInfoDto trainingMessageInfoDto) {
		if (StringUtils.isBlank(trainingMessageInfoDto.getTitle())
				|| StringUtils.isBlank(trainingMessageInfoDto.getContent())) {
			return MessageResp.getError("请求参数不能为空~");
		}
		trainingMessageInfoDto.setType(MessageTypeEnum.System.getIndex()); // 后台添加信息为系统信息
		Integer resObject = (Integer) trainingMessageInfosService
				.insertMessageInfo(trainingMessageInfoDto);
		if (resObject == 0) {
			return MessageResp.getError("添加失败！");
		}
		if (resObject == 1) {
			return MessageResp.getSuccess("添加成功！");
		}
		return MessageResp.getError("未知错误！");

	}

	/***
	 * 修改消息信息
	 * 
	 * @param trainingMessageInfoDto
	 * @return
	 */
	@RequestMapping(value = "updateMessageInfo.do")
	@ResponseBody
	public Map<String, Object> updateMessageInfo(
			TrainingMessageInfoDto trainingMessageInfoDto) {
		if (StringUtils.isBlank(trainingMessageInfoDto.getTitle())
				|| StringUtils.isBlank(trainingMessageInfoDto.getContent())) {
			return MessageResp.getError("请求参数不能为空~");
		}
		Integer resObject = (Integer) trainingMessageInfosService
				.updateMessageInfo(trainingMessageInfoDto);
		if (resObject == 0) {
			return MessageResp.getError("编辑失败！");
		}
		if (resObject == 1) {
			return MessageResp.getSuccess("编辑成功！");
		}
		return MessageResp.getError("未知错误！");

	}

	/***
	 * 编辑时查询消息数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "goEdit.do")
	public ModelAndView goEdit(String id) {
		TrainingMessageInfoDto trainingMessageInfoDto = trainingMessageInfosService
				.getMessageInfo(id);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		mv.addObject("data", trainingMessageInfoDto);
		mv.setViewName("/message/edit");
		return mv;
	}
}