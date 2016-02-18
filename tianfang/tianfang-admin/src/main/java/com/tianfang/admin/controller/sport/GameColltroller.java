package com.tianfang.admin.controller.sport;

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
import com.tianfang.business.dto.SportGameDto;
import com.tianfang.business.service.IGameService;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.FileUtils;
import com.tianfang.common.util.StringUtils;
/**
 * 热门赛事
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/game")
public class GameColltroller extends BaseController {
	
	protected static final Log logger = LogFactory.getLog(GameColltroller.class);
	@Autowired
	private IGameService gameService;
	
	/**
	 * 查询赛事集合  ----全部信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getGameAll")
	public List<SportGameDto> getGameAll(){
		List<SportGameDto> lis =gameService.getGameAll();
		return lis;
	}
	/**
	 * 查询赛事集合   -----分页信息
	 * @param page
	 * @param sportGameDto
	 * @return
	 */
	@RequestMapping("/toGameAll")
	public ModelAndView sportAllPage(ExtPageQuery page,SportGameDto sportGameDto){
		logger.info("分页查询Game数据   参数:"+sportGameDto);
		ModelAndView  mv = this.getModelAndView(this.getSessionUserId());
		Map<String,Object> map = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(sportGameDto.getTitle())){
			map.put("title", sportGameDto.getTitle());
		}
		PageResult<SportGameDto> result = gameService.getAllPage(map,page.changeToPageQuery());
		mv.addObject("pageList", result);
		mv.setViewName("/sport/game/game_list");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/addGame")
	public Map<String, Object> insertGame(SportGameDto sportGameDto){
		logger.info("新增Game 参数:"+sportGameDto);
		long statu = gameService.insertGame(sportGameDto);
		if(statu == 0){
			return MessageResp.getMessage(false,"添加失败~~~");
		}
		return MessageResp.getMessage(true,"添加成功~~~");
	}
	
	@ResponseBody
	@RequestMapping("/upGame")
	public Map<String, Object> updateGame(SportGameDto sportGameDto){
		logger.error("修改Game  参数:"+sportGameDto);
		long statu = gameService.updateGame(sportGameDto);
		if(statu == 0){
			return MessageResp.getMessage(false,"修改失败~~~");
		}
		return MessageResp.getMessage(true,"修改成功~~~");
	}
	
	@ResponseBody
	@RequestMapping("/delGame")
	public Map<String, Object> deleteGame(SportGameDto sportGameDto){
		logger.info("删除Game 参数"+sportGameDto);
		long statu = gameService.deleteGame(sportGameDto.getId());
		if(statu == 0){
			return MessageResp.getMessage(false,"删除失败~~~");
		}
		return MessageResp.getMessage(true,"删除成功~~~");
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logger.info("去新增热门赛事页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("/sport/game/game_add");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(String id){
		logger.info("去修改热门赛事页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);		
		SportGameDto result =gameService.getByCriteria(map);
		try {
			mv.setViewName("/sport/game/game_Edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", result);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	/**
	 * 修改页面显示顺序
	 * @param id
	 * @return
	 */
	@RequestMapping("/goRanking")
	public ModelAndView goRanking(String id,String rid){
		logger.info("修改页面排序~~ ");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		try {
			mv.setViewName("/sport/game/page_position");
			mv.addObject("msg", "save");
			mv.addObject("pd", id);
			mv.addObject("rid", rid);
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
		return mv;
	}
	/**
	 * 修改页面显示顺序
	 * @param id   当前对象
	 * @param pageRanking   排序字段
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upRanking")
	public Map<String,Object> updateRanking(String id ,String pageRanking){
		logger.info("修改页面显示顺序");
		long stat = gameService.updateRanking(id,pageRanking);
		return MessageResp.getMessage(true,"修改成功~~~");
	}
}
