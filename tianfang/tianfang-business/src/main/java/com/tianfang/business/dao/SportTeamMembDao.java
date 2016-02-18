/**
 * 
 */
package com.tianfang.business.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.business.dto.SportHonorRespDto;
import com.tianfang.business.dto.SportTeamDto;
import com.tianfang.business.dto.SportTeamMembersDto;
import com.tianfang.business.mapper.SportTeamMapper;
import com.tianfang.business.mapper.SportTeamMapperX;
import com.tianfang.business.mapper.SportTeamMembersMapper;
import com.tianfang.business.mapper.SportTeamMembersMapperX;
import com.tianfang.business.pojo.SportHonor;
import com.tianfang.business.pojo.SportHonorExample;
import com.tianfang.business.pojo.SportTeam;
import com.tianfang.business.pojo.SportTeamMembers;
import com.tianfang.business.pojo.SportTeamMembersExample;
import com.tianfang.business.pojo.SportTeamMembersExample.Criteria;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;

/**
 * 
 * @author wk.s
 * @date 2015年11月16日
 */
@Repository
public class SportTeamMembDao extends MyBatisBaseDao<SportTeamMembers>{

	@Resource
	private SportTeamDao teamDao;
	@Resource
	private SportTeamMembersMapperX sportTeamMembersMapperX;
	@Resource
	private SportTeamMapperX sportTeamMapperX;
	
	@Autowired
	@Getter
	private SportTeamMembersMapper mapper;
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * 
		 * 此方法描述的是：根据条件查询队员信息
		 * @author: cwftalus@163.com
		 * @version: 2015年12月1日 上午11:29:12
	 */
	public List<SportTeamMembersDto> findMembers(SportTeamMembersDto dto) {

		SportTeamMembersExample example = new SportTeamMembersExample();
		SportTeamMembersExample.Criteria criteria = example.createCriteria();
	    if (!StringUtils.isEmpty(dto.getTid())) {
	        criteria.andTidEqualTo(dto.getTid());
	    }
	    criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause(" is_first asc ");
	    List<SportTeamMembers> dataList = mapper.selectByExample(example);
	    
	    List<SportTeamMembersDto> results = BeanUtils.createBeanListByTarget(dataList, SportTeamMembersDto.class);
	    for (SportTeamMembersDto sportTeamMembersDto : results) {
	    	if (StringUtils.isBlank(sportTeamMembersDto.getPic())){
	    		sportTeamMembersDto.setPic(null);
	    	}
	    }
	    return results;
	}
	
