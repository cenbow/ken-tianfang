package com.tianfang.order.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.dao.SportMShippingAddressDao;
import com.tianfang.order.dto.SportMShippingAddressDto;
import com.tianfang.order.pojo.SportMShippingAddress;
import com.tianfang.order.service.ISportMShippingAddressService;

@Service
public class SportMShippingAddressImpl implements ISportMShippingAddressService{

	@Autowired
	private SportMShippingAddressDao sportMShippingAddressDao;
	
	public PageResult<SportMShippingAddressDto> findPage(SportMShippingAddressDto sportMShippingAddressDto,PageQuery page){
		List<SportMShippingAddressDto> results = sportMShippingAddressDao.findPage(sportMShippingAddressDto, page);
		page.setTotal(sportMShippingAddressDao.count(sportMShippingAddressDto));
		return new PageResult<>(page, results);
	}

	@Override
	public long save(SportMShippingAddressDto shippingAdd) {
		long stat =0l;
		if(StringUtils.isBlank(shippingAdd.getId())){
			//选中默认地址状态  一个用户保证默认地址为一条
			if(shippingAdd.getMaker() == 1){
				List<SportMShippingAddress> lis = sportMShippingAddressDao.selectByUidOrMarker(shippingAdd.getMaker(),shippingAdd.getUserId());
				for (SportMShippingAddress smsa : lis) {
					smsa.setMaker(DataStatus.DISABLED);
					sportMShippingAddressDao.updateByPrimaryKey(smsa);
				}
			}
			SportMShippingAddress shi= BeanUtils.createBeanByTarget(shippingAdd, SportMShippingAddress.class);
			stat= sportMShippingAddressDao.insert(shi);
		}else{
			SportMShippingAddress shi = sportMShippingAddressDao.selectByPrimaryKey(shippingAdd.getId());
			shi.setStat(DataStatus.DISABLED);
			stat= sportMShippingAddressDao.updateByPrimaryKey(shi);
		}
		return stat;
	}

	@Override
	public List<SportMShippingAddressDto> findAll(SportMShippingAddressDto shipping) {
		List<SportMShippingAddressDto> lis_shipping= sportMShippingAddressDao.findPage(shipping, null);
		return lis_shipping;
	}

	@Override
	public long delete(String id) {
		long stat = 0;
		String[] ids = id.split(",");
		for (String d : ids) {
			SportMShippingAddress addr = sportMShippingAddressDao.selectByPrimaryKey(d);
			addr.setStat(DataStatus.DISABLED);
			stat = sportMShippingAddressDao.updateByPrimaryKeySelective(addr);
			if(stat<1){
				return stat;
			}
		}
		return stat;
	}
}
