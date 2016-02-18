package com.tianfang.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tianfang.business.dao.SportFutureStarDao;
import com.tianfang.business.dto.SportFutureStarDto;
import com.tianfang.business.pojo.SportFutureStar;
import com.tianfang.business.service.ISportFutureStarService;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;

@Service
public class SportFutureStarServiceImpl implements ISportFutureStarService {

	@Autowired
	private SportFutureStarDao dao;
	
	@Override
	public int saveFutureStar(SportFutureStarDto dto) {
		checkExceptionForFutureStar(dto);
		
		SportFutureStar star = changToEntity(dto);
		int max = dao.pageRankingMax();
		star.setPageRanking(max+1); // 增加排序字段的赋值
		dao.insert(star);
		return 1;
	}

	@Override
	public int deleteFutureStar(String id) {
		String[] ids = id.split(",");
		if(ids.length>0){
			for (String str : ids) {
				dao.deleteFutureStar(str);
			}
		}else{
			dao.deleteFutureStar(id);
		}
		return 1;
	}

	@Override
	public int updateFutureStar(SportFutureStarDto dto) {
		checkExceptionForFutureStar(dto);
		checkExceptionForId(dto.getId());
		SportFutureStar star = dao.selectByPrimaryKey(dto.getId());
		checkExceptionForFutureStar(star);
		SportFutureStar entity = changToEntity(dto);
		entity.setStat(star.getStat());
		entity.setCreateTime(star.getCreateTime());
		entity.setPageRanking(star.getPageRanking());
		dao.updateByPrimaryKey(entity);
		return 1;
	}

	@Override
	public SportFutureStarDto getFutureStarById(String id) {
		checkExceptionForId(id);
		SportFutureStar star = dao.selectByPrimaryKey(id);
		SportFutureStarDto dto = changToDto(star);
		
		return dto;
	}

	@Override
	public List<SportFutureStarDto> findFutureStarByParams(
			SportFutureStarDto params) {
		List<SportFutureStar> list = dao.findFutureStarByParams(params);
		
		return changToDto(list);
	}

	@Override
	public PageResult<SportFutureStarDto> findFutureStarByParams(
			SportFutureStarDto params, int currPage, int pageSize) {
		int total = dao.countFutureStarByParams(params);
		if (total > 0){
			PageQuery page = new PageQuery(total, currPage, pageSize);
			List<SportFutureStar> stars = dao.findFutureStarByParams(params, page);
			if (!CollectionUtils.isEmpty(stars)){
				PageResult<SportFutureStarDto> result = new PageResult<SportFutureStarDto>(page, changToDto(stars));
				
				return result;
			}
		}		
		return null;
	}

	@Override
	public List<SportFutureStarDto> findIndexFutureStars(Integer type, Integer size) {
		PageQuery page = new PageQuery(100, 1, size);
		SportFutureStarDto params = new SportFutureStarDto();
		params.setType(type);
		List<SportFutureStar> stars = dao.findFutureStarByParams(params, page);
		
		return changToDto(stars);
	}

	@Override
	public int pageRanking(String id, Integer positionId) {
		SportFutureStar star = dao.selectByPrimaryKey(id);
	    if(null == star) {
	        return 0;
	    }
	    SportFutureStar starP = dao.findByPostionId(positionId);
	    if (null == starP) {
	    	star.setPageRanking(positionId);
	        dao.updateByPrimaryKeySelective(star);
	    }
	    if (null == positionId) {
	        star.setPageRanking(dao.pageRankingMax()+1);
	        dao.updateByPrimaryKeySelective(star);           
	    }
	    if (null != starP && null != positionId) {
	        if (null != starP.getPageRanking()) {
	        	starP.setPageRanking(star.getPageRanking());
	            dao.updateByPrimaryKeySelective(starP);
                star.setPageRanking(positionId);
                dao.updateByPrimaryKeySelective(star);	            
	        }
	        if (null == starP.getPageRanking()) {
	        	star.setPageRanking(positionId);
	        	dao.updateByPrimaryKeySelective(star);
                starP.setPageRanking(dao.pageRankingMax()+1);               
                dao.updateByPrimaryKeySelective(starP);
	        }
	    }
	    return 1;
	}
	
	private void checkExceptionForFutureStar(SportFutureStarDto dto) {
		if (null == dto){
			throw new RuntimeException("对不起,未来之星对象为空");
		}
	}
	
	private void checkExceptionForId(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,未来之星主键id为空");
		}
	}
	
	private void checkExceptionForFutureStar(SportFutureStar star) {
		if (null == star){
			throw new RuntimeException("对不起,未来之星对象不存在");
		}
	}
	
	private SportFutureStar changToEntity(SportFutureStarDto dto) {
		if (dto == null) {
			return null;
		}
		SportFutureStar entity = new SportFutureStar();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}

	private SportFutureStarDto changToDto(SportFutureStar entity) {
		if (entity == null) {
			return null;
		}
		SportFutureStarDto dto = new SportFutureStarDto();
		BeanUtils.copyProperties(entity, dto);
		formatDate(dto);
		return dto;
	}

	private List<SportFutureStarDto> changToDto(List<SportFutureStar> list) {
		if (!CollectionUtils.isEmpty(list)){
			List<SportFutureStarDto> dtos = new ArrayList<SportFutureStarDto>(list.size());
			for (SportFutureStar star : list){
				dtos.add(changToDto(star));
			}
			return dtos;
		}
		return null;
	}
	
	private void formatDate(SportFutureStarDto dto) {
		dto.setCreateTimeStr(DateUtils.format(dto.getCreateTime(), "yyyy-MM-dd HH:mm"));
		if (null != dto.getLastUpdateTime()){
			dto.setLastUpdateTimeStr(DateUtils.format(dto.getLastUpdateTime(), "yyyy-MM-dd HH:mm"));
		}
	}
}