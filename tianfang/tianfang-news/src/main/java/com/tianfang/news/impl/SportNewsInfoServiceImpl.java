package com.tianfang.news.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.PropertiesUtils;
import com.tianfang.news.dao.SportNewsInfoDao;
import com.tianfang.news.dto.SportNewsInfoDto;
import com.tianfang.news.pojo.SportNewsInfo;
import com.tianfang.news.service.ISportNewsInfoService;

@Service
public class SportNewsInfoServiceImpl implements ISportNewsInfoService {

	@Autowired
	private SportNewsInfoDao newsDao;
	
	/**
	 * 更改前端页面显示位置
	 */
	public int pageRanking(String id, Integer positionId) {
	    SportNewsInfo info = newsDao.selectByPrimaryKey(id);
	    if(null == info) {
	        return 0;
	    }
	    SportNewsInfo SportNewsInfo = newsDao.findByPostionId(positionId);
	    if (null == SportNewsInfo) {
	        info.setPageRanking(positionId);
	        newsDao.updateByPrimaryKeySelective(info);
	    }
	    if (null == positionId) {
	        System.out.println("最大排序="+newsDao.pageRankingMax());
	        info.setPageRanking(newsDao.pageRankingMax()+1);
            newsDao.updateByPrimaryKeySelective(info);           
	    }
	    if (null != SportNewsInfo && null != positionId) {
	        if (null != SportNewsInfo.getPageRanking()) {
	            SportNewsInfo.setPageRanking(info.getPageRanking());
                newsDao.updateByPrimaryKeySelective(SportNewsInfo);
	            info.setPageRanking(positionId);
	            newsDao.updateByPrimaryKeySelective(info);	            
	        }
	        if (null == SportNewsInfo.getPageRanking()) {
	            info.setPageRanking(positionId);
                newsDao.updateByPrimaryKeySelective(info);
                System.out.println("最大排序="+newsDao.pageRankingMax());
                SportNewsInfo.setPageRanking(newsDao.pageRankingMax()+1);               
                newsDao.updateByPrimaryKeySelective(SportNewsInfo);
	        }
	    }
	    return 1;
	}
	
