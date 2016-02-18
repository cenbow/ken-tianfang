package com.tianfang.home.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.business.dto.AlbumDto;
import com.tianfang.business.dto.SportGameDto;
import com.tianfang.business.dto.SportTeamDto;
import com.tianfang.business.service.IAlbumPicService;
import com.tianfang.business.service.IAlbumService;
import com.tianfang.business.service.IGameService;
import com.tianfang.business.service.ITeamService;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.FileUtils;
import com.tianfang.common.util.StringUtils;

/**
 *  热门赛事管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/game")
public class SportGameColltroller extends BaseController {

	protected static final Log logger = LogFactory.getLog(SportGameColltroller.class);
	@Autowired
	private IGameService gameService;
	
	@Autowired
	private ITeamService iteamSevice;
	
	@Autowired
	private IAlbumPicService iAlbumPicService;
	
	@Autowired
	private IAlbumService iAlbumService;
	
	/**
	 * 跳转到赛事页面
	 * @return
	 */
	@RequestMapping("/toGame")
	public ModelAndView sportAllPage(){
		ModelAndView mv = getModelAndView();
		mv.setViewName("/competition_match");
		return mv;
	}
	
	/**
	 * 跳转到球队页面
	 * @return
	 */
	@RequestMapping("/toTeam/{gameId}")
	public ModelAndView toTeam(@PathVariable String gameId){
		ModelAndView mv = getModelAndView();
		SportTeamDto teamDto = new SportTeamDto();
		teamDto.setGameId(gameId);
		List<SportTeamDto> teamList = iteamSevice.queryTeamByParams(teamDto);
		mv.addObject("teamList",teamList);
		
//		id
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", gameId);
		SportGameDto gameDto = gameService.getByCriteria(map);
		mv.addObject("game",gameDto);
		
		//随机相片
		AlbumDto albumDto = new AlbumDto();
		albumDto.setRandNumber(6);
		albumDto.setGameId(gameId);
		List<AlbumDto> dataList = iAlbumService.findTeamAlbumByRand(albumDto);
		mv.addObject("dataList",dataList);
		
		mv.setViewName("/competition_team");
		return mv;
	}
	
	/**
	 * 跳转到球队页面
	 * @return
	 */
	@RequestMapping("/toTeam")
	public ModelAndView toTeamT(String gameId){
		ModelAndView mv = getModelAndView();
		mv.setViewName("/competition_team");
		return mv;
	}
	
	/**
	 * 热门赛事信息列表 
	 * @return  
	 */
	@ResponseBody
	@RequestMapping("/getGameAll")
	public List<SportGameDto> getGameAll(){
		List<SportGameDto> lis =gameService.getGameAll();
		return lis;
	}
	
	
	/**
	 * 热门赛事列表
	 * @param page
	 * @param sportGameDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getAllPage")
	public Response<PageResult<SportGameDto>> sportAllPage(ExtPageQuery page,SportGameDto sportGameDto){
		Response<PageResult<SportGameDto>> result =new Response<PageResult<SportGameDto>>();
		Map<String,Object> map = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(sportGameDto.getId())){
			map.put("id", sportGameDto.getId());
		}
		if(!StringUtils.isEmpty(sportGameDto.getTitle())){
			map.put("title", sportGameDto.getTitle());
		}
		PageResult<SportGameDto> pg = gameService.getAllPage(map,page.changeToPageQuery());
		result.setData(pg);
		return result;
	}
	/**
	 * 添加热门赛事
	 * @param sportGameDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addGame")
	public Map<String, Object> insertGame(SportGameDto sportGameDto){
		logger.info("新增Game 参数:"+sportGameDto);
		
		if(!StringUtils.isEmpty(sportGameDto.getMicroPic())){
			String microPic="";
			try {
				microPic = FileUtils.uploadImage(sportGameDto.getMicroPic());
			} catch (Exception e) {
				e.printStackTrace();
			}
			sportGameDto.setMicroPic(microPic);
		}
		long statu = gameService.insertGame(sportGameDto);
		if(statu == 0){
			return MessageResp.getMessage(false,"添加失败~~~");
		}
		return MessageResp.getMessage(true,"添加成功~~~");
	}
	
	@ResponseBody
	@RequestMapping("/upGame")
	public Map<String, Object> updateGame(SportGameDto sportGameDto){
		logger.info("修改Game  参数:"+sportGameDto);
		if(!StringUtils.isEmpty(sportGameDto.getMicroPic())){
			String microPic="";
			try {
				microPic = FileUtils.uploadImage(sportGameDto.getMicroPic());
			} catch (Exception e) {
				e.printStackTrace();
			}
			sportGameDto.setMicroPic(microPic);
		}
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
}
