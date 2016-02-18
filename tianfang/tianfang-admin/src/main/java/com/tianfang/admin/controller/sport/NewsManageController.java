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
import com.tianfang.business.service.ISportHomeMenuService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.freemarker.FreeMarkerUtil;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.PropertiesUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.news.dto.SportNewsInfoDto;
import com.tianfang.news.service.ISportNewsInfoService;

import freemarker.template.TemplateException;

/**
 * 新闻管理
 * @author xiang_wang
 *
 * 2015年11月14日下午12:20:50
 */
@Controller
@RequestMapping(value = "/sport/newsInfo")
public class NewsManageController extends BaseController{

	protected static final Log logger = LogFactory.getLog(NewsManageController.class);
	
	@Autowired
	private ISportNewsInfoService newsInfoService;
	
	@Autowired
	private ISportHomeMenuService iSportHomeMenuService;
	/**
	 * 查询全部
	 * @param newsDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findPage.do")
	public ModelAndView findAll(SportNewsInfoDto newsDto,ExtPageQuery page){
		logger.info("SportNewsInfoDto newsDto : " + newsDto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stat", DataStatus.ENABLED);
		isNoNews(newsDto,params);
		PageResult<SportNewsInfoDto> result= newsInfoService.findNewsByParams(newsDto, page.changeToPageQuery().getCurrPage(), page.changeToPageQuery().getPageSize());
		mv.addObject("pageList", result);
		mv.addObject("newsDto",newsDto);		
		mv.setViewName("/sport/news/list");
		return mv;
	}
	/**
	 * 检测查询条件
	 * @param newsDto
	 * @param params
	 */
	public void isNoNews(SportNewsInfoDto newsDto,Map<String, Object> params){
		if(newsDto.getTitle()!=null&&!"".endsWith(newsDto.getTitle())){
			params.put("title", newsDto.getTitle());
		}
		if(newsDto.getMarked()!=null){
			params.put("marked", newsDto.getMarked());
		}
		if(newsDto.getStartTimeStr()!=null && !"".equals(newsDto.getStartTimeStr())){
			params.put("createTimeS", newsDto.getStartTimeStr());
		}
		if(newsDto.getEndTimeStr()!=null && !"".equals(newsDto.getEndTimeStr())){
			params.put("createTimeE", newsDto.getEndTimeStr());
		}
	}
	/**
	 * 新增
	 * @param addressDto
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/saveNew.do")
	public  Map<String, Object> insert(SportNewsInfoDto newsDto,HttpServletRequest request,HttpServletResponse response) throws Exception{
//		//上传图片
//		if(myfile.getOriginalFilename()!=null || !"".equals(myfile.getOriginalFilename())){
//			String fileName = FileUtils.uploadImage(myfile);
//			newsDto.setMicroPic("/"+fileName);
//		}else{
//			newsDto.setMicroPic(null);
//		}
		//上传新闻视频
//		if(myfileVideo.getOriginalFilename()!=null || !"".equals(myfileVideo.getOriginalFilename())){
//			String fileVideo = FileUtils.uploadImage(myfileVideo);
//			newsDto.setNewVideo("/"+fileVideo);
//		}else{
//			newsDto.setNewVideo(null);
//		}
		/**
		 * base64图片处理
		 */
//		if(!StringUtils.isEmpty(newsDto.getMicroPic())){
//			String microPic="";
//			try {
//				microPic = FileUtils.uploadImage(newsDto.getMicroPic());
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			newsDto.setMicroPic(microPic);
//		}
		
	    SportNewsInfoDto sportNewsInfoDto = newsInfoService.saveNews(newsDto);
		if(sportNewsInfoDto.getReleaseStat() == 2){
			return MessageResp.getMessage(false,"对不起,首页显示图片超过三条~");
		}
		String freemarkUrl = PropertiesUtils.getProperty("freemark.url");
        String html = freemarkUrl +File.separator +sportNewsInfoDto.getId() + ".html";
        Map<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("nowDate", DateUtils.format(new Date(),DateUtils.YMD_DASH));
        map.put("sjWeek", DateUtils.dayForWeek(new Date()));
        map.put("content", sportNewsInfoDto.getContent());
        map.put("title", sportNewsInfoDto.getTitle());      
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
            
