package com.tianfang.train.dao;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lombok.Getter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.JsonUtil;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.TrainingAddressDto;
import com.tianfang.train.dto.TrainingTimeDistrictDto;
import com.tianfang.train.dto.TrainingUserDto;
import com.tianfang.train.mapper.TimeDistrictMapper;
import com.tianfang.train.mapper.TrainingAddressMapper;
import com.tianfang.train.mapper.TrainingUserExMapper;
import com.tianfang.train.mapper.TrainingUserMapper;
import com.tianfang.train.mapper.XTrainingUserMapper;
import com.tianfang.train.pojo.TimeDistrict;
import com.tianfang.train.pojo.TimeDistrictExample;
import com.tianfang.train.pojo.TrainingAddress;
import com.tianfang.train.pojo.TrainingAddressExample;
import com.tianfang.train.pojo.TrainingUser;
import com.tianfang.train.pojo.TrainingUserExample;

@Repository
public class TrainingUserDao extends MyBatisBaseDao<TrainingUser> {

	@Autowired
	@Getter
	private TrainingUserMapper mapper;

	@Autowired
	@Getter
	private TrainingUserExMapper exMapper;

	@Resource
	private TrainingAddressMapper amapper;

	@Resource
	private TimeDistrictMapper tmapper;

	@Autowired
	@Getter
	private XTrainingUserMapper xmapper;
	private Logger log = Logger.getLogger(getClass());

	public List<TrainingUserDto> findPage(TrainingUserDto trainingUserDto,
			PageQuery page) {
		TrainingUserExample example = new TrainingUserExample();
		TrainingUserExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(DataStatus.ENABLED);
		if (page != null) {
			example.setOrderByClause("create_time limit " + page.getStartNum()
					+ ", " + page.getPageSize() + "");
		}
		List<TrainingUserDto> results = exMapper.selectUserInfo(
				trainingUserDto, page);
		for (int i = 0; i < results.size(); i++) {
			results.get(i).setLeaveCourse(
					exMapper.findCourseCount(results.get(i).getId(),
							results.get(i).getCourseId(),
							results.get(i).getClassId(), 1).getCount());
			results.get(i).setAbsenteeismCourse(
					exMapper.findCourseCount(results.get(i).getId(),
							results.get(i).getCourseId(),
							results.get(i).getClassId(), 2).getCount());
			results.get(i).setCourseAbove(
					exMapper.findCourseCount(results.get(i).getId(),
							results.get(i).getCourseId(),
							results.get(i).getClassId(), 3).getCount());
			results.get(i).setNotCourse(
					results.get(i).getLeaveCourse()
							+ results.get(i).getAbsenteeismCourse());
			results.get(i).setSpaceTime(
					results.get(i).getDayOfWeek()
							+ results.get(i).getStartTime() + "-"
							+ results.get(i).getEndTime());
		}
		return CollectionUtils.isEmpty(results) ? null : results;

	}

	/**
	 * 查询总条数
	 * 
	 * @param contactDto
	 * @return
	 */
	public long count() {
		TrainingUserExample example = new TrainingUserExample();
		TrainingUserExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(DataStatus.ENABLED);
		// return mapper.count(trainingUserDto);
		return exMapper.count();
	}

	/***
	 * 查询用户记录信息
	 * 
	 * @param trainingUserDto
	 * @param page
	 * @return
	 */
	public List<TrainingUserDto> findAllUser(TrainingUserDto trainingUserDto,
			PageQuery page) {
		TrainingUserExample example = new TrainingUserExample();
		TrainingUserExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(DataStatus.ENABLED);
		if (StringUtils.isNotBlank(trainingUserDto.getPname())) {
			criteria.andPnameLike("%" + trainingUserDto.getPname() + "%");
		}
		if (StringUtils.isNotBlank(trainingUserDto.getMobile())) {
			criteria.andMobileLike("%" + trainingUserDto.getMobile() + "%");
		}
		if (StringUtils.isNotBlank(trainingUserDto.getLocation())) {
			criteria.andLocationLike("%" + trainingUserDto.getLocation() + "%");
		}
		if (page != null) {
			example.setOrderByClause("create_time limit " + page.getStartNum()
					+ ", " + page.getPageSize() + "");
		}
		List<TrainingUser> trainingUsers = mapper.selectByExample(example);
		List<TrainingUserDto> results = BeanUtils.createBeanListByTarget(
				trainingUsers, TrainingUserDto.class);
		return CollectionUtils.isEmpty(results) ? null : results;

	}

