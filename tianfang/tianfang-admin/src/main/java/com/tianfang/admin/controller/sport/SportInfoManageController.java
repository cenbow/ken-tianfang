
package com.tianfang.admin.controller.sport;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.admin.controller.PageData;
import com.tianfang.business.dto.MenuDto;
import com.tianfang.business.dto.SportInformationReqDto;
import com.tianfang.business.dto.SportInformationRespDto;
import com.tianfang.business.service.ISportHomeMenuService;
import com.tianfang.business.service.ISportInfoService;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.freemarker.FreeMarkerUtil;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.PropertiesUtils;
import com.tianfang.common.util.StringUtils;

import freemarker.template.TemplateException;

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
@RequestMapping("/sport/info")
public class SportInfoManageController extends BaseController
{
    protected static final Log logger = LogFactory.getLog(SportHonorManageController.class);
    
    @Autowired
    private ISportInfoService iSportInfoService;
    
	@Autowired
	private ISportHomeMenuService iSportHomeMenuService;
    /**
     * 
     * findPage：咨询列表
     * @param sportInformationReqDto
     * @param page
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月17日 上午10:21:48
     */
    @RequestMapping("/findPage")
    public ModelAndView findPage(SportInformationReqDto sportInformationReqDto,ExtPageQuery page) {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageResult<SportInformationRespDto> result = iSportInfoService.findPage(sportInformationReqDto, page.changeToPageQuery());
        mv.setViewName("/sport/information/list");
        mv.addObject("pageList", result);
        mv.addObject("newsDto", sportInformationReqDto);
        return mv;
    }
    
    /**
     * 
     * openAddView：打开咨询新增页面
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月17日 上午11:19:32
     */
    @RequestMapping("/openAddView")
    public ModelAndView openAddView(){
        logger.info("打开咨询新增页面");
        ModelAndView mv =this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        mv.setViewName("/sport/information/add");
        mv.addObject("msg", "save");
        mv.addObject("pd", pd);
        return mv;
    }
    
    /**
     * 
     * save：新增咨询
     * @param sportInformationReqDto
     * @return
     * @throws Exception 
     * @exception	
     * @author Administrator
     * @date 2015年11月17日 下午2:45:21
     */
    @RequestMapping("/save")
    @ResponseBody
    public Map<String, Object> save(SportInformationReqDto sportInformationReqDto,HttpServletRequest request,HttpServletResponse response) throws Exception {
        SportInformationRespDto sportInformationRespDto = iSportInfoService.save(sportInformationReqDto);
        if (sportInformationRespDto.getMarked() == 3) {
            return MessageResp.getMessage(false, "首页显示最多三条~");
        }
        String freemarkUrl = PropertiesUtils.getProperty("freemarkinfo.url");
        String html = freemarkUrl +File.separator +sportInformationRespDto.getId() + ".html";
        Map<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("nowDate", DateUtils.format(new Date(),DateUtils.YMD_DASH));
        map.put("sjWeek", DateUtils.dayForWeek(new Date()));
        map.put("content", sportInformationRespDto.getContent());
        map.put("title", sportInformationRespDto.getTitle());        
        List<MenuDto> dataList = iSportHomeMenuService.findSportHomeMenuList(null); 
        map.put("s_menu",(Serializable) dataList);
        map.put("partnerList",(Serializable)loadPartnerList());
//        map.put("venusInfoDto", venusInfoDto);
//        map.put("weathInfo", weathInfo);        
        try {
            FreeMarkerUtil.writeTo(request.getSession().getServletContext(),
                map, "/WEB-INF/view/template/", "news_detail.htm", html);
          } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          } catch (TemplateException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }     
            
