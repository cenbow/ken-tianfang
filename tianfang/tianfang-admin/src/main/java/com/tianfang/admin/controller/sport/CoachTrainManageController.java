package com.tianfang.admin.controller.sport;

import java.util.HashMap;
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
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.StringUtils;
import com.tianfang.news.dto.CoachTrainDto;
import com.tianfang.news.service.ICoachTrainService;

/**
 * 后台教练员培训管理
 * @author YIn
 * @time:2015年12月24日 上午10:05:52
 * @ClassName: CoachTrainManageController
 * @
 */
@Controller
@RequestMapping(value = "/sport/coachtrain")
public class CoachTrainManageController extends BaseController{

	protected static final Log logger = LogFactory.getLog(CoachTrainManageController.class);
	
	@Autowired
	private ICoachTrainService coachTrainService;
	/**
	 * 分页查询全部
	 * @author YIn
	 * @param coachTrainDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findPage.do")
	public ModelAndView findAll(CoachTrainDto coachTrainDto,ExtPageQuery page){
		logger.info("CoachTrainDto : " + coachTrainDto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stat", DataStatus.ENABLED);
		byparams(coachTrainDto,params);
		PageResult<CoachTrainDto> result= coachTrainService.findCoachTrainByParams(coachTrainDto, page.changeToPageQuery().getCurrPage(), page.changeToPageQuery().getPageSize());
		mv.addObject("pageList", result);
		mv.addObject("coachTrainDto",coachTrainDto);		
		mv.setViewName("/sport/coach_train/coach_list");
		return mv;
	}

	/**
	 * 查询条件
	 * @author YIn
	 * @param newsDto
	 * @param params
	 */
	public void byparams(CoachTrainDto coachTrainDto,Map<String, Object> params){
		if(StringUtils.isNotEmpty(coachTrainDto.getTitle())){
			params.put("title", coachTrainDto.getTitle());
		}
		if(coachTrainDto.getMarked()!=null){
			params.put("marked", coachTrainDto.getMarked());
		}
		if(StringUtils.isNotEmpty(coachTrainDto.getStartTimeStr())){
			params.put("createTimeS", coachTrainDto.getStartTimeStr());
		}
		if(StringUtils.isNotEmpty(coachTrainDto.getEndTimeStr())){
			params.put("createTimeE", coachTrainDto.getEndTimeStr());
		}
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logger.info("去教练培训新增页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		try {
			mv.setViewName("/sport/coach_train/coach_add");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 新增教练培训
	 */
	@ResponseBody
	@RequestMapping(value="/saveCoachTrain.do")
	public  Map<String, Object> insert(CoachTrainDto coachTrainDto,HttpServletRequest request,HttpServletResponse response) throws Exception{
        Integer status = coachTrainService.addCoachTrain(coachTrainDto);
		if(status == 0){
			return MessageResp.getMessage(false,"添加培训失败！");
		}
		return MessageResp.getMessage(true,"添加培训成功！");
	}
	
	/**
	 * 去培训修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(String id){
		logger.info("去培训修改页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		CoachTrainDto dto =coachTrainService.selectCoachTrainById(id);
		try {
			mv.setViewName("/sport/coach_train/coach_edit");
			mv.addObject("msg", "save");
			mv.addObject("coachTrainDto", dto);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 修改球队相片
	 * @author YIn
	 * @time:2015年11月13日 下午5:53:30
	 */
	
	@ResponseBody
	@RequestMapping("/update.do")
	public Map<String, Object> updateCoachTrain(CoachTrainDto coachTrainDto){
		logger.info("CoachTrainDto"+coachTrainDto);
		int status = coachTrainService.updateCoachTrain(coachTrainDto);
		if(status == 0){
			return MessageResp.getMessage(false,"更新培训失败！");
		}
		return MessageResp.getMessage(true,"更新培训成功！");
	}
	
	/**
	 * 删除教练培训
	 * @author YIn
	 * @time:2015年12月24日 下午2:10:18
	 * @param albumPictureDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete.do")
	public Map<String, Object> delCoachTrain(CoachTrainDto coachTrainDto){
		logger.info("CoachTrainDto"+coachTrainDto);
		int status = coachTrainService.delCoachTrain(coachTrainDto.getId());
		if(status == 0){
			return MessageResp.getMessage(false,"删除相片失败！");
		}
		return MessageResp.getMessage(true,"删除相片成功！");
	}
	
	/**
	 *  批量删除
	 * @author YIn
	 * @time:2015年12月24日 下午2:27:18
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/delByIds.do")
    public Map<String, Object> delByIds(String  ids) throws Exception{
	    if (StringUtils.isEmpty(ids)) {
	        return MessageResp.getMessage(false, "请选择需要删除的项！");
	    }
	    Integer resObject =(Integer) coachTrainService.delByIds(ids);
	    if (resObject == 0) {
            return MessageResp.getMessage(false, "无此条记录！");
        }
	    if (resObject == 1) {
            return MessageResp.getMessage(true, "删除成功！");
        }
	    return MessageResp.getMessage(false, "未知错误！");
	}
	
    /**
     * 修改发布状态   
     * @author YIn
     * @time:2015年12月24日 下午7:39:50
     * @param id
     * @param releaseStat
     * @return
     */
    @RequestMapping("/release")
    @ResponseBody
    public Map<String, Object> release(String id,Integer releaseStat) {
        if(id == null || "".equals(id) || null == releaseStat){
            return MessageResp.getMessage(false, "请求参数不能为空~") ;
        }
        coachTrainService.release(id, releaseStat);
        return MessageResp.getMessage(true, "修改成功！");
    }
}
