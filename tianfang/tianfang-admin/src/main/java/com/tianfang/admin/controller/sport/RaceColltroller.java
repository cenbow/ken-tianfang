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
import com.tianfang.business.dto.SportRaceDto;
import com.tianfang.business.dto.SportTeamDto;
import com.tianfang.business.enums.RaceType;
import com.tianfang.business.service.ISportRaceService;
import com.tianfang.business.service.ITeamService;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;

/**
 * 赛事管理
 * 
 * @author wangxiang
 *
 *         2015年11月17日
 */
@Controller
@RequestMapping(value = "/sport/race")
public class RaceColltroller extends BaseController {
	protected static final Log logger = LogFactory
			.getLog(RaceColltroller.class);

	@Autowired
	private ISportRaceService raceService;
	@Autowired
	private ITeamService teamService;

	/**
	 * 赛事新增页面
	 * 
	 * @return
	 * @author wangxiang
	 * 2015年11月17日下午3:25:16
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() {
		logger.info("赛事新增页面新增页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		try {
			List<SportTeamDto> teams = teamService.getAllTeam();
			mv.setViewName("/sport/race/add");
			mv.addObject("types", RaceType.getValus());
			mv.addObject("teams", teams);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 保存赛事
	 * 
	 * @param dto
	 * @return
	 * @throws IOException
	 * @author wangxiang
	 * 2015年11月17日下午3:25:51
	 */
	@ResponseBody
	@RequestMapping(value = "/save.do")
	public Map<String, Object> insert(SportRaceDto dto) throws IOException {
		try {
			int status = raceService.saveRace(dto);
			if (status != 1) {
				return MessageResp.getMessage(false, "添加失败~");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return MessageResp.getMessage(false, e.getMessage());
		}

		return MessageResp.getMessage(true, "添加成功~");
	}

	/**
	 * 根据id删除(可批量删除)
	 * 
	 * @param id
	 * @return
	 * @author wangxiang
	 * 2015年11月17日下午3:26:43
	 */
	@ResponseBody
	@RequestMapping(value = "/delecte.do")
	public Map<String, Object> del(String id) {
		if (id == null || "".equals(id)) {
			return MessageResp.getMessage(false, "请求参数不能为空~");
		}
		raceService.deleteRace(id);
		return MessageResp.getMessage(true, "删除成功~");
	}

	/**
	 * 跳转赛事修改页面
	 * @param id
	 * @return
	 * @author wangxiang
	 * 2015年11月17日下午3:27:17
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit(String id) {
		logger.info("赛事修改页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		try {
			List<SportTeamDto> teams = teamService.getAllTeam();
			SportRaceDto dto = raceService.getRaceById(id);
			mv.setViewName("/sport/race/edit");
			mv.addObject("teams", teams);
			mv.addObject("types", RaceType.getValus());
			mv.addObject("data", dto);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 更新赛事修改
	 * 
	 * @param dto
	 * @return
	 * @throws IOException
	 * @author wangxiang
	 * 2015年11月17日下午3:28:40
	 */
	@ResponseBody
	@RequestMapping(value = "/update.do")
	public Map<String, Object> update(SportRaceDto dto) throws IOException {
		try {
			int status = raceService.updateRace(dto);
			if (status != 1) {
				return MessageResp.getMessage(false, "修改失败~");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return MessageResp.getMessage(false, e.getMessage());
		}

		return MessageResp.getMessage(true, "修改成功~");
	}

	/**
	 * 赛事列表
	 * 
	 * @param dto
	 * @param page
	 * @return
	 * @author wangxiang
	 * 2015年11月17日下午3:29:45
	 */
	@RequestMapping(value = "/findPage.do")
	public ModelAndView findPage(SportRaceDto dto, ExtPageQuery page) {
		logger.info("SportRaceDto dto : " + dto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<SportRaceDto> result = raceService.findRaceByParams(
				dto, page.changeToPageQuery().getCurrPage(), page
						.changeToPageQuery().getPageSize());
		mv.addObject("pageList", result);
		mv.addObject("params", dto);
		mv.addObject("types", RaceType.getValus());
		mv.setViewName("/sport/race/list");

		return mv;
	}
}