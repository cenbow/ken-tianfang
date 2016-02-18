package com.tianfang.business.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.business.dto.SportAddressesDto;
import com.tianfang.business.mapper.SportAddressesMapper;
import com.tianfang.business.pojo.SportAddresses;
import com.tianfang.business.pojo.SportAddressesExample;
import com.tianfang.business.pojo.SportAddressesExample.Criteria;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;

@Repository
public class SportAddressDao extends MyBatisBaseDao<SportAddresses> {

	@Autowired
	@Getter
	private SportAddressesMapper mapper;

	public List<SportAddresses> getAddresses(String parendId) {
		SportAddressesExample example = new SportAddressesExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(parendId)){
			criteria.andParentIdEqualTo(parendId);	
		}
		return mapper.selectByExample(example);
	}

	public List<SportAddresses> getDistrict(SportAddresses addresses) {
		SportAddressesExample example = new SportAddressesExample();
		Criteria criteria = example.createCriteria();
		if (addresses.getId()!=null && !"".equals(addresses.getId())) {
			criteria.andIdEqualTo(addresses.getId());
		}
		if (addresses.getParentId()!=null && !"".equals(addresses.getParentId())) {
			criteria.andParentIdEqualTo(addresses.getParentId()+"");
		}
		if(addresses.getLevel()!=null && !"".equals(addresses.getLevel())){
			criteria.andLevelEqualTo(addresses.getLevel());
		}
		return mapper.selectByExample(example);
	}

	public SportAddressesDto selectById(Integer id) {
		SportAddressesExample example = new SportAddressesExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		List<SportAddresses> lis= mapper.selectByExample(example);
		if(lis.size()>0){
			return BeanUtils.createBeanByTarget(lis.get(0), SportAddressesDto.class);
		}
		return null;
	}
}
