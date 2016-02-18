package com.tianfang.train.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.train.dao.TrainingNewsInfoDao;
import com.tianfang.train.dto.TrainingNewsInfoDto;
import com.tianfang.train.pojo.TrainingNewsInfo;
import com.tianfang.train.service.ITrainingNewsInfoService;

import freemarker.log.Logger;

@Service
public class TrainingNewsInfoServiceImpl implements ITrainingNewsInfoService {

	@Autowired
	private TrainingNewsInfoDao newsDao;
	
	/**
	 * 更改前端页面显示位置
	 */
	public int pageRanking(String id, Integer positionId) {
	    TrainingNewsInfo info = newsDao.selectByPrimaryKey(id);
	    if(null == info) {
	        return 0;
	    }
	    TrainingNewsInfo trainingNewsInfo = newsDao.findByPostionId(positionId);
	    if (null == trainingNewsInfo) {
	        info.setPageRanking(positionId);
	        newsDao.updateByPrimaryKeySelective(info);
	    }
	    if (null == positionId) {
	        System.out.println("最大排序="+newsDao.pageRankingMax());
	        info.setPageRanking(newsDao.pageRankingMax()+1);
            newsDao.updateByPrimaryKeySelective(info);           
	    }
	    if (null != trainingNewsInfo && null != positionId) {
	        if (null != trainingNewsInfo.getPageRanking()) {
	            trainingNewsInfo.setPageRanking(info.getPageRanking());
                newsDao.updateByPrimaryKeySelective(trainingNewsInfo);
	            info.setPageRanking(positionId);
	            newsDao.updateByPrimaryKeySelective(info);	            
	        }
	        if (null == trainingNewsInfo.getPageRanking()) {
	            info.setPageRanking(positionId);
                newsDao.updateByPrimaryKeySelective(info);
                System.out.println("最大排序="+newsDao.pageRankingMax());
                trainingNewsInfo.setPageRanking(newsDao.pageRankingMax()+1);               
                newsDao.updateByPrimaryKeySelective(trainingNewsInfo);
	        }
	    }
	    return 1;
	}
	
	@Override
	public int saveNews(TrainingNewsInfoDto news) {
		checkExceptionForNews(news);
		
		TrainingNewsInfo info = new TrainingNewsInfo();
		info.setContent(news.getContent());
		info.setMarked(news.getMarked());
		info.setMicroPic(news.getMicroPic());
		info.setSubtract(news.getSubtract());
		info.setTitle(news.getTitle());
		info.setNewVideo(news.getNewVideo());
		info.setCreateTime(new Date());
		if(news.getMarked()==1){
			Map<String,Object> map = new HashMap<String, Object>();		//判断首页显示图片是否超过三条
			map.put("stat", DataStatus.ENABLED);
			map.put("marked", "1");
			List<TrainingNewsInfo> listra = newsDao.countByMarked(map);
			if(listra.size()>=3){
				//throw new RuntimeException("选中图片最多三条");
				return 2;
			}
		}
		
		newsDao.insert(info);
		return 1;
	}

	private void checkExceptionForNews(TrainingNewsInfoDto news) {
		if (null == news){
			throw new RuntimeException("对不起,新闻(资讯)信息为空");
		}
	}

	@Override
	public int deleteNews(String id) {
		String[] ids = id.split(",");
		if(ids.length>0){
			for (String str : ids) {
				newsDao.deleteNews(str);
			}
		}else{
			newsDao.deleteNews(id);
		}
		return 1;
	}

