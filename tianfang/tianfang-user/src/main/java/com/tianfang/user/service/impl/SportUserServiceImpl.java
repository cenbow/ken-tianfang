/**
 * 
 */
package com.tianfang.user.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.user.dao.SportUserDao;
import com.tianfang.user.dto.SportUserReqDto;
import com.tianfang.user.dto.SportUserRespDto;
import com.tianfang.user.pojo.SportUser;
import com.tianfang.user.service.ISportUserService;

/**		
 * <p>Title: SportUserServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年11月18日 下午5:20:12	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年11月18日 下午5:20:12</p>
 * <p>修改备注：</p>
 */
@Service
public class SportUserServiceImpl implements ISportUserService
{
    @Autowired
    @Getter
    private SportUserDao sportUserDao;
    
    public PageResult<SportUserRespDto> findPage(SportUserReqDto sportUserReqDto, PageQuery page) {
        List<SportUserRespDto> result = sportUserDao.findPage(sportUserReqDto, page);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (SportUserRespDto sportUserRespDto : result) {
            sportUserRespDto.setCreateDate(sdf.format(sportUserRespDto.getCreateTime()));
        }
        page.setTotal(sportUserDao.count(sportUserReqDto));
        return new PageResult<SportUserRespDto>(page, result);
    }
    
    public SportUserRespDto getSportUser(String id) {
        SportUser sportUser = sportUserDao.selectByPrimaryKey(id);
        SportUserRespDto sportUserRespDto = BeanUtils.createBeanByTarget(sportUser, SportUserRespDto.class);
        return sportUserRespDto;
    }
    
    public Object save(SportUserReqDto sportUserReqDto) {
        SportUser sportUser = BeanUtils.createBeanByTarget(sportUserReqDto, SportUser.class);
        sportUser.setVisibleStat(DataStatus.ENABLED);
        return sportUserDao.insert(sportUser);
    }
    
    public Object edit (SportUserReqDto sportUserReqDto) {
        SportUser sportUser = BeanUtils.createBeanByTarget(sportUserReqDto, SportUser.class);
        return sportUserDao.updateByPrimaryKeySelective(sportUser);        
    }
    
    public Object visible(String id, Integer visibleStat) {
        SportUser sportUser = sportUserDao.selectByPrimaryKey(id);
        sportUser.setVisibleStat(visibleStat);
        return sportUserDao.updateByPrimaryKeySelective(sportUser);        
    }
    
    public Object audit(String id, Integer auditStat) {
        SportUser sportUser = sportUserDao.selectByPrimaryKey(id);
        sportUser.setAudit(auditStat);
        return sportUserDao.updateByPrimaryKeySelective(sportUser);
    }
    
    public Object delete(String ids) {
        String[] idStr = ids.split(",");
        if (idStr.length>0) {
            for (String id : idStr) {
                SportUser sportUser = sportUserDao.selectByPrimaryKey(id);
                sportUser.setStat(DataStatus.DISABLED);
                sportUserDao.updateByPrimaryKeySelective(sportUser);
            }
        } else {
            SportUser sportUser = sportUserDao.selectByPrimaryKey(ids);
            sportUser.setStat(DataStatus.DISABLED);
            sportUserDao.updateByPrimaryKeySelective(sportUser);
        }
        return null;
     }
    
    public Integer mpUpdateUser(String mobile,String password) {
        List<SportUser> sportUsers = sportUserDao.findByMoblieOrEmail(mobile, null);
        if (sportUsers.size()>0) {
            SportUser sportUser = sportUsers.get(0);
            sportUser.setPassword(password);            
            return sportUserDao.updateByPrimaryKeySelective(sportUser);
        }
        return -1;
    }
    
    public Integer emailUpdateUser(String email,String password) {
        List<SportUser> sportUsers = sportUserDao.findByMoblieOrEmail(null, email);
        if (sportUsers.size()>0) {
            SportUser sportUser = sportUsers.get(0);
            sportUser.setPassword(password);            
            return sportUserDao.updateByPrimaryKeySelective(sportUser);
        }
        return -1;
    }

    
     /** 
     * (non-Javadoc)   
     * @see com.tianfang.user.service.ISportUserService#findByUType(java.lang.Integer)   
     */
    @Override
    public List<SportUserRespDto> findByUType(Integer utype)
    {
        List<SportUser> sportUsers = sportUserDao.findByUtype(utype);
        List<SportUserRespDto> sportUserRespDtos = BeanUtils.createBeanListByTarget(sportUsers, SportUserRespDto.class);
        return sportUserRespDtos;
    }
    
    public Integer findByEmailOrSMS(String email,String mobile) {
    	List<SportUser> sportUsers = sportUserDao.findByMoblieOrEmail(mobile, email);
    	if (sportUsers.size()<=0) {
    		return -1;
    	}
    	return 1;
    }

    /**
	 * @author YIn
	 * @time:2015年12月25日 下午3:34:53
	 */
	@Override
	public int changeLecturer(String id, Integer lecturerStat) {
		SportUser sportUser = sportUserDao.selectByPrimaryKey(id);
        sportUser.setLecturer(lecturerStat);
        int flag = sportUserDao.updateByPrimaryKeySelective(sportUser);
        if(flag > 0){
        	return flag;
        }else{
        	return 0;
        }
		
	}

	@Override
	public int setUserOp(String id, Integer trainerLevel) {
        SportUser sportUser = sportUserDao.selectByPrimaryKey(id);
        sportUser.setTrainerLevel(trainerLevel);
        return sportUserDao.updateByPrimaryKeySelective(sportUser);        
    }
}

