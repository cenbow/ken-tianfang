package com.tianfang.business.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.business.dto.SportSfAnswerDto;
import com.tianfang.business.mapper.SportSfAnswerMapper;
import com.tianfang.business.pojo.SportSfAnswer;
import com.tianfang.business.pojo.SportSfAnswerExample;
import com.tianfang.business.pojo.SportSfAnswerExample.Criteria;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;

/**
 * 答案
 * @author Mr
 *
 */
@Repository
public class SportSfAnswerDao extends MyBatisBaseDao<SportSfAnswer> {

	@Autowired
	@Getter
	private SportSfAnswerMapper mapper; 

	public long save(SportSfAnswer sfAnswer) {
		sfAnswer.setId(UUID.randomUUID()+"");
		sfAnswer.setCreateTime(new Date());
		sfAnswer.setLastUpdateTime(new Date());
		sfAnswer.setStat(DataStatus.ENABLED);
		long stat=0;
		try {
			stat = mapper.insert(sfAnswer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}

	public long delete(String id){
		SportSfAnswer sfAnswer= mapper.selectByPrimaryKey(id);
		sfAnswer.setStat(DataStatus.DISABLED);
		long stat =0;
		try {
			stat= mapper.updateByPrimaryKey(sfAnswer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}
	
	public long update(SportSfAnswer sfAnswer) {
		SportSfAnswer oldSfAnswer = mapper.selectByPrimaryKey(sfAnswer.getId());
		checkUp(sfAnswer,oldSfAnswer);
		sfAnswer.setStat(DataStatus.ENABLED);
		sfAnswer.setLastUpdateTime(new Date());
		long stat = 0;
		try {
			stat = mapper.updateByPrimaryKey(sfAnswer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}

	private void checkUp(SportSfAnswer sfAnswer, SportSfAnswer oldSfAnswer) {
		if(sfAnswer.getAnswerName() == null || sfAnswer.getAnswerName().equals("")){
			sfAnswer.setAnswerName(oldSfAnswer.getAnswerName());
		}
		if(sfAnswer.getCreateTime() ==null || sfAnswer.getCreateTime().equals("")){
			sfAnswer.setCreateTime(oldSfAnswer.getCreateTime());
		}
		if(sfAnswer.getQuestion() == null || sfAnswer.getQuestion().equals("")){
			sfAnswer.setQuestion(oldSfAnswer.getQuestion());
		}
	}

	public List<SportSfAnswer> selectPageAll(SportSfAnswerDto sportSfAnswerDto,PageQuery page) {
		SportSfAnswerExample example = new SportSfAnswerExample();
		Criteria criteria= example.createCriteria();
		checkUp(sportSfAnswerDto,criteria);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		if(page!=null){
			example.setOrderByClause(" create_time desc limit "+page.getStartNum()+","+page.getPageSize());
		}
		return mapper.selectByExample(example);
	}

	private void checkUp(SportSfAnswerDto sportSfAnswerDto,Criteria criteria) {
		if(sportSfAnswerDto.getAnswerName() !=null && !sportSfAnswerDto.equals("")){
			criteria.andAnswerNameEqualTo(sportSfAnswerDto.getAnswerName());
		}
		if(sportSfAnswerDto.getCreateTime() != null && !sportSfAnswerDto.getCreateTime().equals("")){
			criteria.andCreateTimeEqualTo(sportSfAnswerDto.getCreateTime());
		}
		if(sportSfAnswerDto.getQuestion() != null && !sportSfAnswerDto.getQuestion().equals("")){
			criteria.andQuestionEqualTo(sportSfAnswerDto.getQuestion());
		}
	}

	public long selectCount(SportSfAnswerDto sportSfAnswerDto) {
		SportSfAnswerExample example = new SportSfAnswerExample();
		Criteria criteria= example.createCriteria();
		checkUp(sportSfAnswerDto,criteria);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.countByExample(example);
	}

	public SportSfAnswer selectById(String id) {
		return mapper.selectByPrimaryKey(id);
	}
	public List<SportSfAnswerDto> selectByIds(List<String> ids, PageQuery page) {
		SportSfAnswerExample example = new SportSfAnswerExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(ids);
		criteria.andStatEqualTo(1);
		if(page != null){
			example.setOrderByClause("create_time desc limit " + page.getStartNum() + "," + page.getPageSize());
		}else{
			example.setOrderByClause("create_time desc");
		}
		List<SportSfAnswer> lst = mapper.selectByExample(example);
		List<SportSfAnswerDto> re = BeanUtils.createBeanListByTarget(lst, SportSfAnswerDto.class);
		return re;
	}
	
	public List<SportSfAnswerDto> selectAll(){
		SportSfAnswerExample example = new SportSfAnswerExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		example.setOrderByClause("create_time desc");
		List<SportSfAnswer> lst = mapper.selectByExample(example);
		return  BeanUtils.createBeanListByTarget(lst, SportSfAnswerDto.class);
	}
}
