package com.tianfang.admin.controller.sport;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.business.dto.SportHomeDto;
import com.tianfang.business.dto.SportInformationRespDto;
import com.tianfang.business.service.SportHomeService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.freemarker.FreeMarkerUtil;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.PropertiesUtils;
import com.tianfang.news.dto.SportNewsInfoDto;
import com.tianfang.news.service.ISportNewsInfoService;

import freemarker.template.TemplateException;

/**
 * 
	 * 此类描述的是：静态页面生成
	 * @author: cwftalus@163.com
	 * @version: 2015年11月18日 下午6:13:56
 */
@Controller
@RequestMapping(value="/html")
public class HtmlController extends BaseController{

	@Autowired
	private SportHomeService sportHomeService;
	
	@Autowired
	private ISportNewsInfoService iSportNewsInfoService;
	
	
	@RequestMapping(value="/main")
	public ModelAndView main(){
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        mv.setViewName("/sport/html/index");
        return mv;
	}
	
	/**
	 * 
		 * 此方法描述的是：首页静态页面生成
		 * @author: cwftalus@163.com
		 * @version: 2015年11月19日 上午10:46:10
	 */
	@ResponseBody
	@RequestMapping(value="/index")
	public Response<String> index(){
		Response<String> response = new Response<String>();
		String freemarkUrl = PropertiesUtils.getProperty("freemark.url");
		String html = freemarkUrl +File.separator +"index.html";
		Map<String, Object> map = new HashMap<String, Object>();
		
		//首页信息
		SportHomeDto dto = new SportHomeDto();
		List<SportHomeDto> homeList = sportHomeService.findNRecords(dto, null);
		
		//新闻资讯
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("marked",DataStatus.ENABLED);
		List<SportNewsInfoDto> newsList = iSportNewsInfoService.findNewsByParams(params);
		
		map.put("homeList",homeList);
		map.put("newsList",newsList);

		try {
            FreeMarkerUtil.writeTo(getRequest().getSession().getServletContext(),
                    map, "/WEB-INF/view/template/", "index.htm", html);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TemplateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return response;
	}
}
