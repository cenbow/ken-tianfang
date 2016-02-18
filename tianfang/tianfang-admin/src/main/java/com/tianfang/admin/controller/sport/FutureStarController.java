package com.tianfang.admin.controller.sport;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
import com.tianfang.business.dto.SportFutureStarBlogDto;
import com.tianfang.business.dto.SportFutureStarDto;
import com.tianfang.business.service.ISportFutureStarBlogService;
import com.tianfang.business.service.ISportFutureStarService;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.user.dto.SportUserRespDto;
import com.tianfang.user.service.ISportUserService;

/**
 * 未来之星后台管理
 * 
 * @author xiang_wang
 *
 * 2015年11月24日上午11:04:33
 */
@Controller
@RequestMapping(value = "/sport/star")
public class FutureStarController extends BaseController{

	protected static final Log logger = LogFactory.getLog(FutureStarController.class);
	
	@Autowired
	private ISportFutureStarBlogService iSportFutureStarBlogService;
	
	@Autowired
	private ISportFutureStarService starService;
	
	@Autowired
	private ISportUserService iSportUserService;
	
	/**
	 * 分页查询
	 * @param newsDto
	 * @param page
	 * @return
	 * @author xiang_wang
	 * 2015年11月24日上午11:06:02
	 */
	@RequestMapping(value="/findPage")
	public ModelAndView findPage(SportFutureStarDto params, ExtPageQuery page){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageQuery query = page.changeToPageQuery();
		PageResult<SportFutureStarDto> result= starService.findFutureStarByParams(params, query.getCurrPage(), query.getPageSize());		
		mv.addObject("pageList", result);
		mv.addObject("params",params);		
		mv.setViewName("/sport/star/list");
		return mv;
	}
	
	/**
	 * 
	 * findPage：教练博文列表
	 * @param sportFutureStarBlogDto
	 * @param page
	 * @return
	 * @exception	
	 * @author Administrator
	 * @date 2015年11月27日 下午5:04:31
	 */
	@RequestMapping(value="/blog/findPage")
	public ModelAndView findPage (SportFutureStarBlogDto sportFutureStarBlogDto, ExtPageQuery page) {
	    ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageResult<SportFutureStarBlogDto> result = iSportFutureStarBlogService.findPage(sportFutureStarBlogDto, page.changeToPageQuery());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (SportFutureStarBlogDto sportFutureStarBlog : result.getResults()) {
            sportFutureStarBlog.setCreateDate(sdf.format(sportFutureStarBlog.getCreateTime()));
            if (StringUtils.isNotBlank(sportFutureStarBlog.getUserId())) {
                SportUserRespDto sportUserRespDto = iSportUserService.getSportUser(sportFutureStarBlog.getUserId());
                if (StringUtils.isNotBlank(sportUserRespDto.getCname())) {
                    sportFutureStarBlog.setUserId(sportUserRespDto.getCname());
                }               
            }
        }
        Integer utype = 2;//2为教练类型
        List<SportUserRespDto> sportUserRespDtos = iSportUserService.findByUType(utype);
        SportFutureStarBlogDto sportfutureStarBlogDto = BeanUtils.createBeanByTarget(sportFutureStarBlogDto, SportFutureStarBlogDto.class);        
        mv.addObject("sportUserRespDtos", sportUserRespDtos);
        mv.addObject("pageList", result);
        mv.addObject("newsDto",sportfutureStarBlogDto);      
        mv.setViewName("/sport/blog/list");
        return mv;
	}
	
	/**
	 * 
	 * openAddView：打开新增教练博文页面
	 * @return
	 * @exception	
	 * @author Administrator
	 * @date 2015年11月28日 上午10:58:48
	 */
	@RequestMapping(value = "/blog/openAddView")
	public ModelAndView openAddView () {
	    logger.info("打开新增教练博文页面");
	    ModelAndView mv = this.getModelAndView();
	    PageData pd = new PageData();
	    pd = this.getPageData();
	    Integer utype = 2;//2为教练类型
	    List<SportUserRespDto> sportUserRespDtos = iSportUserService.findByUType(utype);
	    mv.addObject("page", sportUserRespDtos);
	    mv.setViewName("/sport/blog/add");
	    mv.addObject("mg", "save");
	    mv.addObject("pd", pd);
	    return mv;
	}
	
	/**
	 * 
	 * openEditView：打开编辑教练博文页面
	 * @return
	 * @exception	
	 * @author Administrator
	 * @date 2015年11月28日 下午1:31:20
	 */
	@RequestMapping(value = "/blog/openEditView")
	public ModelAndView openEditView() {
	    logger.info("打开编辑教练博文页面");
	    ModelAndView mv = this.getModelAndView();
	    PageData pd = new PageData();
	    pd = this.getPageData();
	    String id = pd.getString("id");
	    Integer utype = 2;//2为教练类型
	    SportFutureStarBlogDto sportFutureStarBlogDto = iSportFutureStarBlogService.findById(id);
	    List<SportUserRespDto> sportUserRespDtos = iSportUserService.findByUType(utype);
        mv.addObject("page", sportUserRespDtos);
	    mv.addObject("pd", pd);
        mv.addObject("data", sportFutureStarBlogDto);
        mv.setViewName("/sport/blog/edit");
        return mv;	    
	}
	
