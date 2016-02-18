package com.tianfang.admin.controller.train;

import java.util.HashMap;
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
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.CourseTagDto;
import com.tianfang.train.service.ITrainingTagService;


@Controller
@RequestMapping(value = "/tag")
public class TrainTagManageController extends BaseController{
	protected static final Log logger = LogFactory
			.getLog(TrainTagManageController.class);

	@Autowired
	private ITrainingTagService trainingTagService;

	/***
	 * 分页查询课程标签信息
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/findPage.do")
	public ModelAndView findByPage(CourseTagDto query,
			ExtPageQuery page) {
		logger.info("TrainTagManageController query:" + query);
		if (null != query.getTagName() && !query.getTagName().equals("")) {
		    query.setTagName(query.getTagName());
		}
		if (null != query.getTagName() && query.getTagName().equals("")) {
            query.setTagName(null);
        }
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		query.setStatus(DataStatus.ENABLED);
		PageResult<CourseTagDto> results = trainingTagService.findByPage(query,
				page.changeToPageQuery());
		mv.addObject("pageList", results);
		mv.addObject("newsDto", query);
		mv.setViewName("/course/courseTag_list");
		return mv;
	}

	/**
	 * 
	 * openAddView：打开新增标签页面
	 * @return
	 * @exception	
	 * @author Administrator
	 * @date 2015年10月27日 上午11:10:14
	 */
	@RequestMapping(value = "/openAddView.do")
	public ModelAndView openAddView() {
	    logger.info("打开新增标签页面");
	    ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            mv.setViewName("/course/courseTag_add");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }                       
        return mv;
	}	
	
	/***
	 * 新增标签
	 * 
	 * @param courseTagDto
	 * @return
	 */
	@RequestMapping(value = "/save.do")
	@ResponseBody
	public Map<String, Object> insertTag(CourseTagDto courseTagDto) {
		logger.info("TrainTagManageController courseTagDto:" + courseTagDto);
		if (StringUtils.isBlank(courseTagDto.getTagName())) {
			return MessageResp.getError("请求参数不能为空~");
		}
		Integer resObject = (Integer) trainingTagService
				.insertTag(courseTagDto);
		if (resObject == 0) {
			return MessageResp.getSuccess("新增成功~");
		}
		if (resObject == 1) {
			return MessageResp.getError("标签已存在！");
		}
		if (resObject == 2) {
			return MessageResp.getSuccess("新增成功~");
		}
		return MessageResp.getError("未知错误~");
	}

	@RequestMapping(value = "/deleteTag.do")
	@ResponseBody
	public Map<String, Object> deleteTag(String id) {
		if (StringUtils.isBlank(id)) {
			return MessageResp.getError("请求参数不能为空~");
		}
		Integer resObject = (Integer) trainingTagService.deleteById(id);
		if (resObject == 0) {
			return MessageResp.getError("无此条记录~");
		}
		if (resObject == 1) {
			return MessageResp.getSuccess("删除成功！");
		}
		return MessageResp.getError("未知错误~");
	}

	/**
	 * 
	 * openEditView：打开编辑标签页
	 * @return
	 * @exception	
	 * @author Administrator
	 * @date 2015年10月27日 上午11:53:48
	 */
	@RequestMapping(value = "/openEditView.do")
    public ModelAndView openEditView() {
        logger.info("打开编辑标签页");
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageData pd = new PageData();
        pd = this.getPageData();
        String id = pd.getString("id");
        CourseTagDto courseTagDto = trainingTagService.getTag(id);
        mv.setViewName("/course/courseTag_edit");
        mv.addObject("datas", courseTagDto);
        return mv;
    }
	
	@RequestMapping(value = "/updateTag.do")
	@ResponseBody
	public Map<String, Object> updateTag(CourseTagDto courseTagDto) {
		if (StringUtils.isBlank(courseTagDto.getTagName())) {
			return MessageResp.getError("请求参数不能为空~");
		}
		Integer resObject = (Integer) trainingTagService
				.updateTag(courseTagDto);
		if (resObject == 0) {
			return MessageResp.getError("标签名已经存在~");
		}
		if (resObject == 1) {
			return MessageResp.getSuccess("修改成功！");
		}
		return MessageResp.getError("未知错误~");
	}

	@RequestMapping(value = "/getTag.do")
	@ResponseBody
	public Map<String, Object> getTag(String id) {
		CourseTagDto courseTagDto = trainingTagService.getTag(id);
		if (courseTagDto == null) {
			return MessageResp.getError("没有此条信息！");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("data", courseTagDto);
		return result;
	}
}
