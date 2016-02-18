/**
 * 
 */
package com.tianfang.business.service;

import java.util.List;

import com.tianfang.business.dto.SportHomeDto;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;

/**
 * 
 * @author wk.s
 * @date 2015年11月17日
 */
public interface SportHomeService {

	/**
	 * 新增sprothome数据
	 * @param dto
	 * @return
	 * @2015年11月17日
	 */
	public boolean addHomeRecord(SportHomeDto dto);
	
	/**
	 * 根据id删除sporthome数据
	 * @param hid
	 * @return
	 * @2015年11月17日
	 */
	public boolean deleteHRecord(String hid);
	
	/**
	 * 更新sporthome数据
	 * @param dto
	 * @return
	 * @2015年11月17日
	 */
	public boolean updateHRecord(SportHomeDto dto);
	
	/**
	 * 根据条件查询sporthome数据(支持分页)
	 * @param dto
	 * @param page
	 * @return
	 * @2015年11月17日
	 */
	public PageResult<SportHomeDto> findHRecords(SportHomeDto dto, PageQuery page);
	
	/**
	 * 根据条件查询记录（返回指定条最新的记录(order by create_time desc)）
	 * @param dto
	 * @param n
	 * @return
	 */
	public List<SportHomeDto> findNRecords(SportHomeDto dto, Integer n);

	public List<SportHomeDto> selcetByMarked(SportHomeDto sportHome);
}
