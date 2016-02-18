package com.tianfang.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.business.dao.SportQuestionAnswerDao;
import com.tianfang.business.dao.SportSfAnswerDao;
import com.tianfang.business.dao.SportSfResultDao;
import com.tianfang.business.dto.SportSfAnswerDto;
import com.tianfang.business.dto.SportSfResultDto;
import com.tianfang.business.pojo.SportSfAnswer;
import com.tianfang.business.pojo.SportSfResult;
import com.tianfang.business.service.ISportSfAnswerService;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
@Service
public class SportSfAnswerServiceImpl implements ISportSfAnswerService {
	
	@Autowired
	private SportSfAnswerDao sportSfAnswerDao;
	@Autowired
	private SportQuestionAnswerDao sportQuestionAnswerDao;
	@Autowired
	private SportSfResultDao sportSfResultDao;

	@Override
	public long save(SportSfAnswerDto sportSfAnswerDto) {
		SportSfAnswer sfAnswer = BeanUtils.createBeanByTarget(sportSfAnswerDto, SportSfAnswer.class);	
		long stat = sportSfAnswerDao.save(sfAnswer);
		return stat;
	}

	@Override
	public long update(SportSfAnswerDto sportSfAnswerDto) {
		SportSfAnswer sfAnswer = BeanUtils.createBeanByTarget(sportSfAnswerDto, SportSfAnswer.class);	
		long stat = sportSfAnswerDao.update(sfAnswer);
		return stat;
	}

	@Override
	public long delete(String ids) {
		String[] id =ids.split(",");
		long stat=0;
		for (String str : id) {
			try {
				stat =sportSfAnswerDao.delete(str);
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
	public PageResult<SportSfAnswerDto> selectPageAll(SportSfAnswerDto sportSfAnswerDto, PageQuery page) {
		List<SportSfAnswer> answerList = sportSfAnswerDao.selectPageAll(sportSfAnswerDto,page);
		List<SportSfAnswerDto> answerListDto = BeanUtils.createBeanListByTarget(answerList, SportSfAnswerDto.class);
		if(page!=null){
			long total = sportSfAnswerDao.selectCount(sportSfAnswerDto);
			page.setTotal(total);
		}
		return new PageResult<SportSfAnswerDto>(page,answerListDto);
	}

	@Override
	public SportSfAnswerDto selectById(String id) {
		SportSfAnswer SportSfAnswer = sportSfAnswerDao.selectById(id);
		return BeanUtils.createBeanByTarget(SportSfAnswer, SportSfAnswerDto.class);
	}

	@Override
	public List<SportSfAnswerDto> selectByIds(List<String> ids, PageQuery page) {
		
		return sportSfAnswerDao.selectByIds(ids, page);
	}

	@Override
	public List<SportSfAnswerDto> selectAll() {
		return sportSfAnswerDao.selectAll();
	}
	
	@Override
	public List<SportSfAnswerDto> selectList(SportSfAnswerDto sportSfAnswerDto) {
		List<SportSfAnswer> answer = sportSfAnswerDao.selectPageAll(sportSfAnswerDto, null);
		List<SportSfAnswerDto> sfAnswerDto =BeanUtils.createBeanListByTarget(answer,SportSfAnswerDto.class);
		SportSfResultDto res = new SportSfResultDto();
		res.setSfQuestion(sportSfAnswerDto.getQuestion());
		List<SportSfResult> resultList =  sportSfResultDao.selectPageAll(res,null);     //当前问题 对应的反馈信息集合
		double size = 0;                                                                //循环统计  答案总票数
		for (SportSfResult resLis : resultList) {										
			String[] aid =resLis.getSfAnswer().split(",");
			size += aid.length;
		}
		if(resultList.size()>0){
			for (SportSfAnswerDto sfAnswer : sfAnswerDto) {
				sfAnswer.setNumber(sportSfResultDao.countByAnswer(sfAnswer.getQuestion(), sfAnswer.getId()));     //获取某一固定答案的票数
				double number =sfAnswer.getNumber(); 
				if(number!=0){
					sfAnswer.setPercent((number/size)*100);
				}else{
					sfAnswer.setPercent(0);
				}
			}
		}
		
		return sfAnswerDto;
	}
}