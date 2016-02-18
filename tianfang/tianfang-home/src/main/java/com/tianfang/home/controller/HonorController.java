package com.tianfang.home.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.dto.SportAdminDto;
import com.tianfang.business.dto.SportHonorReqDto;
import com.tianfang.business.dto.SportHonorRespDto;
import com.tianfang.business.service.ISportHonorService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.constants.SessionConstants;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.user.dto.LoginUserDto;

/**
 * 荣誉(web)
 * @author wk.s
 *
 */
@Controller
@RequestMapping("honor")
public class HonorController extends BaseController{

	@Autowired
    private ISportHonorService iSportHonorService;
	private Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * 查询荣誉
	 * @param sportHonorReqDto
	 * @param page
	 * @return
	 */
	@RequestMapping("/findPage")
    public ModelAndView findPage(SportHonorReqDto sportHonorReqDto, ExtPageQuery page) {
        ModelAndView mv = getModelAndView(sportHonorReqDto.getTeamId());
        PageResult<SportHonorRespDto> results = iSportHonorService.findPage(sportHonorReqDto, page.changeToPageQuery());
        mv.setViewName("/team/honor/honor-manage");
        mv.addObject("pageList", results);
        return mv;
    }
	
	/**
	 * 跳转到新增荣誉页面
	 * @param teamId
	 * @param currPage
	 * @return
	 */
    @RequestMapping("toAddHonorView")
    public ModelAndView toAddView(String teamId, Integer page){
        ModelAndView mv = getModelAndView(teamId);
        mv.addObject("teamId", teamId);
        mv.addObject("page", page);
        mv.setViewName("/team/honor/honor_add");
        return mv;
    }
    
    /**
     * 新增球队荣誉
     * @param sdto
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("addHonor")
    public Response<String> addHonor(SportHonorReqDto sdto, HttpSession session){
    	
    	Response<String> resp = new Response<String>();
    	LoginUserDto logDto= (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
        if (logDto == null) {
        	resp.setStatus(DataStatus.HTTP_FAILE);
        	resp.setMessage("当前登录用户超时，请重新登录");
		} else {
			sdto.setPublishPeople(logDto.getId());
			sdto.setPublisherType(1); // 前台用户
			Integer result = iSportHonorService.save(sdto);
			if (result == 1) {
				resp.setStatus(DataStatus.HTTP_SUCCESS);
				resp.setMessage("新增成功~");
			} else {
				resp.setStatus(DataStatus.HTTP_FAILE);
				resp.setMessage("新增失败~");
			}
		}
    	return resp;
    }
    
    /**
     * 跳转到编辑荣誉页面
     * @param honorId
     * @return
     */
    @RequestMapping("toUpdateHView")
    public ModelAndView toEditView(String honorId, Integer page){
        ModelAndView mv = getModelAndView();
        SportHonorRespDto dto = iSportHonorService.findById(honorId);
        mv.addObject("dto", dto);
        mv.addObject("page", page);
        mv.setViewName("/team/honor/honor_edit");
        return mv;
    }
    
    /**
     * 更新荣誉信息
     * @param sdto
     * @return
     */
    @ResponseBody
    @RequestMapping("updateHonor")
    public Response<String> editHonor(SportHonorReqDto sdto, HttpSession session){
    	Response<String> resp = new Response<String>();
    	
    	LoginUserDto logDto= (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
        if (logDto == null) {
        	resp.setStatus(DataStatus.HTTP_FAILE);
        	resp.setMessage("当前登录用户超时，请重新登录");
		} else {
			sdto.setPublishPeople(logDto.getId());
			Integer c = iSportHonorService.update(sdto);
	    	if(c == 1){
	    		resp.setStatus(DataStatus.HTTP_SUCCESS);
				resp.setMessage("更新成功~");
	    	} else {
	    		resp.setStatus(DataStatus.HTTP_FAILE);
				resp.setMessage("更新失败~");
	    	}
		}
    	return resp;
    }
    
    /**
     * 删除球队荣誉记录
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteHonor")
    public Response<String> deleteHonor(String ids){
    	
    	Response<String> resp = new Response<String>();
    	 if(ids == null || ids.equals("")){
    		 resp.setStatus(DataStatus.HTTP_FAILE);
    		 resp.setMessage("请求参数不能为空~");
         }else{
        	 iSportHonorService.delete(ids);
        	 resp.setStatus(DataStatus.HTTP_SUCCESS);
    		 resp.setMessage("删除成功~");
         }
    	return resp;
    }
    
    
}
