package com.fxb.world.dao;

import java.util.List;

import com.fxb.world.entity.TUserRole;
import tk.mybatis.mapper.common.Mapper;

public interface TUserRoleMapper extends Mapper<TUserRole> {
	
	List<TUserRole> getUserRoleByUserId(Long userId);
	
}