package com.fd.aop;

import com.fd.service.ManagerService;
import com.fd.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class PermissionAspect {
    @Resource
    private UserService userService;

    @Pointcut("@annotation(com.fd.annotation.Role)")
    public void point(){}

    @Around("point()")
    public Object around(ProceedingJoinPoint joinPoint){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String name=(String)request.getSession().getAttribute("name");
        //根据用户名获取到用户信息
        //查询用户对应角色
        //查询权限
        //判断是否有权限
        //有则放行
        //无则返回
        return  null;
    }
}
