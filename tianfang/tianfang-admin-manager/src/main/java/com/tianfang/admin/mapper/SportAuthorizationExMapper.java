package com.tianfang.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.admin.pojo.SportMenu;

public interface SportAuthorizationExMapper {
    
	List<SportMenu> findMenuByAdminId(@Param("id") String id);
	
}
