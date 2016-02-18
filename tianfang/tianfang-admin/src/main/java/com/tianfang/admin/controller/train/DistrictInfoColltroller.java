package com.tianfang.admin.controller.train;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.RequestResult;
import com.tianfang.train.dto.TrainingDistrictDto;
import com.tianfang.train.dto.TrainingNewsInfoDto;
import com.tianfang.train.service.ITrainingSpaceInfoService;


/**
 * 场地 区域信息
 * @author mr.w
 *
 */
@Controller
@RequestMapping(value="/district") 
public class DistrictInfoColltroller extends BaseController {
	
	protected static final Log logger = LogFactory.getLog(NewsInfoController.class);
	
	@Autowired
	public ITrainingSpaceInfoService spaceManager;	
	/**
	 * 获取场地区域信息列表
	 * @param districtDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findAll.do")
	public ModelAndView findAll(TrainingDistrictDto districtDto,ExtPageQuery page){
		logger.info("TrainingDistrictDto districtDto : " + districtDto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		if(districtDto.getName()==null){
		}else{
			if(districtDto.getName().equals("全部")){
				districtDto.setName("");
			}
		}
		PageResult<TrainingDistrictDto> result =  spaceManager.findAllDistrict(districtDto,page.changeToPageQuery());
		List<TrainingDistrictDto> disAll =  spaceManager.findAllDistrictNoPage();
		mv.addObject("pageList",result);
		mv.addObject("disAll",disAll);
		mv.setViewName("/train_space/space_district/district_list");
		return mv; 
	}
	
	/**
	 * 获取场地区域信息列表
	 * @param districtDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findAllNoPage.do")
	public ModelAndView findAllNoPage(){
		logger.info("查询所有场地信息");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		List<TrainingDistrictDto> result =  spaceManager.findAllDistrictNoPage();
		mv.addObject("pageList",result);		
		mv.setViewName("/train_space/space_district/district_list");
		return mv; 
	}
	
	/**
	 * 新增区域信息
	 * @param timeDistrictDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/insert.do")
	public Map<String, Object> insertTimeDistrict(TrainingDistrictDto districtDto){
		if (StringUtils.isBlank(districtDto.getName()) ||  StringUtils.isBlank(districtDto.getCity())) {
	        return MessageResp.getMessage(false, "请求参数不能为空~");
        }
		int stat = spaceManager.insertDistrict(districtDto);
		if(stat == 1){
			return MessageResp.getMessage(true,"添加成功~~~");
		}
		if(stat == 2){
			return MessageResp.getMessage(false,"区域已存在~~~");
		}
		return MessageResp.getMessage(false,"添加失败~~~");
	}
	/**
	 *删除
	 * @param distictId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateOrDel.do")
	public RequestResult updateOrDel(String distictId){
		logger.info("删除区域 id="+distictId);
		if(distictId == null || distictId.equals("")){
			return new RequestResult(false,"请选中一条数据进行修改~~~");
		}
		int stat = spaceManager.updateOrDel(distictId);
		if(stat == 0){
			return new RequestResult(false,"删除数据不存在~~~");
		}
		if(stat == 1){
			return new RequestResult(true,"删除成功~~~");
		}
		if(stat == 2){
			return new RequestResult(false,"数据已存在~~~");
		}
		return new RequestResult(false,"操作错误,请重试~~~");
	}
	/**
	 * 修改
	 * @param districtDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateEdit.do")
	public RequestResult updateEdit(TrainingDistrictDto districtDto){
		if (StringUtils.isBlank(districtDto.getName()) ||  StringUtils.isBlank(districtDto.getCity())) {
	        return new RequestResult(true,"请求参数不为空~~~~");
        }
		int stat = spaceManager.updateEdit(districtDto);
		if(stat == 1){
			return new RequestResult(true,"修改成功~~~~");
		}
		if(stat == 2){
			return new RequestResult(false,"数据已存在~~~~");
		}
		return new RequestResult(false,"操作错误,请重试~~~~");
	}
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/getByIdTraining.do")
	public Map<String, Object> getByIdTraining(String id){
		Map<String, Object> result = new HashMap<String, Object>();
		TrainingDistrictDto districtDto=spaceManager.getByIdTraining(id);
		if(districtDto==null){
			MessageResp.getMessage(false, "当前信息不存在,请重试~~~~");
		}
		result.put("success", true);
        result.put("data", districtDto);
		return result;
	}
	
	/**
	 * 去新增时间段页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logger.info("去新增区域页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("/train_space/space_district/district_add");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	/**
	 * 去修改区域页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEditU() throws Exception{
		logger.info("去修改区域页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		//修改资料
		String districtId = pd.getString("districtId");

		TrainingDistrictDto districtDto=spaceManager.getByIdTraining(districtId);
		mv.setViewName("/train_space/space_district/district_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", districtDto);
		
		return mv;
	}
}
