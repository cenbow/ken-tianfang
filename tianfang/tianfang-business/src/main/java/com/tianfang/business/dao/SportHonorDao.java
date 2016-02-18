package com.tianfang.business.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tianfang.business.dto.SportHonorReqDto;
import com.tianfang.business.dto.SportHonorRespDto;
import com.tianfang.business.mapper.SportHonorExMapper;
import com.tianfang.business.mapper.SportHonorMapper;
import com.tianfang.business.pojo.SportHonor;
import com.tianfang.business.pojo.SportHonorExample;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;

@Repository
public class SportHonorDao extends MyBatisBaseDao<SportHonor> { 
    
	@Autowired
	@Getter
	private SportHonorMapper mappers;	
	
	@Autowired
	@Getter
	private SportHonorExMapper mapperEx;		
//
//	@Autowired
//	@Getter
//	private CourseTagExMapper exMappers;
//	private Logger log = Logger.getLogger(getClass());
//
	@Override
	public Object getMapper() {
		return mappers;
	}
//
//	/***
//	 * 分页查询课程标签
//	 * 
//	 * @param courseTagDto
//	 * @param page
//	 * @return
//	 */
//	public List<CourseTagDto> findpage(CourseTagDto courseTagDto, PageQuery page) {
//		CourseTagExample example = new CourseTagExample();
//		CourseTagExample.Criteria criteria = example.createCriteria();
//		if (StringUtils.isNotBlank(courseTagDto.getTagName())) {
//			criteria.andTagNameLike("%" + courseTagDto.getTagName() + "%");
//		}
//		criteria.andStatusEqualTo(DataStatus.ENABLED);
//		if (page != null) {
//			example.setOrderByClause(" create_time limit " + page.getStartNum()
//					+ "," + page.getPageSize() + "");
//		}
//		List<CourseTag> courseTagList = mappers.selectByExample(example);
//		List<CourseTagDto> results = BeanUtils.createBeanListByTarget(
//				courseTagList, CourseTagDto.class);
//		return results;
//	}
	
	/**
	 * 
	 * findPage：分页查询战队荣誉
	 * @param sportHonorReqDto
	 * @param page
	 * @return
	 * @exception	
	 * @author Administrator
	 * @date 2015年11月13日 下午5:17:01
	 */
	public List<SportHonorRespDto> findPage(SportHonorReqDto sportHonorReqDto, PageQuery page) {
//	    SportHonorExample example = new SportHonorExample();
//	    SportHonorExample.Criteria criteria = example.createCriteria();
//	    if (StringUtils.isNotBlank(sportHonorReqDto.getTitle())) {
//	        criteria.andTitleLike("%"+sportHonorReqDto.getTitle()+"%");
//	    }
//	    if (StringUtils.isNotBlank(sportHonorReqDto.getTeamId())) {
//	    	criteria.andTeamIdEqualTo(sportHonorReqDto.getTeamId());
//	    }
//	    
//	    if(null != page){
//            example.setOrderByClause(" create_time desc limit "+page.getStartNum() +"," + page.getPageSize());
//        }
//	    criteria.andStatEqualTo(DataStatus.ENABLED);
//	    List<SportHonor> sportHonorList = mappers.selectByExample(example);
////	    List<SportHonorRespDto> results = mapperEx.selectHonorByPage(sportHonorReqDto, page);
//	    List<SportHonorRespDto> results = BeanUtils.createBeanListByTarget(sportHonorList, SportHonorRespDto.class);
	    return mapperEx.selectHonorList(sportHonorReqDto, page);
	}
	
