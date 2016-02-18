package com.tianfang.admin.controller.train;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.admin.controller.PageData;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.RequestResult;
import com.tianfang.common.util.JsonUtil;
import com.tianfang.train.dto.TrainApplyDto;
import com.tianfang.train.service.ITrainApplyService;



/**
 * 培训模块报名
 * @author YIn
 * @time:2015年9月1日 下午2:35:10
 * @ClassName: TrainApplyController
 * @Description: TODO
 * @
 */
@Controller
@RequestMapping(value="/apply" )
public class TrainApplyController extends BaseController{
	protected static final Log logger = LogFactory.getLog(TrainApplyController.class);
	
	@Autowired
	private ITrainApplyService trainApplyService;
	
	/**
	 * 报名列表信息显示(关联课程表和场地表查询)
	 * @author YIn
	 * @time:2015年9月1日 下午4:38:36
	 * @param trainApplyDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findApplyByPage.do")
	public ModelAndView findApplyByPage(TrainApplyDto trainApplyDto, ExtPageQuery page) {
		logger.info("TrainApplyController trainApplyDto : " + trainApplyDto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stat", DataStatus.ENABLED);
		isNoNews(trainApplyDto,params);
		PageResult<TrainApplyDto> results = trainApplyService.findApplyByPage(trainApplyDto, page.changeToPageQuery());
		mv.addObject("pageList", results);
		mv.addObject("trainApplyDto",trainApplyDto);		
		mv.setViewName("/train/apply_list");
		return mv;		
	}
	
	/**
	 * 检测查询条件
	 * @param trainApplyDto
	 * @param params
	 */
	public void isNoNews(TrainApplyDto trainApplyDto,Map<String, Object> params){
		if(trainApplyDto.getPname()!=null&&!"".equals(trainApplyDto.getPname())){
			params.put("pname", trainApplyDto.getPname());
		}
		if(trainApplyDto.getMobile()!=null&&!"".equals(trainApplyDto.getMobile())){
			params.put("mobile", trainApplyDto.getMobile());
		}
		if(trainApplyDto.getCourseId()!=null && !"".equals(trainApplyDto.getCourseId())){
			params.put("courseId", trainApplyDto.getCourseId());
		}
		if(trainApplyDto.getCname()!=null && !"".equals(trainApplyDto.getCname())){
			params.put("cname", trainApplyDto.getCname());
		}
		if(trainApplyDto.getSpaceId()!=null && !"".equals(trainApplyDto.getSpaceId())){
			params.put("spaceId", trainApplyDto.getSpaceId());
		}
		if(trainApplyDto.getStartTime()!=null && !"".equals(trainApplyDto.getStartTime())){
			params.put("startTime", trainApplyDto.getStartTime());
		}
		if(trainApplyDto.getEndTime()!=null && !"".equals(trainApplyDto.getEndTime())){
			params.put("endTime", trainApplyDto.getEndTime());
		}
	}
	
