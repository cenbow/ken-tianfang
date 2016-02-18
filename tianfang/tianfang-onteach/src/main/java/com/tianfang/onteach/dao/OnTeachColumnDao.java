package com.tianfang.onteach.dao;

import java.util.List;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.onteach.dto.OnlineTeachColumnDto;
import com.tianfang.onteach.mapper.SportOnlineTeachColumnMapper;
import com.tianfang.onteach.pojo.SportOnlineTeachColumn;
import com.tianfang.onteach.pojo.SportOnlineTeachColumnExample;

@Repository
public class OnTeachColumnDao extends MyBatisBaseDao<SportOnlineTeachColumn> {

	@Getter
	@Autowired
	private SportOnlineTeachColumnMapper mapper;
	
	/**
	 * 
		 * 此方法描述的是：新增
		 * @author: cwftalus@163.com
		 * @version: 2016年1月11日 下午3:35:41
	 */
	public void insert(OnlineTeachColumnDto dto){
		SportOnlineTeachColumn record = BeanUtils.createBeanByTarget(dto, SportOnlineTeachColumn.class);
		mapper.insertSelective(record);
	}
	/**
	 * 
		 * 此方法描述的是：修改
		 * @author: cwftalus@163.com
		 * @version: 2016年1月11日 下午3:36:28
	 */
	public void update(OnlineTeachColumnDto dto){
		SportOnlineTeachColumn record = BeanUtils.createBeanByTarget(dto, SportOnlineTeachColumn.class);
		mapper.updateByPrimaryKeySelective(record);
	}
	
	
	public List<SportOnlineTeachColumn> findOnTeachByParams(OnlineTeachColumnDto dto, PageQuery page) {
		SportOnlineTeachColumnExample example = new SportOnlineTeachColumnExample();
		SportOnlineTeachColumnExample.Criteria criteria = example.createCriteria();
        queryBySql(dto, criteria);
        example.setOrderByClause(" create_time asc limit "+page.getStartNum() +"," + page.getPageSize());
        List<SportOnlineTeachColumn> results = mapper.selectByExample(example);  
        return results;
	}
	
	public int countByParams(OnlineTeachColumnDto dto) {
		SportOnlineTeachColumnExample example = new SportOnlineTeachColumnExample();
		SportOnlineTeachColumnExample.Criteria criteria = example.createCriteria();
		queryBySql(dto, criteria);
		return mapper.countByExample(example);
	}
	
	private void queryBySql(OnlineTeachColumnDto dto,SportOnlineTeachColumnExample.Criteria criteria) {
		if(dto.getStat()!=null){
			criteria.andStatEqualTo(dto.getStat());	
		}

		if(!StringUtils.isEmpty(dto.getColumnName())){
			criteria.andColumnNameEqualTo(dto.getColumnName());
		}
   	}
	public List<SportOnlineTeachColumn> findColumnsBy(OnlineTeachColumnDto dto) {
		SportOnlineTeachColumnExample example = new SportOnlineTeachColumnExample();
		SportOnlineTeachColumnExample.Criteria criteria = example.createCriteria();
        queryBySql(dto, criteria);
        List<SportOnlineTeachColumn> results = mapper.selectByExample(example);  
        return results;
	}
}
