package com.tianfang.business.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.business.dto.AlbumPictureDto;
import com.tianfang.business.pojo.SportAlbumPicture;
import com.tianfang.common.model.PageQuery;

public interface SportAlbumPicExMapper {
	List<AlbumPictureDto> selectAlbumPicByPage(@Param("sportAlbumPicture") SportAlbumPicture sportAlbumPicture,@Param("page") PageQuery page);
	
	Long selectAlbumPicByPageCount(@Param("sportAlbumPicture")  SportAlbumPicture sportAlbumPicture);
	
	void insertPictures(@Param("albumPicDto") AlbumPictureDto albumPicDto,@Param("list") List<String> pictures);

	void updateAlbumPicList(@Param("albumId")String albumId);

	List<SportAlbumPicture> findTeamAlbumPicByRand(@Param("albumPictureDto") AlbumPictureDto albumPictureDto);
	
}
