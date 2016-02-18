/**
 * 
 */
package com.tianfang.user.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.user.dto.SportUserReqDto;
import com.tianfang.user.dto.SportUserRespDto;
import com.tianfang.user.mapper.SportUserMapper;
import com.tianfang.user.pojo.SportUser;
import com.tianfang.user.pojo.SportUserExample;

/**		
 * <p>Title: SportInfoDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年11月16日 下午5:05:44	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年11月16日 下午5:05:44</p>
 * <p>修改备注：</p>
 */
@Repository
public class SportUserDao extends MyBatisBaseDao<SportUser>
{
    @Autowired
    @Getter
    private SportUserMapper mappers;
    
     /** 
     * (non-Javadoc)   
     * @see com.tianfang.common.mybatis.MyBatisBaseDao#getMapper()   
     */
    @Override
    public Object getMapper()
    {
        // TODO 添加方法注释
        return mappers;
    }
    
    public List<SportUserRespDto> findPage(SportUserReqDto sportUserReqDto,PageQuery page) {
        SportUserExample example = new SportUserExample();
//        SportUserExample.Criteria criteria = example.createCriteria();

        queryBySql(sportUserReqDto, example);
        
        if (null != page) {
            example.setOrderByClause(" create_time asc limit "+page.getStartNum() +"," + page.getPageSize());
        }
        
        return BeanUtils.createBeanListByTarget(mappers.selectByExample(example), SportUserRespDto.class);
    }
    
    public long count(SportUserReqDto sportUserReqDto) {
        SportUserExample example = new SportUserExample();
        
        queryBySql(sportUserReqDto, example);
        
//        SportUserExample.Criteria criteria = example.createCriteria();
//        if (null != sportUserReqDto.getUtype()) {
//            criteria.andUtypeEqualTo(sportUserReqDto.getUtype());
//        }
//        if (StringUtils.isNotBlank(sportUserReqDto.getEmail())) {
//            criteria.andEmailLike("%"+sportUserReqDto.getEmail()+"%");
//        }
//        if (StringUtils.isNotBlank(sportUserReqDto.getMobile())) {
//            criteria.andMobileLike("%"+sportUserReqDto.getMobile()+"%");
//        }
//        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mappers.countByExample(example);
    }
    
    public SportUserExample queryBySql(SportUserReqDto sportUserReqDto,SportUserExample example){
    	SportUserExample.Criteria criteria = example.createCriteria();
        if (null != sportUserReqDto.getUtype()) {
            criteria.andUtypeEqualTo(sportUserReqDto.getUtype());
        }
        
        if (null != sportUserReqDto.getTrainerLevel()) {
        	criteria.andTrainerLevelEqualTo(sportUserReqDto.getTrainerLevel());
        }
        if (StringUtils.isNotBlank(sportUserReqDto.getEmail())) {
          criteria.andEmailLike("%"+sportUserReqDto.getEmail()+"%");
        }
        if (StringUtils.isNotBlank(sportUserReqDto.getMobile())) {
            criteria.andMobileLike("%"+sportUserReqDto.getMobile()+"%");
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return example;
    }
    
    
    
    public List<SportUser> findByUtype(Integer utype) {
        SportUserExample example = new SportUserExample();
        SportUserExample.Criteria criteria = example.createCriteria();
        if (null != utype) {
            criteria.andUtypeEqualTo(utype);
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        List<SportUser> sportUsers = mappers.selectByExample(example);
        return sportUsers;
    }
    
    public List<SportUser> findByMoblieOrEmail(String mobile,String email) {
        SportUserExample example = new SportUserExample();
        SportUserExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(mobile)) {
            criteria.andMobileEqualTo(mobile);
        }
        if (StringUtils.isNotBlank(email)) {
            criteria.andEmailEqualTo(email);
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        List<SportUser> sportUsers = mappers.selectByExample(example);
        return sportUsers;
    }
//    public List<SportInformationRespDto> findPage(SportInformationReqDto sportInformationReqDto, PageQuery page) {
//        SportInformationExample example = new SportInformationExample();
//        SportInformationExample.Criteria criteria = example.createCriteria();
//        if (StringUtils.isNotBlank(sportInformationReqDto.getLabel())) {
//            criteria.andLabelLike("%"+sportInformationReqDto.getLabel()+"%");
//        }
//        if (StringUtils.isNotBlank(sportInformationReqDto.getTitle())) {
//            criteria.andTitleLike("%"+sportInformationReqDto.getTitle()+"%");
//        }
//        if (null != page) {
//            example.setOrderByClause(" create_time asc limit "+page.getStartNum() +"," + page.getPageSize());
//        }
//        criteria.andStatEqualTo(DataStatus.ENABLED);
//        List<SportInformationRespDto> result = BeanUtils.createBeanListByTarget(mappers.selectByExample(example), SportInformationRespDto.class);
//        return result;
//    }
//
//    public long count(SportInformationReqDto sportInformationReqDto) {
//        SportInformationExample example = new SportInformationExample();
//        SportInformationExample.Criteria criteria = example.createCriteria();
//        if (StringUtils.isNotBlank(sportInformationReqDto.getLabel())) {
//            criteria.andLabelLike("%"+sportInformationReqDto.getLabel()+"%");
//        }
//        if (StringUtils.isNotBlank(sportInformationReqDto.getTitle())) {
//            criteria.andTitleLike("%"+sportInformationReqDto.getTitle()+"%");
//        }
//        criteria.andStatEqualTo(DataStatus.ENABLED);
//        return mappers.countByExample(example);
//    }
//    
//    public Object save(SportInformation sportInformation) {
//        return mappers.insert(sportInformation);
//    }
//    
//    public SportInformation selectById(String id){
//        return mappers.selectByPrimaryKey(id);
//    }
}

