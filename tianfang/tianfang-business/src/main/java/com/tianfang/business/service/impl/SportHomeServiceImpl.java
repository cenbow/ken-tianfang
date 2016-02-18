/**
 * 
 */
package com.tianfang.business.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianfang.business.dao.SportHomeDao;
import com.tianfang.business.dto.SportHomeDto;
import com.tianfang.business.service.SportHomeService;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;

/**
 * 
 * @author wk.s
 * @date 2015年11月17日
 */
@Service
public class SportHomeServiceImpl implements SportHomeService {

	@Resource
	private SportHomeDao sportHomeDao;
	
	@Override
	public boolean addHomeRecord(SportHomeDto dto) {
	
		return sportHomeDao.addHomeRecord(dto);
	}

	@Override
	public boolean deleteHRecord(String hid) {
		
		return sportHomeDao.deleteHRecord(hid);
	}

	@Override
	public boolean updateHRecord(SportHomeDto dto) {
	
		return sportHomeDao.updateHRecord(dto);
	}

	@Override
	public PageResult<SportHomeDto> findHRecords(SportHomeDto dto, PageQuery page) {
		
		return sportHomeDao.findHRecords(dto, page);
	}

	@Override
	public List<SportHomeDto> findNRecords(SportHomeDto dto, Integer n) {
	
		return sportHomeDao.findNRecords(dto, n);
	}

	@Override
	public List<SportHomeDto> selcetByMarked(SportHomeDto sportHome) {
		return sportHomeDao.selcetByMarked(sportHome);
	}

}
