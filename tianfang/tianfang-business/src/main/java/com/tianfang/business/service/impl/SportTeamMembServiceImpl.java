/**
 * 
 */
package com.tianfang.business.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianfang.business.dao.SportTeamMembDao;
import com.tianfang.business.dto.SportTeamDto;
import com.tianfang.business.dto.SportTeamMembersDto;
import com.tianfang.business.pojo.SportTeam;
import com.tianfang.business.pojo.SportTeamMembers;
import com.tianfang.business.service.ISportTeamMembService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;

/**
 * 
 * @author wk.s
 * @date 2015年11月16日
 */
@Service
public class SportTeamMembServiceImpl implements ISportTeamMembService {

	@Resource
	private SportTeamMembDao sportTeamMembDao;
	
	@Override
	public List<SportTeamMembersDto> findMembers(SportTeamMembersDto dto) {
		return sportTeamMembDao.findMembers(dto);
	}
	
	@Override
	public List<SportTeamDto> findTeams() {
		
		return sportTeamMembDao.findTeams();
	}
	@Override
	public boolean addMembers(SportTeamMembersDto dto) {
		
		return sportTeamMembDao.addMembers(dto);
	}
	@Override
	public boolean updateMembers(SportTeamMembersDto dto) {
		
		return sportTeamMembDao.updateMembers(dto);
	}
	@Override
	public boolean deleteMembers(String ids) {
		String[] idStr = ids.split(",");
        if (idStr.length>0) {
            for (String id : idStr) {
            	SportTeamMembers sportTeamMembers = sportTeamMembDao.selectByPrimaryKey(id);
//                SportInformation sportInformation = sportInfoDao.selectByPrimaryKey(id);
            	sportTeamMembers.setStat(DataStatus.DISABLED);
            	sportTeamMembDao.updateByPrimaryKeySelective(sportTeamMembers);
            }
        } else {
        	SportTeamMembers sportTeamMembers = sportTeamMembDao.selectByPrimaryKey(ids);
        	sportTeamMembers.setStat(DataStatus.DISABLED);
        	sportTeamMembDao.updateByPrimaryKeySelective(sportTeamMembers);
        }
        return true;
		
	}
	@Override
	public SportTeamDto findTeamById(String tid) {
		
		return sportTeamMembDao.findTeamById(tid);
	}
	@Override
	public boolean addTeam(SportTeamDto dto) {
		
		return sportTeamMembDao.addTeam(dto);
	}
	@Override
	public boolean deleteTeam(String tid) {
		
		return sportTeamMembDao.deleteTeam(tid);
	}
	@Override
	public boolean updateTeam(SportTeamDto dto) {
		
		return sportTeamMembDao.updateTeam(dto);
	}
	@Override
	public SportTeamMembersDto findMember(String id) {
		
		return sportTeamMembDao.findMember(id);
	}
	@Override
	public PageResult<SportTeamMembersDto> findMembersV02(SportTeamMembersDto dto, PageQuery page) {
		
		return sportTeamMembDao.findMembersV02(dto, page);
	}

	@Override
	public SportTeamMembersDto saveOrUpdateMembers(SportTeamMembersDto dto) {
		SportTeamMembers teamMember = BeanUtils.createBeanByTarget(dto, SportTeamMembers.class);
		SportTeamMembers sportTeamMembers = new SportTeamMembers();
		if(!StringUtils.isEmpty(teamMember.getId())){
			sportTeamMembDao.updateByPrimaryKeySelective(teamMember);
			sportTeamMembers = sportTeamMembDao.selectByPrimaryKey(teamMember.getId());
		}else{
			teamMember.setId(UUIDGenerator.getUUID());
			sportTeamMembDao.insertSelective(teamMember);
			sportTeamMembers = teamMember;
		}		
		
		SportTeamMembersDto reDto = BeanUtils.createBeanByTarget(sportTeamMembers,SportTeamMembersDto.class);		
		return reDto;
	}

}
