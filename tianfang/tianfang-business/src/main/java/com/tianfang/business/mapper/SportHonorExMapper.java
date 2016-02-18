package com.tianfang.business.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.business.dto.AlbumDto;
import com.tianfang.business.dto.SportHonorReqDto;
import com.tianfang.business.dto.SportHonorRespDto;
import com.tianfang.business.pojo.SportAlbum;
import com.tianfang.common.model.PageQuery;

public interface SportHonorExMapper {
	List<SportHonorRespDto> selectHonorList(@Param("sportHonorReqDto") SportHonorReqDto sportHonorReqDto,@Param("page") PageQuery page);
	
	Long selectHonorCount(@Param("sportHonorReqDto")  SportHonorReqDto sportHonorReqDto);
	
}