	@Override
	public SportNewsInfoDto saveNews(SportNewsInfoDto news) {
	    SportNewsInfoDto sportNewsInfoDto = new SportNewsInfoDto();
		checkExceptionForNews(news);
		
		SportNewsInfo info = new SportNewsInfo();
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
			List<SportNewsInfo> listra = newsDao.countByMarked(map);
			if(listra.size()>=3){
				//throw new RuntimeException("选中图片最多三条");
			    sportNewsInfoDto.setReleaseStat(2);
				return sportNewsInfoDto;
			}
		}
		newsDao.insert(info);
		sportNewsInfoDto = BeanUtils.createBeanByTarget(info, SportNewsInfoDto.class);
		sportNewsInfoDto.setReleaseStat(1);
		return sportNewsInfoDto;
	}

	private void checkExceptionForNews(SportNewsInfoDto news) {
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
	public SportNewsInfoDto updateNews(SportNewsInfoDto news) {
	    SportNewsInfoDto sportNewsInfoDto = new SportNewsInfoDto();
		checkExceptionForNews(news);
		SportNewsInfo obj = newsDao.selectByPrimaryKey(news.getId());
		if (null == obj){
			//throw new RuntimeException("对不起,新闻(资讯)信息不存在");
		    sportNewsInfoDto.setResultStat(3);
			return sportNewsInfoDto;
		}
		if(obj.getMarked()!=1 && news.getMarked() ==1){
			Map<String,Object> map = new HashMap<String, Object>();		//判断首页显示图片是否超过三条
			map.put("stat", DataStatus.ENABLED);
			map.put("marked", "1");
			List<SportNewsInfo> listra = newsDao.countByMarked(map);
			if(listra.size()>=3){
				//throw new RuntimeException("选中图片最多三条");
			    sportNewsInfoDto.setResultStat(2);
				return sportNewsInfoDto;
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
		obj.setReleaseStat(DataStatus.DISABLED);
		obj.setStat(DataStatus.ENABLED);
		obj.setSubtract(news.getSubtract());
		obj.setLastUpdateTime(new Date());
		newsDao.updateByPrimaryKey(obj);
		sportNewsInfoDto = BeanUtils.createBeanByTarget(obj, SportNewsInfoDto.class);
		sportNewsInfoDto.setResultStat(1);
		return sportNewsInfoDto;
	}

	@Override
	public SportNewsInfoDto getNewsById(String id) {
		if (null == id || "".equals(id.trim())){
			throw new RuntimeException("对不起,新闻(资讯)主键id为空");
		}
		SportNewsInfo info = newsDao.selectByPrimaryKey(id);
		
		return BeanUtils.createBeanByTarget(info, SportNewsInfoDto.class);
	}

	@Override
	public List<SportNewsInfoDto> findNewsByParams(Map<String, Object> params) {
		List<SportNewsInfo> infos = newsDao.findNewsByParams(params);
		if (null != infos && infos.size() > 0){
			return BeanUtils.createBeanListByTarget(infos, SportNewsInfoDto.class);
		}
		return null;
	}

	@Override
	public PageResult<SportNewsInfoDto> findNewsByParams(SportNewsInfoDto newsDto, int currPage, int pageSize) {
		int total = newsDao.countNewsByParams(newsDto);
		if (total > 0){
			PageQuery page = new PageQuery(total, currPage, pageSize);
			List<SportNewsInfo> infos = newsDao.findNewsByParams(newsDto, page);
			if (null != infos && infos.size() > 0){
				List<SportNewsInfoDto> dtos = BeanUtils.createBeanListByTarget(infos, SportNewsInfoDto.class);
				for (SportNewsInfoDto news : dtos) {
					news.setCreateTimeStr(DateUtils.format(news.getCreateTime(), DateUtils.YMD_DASH)); 
				}
				PageResult<SportNewsInfoDto> result = new PageResult<SportNewsInfoDto>(page, dtos);
				
				return result;
			}
		}		
		return null;
	}
	
	@Override
	public PageResult<SportNewsInfoDto> findNewsByParams(Map<String, Object> params, int currPage, int pageSize) {
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
			List<SportNewsInfo> infos = newsDao.findNewsByParams(params, page);
			if (null != infos && infos.size() > 0){
				List<SportNewsInfoDto> dtos = BeanUtils.createBeanListByTarget(infos, SportNewsInfoDto.class);
				for (SportNewsInfoDto news : dtos) {
					news.setCreateTimeStr(format.format(news.getCreateTime())); 
				}
				PageResult<SportNewsInfoDto> result = new PageResult<SportNewsInfoDto>(page, dtos);
				
				return result;
			}
		}
		
		return null;
	}

	@Override
	public List<SportNewsInfoDto> findIndexNews(Integer size) {
		List<SportNewsInfo> infos = newsDao.findIndexNews(size);
		if (null != infos && infos.size() > 0){
			return BeanUtils.createBeanListByTarget(infos, SportNewsInfoDto.class);
		}
		return null;
	}

	@Override
	public List<SportNewsInfoDto> findNewsByCons(SportNewsInfoDto dto,PageQuery page) {
	
		List<SportNewsInfo> lst = newsDao.findNewsByParams(dto, page);
		List<SportNewsInfoDto> re = new ArrayList<SportNewsInfoDto>();
		try {
			if((lst != null) && (lst.size() != 0)){
				for (SportNewsInfo news : lst) {
					SportNewsInfoDto x = new SportNewsInfoDto();
					PropertyUtils.copyProperties(x, news);
					re.add(x);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}
	
	public Integer release(String id,Integer releaseStat) {
	    SportNewsInfo sportNewsInfo = newsDao.selectByPrimaryKey(id);
	    if(Objects.equal(releaseStat, DataStatus.DISABLED)){
	    	sportNewsInfo.setMarked(DataStatus.DISABLED);
	    }
	    sportNewsInfo.setReleaseStat(releaseStat);
	    return newsDao.updateByPrimaryKeySelective(sportNewsInfo);
	}
}