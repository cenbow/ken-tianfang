package com.tianfang.order.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.dto.SportMSpecDto;
import com.tianfang.order.mapper.SportMSpecMapper;
import com.tianfang.order.pojo.SportMSpec;
import com.tianfang.order.pojo.SportMSpecExample;
import com.tianfang.order.pojo.SportMSpecExample.Criteria;
@Repository
public class SportMSpecDao extends MyBatisBaseDao<SportMSpec>{

	@Autowired
	@Getter
	private SportMSpecMapper mapper;
	
	@Autowired
	@Getter
	private RedisTemplate<String, Object> redisTemplate;
	
	public String save(SportMSpec spec) {
		spec.setId(UUID.randomUUID()+"");
		spec.setCreateTime(new Date());
		spec.setLastUpdateTime(new Date());
		spec.setStat(DataStatus.ENABLED);
		long stat =0;
		try {
			stat= mapper.insert(spec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(stat>0)
			return spec.getId();
		else
			return null;
	}

	public long edit(SportMSpec spec) {
		SportMSpec oldSpec = mapper.selectByPrimaryKey(spec.getId());
		checkUp(oldSpec,spec);
		spec.setStat(DataStatus.ENABLED);
		long stat = 0;
		try {
			stat=mapper.updateByPrimaryKey(spec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}

	/**
	 * 字段为空 替换
	 * @param oldSpec
	 * @param spec
	 */
	private void checkUp(SportMSpec oldSpec, SportMSpec spec) {
		if(StringUtils.isBlank(spec.getSpecName())){
			spec.setSpecName(oldSpec.getSpecName());
		}
		if(spec.getSpecOrder()==null){
			spec.setSpecOrder(oldSpec.getSpecOrder());
		}
		if(spec.getCreateTime()==null){
			spec.setCreateTime(oldSpec.getCreateTime());
		}
	}

	public long delete(String id) {
		SportMSpec spec = mapper.selectByPrimaryKey(id);
		spec.setStat(DataStatus.DISABLED);
		spec.setLastUpdateTime(new Date());
		long stat = 0;
		try {
			stat = mapper.updateByPrimaryKey(spec);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("删除出错"); 
		}
		return stat;
	}

	public List<SportMSpec> selectPageAll(SportMSpecDto sportMSpec,	PageQuery page) {
		SportMSpecExample example = new  SportMSpecExample();
		Criteria criteria = example.createCriteria();
		byCriteria(criteria,sportMSpec);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		if(page!=null){
			example.setOrderByClause(" create_time desc limit "+ page.getStartNum()+","+page.getPageSize());
		}
		return mapper.selectByExample(example);
	}

	/**
	 * 匹配查询对象
	 * @param criteria
	 * @param sportMSpec
	 */
	private void byCriteria(Criteria criteria, SportMSpecDto sportMSpec) {
		if(!StringUtils.isBlank(sportMSpec.getSpecName())){
			criteria.andSpecNameLike("%"+sportMSpec.getSpecName()+"%");
		}
		if(sportMSpec.getSpecOrder()!=null){
			criteria.andSpecOrderEqualTo(sportMSpec.getSpecOrder());
		}
		if(sportMSpec.getCreateTime()!=null){
			criteria.andCreateTimeEqualTo(sportMSpec.getCreateTime());
		}
	}

	public long countByCriteria(SportMSpecDto sportMSpec) {
		SportMSpecExample example = new  SportMSpecExample();
		Criteria criteria = example.createCriteria();
		byCriteria(criteria,sportMSpec);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.countByExample(example);
	}

	public SportMSpec selectById(String id) {
		return mapper.selectByPrimaryKey(id);
	}

	public List<SportMSpec> selectAll() {
		SportMSpecExample example = new  SportMSpecExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}
}
