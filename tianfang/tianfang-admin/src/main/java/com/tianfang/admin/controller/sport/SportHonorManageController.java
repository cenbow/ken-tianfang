/**
 * 
 */
package com.tianfang.admin.controller.sport;

import java.util.HashMap;
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
import com.tianfang.admin.controller.train.TrainMSAdminLoginController;
import com.tianfang.admin.dto.SportAdminDto;
import com.tianfang.business.dao.SportHonorDao;
import com.tianfang.business.dto.SportHonorReqDto;
import com.tianfang.business.dto.SportHonorRespDto;
import com.tianfang.business.service.ISportHonorService;
import com.tianfang.business.service.ITeamService;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;

/**		
 * <p>Title: SportHonorManageController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年11月13日 下午6:03:11	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年11月13日 下午6:03:11</p>
 * <p>修改备注：</p>
 */

@Controller
@RequestMapping(value ="/sport/honor")
public class SportHonorManageController extends BaseController
{
    protected static final Log logger = LogFactory.getLog(SportHonorManageController.class);
    
    @Autowired
    private ISportHonorService iSportHonorService;
    
    @Autowired
    private ITeamService iteamSevice;
    /**
     * 
     * findPage：球队荣誉列表
     * @param sportHonorReqDto
     * @param page
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月14日 下午4:33:41
     */
    @RequestMapping("/findPage")
    public ModelAndView findPage(SportHonorReqDto sportHonorReqDto, ExtPageQuery page) {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageResult<SportHonorRespDto> results = iSportHonorService.findPage(sportHonorReqDto, page.changeToPageQuery());
        mv.setViewName("/sport/honor/list");
        mv.addObject("pageList", results);
        mv.addObject("newsDto", sportHonorReqDto);
        return mv;
    }
    
    /**
     * 
     * openAddView：打开新增荣誉页面
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月16日 上午9:21:18
     */
    @RequestMapping("/openAddView")
    public ModelAndView openAddView(){
        logger.info("打开新增荣誉页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        mv.setViewName("/sport/honor/add");
        mv.addObject("page", iteamSevice.getAllTeam());
        mv.addObject("msg", "save");
        mv.addObject("pd", pd);
        return mv;
    }
    
    /**
     * 
     * save：新增球队荣誉
     * @param sportHonorReqDto
     * @param session
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月16日 上午9:52:29
     */
    @RequestMapping("/save")
    @ResponseBody
    public Map<String, Object> save(SportHonorReqDto sportHonorReqDto,HttpSession session) {
        SportAdminDto trainAdmin = (SportAdminDto) session.getAttribute(Const.SESSION_USER);
        if (null == trainAdmin) {
            return MessageResp.getMessage(false,"当前登录用户超时，请重新登录");
        }
        sportHonorReqDto.setPublishPeople(trainAdmin.getId());
        Integer result = iSportHonorService.save(sportHonorReqDto);
        if (result == 1) {
            return MessageResp.getMessage(true,"新增成功");
        }
        return MessageResp.getMessage(false,"新增失败");
    }
    
    /**
     * 
     * findById：根据id查询
     * @param id
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月16日 上午11:46:29
     */
    @RequestMapping("/findById")
    @ResponseBody
    public Map<String, Object> findById(String id) {
        Map<String, Object> result = new HashMap<String, Object>();
        SportHonorRespDto sportHonorRespDto = iSportHonorService.findById(id);
        result.put("success", true);
        result.put("data", sportHonorRespDto);
        return result;
    }
    
    /**
     * 
     * openEditView：打开编辑球队荣誉页面
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月16日 下午1:00:05
     */
    @RequestMapping("/openEditView")
    public ModelAndView openEditView () {
        logger.info("打开编辑球队荣誉页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String id = pd.getString("id");
        SportHonorRespDto sportHonorRespDto = iSportHonorService.findById(id);
        mv.setViewName("/sport/honor/edit");
        mv.addObject("sportHonorRespDto", sportHonorRespDto);
        mv.addObject("page", iteamSevice.getAllTeam());
        mv.addObject("pd", pd);
        return mv;
    }
    
    /**
     * 
     * update：更新修改
     * @param sportHonorReqDto
     * @param session
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月16日 下午1:30:32
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map<String, Object> edit(SportHonorReqDto sportHonorReqDto,HttpSession session) {
        SportAdminDto trainAdmin = (SportAdminDto) session.getAttribute(Const.SESSION_USER);
        if (null == trainAdmin) {
            return MessageResp.getMessage(false,"当前登录用户超时，请重新登录");
        }
        sportHonorReqDto.setPublishPeople(trainAdmin.getId());
        Integer result = iSportHonorService.update(sportHonorReqDto);
        if (result == 1) {
            return MessageResp.getMessage(true,"修改成功！");
        }
        return MessageResp.getMessage(false, "修改失败！");
    }
    
    /**
     * 
     * delete：删除荣誉记录
     * @param ids
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年11月16日 下午2:33:04
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        if(ids == null || "".equals(ids)){
            return MessageResp.getMessage(false, "请求参数不能为空~") ;
        }
        iSportHonorService.delete(ids);
        return MessageResp.getMessage(true, "删除成功！");
    }
    
    /**
     * 
     * examine：审核荣誉
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月16日 下午3:28:02
     */
    @RequestMapping("/examine")
    @ResponseBody
    public Map<String, Object> examine(String id,Integer honorStatus) {
        if (id == null || "".equals(id) || null == honorStatus) {
            return MessageResp.getMessage(false, "请求参数不能为空~") ;
        }
        iSportHonorService.examine(id, honorStatus);
        return MessageResp.getMessage(true, "操作成功！");
    }
}

