package com.tianfang.admin.controller.sport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.business.dto.SportAddressesDto;
import com.tianfang.business.service.ISportAddressesService;

/**
 * 区域信息
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/addresses")
public class SportAddressesColltroller extends BaseController {
	
	@Autowired
	private ISportAddressesService addressService;
	/**
	 * 获取上海所有区域
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getShInfo")
	public List<SportAddressesDto> getCriteria(String parendId){
		List<SportAddressesDto> lis = addressService.getAddresses(parendId);
		return lis;
	}
}
