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
import com.tianfang.admin.controller.Const;
import com.tianfang.admin.controller.PageData;
import com.tianfang.admin.dto.SportAdminDto;
import com.tianfang.business.dto.AlbumDto;
import com.tianfang.business.dto.SportTeamDto;
import com.tianfang.business.service.IAlbumService;
import com.tianfang.business.service.ITeamService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.StringUtils;

/**
 * 相册控制器
 * @author YIn
 * @time:2015年11月13日 下午5:48:31
 * @ClassName: AlbumController
 * @Description: TODO
 * @
 */
@Controller
@RequestMapping(value = "/album")
public class AlbumController extends BaseController{
	protected static final Log logger = LogFactory.getLog(AlbumController.class);
	@Autowired
	private IAlbumService iAlbumService;
	
	
	@Autowired
	private ITeamService iTeamService;
	/**
	 * 球队首页相册轮播显示
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
	
	 /*	Response<List<AlbumPictureDto>> result = new Response<List<AlbumPictureDto>>();
		List<AlbumPictureDto> albumPicList = iAlbumService.findIndexAlbum(albumPictureDto);
		if(albumPicList.size()>0){
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("查询相册图片成功！");
			result.setData(albumPicList);
			return result;
		}else{
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("查询相册图片失败！");
			return result;
		}*/
	

	/**
	 * 球队相册显示
	 * @author YIn
	 * @time:2015年11月13日 下午5:53:30
	 */
	@RequestMapping(value = "findAlbumByPage.do")
	public ModelAndView findAlbumByPage(AlbumDto query, ExtPageQuery page) {
		logger.info("AlbumDto query : " + query);
		PageResult<AlbumDto> result = iAlbumService.findTeamAlbum(query,
				page.changeToPageQuery());
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		
		List<SportTeamDto>  list = iTeamService.getAllTeam();
		mv.addObject("allteam", list);
		mv.addObject("pageList", result);
		mv.addObject("query", query);
		mv.setViewName("/sport/album/album_list");
		return mv;
	}
	
	/**
	 * 查询所有相册
	 * @author YIn
	 * @time:2015年11月18日 下午5:24:44
	 * @return
	 */
	
	@RequestMapping(value = "findAlbumList.do")
	@ResponseBody
	public Response<List<AlbumDto>> findAlbumList(){
	Response<List<AlbumDto>> result = new Response<List<AlbumDto>>();
	List<AlbumDto> albumList = iAlbumService.findAlbumList();
	if(albumList.size()>0){
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("查询相册图片成功！");
		result.setData(albumList);
		return result;
	}else{
		result.setStatus(DataStatus.HTTP_FAILE);
		result.setMessage("查询相册图片失败！");
		return result;
	}
	}
	
	/**
	 * 添加球队相册
	 * @author YIn
	 * @time:2015年11月13日 下午5:53:30
	 */
	@ResponseBody
	@RequestMapping("/addAlbum.do")
	public Map<String, Object> addAlbum(AlbumDto albumDto,HttpSession session){
		logger.info("AlbumDto"+albumDto);
		
		//System.out.println(session.getAttribute(Const.SESSION_USER));
		SportAdminDto trainAdmin = (SportAdminDto) session.getAttribute(Const.SESSION_USER);
        if (null == trainAdmin) {
            return MessageResp.getMessage(false,"当前登录用户超时，请重新登录");
        }
        albumDto.setPublisher(trainAdmin.getAccount());
		/**
		 * base64图片处理
		 */
		/*if(!StringUtils.isEmpty(albumDto.getThumbnail())){
			String thumbnail="";
			try {
				thumbnail = FileUtils.uploadImage(albumDto.getThumbnail());
			} catch (Exception e) {
				e.printStackTrace();
			}
			albumDto.setThumbnail(thumbnail);
		}*/
		//albumDto.setPublisher(getSessionUserName());
        SportTeamDto teamDto = new SportTeamDto();
        teamDto.setId(albumDto.getTeamId());
        SportTeamDto teamsDto =  iTeamService.findTeamBy(teamDto);
        if(teamsDto!=null){
            String gameId = teamsDto.getGameId();
            albumDto.setGameId(gameId);
    		int status = iAlbumService.addAlbum(albumDto);
    		if(status == 0){
    			return MessageResp.getMessage(false,"添加相册失败！");
    		}        	
        }
		return MessageResp.getMessage(true,"添加相册成功！");
	}
	
	/**
	 * 修改球队相册
	 * @author YIn
	 * @time:2015年11月13日 下午5:53:30
	 */
	
	@ResponseBody
	@RequestMapping("/updateAlbum.do")
	public Map<String, Object> updateAlbum(AlbumDto albumDto){
		logger.info("AlbumDto"+albumDto);
		
        SportTeamDto teamDto = new SportTeamDto();
        teamDto.setId(albumDto.getTeamId());
        SportTeamDto teamsDto =  iTeamService.findTeamBy(teamDto);
        String gameId = teamsDto.getGameId();
        albumDto.setGameId(gameId);
		
		int status = iAlbumService.updateAlbum(albumDto);
		if(status == 0){
			return MessageResp.getMessage(false,"更新相册失败！");
		}
		return MessageResp.getMessage(true,"更新相册成功！");
	}
	
	/**
	 * 删除球队相册
	 * @author YIn
	 * @time:2015年11月13日 下午5:53:30
	 */
	@ResponseBody
	@RequestMapping("/delAlbum.do")
	public Map<String, Object> delAlbum(AlbumDto albumDto){
		logger.info("AlbumDto"+albumDto);
		albumDto.setStat(0); //逻辑删除
		int status = iAlbumService.updateAlbum(albumDto);
		if(status == 0){
			return MessageResp.getMessage(false,"删除相册失败！");
		}
		return MessageResp.getMessage(true,"删除相册成功！");
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
	    Integer resObject =(Integer) iAlbumService.delByIds(ids);
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
	@RequestMapping(value="/goAlbumAdd")
	public ModelAndView goAlbumAdd(){
		logger.info("去相册新增页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("/sport/album/album_add");
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
	@RequestMapping(value="/goAlbumEdit")
	public ModelAndView goAlbumEdit(String albumId){
		logger.info("去相册修改页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		/*Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", albumId);	*/	
		AlbumDto albumDto =iAlbumService.getAlbumById(albumId);
		try {
			mv.setViewName("/sport/album/album_edit");
			mv.addObject("msg", "save");
			mv.addObject("albumDto", albumDto);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
}
}