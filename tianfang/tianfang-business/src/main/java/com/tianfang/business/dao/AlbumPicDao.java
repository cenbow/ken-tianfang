package com.tianfang.business.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Objects;
import com.tianfang.business.dto.AlbumPictureDto;
import com.tianfang.business.mapper.SportAlbumPicExMapper;
import com.tianfang.business.mapper.SportAlbumPictureMapper;
import com.tianfang.business.pojo.SportAlbumPicture;
import com.tianfang.business.pojo.SportAlbumPictureExample;
import com.tianfang.business.pojo.SportAlbumPictureExample.Criteria;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;

@Repository
public class AlbumPicDao extends MyBatisBaseDao<SportAlbumPicture>{
	
	@Autowired
	@Getter
	private SportAlbumPictureMapper mapper;
	
	@Autowired
	@Getter
	private SportAlbumPicExMapper mapperEx;
	
	/*
	@Autowired
	@Getter
	private SportAlbumPictureMapper mappers;*/
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
	public List<AlbumPictureDto> selectAlbumPicbyTeamId(SportAlbumPicture sportAlbumPicture, PageQuery page) {
	/*	SportAlbumPictureExample example = new SportAlbumPictureExample();
		SportAlbumPictureExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(1);
		//criteria.andTeamIdEqualTo(teamId);
		long starNum = page.getStartNum();
		int pageSize = page.getPageSize();
		example.setOrderByClause("create_time desc limit " + starNum + ", " + pageSize);
		return mapper.selectByExample(example);*/
		return mapperEx.selectAlbumPicByPage(sportAlbumPicture, page);
	}
	
	/**
	 * @author YIn
	 * @param string 
	 * @time:2015年11月14日 下午1:52:24
	 */
	public long selectAllAlbumPic(SportAlbumPicture sportAlbumPicture) {
		/*SportAlbumPictureExample example = new SportAlbumPictureExample();
		SportAlbumPictureExample.Criteria criteria = example.createCriteria();
		return mapper.countByExample(example);*/
		return mapperEx.selectAlbumPicByPageCount(sportAlbumPicture);
	}
	
	/**
	 * @author YIn
	 * @time:2015年11月14日 下午6:01:11
	 */
	public SportAlbumPicture selectAlbumPicById(String teamId) {
		SportAlbumPictureExample example = new SportAlbumPictureExample();
		SportAlbumPictureExample.Criteria criteria = example.createCriteria();
        criteria.andAlbumIdEqualTo(teamId);
        criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByPrimaryKey(teamId);
	}
	
	
	public List<SportAlbumPicture> findAlbumPicById(String id) {
	    SportAlbumPictureExample example = new SportAlbumPictureExample();
        SportAlbumPictureExample.Criteria criteria = example.createCriteria();
        criteria.andAlbumIdEqualTo(id);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
	}
	
	public List<SportAlbumPicture> findTeamAlbumPic(AlbumPictureDto albumPictureDto) {
		SportAlbumPictureExample example = new SportAlbumPictureExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(albumPictureDto.getTeamId())){
			criteria.andTeamIdEqualTo(albumPictureDto.getTeamId());	
		}

		if(!StringUtils.isEmpty(albumPictureDto.getAlbumId())){
			criteria.andAlbumIdEqualTo(albumPictureDto.getAlbumId());	
		}
		
		if(albumPictureDto.getPicType()!=null){
			criteria.andPicTypeEqualTo(albumPictureDto.getPicType());
		}
		if(albumPictureDto.getPicStatus()!=null){
			criteria.andPicStatusEqualTo(albumPictureDto.getPicStatus());
		}

		criteria.andStatEqualTo(DataStatus.ENABLED);
		if(!Objects.equal(albumPictureDto.getLimit(),null)){
			example.setOrderByClause("create_time desc limit " + albumPictureDto.getLimit());
		}
		return mapper.selectByExample(example);
	}

	public List<SportAlbumPicture> findByPage(AlbumPictureDto albumPictureDto,PageQuery page) {
		SportAlbumPictureExample example = new SportAlbumPictureExample();
		
		example = queryBySql(albumPictureDto);
		
		example.setOrderByClause("  create_time desc limit " + page.getStartNum() + ", " + page.getPageSize());
		List<SportAlbumPicture> list = mapper.selectByExample(example);
		return list;
	}

	public long count(AlbumPictureDto albumPictureDto) {
		SportAlbumPictureExample example = new SportAlbumPictureExample();
		example = queryBySql(albumPictureDto);
		return mapper.countByExample(example);
	}
	
	public SportAlbumPictureExample queryBySql(AlbumPictureDto albumPictureDto){
		SportAlbumPictureExample example = new SportAlbumPictureExample();
		Criteria criteria = example.createCriteria();
		
		if(!StringUtils.isEmpty(albumPictureDto.getAlbumId())){
			criteria.andAlbumIdEqualTo(albumPictureDto.getAlbumId());
		}
		if(!StringUtils.isEmpty(albumPictureDto.getTeamId())){
			criteria.andTeamIdEqualTo(albumPictureDto.getTeamId());
		}
		if(!StringUtils.isEmpty(albumPictureDto.getUserId())){
			criteria.andUserIdEqualTo(albumPictureDto.getUserId());
		}
		
		if(!StringUtils.isEmpty(albumPictureDto.getId())){
			criteria.andIdEqualTo(albumPictureDto.getId());
		}
		
		if(albumPictureDto.getPicStatus()!=null){
			criteria.andPicStatusEqualTo(albumPictureDto.getPicStatus());
		}
		if(albumPictureDto.getPicType()!=null){
			criteria.andPicTypeEqualTo(albumPictureDto.getPicType());
		}
		
		criteria.andStatEqualTo(DataStatus.ENABLED);
		
		return example;
	}

	public void insertPictures(AlbumPictureDto albumPicDto) {
		List<String> pictures = new ArrayList<String>();
		if(albumPicDto.getPictureList()!=null){
			pictures = Arrays.asList(albumPicDto.getPictureList());
		}
		albumPicDto.setStat(DataStatus.ENABLED);
		mapperEx.insertPictures(albumPicDto,pictures);
	}

	public void updateAlbumPicList(String albumId) {
		// TODO Auto-generated method stub
		mapperEx.updateAlbumPicList(albumId);
	}

	public List<SportAlbumPicture> findTeamAlbumPicByRand(
			AlbumPictureDto albumPictureDto) {
		// TODO Auto-generated method stub
		return mapperEx.findTeamAlbumPicByRand(albumPictureDto);
	}

	public SportAlbumPicture getAlbumPicByDto(AlbumPictureDto albumPicDto) {
		SportAlbumPictureExample example = new SportAlbumPictureExample();
		
		example = queryBySql(albumPicDto);
		
		List<SportAlbumPicture> list = mapper.selectByExample(example);
		
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
}
