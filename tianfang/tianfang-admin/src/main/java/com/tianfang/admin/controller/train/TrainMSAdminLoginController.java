package com.tianfang.admin.controller.train;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.tianfang.admin.controller.BaseController;
import com.tianfang.admin.controller.PageData;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.digest.MD5Coder;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.JsonUtil;
import com.tianfang.train.dto.TrainingAdminDto;
import com.tianfang.train.dto.TrainingMenuAuthRespDto;
import com.tianfang.train.dto.TrainingMenuRespDto;
import com.tianfang.train.service.ITrainAdminService;
import com.tianfang.train.service.ITrainMenuService;


/**
 * 
 * @author rkzhang
 *
 */
@Controller
@RequestMapping(value="/admin")
public class TrainMSAdminLoginController extends BaseController{

    protected static final Log logger = LogFactory.getLog(TrainMSAdminLoginController.class);
    
    @Autowired
    private ITrainAdminService iTrainAdminService;
    
    @Autowired
    private ITrainMenuService iTrainMenuService;
    
    @RequestMapping(value = "findPage.do")
    public ModelAndView findpage(TrainingAdminDto trainingAdminDto, ExtPageQuery page) {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        if (null != trainingAdminDto.getAccount() && !trainingAdminDto.equals("")) {
            trainingAdminDto.setAccount(trainingAdminDto.getAccount());
        }
        if (null != trainingAdminDto.getAccount() && trainingAdminDto.equals("")) {
            trainingAdminDto.setAccount(null);
        }
        trainingAdminDto.setStatus(DataStatus.ENABLED);
        PageResult<TrainingAdminDto> result = iTrainAdminService.findPage(trainingAdminDto, page.changeToPageQuery());
        mv.setViewName("/course/ms_admin_list");
        mv.addObject("pageList", result);
        mv.addObject("newsDto", trainingAdminDto);
        return mv;
    }
    
