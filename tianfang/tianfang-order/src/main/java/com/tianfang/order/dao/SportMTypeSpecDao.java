package com.tianfang.order.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.elasticsearch.index.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.Getter;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.dto.SportMSpecDto;
import com.tianfang.order.dto.SportMSpecValuesDto;
import com.tianfang.order.dto.SportMTypeSpecDto;
import com.tianfang.order.dto.SportTypeSpecExDto;
import com.tianfang.order.mapper.SportMTypeSpecExMapper;
import com.tianfang.order.mapper.SportMTypeSpecMapper;
import com.tianfang.order.pojo.SportMTypeSpec;
import com.tianfang.order.pojo.SportMTypeSpecExample;
import com.tianfang.order.pojo.SportMTypeSpecExample.Criteria;

@Repository
public class SportMTypeSpecDao extends MyBatisBaseDao<SportMTypeSpec>{

	@Getter
	@Autowired
	private SportMTypeSpecMapper mapper;

	@Autowired
	private SportMTypeSpecExMapper mapperEx;
	
	public long save(SportMTypeSpec sportMTypeSpec) {
		sportMTypeSpec.setId(UUID.randomUUID()+"");
		sportMTypeSpec.setCreateTime(new Date());
		sportMTypeSpec.setLastUpdateTime(new Date());
		sportMTypeSpec.setStat(DataStatus.ENABLED);
		long stat = 0;
		try {
			stat = mapper.insert(sportMTypeSpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}

	public List<SportTypeSpecExDto> selectTypeSpec(SportTypeSpecExDto spexDto) {
		return mapperEx.selectTypeSpec(spexDto);
	}
	
	public long countTypeSpec(SportTypeSpecExDto spexDto) {
		return mapperEx.countTypeSpec(spexDto);
	}

	public long delete(String id) {
		SportMTypeSpec old = mapper.selectByPrimaryKey(id);
		old.setStat(DataStatus.DISABLED);
		long stat = 0;
		try {
			stat = mapper.updateByPrimaryKey(old);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}
	
	public List<SportMSpecDto> findSpecByTypeId(String typeId){
		return mapperEx.findSpecByTypeId(typeId);
	}

	public List<SportMTypeSpec> selectAll() {
		SportMTypeSpecExample example = new SportMTypeSpecExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}

	public List<SportMTypeSpec> selectByCriteria(SportMTypeSpecDto mTypeSpec) {
		SportMTypeSpecExample example = new SportMTypeSpecExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(mTypeSpec.getSpecId())){
			criteria.andSpecIdEqualTo(mTypeSpec.getSpecId());
		}
		if(StringUtils.isNotBlank(mTypeSpec.getTypeId())){
			criteria.andTypeIdEqualTo(mTypeSpec.getTypeId());
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}
	
}
