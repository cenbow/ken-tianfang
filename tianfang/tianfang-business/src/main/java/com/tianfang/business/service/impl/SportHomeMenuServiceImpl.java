package com.tianfang.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.business.dao.SportHomeMenuDao;
import com.tianfang.business.dto.MenuDto;
import com.tianfang.business.dto.SportHomeMenuDto;
import com.tianfang.business.enums.MenuTypeEnums;
import com.tianfang.business.pojo.SportHomeMenu;
import com.tianfang.business.service.ISportHomeMenuService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;

@Service
public class SportHomeMenuServiceImpl implements ISportHomeMenuService {

	@Autowired
	private SportHomeMenuDao sportHomeMenuDao;
	
	@Override
	public List<MenuDto> findSportHomeMenuList(SportHomeMenuDto dto) {
		List<SportHomeMenu> dataList = sportHomeMenuDao.findSportHomeMenuList(dto);
		List<SportHomeMenuDto> resultList = BeanUtils.createBeanListByTarget(dataList, SportHomeMenuDto.class);

		HashMap<String,List<SportHomeMenuDto>> listMap = new HashMap<String,List<SportHomeMenuDto>>();
		
		List<SportHomeMenuDto> mapList = null;
		for(SportHomeMenuDto mDto : resultList){
			if(listMap.containsKey(mDto.getParentId())){
				mapList = listMap.get(mDto.getParentId());
				mapList.add(mDto);
			}else{
				mapList = new ArrayList<SportHomeMenuDto>();		
				mapList.add(mDto);
			}
			listMap.put(mDto.getParentId(),mapList);
		}
		List<MenuDto> dataResultList = new ArrayList<MenuDto>();
		MenuDto menuDto = null;
		for(SportHomeMenuDto sportMenuDto : resultList){
			if(sportMenuDto.getMenuType().intValue() == MenuTypeEnums.ONE.getIndex()){
				menuDto = new MenuDto();
				menuDto.setSportHomeMenuDto(sportMenuDto);
				menuDto.setSecondList(listMap.get(sportMenuDto.getId()));
				dataResultList.add(menuDto);
			}
		}

		return dataResultList;
	}
	
	
	public PageResult<SportHomeMenuDto> findPage(SportHomeMenuDto dto,PageQuery page) {
		List<SportHomeMenuDto> results = sportHomeMenuDao.findPage(dto, page);
        page.setTotal(sportHomeMenuDao.count(dto));
        return new PageResult<SportHomeMenuDto>(page,results);
	}
	
	public List<SportHomeMenuDto> findByMenuType(Integer menuType) {
		List<SportHomeMenuDto> results = sportHomeMenuDao.findByMenuType(menuType);
		return results;
	}
	
	public Integer save(SportHomeMenuDto sportHomeMenuDto) {
		SportHomeMenu sportHomeMenu = BeanUtils.createBeanByTarget(sportHomeMenuDto, SportHomeMenu.class);
		return sportHomeMenuDao.insert(sportHomeMenu);
	}
	
	public SportHomeMenuDto findById(String id) {
		SportHomeMenu sportHomeMenu = sportHomeMenuDao.selectByPrimaryKey(id);
		return BeanUtils.createBeanByTarget(sportHomeMenu, SportHomeMenuDto.class);
	}
	
	public Integer edit(SportHomeMenuDto dto) {
		SportHomeMenu sportHomeMenu = BeanUtils.createBeanByTarget(dto, SportHomeMenu.class);
		return sportHomeMenuDao.updateByPrimaryKeySelective(sportHomeMenu);
	}
	
	public Integer delete(String ids) {
		String[] idStr = ids.split(",");
        if (idStr.length>0) {
            for (String id:idStr) {
            	SportHomeMenu sportHomeMenu = sportHomeMenuDao.selectByPrimaryKey(id);
            	sportHomeMenu.setStat(DataStatus.DISABLED);
            	sportHomeMenuDao.updateByPrimaryKeySelective(sportHomeMenu);
            }
        }else{
        	SportHomeMenu sportHomeMenu = sportHomeMenuDao.selectByPrimaryKey(ids);
        	sportHomeMenu.setStat(DataStatus.DISABLED);
        	sportHomeMenuDao.updateByPrimaryKeySelective(sportHomeMenu);
        }
        return null;        
	}


	@Override
	public List<SportHomeMenuDto> findByParentId(String parentId) {
		SportHomeMenuDto dto = new SportHomeMenuDto();
		dto.setParentId(parentId);
		List<SportHomeMenu> dataList = sportHomeMenuDao.findSportHomeMenuList(dto);
		return BeanUtils.createBeanListByTarget(dataList, SportHomeMenuDto.class);
	}

	@Override
	public List<SportHomeMenuDto> findAll() {
		List<SportHomeMenu> dataList = sportHomeMenuDao.findSportHomeMenuList(null);
		return BeanUtils.createBeanListByTarget(dataList, SportHomeMenuDto.class);
	}
}