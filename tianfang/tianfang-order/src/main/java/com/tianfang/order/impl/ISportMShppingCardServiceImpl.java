package com.tianfang.order.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.order.dao.SportMShppingCarDao;
import com.tianfang.order.dto.SportMProductSkuDto;
import com.tianfang.order.dto.SportMProductSpecDto;
import com.tianfang.order.dto.SportMProductSpecValuesDto;
import com.tianfang.order.dto.SportMShoppingCartDto;
import com.tianfang.order.pojo.SportMProductSpec;
import com.tianfang.order.service.ISportMShppingCardService;

@Service
public class ISportMShppingCardServiceImpl implements ISportMShppingCardService {

	@Autowired
	private SportMShppingCarDao sportMShppingCarDao;
	
	@Override
	public List<SportMProductSkuDto> selectByProduct(SportMShoppingCartDto car) {
		return sportMShppingCarDao.selectByProduct(car);
	}

	@Override
	public boolean selectBySpec(String[] specId, String pid) {
		int i = 0;
		for (String spec : specId) {
			SportMProductSpecDto p_spec=  sportMShppingCarDao.selectBySpec(spec,pid);
			 if(p_spec!=null){
				i++;
			 }
		}
		if(i == specId.length){
			return true;
		}
		return false;
	}

	@Override
	public boolean selectBySpecValue(String[] specValueId,String pid) {
		int i= 0;
		for (String vspec : specValueId) {
			SportMProductSpecValuesDto v_spec = sportMShppingCarDao.selectBySpecValue(vspec,pid);
			if(v_spec!=null){
				i++;
			}
		}
		if(i == specValueId.length){
			return true;
		}
		return false;
	}
	
	public SportMProductSpecValuesDto selectBySpecValue(String specValueId,String pid) {
		SportMProductSpecValuesDto v_spec = sportMShppingCarDao.selectBySpecValue(specValueId,pid);
		return v_spec;
	}

	public SportMProductSpecDto selectBySpec(String specId, String pid){
		SportMProductSpecDto p_spec=  sportMShppingCarDao.selectBySpec(specId,pid);
		return p_spec;
	}
}
