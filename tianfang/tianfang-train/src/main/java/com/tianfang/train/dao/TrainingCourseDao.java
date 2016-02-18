package com.tianfang.train.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.TrainingCourseDtoX;
import com.tianfang.train.mapper.CourseClassMapper;
import com.tianfang.train.mapper.TrainingCourseExMapper;
import com.tianfang.train.mapper.TrainingCourseMapper;
import com.tianfang.train.mapper.XTrainingCourseMapper;
import com.tianfang.train.pojo.CourseClass;
import com.tianfang.train.pojo.CourseClassExample;
import com.tianfang.train.pojo.TrainingCourse;
import com.tianfang.train.pojo.TrainingCourseExample;
import com.tianfang.train.pojo.TrainingCourseExample.Criteria;

@Repository
public class TrainingCourseDao extends MyBatisBaseDao<TrainingCourse> {
	
	private Logger log = Logger.getLogger(getClass());
	
	@Autowired
	@Getter
	private XTrainingCourseMapper mappers;
	
	@Autowired
	@Getter
	private TrainingCourseMapper mapperCourse;

	@Autowired
	@Getter
	private TrainingCourseExMapper exMapper;
	
	@Autowired
    @Getter
	private CourseClassMapper ccMapper;

	@Override
	public Object getMapper() {
		return mappers;
	}

