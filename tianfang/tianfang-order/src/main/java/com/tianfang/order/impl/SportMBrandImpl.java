package com.tianfang.order.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.order.dao.SportMBrandDao;
import com.tianfang.order.dao.SportMProductSpuDao;
import com.tianfang.order.dto.SportMBrandDto;
import com.tianfang.order.dto.SportMProductSpuDto;
import com.tianfang.order.pojo.SportMBrand;
import com.tianfang.order.pojo.SportMProductSpu;
import com.tianfang.order.service.ISportMBrandService;

@Service
public class SportMBrandImpl implements ISportMBrandService{

	@Autowired
	private SportMBrandDao sportMBrandDao;
	
	@Autowired
	private SportMProductSpuDao sportMProductSpuDao;
	
	public PageResult<SportMBrandDto> findPage(SportMBrandDto sportMBrandDto,PageQuery page) {
		List<SportMBrandDto> results = sportMBrandDao.findPage(sportMBrandDto, page);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (SportMBrandDto sportmBrandDto : results) {
			sportmBrandDto.setCreateDate(sdf.format(sportmBrandDto.getCreateTime()));
		}
		page.setTotal(sportMBrandDao.count(sportMBrandDto));
		return new PageResult<SportMBrandDto>(page,results);
	}
	
	public SportMBrandDto findById(String id) {
		SportMBrand sportMBrand = sportMBrandDao.selectByPrimaryKey(id);
		return BeanUtils.createBeanByTarget(sportMBrand, SportMBrandDto.class);
	}
	
	public Integer save(SportMBrandDto sportMBrandDto) {
		SportMBrand sportMBrand = BeanUtils.createBeanByTarget(sportMBrandDto, SportMBrand.class);
		if (StringUtils.isNotBlank(sportMBrand.getId())) {
			return sportMBrandDao.updateByPrimaryKeySelective(sportMBrand);
		}
		return sportMBrandDao.insert(sportMBrand);
	}
	
	 public SportMBrandDto delete(String ids) {
       String[] idStr = ids.split(",");
       SportMBrandDto sportMBrandDto = new SportMBrandDto();
       if (idStr.length>0) {
           for (String id : idStr) {
        	   SportMProductSpuDto spu = new SportMProductSpuDto();
        	   spu.setBrandId(id);
        	   List<SportMProductSpu> sportMProductSpus = sportMProductSpuDao.findSpuById(spu);
        	   SportMBrand sportMBrand = sportMBrandDao.selectByPrimaryKey(id);
        	   if (sportMProductSpus.size()>0) {
        		   sportMBrandDto = BeanUtils.createBeanByTarget(sportMBrand, SportMBrandDto.class);
        		   sportMBrandDto.setDeleteStat(DataStatus.DISABLED);
        		   return sportMBrandDto;
        	   }        	   
           }
           for (String id : idStr) {
        	   SportMBrand sportMBrand = sportMBrandDao.selectByPrimaryKey(id);
        	   sportMBrand.setStat(DataStatus.DISABLED);
        	   sportMBrandDao.updateByPrimaryKeySelective(sportMBrand);
           }
           sportMBrandDto.setDeleteStat(DataStatus.ENABLED);
		   return sportMBrandDto;
       } else {
    	   SportMProductSpuDto spu = new SportMProductSpuDto();
    	   spu.setBrandId(ids);
    	   List<SportMProductSpu> sportMProductSpus = sportMProductSpuDao.findSpuById(spu);
    	   SportMBrand sportMBrand = sportMBrandDao.selectByPrimaryKey(ids);
    	   if (sportMProductSpus.size()>0) {
    		   sportMBrandDto = BeanUtils.createBeanByTarget(sportMBrand, SportMBrandDto.class);
    		   sportMBrandDto.setDeleteStat(DataStatus.DISABLED);
    		   return sportMBrandDto;
    	   }   
    	   sportMBrand.setStat(DataStatus.DISABLED);
    	   sportMBrandDao.updateByPrimaryKeySelective(sportMBrand);
    	   sportMBrandDto.setDeleteStat(DataStatus.ENABLED);
		   return sportMBrandDto;
       }
   }

	@Override
	public List<SportMBrandDto> selectAll() {
		List<SportMBrand> lis = sportMBrandDao.selectAll();
		if(lis.size()>0){
			return BeanUtils.createBeanListByTarget(lis, SportMBrandDto.class);
		}
		return null;
	}
	
}
