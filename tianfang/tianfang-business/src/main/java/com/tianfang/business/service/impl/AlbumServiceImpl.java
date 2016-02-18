package com.tianfang.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.business.dao.AlbumDao;
import com.tianfang.business.dao.AlbumPicDao;
import com.tianfang.business.dao.GameDao;
import com.tianfang.business.dao.SportTeamDao;
import com.tianfang.business.dto.AlbumDto;
import com.tianfang.business.dto.AlbumPictureDto;
import com.tianfang.business.pojo.SportAlbum;
import com.tianfang.business.pojo.SportAlbumPicture;
import com.tianfang.business.pojo.SportGame;
import com.tianfang.business.pojo.SportTeam;
import com.tianfang.business.service.IAlbumService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.JsonUtil;
import com.tianfang.common.util.StringUtils;


@Service
public class AlbumServiceImpl implements IAlbumService {

	@Autowired
	private AlbumDao albumDao;
	
	@Autowired
	private SportTeamDao sportTeamDao;
	
	@Autowired
	private AlbumPicDao albumPicDao;
	
	
	/** 球队相册显示
	 * @author YIn
	 * @time:2015年11月14日 下午1:16:27
	 */
	@Override
	public PageResult<AlbumDto> findTeamAlbum(AlbumDto albumDto, PageQuery page) {
		SportAlbum album = new SportAlbum();
    	BeanUtils.copyProperties( albumDto,album);
    	List<AlbumDto> albumList = albumDao.selectAlbumbyTeamId(album, page);
    	/*List<AlbumDto> albumDtoList =new ArrayList<AlbumDto>();
    	albumDtoList = BeanUtils.createBeanListByTarget(albumList, AlbumDto.class);*/
    	if(albumList.size()>0){
    	for(AlbumDto s : albumList){
    		if(s.getCreateTime() != null){
    			s.setCreateTimeStr(JsonUtil.parseDateStr(s.getCreateTime(), "yyyy-MM-dd"));
 	    	}else{
 	    		s.setCreateTimeStr("");
 	    	}
 	    	if(s.getLastUpdateTime()!=null){s.setLastUpdateTimeStr(JsonUtil.parseDateStr(s.getLastUpdateTime(),"yyyy-MM-dd"));
 	    	}else{
 	    		s.setLastUpdateTimeStr("");
 	    	}
 	    }
    	}
		long total = albumDao.selectAllAlbumb(album);
		page.setTotal(total);
    	return new PageResult<AlbumDto>(page, albumList);
	}

	/**
	 * 
	 * findPage：获取相册list
	 * @param albumDto
	 * @param page
	 * @return
	 * @exception	
	 * @author Administrator
	 * @date 2015年11月23日 上午11:16:50
	 */
	public PageResult<AlbumDto> findPage (AlbumDto albumDto, PageQuery page) {
	    SportAlbum album = new SportAlbum();
        BeanUtils.copyProperties( albumDto,album);
        List<AlbumDto> albumList = albumDao.findPage(albumDto, page);
        /*List<AlbumDto> albumDtoList =new ArrayList<AlbumDto>();
        albumDtoList = BeanUtils.createBeanListByTarget(albumList, AlbumDto.class);*/
        if(albumList.size()>0){
        for(AlbumDto s : albumList){
            List<SportAlbumPicture> sportAlbumPictures = albumPicDao.findAlbumPicById(s.getId());
            if (null != sportAlbumPictures) {
                s.setTotalPic(sportAlbumPictures.size());
            }
            if(s.getCreateTime() != null){
                s.setCreateTimeStr(JsonUtil.parseDateStr(s.getCreateTime(), "yyyy-MM-dd"));
            }else{
                s.setCreateTimeStr("");
            }
            if(s.getLastUpdateTime()!=null){s.setLastUpdateTimeStr(JsonUtil.parseDateStr(s.getLastUpdateTime(),"yyyy-MM-dd"));
            }else{
                s.setLastUpdateTimeStr("");
            }
            SportTeam sportTeam = sportTeamDao.selectByPrimaryKey(s.getTeamId());
            if (null != sportTeam) {
                s.setTeamName(sportTeam.getName());
                s.setTeamPic(sportTeam.getLogo());
            }
        }
        }
        long total = albumDao.selectAllAlbumb(album);
        page.setTotal(total);
        return new PageResult<AlbumDto>(page, albumList);
	}
	
	/**
	 * @author YIn
	 * @time:2015年11月14日 下午5:24:19
	 */
	@Override
	public int addAlbum(AlbumDto albumDto) {
		SportAlbum sportAlbum = BeanUtils.createBeanByTarget(albumDto,SportAlbum.class);
		if(!StringUtils.isEmpty(sportAlbum.getId())){
			return albumDao.updateByPrimaryKeySelective(sportAlbum);	
		}else{
			return albumDao.insertSelective(sportAlbum);
		}
	}

	/**
	 * @author YIn
	 * @time:2015年11月14日 下午5:24:19
	 */
	@Override
	public int updateAlbum(AlbumDto albumDto) {
		SportAlbum sportAlbum = BeanUtils.createBeanByTarget(albumDto,SportAlbum.class);
		int status = albumDao.updateByPrimaryKeySelective(sportAlbum);
		return status;
	}

	/**
	 * @author YIn
	 * @time:2015年11月14日 下午5:24:19
	 */
	@Override
	public int delAlbum(String id) {
		int status = albumDao.deleteByPrimaryKey(id);
		return status;
	}

	/**
	 * @author YIn
	 * @time:2015年11月16日 下午3:16:19
	 */
	@Override
	public Integer delByIds(String ids) {
		  String[] idArr = ids.split(",");
	        for (String id : idArr) {
	        	SportAlbum album = albumDao.selectByPrimaryKey(id);
	            if (null == album) {
	                return 0;//无此条记录
	            }
	            album.setStat(DataStatus.DISABLED);
	            albumDao.updateByPrimaryKeySelective(album);
	        }
	        return 1;
	}
	/**
	 * @author YIn
	 * @time:2015年11月14日 下午5:24:19
	 */
	@Override
	public AlbumDto getAlbumById(String albumId) {
		SportAlbum album  = albumDao.selectByPrimaryKey(albumId);
		return BeanUtils.createBeanByTarget(album, AlbumDto.class);
	}

	/**
	 * @author YIn
	 * @time:2015年11月18日 下午5:32:51
	 */
	@Override
	public List<AlbumDto> findAlbumList() {
		List<SportAlbum> albumList  = albumDao.findAlbumList();
		return BeanUtils.createBeanListByTarget(albumList,AlbumDto.class);
	}
	
	public List<AlbumPictureDto> findByAlbumId(String AlbumId) {
	    List<SportAlbumPicture> sportAlbumPictures = albumPicDao.findAlbumPicById(AlbumId);
	    return BeanUtils.createBeanListByTarget(sportAlbumPictures, AlbumPictureDto.class);
	}

	@Override
	public List<AlbumDto> findAlbumList(AlbumDto albumDto) {
		List<SportAlbum> albumList  = albumDao.findAlbumList(albumDto);
		return BeanUtils.createBeanListByTarget(albumList,AlbumDto.class);
	}

	@Override
	public List<AlbumDto> findTeamAlbumByRand(AlbumDto albumDto) {
		List<SportAlbum> results = albumDao.findTeamAlbumByRand(albumDto);
		List<AlbumDto> dataList = BeanUtils.createBeanListByTarget(results,AlbumDto.class);
		return dataList;
	}
}
