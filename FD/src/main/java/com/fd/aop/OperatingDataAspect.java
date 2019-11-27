package com.fd.aop;


import com.fd.annotation.ServerLog;
import com.fd.pojo.Operation;
import com.fd.service.AdminService;
import com.fd.vo.ManagerVo;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class OperatingDataAspect {
    private static String name;
    @Resource
    private AdminService adminService;

    @Pointcut("@annotation(com.fd.annotation.ServerLog)")
    public void point() {
        System.out.println("进入了切面");
    }

    @Around("point()")
    public Object addOperating(ProceedingJoinPoint proceedingJoinPoint) throws Exception {
        //获取正在执行的方法的对象
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        //2通过方法对象获取方法上的注解对象
        ServerLog annotation = method.getAnnotation(ServerLog.class);
        //3获取注解中的值
        String value = annotation.value();
        //4获取requeset
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //5通过request和工具类得到ip
        String ip = getRemortIP(request);
        Operation operation = new Operation();
        HttpSession session = request.getSession();
        if (value.equals("登录了该系统")) {
            Map<String, Object> map = getFieldsNameValueMap(proceedingJoinPoint);
            ManagerVo vo = (ManagerVo) map.get("password");
            name = vo.getLoginId();
            request.getSession().setAttribute("name",name);
        }
        try {
            Object proceed = proceedingJoinPoint.proceed();
            operation.setCreateTime(new Date());
            operation.setOperation(value);
            operation.setName(name);
            operation.setIp(ip);

            adminService.addOperation(operation);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }

    }

    private String getRemortIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    private Map<String, Object> getFieldsNameValueMap(JoinPoint joinPoint) throws Exception {
        Object[] args = joinPoint.getArgs();
        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        String methodName = joinPoint.getSignature().getName(); //获取方法名称
        Map<String, Object> map = new HashMap<String, Object>();
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(this.getClass());
        pool.insertClassPath(classPath);
        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            throw new RuntimeException();
        }
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++) {
            map.put(attr.variableName(i + pos), args[i]);//paramNames即参数名
        }
        return map;
    }
}
