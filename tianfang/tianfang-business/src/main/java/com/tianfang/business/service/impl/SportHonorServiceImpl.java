/**
 * 
 */
package com.tianfang.business.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.admin.dao.SportAdminDao;
import com.tianfang.admin.pojo.SportAdmin;
import com.tianfang.business.dao.SportHonorDao;
import com.tianfang.business.dao.SportTeamDao;
import com.tianfang.business.dto.SportHonorReqDto;
import com.tianfang.business.dto.SportHonorRespDto;
import com.tianfang.business.pojo.SportHonor;
import com.tianfang.business.pojo.SportTeam;
import com.tianfang.business.service.ISportHonorService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.user.dao.SportUserDao;
import com.tianfang.user.pojo.SportUser;

/**		
 * <p>Title: SportHonorServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年11月13日 下午5:25:08	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年11月13日 下午5:25:08</p>
 * <p>修改备注：</p>
 */
@Service
public class SportHonorServiceImpl implements ISportHonorService
{
    @Autowired
    private SportHonorDao sportHonorDao;
    
    @Autowired
    private SportAdminDao sportAdminDao;
    
    @Autowired
    private SportTeamDao sportTeamDao;
    
    @Autowired
    private SportUserDao sportUserDao;
    
    public PageResult<SportHonorRespDto> findPage(SportHonorReqDto sportHonorReqDto,PageQuery page) {
        List<SportHonorRespDto> results = sportHonorDao.findPage(sportHonorReqDto, page);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (results.size()>0) {
            for (SportHonorRespDto sportHonorRespDto : results) {
                sportHonorRespDto.setCreateDate(sdf.format(sportHonorRespDto.getCreateTime()));
                if (sportHonorRespDto.getPublisherType() == DataStatus.DISABLED ) {
                    SportAdmin sportAdmin = sportAdminDao.selectByPrimaryKey(sportHonorRespDto.getPublishPeople());
                    if (null != sportAdmin ) {
                        sportHonorRespDto.setPublishPeople(sportAdmin.getAccount());
                    }else {
                        sportHonorRespDto.setPublishPeople(null);
                    }
                    
                }
                if (sportHonorRespDto.getPublisherType() == DataStatus.ENABLED) {
                    SportUser sportUser = sportUserDao.selectByPrimaryKey(sportHonorRespDto.getPublishPeople());
                    if (null != sportUser) {
                        sportHonorRespDto.setPublishPeople(sportUser.getNickName());
                    }else {
                        sportHonorRespDto.setPublishPeople(null);
                    }                    
                }
                SportTeam sportTeam = sportTeamDao.selectByPrimaryKey(sportHonorRespDto.getTeamId());
                if (null != sportTeam) {
                    sportHonorRespDto.setTeamId(sportTeam.getName());
                }else {
                    sportHonorRespDto.setTeamId(null);
                }
            }
        }
        page.setTotal(sportHonorDao.count(sportHonorReqDto));
        return new PageResult<SportHonorRespDto>(page,results);
    }
    
    public Integer save(SportHonorReqDto sportHonorReqDto) {
        SportHonor sportHonor = BeanUtils.createBeanByTarget(sportHonorReqDto, SportHonor.class);
        sportHonor.setHonorStatus(DataStatus.DISABLED);
        sportHonor.setStat(DataStatus.ENABLED);
        sportHonor.setCreateTime(new Date());
        return sportHonorDao.insert(sportHonor);        
    }
    
    
    public SportHonorRespDto findById(String id) {
        SportHonor sportHonor = sportHonorDao.selectByPrimaryKey(id);
        SportHonorRespDto sportHonorRespDto = BeanUtils.createBeanByTarget(sportHonor, SportHonorRespDto.class);
        return sportHonorRespDto;
    }
    
    public Integer update(SportHonorReqDto sportHonorReqDto) {
        SportHonor sportHonor = BeanUtils.createBeanByTarget(sportHonorReqDto, SportHonor.class);
        sportHonor.setLastUpdateTime(new Date());
        return sportHonorDao.updateByPrimaryKeySelective(sportHonor);
    }
    
    public Object delete(String ids) {
        String[] idStr = ids.split(",");
        if (idStr.length>0) {
            for (String id:idStr) {
                SportHonor sportHonor = sportHonorDao.selectByPrimaryKey(id);
                sportHonor.setStat(DataStatus.DISABLED);
                sportHonorDao.updateByPrimaryKeySelective(sportHonor);
            }
        }else {
            SportHonor sportHonor = sportHonorDao.selectByPrimaryKey(ids);
            sportHonor.setStat(DataStatus.DISABLED);
            sportHonorDao.updateByPrimaryKeySelective(sportHonor);
        }
        return null;
    }
    
    public Object examine(String id,Integer honorStatus) {
        SportHonor sportHonor = sportHonorDao.selectByPrimaryKey(id);
        sportHonor.setHonorStatus(honorStatus);
        return sportHonorDao.updateByPrimaryKeySelective(sportHonor);
    }
}

