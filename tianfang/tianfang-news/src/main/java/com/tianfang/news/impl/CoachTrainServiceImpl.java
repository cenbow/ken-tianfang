package com.tianfang.news.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.news.dao.CoachTrainDao;
import com.tianfang.news.dto.CoachTrainDto;
import com.tianfang.news.pojo.SportCoachTrain;
import com.tianfang.news.service.ICoachTrainService;


@Service
public class CoachTrainServiceImpl implements ICoachTrainService {

	@Autowired
	private CoachTrainDao coachTrainDao;

	/**
	 * @author YIn
	 * @time:2015年12月24日 上午11:03:36
	 */
	@Override
	public PageResult<CoachTrainDto> findCoachTrainByParams(
			CoachTrainDto coachTrainDto, int currPage, int pageSize) {
		int total = coachTrainDao.countByParams(coachTrainDto);
		if (total > 0){
			PageQuery page = new PageQuery(total, currPage, pageSize);
			List<SportCoachTrain> dto = coachTrainDao.findCoachTrainByParams(coachTrainDto, page);
			if (null != dto && dto.size() > 0){
				List<CoachTrainDto> dtos = BeanUtils.createBeanListByTarget(dto, CoachTrainDto.class);
				for (CoachTrainDto c : dtos) {
					c.setCreateTimeStr(DateUtils.format(c.getCreateTime(), DateUtils.YMD_DASH)); 
				}
				PageResult<CoachTrainDto> result = new PageResult<CoachTrainDto>(page, dtos);
				
				return result;
			}
		}		
		return null;
	}
	
	/**
	 * 增加培训
	 * @author YIn
	 * @time:2015年12月24日 上午11:52:21
	 */
	@Override
	public Integer addCoachTrain(CoachTrainDto coachTrainDto) {
		SportCoachTrain coachTrain = BeanUtils.createBeanByTarget(coachTrainDto,SportCoachTrain.class);
		int status = coachTrainDao.insertSelective(coachTrain);
		return status;
	}
	
	/**
	 * 删除培训
	 * @author YIn
	 * @time:2015年12月24日 下午2:19:28
	 */
	@Override
	public int delCoachTrain(String id) {
		//逻辑删除
		SportCoachTrain sportCoachTrain =new SportCoachTrain();
		sportCoachTrain.setId(id);
		sportCoachTrain.setStat(DataStatus.DISABLED);
		int status = coachTrainDao.updateByPrimaryKeySelective(sportCoachTrain);
		return status;
	}
	
	/**
	 * 批量删除培训
	 * @author YIn
	 * @time:2015年12月24日 下午2:28:30
	 */
	@Override
	public Integer delByIds(String ids) {
		 String[] idArr = ids.split(",");
	        for (String id : idArr) {
	        	SportCoachTrain coachTrain = coachTrainDao.selectByPrimaryKey(id);
	            if (null == coachTrain) {
	                return 0;//无此条记录
	            }
	            coachTrain.setStat(DataStatus.DISABLED);
	            coachTrainDao.updateByPrimaryKeySelective(coachTrain);
	        }
	        return 1;
	}

	/**
	 * 根据ID查询
	 * @author YIn
	 * @time:2015年12月24日 下午5:22:53
	 */
	@Override
	public CoachTrainDto selectCoachTrainById(String id) {
		SportCoachTrain coachTrain  = coachTrainDao.selectCoachTrainById(id);
		return BeanUtils.createBeanByTarget(coachTrain, CoachTrainDto.class);
	}

	/**
	 * @author YIn
	 * @time:2015年12月24日 下午7:17:01
	 */
	@Override
	public int updateCoachTrain(CoachTrainDto coachTrainDto) {
		SportCoachTrain coachTrain = BeanUtils.createBeanByTarget(coachTrainDto,SportCoachTrain.class);
		int status = coachTrainDao.updateByPrimaryKeySelective(coachTrain);
		return status;
	}

	/**
	 * @author YIn
	 * @time:2015年12月24日 下午7:40:48
	 */
	@Override
	public void release(String id, Integer releaseStat) {
		SportCoachTrain coachTrain = coachTrainDao.selectByPrimaryKey(id);
	        if (null != coachTrain) {
	        	coachTrain.setReleaseStat(releaseStat);
	        	coachTrainDao.updateByPrimaryKeySelective(coachTrain);
	        }
	}

	@Override
	public List<CoachTrainDto> findCoachTrainByParams(
			CoachTrainDto coachTrainDto) {
		List<SportCoachTrain> dataList = coachTrainDao.findCoachTrainByParams(coachTrainDto);
		List<CoachTrainDto> resultList = BeanUtils.createBeanListByTarget(dataList, CoachTrainDto.class);
		return resultList;
	}
	
}