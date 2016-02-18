package com.tianfang.train.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import lombok.Getter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.TrainingDistrictDto;
import com.tianfang.train.dto.TrainingDistrictDtoX;
import com.tianfang.train.mapper.TrainingDistrictMapper;
import com.tianfang.train.mapper.XTrainingDistrictMapper;
import com.tianfang.train.pojo.TrainingDistrict;
import com.tianfang.train.pojo.TrainingDistrictExample;
import com.tianfang.train.pojo.TrainingDistrictExample.Criteria;

@Repository
public class TrainingDistrictDao {

	@Autowired
	@Getter
	public TrainingDistrictMapper mappers;
	
	@Resource
	private XTrainingDistrictMapper xmapper;
	private Logger log = Logger.getLogger(getClass());
	/**
	 * 区域信息列表
	 * @param districtDto
	 * @param changeToPageQuery
	 * @return
	 */
	public List<TrainingDistrict> findAllDistrict(
			TrainingDistrictDto districtDto, PageQuery pageQuery) {
		TrainingDistrictExample example = new TrainingDistrictExample();
		Criteria criteria = example.createCriteria();
		byParameter(districtDto,criteria);
		if(districtDto.getStatus() == null){
			criteria.andStatusEqualTo(DataStatus.ENABLED);
		}
		if(pageQuery != null && "".equals(pageQuery)){
			example.setOrderByClause(" create_time desc limit "+pageQuery.getStartNum() +"," + pageQuery.getPageSize());
		}
		return mappers.selectByExample(example);
	}

	/**
	 * 根据条件查询
	 */
	public void byParameter(TrainingDistrictDto districtDto,Criteria criteria ){
		if(!StringUtils.isEmpty(districtDto.getCity())){
			criteria.andCityEqualTo(districtDto.getCity());
		}
		if(!StringUtils.isEmpty(districtDto.getName())){
			criteria.andNameEqualTo(districtDto.getName());
		}
	}
	/**
	 * 查询当前总条数
	 * @param page
	 * @return
	 */
	public long count(TrainingDistrictDto districDto) {
		TrainingDistrictExample example = new TrainingDistrictExample();
		Criteria criteria = example.createCriteria();
		byParameter(districDto,criteria);
		criteria.andStatusEqualTo(DataStatus.ENABLED);
		return mappers.countByExample(example);
	}
	/**
	 * 根据id查询对象
	 * @param id
	 * @return
	 */
	public TrainingDistrict findById(String id) {
		return mappers.selectByPrimaryKey(id);
	}
	
	/**
	 * 查询所有有效的区域，比如普陀区、徐汇区
	 * @return
	 * @2015年9月2日
	 */
	public List<TrainingDistrictDtoX> getADistrict(){
		List<TrainingDistrictDtoX> lst = null;
		try {
			lst = xmapper.getADistrict();
		} catch (Exception e) {
			log.error("查询所有有效区域发生异常", e);
		}
		return lst;
	}

	/**
	 * 新增区域信息
	 * @param districtDto
	 */
	public int saveDistrict(TrainingDistrictDto districtDto) {
		TrainingDistrict trainingDistrict = BeanUtils.createBeanByTarget(districtDto, TrainingDistrict.class);
		trainingDistrict.setStatus(DataStatus.ENABLED);
		trainingDistrict.setCreateTime(new Date().getTime()/1000);
		try {
			mappers.insert(trainingDistrict);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}
	/**
	 * 删除
	 * @param district
	 */
	public void updateOrDel(TrainingDistrict district) {
		mappers.updateByPrimaryKey(district);
	}

	/**
	 * 根据主键id获取区域信息
	 * @param valueOf
	 * @return
	 */
	public TrainingDistrictDto getByDistrict(String id) {
		TrainingDistrict district = mappers.selectByPrimaryKey(id);
		TrainingDistrictDto districtDto = BeanUtils.createBeanByTarget(district, TrainingDistrictDto.class);
		return districtDto;
	}

	public void updateEdit(TrainingDistrictDto districtDto) {
		TrainingDistrict district = BeanUtils.createBeanByTarget(districtDto, TrainingDistrict.class);
		district.setStatus(DataStatus.ENABLED);
		district.setUpdateTime(new Date().getTime()/1000);
		mappers.updateByPrimaryKey(district);
	}
}