	/***
	 * 分页查询课程信息
	 * 
	 * @param trainingCourseDto
	 * @param page
	 * @Resource XTrainingCourseMapper mapper; private Logger log =
	 *           Logger.getLogger(getClass());
	 * 
	 *           /** 查询可报名课程列表
	 * @return
	 */
	public List<TrainingCourseDtoX> findByPage(
			TrainingCourseDtoX trainingCourseDto, PageQuery page) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
		TrainingCourseExample example = new TrainingCourseExample();
		TrainingCourseExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(DataStatus.ENABLED);
		if (page != null) {
			example.setOrderByClause(" create_time desc limit "
					+ page.getStartNum() + "," + page.getPageSize());
		}
		List<TrainingCourseDtoX> results = exMapper.selectCourseInfo(
				trainingCourseDto, page);
		for (int i = 0; i < results.size(); i++) {
			Date dateS = new Date();
			Date dateE = new Date();
			if (null != results.get(i).getStartTime()) {
				dateS = new Date(results.get(i).getStartTime() * 1000);
			}
			if (null != results.get(i).getEndTime()) {
				dateE = new Date(results.get(i).getEndTime() * 1000);
			}
			results.get(i).setCourseCycle(
					sdf.format(dateS) + "-" + sdf.format(dateE));
			if ((results.get(i).getDescription().equals(null))
					|| (results.get(i).getDescription().equals(""))) {
				results.get(i).setCourseName(results.get(i).getName());
			} else {
				results.get(i).setCourseName(
						results.get(i).getName() + "("
								+ results.get(i).getDescription() + ")");
			}
		}
		return  results;
	}

	/**
	 * 查询课程（列表）
	 * 
	 * @param ava
	 * <br>
	 * ava=1表示只查询报名未满的课程,ava=0表示查询所有
	 * @param marked
	 * <br>
	 * marked=1表示加精，0表示不加精
	 * @return
	 * @2015年9月5日
	 */
	public List<TrainingCourseDtoX> getACourseLst(String ava, String marked) {
		List<TrainingCourseDtoX> lst = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>(2);
			map.put("ava", ava);
			map.put("marked", marked);
			lst = mappers.getACourseLst(map);
		} catch (Exception e) {
			log.error("查询可报名课程信息发生异常", e);
		}
		return lst;
	}

	/***
	 * 查询总条数
	 * 
	 * @param trainingCourseDto
	 * @return
	 */
	public long count(TrainingCourseDtoX trainingCourseDto) {
		TrainingCourseExample example = new TrainingCourseExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(DataStatus.ENABLED);
		/*
		 * if (StringUtils.isNotBlank(trainingCourseDto.getName())) {
		 * criteria.andNameEqualTo("%" + trainingCourseDto.getName() + "%"); }
		 * if (trainingCourseDto.getCourseTime() != null) {
		 * criteria.andCourseTimeEqualTo(trainingCourseDto.getCourseTime()); }
		 * if (trainingCourseDto.getTagId() != null) {
		 * criteria.andTagIdEqualTo(+trainingCourseDto.getTagId()); } if
		 * (StringUtils.isNotBlank(trainingCourseDto.getAddress())) {
		 * criteria.andAddressEqualTo("%" + trainingCourseDto.getAddress() +
		 * "%"); }
		 */
		return exMapper.count(trainingCourseDto);
	}

	public Integer updateByTrainingCourse(TrainingCourse trainingCourse){
	    return exMapper.updateByTrainingCourse(trainingCourse);
	}
	/***
	 * 删除课程信息根据ID
	 * 
	 * @param id
	 * @return
	 */
	public int deleteById(String id) {
		TrainingCourse trainingCourse = new TrainingCourse();
		trainingCourse.setId(id);
		trainingCourse.setStatus(DataStatus.DISABLED);
		return mapperCourse.updateByPrimaryKeySelective(trainingCourse);
	}

	/**
	 * 通过id查询课程详情
	 * 
	 * @return
	 * @2015年9月2日
	 */
	public TrainingCourseDtoX getCourseById(String courseId) {
		TrainingCourseDtoX tc = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>(1);
			map.put("id", courseId);
			tc = mappers.getCourseById(map);
		} catch (Exception e) {
			log.error("根据courseId查询课程信息发生异常", e);
		}
		return tc;
	}

	public TrainingCourseDtoX getCourse(String id,String courseId) {
        TrainingCourseDtoX tc = null;
        try {
            Map<String, Object> map = new HashMap<String, Object>(1);
            map.put("id", id);
            map.put("courseId", courseId);
            tc = mappers.getCourse(map);
        } catch (Exception e) {
            log.error("根据courseId查询课程信息发生异常", e);
        }
        return tc;
    }
	public TrainingCourse findById(String id) {
		TrainingCourseExample example = new TrainingCourseExample();
		TrainingCourseExample.Criteria criteria = example.createCriteria();
		if (id != null) {
			criteria.andIdEqualTo(id);
		}
		criteria.andStatusEqualTo(DataStatus.ENABLED);
		List<TrainingCourse> results = mapperCourse.selectByExample(example);
		return CollectionUtils.isEmpty(results) ? null : results.get(0);

	}
	
	public CourseClass findByIdCourseId(String courseClassId,String courseId) {
	    CourseClassExample example = new CourseClassExample();
	    CourseClassExample.Criteria criteria = example.createCriteria();
	    if (null != courseClassId) {
	        criteria.andIdEqualTo(courseClassId);
	    }
	    if (null != courseId) {
	        criteria.andCourseIdEqualTo(courseId);
	    }
	    criteria.andStatusEqualTo(DataStatus.ENABLED);
	    List<CourseClass> results= ccMapper.selectByExample(example);
	    return CollectionUtils.isEmpty(results) ? null : results.get(0);
	}

	public List<TrainingCourseDtoX> findCourseName(
			TrainingCourseDtoX trainingCourseDtoX) {
		TrainingCourseExample example = new TrainingCourseExample();
		TrainingCourseExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(DataStatus.ENABLED);
		List<TrainingCourse> trainingCourses = mapperCourse
				.selectByExample(example);
		List<TrainingCourseDtoX> results = BeanUtils.createBeanListByTarget(
				trainingCourses, TrainingCourseDtoX.class);
		return results;
	}

	public TrainingCourse findByNameCourseTime(String name, Integer courseTime) {
		TrainingCourseExample example = new TrainingCourseExample();
		TrainingCourseExample.Criteria criteria = example.createCriteria();
		if (null != courseTime) {
			criteria.andCourseTimeEqualTo(courseTime);
		}
		if (!StringUtils.isEmpty(name)) {
			criteria.andNameEqualTo(name);
		}
		criteria.andStatusEqualTo(DataStatus.ENABLED);
		List<TrainingCourse> results = mapperCourse.selectByExample(example);
		return CollectionUtils.isEmpty(results) ? null : results.get(0);
	}

	public Integer save(TrainingCourse trainingCourse) {
		return exMapper.save(trainingCourse);
	}

	/**
	 * 更新actual_student +1
	 * 
	 * @param courseId
	 */
	public void addOneActualStudent(String courseId) {
		exMapper.addOneActualStudent(courseId);

	}

	/**
	 * @author YIn
	 * @time:2015年9月8日 下午3:35:53
	 */
	public List<TrainingCourse> findAllCourse() {
		TrainingCourseExample example = new TrainingCourseExample();
		TrainingCourseExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(DataStatus.ENABLED);
		return mapperCourse.selectByExample(example);
	}

	public TrainingCourseDtoX findUserCourceBy(String classId) {
		// TODO Auto-generated method stub
		return mappers.findUserCourceBy(classId);
	}
}
