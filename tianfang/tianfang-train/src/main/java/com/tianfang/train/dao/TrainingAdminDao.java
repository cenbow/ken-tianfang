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
import com.tianfang.train.dto.TrainingAdminDto;
import com.tianfang.train.mapper.TrainingAdminMapper;
import com.tianfang.train.pojo.TrainingAdmin;
import com.tianfang.train.pojo.TrainingAdminExample;

@Repository
public class TrainingAdminDao extends MyBatisBaseDao<TrainingAdmin>{

    @Autowired
    @Getter
    private TrainingAdminMapper mapper;
    
    /**
     * 
     * findByAccount：根据用户名查找用户
     * @param account 用户名
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年9月1日 下午6:48:46
     */
//    public TrainingAdmin findByAccount(String account) {
//        TrainingAdminExample example = new TrainingAdminExample();
//        TrainingAdminExample.Criteria criteria = example.createCriteria();
//        criteria.andAccountEqualTo(account);
//        criteria.andStatusEqualTo(DataStatus.ENABLED);
//        List<TrainingAdmin> results = mapper.selectByExample(example);
//        return CollectionUtils.isEmpty(results) ? null : results.get(0);
//    }
    
    public TrainingAdmin findByAccountPwd(String account,String password) {
        TrainingAdminExample example = new TrainingAdminExample();
        TrainingAdminExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(account)) {
            criteria.andAccountEqualTo(account);
        }
        if(StringUtils.isNotBlank(password)) {
            criteria.andPassWordEqualTo(password);
        }
        criteria.andStatusEqualTo(DataStatus.ENABLED);
        List<TrainingAdmin> results = mapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results.get(0);
    }
    
    
    public List<TrainingAdmin> findPage(TrainingAdminDto trainingAdminDto,PageQuery page) {
        TrainingAdminExample example = new TrainingAdminExample();
        TrainingAdminExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(trainingAdminDto.getAccount())) {
            criteria.andAccountLike("%"+trainingAdminDto.getAccount()+"%");
        }
//        if (StringUtils.isNotBlank(assistantDto.getName())) {
//            criteria.andNameEqualTo(assistantDto.getName());
//        }
        criteria.andStatusEqualTo(DataStatus.ENABLED);
        if (page != null) {
            example.setOrderByClause("create_time desc limit "
                    + page.getStartNum() + ", " + page.getPageSize() + "");
        }
        List<TrainingAdmin> results = mapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results;
    }
    
    public long count(TrainingAdminDto trainingAdminDto) {
        TrainingAdminExample example = new TrainingAdminExample();
        TrainingAdminExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(trainingAdminDto.getAccount())) {
            criteria.andAccountLike("%"+trainingAdminDto.getAccount()+"%");
        }
        criteria.andStatusEqualTo(DataStatus.ENABLED);
        return mapper.countByExample(example);
    }
}
