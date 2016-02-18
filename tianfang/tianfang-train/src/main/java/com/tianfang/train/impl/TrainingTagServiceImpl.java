package com.tianfang.train.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.train.dao.CourseTagDao;
import com.tianfang.train.dto.CourseTagDto;
import com.tianfang.train.pojo.CourseTag;
import com.tianfang.train.service.ITrainingTagService;

@Service
public class TrainingTagServiceImpl implements ITrainingTagService {
	@Autowired
	private CourseTagDao courseTagDao;

	@Override
	public PageResult<CourseTagDto> findByPage(CourseTagDto courseTagDto,
			PageQuery page) {
		List<CourseTagDto> results = courseTagDao.findpage(courseTagDto, page);
		page.setTotal(courseTagDao.count(courseTagDto));
		return new PageResult<CourseTagDto>(page, results);
	}

	@Override
	public Object insertTag(CourseTagDto courseTagDto) {
		CourseTag courseTag = courseTagDao.findByTagName(courseTagDto);
		if (courseTag != null) {
			if (courseTag.getTagName() != null && courseTag.getStatus() == 0) {
				int count = courseTagDao.updateCourseTagStatus(courseTagDto);
				if (count == 1) {
					return 0;// 标签已存在,只是状态将状态重新设为1
				} else {
					return 3; // 新增失败
				}
			} else if (courseTag.getTagName() != null
					&& courseTag.getStatus() == 1) {
				return 1; // 标签已存在
			} else {
				return 3;
			}
		} else {
			courseTagDao.insertTag(courseTagDto);
			return 2;// 新增成功
		}
	}

	@Override
	public Object deleteById(String delId) {
		boolean flag[] = new boolean[delId.length()];
		String[] idArr = delId.split(",");
		for (String id : idArr) {
			CourseTag courseTag = courseTagDao.findById(id);
			if (courseTag == null) {
				return 0;// 无此条记录
			}
			flag = courseTagDao.deleteCourseTag(id);
		}
		for (int i = 0; i < delId.length(); i++) {
			if (flag[i] == true) {
				return 1;
			} else {
				return 0;
			}
		}
		return 1;
	}

	@Override
	public Object updateTag(CourseTagDto courseTagDto) {
		CourseTag courseTag = courseTagDao.findByTagName(courseTagDto);
		if (courseTag != null) {
			return 0;// 标签已存在
		}
		int count = courseTagDao.updateCourseTag(courseTagDto);
		if (count == 1) {
			return 1; // 编辑成功
		} else {
			return 2; // 编辑失败
		}
	}

	@Override
	public CourseTagDto getTag(String id) {
		CourseTagDto courseTagDto = BeanUtils.createBeanByTarget(
				courseTagDao.findById(id), CourseTagDto.class);
		return courseTagDto;
	}

}