	/***
	 * 查询用户记录总条数
	 * 
	 * @return
	 */
	public long counts(TrainingUserDto trainingUserDto) {
		TrainingUserExample example = new TrainingUserExample();
		TrainingUserExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(DataStatus.ENABLED);
		if (StringUtils.isNotBlank(trainingUserDto.getPname())) {
			criteria.andPnameLike("%" + trainingUserDto.getPname() + "%");
		}
		if (StringUtils.isNotBlank(trainingUserDto.getMobile())) {
			criteria.andMobileLike("%" + trainingUserDto.getMobile() + "%");
		}
		if (StringUtils.isNotBlank(trainingUserDto.getLocation())) {
			criteria.andLocationLike("%" + trainingUserDto.getLocation() + "%");
		}
		return mapper.countByExample(example);

	}

	/***
	 * 下拉框用户记录所在地信息
	 * 
	 * @param trainingUserDto
	 * @return
	 */
	public List<TrainingUserDto> findLocation(TrainingUserDto trainingUserDto) {
		TrainingUserExample example = new TrainingUserExample();
		TrainingUserExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(DataStatus.ENABLED);
		List<TrainingUserDto> results = exMapper
				.selectUserRecord(trainingUserDto);
		return results;

	}

	/**
	 * 根据主键id查询用户信息
	 * 
	 * @param id
	 * @return
	 */
	public TrainingUser getUserById(String id) {

		return mapper.selectByPrimaryKey(id);
	}

	/**
	 * 申请课程--创建user
	 * 
	 * @param dto
	 * @throws Exception
	 */
	public TrainingUserDto applyCourse(TrainingUserDto dto) throws Exception {
		TrainingUserExample example = new TrainingUserExample();
		TrainingUserExample.Criteria criteria = example.createCriteria();
		criteria.andOpenIdEqualTo(dto.getOpenId());
		List<TrainingUser> users = mapper.selectByExample(example);
		if (users.size() > 0) {
			TrainingUser user = users.get(0);
			if (null == user.getStatus() || user.getStatus() != 1) {
				throw new Exception("当前用户购买课程受到限制");
			}
//			user.setMobile(dto.getMobile());
//			user.setPname(dto.getPname());
//			user.setCname(dto.getCname());
//			user.setUtype(dto.getUtype());
//			user.setUpdateTime(new Date().getTime() / 1000);
//			int updateRow = mapper.updateByPrimaryKey(user);
//			if (updateRow > 0) {
				dto.setId(user.getId());
//			}
		} else {
			TrainingUser trainingUser = (TrainingUser) BeanUtils
					.createBeanByTarget(dto, TrainingUser.class);
			trainingUser.setMobile(dto.getMobile() + "");
			Integer insertRow = xmapper.applyCourse(trainingUser);
			if (insertRow > 0) {
				dto.setId(trainingUser.getId());
			}
		}

		return dto;

	}

	/***
	 * 查询用户
	 * 
	 * @param id
	 * @return
	 */
	public TrainingUser findById(String id) {
		TrainingUserExample example = new TrainingUserExample();
		TrainingUserExample.Criteria criteria = example.createCriteria();
		if (id != null) {
			criteria.andIdEqualTo(id);
		}
		criteria.andStatusEqualTo(DataStatus.ENABLED);
		List<TrainingUser> results = mapper.selectByExample(example);
		return CollectionUtils.isEmpty(results) ? null : results.get(0);

	}

