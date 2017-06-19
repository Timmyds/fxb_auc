package com.fxb.world.dao;

import java.util.List;

import com.fxb.world.entity.TRoleResources;
import com.fxb.world.entity.TUserResources;
import tk.mybatis.mapper.common.Mapper;

public interface TUserResourcesMapper extends Mapper<TUserResources> {
	List<TUserResources> queryUserResourcesByUserId(Long userId);
}