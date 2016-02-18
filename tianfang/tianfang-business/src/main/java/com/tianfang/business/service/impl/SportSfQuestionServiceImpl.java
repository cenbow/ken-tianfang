package com.tianfang.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.business.dao.SportSfQuestionDao;
import com.tianfang.business.dto.SportSfQuestionDto;
import com.tianfang.business.pojo.SportSfQuestion;
import com.tianfang.business.service.ISportSfQuestionService;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;

@Service
public class SportSfQuestionServiceImpl implements ISportSfQuestionService {

	@Autowired
	private SportSfQuestionDao sfQuestionDao;

	@Override
	public long save(SportSfQuestionDto sportSfQuestionDto) {
		SportSfQuestion sportSfQuestion  = BeanUtils.createBeanByTarget(sportSfQuestionDto, SportSfQuestion.class);
		long stat = sfQuestionDao.save(sportSfQuestion);
		return stat ;
	}

	@Override
	public long update(SportSfQuestionDto sportSfQuestionDto) {
		SportSfQuestion sportSfQuestion  = BeanUtils.createBeanByTarget(sportSfQuestionDto, SportSfQuestion.class);
		long stat = sfQuestionDao.update(sportSfQuestion);
		return stat ;
	}

	@Override
	public long delete(String ids) {
		String[] id = ids.split(",");
		long stat=0;
		for (String str : id) {
			try {
				stat= sfQuestionDao.delete(str);
				if(stat<1){
					return stat;
				}
			} catch (Exception e) {
				throw new RuntimeException("删除出错");
			}
		}
		return stat;
	}

	@Override
	public PageResult<SportSfQuestionDto> selectPageAll(SportSfQuestionDto sportSfQuestionDto, PageQuery page) {
		SportSfQuestion sportSfQuestion = BeanUtils.createBeanByTarget(sportSfQuestionDto, SportSfQuestion.class);
		List<SportSfQuestion> sfQuestionList= sfQuestionDao.selectByCriteria(sportSfQuestion,page);
		List<SportSfQuestionDto> sfQuestionListDto = BeanUtils.createBeanListByTarget(sfQuestionList, SportSfQuestionDto.class);
		if(page != null){
			long total = sfQuestionDao.countByCriteria(sportSfQuestion,page); 
			page.setTotal(total);
		}
		return new PageResult<SportSfQuestionDto>(page, sfQuestionListDto);
	}

	@Override
	public SportSfQuestionDto selectById(String id) {
		SportSfQuestion sfQuestion = sfQuestionDao.selectById(id);
		return BeanUtils.createBeanByTarget(sfQuestion, SportSfQuestionDto.class);
	}
	
	@Override
	public List<SportSfQuestionDto> selectAll(SportSfQuestionDto sportSfQuestionDto) {
		SportSfQuestion sportSfQuestion = BeanUtils.createBeanByTarget(sportSfQuestionDto, SportSfQuestion.class);
		return BeanUtils.createBeanListByTarget(sfQuestionDao.selectByCriteria(sportSfQuestion,null), SportSfQuestionDto.class);
	}
}
