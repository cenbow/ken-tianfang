package com.tianfang.admin.controller.train;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.digest.MD5Coder;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.train.dto.AddAssistantReqDto;
import com.tianfang.train.dto.AssistantDto;
import com.tianfang.train.dto.AssistantSpaceTimeRespDto;
import com.tianfang.train.dto.TrainingAddressDto;
import com.tianfang.train.service.IAssistantService;
import com.tianfang.train.service.ITrainingAdressQueryService;
import com.tianfang.train.service.ITrainingSpaceInfoService;

/**
 * 现场负责人管理
 * 
 * @author xiang_wang
 *
 *         2015年10月22日下午2:44:44
 */
@Controller
@RequestMapping(value = "/assistant")
public class AssistantController extends BaseController {

	protected static final Log logger = LogFactory
			.getLog(AssistantController.class);

	@Autowired
	private IAssistantService iAssistantService;
	@Autowired
	private ITrainingSpaceInfoService spaceInfoService;
	@Autowired
	private ITrainingAdressQueryService addressService;

	/**
	 * 负责人列表页展示
	 * 
	 * @author xiang_wang
	 * @param query
	 * @param page
	 *            2015年10月22日下午3:39:47
	 */
	@RequestMapping(value = "findpage.do")
	public ModelAndView findpage(AssistantDto query, ExtPageQuery page) {
		logger.info("AssistantDto query : " + query);
		PageResult<AssistantDto> result = iAssistantService.findPage(query,
				page.changeToPageQuery());
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		mv.addObject("pageList", result);
		mv.addObject("query", query);
		mv.setViewName("/assistant/list");
		return mv;
	}

	/**
	 * 跳转新增页面
	 * 
	 * @author xiang_wang 2015年10月22日下午5:13:56
	 */
	@RequestMapping(value = "goAdd.do")
	public ModelAndView add() {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		mv.setViewName("/assistant/add");
		return mv;
	}

	/**
	 * 添加场地
	 * 
	 * @author xiang_wang 2015年10月23日上午10:32:58
	 */
	@RequestMapping(value = "addPlace.do")
	public ModelAndView addPlace() {
		List<TrainingAddressDto> result = addressService.find(null);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		mv.addObject("datas", result);
		mv.setViewName("/assistant/selectPlace");
		return mv;
	}

	/**
	 * 选择场地时间段
	 * 
	 * @author xiang_wang 2015年10月23日下午12:56:18
	 */
	@RequestMapping(value = "selectTime.do")
	@ResponseBody
	public Response<List<TrainingAddressDto>> selectTime(String spaceId) {

		Response<List<TrainingAddressDto>> result = new Response<List<TrainingAddressDto>>();
		try {
			List<TrainingAddressDto> data = spaceInfoService
					.selectAllAddress(spaceId);
			result.setData(data);
			result.setMessage("查询成功");
			result.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
			result.setMessage("没有查询到相应的记录!");
			result.setStatus(DataStatus.HTTP_FAILE);
		}
		return result;
	}

	/**
	 * 现场负责人保存方法
	 * 
	 * @author xiang_wang 2015年10月23日下午4:10:17
	 */
	@RequestMapping(value = "saves.do")
	@ResponseBody
	public Map<String, Object> saves(AddAssistantReqDto assistantDto,
			String jsonClasss) throws Exception {
		if (StringUtils.isBlank(assistantDto.getAccount())
				|| StringUtils.isBlank(assistantDto.getName())
				|| StringUtils.isBlank(assistantDto.getPassword())) {
			return MessageResp.getMessage(false, "请求参数不能为空~");
		}
		String md5passwd = MD5Coder.encodeMD5Hex(assistantDto.getPassword());
		assistantDto.setPassword(md5passwd);
		Integer resObject = (Integer) iAssistantService.save(assistantDto,
				jsonClasss);
		if (resObject == 0) {
			return MessageResp.getMessage(false, "用户名已经存在~");
		}
		if (resObject == 1) {
			return MessageResp.getMessage(true, "新增成功~");
		}
		if (resObject == 2) {
			return MessageResp.getMessage(false, "记录已存在，不能重复提交~");
		}
		return MessageResp.getMessage(false, "未知错误~");
	}

	/**
	 * 现场负责人编辑操作
	 * 
	 * @param assistantDto
	 * @param jsonClasss
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "edits.do")
	@ResponseBody
	public Map<String, Object> edit(AddAssistantReqDto assistantDto,
			String jsonClasss) throws Exception {
		if (StringUtils.isBlank(assistantDto.getAccount())
				|| StringUtils.isBlank(assistantDto.getName())) {
			return MessageResp.getMessage(false, "请求参数不能为空~");
		}
		if (StringUtils.isNotBlank(assistantDto.getPassword())) {
			String md5passwd = MD5Coder
					.encodeMD5Hex(assistantDto.getPassword());
			assistantDto.setPassword(md5passwd);
		}
		Integer resObject = (Integer) iAssistantService.edit(assistantDto,
				jsonClasss);
		if (resObject == 0) {
			return MessageResp.getMessage(false, "该用户不存在~");
		}
		if (resObject == 1) {
			return MessageResp.getMessage(true, "新增成功~");
		}
		return MessageResp.getMessage(false, "未知错误~");
	}

	/**
	 * 现场负责人跳转编辑页
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "goEdit.do")
	public ModelAndView goEdit(String id) {
		AssistantDto assistantDto = iAssistantService.getAssistant(id);
		List<AssistantSpaceTimeRespDto> assistantSpaceTimeRespDtolists = iAssistantService
				.findAssistantAddressTimeById(id);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		mv.addObject("data", assistantDto);
		mv.addObject("spaces", assistantSpaceTimeRespDtolists);
		mv.setViewName("/assistant/edit");
		return mv;
	}

	/**
	 * 批量删除现场负责人
	 * 
	 * @param delAsIds
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "delAsIds.do")
	@ResponseBody
	public Map<String, Object> delAsIds(String delAsIds) throws Exception {
		if (StringUtils.isBlank(delAsIds)) {
			return MessageResp.getMessage(false, "请求参数不能为空~");
		}
		Integer resObject = (Integer) iAssistantService.delAsIds(delAsIds);
		if (resObject == 0) {
			return MessageResp.getMessage(false, "无此条记录~");
		}
		if (resObject == 1) {
			return MessageResp.getMessage(true, "删除成~");
		}
		return MessageResp.getMessage(false, "未知错误~");
	}
}