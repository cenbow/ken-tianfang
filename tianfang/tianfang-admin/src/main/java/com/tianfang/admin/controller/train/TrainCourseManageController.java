package com.tianfang.admin.controller.train;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.FileUtils;
import com.tianfang.common.util.PropertiesUtils;
import com.tianfang.train.dto.CourseClassReqDto;
import com.tianfang.train.dto.CourseTagDto;
import com.tianfang.train.dto.TrainingAddressDto;
import com.tianfang.train.dto.TrainingCourseDto;
import com.tianfang.train.dto.TrainingCourseDtoX;
import com.tianfang.train.dto.TrainingCourseReqDto;
import com.tianfang.train.dto.TrainingNewsInfoDto;
import com.tianfang.train.dto.TrainingSpaceManagerDto;
import com.tianfang.train.service.ICourseClassService;
import com.tianfang.train.service.ITrainingAdressQueryService;
import com.tianfang.train.service.ITrainingCourService;
import com.tianfang.train.service.ITrainingTagService;


@Controller
@RequestMapping(value = "/course")
public class TrainCourseManageController extends BaseController{

	protected static final Log logger = LogFactory
			.getLog(TrainCourseManageController.class);

	@Autowired
	private ITrainingCourService trainingCourseService;

	@Autowired
	private ICourseClassService iCourseClassService;
	
	@Autowired
	private ITrainingTagService trainingTagService;
	
	@Autowired
    private ITrainingAdressQueryService addressService;
	
	@RequestMapping(value = "/findPage.do")
	public ModelAndView findByPage(TrainingCourseDtoX newsDto,
			ExtPageQuery page) {
		logger.info("TrainCourseManageController newsDto:" + newsDto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("stat", DataStatus.ENABLED);
        isNoNews(newsDto,params);
		List<CourseTagDto> resulTag = trainingCourseService.findCourseTag(null);       
		PageResult<TrainingCourseDtoX> result = trainingCourseService.findByPage(newsDto, page.changeToPageQuery());
		mv.addObject("pageList", result);
		mv.addObject("newsDto",newsDto);
		mv.addObject("datas", resulTag);
		mv.setViewName("/course/course_list");
		return mv;
	}
	
	/**
     * 检测查询条件
     * @param newsDto
     * @param params
     */
    public void isNoNews(TrainingCourseDtoX query,Map<String, Object> params){
        if(query.getName()!=null&&!"".endsWith(query.getName())){
            params.put("name",query.getName());
        }
        if(query.getName()!=null&&"".endsWith(query.getName())){
            query.setName(null);
        }
        if(query.getTagId()!=null &&!"".endsWith(query.getTagId())){
            params.put("tagId", query.getTagId());
        }
        if(query.getTagId()!=null &&"".endsWith(query.getTagId())){
            query.setTagId(null);
        }
        if(null != query.getCourseTime()){
            params.put("courseTime", query.getCourseTime());
        }
    }
	
	/**
     * 去新增页面
     */
    @RequestMapping(value="/openAddView")
    public ModelAndView goAdd(){
        logger.info("打开新增课程页面");
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<CourseTagDto> result = trainingCourseService.findCourseTag(null);
            mv.addObject("datas", result);
            mv.setViewName("/course/course_add");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }                       
        return mv;
    }
     
    /**
     * 添加场地
     * 
     * @author xiang_wang 2015年10月23日上午10:32:58
     */
    @RequestMapping(value = "addPlace.do")
    public ModelAndView addPlace(String startTime,String endTime) {
        List<TrainingAddressDto> result = addressService.find(null);
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        mv.addObject("datas", result);
        mv.addObject("startTime", startTime);
        mv.addObject("endTime", endTime);
        mv.setViewName("/course/course_select_place");
        return mv;
    }

