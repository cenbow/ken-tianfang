package com.tianfang.admin.controller.sport;

import java.io.IOException;
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
import com.tianfang.business.dto.SportNoticeDto;
import com.tianfang.business.dto.SportTeamDto;
import com.tianfang.business.service.ISportNoticeService;
import com.tianfang.business.service.ITeamService;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;

/**
 * 球队公告
 * 
 * @author xiang_wang
 *
 *         2015年11月14日下午5:01:13
 */
@Controller
@RequestMapping(value = "/sport/notice")
public class NoticeColltroller extends BaseController {
	protected static final Log logger = LogFactory
			.getLog(NoticeColltroller.class);

	@Autowired
	private ISportNoticeService noticeService;
	@Autowired
	private ITeamService teamService;

	/**
	 * 球队公告新增页面
	 * @return
	 * @author wangxiang
	 * 2015年11月16日下午1:17:26
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logger.info("球队公告新增页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		try {
			List<SportTeamDto> teams = teamService.getAllTeam();
			mv.setViewName("/sport/notice/add");
			mv.addObject("teams", teams);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 新增球队公告
	 * @param dto
	 * @return
	 * @throws IOException
	 * @author wangxiang
	 * 2015年11月16日下午3:08:48
	 */
	@ResponseBody
	@RequestMapping(value="/save.do")
	public  Map<String, Object> insert(SportNoticeDto dto) throws IOException{
		try {
			int status = noticeService.saveNotice(dto);
			if (status != 1){
				return MessageResp.getMessage(false,"添加失败~");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return MessageResp.getMessage(false, e.getMessage());
		}
		
		return MessageResp.getMessage(true,"添加成功~");
	}
	
	/**
	 * 根据id删除(可批量删除)
	 * @param id
	 * @return
	 * @author wangxiang
	 * 2015年11月16日下午1:35:31
	 */
	@ResponseBody
	@RequestMapping(value="/delecte.do")
	public Map<String, Object> del(String id){
		if(id == null || "".equals(id)){
			return MessageResp.getMessage(false, "请求参数不能为空~") ;
		}
		noticeService.deleteNotice(id);
		return MessageResp.getMessage(true, "删除成功~");
	}
	
	/**
	 * 球队公告修改页面
	 * @param id
	 * @return
	 * @author wangxiang
	 * 2015年11月16日下午3:34:08
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(String id){
		logger.info("球队公告修改页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		try {
			List<SportTeamDto> teams = teamService.getAllTeam();
			SportNoticeDto dto = noticeService.getNoticeById(id);
			mv.setViewName("/sport/notice/edit");
			mv.addObject("teams", teams);
			mv.addObject("data", dto);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 球队公告修改操作
	 * @param dto
	 * @return
	 * @throws IOException
	 * @author wangxiang
	 * 2015年11月16日下午3:49:33
	 */
	@ResponseBody
	@RequestMapping(value="/update.do")
	public Map<String, Object> update(SportNoticeDto dto) throws IOException{
		try {
			int status = noticeService.updateNotice(dto);
			if (status != 1){
				return MessageResp.getMessage(false,"修改失败~");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return MessageResp.getMessage(false, e.getMessage());
		}
		
		return MessageResp.getMessage(true, "修改成功~");
	}
	
	/**
	 * 球队公告列表
	 * @param dto
	 * @param page
	 * @return
	 * @author wangxiang
	 * 2015年11月16日下午1:16:00
	 */
	@RequestMapping(value = "/findPage.do")
	public ModelAndView findPage(SportNoticeDto dto, ExtPageQuery page) {
		logger.info("SportNoticeDto dto : " + dto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<SportNoticeDto> result = noticeService.findNoticeByParams(dto, page.changeToPageQuery().getCurrPage(), page.changeToPageQuery().getPageSize());
		mv.addObject("pageList", result);
		mv.addObject("params", dto);
		mv.setViewName("/sport/notice/list");
		
		return mv;
	}
}