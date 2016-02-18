package com.tianfang.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.business.dao.AlbumPicDao;
import com.tianfang.business.dto.AlbumPictureDto;
import com.tianfang.business.pojo.SportAlbumPicture;
import com.tianfang.business.service.IAlbumPicService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.JsonUtil;


@Service
public class AlbumPicServiceImpl implements IAlbumPicService {

	@Autowired
	private AlbumPicDao albumPicDao;
	
	/**
	 * 球队首页相片轮播显示
	 * @author YIn
	 * @time:2015年11月14日 下午3:24:55
	 */
	/*@Override
	public List<albumPicPictureDto> findIndexalbumPic(albumPicPictureDto albumPicPictureDto) {
		SportalbumPicPicture albumPicPic = new SportalbumPicPicture();
    	BeanUtils.copyProperties( albumPicPictureDto,albumPicPic);
    	List<SportalbumPicPicture> albumPicPicList = albumPicDao.selectalbumPicbyTeamId(albumPicPic.getTeamId());
    	List<albumPicPictureDto> albumPicPicDtoList =new ArrayList<albumPicPictureDto>();
    	albumPicPicDtoList = BeanUtils.createBeanListByTarget(albumPicPicList, albumPicPictureDto.class);
    	return albumPicPicDtoList;
	}*/
	
	/** 球队相片显示
	 * @author YIn
	 * @time:2015年11月14日 下午1:16:27
	 */
	@Override
	public PageResult<AlbumPictureDto> findTeamAlbumPic(AlbumPictureDto albumPictureDto, PageQuery page) {
		SportAlbumPicture albumPic = new SportAlbumPicture();
    	BeanUtils.copyProperties( albumPictureDto,albumPic);
    	List<AlbumPictureDto> albumPicDtoList = albumPicDao.selectAlbumPicbyTeamId(albumPic, page);
    	/*List<AlbumPictureDto> albumPicDtoList =new ArrayList<AlbumPictureDto>();
    	albumPicDtoList = BeanUtils.createBeanListByTarget(albumPicList, AlbumPictureDto.class);*/
    	if(albumPicDtoList.size()>0){
        	for(AlbumPictureDto s : albumPicDtoList){
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
		long total = albumPicDao.selectAllAlbumPic(albumPic);
		page.setTotal(total);
    	return new PageResult<AlbumPictureDto>(page, albumPicDtoList);
	}

	/**
	 * @author YIn
	 * @time:2015年11月14日 下午5:24:19
	 */
	@Override
	public int addAlbumPic(AlbumPictureDto albumPictureDto) {
		SportAlbumPicture albumPic = BeanUtils.createBeanByTarget(albumPictureDto,SportAlbumPicture.class);
		int status = albumPicDao.insertSelective(albumPic);
		return status;
	}

	/**
	 * @author YIn
	 * @time:2015年11月14日 下午5:24:19
	 */
	@Override
	public int updateAlbumPic(AlbumPictureDto albumPictureDto) {
		SportAlbumPicture albumPic  = BeanUtils.createBeanByTarget(albumPictureDto,SportAlbumPicture.class);
		int status = albumPicDao.updateByPrimaryKeySelective(albumPic);
		return status;
	}

	/**
	 * @author YIn
	 * @time:2015年11月14日 下午5:24:19
	 */
	@Override
	public int delAlbumPic(String id) {
		//int status = albumPicDao.deleteByPrimaryKey(id);
		//逻辑删除
		SportAlbumPicture pic =new SportAlbumPicture();
		pic.setId(id);
		pic.setStat(DataStatus.DISABLED);
		int status = albumPicDao.updateByPrimaryKeySelective(pic);
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
	        	SportAlbumPicture pic = albumPicDao.selectByPrimaryKey(id);
	            if (null == pic) {
	                return 0;//无此条记录
	            }
	            pic.setStat(DataStatus.DISABLED);
	            albumPicDao.updateByPrimaryKeySelective(pic);
	        }
	        return 1;
	}

	/**
	 * @author YIn
	 * @time:2015年11月14日 下午5:24:19
	 */
	@Override
	public AlbumPictureDto selectAlbumPicById(String teamId) {
		SportAlbumPicture albumPic  = albumPicDao.selectAlbumPicById(teamId);
		return BeanUtils.createBeanByTarget(albumPic, AlbumPictureDto.class);
	}
	/**
	 * @author Administrator
	 * 
	 */
	@Override
	public List<AlbumPictureDto> findTeamAlbumPic(AlbumPictureDto albumPictureDto) {
		List<SportAlbumPicture> albumPicPicList = albumPicDao.findTeamAlbumPic(albumPictureDto);
		List<AlbumPictureDto> dataList = BeanUtils.createBeanListByTarget(albumPicPicList, AlbumPictureDto.class);
		return dataList;
	}

	@Override
	public PageResult<AlbumPictureDto> findTeamAlbumPicByPage(AlbumPictureDto albumPictureDto, PageQuery page) {
		List<SportAlbumPicture> results = albumPicDao.findByPage(albumPictureDto,page);
		List<AlbumPictureDto> dataList = BeanUtils.createBeanListByTarget(results,AlbumPictureDto.class);
		long total = albumPicDao.count(albumPictureDto);
		page.setTotal(total);
		return new PageResult<AlbumPictureDto>(page, dataList);
	}

	@Override
	public void insertPictures(AlbumPictureDto albumPicDto) {
		// TODO Auto-generated method stub
		albumPicDao.insertPictures(albumPicDto);
	}

	@Override
	public void updateAlbumPicList(String albumId) {
		albumPicDao.updateAlbumPicList(albumId);
	}

	@Override
	public List<AlbumPictureDto> findTeamAlbumPicByRand(AlbumPictureDto albumPictureDto) {
		List<SportAlbumPicture> results = albumPicDao.findTeamAlbumPicByRand(albumPictureDto);
		List<AlbumPictureDto> dataList = BeanUtils.createBeanListByTarget(results,AlbumPictureDto.class);
		return dataList;
	}

	@Override
	public AlbumPictureDto getAlbumPicByDto(AlbumPictureDto albumPicDto) {
		SportAlbumPicture sportAlbumPicture =  albumPicDao.getAlbumPicByDto(albumPicDto);
		AlbumPictureDto data = BeanUtils.createBeanByTarget(sportAlbumPicture, AlbumPictureDto.class);
		return data;
	}

}
