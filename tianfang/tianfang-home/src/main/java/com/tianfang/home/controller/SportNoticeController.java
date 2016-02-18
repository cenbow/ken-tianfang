package com.tianfang.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.business.dto.SportNoticeDto;
import com.tianfang.business.service.ISportNoticeService;

@Controller
public class SportNoticeController extends BaseController{

	@Autowired
	private ISportNoticeService sportNoticeService; 
	
	
	@RequestMapping("/viewNotice")
    public ModelAndView findPage(String noticeId) {
        ModelAndView mv = getModelAndView();
        SportNoticeDto data = sportNoticeService.getNoticeById(noticeId);
        mv.addObject("data", data);
        mv.setViewName("/team/notice/details");
        return mv;
    }

}
