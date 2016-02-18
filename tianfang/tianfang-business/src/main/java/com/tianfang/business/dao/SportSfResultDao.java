/**
 * 
 */
package com.tianfang.business.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.business.dto.SportSfResultDto;
import com.tianfang.business.dto.SportSfResultExDto;
import com.tianfang.business.mapper.SportQuestionAnswerExMapper;
import com.tianfang.business.mapper.SportSfResultExMapper;
import com.tianfang.business.mapper.SportSfResultMapper;
import com.tianfang.business.pojo.SportSfResult;
import com.tianfang.business.pojo.SportSfResultExample;
import com.tianfang.business.pojo.SportSfResultExample.Criteria;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;

/**
 * 反馈意见 结果
 * @author Mr
 *
 */
@Repository
public class SportSfResultDao extends MyBatisBaseDao<SportSfResult> {

	@Autowired
	@Getter
	private SportSfResultMapper mapper;
	
	@Autowired
	private SportSfResultExMapper exMapper;
	
	@Autowired
	private SportQuestionAnswerExMapper sfExmapper;
	
	
	public long save(SportSfResult sfResult) {
		sfResult.setId(UUID.randomUUID()+"");
		sfResult.setCreateTime(new Date());
		sfResult.setStat(DataStatus.ENABLED);
		long stat=0;
		try {
			stat= mapper.insert(sfResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}
	
	public void batchInsert(List<SportSfResult> results){
		if (null != results && results.size() > 0){
			for (SportSfResult result : results){
				result.setId(UUID.randomUUID()+"");
				result.setCreateTime(new Date());
				result.setStat(DataStatus.ENABLED);
			}
			exMapper.batchInsert(results);
		}
	}

	public long update(SportSfResult sfResult) {
		SportSfResult oldSfResult = mapper.selectByPrimaryKey(sfResult.getId());
		checkUp(sfResult,oldSfResult);
		sfResult.setStat(DataStatus.ENABLED);
		long stat=0;
		try {
			stat= mapper.updateByPrimaryKey(sfResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}

	private void checkUp(SportSfResult sfResult, SportSfResult oldSfResult) {
		if(!StringUtils.isBlank(sfResult.getSfUserId())){
			sfResult.setSfUserId(oldSfResult.getSfUserId());
		}
		if(!StringUtils.isBlank(sfResult.getSfAnswer())){
			sfResult.setSfAnswer(oldSfResult.getSfAnswer());
		}
		if(!StringUtils.isBlank(sfResult.getSfQuestion())){
			sfResult.setSfQuestion(oldSfResult.getSfQuestion());
		}
		if(sfResult.getCreateTime() == null){
			sfResult.setCreateTime(oldSfResult.getCreateTime());
		}
	}

	public long delete(String str) {
		SportSfResult sfResult = mapper.selectByPrimaryKey(str);
		sfResult.setStat(DataStatus.DISABLED);
		long stat = 0;
		try {
			stat = mapper.updateByPrimaryKey(sfResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}

	public List<SportSfResult> selectPageAll(SportSfResultDto resultDto,	PageQuery page) {
		SportSfResultExample example = new SportSfResultExample();
		Criteria criteria = example.createCriteria();
		byCriteria(resultDto,criteria);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}
	
	private void byCriteria(SportSfResultDto resultDto, Criteria criteria) {
		if(!StringUtils.isBlank(resultDto.getSfAnswer())){
			criteria.andSfAnswerEqualTo(resultDto.getSfAnswer());
		}
		if(!StringUtils.isBlank(resultDto.getSfQuestion())){
			criteria.andSfQuestionEqualTo(resultDto.getSfQuestion());
		}
		if(!StringUtils.isBlank(resultDto.getSfUserId())){
			criteria.andSfUserIdEqualTo(resultDto.getSfUserId());
		}
		if(!StringUtils.isBlank(resultDto.getId())){
			criteria.andIdEqualTo(resultDto.getId());
		}
		if(resultDto.getCreateTime() != null){
			criteria.andCreateTimeEqualTo(resultDto.getCreateTime());
		}
	}

	public long countPageAll(SportSfResultDto resultDto) {
		SportSfResultExample example = new SportSfResultExample();
		Criteria criteria = example.createCriteria();
		byCriteria(resultDto,criteria);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.countByExample(example);
	}

	public List<SportSfResultExDto> selecrOrSfCriteria(SportSfResultExDto resultDto, PageQuery page) {
		if(!StringUtils.isBlank(resultDto.getSfUname())){
			resultDto.setSfUname("%"+resultDto.getSfUname()+"%");
		} 
		if(page!=null){
			resultDto.setLimit(page.getPageSize());
			resultDto.setStart(page.getStartNum());
		}
		return sfExmapper.selectOrSfCriteria(resultDto);
	}

	public long countOrSfCriteria(SportSfResultExDto resultDto) {
		if(!StringUtils.isBlank(resultDto.getSfUname())){
			resultDto.setSfUname("%"+resultDto.getSfUname()+"%");
		}
		return sfExmapper.countOrSfCriterias(resultDto);
	}

	/**
	 * 统计当前问题下 某一答案的条数
	 * @param qId 问题id , aId 答案id
	 * @return
	 */
	public long countByAnswer(String qId ,String aId){
		SportSfResultExample example = new SportSfResultExample();
		Criteria criteria = example.createCriteria();
		if(qId != null){
			criteria.andSfQuestionEqualTo(qId);
		}
		if(aId != null){
			criteria.andSfAnswerLike("%"+aId+"%");
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.countByExample(example);
	}

}
