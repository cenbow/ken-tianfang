package com.tianfang.admin.controller.sport;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.admin.controller.PageData;
import com.tianfang.business.dto.AlbumDto;
import com.tianfang.business.dto.AlbumPictureDto;
import com.tianfang.business.dto.SportTeamDto;
import com.tianfang.business.service.IAlbumPicService;
import com.tianfang.business.service.IAlbumService;
import com.tianfang.business.service.ITeamService;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.StringUtils;

/**
 * 相片控制器
 * @author YIn
 * @time:2015年11月13日 下午6:08:21
 * @ClassName: AlbumController
 * @Description: TODO
 * @
 */
@Controller
@RequestMapping(value = "/albumPic")
public class AlbumPicController extends BaseController{
	protected static final Log logger = LogFactory.getLog(AlbumPicController.class);
	@Autowired
	private IAlbumPicService iAlbumPicService;
	
	@Autowired
	private IAlbumService iAlbumService;
	
	@Autowired
	private ITeamService iTeamService;
	/**
	 * 球队首页相片轮播显示
	 * @author YIn
	 * @time:2015年11月13日 下午5:53:30
	 */
	 /*
  	@RequestMapping("indexAlbumList.do")
	public ModelAndView indexAlbumList(AlbumPictureDto  query){
		logger.info("AlbumPictureDto query : " + query);
		List<AlbumPictureDto> result = iAlbumService.findIndexAlbum(query);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		mv.addObject("pageList",result);
		mv.addObject("query", query);
		mv.setViewName("/albumPic/list");
		return mv; 
	}
	*/
	
	/**
	 * 球队相片显示
	 * @author YIn
	 * @time:2015年11月13日 下午5:53:30
	 */
	@RequestMapping(value = "findAlbumPicByPage.do")
	public ModelAndView findAlbumPicByPage(AlbumPictureDto query, ExtPageQuery page) {
		logger.info("AlbumPictureDto query : " + query);
		PageResult<AlbumPictureDto> result = iAlbumPicService.findTeamAlbumPic(query,
				page.changeToPageQuery());
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		List<AlbumDto> albumList = iAlbumService.findAlbumList();
		List<SportTeamDto>  list = iTeamService.getAllTeam();
		mv.addObject("allteam", list);
		mv.addObject("allAlbum", albumList);
		mv.addObject("pageList", result);
		mv.addObject("query", query);
		mv.setViewName("/sport/albumPic/albumPic_list");
		return mv;
	}
	
	/**
	 * 添加球队相片
	 * @author YIn
	 * @time:2015年11月13日 下午5:53:30
	 */
	@ResponseBody
	@RequestMapping("/addAlbumPic.do")
	public Map<String, Object> addAlbumPic(AlbumPictureDto albumPictureDto, HttpSession session){
		logger.info("AlbumPictureDto"+albumPictureDto);
		/*SportAdminDto trainAdmin = (SportAdminDto) session.getAttribute(Const.SESSION_USER);
        if (null == trainAdmin) {
            return MessageResp.getMessage(false,"当前登录用户超时，请重新登录");
        }
        albumPictureDto.setPublisher(trainAdmin.getAccount());*/
		
		AlbumDto albumDto = iAlbumService.getAlbumById(albumPictureDto.getAlbumId());
		albumPictureDto.setTeamId(albumDto.getTeamId());
		
        Integer status = 0;
		if(StringUtils.isBlank(albumPictureDto.getPic())){
			String[] urls=albumPictureDto.getPics().split(",");
			for(int i = 0;i < urls.length;i++){
				String imgurl = urls[i];
				albumPictureDto.setPic(imgurl);
				status = iAlbumPicService.addAlbumPic(albumPictureDto);
				
			}
		}
		
		if(status == 0){
			return MessageResp.getMessage(false,"添加相片失败！");
		}
		return MessageResp.getMessage(true,"添加相片成功！");
	}
	
	/**
	 * 修改球队相片
	 * @author YIn
	 * @time:2015年11月13日 下午5:53:30
	 */
	
	@ResponseBody
	@RequestMapping("/updateAlbumPic.do")
	public Map<String, Object> updateAlbum(AlbumPictureDto albumPictureDto){
		logger.info("AlbumPictureDto"+albumPictureDto);
		int status = iAlbumPicService.updateAlbumPic(albumPictureDto);
		if(status == 0){
			return MessageResp.getMessage(false,"更新相片失败！");
		}
		return MessageResp.getMessage(true,"更新相片成功！");
	}
	
	/**
	 * 删除球队相片
	 * @author YIn
	 * @time:2015年11月13日 下午5:53:30
	 */
	@ResponseBody
	@RequestMapping("/delAlbumPic.do")
	public Map<String, Object> delAlbumPic(AlbumPictureDto albumPictureDto){
		logger.info("AlbumPictureDto"+albumPictureDto);
		int status = iAlbumPicService.delAlbumPic(albumPictureDto.getId());
		if(status == 0){
			return MessageResp.getMessage(false,"删除相片失败！");
		}
		return MessageResp.getMessage(true,"删除相片成功！");
	}
	
	/**
	 * 批量删除
	 * @author YIn
	 * @time:2015年11月16日 下午3:14:51
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/delByIds.do")
    public Map<String, Object> delByIds(String  ids) throws Exception{
	    if (StringUtils.isBlank(ids)) {
	        return MessageResp.getMessage(false, "请选择需要删除的项！");
	    }
	    Integer resObject =(Integer) iAlbumPicService.delByIds(ids);
	    if (resObject == 0) {
            return MessageResp.getMessage(false, "无此条记录！");
        }
	    if (resObject == 1) {
            return MessageResp.getMessage(true, "删除成功！");
        }
	    return MessageResp.getMessage(false, "未知错误！");
	}
	
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAlbumPicAdd")
	public ModelAndView goAdd(){
		logger.info("去相片新增页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("/sport/albumPic/albumPic_add");
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
	@RequestMapping(value="/goAlbumPicEdit")
	public ModelAndView goEdit(String teamId){
		logger.info("去相片修改页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		AlbumPictureDto result =iAlbumPicService.selectAlbumPicById(teamId);
		try {
			mv.setViewName("/sport/albumPic/albumPic_Edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", result);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/setPageOver")
	public Map<String, Object> setPageOver(String picId){

		AlbumPictureDto albumPicDto = new AlbumPictureDto();
		albumPicDto.setId(picId);
		AlbumPictureDto picDto = iAlbumPicService.getAlbumPicByDto(albumPicDto);
		if(picDto!=null){
			AlbumDto albumDto = iAlbumService.getAlbumById(picDto.getAlbumId());
			SportTeamDto teamDto = new SportTeamDto();
			teamDto.setId(albumDto.getTeamId());
			teamDto.setPageCover(picDto.getPic());
			iTeamService.editTeam(teamDto);
			return MessageResp.getMessage(true, "处理成功！");
		}else{
			return MessageResp.getMessage(false, "未知错误！");
		}    
	}
}
