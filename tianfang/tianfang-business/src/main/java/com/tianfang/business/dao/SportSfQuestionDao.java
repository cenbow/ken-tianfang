package com.tianfang.business.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.business.mapper.SportSfQuestionMapper;
import com.tianfang.business.pojo.SportSfQuestion;
import com.tianfang.business.pojo.SportSfQuestionExample;
import com.tianfang.business.pojo.SportSfQuestionExample.Criteria;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;

@Repository
public class SportSfQuestionDao extends MyBatisBaseDao<SportSfQuestion> {

	@Autowired
	@Getter
	private SportSfQuestionMapper mapper;

	public long save(SportSfQuestion sportSfQuestion) {
		sportSfQuestion.setId(UUID.randomUUID()+"");
		sportSfQuestion.setCreateTime(new Date());
		sportSfQuestion.setLastUpdateTime(new Date());
		sportSfQuestion.setStat(DataStatus.ENABLED);
		long stat=0;
		try {
			stat = mapper.insert(sportSfQuestion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}

	public long update(SportSfQuestion sportSfQuestion) {
		SportSfQuestion oldSportsfQuestion = mapper.selectByPrimaryKey(sportSfQuestion.getId());
		checkUp(sportSfQuestion,oldSportsfQuestion);
		sportSfQuestion.setLastUpdateTime(new Date());
		sportSfQuestion.setStat(DataStatus.ENABLED);
		long stat=0;
		try {
			stat =mapper.updateByPrimaryKey(sportSfQuestion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}
	
	public long delete(String id){
		SportSfQuestion sportSfQuestion= mapper.selectByPrimaryKey(id);
		sportSfQuestion.setStat(DataStatus.DISABLED);
		long stat =0;
		try {
			stat= mapper.updateByPrimaryKey(sportSfQuestion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}

	private void checkUp(SportSfQuestion sportSfQuestion,SportSfQuestion oldSportsfQuestion) {
		if(sportSfQuestion.getSfType()==null || sportSfQuestion.getSfType().equals("")){
			sportSfQuestion.setSfType(oldSportsfQuestion.getSfType());
		}
		if(sportSfQuestion.getSfSelectType() == null){
			sportSfQuestion.setSfSelectType(oldSportsfQuestion.getSfSelectType());
		}
		if(sportSfQuestion.getSfQuestionContent() == null || sportSfQuestion.getSfQuestionContent().equals("")){
			sportSfQuestion.setSfQuestionContent(oldSportsfQuestion.getSfQuestionContent());
		}
		if(sportSfQuestion.getCreateTime() == null || sportSfQuestion.getCreateTime().equals("")){
			sportSfQuestion.setCreateTime(oldSportsfQuestion.getCreateTime());
		}
	}

	public List<SportSfQuestion> selectByCriteria(SportSfQuestion sportSfQuestion, PageQuery page) {
		SportSfQuestionExample example = new SportSfQuestionExample();
		Criteria criteria= example.createCriteria();
		selectCheckUp(sportSfQuestion,criteria);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		if(page!=null){
			example.setOrderByClause(" create_time desc limit "+page.getStartNum()+","+page.getPageSize());
		}
		return mapper.selectByExample(example);
	}

	private void selectCheckUp(SportSfQuestion sportSfQuestion,	Criteria criteria) {
		if(sportSfQuestion.getSfType()!=null && !sportSfQuestion.getSfType().equals("")){
			criteria.andSfTypeEqualTo(sportSfQuestion.getSfType());
		}
		if(sportSfQuestion.getSfSelectType()!= null && !sportSfQuestion.getSfSelectType().equals("")){
			criteria.andSfSelectTypeEqualTo(sportSfQuestion.getSfSelectType());
		}
		if(sportSfQuestion.getSfQuestionContent()!=null && !sportSfQuestion.getSfQuestionContent().equals("")){
			criteria.andSfQuestionContentLike("%"+sportSfQuestion+"%");
		}
		if(sportSfQuestion.getCreateTime()!=null && !sportSfQuestion.getCreateTime().equals("")){
			criteria.andCreateTimeEqualTo(sportSfQuestion.getCreateTime());
		}
	}

	public long countByCriteria(SportSfQuestion sportSfQuestion, PageQuery page) {
		SportSfQuestionExample example = new SportSfQuestionExample();
		Criteria criteria= example.createCriteria();
		selectCheckUp(sportSfQuestion,criteria);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.countByExample(example);
	}

	public SportSfQuestion selectById(String id) {
		return mapper.selectByPrimaryKey(id);
	}
}
