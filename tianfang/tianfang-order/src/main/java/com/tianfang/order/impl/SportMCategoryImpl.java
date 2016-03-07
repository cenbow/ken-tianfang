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
import com.tianfang.order.dao.SportMCategoryDao;
import com.tianfang.order.dao.SportMProductSpuDao;
import com.tianfang.order.dao.SportMTypeDao;
import com.tianfang.order.dto.SportMCategoryDto;
import com.tianfang.order.dto.SportMProductSpuDto;
import com.tianfang.order.dto.SportMTypeDto;
import com.tianfang.order.pojo.SportMCategory;
import com.tianfang.order.pojo.SportMProductSpu;
import com.tianfang.order.service.ISportMCategoryService;

@Service
public class SportMCategoryImpl implements ISportMCategoryService{

	@Autowired
	private SportMCategoryDao sportMCategoryDao;
	
	@Autowired
	private SportMTypeDao sportMTypeDao;
	
	@Autowired
	private SportMProductSpuDao sportMProductSpuDao;
	
	public PageResult<SportMCategoryDto> findPage(SportMCategoryDto sportMCategoryDto,PageQuery page) {
		List<SportMCategoryDto> results = sportMCategoryDao.findPage(sportMCategoryDto, page);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (results.size()>0) {
			for (SportMCategoryDto sportmCategoryDto : results) {
				if(StringUtils.isNotBlank(sportMCategoryDto.getParentId())){
					sportmCategoryDto.setCreateDate(sdf.format(sportmCategoryDto.getCreateTime()));
					if(StringUtils.isNotBlank(sportMCategoryDto.getParentId())){
						SportMCategory cate = sportMCategoryDao.selectByPrimaryKey(sportMCategoryDto.getParentId());
						if(cate!=null){
							sportmCategoryDto.setParentName(cate.getCategoryName());
						}
					}
				}else{
					if (StringUtils.isNotBlank(sportmCategoryDto.getParentId()) && !sportmCategoryDto.getParentId().equals("0")) {
						for (SportMCategoryDto mcategoryDto : results) {
							if (sportmCategoryDto.getParentId().equals(mcategoryDto.getId())) {
								sportmCategoryDto.setParentName(mcategoryDto.getCategoryName());
							}
						}
					}
				}
				
			}
		}
		page.setTotal(sportMCategoryDao.count(sportMCategoryDto));
		return new PageResult<SportMCategoryDto>(page, results);
	}
	
	public Integer save(SportMCategoryDto sportMCategoryDto) {
		SportMCategory sportMCategory = BeanUtils.createBeanByTarget(sportMCategoryDto, SportMCategory.class);
		Integer stat=0;
		if (StringUtils.isNotBlank(sportMCategoryDto.getId())) {
			stat= sportMCategoryDao.updateByPrimaryKeySelective(sportMCategory);
			return stat;
		}
		stat = sportMCategoryDao.insert(sportMCategory);
		return stat;
	}
	
	public SportMCategoryDto findById(String id) {
		SportMCategory sportMCategory = sportMCategoryDao.selectByPrimaryKey(id);
		return BeanUtils.createBeanByTarget(sportMCategory, SportMCategoryDto.class);
	}
	
	public List<SportMTypeDto> getAllType() {
		List<SportMTypeDto> sportMTypes = sportMTypeDao.findAllType();
		return sportMTypes;
	}
	
	public List<SportMCategoryDto> findAllCategory() {
		List<SportMCategoryDto> sportMCategoryDtos = sportMCategoryDao.findAllCategory();
		return sportMCategoryDtos;
	}
	
	public Object delete(String ids) {
		String[] idStr = ids.split(",");
		SportMCategoryDto sportMCategoryDto = new SportMCategoryDto();
		if (idStr.length > 0) {
			for (String id : idStr) {
				SportMProductSpuDto spu = new SportMProductSpuDto();
	        	spu.setCategoryId(id);
				List<SportMProductSpu> sportMProductSpus = sportMProductSpuDao.findSpuById(spu);
				SportMCategory sportMCategory = sportMCategoryDao.selectByPrimaryKey(id);
				if (sportMProductSpus.size()>0) {
					sportMCategoryDto = BeanUtils.createBeanByTarget(sportMCategory, SportMCategoryDto.class);
					sportMCategoryDto.setDeleteStat(DataStatus.DISABLED);
					return sportMCategoryDto;
				}				
			}
			for (String id : idStr) {
				SportMCategory sportMCategory = sportMCategoryDao.selectByPrimaryKey(id);
				sportMCategory.setStat(DataStatus.DISABLED);
				sportMCategoryDao.updateByPrimaryKeySelective(sportMCategory);
			}
			sportMCategoryDto.setDeleteStat(DataStatus.ENABLED);
			return sportMCategoryDto;
		} else {
			SportMProductSpuDto spu = new SportMProductSpuDto();
        	spu.setCategoryId(ids);
			List<SportMProductSpu> sportMProductSpus = sportMProductSpuDao.findSpuById(spu);
			SportMCategory sportMCategory = sportMCategoryDao.selectByPrimaryKey(ids);
			if (sportMProductSpus.size()>0) {
				sportMCategoryDto = BeanUtils.createBeanByTarget(sportMCategory, SportMCategoryDto.class);
				sportMCategoryDto.setDeleteStat(DataStatus.DISABLED);
				return sportMCategoryDto;
			}
			sportMCategory.setStat(DataStatus.DISABLED);
			sportMCategoryDao.updateByPrimaryKeySelective(sportMCategory);
			sportMCategoryDto.setDeleteStat(DataStatus.ENABLED);
			return sportMCategoryDto;
		}
	}
}
