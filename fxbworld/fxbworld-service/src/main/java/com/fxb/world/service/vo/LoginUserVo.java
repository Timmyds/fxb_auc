package com.fxb.world.service.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fxb.world.util.security.UserResMenu;


public class LoginUserVo implements Serializable {

	/**
	 * 用户基本信息
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;
	private String loginName;
	private String userName;// 用户名称
	private Date gmtLast;// 最后登录时间
	private String lastIp;//上次登录ip
	private String loginIp;//登录ip
	private Date loginTime;//登录时间
	private Long loginCount;
	private String token;
	private Long bizId; // 平台id/供应商id/县域中心id、/网点id
	private String sessionId;
	private Long belongId; // 所属县域ID
	private Integer roleType; // (accountType) 角色类型 2:平台管理 3:县域 4:网点，5：供应商 9会员
	private Long roleId; // 角色类型
	private boolean isManager;//是否为管理员账号
	// 主账户id
	private Long masteraccId;// 平台账户id/供应商账户id/中心账户id/网点账户id
	private String oldUsrId; //
	private String bizName; //
	private Long errorCount;

	private List<UserResMenu> resourcesMenu;// 菜单

	private boolean isCertificate;// 是否需要绑定银行卡
	private boolean isNet;// 是否网签
	private Integer status;// 状态
	private Date createDate; // 用于判断是否是老网点
	private Integer isProtection;// 是否开启登录保护
	private String phone;//手机号

	private  Integer shopType;//网点类型（网点账号登录时区分网点类型，枚举：ShopTypeEnum）
	private  Integer supplierType;//供应商类型（供应商账号登录时区分网点类型，枚举：SupplierTypeEnum）

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Date getGmtLast() {
		return gmtLast;
	}

	public void setGmtLast(Date gmtLast) {
		this.gmtLast = gmtLast;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Long getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Long loginCount) {
		this.loginCount = loginCount;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getBizId() {
		return bizId;
	}

	public void setBizId(Long bizId) {
		this.bizId = bizId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Long getBelongId() {
		return belongId;
	}

	public void setBelongId(Long belongId) {
		this.belongId = belongId;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getMasteraccId() {
		return masteraccId;
	}

	public void setMasteraccId(Long masteraccId) {
		this.masteraccId = masteraccId;
	}

	public String getOldUsrId() {
		return oldUsrId;
	}

	public void setOldUsrId(String oldUsrId) {
		this.oldUsrId = oldUsrId;
	}

	public Long getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Long errorCount) {
		this.errorCount = errorCount;
	}

	public boolean isCertificate() {
		return isCertificate;
	}

	public void setCertificate(boolean isCertificate) {
		this.isCertificate = isCertificate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public boolean getIsNet() {
		return isNet;
	}

	public void setNet(boolean isNet) {
		this.isNet = isNet;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	public List<UserResMenu> getResourcesMenu() {
		return resourcesMenu;
	}

	public void setResourcesMenu(List<UserResMenu> resourcesMenu) {
		this.resourcesMenu = resourcesMenu;
	}



	public Integer getIsProtection() {
		return isProtection;
	}

	public void setIsProtection(Integer isProtection) {
		this.isProtection = isProtection;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isManager() {
		return isManager;
	}

	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}



	public String getLastIp() {
		return lastIp;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Integer getShopType() {
		return shopType;
	}

	public void setShopType(Integer shopType) {
		this.shopType = shopType;
	}

	public Integer getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(Integer supplierType) {
		this.supplierType = supplierType;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