    @RequestMapping(value = "openAddView.do")
    public ModelAndView openAddView() {
        logger.info("打开新增用户页面");
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            mv.setViewName("/course/ms_admin_add");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }                       
        return mv;
    }
    
    @RequestMapping(value = "save.do")
    @ResponseBody
    public Map<String, Object> save(TrainingAdminDto trainingAdminDto) throws Exception{
        if (StringUtils.isBlank(trainingAdminDto.getAccount()) || StringUtils.isBlank(trainingAdminDto.getPassWord())) {
            return MessageResp.getMessage(false, "请求参数不能为空~");
        }
        String md5passwd = MD5Coder.encodeMD5Hex(trainingAdminDto.getPassWord());
        trainingAdminDto.setPassWord(md5passwd);
        Integer resObject =(Integer) iTrainAdminService.save(trainingAdminDto);
        if (resObject == 0) {
            return MessageResp.getMessage(false, "用户名已经存在~");
        }
        if (resObject == 1) {
            return MessageResp.getMessage(true, "新增成功~");
        }
        return MessageResp.getMessage(false, "未知错误~");
    }
    
    @RequestMapping(value = "openEditView.do")
    public ModelAndView openEditView() {
        logger.info("打开新增用户页面");
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageData pd = new PageData();
        pd = this.getPageData();
        String id = pd.getString("id");
        TrainingAdminDto trainingAdminDto = iTrainAdminService.getAdmin(id);
        mv.setViewName("/course/ms_admin_edit");
        mv.addObject("datas", trainingAdminDto);
        mv.addObject("pd", pd);
        return mv;
    }
    
    @RequestMapping(value = "getAdmin.do")
    @ResponseBody
    public Map<String, Object> getAdmin(String id) {
        TrainingAdminDto trainingAdminDto = iTrainAdminService.getAdmin(id);
        if(trainingAdminDto == null){ return  MessageResp.getMessage(false, "没有此条信息");}
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("data", trainingAdminDto);
        return result;
    }
    
    @RequestMapping(value = "edit.do")
    @ResponseBody
    public Map<String, Object> edit(TrainingAdminDto trainingAdminDto) throws Exception{
        if (StringUtils.isBlank(trainingAdminDto.getAccount())) {
            return MessageResp.getMessage(false, "请求参数不能为空~");
        }
        if (StringUtils.isBlank(trainingAdminDto.getPassWord())) {
            trainingAdminDto.setPassWord(null);
        }
        if (StringUtils.isNotBlank(trainingAdminDto.getPassWord())) {
            String md5passwd = MD5Coder.encodeMD5Hex(trainingAdminDto.getPassWord());
            trainingAdminDto.setPassWord(md5passwd);
        }       
        Integer resObject =(Integer) iTrainAdminService.edit(trainingAdminDto);
        if (resObject == 0) {
            return MessageResp.getMessage(false, "该用户不存在~");
        }
        if (resObject == 1) {
            return MessageResp.getMessage(true, "编辑成功~");
        }
        return MessageResp.getMessage(false, "未知错误~");
    }
    
    @RequestMapping(value = "delAdIds.do")
    @ResponseBody
    public Map<String, Object> delAdIds(String  delAsIds) throws Exception{
        if (StringUtils.isBlank(delAsIds)) {
            return MessageResp.getMessage(false, "请求参数不能为空~");
        }
        Integer resObject =(Integer) iTrainAdminService.delAdIds(delAsIds);
        if (resObject == 0) {
            return MessageResp.getMessage(false, "无此条记录~");
        }
        if (resObject == 1) {
            return MessageResp.getMessage(true, "删除成功~");
        }
        return MessageResp.getMessage(false, "未知错误~");
    }
    
    
    /**
     * 
     * auth：打开菜单权限
     * @param userId 用户id
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年10月29日 下午2:07:32
     */
    @RequestMapping(value = "auth.do")
    public ModelAndView auth(String userId) {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());   
        //zTree 格式
        String json = "["
                       +"{"
                       +   "\"MENU_ICON\": \"icon-desktop\", "
                       +   "\"id\": \"90\","    //此参数必须有
                       +   "\"name\": \"报名管理\", "  //此参数必须有
                       +   "\"MENU_ORDER\":\"1\","
                       +   "\"MENU_TYPE\": \"2\"," 
                       +   "\"MENU_URL\": \"#\"," 
                       +   "\"PARENT_ID\": \"0\"," 
                       +   "\"checked\": true," //此参数必须有
                       +   "\"parentMenu\": null," 
                       +   "\"nodes\": [" //此参数必须有
                       +        "{"
                       +           "\"MENU_ICON\": \"\"," 
                       +            "\"id\": \"91\"," //此参数必须有
                       +           "\"name\": \"报名信息\", "//此参数必须有
                       +           "\"MENU_ORDER\": \"2\","
                       +           "\"MENU_TYPE\": \"2\"," 
                       +            "\"MENU_URL\": \"role.do\"," 
                       +            "\"PARENT_ID\": \"1\"," 
                       +            "\"checked\": true," //此参数必须有
                       +            "\"parentMenu\": null," 
                       +            "\"nodes\": [ ]," //此参数必须有
                       +            "\"target\": \"\""
                       +        "}]," 
                       +    "\"target\": \"\""
                       +"}]";
        List<TrainingMenuAuthRespDto> trainingMenuAuthRespDtos = iTrainMenuService.getAdminAuthById(userId);
        String jsonStr = JsonUtil.getJsonStr(trainingMenuAuthRespDtos);
        jsonStr = jsonStr.replaceAll("menuTitle", "name");
        logger.info("jsonStr="+jsonStr);
        mv.setViewName("/course/ms_admin_auth");
        mv.addObject("userId", userId);
        mv.addObject("zTreeNodes", jsonStr);
        return mv;
    }
    
    @RequestMapping(value="distributionAuth.do")
    @ResponseBody
    public Map<String, Object> distributionAuth(String adminId,String menuIds) {
        iTrainAdminService.distributionAuth(adminId, menuIds);
        return MessageResp.getMessage(true, "授权成功~");
    }
}
