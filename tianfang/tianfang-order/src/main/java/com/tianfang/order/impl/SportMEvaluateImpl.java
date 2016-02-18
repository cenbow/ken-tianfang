package com.tianfang.order.impl;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.order.dao.SportMEvaluateDao;
import com.tianfang.order.dao.SportMProductSkuDao;
import com.tianfang.order.dao.SportMProductSpuDao;
import com.tianfang.order.dto.SportMEvaluateDto;
import com.tianfang.order.pojo.SportMEvaluate;
import com.tianfang.order.pojo.SportMProductSku;
import com.tianfang.order.pojo.SportMProductSpu;
import com.tianfang.order.service.ISportMEvaluateService;

@Service
public class SportMEvaluateImpl implements ISportMEvaluateService{

	@Autowired
	private SportMEvaluateDao sportMEvaluateDao;
	
	@Autowired
	private SportMProductSpuDao sportMProductSpuDao;
	
	@Autowired
	private SportMProductSkuDao sportMProductSkuDao;
	
	public Integer saveEvaluate(SportMEvaluateDto sportMEvaluateDto) {
		SportMEvaluate sportMEvaluate = BeanUtils.createBeanByTarget(sportMEvaluateDto, SportMEvaluate.class);
		SportMProductSku sportMProductSku = sportMProductSkuDao.selectByPrimaryKey(sportMEvaluate.getProductSkuId()); 
		SportMProductSpu sportMProductSpu = sportMProductSpuDao.selectByPrimaryKey(sportMProductSku.getProductId());
		sportMEvaluateDao.insert(sportMEvaluate);
		SportMEvaluateDto sportmEvaluateDto = new SportMEvaluateDto();
		sportmEvaluateDto.setProductSkuId(sportMEvaluate.getProductSkuId());
		List<SportMEvaluate> sportMEvaluates = sportMEvaluateDao.getAllEvaluate(sportmEvaluateDto);
		sportmEvaluateDto.setEvaluateStatus(DataStatus.ENABLED);
		List<SportMEvaluate> praise = sportMEvaluateDao.getAllEvaluate(sportmEvaluateDto);
		sportmEvaluateDto.setEvaluateStatus(DataStatus.DEALS_ORDER);
		List<SportMEvaluate> medium = sportMEvaluateDao.getAllEvaluate(sportmEvaluateDto);
		sportmEvaluateDto.setEvaluateStatus(DataStatus.MANAGERTYPE);
		List<SportMEvaluate> bad = sportMEvaluateDao.getAllEvaluate(sportmEvaluateDto);
		long countStar = sportMEvaluateDao.countStar(sportMEvaluate.getProductSkuId());
		if (sportMEvaluates.size()>0) {
			sportMProductSpu.setCountEvaluate(sportMEvaluates.size());
			sportMProductSpu.setGoodEvaluate((int)((float)praise.size()/sportMEvaluates.size()*100));
			sportMProductSpu.setInEvaluate((int)((float)medium.size()/sportMEvaluates.size()*100)) ;
			sportMProductSpu.setBadEvaluate((int)((float)bad.size()/sportMEvaluates.size()*100));
			sportMProductSpu.setStar(Math.round((float)countStar/sportMEvaluates.size()));
		}
		return sportMProductSpuDao.updateByPrimaryKeySelective(sportMProductSpu);
	}
	
	public static void main(String[] args) {
		int a=1;
		int b=4;
		float c = (float)a/b; 
		System.out.println(c);
	}
	
}
