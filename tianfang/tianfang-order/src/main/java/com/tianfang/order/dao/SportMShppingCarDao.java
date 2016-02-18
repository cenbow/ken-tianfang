package com.tianfang.order.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.dto.SportMProductSkuDto;
import com.tianfang.order.dto.SportMProductSpecDto;
import com.tianfang.order.dto.SportMProductSpecValuesDto;
import com.tianfang.order.dto.SportMShoppingCartDto;
import com.tianfang.order.mapper.SportMProductSkuMapper;
import com.tianfang.order.mapper.SportMProductSpecMapper;
import com.tianfang.order.mapper.SportMProductSpecValuesMapper;
import com.tianfang.order.mapper.SportMShppingCarExMapper;
import com.tianfang.order.pojo.SportMProductSku;
import com.tianfang.order.pojo.SportMProductSpec;
import com.tianfang.order.pojo.SportMProductSpecExample;
import com.tianfang.order.pojo.SportMProductSpecValues;
import com.tianfang.order.pojo.SportMProductSpecValuesExample;
import com.tianfang.order.pojo.SportMProductSpecExample.Criteria;
/**
 * 购物车
 * @author Administrator
 *
 */
@Repository
public class SportMShppingCarDao{
	
	@Autowired
	private SportMProductSpecValuesMapper vspecmapper;
	@Autowired
	private SportMProductSpecMapper specMapper;
	@Autowired
	private SportMProductSkuMapper skuMapper;
	@Autowired
	private SportMShppingCarExMapper mapper;

	public List<SportMProductSkuDto> selectByProduct(SportMShoppingCartDto car) {
		List<SportMProductSku> lis = mapper.selectByProduct(car);
		if(lis.size()>0){
			return BeanUtils.createBeanListByTarget(lis, SportMProductSkuDto.class);
		}
		return null;
	}

	public SportMProductSpecDto selectBySpec(String spec, String pid) {
		SportMProductSpecExample example = new SportMProductSpecExample();
		Criteria criteria= example.createCriteria();
		criteria.andSpecIdEqualTo(spec);
		criteria.andProductSkuIdEqualTo(pid);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		List<SportMProductSpec> lis = specMapper.selectByExample(example);
		if(lis.size()>0){
			return BeanUtils.createBeanByTarget(lis.get(0), SportMProductSpecDto.class);
		}
		return null;
	}

	public SportMProductSpecValuesDto selectBySpecValue(String vspec, String pid) {
		SportMProductSpecValuesExample example = new SportMProductSpecValuesExample();
		com.tianfang.order.pojo.SportMProductSpecValuesExample.Criteria criteria = example.createCriteria();
		criteria.andSpecValuesIdEqualTo(vspec);
		criteria.andProductSkuIdEqualTo(pid);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		List<SportMProductSpecValues> lis= vspecmapper.selectByExample(example);
		if(lis.size()>0){
			return BeanUtils.createBeanByTarget(lis.get(0),SportMProductSpecValuesDto.class);
		}
		return null;
	}

}
