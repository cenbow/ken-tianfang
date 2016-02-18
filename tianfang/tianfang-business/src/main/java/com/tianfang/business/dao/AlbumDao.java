package com.tianfang.business.dao;

import java.util.List;
import java.util.Map;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.business.dto.AlbumDto;
import com.tianfang.business.mapper.SportAlbumExMapper;
import com.tianfang.business.mapper.SportAlbumMapper;
import com.tianfang.business.pojo.SportAlbum;
import com.tianfang.business.pojo.SportAlbumExample;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;

@Repository
public class AlbumDao extends MyBatisBaseDao<SportAlbum>{
	
	@Autowired
	@Getter
	private SportAlbumMapper mapper;
	
	@Autowired
	@Getter
	private SportAlbumExMapper mapperEx;
	
	/*
	@Autowired
	@Getter
	private SportAlbumPictureMapper mappers;*/
	
	
	@Override
    public Object getMapper() {
        return mapper;
    }
	/**
	 * 球队首页相册轮播显示
	 * @author YIn
	 * @time:2015年11月14日 下午3:33:33
	 */
	/*public List<SportAlbumPicture> selectAlbumbyTeamId(String teamId) {
		SportAlbumPictureExample example = new SportAlbumPictureExample();
		SportAlbumPictureExample.Criteria criteria = example.createCriteria();
		criteria.andTeamIdEqualTo(teamId);
		example.setOrderByClause("create_time desc limit ");
		return mappers.selectByExample(example);
	}*/
	
	/**
	 * @author YIn
	 * @time:2015年11月14日 下午1:51:38
	 */
	public List<AlbumDto> selectAlbumbyTeamId(SportAlbum sportAlbum, PageQuery page) {
		/*SportAlbumExample example = new SportAlbumExample();
		SportAlbumExample.Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(teamId)){
		criteria.andTeamIdEqualTo(teamId);
		}
		criteria.andStatEqualTo(1);
		long starNum = page.getStartNum();
		int pageSize = page.getPageSize();
		example.setOrderByClause("create_time desc limit " + starNum + ", " + pageSize);
		return mapper.selectByExample(example);*/
		return mapperEx.selectAlbumByPage(sportAlbum, page);
	}
	
	public List<AlbumDto> findPage(AlbumDto albumDto, PageQuery page) {
	    SportAlbumExample example = new SportAlbumExample();
	    SportAlbumExample.Criteria criteria = example.createCriteria();
	    if(albumDto.getTeamId()!=null && !albumDto.getTeamId().equals("")){
	    	criteria.andTeamIdEqualTo(albumDto.getTeamId());
	    }

	    if(albumDto.getPicType()!=null){
	    	criteria.andPicTypeEqualTo(albumDto.getPicType());
	    }

	    criteria.andStatEqualTo(DataStatus.ENABLED);
        
        
        if(null != page){
            example.setOrderByClause(" create_time asc limit "+page.getStartNum() +"," + page.getPageSize());
        }
        List<SportAlbum> sportAlbums = mapper.selectByExample(example);
        return BeanUtils.createBeanListByTarget(sportAlbums, AlbumDto.class);
	}
	
	/**
	 * @author YIn
	 * @param string 
	 * @time:2015年11月14日 下午1:52:24
	 */
	public long selectAllAlbumb(SportAlbum sportAlbum) {
	/*	SportAlbumExample example = new SportAlbumExample();
		SportAlbumExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(1);
		if(StringUtils.isNotBlank(teamId)){
			criteria.andTeamIdEqualTo(teamId);
			}
		return mapper.countByExample(example);*/
		return mapperEx.selectAlbumByPageCount(sportAlbum);
	}
	
	/**
	 * @author YIn
	 * @time:2015年11月14日 下午6:01:11
	 */
	public List<SportAlbum> findAlbumById(Map<String, Object> map,
			Object object) {
		
		return null;
	}
	

	/**
	 * @author YIn
	 * @time:2015年11月18日 下午5:35:48
	 */
	public List<SportAlbum> findAlbumList() {
		SportAlbumExample example = new SportAlbumExample();
		SportAlbumExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(1);
		return mapper.selectByExample(example);
	}

	public List<SportAlbum> findAlbumList(AlbumDto albumDto) {
		SportAlbumExample example = new SportAlbumExample();
		SportAlbumExample.Criteria criteria = example.createCriteria();
		
		if(!StringUtils.isEmpty(albumDto.getTeamId())){
			criteria.andTeamIdEqualTo(albumDto.getTeamId());
		}
		
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}

	public List<SportAlbum> findTeamAlbumByRand(AlbumDto albumDto) {
		// TODO Auto-generated method stub
		return mapperEx.findTeamAlbumByRand(albumDto);
	}
}
