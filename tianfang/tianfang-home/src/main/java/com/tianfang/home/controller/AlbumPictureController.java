package com.tianfang.home.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.business.dto.AlbumDto;
import com.tianfang.business.dto.AlbumPictureDto;
import com.tianfang.business.service.IAlbumPicService;
import com.tianfang.business.service.IAlbumService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.home.util.SessionUtil;
import com.tianfang.user.dto.LoginUserDto;
import com.tianfang.user.dto.SportUserReqDto;
import com.tianfang.user.service.IUserService;

/**		
 * <p>Title: AlbumController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年11月22日 下午4:40:05	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年11月21日 下午4:40:05</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/pic")
public class AlbumPictureController extends BaseController{

	private final static int PAGESIZE = 10;

	@Autowired
	private IAlbumPicService iAlbumPicService;

	@Autowired
	private IAlbumService iAlbumService;
	
	@Autowired
	private IUserService iUserService;
	
	/**后台相片列表显示
	 * @author YIn
	 * @time:2015年11月30日 下午5:01:56
	 * @param albumId
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/picList/{albumId}")
	public ModelAndView piclist(@PathVariable String albumId,PageQuery page) {
		AlbumDto albumDto = iAlbumService.getAlbumById(albumId);
		ModelAndView mv = getModelAndView(albumDto.getTeamId());
		AlbumPictureDto albumPictureDto = new AlbumPictureDto();
		albumPictureDto.setAlbumId(albumId);
		page.setPageSize(PAGESIZE);
		PageResult<AlbumPictureDto> pageList = iAlbumPicService.findTeamAlbumPicByPage(albumPictureDto, page);//iAlbumService.findPage(dto, page);
		mv.addObject("pageList",pageList);
		mv.addObject("albumDto",albumDto);
		mv.setViewName("/team/youth-picList");
		return mv;
	}

	/**
	 * 
	 * 此方法描述的是：上传相片至对应的相册
	 * @author: cwftalus@163.com
	 * @version: 2015年11月30日 下午3:34:38
	 */
	@ResponseBody
	@RequestMapping(value="/add")
	public Response<String> add(AlbumPictureDto albumPicDto){
		Response<String> data = new Response<String>();

		iAlbumPicService.insertPictures(albumPicDto);

		data.setMessage("数据处理成功");	   	

		return data;
	}
	/**
	 * 
	 * 此方法描述的是：设置相册封面
	 * @author: cwftalus@163.com
	 * @version: 2015年11月30日 下午3:37:36
	 */
	@ResponseBody
	@RequestMapping(value="/setCover")
	public Response<String> setCover(String picUrl,String albumId){
		Response<String> data = new Response<String>();
		AlbumDto albumDto = new AlbumDto();
		albumDto.setId(albumId);
		albumDto.setThumbnail(picUrl);
		if(iAlbumService.updateAlbum(albumDto)>0){
			data.setMessage("设置封面成功");	
		}else{
			data.setStatus(DataStatus.HTTP_FAILE);
			data.setMessage("设置封面失败");
		}
		return data;
	}

	/**
	 * 
	 * 此方法描述的是：设置相片标题
	 * @author: cwftalus@163.com
	 * @version: 2015年11月30日 下午4:06:37
	 */
	@ResponseBody
	@RequestMapping(value="/setTitle")
	public Response<String> setTitle(String picTitle,String picId){
		Response<String> data = new Response<String>();
		AlbumPictureDto pictDto = new AlbumPictureDto();
		pictDto.setId(picId);
		pictDto.setTitle(picTitle);
		if(iAlbumPicService.updateAlbumPic(pictDto)>0){
			data.setMessage("设置标题成功");	
		}else{
			data.setStatus(DataStatus.HTTP_FAILE);
			data.setMessage("设置标题失败");
		}
		return data;
	}
	
	/**此方法描述的是：设置相片描述
	 * @author YIn
	 * @time:2015年12月1日 上午11:14:50
	 * @param picDescribe
	 * @param picId
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value="/setDescribe")
	public Response<String> setDescribe(String picDescribe,String picId){
		Response<String> data = new Response<String>();
		AlbumPictureDto pictDto = new AlbumPictureDto();
		pictDto.setId(picId);
		pictDto.setDescribed(picDescribe);;
		if(iAlbumPicService.updateAlbumPic(pictDto)>0){
			data.setMessage("设置相片描述成功");	
		}else{
			data.setStatus(DataStatus.HTTP_FAILE);
			data.setMessage("设置相片描述失败");
		}
		return data;
	}

	/**
	 * 
	 * 此方法描述的是：图片删除功能
	 * @author: cwftalus@163.com
	 * @version: 2015年11月30日 下午4:15:42
	 */
	@ResponseBody
	@RequestMapping(value="/setDelete")
	public Response<String> setDelete(String picId){
		Response<String> data = new Response<String>();
		if(iAlbumPicService.delAlbumPic(picId)>0){
			data.setMessage("删除图片成功");	
		}else{
			data.setStatus(DataStatus.HTTP_FAILE);
			data.setMessage("删除图片失败");
		}
		return data;
	}

	/**前台个人中心相片列表显示
	 * @author YIn
	 * @time:2015年11月30日 下午5:01:56
	 * @param albumId
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/myPicList")
	public ModelAndView myPicList(PageQuery page,HttpSession session) {
		ModelAndView mv = getModelAndView();
		// 获取当前登录用户Id
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		// 只允许登录用户进行操作
		if (loginUserDto == null) {
			mv.addObject("status", "500");
			mv.addObject("message", "用户没有登录!");
			return new ModelAndView("redirect:/index.htm");
//			return mv;
		}
		String userId =loginUserDto.getId();
		//String userId = "17b5c436-1ab7-437e-8376-d98075b50a22";
		AlbumPictureDto dto = new AlbumPictureDto();
		dto.setUserId(userId);
		page.setPageSize(8);  
		PageResult<AlbumPictureDto> albumPictureDto = iAlbumPicService.findTeamAlbumPicByPage(dto,page);
		SportUserReqDto userInfo= iUserService.selectUserAccountByUserId(loginUserDto.getId());
		if(albumPictureDto != null){
			mv.addObject("userInfo", userInfo);
			mv.addObject("status", "200");
			mv.addObject("message", "查询成功!");
			mv.addObject("pageList", albumPictureDto);
			mv.setViewName("/usercenter_mypic");
		}else{
			mv.addObject("status", "500");
			mv.addObject("message", "没有查到图片!");
		}
		return mv;
	}
}

