package com.tianfang.business.dao;

import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.business.dto.SportSuggestionUserDto;
import com.tianfang.business.mapper.SportSuggestionUserMapper;
import com.tianfang.business.pojo.SportSuggestionUser;
import com.tianfang.business.pojo.SportSuggestionUserExample;
import com.tianfang.business.pojo.SportSuggestionUserExample.Criteria;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.UUIDGenerator;

@Repository
public class SportSuggestionUserDao extends MyBatisBaseDao<SportSuggestionUser> {

	@Autowired
	@Getter
	private SportSuggestionUserMapper mapper;
	
	public long selectCount(SportSuggestionUserDto suggestionUser) {
		SportSuggestionUserExample example = new SportSuggestionUserExample();
		Criteria criteria = example.createCriteria();
		selectCheckUp(suggestionUser,criteria);
		return mapper.countByExample(example);
	}
	
	public List<SportSuggestionUser> getByCriteria(SportSuggestionUserDto suggestionUser,PageQuery page){
		SportSuggestionUserExample example = new SportSuggestionUserExample();
		Criteria criteria = example.createCriteria();
		selectCheckUp(suggestionUser,criteria);
		if(page!=null){
			example.setOrderByClause(" create_time desc limit "+page.getStartNum()+","+page.getPageSize());
		}
		return mapper.selectByExample(example);
	}

	public String add(SportSuggestionUser suggestionUser) {
		String id = UUIDGenerator.getUUID();
		try {
			suggestionUser.setId(id);
			insert(suggestionUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public long delete(String id) {
		SportSuggestionUser oldSuggestionUser = mapper.selectByPrimaryKey(id);
		oldSuggestionUser.setStat(DataStatus.DISABLED);
		long stat=0;
		try {
			stat = mapper.updateByPrimaryKey(oldSuggestionUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}
	
	public long update(SportSuggestionUser suggestionUser) {
		SportSuggestionUser oldsugges = mapper.selectByPrimaryKey(suggestionUser.getId());
		checkUp(oldsugges,suggestionUser);
		suggestionUser.setStat(DataStatus.ENABLED);
		suggestionUser.setLastUpdateTime(new Date());
		try {
			mapper.updateByPrimaryKey(suggestionUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 检查对象 为空替换
	 * @param oldsugges 旧对象
	 * @param suggestionUser 更新对象
	 */
	private void checkUp(SportSuggestionUser oldsugges, SportSuggestionUser suggestionUser) {
		if(suggestionUser.getSfType()==null || suggestionUser.getSfType().equals("")){
			suggestionUser.setSfType(oldsugges.getSfType());
		}
		if(suggestionUser.getSfUname() == null || suggestionUser.getSfUname().equals("")){
			suggestionUser.setSfUname(oldsugges.getSfUname());
		}
		if(suggestionUser.getSfPhone() == null || suggestionUser.getSfPhone().equals("")){
			suggestionUser.setSfPhone(oldsugges.getSfPhone());
		}
		if(suggestionUser.getSfEmail() == null || suggestionUser.getSfEmail().equals("")){
			suggestionUser.setSfEmail(oldsugges.getSfEmail());
		}
		if(suggestionUser.getSfFeedback() == null || suggestionUser.getSfFeedback().equals("")){
			suggestionUser.setSfFeedback(oldsugges.getSfFeedback());
		}
		if(suggestionUser.getSfPicture() == null || suggestionUser.getSfPicture().equals("")){
			suggestionUser.setSfPicture(oldsugges.getSfFeedback());
		}
		if(suggestionUser.getCreateTime() == null || suggestionUser.getCreateTime().equals("")){
			suggestionUser.setCreateTime(oldsugges.getCreateTime());
		}
	}
	/**
	 * 查询时判断条件是否为空过滤
	 * @param suggestionUser 查询对象
	 * @param criteria   
	 */
	private void selectCheckUp(SportSuggestionUserDto suggestionUser, Criteria criteria) {
		if(suggestionUser.getSfType()!=null &&! suggestionUser.getSfType().equals("")){
			criteria.andSfTypeEqualTo(suggestionUser.getSfType());
		}
		if(suggestionUser.getSfUname() != null && !suggestionUser.getSfUname().equals("")){
			criteria.andSfUnameEqualTo(suggestionUser.getSfUname());
		}
		if(suggestionUser.getSfPhone() != null && !suggestionUser.getSfPhone().equals("")){
			criteria.andSfPhoneEqualTo(suggestionUser.getSfPhone());
		}
		if(suggestionUser.getSfEmail() != null && !suggestionUser.getSfEmail().equals("")){
			criteria.andSfEmailEqualTo(suggestionUser.getSfEmail());
		}
		if(suggestionUser.getSfFeedback() != null && !suggestionUser.getSfFeedback().equals("")){
			criteria.andSfFeedbackEqualTo(suggestionUser.getSfFeedback());
		}
		if(suggestionUser.getSfPicture() != null && !suggestionUser.getSfPicture().equals("")){
			criteria.andSfPhoneEqualTo(suggestionUser.getSfPhone());
		}
		if(suggestionUser.getCreateTime() != null && !suggestionUser.getCreateTime().equals("")){
			criteria.andCreateTimeEqualTo(suggestionUser.getCreateTime());
		}
	}

}