	@Override
	public int updateNews(TrainingNewsInfoDto news) {
		checkExceptionForNews(news);
		TrainingNewsInfo obj = newsDao.selectByPrimaryKey(news.getId());
		if (null == obj){
			//throw new RuntimeException("对不起,新闻(资讯)信息不存在");
			return 3;
		}
		if(obj.getMarked()!=1 && news.getMarked() ==1){
			Map<String,Object> map = new HashMap<String, Object>();		//判断首页显示图片是否超过三条
			map.put("stat", DataStatus.ENABLED);
			map.put("marked", "1");
			List<TrainingNewsInfo> listra = newsDao.countByMarked(map);
			if(listra.size()>=3){
				//throw new RuntimeException("选中图片最多三条");
				return 2;
			}
		}
		obj.setTitle(news.getTitle());
		obj.setContent(news.getContent());
		obj.setMarked(news.getMarked());
		if(news.getMicroPic() == null || news.getMicroPic().equals("")){
		}else{
			obj.setMicroPic(news.getMicroPic());
		}
		obj.setNewVideo(news.getNewVideo());
		/*if(news.getMicroPic()==null || news.getMicroPic().equals("") ){
			obj.setMicroPic(obj.getMicroPic()+"jpg");
		}else{
			obj.setMicroPic(news.getMicroPic());
		}*/
		obj.setStat(DataStatus.ENABLED);
		obj.setSubtract(news.getSubtract());
		obj.setLastUpdateTime(new Date());
		newsDao.updateByPrimaryKey(obj);
		return 1;
	}

	@Override
	public TrainingNewsInfoDto getNewsById(String id) {
		if (null == id || "".equals(id.trim())){
			throw new RuntimeException("对不起,新闻(资讯)主键id为空");
		}
		TrainingNewsInfo info = newsDao.selectByPrimaryKey(id);
		
		return BeanUtils.createBeanByTarget(info, TrainingNewsInfoDto.class);
	}

	@Override
	public List<TrainingNewsInfoDto> findNewsByParams(Map<String, Object> params) {
		List<TrainingNewsInfo> infos = newsDao.findNewsByParams(params);
		if (null != infos && infos.size() > 0){
			return BeanUtils.createBeanListByTarget(infos, TrainingNewsInfoDto.class);
		}
		return null;
	}

	@Override
	public PageResult<TrainingNewsInfoDto> findNewsByParams(TrainingNewsInfoDto newsDto, int currPage, int pageSize) {
		int total = newsDao.countNewsByParams(newsDto);
		if (total > 0){
			PageQuery page = new PageQuery(total, currPage, pageSize);
			List<TrainingNewsInfo> infos = newsDao.findNewsByParams(newsDto, page);
			if (null != infos && infos.size() > 0){
				List<TrainingNewsInfoDto> dtos = BeanUtils.createBeanListByTarget(infos, TrainingNewsInfoDto.class);
				for (TrainingNewsInfoDto news : dtos) {
					news.setCreateTimeStr(DateUtils.format(news.getCreateTime(), DateUtils.YMD_DASH)); 
				}
				PageResult<TrainingNewsInfoDto> result = new PageResult<TrainingNewsInfoDto>(page, dtos);
				
				return result;
			}
		}		
		return null;
	}
	
	@Override
	public PageResult<TrainingNewsInfoDto> findNewsByParams(Map<String, Object> params, int currPage, int pageSize) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if(params.get("createTimeS")!=null && !"".equals(params.get("createTimeS")) && params.get("createTimeE")!=null 
				&& !"".equals(params.get("createTimeE"))){
			try {
	 			params.put("createTimeS", format.parse(params.get("createTimeS").toString()));
	 			params.put("createTimeE", format.parse(params.get("createTimeE").toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		int total = newsDao.countNewsByParams(params);
		if (total > 0){
			PageQuery page = new PageQuery(total, currPage, pageSize);
			List<TrainingNewsInfo> infos = newsDao.findNewsByParams(params, page);
			if (null != infos && infos.size() > 0){
				List<TrainingNewsInfoDto> dtos = BeanUtils.createBeanListByTarget(infos, TrainingNewsInfoDto.class);
				for (TrainingNewsInfoDto news : dtos) {
					news.setCreateTimeStr(format.format(news.getCreateTime())); 
				}
				PageResult<TrainingNewsInfoDto> result = new PageResult<TrainingNewsInfoDto>(page, dtos);
				
				return result;
			}
		}
		
		return null;
	}

	@Override
	public List<TrainingNewsInfoDto> findIndexNews(Integer size) {
		List<TrainingNewsInfo> infos = newsDao.findIndexNews(size);
		if (null != infos && infos.size() > 0){
			return BeanUtils.createBeanListByTarget(infos, TrainingNewsInfoDto.class);
		}
		return null;
	}
}