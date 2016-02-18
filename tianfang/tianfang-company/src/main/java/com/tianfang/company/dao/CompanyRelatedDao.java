/**
 * 
 */
package com.tianfang.company.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.ConGenerator;
import com.tianfang.common.util.StringUtils;
import com.tianfang.company.dto.SportCompanyRelatedDto;
import com.tianfang.company.mapper.SportCompanyRelatedExMapper;
import com.tianfang.company.mapper.SportCompanyRelatedMapper;
import com.tianfang.company.pojo.SportCompanyRelated;
import com.tianfang.company.pojo.SportCompanyRelatedExample;

/**
 * @author wk.s
 * 2015年12月4日
 */
@Repository
public class CompanyRelatedDao {

	@Resource
	private SportCompanyRelatedMapper mapper;
	@Resource
	private SportCompanyRelatedExMapper exMapper;
	private Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * 根据条件分页查询数据
	 * @param param
	 * @param page
	 * @return
	 */
	public List<SportCompanyRelatedDto> getCRInfoList(SportCompanyRelatedDto param, PageQuery page){
		SportCompanyRelatedExample example = new SportCompanyRelatedExample();
		SportCompanyRelatedExample.Criteria criteria = example.createCriteria();
		assemblyParams(param, criteria);
		if(page != null){
			example.setOrderByClause(" create_time asc limit "+page.getStartNum() +"," + page.getPageSize());
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return BeanUtils.createBeanListByTarget(mapper.selectByExample(example), SportCompanyRelatedDto.class);
	}
	
	/**
	 * 查询符合条件的记录条数
	 * @param param
	 * @return
	 */
	public long count(SportCompanyRelatedDto param){
		SportCompanyRelatedExample example = new SportCompanyRelatedExample();
		SportCompanyRelatedExample.Criteria criteria = example.createCriteria();
		assemblyParams(param, criteria);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.countByExample(example);
	}
	
	/**
	 * 新增数据
	 * @param data
	 * @return
	 */
	public boolean addCRData(SportCompanyRelatedDto data){
		boolean flag = true;
		try {
			SportCompanyRelated info = BeanUtils.createBeanByTarget(data, SportCompanyRelated.class);
			info.setStat(1);
			info.setCreateTime(new Date());
			info.setId(UUID.randomUUID().toString());
			int count = mapper.insert(info);
			if(count <= 0){
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 根据id更新数据
	 * @param data
	 * @return
	 */
	public boolean updateCRDataById(SportCompanyRelatedDto data){
		boolean flag = true;
		try {
			data.setLastUpdateTime(new Date());
			SportCompanyRelated param = BeanUtils.createBeanByTarget(data, SportCompanyRelated.class);
			int c = mapper.updateByPrimaryKeySelective(param);
			if(c != 1){
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 根据id逻辑删除数据
	 * @param data
	 * @return
	 */
	public boolean deleteCRDataById(SportCompanyRelatedDto data){
		boolean flag = true;
		try {
			SportCompanyRelated param = BeanUtils.createBeanByTarget(data, SportCompanyRelated.class);
			param.setStat(0);
			param.setLastUpdateTime(new Date());
			int c = mapper.updateByPrimaryKeySelective(param);
			if(c != 1){
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 逻辑批量删除数据
	 * @param ids
	 * @return
	 */
	public boolean deleteByIds(String ids){
		boolean flag = true;
		try {
			Map<String, Object> map = new HashMap<String, Object>(2);
			ids = ConGenerator.getCons(ids, ",");
			Date updateTime = new Date();
			map.put("ids", ids);
			map.put("updateTime", updateTime);
			int c = exMapper.deleteByIds(map);
			if(c < 1){
				flag = false;
				log.error("批量删除公司相关数据操作没有改变任何数据");
			}
		} catch (Exception e) {
			flag = false;
			log.error("批量删除公司公告相关数据发生异常", e);
		}
		return flag;
	}
	
	/**
	 * 根据id查询数据
	 * @param id
	 * @return
	 */
	public SportCompanyRelatedDto getCRInfoById(String id){
		SportCompanyRelatedDto dto = null;
		try {
			SportCompanyRelated cr = mapper.selectByPrimaryKey(id);
			if(cr != null){
				dto = BeanUtils.createBeanByTarget(cr, SportCompanyRelatedDto.class);
			}
		} catch (Exception e) {
			log.error("根据id查询companyrelated数据发生异常", e);
		}
		return dto;
	}

	public List<SportCompanyRelatedDto> findCompanies(SportCompanyRelatedDto param) {
		SportCompanyRelatedExample example = new SportCompanyRelatedExample();
		SportCompanyRelatedExample.Criteria criteria = example.createCriteria();
		assemblyParams(param, criteria);
		example.setOrderByClause(" create_time desc");
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return BeanUtils.createBeanListByTarget(mapper.selectByExample(example), SportCompanyRelatedDto.class);
	}
	
	/**
	 * 封装查询参数组装条件
	 * @param dto
	 * @param criteria
	 * @author xiang_wang
	 * 2015年12月4日下午4:19:07
	 */
	private void assemblyParams(SportCompanyRelatedDto dto, SportCompanyRelatedExample.Criteria criteria) {
		if (StringUtils.isNotBlank(dto.getId())) {
			criteria.andIdEqualTo(dto.getId());
		}
		if (StringUtils.isNotBlank(dto.getTitle())) {
			criteria.andTitleLike("%" + dto.getTitle() + "%");
		}
		if(StringUtils.isNotBlank(dto.getEnTitle())){
			criteria.andEnTitleLike("%" + dto.getEnTitle() + "%");
		}
		if (null != dto.getType() && dto.getType() > 0) {
			criteria.andTypeEqualTo(dto.getType());
		}
		if (dto.getStat() != null) {
			if (dto.getStat().intValue() == 0 || dto.getStat().intValue() == 1) {
				criteria.andStatEqualTo(dto.getStat());
			}
		} else {
			criteria.andStatEqualTo(DataStatus.ENABLED);
		}
	}
}