    @RequestMapping(value = "/findByCourseId.do")
    @ResponseBody
	public List<CourseClassReqDto> findByCourseId(String courseId) {
	    return iCourseClassService.findByCourseId(courseId);
	}
	/**
	 * 查询所有课程
	 * @author YIn
	 * @time:2015年9月8日 下午3:25:28
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/findAllCourse.do")
	@ResponseBody
	public List<TrainingCourseDto> findAllCourse() {
		return trainingCourseService.findAllCourse();
	}

	@RequestMapping(value = "/findAll.do")
	@ResponseBody
	public List<CourseTagDto> findAll(CourseTagDto courseTagDto) {
		logger.info("TrainCourseManageController courseTagDto:" + courseTagDto);
		List<CourseTagDto> courseTagDtos = trainingCourseService
				.findCourseTag(courseTagDto);
		return courseTagDtos;
	}
	
	@RequestMapping(value = "/saves.do")
    @ResponseBody
    public Map<String, Object> save(TrainingCourseReqDto trainingCourseDtoX,String jsonClasss, HttpServletRequest request) throws Exception{	  
	    if(!StringUtils.isEmpty(trainingCourseDtoX.getPic())){
            String microPic="";
            try {
                microPic = FileUtils.uploadImage(trainingCourseDtoX.getPic());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            trainingCourseDtoX.setPic(microPic);
        }
	    if (StringUtils.isNotBlank(trainingCourseDtoX.getMicroPic())) {
	        String microPic="";
            try {
                microPic = FileUtils.uploadImage(trainingCourseDtoX.getMicroPic());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            trainingCourseDtoX.setMicroPic(microPic);
	    }
        TrainingCourseReqDto resObject = trainingCourseService.save(trainingCourseDtoX,jsonClasss);
        if (null != resObject && StringUtils.isNotBlank(resObject.getAddress()) &&  resObject.getAddress().equals("0")) {
            return MessageResp.getMessage(true, "价格已经有此条记录~");
        }
        if ( null != resObject && StringUtils.isNotBlank(resObject.getAddress()) &&  resObject.getAddress().equals("1")) {
            return MessageResp.getMessage(true, "添加的课程重复~");
        }
        if (null != resObject && StringUtils.isBlank(resObject.getAddress())) {
            return MessageResp.getMessage(true, resObject.getId());
        }
        return MessageResp.getMessage(false, "未知错误~");
    }
	
	/**
	 *      
	 * goEditU：打开编辑课程页面
	 * @return
	 * @throws Exception
	 * @exception	
	 * @author Administrator
	 * @date 2015年10月28日 上午9:32:15
	 */
    @RequestMapping(value="/openEditView")
    public ModelAndView goEditU() throws Exception{
        logger.info("打开编辑课程页面");
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageData pd = new PageData();
        pd = this.getPageData();
        //修改资料
        String courseClassId = pd.getString("courseClassId");
        TrainingCourseDtoX trainingCourseDtoX = trainingCourseService.getTrainingCourse(courseClassId);
        TrainingCourseReqDto trainingCourseReqDto = BeanUtils.createBeanByTarget(trainingCourseDtoX, TrainingCourseReqDto.class);
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM");
        Date dateS = new Date();
        Date dateE = new Date();
        if (null != trainingCourseDtoX.getStartTime()) {
             dateS = new Date(trainingCourseDtoX.getStartTime()*1000);
        }
        if (null != trainingCourseDtoX.getEndTime()) {
             dateE = new Date(trainingCourseDtoX.getEndTime()*1000);
        }  
        trainingCourseReqDto.setStartTime(sdf.format(dateS));
        trainingCourseReqDto.setEndTime(sdf.format(dateE));
        List<CourseTagDto> result = trainingCourseService.findCourseTag(null);
        List<CourseClassReqDto> courseClassReqDtos = iCourseClassService.findByCourseId(courseClassId);
        mv.setViewName("/course/course_edit");
        mv.addObject("courseClassReqDtos", courseClassReqDtos);
        mv.addObject("datas", result);
        mv.addObject("msg", "editU");
        mv.addObject("pd", pd);
        mv.addObject("trainingCourseReqDto", trainingCourseReqDto);        
        return mv;
    }
	