	/**
	 * 删除报名
	 * @author YIn
	 * @time:2015年9月1日 下午4:41:48
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delByIds.do")
    public Map<String, Object> delByIds(String  ids) throws Exception{
	    if (StringUtils.isBlank(ids)) {
	        return MessageResp.getMessage(false, "请求参数不能为空~");
	    }
	    Integer resObject =(Integer) trainApplyService.delByIds(ids);
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
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logger.info("去新增新闻页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("/train/apply_add");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 增加报名
	 * @author YIn
	 * @time:2015年9月1日 下午4:41:30
	 * @param trainApplyDto
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="/addApply.do")
	public String addApply(TrainApplyDto trainApplyDto,HttpServletRequest request, String applyTimeAdd,HttpServletResponse response) throws IOException {
		logger.info("TrainApplyController trainApplyDto : " + trainApplyDto);
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Date applyTime = null;
		try {
			applyTime = sdf.parse(applyTimeAdd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//long time =JsonUtil.parseDateStr(applyTimeAdd.substring(0, 10), "yyyy-MM-dd");
		//Date time = DateUtils.parse(applyTimeAdd.substring(0, 10), "yyyy-MM-dd");
		trainApplyDto.setApplyTime(applyTime.getTime()/1000);
		trainApplyService.save(trainApplyDto);
		return JsonUtil.getJsonStr(new RequestResult(true, "200"));
	}
	
	/**
	 * 去报名修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEditU() throws Exception{
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		//修改资料
		String id = pd.getString("id");
		TrainApplyDto trainApplyDto =  trainApplyService.getApplyById(id);//根据ID读取
		mv.setViewName("/train/apply_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("trainApplyDto", trainApplyDto);
		return mv;
	}
	
/*	*//**
	 * 增加报名
	 * @author YIn
	 * @time:2015年9月1日 下午4:41:30
	 * @param trainApplyDto
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 *//*
	@ResponseBody
	@RequestMapping(value="/create.do")
	public String createTrainApply(TrainApplyDto trainApplyDto,HttpServletRequest request, String applyTimeAdd,HttpServletResponse response) throws IOException {
		logger.info("TrainApplyController trainApplyDto : " + trainApplyDto);
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Date applyTime = null;
		try {
			applyTime = sdf.parse(applyTimeAdd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//long time =JsonUtil.parseDateStr(applyTimeAdd.substring(0, 10), "yyyy-MM-dd");
		//Date time = DateUtils.parse(applyTimeAdd.substring(0, 10), "yyyy-MM-dd");
		trainApplyDto.setApplyTime(applyTime.getTime()/1000);
		iTrainApplyService.save(trainApplyDto);
		return JsonUtil.getJsonStr(new RequestResult(true, "200"));
	}
	

	*//**
	 * 删除报名
	 * @author YIn
	 * @time:2015年9月1日 下午4:41:48
	 * @param id
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value="/delByIds.do")
    public Map<String, Object> delByIds(String  ids) throws Exception{
	    if (StringUtils.isBlank(ids)) {
	        return MessageResp.getMessage(false, "请求参数不能为空~");
	    }
	    Integer resObject =(Integer) iTrainApplyService.delByIds(ids);
	    if (resObject == 0) {
            return MessageResp.getMessage(false, "无此条记录！");
        }
	    if (resObject == 1) {
            return MessageResp.getMessage(true, "删除成功！");
        }
	    return MessageResp.getMessage(false, "未知错误！");
	}
	
	*//**
	 * 修改时根据ID查询
	 * @author YIn
	 * @time:2015年9月6日 上午9:48:21
	 * @param id
	 * @return
	 *//*
	@RequestMapping(value="getApplyById.do")
	@ResponseBody
	public Map<String, Object> getApplyById(String id) {
		TrainApplyDto trainApplyDto = iTrainApplyService.getApplyById(id);
	    if(trainApplyDto == null){ return  MessageResp.getMessage(false, "没有此条信息");}
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("data", trainApplyDto);
        return  result;
	}

	*/
	/**
	 * 修改报名
	 * @author YIn
	 * @time:2015年9月1日 下午4:42:38
	 * @param trainInfoDto
	 * @param myfile
	 * @param logofile
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="/update.do")
	public String updateTrainApply(TrainApplyDto trainApplyDto,HttpServletRequest request,String applyTimeAdd,HttpServletResponse response) throws IOException {
		logger.info("TrainApplyController trainApplyDto : " + trainApplyDto);
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Date applyTime = null;
		try {
			applyTime = sdf.parse(applyTimeAdd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		trainApplyDto.setApplyTime(applyTime.getTime()/1000);
		int flag = trainApplyService.update(trainApplyDto);
		if(flag >0){
			return JsonUtil.getJsonStr(new RequestResult(true, "200"));
		}else{
			return JsonUtil.getJsonStr(new RequestResult(false, "500"));
		}
	}

}
