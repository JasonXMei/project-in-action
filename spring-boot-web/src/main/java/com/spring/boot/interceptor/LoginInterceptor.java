package com.spring.boot.interceptor;

import cn.hutool.core.util.StrUtil;
import com.spring.boot.enums.ResponseCodeEnum;
import com.spring.boot.exception.BusinessException;
import com.spring.boot.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Jason
 * @Date 2022/06/21
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private CredentialService credentialService;

    public static String HEADER_KEY = "Authorization";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(HEADER_KEY);
        if (StrUtil.isEmpty(token)) {
            throw new BusinessException(ResponseCodeEnum.UNAUTHORIZED, "header token cannot be blank");
        }

        credentialService.verifyToken(token);
        return true;
    }

}
