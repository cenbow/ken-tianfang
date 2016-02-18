package com.tianfang.train.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.JsonUtil;
import com.tianfang.train.dao.TrainApplyDao;
import com.tianfang.train.dao.TrainingCourseDao;
import com.tianfang.train.dao.UserCourseDao;
import com.tianfang.train.dto.TrainApplyDto;
import com.tianfang.train.pojo.UserCourse;
import com.tianfang.train.service.ITrainApplyService;

/**
 * 
 * @author YIn
 * @time:2015年9月1日 下午5:26:18
 * @ClassName: TrainApplyServiceImpl
 * @Description: TODO
 * @
 */
@Service
public class TrainApplyServiceImpl implements ITrainApplyService {
	
	@Autowired
	private TrainApplyDao trainApplyDao;
	
	@Autowired
	private UserCourseDao userCourseDao;
	
	@Autowired
	private TrainingCourseDao courseDao;
	/**
	 * @author YIn
	 * @time:2015年9月1日 下午5:27:31
	 */
	@Override
	public PageResult<TrainApplyDto> findByPage(TrainApplyDto trainApplyDto,
			PageQuery page) {
		    List<UserCourse> results = trainApplyDao.findByPage(trainApplyDto, page);
	        long total = trainApplyDao.count(trainApplyDto);
	        page.setTotal(total);
	        List<TrainApplyDto> dto= BeanUtils.createBeanListByTarget(results, TrainApplyDto.class);
	        for(TrainApplyDto s : dto){
		    	s.setDistrictTime(s.getDayOfWeek()+":"+s.getStartTime()+"-"+s.getEndTime());
		    }
	        return new PageResult<TrainApplyDto>(page, dto);
	}
	
	/**
	 * @author YIn
	 * @time:2015年9月8日 下午5:00:28
	 */
	@Override
	public PageResult<TrainApplyDto> findApplyByPage(
			TrainApplyDto trainApplyDto, PageQuery page) {
		List<UserCourse> results = trainApplyDao.findApplyByPage(trainApplyDto, page);
        long total = trainApplyDao.countApply(trainApplyDto);
        page.setTotal(total);
        List<TrainApplyDto> dto= BeanUtils.createBeanListByTarget(results, TrainApplyDto.class);
        for(TrainApplyDto s : dto){
	    	s.setDistrictTime(s.getDayOfWeek()+":"+s.getStartTime()+"-"+s.getEndTime());
	    	s.setApplyTimeAdd(JsonUtil.parseTimestamp(s.getApplyTime(), "yyyy-MM-dd"));
	    }
        return new PageResult<TrainApplyDto>(page, dto);
	}  

	/**
	 * @author YIn
	 * @return 
	 * @time:2015年9月1日 下午5:27:31
	 */
	@Override
	public void save(TrainApplyDto trainApplyDto) {
		UserCourse userCouser = BeanUtils.createBeanByTarget(trainApplyDto, UserCourse.class);
		UserCourse u = trainApplyDao.save(userCouser);
		if(u != null){
			userCourseDao.addActualStudent(u.getCourseId());
			courseDao.addOneActualStudent(u.getCourseId());
		}
	}


	/**
	 * @author YIn
	 * @time:2015年9月1日 下午5:27:31
	 */
	@Override
	public int update(TrainApplyDto trainApplyDto) {
		UserCourse userCouser = BeanUtils.createBeanByTarget(trainApplyDto, UserCourse.class);
		return trainApplyDao.update(userCouser);
	}
	
	/**
	 * @author YIn
	 * @time:2015年9月5日 下午6:02:16
	 */
	@Override
	public Integer delByIds(String ids) {
		    String[] idArr = ids.split(",");
	        for (String id : idArr) {
	        	UserCourse userCourse = trainApplyDao.findById(id);
	            if (null == userCourse) {
	                return 0;//无此条记录
	            }
	            userCourse.setUpdateTime(new Date().getTime());
	            userCourse.setStatus(DataStatus.DISABLED);
	            trainApplyDao.update(userCourse);
	        }
	        return 1;
		}

	@Override
	public TrainApplyDto getApplyById(String id) {
			TrainApplyDto trainApplyDto = BeanUtils.createBeanByTarget(trainApplyDao.findById(id), TrainApplyDto.class);
			trainApplyDto.setApplyTimeAdd(JsonUtil.parseTimestamp(trainApplyDto.getApplyTime(), "yyyy-MM-dd"));
		    return trainApplyDto;
		}


}
