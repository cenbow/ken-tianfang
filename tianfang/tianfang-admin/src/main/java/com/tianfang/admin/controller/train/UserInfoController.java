/**
 * 
 */
package com.tianfang.admin.controller.train;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.tianfang.admin.controller.BaseController;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.JsonUtil;
import com.tianfang.train.dto.TrainingAddressDto;
import com.tianfang.train.dto.TrainingCourseDtoX;
import com.tianfang.train.dto.TrainingTimeDistrictDto;
import com.tianfang.train.dto.TrainingUserDto;
import com.tianfang.train.service.ITrainUserService;


/**
 * 用户管理(后端)
 * @author wk.s
 * @date 2015年10月22日
 */
@Controller
@RequestMapping("/user")
public class UserInfoController extends BaseController {

	@Resource
	private ITrainUserService iTrainUserService;
	
	/**
	 * 查询所有上课信息
	 * 
	 * @param permissionDTO
	 * @return
	 */
	@RequestMapping("/findpage.do")
	public ModelAndView findpage(TrainingUserDto query, ExtPageQuery page) {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<TrainingUserDto> result = iTrainUserService.findPage(query, page.changeToPageQuery());
		mv.addObject("params", query);
		mv.addObject("pageList", result);
		mv.setViewName("/train/userInfo_list");
		return mv;
	}

	/**
	 * 查找课程（用于搜索框）
	 * @param trainingCourseDtoX
	 * @return
	 * @2015年10月24日
	 */
	@ResponseBody
	@RequestMapping("/findCourseName.do")
	public Response<List<TrainingCourseDtoX>> findCourseName(TrainingCourseDtoX trainingCourseDtoX) {
		Response<List<TrainingCourseDtoX>> response = new Response<List<TrainingCourseDtoX>>();
		List<TrainingCourseDtoX> trainingCourseDtoXs = iTrainUserService.findCourseName(trainingCourseDtoX);
		response.setStatus(DataStatus.HTTP_SUCCESS);
		response.setData(trainingCourseDtoXs);
		return response;
	}
	
	/**
	 * 查找场地（用于搜索框）
	 * @param trainingAddressDto
	 * @return
	 * @2015年10月24日
	 */
	@ResponseBody
	@RequestMapping("/findAddress.do")
	public Response<List<TrainingAddressDto>> findAddress(TrainingAddressDto trainingAddressDto) {
		Response<List<TrainingAddressDto>> response = new Response<List<TrainingAddressDto>>();
		List<TrainingAddressDto> results = iTrainUserService.findAddress(trainingAddressDto);
		response.setStatus(DataStatus.HTTP_SUCCESS);
		response.setData(results);
		return response;
	}
	
	/**
	 * 查找时间段（用于搜索框）
	 * @param trainingTimeDistrictDto
	 * @return
	 * @2015年10月24日
	 */
	@ResponseBody
	@RequestMapping("/findAddressTime.do")
	public Response<List<TrainingTimeDistrictDto>> findAddressTime(TrainingTimeDistrictDto trainingTimeDistrictDto) {
		Response<List<TrainingTimeDistrictDto>> response = new Response<List<TrainingTimeDistrictDto>>();
		List<TrainingTimeDistrictDto> results = iTrainUserService.findAddressTime(trainingTimeDistrictDto);
		response.setStatus(DataStatus.HTTP_SUCCESS);
		response.setData(results);
		return response;
	}
	
	/***
	 * 查询所有用户记录信息
	 * @param query
	 * @param page
	 * @return
	 */
	@RequestMapping("/findAllUser.do")
	public ModelAndView findAllUser(TrainingUserDto query, ExtPageQuery page) {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<TrainingUserDto> result = iTrainUserService.findAllUser(query, page.changeToPageQuery());
		mv.addObject("params", query);
		mv.addObject("pageList", result);
		mv.setViewName("/train/useRecord_list");
		return mv;
	}
	
	/***
	 * 得到用户记录信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/getUser.do")
	public ModelAndView getUser(String id) {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		TrainingUserDto trainingUserDto = iTrainUserService.getUser(id);
		mv.addObject("result", trainingUserDto);
		mv.setViewName("/train/user_edit");
		return mv;
	}

	/***
	 * 修改用户记录信息
	 * @param trainingUserDto
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateUser.do")
	public String updateUser(TrainingUserDto trainingUserDto) throws Exception {
		Map<Object, Object> result = new HashMap<Object, Object>();
		trainingUserDto.setBirthday(JsonUtil.parseDateStr(trainingUserDto.getBirthdayStr(), "yyyy-MM-dd"));
		Integer resObject = (Integer) iTrainUserService.updateUser(trainingUserDto);
		if (resObject == 0) {
			result.put("status", DataStatus.HTTP_FAILE);
			result.put("msg", "无此条记录~");
		}
		if (resObject == 1) {
			result.put("status", DataStatus.HTTP_SUCCESS);
			result.put("msg", "编辑成功");
		}
		if (resObject == 2) {
			result.put("status", DataStatus.HTTP_FAILE);
			result.put("msg", "编辑失败");
		}
		String re = JSONObject.toJSONString(result);
		return re;
	}
	
	/***
	 * 删除用户记录信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteUser.do")
	public String deleteUser(String id) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Integer resObject = (Integer) iTrainUserService.deleteUser(id);
		if (resObject == 0) {
			result.put("status", DataStatus.HTTP_FAILE);
			result.put("msg", "无此条记录~");
		}
		if (resObject == 1) {
			result.put("status", DataStatus.HTTP_SUCCESS);
			result.put("msg", "删除成功");
		}
		if (resObject == 2) {
			result.put("status", DataStatus.HTTP_FAILE);
			result.put("msg", "删除失败");
		}
		String re = JSONObject.toJSONString(result);
		return re;
	}
	
	/***
	 * 后台管理用户记录查询所在地
	 * @param trainingUserDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findLocation.do")
	public Response<List<TrainingUserDto>> findLocation(TrainingUserDto trainingUserDto) {
		Response<List<TrainingUserDto>> response = new Response<List<TrainingUserDto>>();
		List<TrainingUserDto> result = iTrainUserService.findLocation(trainingUserDto);
		response.setStatus(DataStatus.HTTP_SUCCESS);
		response.setData(result);
		return response;
	}
	
}
