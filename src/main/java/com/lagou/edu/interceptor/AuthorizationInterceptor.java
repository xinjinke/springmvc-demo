package com.lagou.edu.interceptor;

import com.lagou.edu.annotation.MySecurity;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        if(method.isAnnotationPresent(MySecurity.class)){
            boolean authPass = false;
            System.out.println("开始权限校验");
            MySecurity security = method.getAnnotation(MySecurity.class);
            String[] authorizathonValue = security.value();
            String accessAthority = request.getParameter("username");

            int size = authorizathonValue.length;
            for (int i = 0; i < size; i++) {
                if(accessAthority.equalsIgnoreCase(authorizathonValue[i])){
                    authPass = true;
                }
            }

            if (!authPass) {
                throw new RuntimeException("没有访问权限");
            }
        }
        return true;
    }
}
