package com.tianfang.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianfang.business.dao.SportSfResultDao;
import com.tianfang.business.dao.SportSuggestionUserDao;
import com.tianfang.business.dto.SportSfResultDto;
import com.tianfang.business.dto.SportSuggestionUserDto;
import com.tianfang.business.pojo.SportSfResult;
import com.tianfang.business.pojo.SportSuggestionUser;
import com.tianfang.business.service.ISportSuggestionUserService;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
@Service
public class SportSuggestionUserServiceImpl implements ISportSuggestionUserService {

	@Autowired
	private SportSuggestionUserDao suggesDao;
	@Autowired
	private SportSfResultDao resultDao;

	@Override
	public String save(SportSuggestionUserDto suggestionUserDto) {
		SportSuggestionUser suggestionUser = BeanUtils.createBeanByTarget(suggestionUserDto, SportSuggestionUser.class);
		String stat = suggesDao.add(suggestionUser);
		return stat;
	}

	@Override
	public long update(SportSuggestionUserDto suggestionUserDto) {
		SportSuggestionUser suggestionUser = BeanUtils.createBeanByTarget(suggestionUserDto, SportSuggestionUser.class);
		long stat = suggesDao.update(suggestionUser);
		return stat;
	}

	@Override
	public long delete(String ids) {
		String[] id = ids.split(",");
		long stat =0;
		for (String str : id) {
			try {
				stat = suggesDao.delete(str);
			} catch (Exception e) {
				throw new RuntimeException("删除出错");
			}
			if(stat<1){
				return 0;
			}
		}
		return stat;
	}

	@Override
	public PageResult<SportSuggestionUserDto> selectPageAll(SportSuggestionUserDto suggestionUserDto,PageQuery page) {
		List<SportSuggestionUser> suggesList = suggesDao.getByCriteria(suggestionUserDto,page);
		List<SportSuggestionUserDto> suggesListDto= BeanUtils.createBeanByTarget(suggesList, SportSuggestionUserDto.class);
		long total = suggesDao.selectCount(suggestionUserDto);
	    if(page!=null){
	    	page.setTotal(total);
	    }
		return  new PageResult<SportSuggestionUserDto>(page,suggesListDto);
	}

	@Override
	@Transactional
	public boolean submitSuggestion(SportSuggestionUserDto suggestionUserDto, List<SportSfResultDto> results) {
		String sfUserId = this.save(suggestionUserDto);
		if (null != results && results.size() > 0){
			for (SportSfResultDto result : results){
				result.setSfUserId(sfUserId);
			}
			List<SportSfResult> list = BeanUtils.createBeanListByTarget(results, SportSfResult.class);
			resultDao.batchInsert(list);
		}
		
		return true;
	}
}
