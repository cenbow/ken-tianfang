package com.tianfang.train.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.train.dto.TrainingMessageInfoDto;
import com.tianfang.train.dto.UserCourseMessageDto;
import com.tianfang.train.mapper.TrainingMessageInfoExMapper;
import com.tianfang.train.mapper.TrainingMessageInfoMapper;
import com.tianfang.train.mapper.UserCourseMapper;
import com.tianfang.train.mapper.XUserCourseMapper;
import com.tianfang.train.pojo.TrainingMessageInfo;
import com.tianfang.train.pojo.TrainingMessageInfoExample;
import com.tianfang.train.pojo.UserCourse;
import com.tianfang.train.pojo.UserCourseExample;

@Repository
public class TrainingMessageInfoDao extends MyBatisBaseDao<TrainingMessageInfo> {
	@Autowired
	@Getter
	private TrainingMessageInfoMapper mappers;

	@Autowired
	@Getter
	private TrainingMessageInfoExMapper messageInfoMapper;

	@Autowired
	@Getter
	private XUserCourseMapper xUserCourseMapper;

	@Autowired
	@Getter
	private UserCourseMapper userCourseMapper;

	@Override
	public Object getMapper() {
		return mappers;
	}

	/***
	 * 分页查询消息详情
	 * 
	 * @param trainingMessageInfoDto
	 * @param page
	 * @return
	 */
	public List<TrainingMessageInfoDto> findPage(
			TrainingMessageInfoDto trainingMessageInfoDto, PageQuery page) {
		TrainingMessageInfoExample example = new TrainingMessageInfoExample();
		TrainingMessageInfoExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		if (page != null) {
			example.setOrderByClause(" create_time desc limit "+page.getStartNum() +"," + page.getPageSize());
		}
		List<TrainingMessageInfoDto> results = messageInfoMapper.selectMessageInfo(trainingMessageInfoDto, page);
		return CollectionUtils.isEmpty(results) ? null : results;
	}

	/***
	 * 查询消息总条数
	 * 
	 * @param trainingMessageInfoDto
	 * @return
	 */
	public long count(TrainingMessageInfoDto trainingMessageInfoDto) {
		TrainingMessageInfoExample example = new TrainingMessageInfoExample();
		TrainingMessageInfoExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return messageInfoMapper.countMessageInfo(trainingMessageInfoDto);
	}

	/***
	 * 通过ID查询消息信息
	 * 
	 * @param id
	 * @return
	 */
	public TrainingMessageInfo findById(String id) {
		TrainingMessageInfoExample example = new TrainingMessageInfoExample();
		TrainingMessageInfoExample.Criteria criteria = example.createCriteria();
		if (id != null) {
			criteria.andIdEqualTo(id);
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		List<TrainingMessageInfo> results = mappers.selectByExample(example);
		return CollectionUtils.isEmpty(results) ? null : results.get(0);
	}

	/***
	 * 通过ID物理删除消息信息
	 * 
	 * @param id
	 * @return
	 */
	public int deleteById(String id) {
		TrainingMessageInfo trainingMessageInfo = new TrainingMessageInfo();
		trainingMessageInfo.setId(id);
		trainingMessageInfo.setStat(DataStatus.DISABLED);
		return mappers.updateByPrimaryKeySelective(trainingMessageInfo);

	}

	/***
	 * 保存消息信息
	 * 
	 * @param trainingMessageInfo
	 * @return
	 */
	public int save(TrainingMessageInfo trainingMessageInfo) {
		trainingMessageInfo.setId(UUIDGenerator.getUUID());
		trainingMessageInfo.setCreateTime(new Date());
		trainingMessageInfo.setStat(DataStatus.ENABLED);
		int flag = mappers.insert(trainingMessageInfo);
		return flag;
	}

	/***
	 * 修改消息信息
	 * 
	 * @param trainingMessageInfo
	 * @return
	 */
	public int edit(TrainingMessageInfo trainingMessageInfo) {
		trainingMessageInfo.setTitle(trainingMessageInfo.getTitle());
		trainingMessageInfo.setContent(trainingMessageInfo.getContent());
		trainingMessageInfo.setLastUpdateTime(new Date());
		trainingMessageInfo.setCreateTime(new Date());
		return mappers.updateByPrimaryKeySelective(trainingMessageInfo);

	}

	public List<UserCourseMessageDto> findCourseMessage(UserCourse userCourse,
			PageQuery page) {
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("user_id", userCourse.getUserId());
		para.put("startNum", page.getStartNum());
		para.put("pageSize", page.getPageSize());
		return xUserCourseMapper.findCourseMessage(para);
	}

	public long selectAllUserCourseMessages(UserCourse userCourse) {
		UserCourseExample example = new UserCourseExample();
		UserCourseExample.Criteria criteria = example.createCriteria();

		if (!StringUtils.isEmpty(userCourse.getUserId())) {
			criteria.andUserIdEqualTo(userCourse.getUserId());
		}
		if (!org.springframework.util.StringUtils.isEmpty(userCourse
				.getStatus())) {
			criteria.andStatusEqualTo(userCourse.getStatus());
		}
		return userCourseMapper.countByExample(example);
	}

	public List<TrainingMessageInfo> findAll() {
		TrainingMessageInfoExample example = new TrainingMessageInfoExample();
		TrainingMessageInfoExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		example.setOrderByClause(" create_time desc ");
		List<TrainingMessageInfo> results = mappers.selectByExample(example);
		return CollectionUtils.isEmpty(results) ? null : results;
	}
}
