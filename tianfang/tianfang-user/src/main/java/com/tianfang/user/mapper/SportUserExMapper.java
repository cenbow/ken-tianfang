package com.tianfang.user.mapper;

import org.apache.ibatis.annotations.Param;

import com.tianfang.user.dto.SportUserReqDto;
import com.tianfang.user.pojo.SportUser;

public interface SportUserExMapper {
	int checkRepeat(@Param("sportUser") SportUser sportUser);
	SportUser findByNameAndPassword(@Param("sportUserReqDto") SportUserReqDto sportUserReqDto);
}
