package com.fxb.world.shiro;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.fxb.world.dao.TResourcesMapper;
import com.fxb.world.dao.TRoleResourcesMapper;
import com.fxb.world.dao.TUserMapper;
import com.fxb.world.dao.TUserRoleMapper;
import com.fxb.world.entity.TUser;
import com.fxb.world.entity.TUserRole;

/**
 * Created by yangqj on 2017/4/21.
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private TUserMapper userRepo;

    @Resource
    private TResourcesMapper tResourcesMapper;
    
    @Resource 
    private TUserRoleMapper userRoleRepo;
    @Resource
    private TRoleResourcesMapper roleResourcesRepo;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    	TUser user= (TUser) SecurityUtils.getSubject().getPrincipal();//User{id=1, username='admin', password='3ef7164d1f6167cb9f2658c07d3c2f0a', enable=1}
     
    /*	 Map<String,Object> map = new HashMap<String,Object>();
         map.put("userid",user.getId());
         List<TResources> resourcesList = tResourcesMapper.loadUserResources(map);
         // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
         SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
         for(Resources resources: resourcesList){
             info.addStringPermission(resources.getResurl());
         }*/
    	 SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
       /* String username = (String)token.getPrincipal();
        TUser user = userRepo.findByLoginName(username);
        if(user==null) throw new UnknownAccountException();
        if (0==user.getEnable()) {
            throw new LockedAccountException(); // 帐号锁定
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, //用户
                user.getLoginPwd(), //密码
                ByteSource.Util.bytes(username),
                getName()  //realm name
        );
        // 当验证都通过后，把用户信息放在session里
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("userSession", user);
        session.setAttribute("userSessionId", user.getId());*/
    	SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                new TUser(), //用户
                "asdas", //密码
                ByteSource.Util.bytes("dasdas"),
                getName()  //realm name
        );
        return authenticationInfo;
    }

    /**
     * 指定principalCollection 清除
     */
  /*  public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {

        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
*/
}
