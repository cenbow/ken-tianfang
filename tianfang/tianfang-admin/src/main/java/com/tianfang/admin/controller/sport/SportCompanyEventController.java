/**
 * 
 */
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
import com.tianfang.business.dto.SportHonorRespDto;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.StringUtils;
import com.tianfang.company.dto.SportCompanyEventDto;
import com.tianfang.company.service.ISportCompanyEventService;

/**		
 * <p>Title: SportCompanyEventController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年12月4日 上午11:04:39	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年12月4日 上午11:04:39</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping(value ="/sport/company/event")
public class SportCompanyEventController extends BaseController
{ 

    protected static final Log logger = LogFactory.getLog(SportHonorManageController.class);
    
    @Autowired
    private ISportCompanyEventService iSportCompanyEventService;
    
    /**
     * 
     * findPage：大事记列表页
     * @param sportCompanyEventDto
     * @param page
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年12月4日 上午11:09:20
     */
    @RequestMapping("/findPage")
    public ModelAndView findPage(SportCompanyEventDto sportCompanyEventDto, ExtPageQuery page){
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageResult<SportCompanyEventDto> result = iSportCompanyEventService.findPage(sportCompanyEventDto, page.changeToPageQuery());
        mv.setViewName("/sport/event/list");
        mv.addObject("pageList", result);
        mv.addObject("newsDto", sportCompanyEventDto);
        return mv;
    }
    
    /**
     * 
     * openAddView：打开新增事记页面
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年11月16日 上午9:21:18
     */
    @RequestMapping("/openAddView")
    public ModelAndView openAddView(){
        logger.info("打开新增事记页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        mv.setViewName("/sport/event/add");
        mv.addObject("msg", "save");
        mv.addObject("pd", pd);
        return mv;
    }
    
    /**
     * 
     * save：新增事记
     * @param sportCompanyEventDto
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年12月4日 上午11:31:31
     */
    @RequestMapping("/save")
    @ResponseBody
    public Map<String, Object> save(SportCompanyEventDto sportCompanyEventDto) {
        Integer result = (Integer) iSportCompanyEventService.save(sportCompanyEventDto);
        if (result == 1) {
            return MessageResp.getMessage(true,"新增成功");
        }
        return MessageResp.getMessage(false,"新增失败");
    }
    
    
    /**
     * 
     * openEditView：打开编辑事记页面
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年11月16日 下午1:00:05
     */
    @RequestMapping("/openEditView")
    public ModelAndView openEditView () {
        logger.info("打开编辑事记页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String id = pd.getString("id");
        SportCompanyEventDto sportCompanyEventDto = iSportCompanyEventService.findById(id);
        mv.setViewName("/sport/event/edit");
        mv.addObject("data", sportCompanyEventDto);
        mv.addObject("pd", pd);
        return mv;
    }
    
    /**
     * 
     * edit：编辑事记
     * @param sportCompanyEventDto
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年12月4日 上午11:40:06
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map<String, Object> edit(SportCompanyEventDto sportCompanyEventDto){
        if (StringUtils.isBlank(sportCompanyEventDto.getThumbnail())) {
            sportCompanyEventDto.setThumbnail(null);
        }
        Integer result = (Integer) iSportCompanyEventService.edit(sportCompanyEventDto);
        if (result == 1) {
            return MessageResp.getMessage(true,"修改成功！");
        }
        return MessageResp.getMessage(false, "修改失败！");
    }
    
    /**
     * 
     * delete：删除事记记录
     * @param ids
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年12月4日 上午11:44:44
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        if(ids == null || "".equals(ids)){
            return MessageResp.getMessage(false, "请求参数不能为空~") ;
        }
        iSportCompanyEventService.delete(ids);
        return MessageResp.getMessage(true, "删除成功！");
    }
}

