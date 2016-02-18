package com.tianfang.business.dao;

import java.util.Date;
import java.util.UUID;
import java.util.List;
import java.util.Map;

import lombok.Getter;

import org.apache.zookeeper.Op.Create;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.business.dto.SportQuestionAnswerDto;
import com.tianfang.business.dto.SportSfQuestionExDto;
import com.tianfang.business.mapper.SportQuestionAnswerExMapper;
import com.tianfang.business.mapper.SportQuestionAnswerMapper;
import com.tianfang.business.pojo.SportQuestionAnswer;
import com.tianfang.business.pojo.SportQuestionAnswerExample;
import com.tianfang.business.pojo.SportQuestionAnswerExample.Criteria;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;

/**
 * 问题答案关联表
 * @author Mr
 *
 */
@Repository
public class SportQuestionAnswerDao extends MyBatisBaseDao<SportQuestionAnswer> {

	@Autowired
	private SportQuestionAnswerMapper mapper;
	@Autowired
	private SportQuestionAnswerExMapper mapperEx;
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public Object getMapper() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 保存
	 * @param sportQuestion
	 * @return
	 */
	public long save(SportQuestionAnswer sportQuestion){
		sportQuestion.setId(UUID.randomUUID()+"");
		sportQuestion.setCreateTime(new Date());
		sportQuestion.setLastUpdateTime(new Date());
		sportQuestion.setStat(DataStatus.ENABLED);
		long stat =0;
		try {
			stat = mapper.insert(sportQuestion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}
	
	/**
	 * 逻辑删除
	 * @param id
	 * @return
	 */
	public long delete(String id){
		SportQuestionAnswer sportQuestion= mapper.selectByPrimaryKey(id);
		sportQuestion.setStat(DataStatus.DISABLED);
		long stat = 0;
		try {
			stat= mapper.updateByPrimaryKey(sportQuestion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}

	/**
	 * 根据id更新questionAnswer
	 * @param questionAnswer
	 * @return
	 */
	public boolean updateById(SportQuestionAnswer questionAnswer) {
		boolean flag = false;
		SportQuestionAnswerExample example = new SportQuestionAnswerExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(questionAnswer.getId());
		int i = mapper.updateByExample(questionAnswer, example);
		if(i == 1){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 条件查询
	 * @param map
	 * @param page
	 * @return
	 */
	public List<SportQuestionAnswer> list(Map<String, Object> map, PageQuery page){
		
		List<SportQuestionAnswer> lst = null;
		SportQuestionAnswerExample example = new SportQuestionAnswerExample();
		Criteria criteria = example.createCriteria();
		assebleCons(criteria, map);
		StringBuffer sbf = new StringBuffer();
		sbf.append("create_time desc");
		if(page != null){
			sbf.append(" limit").append(page.getStartNum()).append(",").append(page.getPageSize());
		}
		example.setOrderByClause(sbf.toString());
		try {
			lst = mapper.selectByExample(example);
		} catch (Exception e) {
			log.error("条件查询发生异常", e);
		}
		return lst;
	}
	
	/**
	 * 查询符合条件的记录条数
	 * @param map
	 * @return
	 */
	public Integer countByCons(Map<String, Object> map){
		
		SportQuestionAnswerExample example = new SportQuestionAnswerExample();
		Criteria criteria = example.createCriteria();
		assebleCons(criteria, map);
		Integer c = mapper.countByExample(example);
		return c;
	}
	
	/**
	 * 条件组装
	 * @param criteria
	 * @param map
	 */
	public void assebleCons(Criteria criteria, Map<String, Object> map){
		
		if(map != null){
			@SuppressWarnings("unchecked")
			List<String> ids = (List<String>) map.get("ids");
			if(ids.size() > 0){
				criteria.andIdIn(ids);
			}
			String sfAnswer = (String) map.get("sfAnswer");
			if(StringUtils.isNotBlank(sfAnswer)){
				criteria.andSfAnswerEqualTo(sfAnswer);
			}
			String sfQuestion = (String) map.get("sfQuestion");
			if(StringUtils.isNotBlank(sfQuestion)){
				criteria.andSfQuestionEqualTo(sfQuestion);
			}
			String createTime = (String) map.get("createTime");
			if(StringUtils.isNotBlank(createTime)){
				criteria.andCreateTimeEqualTo(DateUtils.parse(createTime, "yyyy-MM-dd"));
			}
			String lastUpdateTime = (String) map.get("lastUpdateTime");
			if(StringUtils.isNotBlank(lastUpdateTime)){
				criteria.andLastUpdateTimeEqualTo(DateUtils.parse(lastUpdateTime, "yyyy-MM-dd"));
			}
			String stat = (String) map.get("stat");
			if(StringUtils.isNotBlank(stat)){
				criteria.andStatEqualTo(Integer.parseInt(stat));
			}
			
		}

	}

	public List<SportQuestionAnswer> selectByCriteria(SportQuestionAnswerDto dto) {
		SportQuestionAnswerExample example = new SportQuestionAnswerExample();
		Criteria criteria = example.createCriteria();
		byCriteria(criteria,dto);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}

	private void byCriteria(Criteria criteria, SportQuestionAnswerDto dto) {
		if(!StringUtils.isBlank(dto.getId())){
			criteria.andIdEqualTo(dto.getId());
		}
		if(!StringUtils.isBlank(dto.getSfAnswer())){
			criteria.andSfAnswerEqualTo(dto.getSfAnswer());
		}
		if(!StringUtils.isBlank(dto.getSfQuestion())){
			criteria.andSfQuestionEqualTo(dto.getSfQuestion());
		}
	}

	public List<SportSfQuestionExDto> selectInfo(SportSfQuestionExDto dto) {
		return mapperEx.selectInfo(dto);
	}

}
