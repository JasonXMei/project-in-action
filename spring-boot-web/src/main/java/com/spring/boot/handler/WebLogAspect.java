package com.spring.boot.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Sets;
import com.spring.boot.entity.SystemLog;
import com.spring.boot.service.SystemLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @Author Jason
 * @Date 2023-04-26
 */
@Aspect
@Component
@Order(1)
@Slf4j
public class WebLogAspect {

    private HashSet<String> ignoreHeaders = Sets.newHashSet("cache-control,postman-token,host,connection,content-length,sec-ch-ua,accept,content-type,sec-ch-ua-mobile,user-agent,sec-ch-ua-platform,origin,sec-fetch-site,sec-fetch-mode,sec-fetch-dest,referer,accept-encoding,accept-language,cookie,request-origion".split(","));

    @Autowired
    private SystemLogService systemLogService;

    @Pointcut("execution(public * com.spring.boot.controller.*.*(..))")
    public void systemLog() {
    }

    @Around("systemLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = DateUtil.createStopWatch();
        stopWatch.start();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        SystemLog systemLog = new SystemLog();
        Object result = null;
        try {
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            systemLog.setParameter(JSONUtil.toJsonStr(getParameter(method, joinPoint.getArgs())));
            systemLog.setHeader(JSONUtil.toJsonStr(getHeader(request)));
            systemLog.setUri(request.getRequestURI());
            systemLog.setMethod(request.getMethod());

            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            systemLog.setException(throwable.getMessage());
            throw throwable;
        } finally {
            stopWatch.stop();
            if (Objects.nonNull(result)) {
                systemLog.setResponse(JSONUtil.toJsonStr(result));
            }
            systemLog.setSpendTime(stopWatch.getTotalTimeMillis());
            systemLogService.save(systemLog);
        }

        return result;
    }

    private Map<String, String> getHeader(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (ignoreHeaders.contains(headerName)) {
                continue;
            }

            String headerValue = request.getHeader(headerName);
            map.put(headerName, headerValue);
        }

        return map;
    }

    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (ObjectUtil.isNotNull(requestParam)) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (StrUtil.isNotEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            } else {
                argList.add(args[i]);
            }
        }

        return argList;
    }

}
