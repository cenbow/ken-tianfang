/**
 * 
 */
package com.tianfang.business.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.business.dto.SportHomeDto;
import com.tianfang.business.mapper.SportHomeMapper;
import com.tianfang.business.mapper.SportHomeMapperX;
import com.tianfang.business.pojo.SportHome;
import com.tianfang.business.pojo.SportHomeExample;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;

/**
 * 
 * @author wk.s
 * @date 2015年11月17日
 */
@Repository
public class SportHomeDao extends MyBatisBaseDao<SportHome> {

	@Getter
	@Autowired
	private SportHomeMapper mapper;
	
	@Resource
	private SportHomeMapperX sportHomeMapperX;
	private Logger log = LoggerFactory.getLogger(getClass());
	
	public boolean addHomeRecord(SportHomeDto dto) {

		boolean flag = true;
		try {
			dto.setStat(1);
			dto.setCreateTime(new Date());
			dto.setId(UUID.randomUUID().toString());
			SportHome sportHome = BeanUtils.createBeanByTarget(dto, SportHome.class);
			int c = mapper.insertSelective(sportHome);//sportHomeMapperX.insertSelective(dto);
			if(c!=1){
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public boolean deleteHRecord(String hid) {
		boolean flag = false;
		String[] ids = hid.split(",");
		if(ids.length>0){
			for (String str : ids) {
				SportHomeDto dto = new SportHomeDto();
				dto.setId(str);
				dto.setStat(0);
				flag = updateHRecord(dto);				
			}
			return flag;
		}else{
			SportHomeDto dto = new SportHomeDto();
			dto.setId(hid);
			dto.setStat(0);
			flag = updateHRecord(dto);
			return flag;
		}	
	}

	public boolean updateHRecord(SportHomeDto dto) {
		SportHome sportHome  = BeanUtils.createBeanByTarget(dto,SportHome.class);
		
		int result = mapper.updateByPrimaryKeySelective(sportHome);
		if(result>0){
			return true;
		}else{
			return false;
		}

	}

	/**
	 * 根据条件查询数据（分页）
	 * @param dto
	 * @param page
	 * @return
	 */
	public PageResult<SportHomeDto> findHRecords(SportHomeDto dto, PageQuery page) {
	
		List<SportHomeDto> relst = null;
		PageResult<SportHomeDto> pageRes = null;
		try {
			dto.setStat(1);
			Map<String, Object> map = new HashMap<String, Object>(2);
			map.put("dto", dto);
			if(page != null){
				map.put("page", page);
				long start = page.getStartNum();
				int size = page.getPageSize();
				map.put("start", start);
				map.put("size", size);
			}
			relst = sportHomeMapperX.findHRecords(map);
			if(relst != null){
				Long sum = sportHomeMapperX.countSum(map);
				page.setTotal(sum);
				pageRes = new PageResult<SportHomeDto>(page, relst);
			}
		} catch (Exception e) {
			log.error("根据条件查询sporthome信息发生异常", e);
		}
		return pageRes;
	}
	
	/**
	 * 根据条件查询记录（返回指定条最新的记录(order by create_time desc)）
	 * @param dto
	 * @param n
	 * @return
	 */
	public List<SportHomeDto> findNRecords(SportHomeDto dto, Integer n){
		SportHomeExample example = new SportHomeExample();
		SportHomeExample.Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(dto.getId())){
			criteria.andIdEqualTo(dto.getId());	
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		example.setOrderByClause(" pic_marked desc");
 		List<SportHome> dataList = mapper.selectByExample(example); 
 		List<SportHomeDto> resultList = BeanUtils.createBeanListByTarget(dataList, SportHomeDto.class);
		return resultList;
//		List<SportHomeDto> re = null;
//		try {
//			dto.setStat(1);
//			Map<String, Object> map = new HashMap<String, Object>(2);
//			map.put("dto", dto);
//			map.put("limit", n);
//			re = sportHomeMapperX.findHRecords(map);
//		} catch (Exception e) {
//			log.error("根据条件查询最新n条sporthome信息发生异常", e);
//		}
//		return re;
	}

	public List<SportHomeDto> selcetByMarked(SportHomeDto sportHome) {
		SportHomeExample example = new SportHomeExample();
		SportHomeExample.Criteria criteria = example.createCriteria();
		if(sportHome.getPicMarked()!=null){
			criteria.andPicMarkedEqualTo(sportHome.getPicMarked());
		}
		if(sportHome.getType()!=null){
			criteria.andTypeEqualTo(sportHome.getType());
		}
		example.setOrderByClause("create_time desc");
		List<SportHome> lis = mapper.selectByExample(example) ;
		if(lis.size()>0){
			return BeanUtils.createBeanListByTarget(lis, SportHomeDto.class);
		}
		return null;
	}
}
