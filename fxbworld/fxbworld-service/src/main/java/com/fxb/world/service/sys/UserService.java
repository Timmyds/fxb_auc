package com.fxb.world.service.sys;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.fxb.work.util.bean.Result;
import com.fxb.world.dao.TRoleMapper;
import com.fxb.world.dao.TRoleResourcesMapper;
import com.fxb.world.dao.TUserMapper;
import com.fxb.world.dao.TUserResourcesMapper;
import com.fxb.world.dao.TUserRoleMapper;
import com.fxb.world.entity.TUser;
import com.fxb.world.entity.TUserRole;
import com.fxb.world.service.vo.UserVo;

/**
 * 
 * @author ds
 * 
 */
@Service
public class UserService {

	@Resource
	TUserMapper tUserMapper;
	@Resource
	TRoleMapper tRoleMapper;
	
	@Resource
	TUserRoleMapper tUserRoleMapper;
	

	@Resource
	TRoleResourcesMapper tRoleResourcesMapper;
	@Resource 
	TUserResourcesMapper tUserResourcesMapper;

	/**
	 * 添加用户
	 * 
	 * @param result
	 * @param userPo
	 * @param userVo
	 * @param defaultpwd
	 * @return
	 * @throws Exception
	 */
	public Result saveUserInfo(Result result, TUser userPo, UserVo userVo, String defaultpwd) throws Exception {
		if (userPo == null) {
			String msg = "添加的用户信息为空";
			result.setStatus(500);
			msg = "用户信息为空";
			result.setMsg(msg);
			result.setData(null);
		}
		int i = tUserMapper.insert(userPo);
		if(i==0){
			result.setData(null);
			return result;
		}
		if (userPo != null) {
			userPo.setLoginPwd(DigestUtils.sha1Hex(defaultpwd + userPo.getId()));
		}
		if (result.getStatus() == 200) {
			
			// 添加用户和角色关联关系
			TUserRole tUserRole = new TUserRole();
			tUserRole.setUserId(userPo.getId());
			tUserRole.setRoleId(userVo.getRoleId());
			int j = tUserRoleMapper.insert(tUserRole);
			if(i==0){
				result.setData(null);
				return result;
			}
			result.setData(tUserRole);
		}

		return result;
	}

