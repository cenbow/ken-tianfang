package com.tianfang.admin.controller.sport;

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
import com.tianfang.business.dto.SportHomeMenuDto;
import com.tianfang.business.enums.ActivityTypeEnums;
import com.tianfang.business.enums.MenuTypeEnums;
import com.tianfang.business.enums.VideoTypeEnums;
import com.tianfang.business.service.ISportHomeMenuService;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;

@Controller
@RequestMapping(value ="/sport/home/menu")
public class SportHomeMenuManageController extends BaseController{

protected static final Log logger = LogFactory.getLog(SportHonorManageController.class);
    
    @Autowired
    private ISportHomeMenuService iSportHomeMenuService;
    
    @RequestMapping("/findPage")
    public ModelAndView findPage(SportHomeMenuDto dto,ExtPageQuery page){
    	ModelAndView mv = this.getModelAndView(this.getSessionUserId());
    	PageResult<SportHomeMenuDto> results= iSportHomeMenuService.findPage(dto, page.changeToPageQuery());
    	 mv.setViewName("/sport/menu/list");
         mv.addObject("pageList", results);
         mv.addObject("params", dto);
         mv.addObject("alls", iSportHomeMenuService.findAll());
         mv.addObject("menuTypes", MenuTypeEnums.getValus());
         mv.addObject("acTypes", ActivityTypeEnums.getValus());
         mv.addObject("videoTypes", VideoTypeEnums.getValus());
         return mv;
    }
    
    @RequestMapping("/openAddView")
    public ModelAndView openAddView(){
        logger.info("打开新增菜单页面");
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("/sport/menu/add");
        mv.addObject("menuTypes", MenuTypeEnums.getValus());
        mv.addObject("acTypes", ActivityTypeEnums.getValus());
        mv.addObject("videoTypes", VideoTypeEnums.getValus());
        return mv;
    }
    
    /**
     * ajax查询上级菜单
     * @param type
     * @return
     * @author xiang_wang
     * 2015年12月22日下午3:18:39
     */
    @RequestMapping("/selectMenumType")
    @ResponseBody
    public List<SportHomeMenuDto> selectMenumType(Integer menuType){
    	List<SportHomeMenuDto> sportHomeMenuDtos = null;
    	if (null != menuType && menuType.intValue() > 1 ){
    		sportHomeMenuDtos = iSportHomeMenuService.findByMenuType(menuType - 1);
    	}
    	return sportHomeMenuDtos;
    }
    
    /**
     * 更新菜单排序
     * @param sportHomeMenuDto
     * @return
     * @author xiang_wang
     * 2015年12月23日下午3:36:44
     */
    @RequestMapping("/updateOrder")
    @ResponseBody
    public Map<String, Object> updateOrder(SportHomeMenuDto sportHomeMenuDto){
    	Integer result = iSportHomeMenuService.edit(sportHomeMenuDto);
    	if (result == 1) {
            return MessageResp.getMessage(true,"更新菜单排序成功");
        }
        return MessageResp.getMessage(false,"更新菜单排序失败");	
    }

    /**
     * 新增菜单
     * @param sportHomeMenuDto
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Map<String, Object> save(SportHomeMenuDto sportHomeMenuDto) {
    	if (null != sportHomeMenuDto.getMenuType() && sportHomeMenuDto.getMenuType().intValue() == MenuTypeEnums.ONE.getIndex()) {
    		sportHomeMenuDto.setParentId("0");
    	}
        Integer result = iSportHomeMenuService.save(sportHomeMenuDto);
        if (result == 1) {
            return MessageResp.getMessage(true,"新增成功");
        }
        return MessageResp.getMessage(false,"新增失败");
    }
    
    @RequestMapping("/openEditView")
    public ModelAndView openEditView () {
        logger.info("打开编辑菜单页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String id = pd.getString("id");
        SportHomeMenuDto sportHomeMenuDto = iSportHomeMenuService.findById(id);     
        if (null != sportHomeMenuDto.getParentId() && !"0".equals(sportHomeMenuDto.getParentId().trim()) && null != sportHomeMenuDto.getMenuType() && sportHomeMenuDto.getMenuType().intValue() > 1){
        	List<SportHomeMenuDto> sportHomeMenuDtos = iSportHomeMenuService.findByMenuType(sportHomeMenuDto.getMenuType() - 1);
        	mv.addObject("parents", sportHomeMenuDtos);
        }
        mv.setViewName("/sport/menu/edit");
        mv.addObject("data", sportHomeMenuDto);
        mv.addObject("menuTypes", MenuTypeEnums.getValus());
        mv.addObject("acTypes", ActivityTypeEnums.getValus());
        mv.addObject("videoTypes", VideoTypeEnums.getValus());
        
        return mv;
    }
    
    /**
     * 编辑菜单
     * @param sportHomeMenuDto
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map<String, Object> edit(SportHomeMenuDto sportHomeMenuDto) {
    	if (null != sportHomeMenuDto.getMenuType() && sportHomeMenuDto.getMenuType().intValue() == MenuTypeEnums.ONE.getIndex()) {
    		sportHomeMenuDto.setParentId("0");
    	}
    	Integer result = iSportHomeMenuService.edit(sportHomeMenuDto);
    	if (result == 1) {
            return MessageResp.getMessage(true,"编辑成功");
        }
        return MessageResp.getMessage(false,"编辑失败");		
    }
    
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        if(ids == null || "".equals(ids)){
            return MessageResp.getMessage(false, "请求参数不能为空~") ;
        }
        iSportHomeMenuService.delete(ids);
        return MessageResp.getMessage(true, "删除成功！");
    }
}
