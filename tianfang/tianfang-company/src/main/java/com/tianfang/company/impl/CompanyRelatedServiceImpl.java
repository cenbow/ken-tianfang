/**
 * 
 */
package com.tianfang.company.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.DateUtils;
import com.tianfang.company.dao.CompanyRelatedDao;
import com.tianfang.company.dto.SportCompanyRelatedDto;
import com.tianfang.company.service.CompanyRelatedService;

/**
 * @author wk.s
 * 2015年12月4日
 */
@Service
public class CompanyRelatedServiceImpl implements CompanyRelatedService {

	@Resource
	private CompanyRelatedDao comRDao;

	@Override
	public PageResult<SportCompanyRelatedDto> getCRInfoList(SportCompanyRelatedDto param, PageQuery page){
		PageResult<SportCompanyRelatedDto> re = null;
		List<SportCompanyRelatedDto> lst = comRDao.getCRInfoList(param, page);
		if(!(lst == null || lst.size() == 0)){
			long sum = comRDao.count(param);
			page.setTotal(sum);
			for (SportCompanyRelatedDto dto : lst) {
				dto.setCreateTimeFM(DateUtils.format(dto.getCreateTime(), "yyyy-MM-dd"));
			}
			re = new PageResult<SportCompanyRelatedDto>(page, lst);
		}
		return re;
	}

	@Override
	public List<SportCompanyRelatedDto> findCompanies(SportCompanyRelatedDto param){
		return comRDao.findCompanies(param);
	}
	
	@Override
	public boolean addCRData(SportCompanyRelatedDto data) {
		
		return comRDao.addCRData(data);
	}

	@Override
	public boolean updateCRDataById(SportCompanyRelatedDto data) {
		
		return comRDao.updateCRDataById(data);
	}

	@Override
	public boolean deleteCRDataById(SportCompanyRelatedDto data) {
		
		return comRDao.deleteCRDataById(data);
	}

	@Override
	public SportCompanyRelatedDto getCRInfoById(String id) {
	
		return comRDao.getCRInfoById(id);
	}

	@Override
	public boolean deleteByIds(String ids) {

		return comRDao.deleteByIds(ids);
	}
}
