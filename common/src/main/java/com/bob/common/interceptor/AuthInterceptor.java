package com.bob.common.interceptor;

import com.bob.common.annotation.AuthAnnotation;
import com.bob.common.constant.CONSTANT;
import com.bob.common.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Arrays;

public class AuthInterceptor extends HandlerInterceptorAdapter {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("execute AuthInterceptor.");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(CONSTANT.USER_SESSION.USER_SESSION_KEY);
        if(user == null){
            return true;
        }
        String[]  authCode = user.getAuthCode();
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String code = null;
        if (method.isAnnotationPresent(AuthAnnotation.class)) {
            AuthAnnotation authAnnotation = method.getAnnotation(AuthAnnotation.class);
            code = authAnnotation.code();
            return Arrays.asList(authCode).contains(code);
        }
        return true;
    }
}