        String redirectUrl = "/admin/app/"+sportNewsInfoDto.getId() + ".html";
        //判断文件是否存在
        File file = new File(html);
        if(!file.exists()){//文件不存在跳转至404 页面
            redirectUrl = "/app/404.html";
        }
		return MessageResp.getMessage(true,"添加成功~");
	}
	/**
	 * 根据id查询
	 * @param newsDto
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findNewsById.do")
	public Map<String, Object> findNewsById(SportNewsInfoDto newsDto){
		logger.info("SportNewsInfoDto newsDto : " + newsDto);
		Map<String, Object> result = new HashMap<String, Object>();
		SportNewsInfoDto  traNews = newsInfoService.getNewsById(newsDto.getId());
		if(traNews.getMarked()==1){
			traNews.setMarkedStr("选中");
		}else if(traNews.getMarked()==0){
			traNews.setMarkedStr("取消");
		}
		result.put("success", true);
        result.put("data", traNews);
		return result; 
	}
	
	/**
	 * 
	 * openPagePosition：打开分配页面位置页面
	 * @return
	 * @exception	
	 * @author Administrator
	 * @date 2015年11月9日 下午5:26:28
	 */
	@RequestMapping(value="/openPagePosition")
    public ModelAndView openPagePosition(){
        logger.info("打开分配页面位置页面");
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageData pd = new PageData();
        pd = this.getPageData();
        String id = pd.getString("id");
        SportNewsInfoDto trainNewsInfo =  newsInfoService.getNewsById(id);//根据ID读取
        try {
            mv.setViewName("/sport/news/page_position");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
            mv.addObject("datas", trainNewsInfo);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }                       
        return mv;
    }
	
	/**
	 * 
	 * pageRanking：更改前端页面显示位置
	 * @param id
	 * @param positionId
	 * @return
	 * @exception	
	 * @author Administrator
	 * @date 2015年11月9日 下午4:54:02
	 */
	@RequestMapping(value="/pageRanking.do")
	@ResponseBody
	public Map<String, Object> pageRanking(String id,Integer positionId){
	    if(StringUtils.isBlank(id)){
            return MessageResp.getMessage(false, "请求参数不能为空~") ;
        }
	    Integer result = newsInfoService.pageRanking(id, positionId);
	    if (result == 1) {
	        return MessageResp.getMessage(true, "操作成功~");
	    }
	    return MessageResp.getMessage(false, "操作失败~") ;
	}
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delecte.do")
	public Map<String, Object> updateOrDel(String id){
		if(id == null || "".equals(id)){
			return MessageResp.getMessage(false, "请求参数不能为空~") ;
		}
		newsInfoService.deleteNews(id);
		return MessageResp.getMessage(true, "删除成功~");
	}
	
	/**
	 * 
	 * release：操作发布
	 * @param id
	 * @param stat
	 * @return
	 * @exception	
	 * @author Administrator
	 * @date 2015年11月28日 下午3:28:50
	 */
	@RequestMapping(value = "/release.do")
	@ResponseBody
	public Map<String, Object> release(String id,Integer stat) {
	    Integer result = newsInfoService.release(id, stat);
	    if (result == 1) {
	        return MessageResp.getMessage(true, "操作成功~");
	    }
	    return MessageResp.getMessage(false, "操作失败~");
	}
	
	/**
	 * 修改新闻信息
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/update.do")
	public Map<String, Object> updateNews(SportNewsInfoDto newsDto,HttpServletRequest request,HttpServletResponse response) throws Exception{
		/**
		 * base64图片处理
		 */
//		if(!StringUtils.isEmpty(newsDto.getMicroPic())){
//			String microPic="";
//			try {
//				microPic = FileUtils.uploadImage(newsDto.getMicroPic());
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			newsDto.setMicroPic(microPic);
//		}else{
//			newsDto.setMicroPic(newsDto.getThumbnail());
//		}
	    SportNewsInfoDto sportNewsInfoDto = newsInfoService.updateNews(newsDto);
		if(sportNewsInfoDto.getResultStat()==2){
			return MessageResp.getMessage(true, "选中显示新闻最多三条~");
		}
		if(sportNewsInfoDto.getResultStat() == 3){
			return MessageResp.getMessage(true, "消息不存在~");
		}
//        map.put("nowDate", DateUtils.format(new Date(),DateUtils.YMD_DASH));
//        map.put("sjWeek", DateUtils.dayForWeek(new Date()));
//        map.put("info", sportNewsInfoDto.getContent());
//        map.put("title", sportNewsInfoDto.getTitle());
		String freemarkUrl = PropertiesUtils.getProperty("freemark.url");
        String html = freemarkUrl +File.separator +sportNewsInfoDto.getId()+".html";
        Map<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("content", sportNewsInfoDto.getContent());
        map.put("title", sportNewsInfoDto.getTitle());     
        List<MenuDto> dataList = iSportHomeMenuService.findSportHomeMenuList(null); 
        map.put("s_menu",(Serializable) dataList);        
        map.put("partnerList",(Serializable)loadPartnerList());
        try {
            FreeMarkerUtil.writeTo(getRequest().getSession().getServletContext(),
                    map, "/WEB-INF/view/template/", "news_detail.htm", html);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TemplateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return MessageResp.getMessage(true, "修改成功~");
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logger.info("去新增新闻页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		try {
			mv.setViewName("/sport/news/add");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEditU() throws Exception{
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		//修改资料
		String newsId = pd.getString("newsId");

		SportNewsInfoDto trainNewsInfo =  newsInfoService.getNewsById(newsId);//根据ID读取
	
		mv.setViewName("/sport/news/edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("trainNewsInfo", trainNewsInfo);
		
		return mv;
	}	
}
