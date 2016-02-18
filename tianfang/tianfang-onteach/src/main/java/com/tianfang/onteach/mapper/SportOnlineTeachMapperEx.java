package com.tianfang.onteach.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.onteach.pojo.SportOnlineTeach;

public interface SportOnlineTeachMapperEx {

	void updateClickCount(@Param("oId") String oId);
	
	List<SportOnlineTeach> selectByExample(@Param("start") Integer start,@Param("limit") Integer limit);
	
	Integer countcolumnNameByParams();

}