        String redirectUrl = "/admin/app/"+sportInformationRespDto.getId() + ".html";
        //判断文件是否存在
        File file = new File(html);
        if(!file.exists()){//文件不存在跳转至404 页面
            redirectUrl = "/app/404.html";
        }
//        response.sendRedirect(redirectUrl);
        return MessageResp.getMessage(true, "新增成功~");
    }
    
    /**
     * 
     * openEditView：打开咨询编辑页面
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月17日 下午3:16:44
     */
    @RequestMapping("/openEditView")
    public ModelAndView openEditView() {
        logger.info("打开咨询编辑页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String id = pd.getString("id");
        SportInformationRespDto sportInformationRespDto = iSportInfoService.findById(id);
        mv.setViewName("/sport/information/edit");
        mv.addObject("sportInfoRespDto", sportInformationRespDto);
        mv.addObject("pd", pd);
        return mv;
    }
    
    /**
    * 
    * edit：修改咨询
    * @param sportInformationReqDto
    * @return
     * @throws Exception 
    * @exception   
    * @author Administrator
    * @date 2015年11月17日 下午3:20:45
    */
    @RequestMapping("/edit")
    @ResponseBody
    public Map<String, Object> edit(SportInformationReqDto sportInformationReqDto,HttpServletRequest request) throws Exception {
        if (StringUtils.isBlank(sportInformationReqDto.getMicroPic())) {
            sportInformationReqDto.setMicroPic(null);
        }
        SportInformationRespDto sportInformationRespDto = iSportInfoService.edit(sportInformationReqDto);
        if (null != sportInformationRespDto.getMarked() && sportInformationRespDto.getMarked() == 3) {
            return MessageResp.getMessage(false, "首页显示新闻最多三条~");
        }
        String freemarkUrl = PropertiesUtils.getProperty("freemarkinfo.url");
        String html = freemarkUrl +File.separator +sportInformationRespDto.getId() + ".html";
        Map<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("nowDate", DateUtils.format(new Date(),DateUtils.YMD_DASH));
        map.put("sjWeek", DateUtils.dayForWeek(new Date()));
        map.put("content", sportInformationRespDto.getContent());
        map.put("title", sportInformationRespDto.getTitle());       
        List<MenuDto> dataList = iSportHomeMenuService.findSportHomeMenuList(null); 
        map.put("s_menu",(Serializable) dataList);         
        map.put("partnerList",(Serializable)loadPartnerList());
//        map.put("venusInfoDto", venusInfoDto);
//        map.put("weathInfo", weathInfo);        
        try {
            FreeMarkerUtil.writeTo(request.getSession().getServletContext(),
                map, "/WEB-INF/view/template/", "news_detail.htm", html);
          } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          } catch (TemplateException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }     
            
        String redirectUrl = "/admin/app/"+sportInformationRespDto.getId() + ".html";
        //判断文件是否存在
        File file = new File(html);
        if(!file.exists()){//文件不存在跳转至404 页面
            redirectUrl = "/app/404.html";
        }
//        response.sendRedirect(redirectUrl);
        return MessageResp.getMessage(true, "编辑成功~");
    }
    
	@RequestMapping(value="/openPagePosition")
    public ModelAndView openPagePosition(){
        logger.info("打开分配页面位置页面");
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageData pd = new PageData();
        pd = this.getPageData();
        String id = pd.getString("id");
        SportInformationRespDto sportInformationRespDto = iSportInfoService.findById(id);
        try {
            mv.setViewName("/sport/information/page_position");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
            mv.addObject("datas", sportInformationRespDto);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }                       
        return mv;
    }
    
	@RequestMapping(value="/pageRanking.do")
	@ResponseBody
	public Map<String, Object> pageRanking(String id,Integer positionId){
	    if(StringUtils.isBlank(id)){
            return MessageResp.getMessage(false, "请求参数不能为空~") ;
        }
	    Integer result = iSportInfoService.pageRanking(id, positionId);
	    if (result == 1) {
	        return MessageResp.getMessage(true, "操作成功~");
	    }
	    return MessageResp.getMessage(false, "操作失败~") ;
	}
	
    /**
     *     
     * delete：删除咨询
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
        iSportInfoService.delete(ids);
        return MessageResp.getMessage(true, "删除成功！");
    }
    
    /**
     * 
     * release：修改发布状态
     * @param id
     * @param releaseStat
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月18日 下午3:20:38
     */    
    @RequestMapping("/release")
    @ResponseBody
    public Map<String, Object> release(String id,Integer releaseStat) {
        if(id == null || "".equals(id) || null == releaseStat){
            return MessageResp.getMessage(false, "请求参数不能为空~") ;
        }
        iSportInfoService.release(id, releaseStat);
        return MessageResp.getMessage(true, "修改成功！");
    }
}

