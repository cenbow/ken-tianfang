package com.tianfang.admin.controller.sport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.business.dto.SportTeamTypeDto;
import com.tianfang.business.service.ISportTeamTypeService;

/**
 * 运动类型
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/teamType")
public class SportTeamTypeColltroller extends BaseController {
	
	@Autowired
	private ISportTeamTypeService sportTeamType;
	
	@ResponseBody
	@RequestMapping("/getTeamTypeAll")
	public List<SportTeamTypeDto> getAllTeamType(){
		return sportTeamType.getAllTeamType();
	}
}
