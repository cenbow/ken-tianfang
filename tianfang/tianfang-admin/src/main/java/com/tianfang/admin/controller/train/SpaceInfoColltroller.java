package com.tianfang.admin.controller.train;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import com.tianfang.common.util.FileUtils;
import com.tianfang.train.dto.TrainApplyDto;
import com.tianfang.train.dto.TrainingAddressDto;
import com.tianfang.train.dto.TrainingDistrictDto;
import com.tianfang.train.dto.TrainingNewsInfoDto;
import com.tianfang.train.dto.TrainingSpaceManagerDto;
import com.tianfang.train.dto.TrainingTimeDistrictDto;
import com.tianfang.train.service.ITrainingAdressQueryService;
import com.tianfang.train.service.ITrainingSpaceInfoService;

/**
 * 场地信息管理模块
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/spaceInfo")
public class SpaceInfoColltroller extends BaseController{
	
	protected static final Log logger = LogFactory.getLog(NewsInfoController.class);
	
	@Autowired
	public ITrainingSpaceInfoService spaceManager;
	
	@Autowired
	private ITrainingAdressQueryService addressService;
	
	/**
	 * 查询场地信息的数据
	 * @param spaceDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findAll.do")
	public ModelAndView findAll(TrainingSpaceManagerDto spaceDto,ExtPageQuery page){
		logger.info("SpaceInfoColltroller spaceDto : " + spaceDto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		List<TrainingDistrictDto> res =  spaceManager.findAllDistrictNoPage();  //区域信息
		PageResult<TrainingSpaceManagerDto> result =  spaceManager.findAll(spaceDto,page.changeToPageQuery());
		mv.addObject("pageList",result);
		mv.setViewName("/train_space/spaceInfo_list");
		mv.addObject("dis",res);
		return mv;  
	}
	/**
	 * 获取所有场地信息
	 * 
	 * @author xiang_wang 2015年10月23日上午10:32:58
	 */
	@RequestMapping(value = "findAllNoPage.do")
	public ModelAndView findAllNoPage() {
		List<TrainingAddressDto> result = addressService.find(null);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		mv.addObject("datas", result);
		mv.setViewName("/assistant/selectPlace");
		return mv;
	}
	/**
	 * 获取所有场地信息
	 *  @author Yin 2015年10月23日 
	 */
	@ResponseBody
	@RequestMapping(value = "findSpaceNoPage.do")
	public List<TrainingAddressDto> findSpaceNoPage() {
		List<TrainingAddressDto> result = addressService.find(null);
		return result;
	}
	/**
	 * 关联查询一个场地关联的所有信息
	 * @return
	 */
	@RequestMapping(value="/selectAll.do")
	public List<TrainingAddressDto> selectAllAddress(TrainingAddressDto addressDto){
		List<TrainingAddressDto> result = spaceManager.selectAllAddress(addressDto.getId());
		return result;
	}
	
	/**
	 * 根据场地查询对应的时间段
	 * Ken  YIn
	 * @return
	 */
	@RequestMapping(value="/findTimeDistrictBySpaceId.do")
	public List<TrainingTimeDistrictDto> findTimeDistrictBySpaceId(String spaceId){
		List<TrainingTimeDistrictDto> result = spaceManager.findTimeDistrictBySpaceId(spaceId);
		return result;
	}
	
	/**
	 *  新增场地
	 * @param timeDistrictDto
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/insert.do")
	public  Map<String, Object> insertTimeDistrict(TrainingAddressDto addressDto,String checkBoxId) throws Exception{
		
		if(!StringUtils.isEmpty(addressDto.getThumbnail())){
			String microPic="";
			try {
				microPic = FileUtils.uploadImage(addressDto.getThumbnail());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			addressDto.setThumbnail(microPic);
		}
		int stat = spaceManager.insertAddress(addressDto);
		if(stat == 1){
			return MessageResp.getMessage(true,"添加成功~~~");
		}
		if(stat == 3){
			return MessageResp.getMessage(true, "地址名称重复,请重新输入~");
		}
		if(stat == 4){
			return MessageResp.getMessage(true, "场地名称重复,请重新输入~");
		}
		if(stat == 5){
			return MessageResp.getMessage(true, "经纬度已存在,请重新选择~");
		}
		return MessageResp.getMessage(false,"添加失败,请重试~~~");
	}
	
	private void uploadImage(TrainingAddressDto addressDto, MultipartFile myfile1) throws IOException {
       /* //如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解  
        //如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解  
        String context = "/upload";
        String realPath = PropertiesUtils.getProperty("upload.url");
        String fileDe = DateUtils.format(new Date(), DateUtils.YMD);
        String fileName1 = null;
        String thumbnail = null;
        if (null != myfile1) {
            fileName1 = FileUtils.getUploadFileName(myfile1.getOriginalFilename());
        } 
        logger.info("filePaht :"+realPath + "/" +fileDe + "/" + fileName1);
        File file = new File(realPath + "/" +context+"/"+fileDe);
        //如果文件夹不存在则创建    
        if(!file.exists() && !file.isDirectory()) {
            file.mkdir();    
        }
    //    String fileName = FileUtils.getUploadFileName(myfile.getOriginalFilename());
        if (null != fileName1) {
            FileUtils.copyInputStreamToFile(myfile1.getInputStream(), new File(realPath + "/" +context+"/"+fileDe , fileName1));
        }
    //    FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath + "/" + context, fileName));  
    //    raceInfo.setPic(context + "/" + fileName);
    //    raceInfo.setPic(context + "/" + fileDe + "/" + fileName);
    //    raceInfo.setLogo(context + "/" + fileDe + "/" + logoName);
        if (null != fileName1) {
            thumbnail = context + "/" + fileDe + "/" + fileName1;
        }
        addressDto.setThumbnail(thumbnail);*/
}
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getByIdAddress.do")
	public Map<String, Object> getByIdTraining(String id){
		Map<String, Object> result = new HashMap<String, Object>();
		TrainingAddressDto traAddDto = spaceManager.findByIdTraAddress(id);
		
		result.put("success", true);
        result.put("data", traAddDto);
        return  result;
	}
	/**
	 * 删除
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delecte.do")
	public Map<String, Object> updateOrDel(String id){
		int stat = spaceManager.deleteByAddress(id);
		if(stat == 1){
			return MessageResp.getMessage(true, "删除成功~~~~");
		}
		if(stat == 2){
			return MessageResp.getMessage(false, "数据不存在~~~~");
		}
		return MessageResp.getMessage(false, "未知错误~");
	}
	/**
	 * 修改
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/edit.do")
	public Map<String, Object> edit(TrainingAddressDto addressDto) throws Exception{
		if(addressDto.getAddress() == null || addressDto.getPlace() == null || addressDto.getDistrictId() == null ||addressDto.getLongtitude()==null ||addressDto.getLatitude() ==null  ){
			return MessageResp.getMessage(false, "请求数据不能为空~");
		}
		
		if(!StringUtils.isEmpty(addressDto.getThumbnail())){
			String microPic="";
			try {
				microPic = FileUtils.uploadImage(addressDto.getThumbnail());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			addressDto.setThumbnail(microPic);
		}
		
		int stat=  spaceManager.editAddressOrTime(addressDto);
		if(stat == 1){
			return MessageResp.getMessage(true, "更新成功~");
		}
		if(stat == 2){
			return MessageResp.getMessage(true, "数据不存在,请重新选择~");
		}
		/*if(stat == 3){
			return MessageResp.getMessage(false, "数据已存在,请重新输入~");
		}*/
		return MessageResp.getMessage(false, "未知错误~");
	}
	
	@RequestMapping(value="/insertByTimeAdd.do")
	public Map<String, Object> editTimePlace(TrainingAddressDto addressDto){
		int isno = spaceManager.insertBytimeAdd(addressDto);
		if(isno == 1 ){
			return MessageResp.getMessage(true, "添加成功~");
		}else{
			return MessageResp.getMessage(false, "添加出错~"); 
		}
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logger.info("去新增场地页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		
		TrainingTimeDistrictDto timeDistrictDto = new TrainingTimeDistrictDto();
		List<TrainingDistrictDto> result =  spaceManager.findAllDistrictNoPage();
		pd = this.getPageData();
		try {
			mv.setViewName("/train_space/space_add");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
			mv.addObject("dis",result);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	/**
	 * 去修改场地页面
	 * @return
	 */
	@RequestMapping(value="/edits")
	public ModelAndView goEditspace(String spaceId){
		logger.info("去修改场地页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		//String spaceId = pd.getString("spaceId");
		pd = this.getPageData();
		List<TrainingDistrictDto> res =  spaceManager.findAllDistrictNoPage();  
		
		TrainingAddressDto spaceInfo = spaceManager.getByIdAddress(spaceId);
		
		
		List<TrainingAddressDto> result =spaceManager.selectAllAddress(spaceId);
		
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd); 
		mv.addObject("spaceInfo", spaceInfo);				//当前场地对象
		mv.addObject("dis",res);							//全部区信息
		mv.addObject("time",result);						//场地对应时间段信息
		mv.setViewName("/train_space/space_edit");
		return mv;
	}
	
	
	/**
	 * 获取地图坐标页面
	 */
	@RequestMapping(value="/mapXY")
	public ModelAndView mapXY() throws Exception{
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/tools/mapXY");
		mv.addObject("pd", pd);
		return mv;
	}
}