	/**
	 * 根据id修改对象
	 * 
	 * @param result
	 * @param userVo
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Result updateUser(Result result, UserVo userVo, Long userId) throws Exception {
		
		TUser po = (TUser) tUserMapper.selectByPrimaryKey(userId);
		if (po == null) {
			result.setStatus(500);
			result.setMsg("用户不存在");
			result.setData(null);
			return result;
		}
		if (userId != 1 && po.getIsManager() == true) {
			result.setStatus(500);
			result.setMsg("非法修改用户信息！");
			result.setData(null);
			return result;
		}
		po.setUserName(userVo.getUserName());
		po.setStatus(userVo.getStatus());
		po.setUpdateBy(userId);
		po.setUpdateDate(new Date());
		po.setPhone(userVo.getPhone());
		tUserMapper.updateByPrimaryKey(po);
		if (userId == 1) {// 修改用户不为1（超级管理员）不修改角色
			return result;
		}
		if (result.getStatus() == 200) {
			TUserRole tur=new TUserRole();
			tur.setUserId(userVo.getId());
			TUserRole tUserRole = (TUserRole) tUserRoleMapper.selectOne(tur);
			if (null == tUserRole) {
				TUserRole tUserRolePo = new TUserRole();
				tUserRolePo.setUserId(userVo.getId());
				tUserRolePo.setRoleId(userVo.getRoleId());
				tUserRoleMapper.insert(tUserRolePo);
			} else {
				if (tUserRole.getRoleId() != null && !tUserRole.getRoleId().equals(userVo.getRoleId())) {
					// 不相同 则需要修改，用户和角色关联关系
					TUserRole tUserRolePo = new TUserRole();
					tUserRolePo.setUserId(userVo.getId());
					tUserRolePo.setRoleId(userVo.getRoleId());
					tUserRoleMapper.insert(tUserRolePo);
				}
			}

			
		}
		return result;
	}

	/**
	 * 根基id修改用户状态 0. 删除 1.正常 2.冻结 3.解冻
	 * 
	 * @param result
	 * @param id
	 * @param status
	 * @return
	 * @throws Exception
	 *//*
	public Result updateUserStatus(Result result, Long id, Integer status) throws Exception {
		result = tUserMapper.//updateStatus(result, status, id);
		return result;
	}

	*//**
	 * 
	 * @param result
	 * @param userId
	 * @param status
	 * @param updateBy
	 * @return
	 * @throws Exception
	 *//*
	public Result frozenUserById(Result result, String userId, Integer status, Long updateBy) throws Exception {
		if (StringUtils.isBlank(userId)) {
			result.setMsg(MessageResource.PARAMETERS_ERROR);
			throw new FunctionException(result, "");
		}
		result = userDao.updateUserStatus(result, Long.valueOf(userId), status, updateBy);
		return result;
	}

	*//**
	 * 检验账号是否存在
	 * 
	 * @param result
	 * @param loginName
	 * @return
	 * @throws Exception
	 *//*
	public Result findUserIsRegister(Result result, String loginName) throws Exception {
		if (StringUtils.isBlank(loginName)) {
			result.setMsg(MessageResource.PARAMETERS_ERROR);
			result.setData(null);
			return result;
		}
		return userDao.findUserIsRegister(result, loginName);
	}

	*//**
	 * 重置密码
	 * 
	 * @param result
	 * @param newPassword
	 * @param id
	 * @param create_by
	 * @param bizId
	 * @return
	 * @throws Exception
	 *//*
	public Result resetUserPwd(Result result, String newPassword, Long id, Long updateUserId, Long bizId) throws Exception {
		result = userDao.getUser(result, id);
		if (result.getData() == null) {
			result.setStatus(500);
			String msg = "修改用户不存在！";
			result.setMsg(msg);
			return result;
		}
		UserPo userPo = result.getData();
		if (!updateUserId.equals(1L)) {// 为了安全，非系统管理员情况下，重置密码是否合法
			result = userBusinessDao.getUserBusinessByUserIdToBizId(result, id, bizId);// 判断被重置密码用户和操作用户是否为统一商家用户
			List<UserBusinessPo> list = result.getData();
			if (list == null || list.isEmpty()) {
				result.setStatus(500);
				result.setMsg("非法修改密码");
				return result;
			}
		}

		// 新密码加
		String digestPassword = DigestUtils.sha1Hex(newPassword + id);
		userPo.setLoginPwd(digestPassword);
		userPo.setUpdateBy(updateUserId);
		result.setData(userPo);
		return result;
	}

	*//**
	 * 
	 * @param result
	 * @param id
	 * @return
	 * @throws Exception
	 *//*
	public Result findUserByUserId(Result result, Long id) throws Exception {
		if (null == id) {
			result.setMsg(MessageResource.PARAMETERS_ERROR);
			throw new FunctionException(result, "");
		}
		UserPo us = (UserPo) userDao.findUserByUserId(result, id).getData();

		SysUserRolePo sysUserRolePo = new SysUserRolePo();
		// 获取用户和角色的关系
		result = sysUserRoleDao.findSysUserRole(result, id);

		List<SysUserRolePo> list = result.getData();
		if (list != null && list.size() > 0) {
			sysUserRolePo = list.get(0);
		}
		List<Long> userIds = new ArrayList<Long>();
		userIds.add(id);
		// 获取用户和用户组的关系
		Map<String, String> groupMap = (Map<String, String>) sysGroupDao.selectGroupIdVoByUserIds(result, userIds).getData();
		UserVo vo = new UserVo();

		PropertyUtils.copyProperties(vo, us);
		vo.setRoleId(sysUserRolePo.getRoleId());
		result = userBusinessDao.findUserBusinessByUserId(result, id);
		List<UserBusinessPo> ublist = result.getData();
		if (ublist != null && !ublist.isEmpty()) {
			UserBusinessPo userBusinessPo = ublist.get(0);
			vo.setRoleType(userBusinessPo.getAccountType());
		}
		vo.setGroupIds(groupMap.get(id.toString()));
		result.setData(vo);
		return result;
	}

	*//**
	 * 
	 * @param result
	 * @param userVo
	 * @param userId
	 * @param roleId
	 * @param page
	 * @return
	 * @throws Exception
	 *//*
	public Result findUserVByUserVo(Result result, UserVo userVo, Long userId, Long roleId, Page<UserPo> page) throws Exception {
		result = userDao.findUserByUser(result, userVo, userId, roleId, page);
		Pagination pagination = (Pagination) result.getData();
		if (pagination.getTotal() > 0) {
			List<UserPo> us = (List<UserPo>) pagination.getRows();
			List<UserPageVo> vos = new ArrayList<UserPageVo>();
			if (CollectionUtils.isNotEmpty(us)) {
				// 收集用户id
				List<Long> userids = new ArrayList<Long>();
				for (UserPo u : us) {
					userids.add(u.getId());
				}
				if (userids.size() > 0) {
					// 获取用户和角色的关系
					Map<String, String> roleMap = (Map<String, String>) sysRoleDao.selectRoleVoByUserIds(result, userids).getData();
					// 获取用户和用户组的关系
					Map<String, String> groupMap = (Map<String, String>) sysGroupDao.selectGroupVoByUserIds(result, userids).getData();
					for (UserPo u : us) {
						UserPageVo vo = new UserPageVo();
						if (roleId == 1) {// 超级管理员
							PropertyUtils.copyProperties(vo, u);
							if (u.getIsManager()) {
								vo.setManager(1);
							} else {
								vo.setManager(0);
							}
							result = userBusinessDao.findUserBusinessByUserId(result, u.getId());
							List<UserBusinessPo> list = result.getData();
							String roleStr = getRoleTypeToString(list);
							vo.setRoleName(roleStr);
						} else {
							PropertyUtils.copyProperties(vo, u);
							vo.setRoleName(roleMap.get(u.getId().toString()));
							vo.setGroupName(groupMap.get(u.getId().toString()));
						}
						if (!vos.contains(vo)) {
							vos.add(vo);
						}
					}
				}

			}
			pagination.setRows(vos);
			pagination.setTotal(pagination.getTotal());
			result.setData(pagination);
		}
		return result;
	}*/



	

	
	

	
}