	/***
	 * 删除用户记录信息
	 * 
	 * @param id
	 * @return
	 */
	public boolean[] deleteUser(String id) {
		boolean flag[] = new boolean[id.length()];
		try {
			for (int i = 0; i < id.length(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", id);
				map.put("updateTime", System.currentTimeMillis() / 1000);
				if (xmapper.deleteUser(map)) {
					flag[i] = true;
					return flag;
				}
			}
		} catch (Exception e) {
			log.error("删除用户数据发生异常", e);
		}
		return flag;
	}

	/***
	 * 修改用户记录信息
	 * 
	 * @param trainingUserDto
	 * @return
	 */
	public int updateUser(TrainingUserDto trainingUserDto) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", trainingUserDto.getId());
			map.put("pname", trainingUserDto.getPname());
			map.put("mobile", trainingUserDto.getMobile());
			map.put("location", trainingUserDto.getLocation());
			map.put("position", trainingUserDto.getPosition());
			map.put("pic", trainingUserDto.getPic());
			map.put("cname", trainingUserDto.getCname());
			map.put("birthday",
					sf.parse(trainingUserDto.getBirthdayStr().substring(0))
							.getTime() / 1000);
			map.put("originSchool", trainingUserDto.getOriginSchool());
			map.put("updateTime", System.currentTimeMillis() / 1000);
			map.put("description", trainingUserDto.getDescription());
			Integer count = xmapper.updateUser(map);
			if (count == 1) {
				return 1; // 编辑成功
			}
		} catch (Exception e) {
			log.error("删除用户数据发生异常", e);
		}
		return 2; // 编辑失败
	}

	/***
	 * 查询场地
	 * 
	 * @param trainingAddressDto
	 * @return
	 */
	public List<TrainingAddressDto> findAddress(
			TrainingAddressDto trainingAddressDto) {
		TrainingAddressExample example = new TrainingAddressExample();
		TrainingAddressExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(DataStatus.ENABLED);
		List<TrainingAddress> trainingAddresses = amapper
				.selectByExample(example);
		List<TrainingAddressDto> results = BeanUtils.createBeanListByTarget(
				trainingAddresses, TrainingAddressDto.class);
		return results;

	}

	/***
	 * 查询场地时间
	 * 
	 * @param trainingTimeDistrictDto
	 * @return
	 */
	public List<TrainingTimeDistrictDto> findAddressTime(
			TrainingTimeDistrictDto trainingTimeDistrictDto) {
		TimeDistrictExample example = new TimeDistrictExample();
		TimeDistrictExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(DataStatus.ENABLED);
		List<TimeDistrict> timeDistricts = tmapper.selectByExample(example);
		List<TrainingTimeDistrictDto> results = BeanUtils
				.createBeanListByTarget(timeDistricts,
						TrainingTimeDistrictDto.class);
		for (int i = 0; i < results.size(); i++) {
			results.get(i).setDayOfWeekStr(
					results.get(i).getDayOfWeek()
							+ results.get(i).getStartTime() + "-"
							+ results.get(i).getEndTime());
		}
		return results;
	}

	/**
	 * 通过openID查询用户信息
	 * @param openID
	 * @return
	 * @2015年9月28日
	 */
	public TrainingUserDto getUIByOpenID(String openID){
		TrainingUserDto tui = null;
		try {
			tui = xmapper.getUIByOpenID(openID);
			if (tui != null){
				if(tui.getBirthday()!=null){
					tui.setFmbirthday(JsonUtil.parseTimestamp(tui.getBirthday(), DateUtils.YMD_DASH));
					tui.setFmCreateTime(JsonUtil.parseTimestamp(tui.getCreateTime(), DateUtils.YMD_DASH));
					tui.setFmLastLoginTime(JsonUtil.parseTimestamp(tui.getLastLoginTime(), DateUtils.YMD_DASH_WITH_TIME));
				}
			}
		} catch (Exception e) {
//			log.error("通过openID查询用户信息发生异常", e);
			return null;
		}
		return tui;
	}
	
	/**
	 * 保存用户信息
	 * @param dto
	 * @return
	 * @2015年9月28日
	 */
	public boolean addUserInfo(TrainingUserDto dto){
		boolean flag = false;
		TrainingUser tu = new TrainingUser();
		try {
			BeanUtils.copyProperties(tu, dto);
//			PropertyUtils.copyProperties(tu, dto);
			mapper.insert(tu);
			flag = true;
		} catch (Exception e) {
			log.error("保存用户信息过程发生异常", e);
		}
		return flag;
	}
	
	/**
	 * 根据userID更新用户的登录时间
	 * @param userID
	 * @return
	 * @2015年10月8日
	 */
	public boolean updateLoginTime(String userID){
		boolean flag = false;
		try {
			Map<String, Object> map = new HashMap<String, Object>(2);
			map.put("id", userID);
			map.put("loginTime", System.currentTimeMillis() / 1000);
			xmapper.updateLoginTime(map);
			flag = true;
		} catch (Exception e) {
			log.error("根据id更新用户信息发生异常", e);
		}
		return flag;
	}
}
