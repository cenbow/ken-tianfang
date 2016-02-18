package com.tianfang.home.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.business.dto.SportAddressesDto;
import com.tianfang.business.dto.SportGameDto;
import com.tianfang.business.dto.SportTeamDto;
import com.tianfang.business.dto.SportTeamMembersDto;
import com.tianfang.business.dto.SportTeamTypeDto;
import com.tianfang.business.service.IGameService;
import com.tianfang.business.service.ISportAddressesService;
import com.tianfang.business.service.ISportTeamMembService;
import com.tianfang.business.service.ISportTeamTypeService;
import com.tianfang.business.service.ITeamService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.constants.PlayerPosition;
import com.tianfang.common.constants.SessionConstants;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.FileUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.user.dto.LoginUserDto;
import com.tianfang.user.dto.SportUserReqDto;
import com.tianfang.user.dto.UserType;
import com.tianfang.user.service.IUserService;

/**
 * 球队信息管理页面
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/team")
public class SportTeamInfoController extends BaseController {
	
	private static final int TOTAL = 10;
	protected static final Log logger = LogFactory.getLog(SportTeamInfoController.class);
	@Autowired
	private ITeamService iteamSevice;
	@Autowired
	private IGameService gameService;
	@Autowired
	private ISportTeamTypeService teamTypeService;
	@Autowired
	private ISportAddressesService addressService;
	@Resource
	private ISportTeamMembService sportTeamMembService;
	@Autowired
	private IUserService iUserService;	
	
	/**
	 * ajax请求 单表获取  球队全部信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getAllTeam")
	public List<SportTeamDto> getAllTeam(){
		return iteamSevice.getAllTeam();
	}
	/**
	 * ajax请求  单表查询分页
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllTeamPage")
	public Response<PageResult<SportTeamDto>>  getAllTeam(ExtPageQuery page,SportTeamDto teamDto){
		Response<PageResult<SportTeamDto>> result  = new  Response<PageResult<SportTeamDto>>();
		Map<String, Object> map = byCriteria(teamDto);
		PageResult<SportTeamDto> pageList = iteamSevice.getByCriteriaPage(page.changeToPageQuery(),map);
		result.setData(pageList);
		return result;
	}
 	
	@ResponseBody
	@RequestMapping(value="/saveTeam")
	public Map<String ,Object> saveTeam(SportTeamDto teamDto,HttpSession session){
		logger.info("添加球队信息~~~");
		String isNo = session.getAttribute("resubmit").toString();
		if(isNo.equals("noSubmit")){
			return MessageResp.getMessage(false,"请不要重复提交~~~");
		}
		LoginUserDto logDto= (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
    	if(logDto==null){
    		return MessageResp.getMessage(false,"请先登录~~~");
    	}
    	SportUserReqDto userInfo= iUserService.selectUserAccountByUserId(logDto.getId());
    	if(userInfo.getUtype()==UserType.DEFAULT.getIndex()){
    		return MessageResp.getMessage(false,"普通会员不能注册球队~~~");
    	}
    	
    	teamDto.setOwnerId(userInfo.getId());
		long statu = iteamSevice.saveTeam(teamDto);
		if(statu == 0){
			return MessageResp.getMessage(false,"添加失败~~~");
		}
		session.setAttribute("resubmit", "noSubmit");
		return MessageResp.getMessage(true,"添加成功~~~");
	}
	/**
	 * 按id查询球队信息
	 * @author YIn
	 * @time:2015年12月10日 下午4:08:09
	 * @param teamDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findTeamByteamId")
	public Response<SportTeamDto> findTeamByteamId(SportTeamDto teamDto){
		Response<SportTeamDto> data = new Response<SportTeamDto>();
		SportTeamDto dto = iteamSevice.findTeamBy(teamDto);
		if(dto != null){
			data.setData(dto);
			data.setMessage("查询成功!");
			data.setStatus(200);
		}else{
			data.setMessage("查询失败!");
			data.setStatus(500);
		}
		return data;
	}
	
	@ResponseBody
	@RequestMapping(value="/edit")
	public Map<String,Object> editTeam(SportTeamDto teamDto,HttpSession session){
		long statu = iteamSevice.editTeam(teamDto);
		if(statu == 0){
			return MessageResp.getMessage(false, "修改失败~~~");
		}
		return MessageResp.getMessage(true, "修改成功~~~");
	}
	
	@ResponseBody
	@RequestMapping(value="/delTeam")
	public Map<String,Object> delTeam(String id){
		long statu = iteamSevice.delTeam(id);
		if(statu == 0){
			return MessageResp.getMessage(false, "删除失败~~~");
		}
		return MessageResp.getMessage(true, "删除成功~~~");
	}
	
	/**
	 * 跳转赛事下全部球队页面
	 * 
	 * @param request
	 * @param gameId
	 * @return
	 * @author xiang_wang
	 * 2015年11月20日下午2:17:08
	 */
	@RequestMapping("allTeamDetail")
	public ModelAndView queryGameAllTeam(HttpServletRequest request, @RequestParam(required=true)String gameId){
		ModelAndView mv = getModelAndView();
		if (StringUtils.isNotBlank(gameId)){
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("gameId", gameId);
			List<SportTeamDto> hots = iteamSevice.queryHotTeam(map, TOTAL);
			long total = iteamSevice.countTeam(map);
			map.clear();
			map.put("id", gameId);
			SportGameDto game = gameService.getByCriteria(map);
			List<SportTeamTypeDto> types = teamTypeService.getAllTeamType();
			
			request.setAttribute("hots", hots);
			request.setAttribute("game", game);
			request.setAttribute("total", total);
			request.setAttribute("types", types);
		}
		mv.setViewName("/competition_team_list");
		return mv;
	}
	
	/**
	 * ajax分页查询球队
	 * @param dto 支持查询参数{id,gameId,name,distruct,setUpTime,grade}
	 * @param page
	 * @return
	 * @author xiang_wang
	 * 2015年11月20日下午2:56:25
	 */
	@ResponseBody
	@RequestMapping(value="/queryTeam")
	public PageResult<SportTeamDto> teamPage(SportTeamDto dto, ExtPageQuery page){
		Map<String, Object> map = byCriteria(dto);
		PageResult<SportTeamDto> result = iteamSevice.selectTeam(map, page.changeToPageQuery());
		return result;
	}
	
	/**
	 * 检索条件
	 */
	private Map<String, Object> byCriteria(SportTeamDto teamDto){
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != teamDto){
			if(!StringUtils.isEmpty(teamDto.getId())){
				map.put("id", teamDto.getId());
			}
			if(!StringUtils.isEmpty(teamDto.getGameId())){
				map.put("gameId", teamDto.getGameId());
			}
			if(!StringUtils.isEmpty(teamDto.getName())){
				map.put("name", teamDto.getName());
			}
			if(!StringUtils.isEmpty(teamDto.getDistruct())){
				map.put("distruct", teamDto.getDistruct());
			}
			if(!StringUtils.isEmpty(teamDto.getSetUpTimeStr())){
				map.put("setUpTime", teamDto.getSetUpTimeStr());
			}
			if(!StringUtils.isEmpty(teamDto.getGrade())){
				map.put("grade", teamDto.getGrade());
			}
			if(!StringUtils.isEmpty(teamDto.getTeamType())){
				map.put("teamType", teamDto.getTeamType());
			}
		}
		
		return map;
	}
	/**
	 * 新增球队页面
	 * @return 返回页面
	 */
	@RequestMapping("/addTeam")
	public ModelAndView doAddTeam(HttpServletRequest request,SportAddressesDto addresses,HttpSession session){
		ModelAndView mv = getModelAndView();
		List<SportAddressesDto> lis = new ArrayList<SportAddressesDto>();
		session.setAttribute("resubmit", UUID.randomUUID()+"");
		addresses.setParentId("1 ");  //'1'所有省份的父节点
		lis = addressService.getDistrict(addresses);
		mv.addObject("result", lis);
		mv.setViewName("/team-create");
		return mv;
	}
	
	/**
	 * 根据省份id查询 区域
	 * @return 省份对应区域
	 */
	@ResponseBody
	@RequestMapping("/byCreateriaAddress")
	public Response<List<SportAddressesDto>> byCreateriaAddress(String id){
		Response<List<SportAddressesDto>>  result = new Response<List<SportAddressesDto>>();
		List<SportAddressesDto> lis= new ArrayList<SportAddressesDto>();
		SportAddressesDto addresses = new SportAddressesDto();
		addresses.setParentId(id.replace(",",""));
		lis= addressService.getDistrict(addresses);
		if(lis.size()<=0){
			return null;
		}
		SportAddressesDto res = new SportAddressesDto();
		res.setParentId(lis.get(0).getId()+"");
		lis=addressService.getDistrict(res);
		result.setData(lis);
		result.setMessage("success");
		result.setStatus(DataStatus.ENABLED);
		return result;
	}

	
	/**
	 * 用户修改上传图片
	 * @param myfile
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("/up")
	public Response<String> up(@RequestParam MultipartFile myfile) throws IOException{
		Response<String> result = new Response<String>();
		String fileName = FileUtils.uploadImage(myfile);
		result.setData("/"+fileName);
		return result;
	}
	
	
	/*
	 * 新增队员信息
	 */
	@ResponseBody
	@RequestMapping("/saveOrUpdate")
	public Response<SportTeamMembersDto> saveOrUpdate(SportTeamMembersDto dto) throws IOException{
		Response<SportTeamMembersDto> result = new Response<SportTeamMembersDto>();
		if (StringUtils.isBlank(dto.getPic())) {
			dto.setPic(null);
		}
		SportTeamMembersDto reDto = sportTeamMembService.saveOrUpdateMembers(dto);
		if(reDto!=null){
			String player = PlayerPosition.map.get(reDto.getPosition());
			reDto.setPosition(player);			
			result.setMessage("数据处理成功");
			result.setData(reDto);
		}else{
			result.setMessage("数据处理失败");
			result.setStatus(DataStatus.HTTP_FAILE);
		}
		return result;
	}
	/**
	 * 
		 * 此方法描述的是：删除队员
		 * @author: cwftalus@163.com
		 * @version: 2015年12月10日 下午5:04:20
	 */
	
	@ResponseBody
	@RequestMapping("/delete")
	public Response<String> delete(String memberId) throws IOException{
		Response<String> result = new Response<String>();
		if(sportTeamMembService.deleteMembers(memberId)){	
			result.setMessage("数据处理成功");
		}else{
			result.setMessage("数据处理失败");
			result.setStatus(DataStatus.HTTP_FAILE);
		}
		return result;
	}
	/**
	 * 
		 * 此方法描述的是：保存阵型
		 * @author: cwftalus@163.com
		 * @version: 2015年12月10日 下午5:04:20
	 */
	@ResponseBody
	@RequestMapping("/changeLine")
	public Response<String> changeLine(SportTeamDto teamDto) throws IOException{
		Response<String> result = new Response<String>();
		iteamSevice.editTeam(teamDto);
		result.setMessage("数据处理成功");
		return result;
	}
}