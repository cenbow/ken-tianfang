/**
 * 
 */
package com.tianfang.business.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.business.dao.SportQuestionAnswerDao;
import com.tianfang.business.dto.SportQuestionAnswerDto;
import com.tianfang.business.dto.SportSfQuestionDto;
import com.tianfang.business.dto.SportSfQuestionExDto;
import com.tianfang.business.pojo.SportQuestionAnswer;
import com.tianfang.business.pojo.SportSfQuestion;
import com.tianfang.business.service.ISportSfQAService;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;

/**
 * @author wk.s
 *
 */
@Service
public class ISportSfQAServiceImpl implements ISportSfQAService {

	@Autowired
	private SportQuestionAnswerDao questionAnswerDao;
	
	@Override
	public boolean add(SportQuestionAnswerDto dto) {
		boolean flag = false;
		SportQuestionAnswer sportQuestion = BeanUtils.createBeanByTarget(dto, SportQuestionAnswer.class);
		Long i = questionAnswerDao.save(sportQuestion);
		if(i == 1){
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean update(SportQuestionAnswerDto dto) {
		
		SportQuestionAnswer sportQuestion = BeanUtils.createBeanByTarget(dto, SportQuestionAnswer.class);
		boolean flag = questionAnswerDao.updateById(sportQuestion);
		return flag;
	}

	@Override
	public List<SportQuestionAnswerDto> list(Map<String, Object> map, PageQuery page) {
		
		List<SportQuestionAnswer> lst = questionAnswerDao.list(map, page);
		List<SportQuestionAnswerDto> relst = BeanUtils.createBeanListByTarget(lst, SportQuestionAnswerDto.class);
		return relst;
	}

	@Override
	public boolean delete(String id) {
		
		boolean flag = false;
		long i = questionAnswerDao.delete(id);
		if(i==1){
			flag = true;
		}
		return flag;
	}

	@Override
	public List<SportQuestionAnswerDto> selectByCriteria(SportQuestionAnswerDto dto) {
		List<SportQuestionAnswer> lis = questionAnswerDao.selectByCriteria(dto);
		return BeanUtils.createBeanListByTarget(lis, SportQuestionAnswerDto.class);
	}

	@Override
	public List<SportSfQuestionExDto> selectInfo(SportSfQuestionExDto dto) {
		return questionAnswerDao.selectInfo(dto);
	}

}
