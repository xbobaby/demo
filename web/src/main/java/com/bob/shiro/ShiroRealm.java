package com.bob.shiro;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bob.system.entity.SysPermission;
import com.bob.system.entity.SysRole;
import com.bob.system.entity.SysUser;
import com.bob.system.service.ISysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

public class ShiroRealm extends AuthorizingRealm {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Reference(version = "${bob.service.version}")
    private ISysUserService sysUserService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser userInfo  = (SysUser)principals.getPrimaryPrincipal();
        for(SysRole role:userInfo.getRoleList()){
            authorizationInfo.addRole(role.getRole());
            for(SysPermission p:role.getPermissionList()){
                authorizationInfo.addStringPermission(p.getPermission());
            }
        }
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        logger.info("ShiroRealm执行doGetAuthenticationInfo,token：{}", token);
/*        String accessToken = (String) token.getPrincipal();

        String username = JwtUtil.getUserId(accessToken);
        if (StringUtils.isEmpty(username)) {
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }

        // 查询用户信息
        SysUser user = sysUserService.getOne(new QueryWrapper<SysUser>().eq("username",username));
        // 账号锁定
        if ("1".equals(user.getState())) {
            throw new LockedAccountException();
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
        return info;*/
        //获取用户的输入的账
        String username = (String)token.getPrincipal();
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        SysUser user = sysUserService.getSysUserByUsername(username);
        if(user == null){
            throw new UnknownAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

}
