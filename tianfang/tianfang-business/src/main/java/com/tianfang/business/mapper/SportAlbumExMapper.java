package com.tianfang.business.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.business.dto.AlbumDto;
import com.tianfang.business.pojo.SportAlbum;
import com.tianfang.common.model.PageQuery;

public interface SportAlbumExMapper {
	List<AlbumDto> selectAlbumByPage(@Param("sportAlbum") SportAlbum sportAlbum,@Param("page") PageQuery page);
	Long selectAlbumByPageCount(@Param("sportAlbum")  SportAlbum sportAlbum);
	List<SportAlbum> findTeamAlbumByRand(@Param("albumDto") AlbumDto albumDto);
	
}
