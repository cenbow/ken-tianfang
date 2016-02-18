package com.tianfang.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.business.dao.SportNoticeDao;
import com.tianfang.business.dto.SportNoticeDto;
import com.tianfang.business.pojo.SportNotice;
import com.tianfang.business.service.ISportNoticeService;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;

@Service
public class SportNoticeServiceImpl implements ISportNoticeService {

	@Autowired
	private SportNoticeDao dao;

	@Override
	public int saveNotice(SportNoticeDto notice) {
		checkExceptionForNotice(notice);
		if (null != notice.getMarked() && notice.getMarked().intValue() == 1) {
			// 如果该公告是置顶公告,则重置该球队其他公告marked字段
			dao.resetTop(notice.getTeamId());
		}
		dao.insert(changToEntity(notice));
		
		return 1;
	}

	@Override
	public int deleteNotice(String id) {
		String[] ids = id.split(",");
		if (ids.length > 0) {
			for (String str : ids) {
				dao.deleteNotice(str);
			}
		} else {
			dao.deleteNotice(id);
		}
		return 1;
	}

	@Override
	public int updateNotice(SportNoticeDto notice) {
		checkExceptionForNotice(notice);
		SportNotice obj = dao.selectByPrimaryKey(notice.getId());
		if (null == obj) {
			throw new RuntimeException("对不起,球队公告信息不存在");
		}
		if (null != notice.getMarked() && notice.getMarked().intValue() == 1) {
			// 如果该公告是置顶公告,则重置该球队其他公告marked字段
			dao.resetTop(notice.getTeamId());
		}
		obj.setTeamId(notice.getTeamId());
		obj.setTitle(notice.getTitle());
		obj.setSubtract(notice.getSubtract());
		obj.setContent(notice.getContent());
		obj.setMarked(notice.getMarked());
		obj.setPublisher(notice.getPublisher());
		dao.updateByPrimaryKey(obj);
		return 1;
	}

	@Override
	public SportNoticeDto getNoticeById(String id) {
		if (null == id || "".equals(id.trim())){
			throw new RuntimeException("对不起,球队公告主键id为空");
		}
		SportNotice entity = dao.selectByPrimaryKey(id);
		SportNoticeDto dto = changToDto(entity);
		dto.setCreateTimeStr(DateUtils.format(dto.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
		return dto;
	}

	@Override
	public List<SportNoticeDto> findNoticeByParams(SportNoticeDto params) {
		List<SportNoticeDto> datas = dao.findNoticeByParams(params);
		if (null != datas && datas.size() > 0){
			for (SportNoticeDto data : datas){
				data.setCreateTimeStr(DateUtils.format(data.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			}
			return datas;
		}
		return null;
	}

	@Override
	public PageResult<SportNoticeDto> findNoticeByParams(SportNoticeDto params,
			int currPage, int pageSize) {
		int total = dao.countNoticeByParams(params);
		if (total > 0){
			PageQuery page = new PageQuery(total, currPage, pageSize);
			List<SportNoticeDto> datas = dao.findNoticeByParams(params, page);
			if (null != datas && datas.size() > 0){
				for (SportNoticeDto data : datas){
					data.setCreateTimeStr(DateUtils.format(data.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
				}
				PageResult<SportNoticeDto> result = new PageResult<SportNoticeDto>(page, datas);
				
				return result;
			}
		}		
		return null;
	}

	@Override
	public boolean setTop(String id, String teamId) {
		if (id == null || "".equals(id.trim())){
			throw new RuntimeException("对不起,球队公告主键id为空");
		}
		checkTeamIdException(teamId);
		SportNotice entity = dao.selectByPrimaryKey(id);
		checkExceptionForNotice(entity);
		dao.resetTop(teamId);
		entity.setMarked(1);
		dao.updateByPrimaryKey(entity);
		
		return true;
	}

	@Override
	public SportNoticeDto getTopNotice(String teamId) {
		checkTeamIdException(teamId);
		
		return dao.getTopNotice(teamId);
	}
	
	private void checkTeamIdException(String teamId) {
		if (teamId == null || "".equals(teamId.trim())){
			throw new RuntimeException("对不起,球队id为空");
		}
	}

	private SportNotice changToEntity(SportNoticeDto dto) {
		if (dto == null) {
			return null;
		}
		SportNotice entity = new SportNotice();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}

	private SportNoticeDto changToDto(SportNotice entity) {
		if (entity == null) {
			return null;
		}
		SportNoticeDto dto = new SportNoticeDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	private void checkExceptionForNotice(SportNoticeDto notice) {
		if (null == notice) {
			throw new RuntimeException("对不起,球队公告信息为空");
		}
		if (StringUtils.isBlank(notice.getTeamId())){
			throw new RuntimeException("对不起,未关联到球队");
		}
	}
	
	private void checkExceptionForNotice(SportNotice notice) {
		if (null == notice) {
			throw new RuntimeException("对不起,球队公告信息为空");
		}
		if (StringUtils.isBlank(notice.getTeamId())){
			throw new RuntimeException("对不起,未关联到球队");
		}
	}
}