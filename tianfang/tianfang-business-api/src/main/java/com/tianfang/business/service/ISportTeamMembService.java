/**
 * 
 */
package com.tianfang.business.service;

import java.util.List;
import java.util.Map;

import com.tianfang.business.dto.SportTeamDto;
import com.tianfang.business.dto.SportTeamMembersDto;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;

/**
 * 
 * @author wk.s
 * @date 2015年11月16日
 */
public interface ISportTeamMembService {

//	/**
//	 * 根据tid查询所有队员
//	 * @param dto
//	 * @param page
//	 * @return
//	 * @2015年11月16日
//	 */
	public List<SportTeamMembersDto> findMembers(SportTeamMembersDto dto);
//	
	/**
	 * 根据tid查询所有队员
	 * @param dto
	 * @param page
	 * @return
	 * @2015年11月16日
	 */
	public PageResult<SportTeamMembersDto> findMembersV02(SportTeamMembersDto dto, PageQuery page);
	
	
	/**
	 * 查询所有队伍
	 * @return
	 * @2015年11月16日
	 */
	public List<SportTeamDto> findTeams();
	
	/**
	 * 添加队员
	 * @param dto
	 * @return
	 * @2015年11月16日
	 */
	public boolean addMembers(SportTeamMembersDto dto);
	
	/**
	 * 更新队员信息
	 * @param dto
	 * @return
	 * @2015年11月16日
	 */
	public boolean updateMembers(SportTeamMembersDto dto);
	
	/**
	 * 根据队员id删除队员
	 * @param id
	 * @return
	 * @2015年11月16日
	 */
	public boolean deleteMembers(String id);
	
	/**
	 * 根据tid查询队伍信息
	 * @param tid
	 * @return
	 * @2015年11月16日
	 */
	public SportTeamDto findTeamById(String tid);
	
	/**
	 * 新增队伍信息
	 * @param dto
	 * @return
	 * @2015年11月16日
	 */
	public boolean addTeam(SportTeamDto dto);
	
	/**
	 * 根据tid删除队伍信息
	 * @param tid
	 * @return
	 * @2015年11月16日
	 */
	public boolean deleteTeam(String tid);
	
	/**
	 * 更新队伍信息
	 * @param dto
	 * @return
	 * @2015年11月16日
	 */
	public boolean updateTeam(SportTeamDto dto);
	
	/**
	 * 通过id查询用户
	 * @param id
	 * @return
	 * @2015年11月18日
	 */
	public SportTeamMembersDto findMember(String id);
	
	
	public SportTeamMembersDto saveOrUpdateMembers(SportTeamMembersDto dto);
	
}
