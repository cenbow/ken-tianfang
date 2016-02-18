package com.tianfang.onteach.dao;

import java.util.List;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.onteach.dto.OnTeachDto;
import com.tianfang.onteach.mapper.SportOnlineTeachMapper;
import com.tianfang.onteach.mapper.SportOnlineTeachMapperEx;
import com.tianfang.onteach.pojo.SportOnlineTeach;
import com.tianfang.onteach.pojo.SportOnlineTeachExample;

@Repository
public class OnTeachDao extends MyBatisBaseDao<SportOnlineTeach> {

	@Getter
	@Autowired
	private SportOnlineTeachMapper mapper;
	
	@Getter
	@Autowired
	private SportOnlineTeachMapperEx mapperEx;
	
	/**
	 * 
		 * 此方法描述的是：新增
		 * @author: cwftalus@163.com
		 * @version: 2016年1月11日 下午3:35:41
	 */
	public void insert(OnTeachDto dto){
		SportOnlineTeach record = BeanUtils.createBeanByTarget(dto, SportOnlineTeach.class);
		mapper.insertSelective(record);
	}
	/**
	 * 
		 * 此方法描述的是：修改
		 * @author: cwftalus@163.com
		 * @version: 2016年1月11日 下午3:36:28
	 */
	public void update(OnTeachDto dto){
		SportOnlineTeach record = BeanUtils.createBeanByTarget(dto, SportOnlineTeach.class);
		mapper.updateByPrimaryKeySelective(record);
	}
	
	
	public List<SportOnlineTeach> findOnTeachByParams(OnTeachDto dto, PageQuery page) {
		SportOnlineTeachExample example = new SportOnlineTeachExample();
		SportOnlineTeachExample.Criteria criteria = example.createCriteria();
        queryBySql(dto, criteria);
        example.setOrderByClause(" create_time asc limit "+page.getStartNum() +"," + page.getPageSize());
        List<SportOnlineTeach> results = mapper.selectByExample(example);  
        return results;
	}
	
	public int countByParams(OnTeachDto dto) {
		SportOnlineTeachExample example = new SportOnlineTeachExample();
		SportOnlineTeachExample.Criteria criteria = example.createCriteria();
		queryBySql(dto, criteria);
		return mapper.countByExample(example);
	}
	
	private void queryBySql(OnTeachDto dto,SportOnlineTeachExample.Criteria criteria) {
		if(dto.getStat()!=null){
			criteria.andStatEqualTo(dto.getStat());	
		}
		if(!StringUtils.isEmpty(dto.getCourseName())){
			criteria.andCourseNameLike("%"+dto.getCourseName()+"%");
		}
		if(!StringUtils.isEmpty(dto.getColumnName())){
			criteria.andColumnNameEqualTo(dto.getColumnName());
		}
		
		if(!StringUtils.isEmpty(dto.getColumnId())){
			criteria.andColumnIdEqualTo(dto.getColumnId());
		}
		
		if(dto.getCourseType()!=null){
			criteria.andCourseTypeEqualTo(dto.getCourseType());
		}
   	}
	
	public void updateClickCount(String oId) {	
		mapperEx.updateClickCount(oId);
	}
	public int countcolumnNameByParams() {
		return mapperEx.countcolumnNameByParams();
	}
	public List<SportOnlineTeach> findOnTeachcolumnNameByParams(PageQuery page) {
		long startNum = page.getStartNum();
        List<SportOnlineTeach> results = mapperEx.selectByExample(Integer.valueOf(String.valueOf(startNum)), page.getPageSize());  
        return results;
	}
}
