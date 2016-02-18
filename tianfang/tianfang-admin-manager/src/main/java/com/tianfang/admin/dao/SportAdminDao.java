package com.tianfang.admin.dao;

import java.util.List;

import lombok.Getter;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tianfang.admin.dto.SportAdminDto;
import com.tianfang.admin.mapper.SportAdminMapper;
import com.tianfang.admin.pojo.SportAdmin;
import com.tianfang.admin.pojo.SportAdminExample;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;

@Repository
public class SportAdminDao extends MyBatisBaseDao<SportAdmin>{

    @Autowired
    @Getter
    private SportAdminMapper mapper;
    
    /**
     * 
     * findByAccount：根据用户名查找用户
     * @param account 用户名
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年9月1日 下午6:48:46
     */
//    public SportAdmin findByAccount(String account) {
//        SportAdminExample example = new SportAdminExample();
//        SportAdminExample.Criteria criteria = example.createCriteria();
//        criteria.andAccountEqualTo(account);
//        criteria.andStatusEqualTo(DataStatus.ENABLED);
//        List<SportAdmin> results = mapper.selectByExample(example);
//        return CollectionUtils.isEmpty(results) ? null : results.get(0);
//    }
    
    public SportAdmin findByAccountPwd(String account,String password) {
        SportAdminExample example = new SportAdminExample();
        SportAdminExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(account)) {
            criteria.andAccountEqualTo(account);
        }
        if(StringUtils.isNotBlank(password)) {
            criteria.andPassWordEqualTo(password);
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        List<SportAdmin> results = mapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results.get(0);
    }
    
    
    public List<SportAdmin> findPage(SportAdminDto trainingAdminDto,PageQuery page) {
        SportAdminExample example = new SportAdminExample();
        SportAdminExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(trainingAdminDto.getAccount())) {
            criteria.andAccountLike("%"+trainingAdminDto.getAccount()+"%");
        }
//        if (StringUtils.isNotBlank(assistantDto.getName())) {
//            criteria.andNameEqualTo(assistantDto.getName());
//        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (page != null) {
            example.setOrderByClause("create_time desc limit "
                    + page.getStartNum() + ", " + page.getPageSize() + "");
        }
        List<SportAdmin> results = mapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results;
    }
    
    public long count(SportAdminDto trainingAdminDto) {
        SportAdminExample example = new SportAdminExample();
        SportAdminExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(trainingAdminDto.getAccount())) {
            criteria.andAccountLike("%"+trainingAdminDto.getAccount()+"%");
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.countByExample(example);
    }
}
