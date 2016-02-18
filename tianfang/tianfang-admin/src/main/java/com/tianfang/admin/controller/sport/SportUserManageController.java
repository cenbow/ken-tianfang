
package com.tianfang.admin.controller.sport;

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
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.StringUtils;
import com.tianfang.user.dto.SportUserReqDto;
import com.tianfang.user.dto.SportUserRespDto;
import com.tianfang.user.service.ISportUserService;

/**		
 * <p>Title: SportInfoManageController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年11月16日 下午4:57:05	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年11月16日 下午4:57:05</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/sport/user")
public class SportUserManageController extends BaseController
{
    protected static final Log logger = LogFactory.getLog(SportUserManageController.class);
    
    @Autowired
    private ISportUserService iSportUserService;
    
    /**
     * 
     * findPage：用户列表
     * @param sportUserReqDto
     * @param page
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月18日 下午5:36:14
     */
    @RequestMapping("/findPage")
    public ModelAndView findPage(SportUserReqDto sportUserReqDto, ExtPageQuery page) {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageResult<SportUserRespDto> result = iSportUserService.findPage(sportUserReqDto, page.changeToPageQuery());
        mv.setViewName("/sport/user/list");
        mv.addObject("pageList", result);
        mv.addObject("newsDto", sportUserReqDto);
        return mv;
    }
    
    
    /**
     * 
     * release：修改用户可见状态
     * @param id
     * @param visibleStat
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月19日 上午9:40:11
     */
    @RequestMapping("/visible")
    @ResponseBody
    public Map<String, Object> release(String id,Integer visibleStat) {
        if(id == null || "".equals(id) || null == visibleStat){
            return MessageResp.getMessage(false, "请求参数不能为空~") ;
        }
        iSportUserService.visible(id, visibleStat);
        return MessageResp.getMessage(true, "修改成功！");
    }
    
    @RequestMapping("/setUserOp")
    @ResponseBody
    public Map<String, Object> setUserOp(String id,Integer trainerLevel) {
        if(id == null || "".equals(id) || null == trainerLevel){
            return MessageResp.getMessage(false, "请求参数不能为空~") ;
        }
        iSportUserService.setUserOp(id, trainerLevel);
        return MessageResp.getMessage(true, "修改成功！");
    }
    
    
    /**
     * 
     * audit：修改审核状态
     * @param id
     * @param auditStat
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年12月8日 下午4:44:06
     */
    @RequestMapping("/audit")
    @ResponseBody
    public Map<String, Object> audit(String id,Integer auditStat) {
        if(id == null || "".equals(id) || null == auditStat){
            return MessageResp.getMessage(false, "请求参数不能为空~") ;
        }
        iSportUserService.audit(id, auditStat);
        return MessageResp.getMessage(true, "修改成功！");
    }
    
    /**
     * 修改主讲人状态
     * @author YIn
     * @time:2015年12月25日 下午3:31:40
     * @param id
     * @param auditStat
     * @return
     */
    @RequestMapping("/changeLecturer")
    @ResponseBody
    public Map<String, Object> changeLecturer(String id,Integer lecturerStat) {
        if(id == null || "".equals(id) || null == lecturerStat){
            return MessageResp.getMessage(false, "请求参数不能为空~") ;
        }
        int flag = iSportUserService.changeLecturer(id, lecturerStat);
        if(flag > 0){
        	return MessageResp.getMessage(true, "修改主讲人状态成功！");
        }else{
        	return MessageResp.getMessage(true, "修改主讲人状态失败！");
        }
        
    }
    
    /**
     * 
     * openAddView：打开用户新增页面
     * @return
     * @exception  
     * @author Administrator
     * @date 2015年11月30日 下午4:42:15
     */
    @RequestMapping("/openAddView")
    public ModelAndView openAddView(){
        logger.info("打开用户新增页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        mv.setViewName("/sport/user/add");
        mv.addObject("pd", pd);
        mv.addObject("msg", "save");
        return mv;
    }
    
    /**
     * 
     * save：新增用户
     * @param sportUserReqDto
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月30日 下午4:43:02
     */
    @RequestMapping("/save")
    @ResponseBody
    public Map<String, Object> save(SportUserReqDto sportUserReqDto) {
        if (StringUtils.isBlank(sportUserReqDto.getMicroPic())) {
            sportUserReqDto.setMicroPic(null);
        }
        if (StringUtils.isBlank(sportUserReqDto.getPapersPic())) {
            sportUserReqDto.setPapersPic(null);             
        }
        if (StringUtils.isBlank(sportUserReqDto.getPic())) {
            sportUserReqDto.setPic(null);             
        }
        int result = (int)iSportUserService.save(sportUserReqDto);
        if (result == 1) {
            return MessageResp.getMessage(true,"新增成功");
        }
        return MessageResp.getMessage(false,"新增失败");
    }
    
      /**
      * 
      * openEditView：打开用户编辑页面
      * @return
      * @exception 
      * @author Administrator
      * @date 2015年11月17日 下午3:16:44
      */
     @RequestMapping("/openEditView")
     public ModelAndView openEditView() { 
         logger.info("打开用户编辑页面");
         ModelAndView mv = this.getModelAndView();
         PageData pd = new PageData();
         pd = this.getPageData();
         String id = pd.getString("id");
         SportUserRespDto sportUserRespDto = iSportUserService.getSportUser(id);
         mv.setViewName("/sport/user/edit");
         mv.addObject("sportUserRespDto", sportUserRespDto);
         mv.addObject("pd", pd);
         return mv;
     }    
     
     /**
      * 
      * edit：编辑用户
      * @param sportUserReqDto
      * @return
      * @exception	
      * @author Administrator
      * @date 2015年11月30日 下午4:42:46
      */
     @RequestMapping("/edit")
     @ResponseBody
     public Map<String, Object> edit(SportUserReqDto sportUserReqDto) {
         if (StringUtils.isBlank(sportUserReqDto.getMicroPic())) {
             sportUserReqDto.setMicroPic(null);
         }
         if (StringUtils.isBlank(sportUserReqDto.getPapersPic())) {
             sportUserReqDto.setPapersPic(null);             
         }
         if (StringUtils.isBlank(sportUserReqDto.getPic())) {
             sportUserReqDto.setPic(null);             
         }
         int result = (int)iSportUserService.edit(sportUserReqDto);
         if (result == 1) {
             return MessageResp.getMessage(true,"编辑成功");
         }
         return MessageResp.getMessage(false,"编辑失败");
     }
    
      /**
      *     
      * delete：删除用户
      * @param ids
      * @return
      * @exception 
      * @author Administrator
      * @date 2015年11月17日 下午4:22:39
      */
     @RequestMapping("/delete")
     @ResponseBody
     public Map<String, Object> delete(String ids) {
         if(ids == null || "".equals(ids)){
             return MessageResp.getMessage(false, "请求参数不能为空~") ;
         }
         iSportUserService.delete(ids);
         return MessageResp.getMessage(true, "删除成功！");
     }
}

