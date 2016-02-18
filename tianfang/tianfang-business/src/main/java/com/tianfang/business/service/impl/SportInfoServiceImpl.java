/**
 * 
 */
package com.tianfang.business.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.tianfang.business.dao.SportInfoDao;
import com.tianfang.business.dto.SportInformationReqDto;
import com.tianfang.business.dto.SportInformationRespDto;
import com.tianfang.business.pojo.SportInformation;
import com.tianfang.business.service.ISportInfoService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.PropertiesUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.news.dto.SportNewsInfoDto;
import com.tianfang.news.pojo.SportNewsInfo;

/**		
 * <p>Title: SportInfoServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年11月16日 下午5:02:54	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年11月16日 下午5:02:54</p>
 * <p>修改备注：</p>
 */
@Service
public class SportInfoServiceImpl implements ISportInfoService
{
    @Autowired
    @Getter
    private SportInfoDao sportInfoDao;
    
    public PageResult<SportInformationRespDto> findPage(SportInformationReqDto sportInformationReqDto,PageQuery page) {
        List<SportInformationRespDto> result = sportInfoDao.findPage(sportInformationReqDto, page);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (SportInformationRespDto sportInformationRespDto : result) {
            sportInformationRespDto.setCreateDate(sdf.format(sportInformationRespDto.getCreateTime()));
        }
        page.setTotal(sportInfoDao.count(sportInformationReqDto));
        return new PageResult<SportInformationRespDto>(page,result);
    }
    
    public SportInformationRespDto save(SportInformationReqDto sportInformationReqDto) {
        SportInformation sportInformation = BeanUtils.createBeanByTarget(sportInformationReqDto, SportInformation.class);
        if (sportInformation.getMarked() == 1) {
            List<SportInformation> result = sportInfoDao.findCountByMarked(null,sportInformation.getMarked());
            if (result != null && result.size() >= 3) {
                SportInformationRespDto SportInformationRespDto = new SportInformationRespDto();
                SportInformationRespDto.setMarked(3);
                return SportInformationRespDto;
            }
        }
        sportInformation.setClickRate(DataStatus.DISABLED);
        sportInformation.setBrowseQuantity(DataStatus.DISABLED);
        sportInfoDao.insert(sportInformation);        
        return BeanUtils.createBeanByTarget(sportInformation, SportInformationRespDto.class);
    }
    
    public SportInformationRespDto findById(String id){
        SportInformation sportInformation = sportInfoDao.selectByPrimaryKey(id);
        return BeanUtils.createBeanByTarget(sportInformation, SportInformationRespDto.class);
    }
    
    public SportInformationRespDto edit(SportInformationReqDto sportInformationReqDto) {
        SportInformation sportInformation = BeanUtils.createBeanByTarget(sportInformationReqDto, SportInformation.class);        
        if (null != sportInformation.getMarked() && sportInformation.getMarked() == 1) {          
            List<SportInformation> result = sportInfoDao.findCountByMarked(sportInformation.getId(),sportInformation.getMarked());
            if (result.size()==0) {
                List<SportInformation> results = sportInfoDao.findCountByMarked(null, sportInformation.getMarked());
                if (results != null && results.size() >= 3) {
                    SportInformationRespDto SportInformationRespDto = new SportInformationRespDto();
                    SportInformationRespDto.setMarked(3);
                    return SportInformationRespDto;
                }
            }
           
        }
        sportInformation.setReleaseStat(DataStatus.DISABLED);
        sportInfoDao.updateByPrimaryKeySelective(sportInformation);
        return BeanUtils.createBeanByTarget(sportInformation, SportInformationRespDto.class);
    }
    
    public Object delete(String ids) {
        String[] idStr = ids.split(",");
        if (idStr.length>0) {
            for (String id : idStr) {
                SportInformation sportInformation = sportInfoDao.selectByPrimaryKey(id);
                sportInformation.setStat(DataStatus.DISABLED);
                sportInfoDao.updateByPrimaryKeySelective(sportInformation);
            }
        } else {
            SportInformation sportInformation = sportInfoDao.selectByPrimaryKey(ids);
            sportInformation.setStat(DataStatus.DISABLED);
            sportInfoDao.updateByPrimaryKeySelective(sportInformation);
        }
        return null;
    }
    
    public Object release(String id,Integer releaseStat) {
        SportInformation sportInformation = sportInfoDao.selectByPrimaryKey(id);
        if (null != sportInformation) {
    	    if(Objects.equal(releaseStat, DataStatus.DISABLED)){
    	    	sportInformation.setMarked(DataStatus.DISABLED);
    	    }
            sportInformation.setReleaseStat(releaseStat);
            sportInfoDao.updateByPrimaryKeySelective(sportInformation);
        }
        return null;
    }

    /**
	 * 更改前端页面显示位置
	 */
	public int pageRanking(String id, Integer positionId) {
		SportInformation info = sportInfoDao.selectByPrimaryKey(id);
	    if(null == info) {
	        return 0;
	    }
	    SportInformation sportInformation = sportInfoDao.findByPostionId(positionId);
	    if (null == sportInformation) {
	        info.setPageRanking(positionId);
	        sportInfoDao.updateByPrimaryKeySelective(info);
	    }
	    if (null == positionId) {
	        System.out.println("最大排序="+sportInfoDao.pageRankingMax());
	        info.setPageRanking(sportInfoDao.pageRankingMax()+1);
	        sportInfoDao.updateByPrimaryKeySelective(info);           
	    }
	    if (null != sportInformation && null != positionId) {
	        if (null != sportInformation.getPageRanking()) {
	        	sportInformation.setPageRanking(info.getPageRanking());
	        	sportInfoDao.updateByPrimaryKeySelective(sportInformation);
	            info.setPageRanking(positionId);
	            sportInfoDao.updateByPrimaryKeySelective(info);	            
	        }
	        if (null == sportInformation.getPageRanking()) {
	            info.setPageRanking(positionId);
	            sportInfoDao.updateByPrimaryKeySelective(info);
                System.out.println("最大排序="+sportInfoDao.pageRankingMax());
                sportInformation.setPageRanking(sportInfoDao.pageRankingMax()+1);               
                sportInfoDao.updateByPrimaryKeySelective(sportInformation);
	        }
	    }
	    return 1;
	}
    
	@Override
	public List<SportInformationRespDto> findInfoByParams(HashMap<String, Object> infoParams) {
		List<SportInformation> infos = sportInfoDao.findInfoByParams(infoParams);
		if (null != infos && infos.size() > 0){
			return BeanUtils.createBeanListByTarget(infos, SportNewsInfoDto.class);
		}
		return null;
	}
}

