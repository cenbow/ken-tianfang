package com.tianfang.train.dao;

import java.util.List;

import lombok.Getter;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.train.dto.AddAssistantReqDto;
import com.tianfang.train.dto.AssistantDto;
import com.tianfang.train.dto.AssistantSpaceTimeRespDto;
import com.tianfang.train.mapper.AssistantMappingExMapper;
import com.tianfang.train.mapper.AssistantMappingMapper;
import com.tianfang.train.mapper.TrainingAssistantExMapper;
import com.tianfang.train.mapper.TrainingAssistantMapper;
import com.tianfang.train.pojo.AssistantMapping;
import com.tianfang.train.pojo.AssistantMappingExample;
import com.tianfang.train.pojo.TrainingAssistant;
import com.tianfang.train.pojo.TrainingAssistantExample;

@Repository
public class AssistantDao extends MyBatisBaseDao<TrainingAssistant>{
	
    @Autowired
    @Getter
    private TrainingAssistantMapper mapper;
    
    @Autowired
    @Getter
    private TrainingAssistantExMapper exMapper;
    
    @Autowired
    @Getter
    private AssistantMappingMapper mappingMapper;
    
    @Autowired
    @Getter
    private AssistantMappingExMapper exMappingMapper;
    
    public TrainingAssistant findByAccountAndPassword(String account,String password){
    	
    	TrainingAssistantExample example = new TrainingAssistantExample();
    	TrainingAssistantExample.Criteria criteria = example.createCriteria();
    	if (StringUtils.isNotBlank(password)) {
    	    criteria.andPasswordEqualTo(password);
    	}
    	if (StringUtils.isNotBlank(account)) {
    	    criteria.andAccountEqualTo(account);
    	}        
        criteria.andStatusEqualTo(DataStatus.ENABLED);
        List<TrainingAssistant> results = mapper.selectByExample(example);
        
        return CollectionUtils.isEmpty(results) ? null : results.get(0);
    }
    public AssistantDto findByAssistantDto(AddAssistantReqDto assistantDto) {
        TrainingAssistantExample example = new TrainingAssistantExample();
        TrainingAssistantExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(assistantDto.getAccount())) {
            criteria.andAccountEqualTo(assistantDto.getAccount());
        }
        if (StringUtils.isNotBlank(assistantDto.getName())){
            criteria.andNameEqualTo(assistantDto.getName());
        }
        if (StringUtils.isNotBlank(assistantDto.getPassword())) {
            criteria.andPasswordEqualTo(assistantDto.getPassword());
        }
        criteria.andStatusEqualTo(DataStatus.ENABLED);
        List<TrainingAssistant> trainingAssistantList = mapper.selectByExample(example);
        List<AssistantDto> results = BeanUtils.createBeanListByTarget(trainingAssistantList, AssistantDto.class);
        return CollectionUtils.isEmpty(results) ? null : results.get(0);
    }
    
    public List<AssistantDto> findPage(AssistantDto assistantDto,PageQuery page) {
        return exMappingMapper.findAssistantByPage(assistantDto, page);
//        TrainingAssistantExample example = new TrainingAssistantExample();
//        TrainingAssistantExample.Criteria criteria = example.createCriteria();
//        if (StringUtils.isNotBlank(assistantDto.getAccount())) {
//            criteria.andAccountEqualTo(assistantDto.getAccount());
//        }
//        if (StringUtils.isNotBlank(assistantDto.getName())) {
//            criteria.andNameEqualTo(assistantDto.getName());
//        }
//        criteria.andStatusEqualTo(DataStatus.ENABLED);
//        if (page != null) {
//            example.setOrderByClause("create_time desc limit "
//                    + page.getStartNum() + ", " + page.getPageSize() + "");
//        }
//        List<TrainingAssistant> results = mapper.selectByExample(example);
//        List<AssistantDto> assistantDtos = BeanUtils.createBeanListByTarget(results, AssistantDto.class);
//        return CollectionUtils.isEmpty(assistantDtos) ? null : assistantDtos;
    }
    
    public long count(AssistantDto assistantDto){
        TrainingAssistantExample example = new TrainingAssistantExample();
        TrainingAssistantExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(assistantDto.getAccount())) {
            criteria.andAccountEqualTo(assistantDto.getAccount());
        }
        if (StringUtils.isNotBlank(assistantDto.getName())) {
            criteria.andNameEqualTo(assistantDto.getName());
        }
        criteria.andStatusEqualTo(DataStatus.ENABLED);
        return mapper.countByExample(example);
    }
    
    public long findAssistantByPageCount(AssistantDto assistantDto) {
        return exMappingMapper.findAssistantByPageCount(assistantDto);
    }
    
    public TrainingAssistant findById(String id) {
        TrainingAssistantExample example = new TrainingAssistantExample();
        TrainingAssistantExample.Criteria criteria = example.createCriteria();
        if (null != id) {
            criteria.andIdEqualTo(id);
        }
        criteria.andStatusEqualTo(DataStatus.ENABLED);
        List<TrainingAssistant> results = mapper.selectByExample(example);        
        return CollectionUtils.isEmpty(results) ? null :results.get(0);
    }
    
    public List<AssistantMapping> findByAssistantMappingByAssistantId(String assistantId){
    	AssistantMappingExample example = new AssistantMappingExample();
    	AssistantMappingExample.Criteria criteria = example.createCriteria();
    	criteria.andAssistantIdEqualTo(assistantId);
    	criteria.andStatusEqualTo(1);
    	List<AssistantMapping> results = mappingMapper.selectByExample(example);
    	 
    	return results;
    }
    
    public Integer save(TrainingAssistant trainingAssistant) {
        return exMapper.save(trainingAssistant);
    }
    
    public List<AssistantSpaceTimeRespDto> findAssistantAddressTimeById(String id) {
        return exMappingMapper.findAssistantAddressTimeById(id);
    }
}