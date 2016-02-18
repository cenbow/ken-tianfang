package com.tianfang.admin.controller.sport;

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
import com.tianfang.admin.dto.SportAdminDto;
import com.tianfang.admin.dto.SportMenuAuthRespDto;
import com.tianfang.admin.service.ISportAdminService;
import com.tianfang.admin.service.ISportMenuService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.digest.MD5Coder;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.JsonUtil;


/**
 * 
 * @author rkzhang
 *
 */
@Controller
@RequestMapping(value="/sport/admin")
public class SportMSAdminLoginController extends BaseController{

    protected static final Log logger = LogFactory.getLog(SportMSAdminLoginController.class);
    
    @Autowired
    private ISportAdminService iSportAdminService;
    
    @Autowired
    private ISportMenuService iSportMenuService;
    
    @RequestMapping(value = "findPage.do")
    public ModelAndView findpage(SportAdminDto sportAdminDto, ExtPageQuery page) {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        if (null != sportAdminDto.getAccount() && !sportAdminDto.equals("")) {
        	sportAdminDto.setAccount(sportAdminDto.getAccount());
        }
        if (null != sportAdminDto.getAccount() && sportAdminDto.equals("")) {
        	sportAdminDto.setAccount(null);
        }
        sportAdminDto.setStat(DataStatus.ENABLED);
        PageResult<SportAdminDto> result = iSportAdminService.findPage(sportAdminDto, page.changeToPageQuery());
        mv.setViewName("/course/ms_admin_list");
        mv.addObject("pageList", result);
        mv.addObject("newsDto", sportAdminDto);
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
    public Map<String, Object> save(SportAdminDto sportAdminDto) throws Exception{
        if (StringUtils.isBlank(sportAdminDto.getAccount()) || StringUtils.isBlank(sportAdminDto.getPassWord())) {
            return MessageResp.getMessage(false, "请求参数不能为空~");
        }
        String md5passwd = MD5Coder.encodeMD5Hex(sportAdminDto.getPassWord());
        sportAdminDto.setPassWord(md5passwd);
        Integer resObject =(Integer) iSportAdminService.save(sportAdminDto);
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
        SportAdminDto sportAdminDto = iSportAdminService.getAdmin(id);
        mv.setViewName("/course/ms_admin_edit");
        mv.addObject("datas", sportAdminDto);
        mv.addObject("pd", pd);
        return mv;
    }
    
    @RequestMapping(value = "getAdmin.do")
    @ResponseBody
    public Map<String, Object> getAdmin(String id) {
        SportAdminDto sportAdminDto = iSportAdminService.getAdmin(id);
        if(sportAdminDto == null){ return  MessageResp.getMessage(false, "没有此条信息");}
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("data", sportAdminDto);
        return result;
    }
    
    @RequestMapping(value = "edit.do")
    @ResponseBody
    public Map<String, Object> edit(SportAdminDto sportAdminDto) throws Exception{
        if (StringUtils.isBlank(sportAdminDto.getAccount())) {
            return MessageResp.getMessage(false, "请求参数不能为空~");
        }
        if (StringUtils.isBlank(sportAdminDto.getPassWord())) {
            sportAdminDto.setPassWord(null);
        }
        if (StringUtils.isNotBlank(sportAdminDto.getPassWord())) {
            String md5passwd = MD5Coder.encodeMD5Hex(sportAdminDto.getPassWord());
            sportAdminDto.setPassWord(md5passwd);
        }       
        Integer resObject =(Integer) iSportAdminService.edit(sportAdminDto);
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
        Integer resObject =(Integer) iSportAdminService.delAdIds(delAsIds);
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
        List<SportMenuAuthRespDto> trainingMenuAuthRespDtos = iSportMenuService.getAdminAuthById(userId);
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
        iSportAdminService.distributionAuth(adminId, menuIds);
        return MessageResp.getMessage(true, "授权成功~");
    }
}
