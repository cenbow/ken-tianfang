/**
 * 
 */
package com.tianfang.company.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.company.dto.SportCompanyRelatedDto;

/**
 * 
 * @author wk.s
 * 2015年12月4日
 */
public interface CompanyRelatedService {

	/**
	 * 根据条件分页查询数据
	 * @param param
	 * @param page
	 * @return
	 */
	public PageResult<SportCompanyRelatedDto> getCRInfoList(SportCompanyRelatedDto param, PageQuery page);
	
	/**
	 * 根据参数查询公司相关信息
	 * @param param
	 * @return
	 * @author xiang_wang
	 * 2015年12月4日下午4:26:54
	 */
	public List<SportCompanyRelatedDto> findCompanies(SportCompanyRelatedDto param);
	
	/**
	 * 新增数据
	 * @param data
	 * @return
	 */
	public boolean addCRData(SportCompanyRelatedDto data);
	
	/**
	 * 根据id更新数据
	 * @param data
	 * @return
	 */
	public boolean updateCRDataById(SportCompanyRelatedDto data);
	
	/**
	 * 根据id逻辑删除数据
	 * @param data
	 * @return
	 */
	public boolean deleteCRDataById(SportCompanyRelatedDto data);
	
	/**
	 * 逻辑批量删除数据
	 * @param ids
	 * @return
	 */
	public boolean deleteByIds(String ids);
	
	/**
	 * 通过id查询数据
	 * @param id
	 * @return
	 */
	public SportCompanyRelatedDto getCRInfoById(String id);
}
