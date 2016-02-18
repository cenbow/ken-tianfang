package com.tianfang.train.dao;

import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.common.util.reflect.DynamicMethod;
import com.tianfang.train.mapper.TrainingAssistantAddressTimeMapper;
import com.tianfang.train.pojo.TrainingAssistantAddressTime;
import com.tianfang.train.pojo.TrainingAssistantAddressTimeExample;

@Repository
public class TrainingAssistantAddressTimeDao extends MyBatisBaseDao<TrainingAssistantAddressTime>{

    @Autowired
    @Getter
    private TrainingAssistantAddressTimeMapper mapper;
    
    /**
     * 重写基类的inseret方法
     */
    public int insert(TrainingAssistantAddressTime obj){
		Object id = DynamicMethod.invokeMethod(obj, "getId");
		if(id == null || StringUtils.isEmpty(((String)id).trim())){
			DynamicMethod.invokeMethod(obj, "setId", new Object[]{UUIDGenerator.getUUID()});
		}
		DynamicMethod.invokeMethod(obj, "setCreateTime", new Object[]{new Date().getTime()/1000});
		DynamicMethod.invokeMethod(obj, "setStatus", new Object[]{new Integer(DataStatus.ENABLED)});
		return (Integer)DynamicMethod.invokeMethod(getMapper(), "insert", new Object[]{obj});
	}
    
    /**
     * 重写基类的update方法
     */
    public int updateByPrimaryKey(TrainingAssistantAddressTime obj){
		DynamicMethod.invokeMethod(obj, "setUpdateTime", new Object[]{new Date().getTime()/1000});
		return (Integer)DynamicMethod.invokeMethod(getMapper(), "updateByPrimaryKey", new Object[]{obj});
	}
    
    /**
     * 
     * findByAccount：根据addressDistrictTimeId和assistantId查询信息
     * @param addressDistrictTimeId
     * @param assistantId
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年9月4日 下午5:46:55
     */
    public TrainingAssistantAddressTime findByAccount(String addressDistrictTimeId,String assistantId) {
        TrainingAssistantAddressTimeExample example = new TrainingAssistantAddressTimeExample();
        TrainingAssistantAddressTimeExample.Criteria criteria = example.createCriteria();
        if (null != addressDistrictTimeId) {
            criteria.andAddressDistrictTimeIdEqualTo(addressDistrictTimeId);
        }
        if (null != assistantId) {
            criteria.andAssistantIdEqualTo(assistantId);
        }
        criteria.andStatusEqualTo(DataStatus.ENABLED);
        List<TrainingAssistantAddressTime> results = mapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results.get(0);
    }
    
    public TrainingAssistantAddressTime findByAccountAll(String addressDistrictTimeId,String assistantId) {
        TrainingAssistantAddressTimeExample example = new TrainingAssistantAddressTimeExample();
        TrainingAssistantAddressTimeExample.Criteria criteria = example.createCriteria();
        if (null != addressDistrictTimeId) {
            criteria.andAddressDistrictTimeIdEqualTo(addressDistrictTimeId);
        }
        if (null != assistantId) {
            criteria.andAssistantIdEqualTo(assistantId);
        }
        List<TrainingAssistantAddressTime> results = mapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results.get(0);
    }
    public List<TrainingAssistantAddressTime> findByAssistantId(String assistantId) {
        TrainingAssistantAddressTimeExample example = new TrainingAssistantAddressTimeExample();
        TrainingAssistantAddressTimeExample.Criteria criteria = example.createCriteria();
        if (null != assistantId) {
            criteria.andAssistantIdEqualTo(assistantId);
        }
        criteria.andStatusEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }
    
    public List<TrainingAssistantAddressTime> findByAddAssId(String addressId,String assistantId) {
        TrainingAssistantAddressTimeExample example = new TrainingAssistantAddressTimeExample();
        TrainingAssistantAddressTimeExample.Criteria criteria = example.createCriteria();
        if (null != addressId) {
            criteria.andAddressIdEqualTo(addressId);
        }
        if (null != assistantId) {
            criteria.andAssistantIdEqualTo(assistantId);
        }
        criteria.andStatusEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }
    
//    public TrainingAdmin findByAccountPwd(String account,String password) {
//        TrainingAdminExample example = new TrainingAdminExample();
//        TrainingAdminExample.Criteria criteria = example.createCriteria();
//        if(StringUtils.isNotBlank(account)) {
//            criteria.andAccountEqualTo(account);
//        }
//        if(StringUtils.isNotBlank(password)) {
//            criteria.andPassWordEqualTo(password);
//        }
//        criteria.andStatusEqualTo(DataStatus.ENABLED);
//        List<TrainingAdmin> results = mapper.selectByExample(example);
//        return CollectionUtils.isEmpty(results) ? null : results.get(0);
//    }
}
