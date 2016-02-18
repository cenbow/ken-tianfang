package com.tianfang.admin.controller.train;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.admin.controller.PageData;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.FileUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.TrainingNewsInfoDto;
import com.tianfang.train.service.ITrainingNewsInfoService;
/**
 * 新闻资讯模块
 * @author mr.w
 *
 */
@Controller	
@RequestMapping(value="/newsInfo") 
public class NewsInfoController extends BaseController{
	protected static final Log logger = LogFactory.getLog(NewsInfoController.class);
	
	@Autowired
	private ITrainingNewsInfoService newsInfoService;
	/**
	 * 查询全部
	 * @param newsDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findPage.do")
	public ModelAndView findAll(TrainingNewsInfoDto newsDto,ExtPageQuery page){
		logger.info("TrainingNewsInfoDto newsDto : " + newsDto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stat", DataStatus.ENABLED);
		isNoNews(newsDto,params);
		PageResult<TrainingNewsInfoDto> result= newsInfoService.findNewsByParams(newsDto, page.changeToPageQuery().getCurrPage(), page.changeToPageQuery().getPageSize());
		mv.addObject("pageList", result);
		mv.addObject("newsDto",newsDto);		
		mv.setViewName("/train/news_list");
		return mv;
	}
	/**
	 * 检测查询条件
	 * @param newsDto
	 * @param params
	 */
	public void isNoNews(TrainingNewsInfoDto newsDto,Map<String, Object> params){
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
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value="/saveNew.do")
	public  Map<String, Object> insert(TrainingNewsInfoDto newsDto) throws IOException{
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
		if(!StringUtils.isEmpty(newsDto.getMicroPic())){
			String microPic="";
			try {
				microPic = FileUtils.uploadImage(newsDto.getMicroPic());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			newsDto.setMicroPic(microPic);
		}
		
		int stat = newsInfoService.saveNews(newsDto);
		if(stat == 2){
			return MessageResp.getMessage(true,"最多选中三条~");
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
	public Map<String, Object> findNewsById(TrainingNewsInfoDto newsDto){
		logger.info("TrainingNewsInfoDto newsDto : " + newsDto);
		Map<String, Object> result = new HashMap<String, Object>();
		TrainingNewsInfoDto  traNews = newsInfoService.getNewsById(newsDto.getId());
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
        TrainingNewsInfoDto trainNewsInfo =  newsInfoService.getNewsById(id);//根据ID读取
        try {
            mv.setViewName("/train/news_page_position");
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
	 * 修改新闻信息
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value="/update.do")
	public Map<String, Object> updateNews(TrainingNewsInfoDto newsDto) throws IOException{
		/**
		 * base64图片处理
		 */
		if(!StringUtils.isEmpty(newsDto.getMicroPic())){
			String microPic="";
			try {
				microPic = FileUtils.uploadImage(newsDto.getMicroPic());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			newsDto.setMicroPic(microPic);
		}else{
			newsDto.setMicroPic(newsDto.getThumbnail());
		}
		int stat = newsInfoService.updateNews(newsDto);
		if(stat==2){
			return MessageResp.getMessage(true, "选中显示新闻最多三条~");
		}
		if(stat == 3){
			return MessageResp.getMessage(true, "消息不存在~");
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
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("/train/news_add");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 去修改用户页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEditU() throws Exception{
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		//修改资料
		String newsId = pd.getString("newsId");

		TrainingNewsInfoDto trainNewsInfo =  newsInfoService.getNewsById(newsId);//根据ID读取
	
		mv.setViewName("/train/news_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("trainNewsInfo", trainNewsInfo);
		
		return mv;
	}
}