	/**
	 * 
	 * save：新增博文
	 * @param sportFutureStarBlogDto
	 * @return
	 * @exception	
	 * @author Administrator
	 * @date 2015年11月28日 上午11:56:35
	 */
	@RequestMapping(value = "/blog/save")
	@ResponseBody
	public Map<String, Object> save(SportFutureStarBlogDto sportFutureStarBlogDto) {
	    Integer result = (Integer)iSportFutureStarBlogService.save(sportFutureStarBlogDto);
	    if (result == 1) {
	        return MessageResp.getMessage(true, "新增成功");
	    }
	    return MessageResp.getMessage(false, "新增失败");
	}
	
	/**
	 * 
	 * edit：编辑博文
	 * @param sportFutureStarBlogDto
	 * @return
	 * @exception	
	 * @author Administrator
	 * @date 2015年11月28日 下午1:34:55
	 */
	@RequestMapping(value = "/blog/edit")
	@ResponseBody
	public Map<String, Object> edit(SportFutureStarBlogDto sportFutureStarBlogDto) {
	    Integer result = (Integer)iSportFutureStarBlogService.edit(sportFutureStarBlogDto);
	    if (result == 1) {
	        return MessageResp.getMessage(true, "编辑成功");
	    }
	    return MessageResp.getMessage(false, "编辑失败");
	}
	
	/**
	 * 
	 * delete：删除博文
	 * @param ids
	 * @return
	 * @exception	
	 * @author Administrator
	 * @date 2015年11月28日 下午1:56:47
	 */
	@RequestMapping("/blog/delete")
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        if(ids == null || "".equals(ids)){
            return MessageResp.getMessage(false, "请求参数不能为空~") ;
        }
        iSportFutureStarBlogService.delete(ids);
        return MessageResp.getMessage(true, "删除成功！");
    }
	
	/**
	 * 保存未来之星对象
	 * @param dto
	 * @return
	 * @throws IOException
	 * @author xiang_wang
	 * 2015年11月24日上午11:25:54
	 */
	@ResponseBody
	@RequestMapping(value="/save")
	public  Map<String, Object> insert(SportFutureStarDto dto) throws IOException{
		try {
			int stat = starService.saveFutureStar(dto);
			if(stat != 1){
				return MessageResp.getMessage(false,"对不起,保存错误~");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return MessageResp.getMessage(false, e.getMessage());
		}
		
		return MessageResp.getMessage(true,"添加成功~");
	}
	
	/**
	 * 打开分配页面位置页面
	 * @return
	 * @author xiang_wang
	 * 2015年11月24日上午11:16:13
	 */
	@RequestMapping(value="/openPagePosition")
    public ModelAndView openPagePosition(){
        logger.info("打开分配页面位置页面");
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageData pd = new PageData();
        pd = this.getPageData();
        String id = pd.getString("id");
        SportFutureStarDto dto = starService.getFutureStarById(id);//根据ID读取
        try {
            mv.setViewName("/sport/star/page_position");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
            mv.addObject("datas", dto);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }                       
        return mv;
    }
	
	/**
	 * 更改前端页面显示位置
	 * @param id
	 * @param positionId
	 * @return
	 * @author xiang_wang
	 * 2015年11月24日上午11:19:41
	 */
	@RequestMapping(value="/pageRanking")
	@ResponseBody
	public Map<String, Object> pageRanking(String id,Integer positionId){
	    if(StringUtils.isBlank(id)){
            return MessageResp.getMessage(false, "请求参数不能为空~") ;
        }
	    Integer result = starService.pageRanking(id, positionId);
	    if (result == 1) {
	        return MessageResp.getMessage(true, "操作成功~");
	    }
	    return MessageResp.getMessage(false, "操作失败~") ;
	}
	
	/**
	 * 根据id删除(可批量删除)
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2015年11月24日上午11:20:47
	 */
	@ResponseBody
	@RequestMapping(value="/delecte")
	public Map<String, Object> delecte(String id){
		if(StringUtils.isBlank(id)){
			return MessageResp.getMessage(false, "请求参数不能为空~") ;
		}
		int status = starService.deleteFutureStar(id);
		if (status != 1){
			return MessageResp.getMessage(false, "删除失败~");
		}
		return MessageResp.getMessage(true, "删除成功~");
	}
	
	/**
	 * 更新未来之星对象
	 * @param dto
	 * @return
	 * @throws IOException
	 * @author xiang_wang
	 * 2015年11月24日上午11:23:04
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public Map<String, Object> update(SportFutureStarDto dto) throws IOException{
		try {
			int stat = starService.updateFutureStar(dto);
			if(stat != 1){
				return MessageResp.getMessage(false, "对不起,更新失败~");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return MessageResp.getMessage(false, e.getMessage());
		}
		
		return MessageResp.getMessage(true, "修改成功~");
	}
	
	/**
	 * 去新增页面
	 * @return
	 * @author xiang_wang
	 * 2015年11月24日上午11:28:59
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logger.info("去新增新闻页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		try {
			mv.setViewName("/sport/star/add");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 去修改页面
	 * @return
	 * @throws Exception
	 * @author xiang_wang
	 * 2015年11月24日上午11:29:07
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit() throws Exception{
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		pd = this.getPageData();
		//修改资料
		String id = pd.getString("id");

		SportFutureStarDto dto = starService.getFutureStarById(id);//根据ID读取
	
		mv.setViewName("/sport/star/edit");
		mv.addObject("pd", pd);
		mv.addObject("data", dto);
		
		return mv;
	}
}