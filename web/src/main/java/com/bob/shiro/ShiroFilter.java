package com.bob.shiro;

import com.alibaba.fastjson.JSON;
import com.bob.common.vo.RO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.bob.common.constant.ERRORCODE.*;


public class ShiroFilter extends AuthenticatingFilter {

    private static final Logger logger = LoggerFactory.getLogger(ShiroFilter.class);

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        logger.info("进入ShiroFilter.createToken()方法......");
        // 获取请求token
        String token = getRequestToken((HttpServletRequest) request);

        if (StringUtils.isBlank(token)) {
            return null;
        }
        return new JwtToken(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }

        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        logger.info("进入ShiroFilter.onAccessDenied()方法......");
        // 对于跨域CORS请求，每次ajax操作会请求两次，第一次浏览器自己发起的OPTION请求到server端验证该次请求是否支持跨域，所以对OPTION请求直接放行，不做鉴权认证
        if ("OPTIONS".equals(((HttpServletRequest) request).getMethod())) {
            return true;
    }
        // 获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);

        if (StringUtils.isEmpty(token)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json;charset=utf-8");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest) request).getHeader("Origin"));

            String json = JSON.toJSONString(new RO().setError(UNLOGIN));
            logger.error("执行ShiroFilter.onAccessDenied()方法完毕，出参:{}", json);
            httpResponse.getWriter().print(json);

            return false;
        }
        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
                                     ServletResponse response) {
        logger.info("进入ShiroFilter.onLoginFailure()方法......,token：{}, e：{}", token, e);
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest) request).getHeader("Origin"));
        try {
            // 处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            RO ro = new RO().setError(LOGIN_FAILED);
            String json = JSON.toJSONString(ro);
            logger.info("执行ShiroFilter.onLoginFailure()方法完毕......,出参json：{}", json);
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {
            logger.error("执行ShiroFilter.onLoginFailure()方法,发生异常：{}", e1);
        }
        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        // 从header中获取token
        String token = httpRequest.getHeader("token");
        // 如果header中不存在token，则从参数中获取token
        if (StringUtils.isEmpty(token)) {
            token = httpRequest.getParameter("token");
        }
        logger.info("执行ShiroFilter.getRequestToken()方法完毕,出参token：{}", token);
        return token;
    }
    @Bean
    public FilterRegistrationBean registration(ShiroFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }
}