	/**
	 * 根据tid查询队员信息
	 * @param tid
	 * @param page
	 * @return
	 * @2015年11月16日
	 */
	public PageResult<SportTeamMembersDto> findMembersV02(SportTeamMembersDto dto, PageQuery page) {
		
		PageResult<SportTeamMembersDto> res = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>(2); 
			dto.setStat(DataStatus.ENABLED); // 查询有效信息
			map.put("record", dto);
			if(page != null){
				map.put("start", page.getStartNum());
				map.put("pageSize", page.getPageSize());
			}
			List<SportTeamMembersDto> lst = sportTeamMembersMapperX.findMemByCons(map);
			if((lst != null) && (page != null)){
				Map<String, Object> mapv02 = new HashMap<String, Object>(2);
				mapv02.put("stat", 1);
				for (SportTeamMembersDto sdto : lst) {
					mapv02.put("id", sdto.getTid());
					List<SportTeam> lis = teamDao.getByCriteriaPage(null, mapv02);
					if((lis != null) && (lis.size() == 1)){
						sdto.setTeamName(lis.get(0).getName());
					}
				}
				res = new PageResult<SportTeamMembersDto>(page, lst);
				Long sum = selectSum(dto);
				res.setTotal(sum);
			}
		} catch (Exception e) {
			log.error("根据条件查询队员信息过程中发生异常", e);
		}
		return res;
	}
	
	/**
	 * 查询符合条件的记录数
	 * @param dto
	 * @return
	 */
	public Long selectSum(SportTeamMembersDto dto){
		
		Long count = 0l;
		try {
			count = sportTeamMembersMapperX.selectSum(dto);
		} catch (Exception e) {
			log.error("查询符合条件记录条数过程中发生异常", e);
		}
		return count;
	}
	
	/**
	 * 此方法根据条件查询
	 * @param criteria
	 * @param team
	 */
	private void byCriteria(Criteria criteria,Map<String, Object> map) {
		if(map!=null){
			if(map.get("id")!=null && !map.get("id").equals("")){
				criteria.andIdEqualTo(map.get("id")+"");
			}
			if(map.get("tid")!=null && !map.get("tid").equals("")){
				criteria.andTidEqualTo(map.get("tid")+"");
			}
			if(map.get("name")!=null && !map.get("name").equals("")){
				criteria.andNameLike("%"+map.get("name")+"%");
			}
			if(map.get("number")!=null && !map.get("number").equals("")){
				criteria.andNumberEqualTo((map.get("number")+""));
			}
			if(map.get("position")!=null && !map.get("position").equals("")){
				criteria.andPositionEqualTo(map.get("position")+"");
			}
			if(map.get("pic")!=null && !map.get("pic").equals("")){
				criteria.andPicEqualTo(map.get("pic")+"");
			}
			if(map.get("stat")!=null && !map.get("stat").equals("")){
				criteria.andStatEqualTo(Integer.parseInt(map.get("stat")+""));
			}
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}
	
	/**
	 * 获取最大条数
	 * @param page
	 * @param map
	 * @return
	 */
	public long countCriteria(Map<String, Object> map) {
		SportTeamMembersExample example = new SportTeamMembersExample();
		Criteria criteria = example.createCriteria();
		byCriteria(criteria,map);
		return sportTeamMembersMapperX.selectByExample(example).size();
	}
	
	/**
	 * 添加队员
	 * @param dto
	 * @return
	 * @2015年11月16日
	 */
	public boolean addMembers(SportTeamMembersDto dto){
		
		boolean flag = true;
		try {
			dto.setStat(1);
			dto.setCreateTime(new Date());
			int i = sportTeamMembersMapperX.insert(dto);
			if(i != 1){
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 更新队员信息
	 * @param dto
	 * @return
	 * @2015年11月16日
	 */
	public boolean updateMembers(SportTeamMembersDto dto){
		
		boolean flag = true;
		try {
			dto.setLastUpdateTime(new Date());
			int i = sportTeamMembersMapperX.updateByPrimaryKeySelective(dto);
			if(i != 1){
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 根据队员id删除队员
	 * @param id
	 * @return
	 * @2015年11月16日
	 */
	public boolean deleteMembers(String id){
		
		boolean flag = true;
		try {
			SportTeamMembersDto dto = new SportTeamMembersDto();
			dto.setId(id);
			int i = sportTeamMembersMapperX.deleteMember(dto);
			if(i != 1){
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 查询所有队伍信息
	 * @return
	 * @2015年11月16日
	 */
	public List<SportTeamDto> findTeams() {
		
		List<SportTeamDto> stLst = null;
		try {
			stLst = sportTeamMapperX.findAllTeams();
		} catch (Exception e) {
			log.error("查询所有队伍信息发生异常", e);
		}
		return stLst;
	}
	
	/**
	 * 根据tid查询队伍信息
	 * @param tid
	 * @return
	 * @2015年11月16日
	 */
	public SportTeamDto findTeamById(String tid){
		
		SportTeamDto dto = null;
		try {
			dto = sportTeamMapperX.findById(tid);
		} catch (Exception e) {
			log.error("根据id查询队伍信息发生异常", e);
		}
		return dto;
	}
	
	/**
	 * 新增队伍信息
	 * @param dto
	 * @return
	 * @2015年11月16日
	 */
	public boolean addTeam(SportTeamDto dto){
		
		boolean flag = true;
		try {
			int i = sportTeamMapperX.insert(dto);
			if(i != 1){
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 根据tid删除队伍信息
	 * @param tid
	 * @return
	 * @2015年11月16日
	 */
	public boolean deleteTeam(String tid){
		
		boolean flag = true;
		try {
			int i = sportTeamMapperX.deleteByPrimaryKey(tid);
			if(i != 1){
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 更新队伍信息
	 * @param dto
	 * @return
	 * @2015年11月16日
	 */
	public boolean updateTeam(SportTeamDto dto){
		
		boolean flag = true;
		try {
			int i = sportTeamMapperX.updateByPrimaryKeySelective(dto);
			if(i != 1){
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	public SportTeamMembersDto findMember(String id){
		
		SportTeamMembersDto dto = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>(1);
			map.put("id", id);
			dto = sportTeamMembersMapperX.selectByPrimaryKey(id);
			if(dto != null){
				Map<String, Object> mapv02 = new HashMap<String, Object>(1);
				mapv02.put("id", dto.getTid());
				List<SportTeam> lis = teamDao.getByCriteriaPage(null, mapv02);
				if((lis != null) && (lis.size() == 1)){
					dto.setTeamName(lis.get(0).getName());
				}
			}
		} catch (Exception e) {
			log.error("根据id插叙队员信息发生异常", e);
		}
		return dto;
	}
}
