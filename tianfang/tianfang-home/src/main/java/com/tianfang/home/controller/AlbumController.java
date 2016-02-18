package com.tianfang.home.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;

/**		
 * <p>Title: AlbumController c</p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年11月21日 下午4:40:05	
 * @version 1.0
 * <p>修改人：Administrator Y</p>
 * <p>修改时间：2015年11月21日 下午4:40:05</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/album")
public class AlbumController extends BaseController{
    @Autowired
    private IAlbumService iAlbumService;
    
    @Autowired
    private IAlbumPicService iAlbumPicService;
    
    
    @RequestMapping(value="/list")
    public ModelAndView list() {
    	ModelAndView mv = getModelAndView();
    	mv.setViewName("/team/youth-pic");
        return mv;
    }
    
    @ResponseBody
    @RequestMapping(value="/queryAlbum")
    public PageResult<AlbumDto> albumPage(AlbumDto dto, ExtPageQuery page){
        PageResult<AlbumDto> result = iAlbumService.findPage(dto, page.changeToPageQuery());
        return result;
    }
    
    @RequestMapping(value="/getPicList")
    @ResponseBody
    public PageResult<AlbumPictureDto> findByAlbumId(String id) {
        List<AlbumPictureDto> result = iAlbumService.findByAlbumId(id);
        PageQuery page = new PageQuery();
        page.setTotal(result.size());
        return new PageResult<AlbumPictureDto>(page, result);
    }
    
    @RequestMapping(value="/picList/{albumId}")
    public ModelAndView piclist(@PathVariable String albumId,HttpServletRequest req) {
    	AlbumDto albumDto = iAlbumService.getAlbumById(albumId);
        List<AlbumPictureDto> result = iAlbumService.findByAlbumId(albumId);
        ModelAndView mv = getModelAndView(albumDto.getTeamId());
        mv.addObject("data", result);
        mv.setViewName("/team/youth-pics");
        return mv;
    }
    
    @ResponseBody
    @RequestMapping(value="/add")
    public Response<String> add(AlbumDto albumDto){
    	Response<String> data = new Response<String>();
    	if(iAlbumService.addAlbum(albumDto)>0){
    		data.setMessage("相册保存成功");	
    	}else{
    		data.setStatus(DataStatus.HTTP_FAILE);
    		data.setMessage("相册保存出错");
    	}
    	return data;
    }
    
    
    @ResponseBody
    @RequestMapping(value="/deletes")
    public Response<String> add(String albumId){
    	Response<String> data = new Response<String>();
    	
    	//修改相册状态 
    	AlbumDto albumDto = new AlbumDto();
    	albumDto.setStat(DataStatus.DISABLED);
    	albumDto.setId(albumId);
    	iAlbumService.updateAlbum(albumDto);

    	//修改相片状态
    	iAlbumPicService.updateAlbumPicList(albumId);
    	
//    	iAlbumService.delAlbum(albumId);
//    	iAlbumPicService.delAlbumPicByalbumId(albumId);
    	
    	return data;
    }    
}