	/**
	 * 
	 * count：查询总条数
	 * @param sportHonorReqDto
	 * @return
	 * @exception	
	 * @author Administrator
	 * @date 2015年11月13日 下午5:22:31
	 */
	public long count(SportHonorReqDto sportHonorReqDto) {
//	    SportHonorExample example = new SportHonorExample();
//        SportHonorExample.Criteria criteria = example.createCriteria();
//        if (StringUtils.isNotBlank(sportHonorReqDto.getTitle())) {
//            criteria.andTitleLike("%"+sportHonorReqDto.getTitle()+"%");
//        }
//        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapperEx.selectHonorCount(sportHonorReqDto);
	}
//
//	/***
//	 * 查询总条数
//	 * 
//	 * @param courseTagDto
//	 * @return
//	 */
//	public long count(CourseTagDto courseTagDto) {
//		CourseTagExample example = new CourseTagExample();
//		Criteria criteria = example.createCriteria();
//		if (StringUtils.isNotBlank(courseTagDto.getTagName())) {
//			criteria.andTagNameLike("%" + courseTagDto.getTagName() + "%");
//		}
//		criteria.andStatusEqualTo(DataStatus.ENABLED);
//		return mappers.countByExample(example);
//	}
//
//	/***
//	 * 下拉框查询课程标签
//	 * 
//	 * @param courseTagDto
//	 * @return
//	 */
//	public List<CourseTagDto> findAllCourseTag(CourseTagDto courseTagDto) {
//		CourseTagExample example = new CourseTagExample();
//		CourseTagExample.Criteria criteria = example.createCriteria();
//		criteria.andStatusEqualTo(DataStatus.ENABLED);
//		List<CourseTag> courseTags = mappers.selectByExample(example);
//		List<CourseTagDto> results = BeanUtils.createBeanListByTarget(
//				courseTags, CourseTagDto.class);
//		return results;
//	}
//
//	/***
//	 * 通过标签名进行查询
//	 * 
//	 * @param tagName
//	 * @return
//	 */
//	public CourseTag findByTagName(CourseTagDto courseTagDto) {
//		CourseTagExample example = new CourseTagExample();
//		CourseTagExample.Criteria criteria = example.createCriteria();
//		if (StringUtils.isNotBlank(courseTagDto.getTagName())) {
//			criteria.andTagNameEqualTo(courseTagDto.getTagName());
//		}
//		criteria.andStatusEqualTo(DataStatus.ENABLED);
//		CourseTag results = exMappers.selectTagInfoByTagName(courseTagDto);
//		return results;
//	}
//
//	/***
//	 * 新增标签
//	 * 
//	 * @param courseTagDto
//	 */
//	public void insertTag(CourseTagDto courseTagDto) {
//		CourseTag courseTag = BeanUtils.createBeanByTarget(courseTagDto,
//				CourseTag.class);
//		courseTag.setId(UUID.randomUUID().toString());
//		courseTag.setId(UUIDGenerator.getUUID());
//		courseTag.setCreateTime(System.currentTimeMillis() / 1000);
//		courseTag.setUpdateTime(System.currentTimeMillis() / 1000);
//		courseTag.setStatus(DataStatus.ENABLED);
//		mappers.insert(courseTag);
//	}
//
//	/***
//	 * 新增标签（标签名已存在只是状态为0）
//	 * 
//	 * @param courseTagDto
//	 * @return
//	 */
//	public int updateCourseTagStatus(CourseTagDto courseTagDto) {
//		try {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("tagName", courseTagDto.getTagName());
//			map.put("createTime", System.currentTimeMillis() / 1000);
//			map.put("updateTime", System.currentTimeMillis() / 1000);
//			Integer count = exMappers.updateCourseTagStatus(map);
//			if (count == 1) {
//				return 1;
//			}
//		} catch (Exception e) {
//			log.error("删除用户数据发生异常", e);
//		}
//		return 0;
//
//	}
//
//	/***
//	 * 标签课标签
//	 * 
//	 * @param courseTagDto
//	 * @return
//	 */
//	public int updateCourseTag(CourseTagDto courseTagDto) {
//		try {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("id", courseTagDto.getId());
//			map.put("tagName", courseTagDto.getTagName());
//			map.put("createTime", System.currentTimeMillis() / 1000);
//			map.put("updateTime", System.currentTimeMillis() / 1000);
//			Integer count = exMappers.updateCourseTag(map);
//			if (count == 1) {
//				return 1; // 编辑成功
//			}
//		} catch (Exception e) {
//			log.error("删除用户数据发生异常", e);
//		}
//		return 2; // 编辑失败
//	}
//
//	/***
//	 * 批量删除课程标签
//	 * 
//	 * @param id
//	 * @return flag
//	 */
//	public boolean[] deleteCourseTag(String id) {
//		boolean flag[] = new boolean[id.length()];
//		try {
//			for (int i = 0; i < id.length(); i++) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("id", id);
//				map.put("updateTime", System.currentTimeMillis() / 1000);
//				map.put("createTime", System.currentTimeMillis() / 1000);
//				if (exMappers.deleteCourseTag(map)) {
//					flag[i] = true;
//					return flag;
//				}
//			}
//		} catch (Exception e) {
//			log.error("删除用户数据发生异常", e);
//		}
//		return flag;
//
//	}
//
//	/***
//	 * 通过ID查询课程标签
//	 * 
//	 * @param id
//	 * @return
//	 */
//	public CourseTag findById(String id) {
//		CourseTagExample example = new CourseTagExample();
//		Criteria criteria = example.createCriteria();
//		if (id != null) {
//			criteria.andIdEqualTo(id);
//		}
//		criteria.andStatusEqualTo(DataStatus.ENABLED);
//		List<CourseTag> results = mappers.selectByExample(example);
//		return CollectionUtils.isEmpty(results) ? null : results.get(0);
//	}
//
//	/***
//	 * 通过ID查询课程标签
//	 * 
//	 * @param id
//	 * @return
//	 */
//	public CourseTagDto getCTBId(String id) {
//		CourseTagDto ctdto = null;
//		try {
//			Map<String, Object> map = new HashMap<String, Object>(1);
//			map.put("id", id);
//			ctdto = exMappers.getCTBId(map);
//		} catch (Exception e) {
//			log.error("通过id查询courseTag信息发生异常", e);
//		}
//		return ctdto;
//	}
}
