/**
 * 
 */
package com.tianfang.train.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.tianfang.common.util.BeanUtils;
import com.tianfang.train.dto.TrainingAddressDto;
import com.tianfang.train.dto.TrainingAddressDtoX01;
import com.tianfang.train.dto.TrainingCourseAddressDto;
import com.tianfang.train.mapper.TrainingAddressMapper;
import com.tianfang.train.mapper.XTrainingAddressMapper;
import com.tianfang.train.pojo.TrainingAddress;
import com.tianfang.train.pojo.TrainingAddressExample;

/**
 * 
 * @author wk.s
 * @date 2015年9月1日
 */
@Repository
public class TrainingAddressDao {

	@Resource
	private XTrainingAddressMapper Xmapper;
	
	@Resource
	private TrainingAddressMapper mapper;
	
	private Logger log = Logger.getLogger(getClass());
	
	/**
	 * 通过id查询上课地址
	 * @param ID
	 * @return
	 * @2015年9月1日
	 */
	public TrainingAddress getAddrById(String id)
	{
		TrainingAddress ta = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>(1);
			map.put("id", id);
			ta = Xmapper.getAddrById(map);
		} catch (Exception e) {
			log.error("根据id查询上课地址发生异常", e);
		}
		
		return ta;
	}
	
	/**
	 * 查询Adress列表
	 */
	public List<TrainingAddress> find(TrainingAddressDto addressDto ){

		TrainingAddressExample example = new TrainingAddressExample();
		TrainingAddressExample.Criteria criteria = example.createCriteria();
		
		if (null != addressDto){
			if(!StringUtils.isEmpty(addressDto.getId())){
				criteria.andIdEqualTo(addressDto.getId());
			}
			if(!StringUtils.isEmpty(addressDto.getDistrictId())){
				criteria.andDistrictIdEqualTo(addressDto.getDistrictId());
			}
			if(!StringUtils.isEmpty(addressDto.getPlace())){
			    criteria.andPlaceEqualTo(addressDto.getPlace());
			}
		}
		criteria.andStatusEqualTo(1);
		return mapper.selectByExample(example);
	}

	public List<TrainingCourseAddressDto> getCourseAddressesTime(
			TrainingCourseAddressDto courseAddressDto) {
		return Xmapper.getCourseAddressesTime(courseAddressDto);
	}

	public TrainingAddressDto findById(String id) {
		TrainingAddressDto addressDto = null;
		TrainingAddress address =  mapper.selectByPrimaryKey(id);
		if(null != address){
			addressDto = BeanUtils.createBeanByTarget(address, TrainingAddressDto.class);
		}
		return addressDto;
	}
	
	/**
	 * 查询所有场地的信息
	 * @return
	 * @2015年9月8日
	 */
	public List<TrainingAddressDtoX01> getAllAddrs() {
		List<TrainingAddressDtoX01> lst = null;
		try {
			lst = Xmapper.getAllAddrs();
		} catch (Exception e) {
			log.error("查询所有场地信息发生异常", e);
		}
		return lst;
	}
}
