package com.bob.system.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bob.common.service.RedissionService;
import com.bob.common.util.JwtUtil;
import com.bob.common.vo.RO;
import com.bob.system.entity.SysUser;
import com.bob.system.service.ISysUserService;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.bob.common.constant.ERRORCODE.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bob
 * @since 2018-11-03
 */
@RestController
@Api(value = "LoginController|用户登录操作的控制器")
public class LoginController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RedissionService redissionService;

    @PostMapping(value = "/login")
    public RO login(@RequestParam("username")String username,@RequestParam("password")String password){
        RO ro = new RO().setError(LOGIN_FAILED);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        try {
            subject.login(usernamePasswordToken);
            SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            String token = JwtUtil.createJWT(sysUser.getUsername());
            JSONObject j = new JSONObject();
            j.put("token",token);
            j.put("roles",sysUser.getRoleList());
            j.put("name",sysUser.getName());
            j.put("userface","");
            j.put("username",sysUser.getUsername());
            ro.put("user",j).setError("000000","登陆成功!");
        } catch (IncorrectCredentialsException e) {
            ro.setError(LOGIN_FAILED_PASSWORD);
        } catch (LockedAccountException e) {
            ro.setError(LOGIN_FAILED_LOCKED);
        } catch (AuthenticationException e) {
            ro.setError(LOGIN_FAILED_NOT_EXIST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ro ;
    }
    /**
     * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
     * @return RO
     */
    @RequestMapping(value = "/unauth")
    public RO unauth() {
        return new RO().setError(UNLOGIN);
    }

}
