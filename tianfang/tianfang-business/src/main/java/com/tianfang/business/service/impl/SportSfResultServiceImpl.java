package com.tianfang.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.business.dao.SportSfAnswerDao;
import com.tianfang.business.dao.SportSfResultDao;
import com.tianfang.business.dto.SportSfResultDto;
import com.tianfang.business.dto.SportSfResultExDto;
import com.tianfang.business.pojo.SportSfAnswer;
import com.tianfang.business.pojo.SportSfResult;
import com.tianfang.business.service.ISportSfResultService;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
@Service
public class SportSfResultServiceImpl implements ISportSfResultService {

	@Autowired
	private SportSfResultDao sfResultDao;
	
	@Autowired
	private SportSfAnswerDao sportSfAnswerDao;
		
	@Override
	public long save(SportSfResultDto sfResultDto) {
		SportSfResult sfResult = BeanUtils.createBeanByTarget(sfResultDto, SportSfResult.class);
		return sfResultDao.save(sfResult);
	}

	@Override
	public long update(SportSfResultDto resultDto) {
		SportSfResult sfResult = BeanUtils.createBeanByTarget(resultDto, SportSfResult.class);
		return sfResultDao.update(sfResult);
	}

	@Override
	public long delete(String id) {
		String[] ids = id.split(",");
		long stat =0;
		for (String str : ids) {
			stat = sfResultDao.delete(str);
			if(stat < 1){
				return stat ;
			}
		}
		return stat;
	}

	@Override
	public PageResult<SportSfResultDto> selectPageAll(SportSfResultDto resultDto, PageQuery page) {
		List<SportSfResult> sfResult = sfResultDao.selectPageAll(resultDto,page);
		List<SportSfResultDto> sfResultDto = BeanUtils.createBeanListByTarget(sfResult, SportSfResultDto.class);
		long total=0;
		if(page!=null){
			total = sfResultDao.countPageAll(resultDto);
			page.setTotal(total);
		}
		return new PageResult<SportSfResultDto>(page, sfResultDto);
	}

	@Override
	public void batchInsert(List<SportSfResultDto> results) {
		if (null == results || results.size() == 0){
			throw new RuntimeException("对不 起,批量保存操作数据源为空");
		}
		List<SportSfResult> datas = BeanUtils.createBeanListByTarget(results, SportSfResult.class);
		sfResultDao.batchInsert(datas);
	}

	@Override
	public PageResult<SportSfResultExDto> selectOrSfCriteria(SportSfResultExDto resultDto, PageQuery page) {
		List<SportSfResultExDto> sfResultDto = sfResultDao.selecrOrSfCriteria(resultDto,page);
		for (SportSfResultExDto sfResult : sfResultDto) {
			String[] sfAnswerId = sfResult.getSfAnswer().split(",");
			String AnswerName ="";
			for (String sid : sfAnswerId) {
				SportSfAnswer answer = sportSfAnswerDao.selectById(sid);
				if(answer!=null){
					AnswerName += answer.getAnswerName()+",";
				}
			}
			if(AnswerName.contains(",")){
				sfResult.setAnswerName(AnswerName.substring(0,AnswerName.length()-1));
			}
		}
		if(page!=null){
			long count= sfResultDao.countOrSfCriteria(resultDto);
			page.setTotal(count);
		}
		return new PageResult<SportSfResultExDto>(page,sfResultDto);
	}
}