package com.tianfang.train.dao;

import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.TrainingTimeDistrictDto;
import com.tianfang.train.mapper.TimeDistrictExMapper;
import com.tianfang.train.mapper.TimeDistrictMapper;
import com.tianfang.train.pojo.TimeDistrict;
import com.tianfang.train.pojo.TimeDistrictExample;
import com.tianfang.train.pojo.TimeDistrictExample.Criteria;

@Repository
public class TrainingTimeDistrictDao {
	
	@Autowired
	@Getter
	private TimeDistrictMapper mappers;
	
	@Autowired
	@Getter
	private TimeDistrictExMapper exMapper;

	/**
	 * 区域时间段信息 
	 * @param timeDistrictDto
	 * @param changeToPageQuery
	 * @return
	 */
	public List<TimeDistrict> findAllTimeDistrict(TrainingTimeDistrictDto timeDistrictDto, PageQuery changeToPageQuery) {
		TimeDistrictExample example = new TimeDistrictExample();
		Criteria criteria = example.createCriteria();
		byParameter(timeDistrictDto,criteria);
		if(timeDistrictDto.getStatus()==null){
			criteria.andStatusEqualTo(DataStatus.ENABLED);
		}
		if(changeToPageQuery!=null){
			if(changeToPageQuery.getPageSize()>0){
				example.setOrderByClause(" create_time desc limit "+changeToPageQuery.getStartNum() +"," + changeToPageQuery.getPageSize());
			}
		}
		return mappers.selectByExample(example);
	}
	/**
	 * 获取时间段信息
	 * @param spaceDto
	 * @return
	 */
	public long count(TrainingTimeDistrictDto timeDistrictDto) {
		TimeDistrictExample example = new TimeDistrictExample();
		Criteria criteria = example.createCriteria();
		byParameter(timeDistrictDto,criteria);
		criteria.andStatusEqualTo(DataStatus.ENABLED);
		return mappers.countByExample(example);
	}
	/**
	 * 根据参数参数查询方法
	 * @param timeDistrictDto
	 * @param criteria
	 */
	public void byParameter(TrainingTimeDistrictDto timeDistrictDto,Criteria criteria){
		if(!StringUtils.isEmpty(timeDistrictDto.getDayOfWeek())){
			criteria.andDayOfWeekEqualTo(timeDistrictDto.getDayOfWeek());
		}
		if(!StringUtils.isEmpty(timeDistrictDto.getStartTime())){
			criteria.andStartTimeEqualTo(timeDistrictDto.getStartTime());
		}
		if(!StringUtils.isEmpty(timeDistrictDto.getEndTime())){
			criteria.andEndTimeEqualTo(timeDistrictDto.getEndTime());
		}
	}
	
	/**
	 * 根据主键ID获取
	 * @param id
	 * @return
	 */
	public TimeDistrict findById(String id){
		return mappers.selectByPrimaryKey(id);
	}
	/**
	 * 新增时间段
	 * @param timeDistrictDto
	 */
	public void saveTimeDistrict(TrainingTimeDistrictDto timeDistrictDto) {
		TimeDistrict timeDistrict = BeanUtils.createBeanByTarget(timeDistrictDto,TimeDistrict.class);
		timeDistrict.setStatus(DataStatus.ENABLED);
		timeDistrict.setCreateTime(new Date().getTime()/1000);
		mappers.insert(timeDistrict);
	}
	/**
	 * 修改
	 * @param timeDistrict
	 */
	public void update(TimeDistrict timeDistrict) {
		mappers.updateByPrimaryKey(timeDistrict);
	}
	/**
	 * 修改时间段
	 * @param timeDistrictDto
	 */
	public void updateTimeDrict(TrainingTimeDistrictDto timeDistrictDto) {
		TimeDistrict timeDistrict = BeanUtils.createBeanByTarget(timeDistrictDto,TimeDistrict.class);
		mappers.updateByPrimaryKey(timeDistrict);
	}
	
	/**
	 * @author YIn
	 * @time:2015年9月7日 下午2:46:36
	 */
	public List<TimeDistrict> findTimeDistrictBySpaceId(String spaceId) {
		return exMapper.findTimeDistrictBySpaceId(spaceId);
	}
}
