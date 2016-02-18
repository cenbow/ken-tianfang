package com.tianfang.admin.controller.train;

import java.util.HashMap;
import java.util.List;
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
import com.tianfang.common.model.RequestResult;
import com.tianfang.train.dto.TrainingNewsInfoDto;
import com.tianfang.train.dto.TrainingTimeDistrictDto;
import com.tianfang.train.dto.TrainingTimeDistrictExDto;
import com.tianfang.train.service.ITrainingSpaceInfoService;

/**
 * 场地区域 时间段管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/timeDistrict")
public class TimeDistrictInfoColltroller extends BaseController {

	protected static final Log logger = LogFactory.getLog(NewsInfoController.class);
	
	@Autowired
	public ITrainingSpaceInfoService spaceManager;	
	
	/**
	 * 返回场地时间段信息  分页
	 * @param timeDistrictDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findAll.do")
	public ModelAndView findAll(TrainingTimeDistrictDto timeDistrictDto,ExtPageQuery page){
		logger.info("TrainingTimeDistrictDto timeDistrictDto : " + timeDistrictDto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<TrainingTimeDistrictDto> result =  spaceManager.findAllTiemDistrict(timeDistrictDto,page.changeToPageQuery());
		mv.addObject("pageList",result);
		mv.setViewName("/train_space/space_time/time_list");
		return mv; 
	}
	/**
	 * 返回所有时间段
	 * @param timeDistrictDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findAllNoPage.do")
	public ModelAndView findAllNoPage(TrainingTimeDistrictDto timeDistrictDto){
		logger.info("TrainingTimeDistrictDto timeDistrictDto : " + timeDistrictDto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		List<TrainingTimeDistrictDto> result =  spaceManager.findAllTiemDistrict(timeDistrictDto);
		mv.addObject("pageList",result);
		mv.setViewName("/train_space/space_time/timeAll_list");
		return mv; 
	}
	/**
	 * 新增场地时间段信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/save.do")
	public Map<String, Object> insertTimeDistrict(TrainingTimeDistrictDto timeDistrictDto){
		System.out.println("123");
		if(timeDistrictDto.getDayOfWeek()==null || timeDistrictDto.getStartTime() == null ||timeDistrictDto.getStartTime().equals("") ||timeDistrictDto.getEndTime().equals("")||timeDistrictDto.getEndTime()==null){
			return MessageResp.getMessage(false,"请求参数不能为空~~~");
		}
		int stat = spaceManager.insertTimeDistrict(timeDistrictDto);
		if(stat == 2){
			return MessageResp.getMessage(false,"数据已存在~~~");
		}
		if(stat == 1){
			return MessageResp.getMessage(true,"保存成功~~~");
		}
		return MessageResp.getMessage(false,"操作错误请重试~~~");
	}
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateOrDel.do")
	public RequestResult updateOrDel(String ids){
		if(ids == null || ids.equals("")){
			return new RequestResult(false,"请选中~~~");
		}
		int stat = spaceManager.updateOrDelByTimeDitrict(ids);
		if(stat == 2){
			return new RequestResult(false,"数据不存在~~~");
		}
		if(stat == 1){
			return new RequestResult(true,"删除成功~~~");
		}
		return new RequestResult(false,"出错了,请重试~~~");
	}
	/**
	 * 根据id获取对象
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/getByIdTraining.do")
	public Map<String, Object> getByIdTraining(String id){
		Map<String, Object> result = new HashMap<String, Object>();
		TrainingTimeDistrictDto timedistrictDto=spaceManager.getByIdTimeDistrict(id);
		if(timedistrictDto==null){
			return MessageResp.getMessage(false, "当前信息不存在,请重试~~~~");
		}
		result.put("success", true);
        result.put("data", timedistrictDto);
		return result;
	}
	/***
	 *  修改场地
	 * @param districtDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateEdit.do")
	public RequestResult updateEdit(TrainingTimeDistrictDto timeDistrictDto){
		int stat = spaceManager.updateEditByTimeDistrict(timeDistrictDto);
		if(stat == 1){
			return new RequestResult(true,"修改成功~~~~");
		}
		if(stat == 2){
			return new RequestResult(false,"数据已存在,请重新操作~~~~");
		}
		return new RequestResult(false,"操作错误,请重试~~~~");
	}
	/**
	 * 查询所有的时间段集合
	 * @return
	 */
	@RequestMapping(value="/listTime.do")
	public List<TrainingTimeDistrictExDto> getListTime(){
		return spaceManager.findAllTime();
	}
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logger.info("去新增时间段页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("/train_space/space_time/time_add");
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
		logger.info("去修改时间段页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		//修改资料
		String timeId = pd.getString("timeId");
		TrainingTimeDistrictDto timedistrictDto=spaceManager.getByIdTimeDistrict(timeId);
		mv.addObject("msg", "editU");
		mv.addObject("pd", timedistrictDto);
		mv.setViewName("/train_space/space_time/time_edit");
		return mv;
	}
}