	@RequestMapping(value = "/edit.do")
    @ResponseBody
    public Map<String, Object> edit(TrainingCourseReqDto trainingCourseDtoX, String jsonClasss, HttpServletRequest request) throws Exception{       
	    if(!StringUtils.isEmpty(trainingCourseDtoX.getPic())){
            String microPic="";
            try {
                microPic = FileUtils.uploadImage(trainingCourseDtoX.getPic());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            trainingCourseDtoX.setPic(microPic);
        }else {
            trainingCourseDtoX.setPic(null);
        }
        if (StringUtils.isNotBlank(trainingCourseDtoX.getMicroPic())) {
            String microPic="";
            try {
                microPic = FileUtils.uploadImage(trainingCourseDtoX.getMicroPic());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            trainingCourseDtoX.setMicroPic(microPic);
        }else {
            trainingCourseDtoX.setMicroPic(null);
        }        
        TrainingCourseReqDto resObject = trainingCourseService.edit(trainingCourseDtoX, jsonClasss);
        if (null != resObject && StringUtils.isNotBlank(resObject.getAddress()) &&  resObject.getAddress().equals("0")) {
            return MessageResp.getMessage(false, "此课程不存在~");
        }
        if (null != resObject && StringUtils.isNotBlank(resObject.getAddress()) &&  resObject.getAddress().equals("1")) {
            return MessageResp.getMessage(false, "此课程已经存在~");
        }
        if (null != resObject && StringUtils.isBlank(resObject.getAddress())) {
            return MessageResp.getMessage(true, resObject.getId());
        }
        return MessageResp.getMessage(false, "未知错误~");
    }
	
	/**
	 * 根据课程查询场地Id
	 * @author YIn
	 * @time:2015年9月6日 下午5:37:40
	 * @param courseId
	 * @return
	 */
	@RequestMapping(value = "/findSpaceByCourseId.do")
	@ResponseBody
	public List<TrainingSpaceManagerDto> findSpaceByCourseId(String courseId) {
		logger.info("TrainCourseManageController courseId:" + courseId);
		List<TrainingSpaceManagerDto> tsDto = trainingCourseService
				.findSpaceByCourseId(courseId);
		return tsDto;
	}
	
	@RequestMapping(value = "/getTrainingCourse.do")
    @ResponseBody
    public  Map<String, Object> getTrainingCourse(String id,String courseClassId) {
	    Map<String, Object> result = new HashMap<String, Object>();
	    TrainingCourseDtoX trainingCourseDtoX = trainingCourseService.getTrainingCourse(courseClassId);
	    if(trainingCourseDtoX == null){ return  MessageResp.getMessage(false, "没有此条信息");}
	    TrainingCourseReqDto trainingCourseReqDto = BeanUtils.createBeanByTarget(trainingCourseDtoX, TrainingCourseReqDto.class);
	    SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM");
	    Date dateS = new Date();
        Date dateE = new Date();
        if (null != trainingCourseDtoX.getStartTime()) {
             dateS = new Date(trainingCourseDtoX.getStartTime()*1000);
        }
        if (null != trainingCourseDtoX.getEndTime()) {
             dateE = new Date(trainingCourseDtoX.getEndTime()*1000);
        }  
        trainingCourseReqDto.setStartTime(sdf.format(dateS));
        trainingCourseReqDto.setEndTime(sdf.format(dateE));
        result.put("success", true);
        result.put("data", trainingCourseReqDto);
        return  result;
	}

	@RequestMapping(value = "/deleteCourseInfo.do")
	@ResponseBody
	public Map<String, Object> deleteCourseInfo(String ids,String courseClassIds) {
		if (StringUtils.isBlank(ids)) {
			return MessageResp.getMessage(false, "请求参数不能为空~");
		}
		Integer resObject = (Integer) trainingCourseService.deleteCourseInfo(ids,courseClassIds);
		if (resObject == 0) {
			return MessageResp.getMessage(false, "无此条记录~");
		}
		if (resObject == 1) {
			return MessageResp.getMessage(true, "删除成功");
		}
		return MessageResp.getMessage(false, "未知错误~");

	}
}
