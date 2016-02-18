package com.tianfang.admin.controller.sport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.admin.controller.PageData;
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
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;

/**
 * 球隊信息
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/team")
public class SportTeamController extends BaseController{

	protected static final Log logger = LogFactory.getLog(SportTeamController.class);
	@Autowired
	private ITeamService iteamSevice;
	@Resource
	private ISportTeamMembService iSportTeamMembService;
	@Autowired
	private ISportTeamTypeService sportTeamType;
	@Autowired
	private IGameService gameService;
	@Autowired
	private ISportAddressesService addressService;
	
	@ResponseBody
	@RequestMapping(value="/getAllTeam")
	public List<SportTeamDto> getAllTeam(){
		return iteamSevice.getAllTeam();
	}
	
	@RequestMapping(value="/teamPage")
	public ModelAndView getAllPage(ExtPageQuery page,SportTeamDto teamDto){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		Map<String, Object> map = new HashMap<String, Object>();
		byCriteria(teamDto,map);
		PageResult<SportTeamDto> pageList = iteamSevice.getByCriteriaPage(page.changeToPageQuery(),map);
		List<SportTeamTypeDto> lis = sportTeamType.getAllTeamType();
		List<SportGameDto> lisGame =gameService.getGameAll();
		mv.addObject("game", lisGame);
		mv.addObject("pd", lis);
		mv.addObject("pageList", pageList);
		mv.addObject("newDto", map);
		mv.setViewName("sport/team/team_list");
		return mv;
	}
	
	/**
	 * 检索条件
	 */
	public void byCriteria(SportTeamDto teamDto, Map<String, Object> map){
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
		if(!StringUtils.isEmpty(teamDto.getGameId())){
			map.put("gameId", teamDto.getGameId());
		}
		if(teamDto.getStat()!=null){
			if(!StringUtils.isEmpty(teamDto.getStat().toString())){
				map.put("stat", teamDto.getStat());
			}			
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/saveTeam")
	public Map<String ,Object> saveTeam(SportTeamDto teamDto){
		logger.info("添加球队信息~~~");
		long statu = iteamSevice.saveTeam(teamDto);
		if(statu == 0){
			return MessageResp.getMessage(false,"添加失败~~~");
		}
		return MessageResp.getMessage(true,"添加成功~~~");
	}
	
	@ResponseBody
	@RequestMapping(value="/edit")
	public Map<String,Object> editTeam(SportTeamDto teamDto){
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
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(SportAddressesDto addresses){
		logger.info("战队新增页面~~~");
		ModelAndView mv =  this.getModelAndView(this.getSessionUserId());
		List<SportAddressesDto> lis = new ArrayList<SportAddressesDto>();
		addresses.setParentId("1 ");  //'1'所有省份的父节点
		lis = addressService.getDistrict(addresses);
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("/sport/team/team_add");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
			mv.addObject("resultAddress", lis);
		} catch (Exception e) {
			e.printStackTrace();
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
		SportTeamDto result = iteamSevice.selectById(map);
		result.setSetUpTimeStr(DateUtils.format(result.getSetUpTime(), DateUtils.YMD_DASH));
		List<SportAddressesDto> lis = new ArrayList<SportAddressesDto>();
		SportAddressesDto addresses = new SportAddressesDto();
		addresses.setParentId("1 ");  //'1'所有省份的父节点
		lis = addressService.getDistrict(addresses);
		try {
			mv.setViewName("sport/team/team_Edit");
			mv.addObject("msg", "save");
			mv.addObject("result", result);
			mv.addObject("resultAddress", lis);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
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
		if(id == null || id.equals("")){
			return null;
		}
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
	
	/**--------------------------队员管理----------------------------*/
	
	/**
	 * 通过tid查询团队成员
	 * @param tid
	 * @2015年11月16日
	 */
	@RequestMapping("/findMembers")
	public ModelAndView findMembers(SportTeamMembersDto dto, PageQuery page){
		if(page == null){
			page = new PageQuery();
			page.setCurrPage(1);
		}
		page.setPageSize(10);
		ModelAndView mv = getModelAndView(getSessionUserId());
		PageResult<SportTeamMembersDto> stLst = iSportTeamMembService.findMembersV02(dto, page);
		mv.setViewName("sport/team/member_list"); 
		mv.addObject("pageList", stLst);
		mv.addObject("dto", dto);
		return mv;
	}
	
	/**
	 * 跳转到新增队员页面
	 * @return
	 * @2015年11月18日
	 */
	@RequestMapping("/goAddMember")
	public ModelAndView goAddMember(String tid){
		
		ModelAndView mv = getModelAndView();

		List<SportTeamDto> pageList = getAllTeam();
		mv.addObject("teamLst", pageList);
		mv.addObject("tid", tid);
		mv.setViewName("sport/team/member_add");
		return mv;
	}
	
	/**
	 * 添加队员
	 * @param dto
	 * @return
	 * @2015年11月16日
	 */
	@ResponseBody
	@RequestMapping("/addMembers")
	public Response<String> addMembers(SportTeamMembersDto dto){
		
		Response<String> resp = new Response<String>();
		dto.setId(UUID.randomUUID().toString());
		boolean flag = iSportTeamMembService.addMembers(dto);
		if(flag){
			resp.setStatus(DataStatus.HTTP_SUCCESS);
			resp.setMessage("新增成功~");
		}else{
			resp.setStatus(DataStatus.HTTP_FAILE);
			resp.setMessage("新增失败~");
		}
		return resp;
	}
	
	/**
	 * 跳转到更队员信息新页面
	 * @param id
	 * @return
	 * @2015年11月18日
	 */
	@RequestMapping("/goUpdateMember")
	public ModelAndView goUpdateMember(String id){
		ModelAndView mv = getModelAndView();
		List<SportTeamDto> pageList = getAllTeam();
		mv.addObject("teamLst", pageList);
		SportTeamMembersDto dto = iSportTeamMembService.findMember(id);
		mv.addObject("member", dto);
		mv.setViewName("sport/team/member_edit");
		return mv;
	}
	/**
	 * 更新队员信息
	 * @param dto
	 * @return
	 * @2015年11月16日
	 */
	@ResponseBody
	@RequestMapping("/updateMember")
	public Response<String> updateMembers(SportTeamMembersDto dto){
		
		Response<String> resp = new Response<String>();
		boolean flag = iSportTeamMembService.updateMembers(dto);
		if(flag){
			resp.setStatus(DataStatus.HTTP_SUCCESS);
			resp.setMessage("修改成功~");
		}else{
			resp.setStatus(DataStatus.HTTP_FAILE);
			resp.setMessage("修改失败~");
		}
		return resp;
	}
	
	/**
	 * 根据队员id删除队员
	 * @param id
	 * @return
	 * @2015年11月16日
	 */
	@ResponseBody
	@RequestMapping("/deleteMembers")
	public Response<String> deleteMember(String id){
		
		Response<String> resp = new Response<String>();
		boolean flag = iSportTeamMembService.deleteMembers(id);
		if(flag){
			resp.setStatus(DataStatus.HTTP_SUCCESS);
			resp.setMessage("删除成功~");
		}else{
			resp.setStatus(DataStatus.HTTP_FAILE);
			resp.setMessage("删除失败~");
		}
		return resp;
	}
	
	
}
