package com.tianfang.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.business.dao.SportVideoDao;
import com.tianfang.business.dto.SportVideoDto;
import com.tianfang.business.pojo.SportVideo;
import com.tianfang.business.service.ISportVideoService;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;

@Service
public class SportVideoServiceImpl implements ISportVideoService {

	@Autowired
	private SportVideoDao sportVideoDao;

	@Override
	public PageResult<SportVideoDto> getCriteriaPage(PageQuery page,
			Map<String, Object> map) {
		List<SportVideo> lis =  sportVideoDao.getCriteriaPage(page,map);
		List<SportVideoDto> lisDto = BeanUtils.createBeanListByTarget(lis, SportVideoDto.class);
		for (SportVideoDto sport : lisDto) {
			if(sport.getCreateTime()!=null){
				sport.setCreateTimeStr(DateUtils.format(sport.getCreateTime(), DateUtils.YMD_DASH));	
			}
			if(sport.getLastUpdateTime()!=null){
				sport.setLastUpdateTimeStr(DateUtils.format(sport.getLastUpdateTime(), DateUtils.YMD_DASH));	
			}
			if(!StringUtils.isEmpty(sport.getVideo())){
				sport.setVideo("vcastr_file="+sport.getVideo().replace("\\", "/"));	
			}
			
		}
		if(page!=null){
			long total = sportVideoDao.getCount(map);
			page.setTotal(total);
		}
		return new PageResult<SportVideoDto>(page,lisDto);
	}

	@Override
	public long insertVideo(SportVideoDto videoDto) {
		SportVideo sportVideo = BeanUtils.createBeanByTarget(videoDto, SportVideo.class);
		long stat = sportVideoDao.insertVideo(sportVideo);
		return stat;
	}

	@Override
	public long editVideo(SportVideoDto videoDto) {
		SportVideo sportVideo = BeanUtils.createBeanByTarget(videoDto, SportVideo.class);
		long stat = sportVideoDao.editVideo(sportVideo);
		return stat;
	}

	@Override
	public long delVideo(String ids) {
		String[] id = ids.split(",");
		for (String str : id) {
			long stat = sportVideoDao.delVideo(str);
			if(stat ==0 ){
				return 0;
			}
		}
		return 1;
	}

	@Override
	public SportVideoDto selectById(Map<String, Object> map) {
		List<SportVideo> sport = sportVideoDao.getCriteriaPage(null, map);
		return sport.size()<1 ? null :(SportVideoDto) BeanUtils.createBeanListByTarget(sport, SportVideoDto.class).get(0);
	}

	@Override
	public void updateClickCount(String videoId) {
		// TODO Auto-generated method stub
		sportVideoDao.updateClickCount(videoId);
	}

	@Override
	public List<SportVideoDto> findVideoByTop(int i,Integer videoStatus) {
		List<SportVideo> dataList = sportVideoDao.findVideoByTop(i,videoStatus);
		List<SportVideoDto> objList = BeanUtils.createBeanListByTarget(dataList, SportVideoDto.class);
		return objList;
	}
}
